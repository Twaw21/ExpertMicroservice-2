package com.oecci.expert.utils;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.liferay.portal.vulcan.pagination.Pagination;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Composant OSGi central qui encapsule ObjectEntryLocalService et
 * ObjectDefinitionLocalService pour les opérations CRUD sur les
 * Liferay Custom Objects (Object Definitions).
 *
 * <p>Remplace Utils.executeHttpRequest() pour toutes les opérations de
 * persistance internes, en utilisant le vrai userId (contextUser.getUserId())
 * à la place du token OAuth2 applicatif partagé.</p>
 *
 * <p><strong>ERCs des Object Definitions</strong> : les noms passés en paramètre
 * {@code objectDefinitionErc} suivent la convention Liferay 7.4 :
 * {@code C_<NomObjet>} (ex : {@code C_Client}, {@code C_ExpertComptable}).
 * Vérifier les valeurs exactes dans Admin → Objects si une PortalException
 * "Object Definition introuvable" est levée au démarrage.</p>
 *
 * @author OECCI / DiginFactory — Refactorisation sécurité 2026
 */
@Component(immediate = true, service = ObjectEntryHelper.class)
public class ObjectEntryHelper {

	private static final Log _log = LogFactoryUtil.getLog(ObjectEntryHelper.class);


	/**
	 * Cache thread-safe des IDs d'Object Definition par (companyId + ERC).
	 * Évite un appel base de données à chaque requête.
	 */
	private final ConcurrentHashMap<String, Long> _definitionIdCache =
		new ConcurrentHashMap<>();

	// -------------------------------------------------------------------------
	// Résolution de l'Object Definition
	// -------------------------------------------------------------------------

	/**
	 * Résout l'objectDefinitionId à partir de l'ERC (externalReferenceCode).
	 * Résultat mis en cache après le premier appel.
	 *
	 * @param companyId companyId de l'instance Liferay
	 * @param erc       externalReferenceCode de la définition (ex : "C_Client")
	 * @return objectDefinitionId
	 * @throws IllegalStateException si la définition est introuvable
	 */
	public long resolveObjectDefinitionId(long companyId, String erc) {
		String cacheKey = companyId + ":" + erc;

		return _definitionIdCache.computeIfAbsent(cacheKey, k -> {
			try {
				ObjectDefinition od =
					_objectDefinitionLocalService
						.fetchObjectDefinitionByExternalReferenceCode(
							erc, companyId);

				if (od == null) {
					_log.error(
						"[ObjectEntryHelper] Object Definition introuvable " +
							"pour ERC='" + erc + "' et companyId=" + companyId +
							". Vérifier dans Admin → Objects.");
					throw new IllegalStateException(
						"Object Definition introuvable : ERC=" + erc);
				}

				_log.info(
					"[ObjectEntryHelper] ObjectDefinition résolue : ERC=" +
						erc + " → id=" + od.getObjectDefinitionId());

				return od.getObjectDefinitionId();
			}
			catch (IllegalStateException e) {
				throw e;
			}
			catch (Exception e) {
				throw new IllegalStateException(
					"Erreur résolution ObjectDefinition ERC=" + erc, e);
			}
		});
	}

	// -------------------------------------------------------------------------
	// CRUD — Création
	// -------------------------------------------------------------------------

	/**
	 * Crée une nouvelle entrée dans un Custom Object.
	 *
	 * <p><strong>userId doit être contextUser.getUserId()</strong> —
	 * c'est le vrai utilisateur connecté, pas un utilisateur technique.</p>
	 *
	 * @param userId    ID de l'utilisateur connecté (contextUser.getUserId())
	 * @param groupId   ID du site (groupe) cible
	 * @param companyId ID de l'instance Liferay
	 * @param erc       ERC de l'Object Definition (ex : "C_Client")
	 * @param values    Map des valeurs des champs (nom du champ Liferay → valeur)
	 * @return l'ObjectEntry créée
	 * @throws Exception si la création échoue
	 */
	public ObjectEntry addEntry(
			long userId, long groupId, long companyId,
			String erc, Map<String, Serializable> values)
		throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);
		ServiceContext sc = _buildServiceContext(userId, groupId, companyId);

		_log.info(
			"[ObjectEntryHelper] addEntry : erc=" + erc +
				" userId=" + userId + " groupId=" + groupId);

		return _objectEntryLocalService.addObjectEntry(
			userId, groupId, odId, values, sc);
	}

	// -------------------------------------------------------------------------
	// CRUD — Mise à jour
	// -------------------------------------------------------------------------

	/**
	 * Met à jour une entrée existante.
	 *
	 * @param userId        ID de l'utilisateur connecté
	 * @param groupId       ID du site cible
	 * @param companyId     ID de l'instance Liferay
	 * @param objectEntryId ID de l'entrée à mettre à jour
	 * @param values        Map des nouvelles valeurs
	 * @return l'ObjectEntry mise à jour
	 * @throws Exception si la mise à jour échoue
	 */
	public ObjectEntry updateEntry(
			long userId, long groupId, long companyId,
			long objectEntryId, Map<String, Serializable> values)
		throws Exception {

		ServiceContext sc = _buildServiceContext(userId, groupId, companyId);

		_log.info(
			"[ObjectEntryHelper] updateEntry : objectEntryId=" + objectEntryId +
				" userId=" + userId);

		return _objectEntryLocalService.updateObjectEntry(
			userId, objectEntryId, values, sc);
	}

	// -------------------------------------------------------------------------
	// CRUD — Lecture par ID
	// -------------------------------------------------------------------------

	/**
	 * Récupère une entrée par son ID primaire.
	 *
	 * @param objectEntryId ID de l'entrée
	 * @return l'ObjectEntry, ou null si inexistante
	 */
	public ObjectEntry getEntry(long objectEntryId) {
		try {
			_log.info(">> Getting entry by ID");
			return _objectEntryLocalService.fetchObjectEntry(objectEntryId);
		}
		catch (Exception e) {
			_log.info(
				"[ObjectEntryHelper] getEntry : objectEntryId=" +
					objectEntryId + " introuvable — " + e.getMessage());
			return null;
		}
	}

	/**
	 * Récupère une entrée par son ID et lève une exception si absente.
	 */
	public ObjectEntry getEntryOrThrow(long objectEntryId) throws Exception {
		ObjectEntry entry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		if (entry == null) {
			throw new IllegalArgumentException(
				"ObjectEntry introuvable : id=" + objectEntryId);
		}

		return entry;
	}

	// -------------------------------------------------------------------------
	// Permission helper — switch de PermissionChecker pour un userId donné
	// -------------------------------------------------------------------------

	/**
	 * Remplace temporairement le {@link PermissionChecker} du thread courant
	 * par celui de l'utilisateur {@code userId} et retourne l'ancien checker
	 * pour permettre sa restauration dans un bloc {@code finally}.
	 *
	 * <p><strong>Pourquoi c'est nécessaire :</strong><br>
	 * {@code ObjectEntryLocalService.getValuesList} utilise
	 * {@link PermissionThreadLocal} pour les vérifications d'accès, pas le
	 * paramètre {@code userId}. Si le thread tourne sous les permissions de
	 * l'utilisateur connecté sans droit VIEW, {@code getValuesList} retourne
	 * une liste vide même si un {@code techUserId} omniadmin est passé.</p>
	 *
	 * <p><strong>Utilisation :</strong> passer {@code techUserId} pour un accès
	 * total, ou {@code contextUser.getUserId()} pour un accès restreint aux
	 * permissions de l'utilisateur connecté.</p>
	 *
	 * @param userId ID de l'utilisateur dont le checker doit être activé
	 * @return le {@link PermissionChecker} précédent, à restaurer dans finally
	 */
	public PermissionChecker switchPermissionChecker(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		PermissionChecker previous = PermissionThreadLocal.getPermissionChecker();

		User user = userLocalService.getUser(userId);
		PermissionChecker checker = PermissionCheckerFactoryUtil.create(user);
		PermissionThreadLocal.setPermissionChecker(checker);

		_log.info(
			"[ObjectEntryHelper] switchPermissionChecker — userId=" + userId +
				" isOmniadmin=" + checker.isOmniadmin());

		return previous;
	}

	// -------------------------------------------------------------------------
	// CRUD — Recherche par filtre OData
	// -------------------------------------------------------------------------


//	public List<ObjectEntry> searchByFilter(
//			long userId, long companyId, long groupId,
//			String erc, String filterString) throws Exception {
//		return _searchByFilter(userId, companyId, groupId, erc, filterString, (OrderByExpression[]) null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
//	}

	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString) throws Exception {
		return _searchByFilter(userId, companyId, groupId, erc, filterString, new Sort[]{}, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString, Sort[] sorts, Long page, Long pageSize)
			throws Exception {

		int _page = (page != null && page > 0) ? page.intValue() : 1;
		int start = (_page - 1) * pageSize.intValue();
		int end = start + ((pageSize != null && pageSize > 0) ? pageSize.intValue() : 20);
		return _searchByFilter(userId, companyId, groupId, erc, filterString, sorts != null ? sorts : null, start, end);
	}

/*	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString, OrderByExpression[] orderByExpressions, Long page, Long pageSize)
					throws Exception {
		
		int _page = (page != null && page > 0) ? page.intValue() : 1;
		int start = (_page - 1) * pageSize.intValue();
		int end = start + ((pageSize != null && pageSize > 0) ? pageSize.intValue() : 20);
//		return _searchByFilter(userId, companyId, groupId, erc, filterString, sorts != null ? sort : null, start, end);
		return _searchByFilter(userId, companyId, groupId, erc, filterString, orderByExpressions, start, end);
	}
*/
	/**
	 * Recherche des entrées avec un filtre OData (équivalent des appels
	 * GET /o/c/{object}/?filter=...).
	 *
	 * <p><strong>IMPORTANT</strong> : le filterString doit être construit
	 * uniquement avec des valeurs validées/encodées côté appelant pour éviter
	 * toute injection OData.</p>
	 *
	 * <p>Exemples de filterString valides :</p>
	 * <ul>
	 *   <li>{@code "email eq 'user@example.com'"}</li>
	 *   <li>{@code "r_iDExpertComptable_c_expertComptableId eq '42'"}</li>
	 *   <li>{@code "validationStatut eq 'APPROUVE'"}</li>
	 * </ul>
	 *
	 * @param userId      ID utilisateur (pour le contexte de sécurité)
	 * @param companyId   ID de l'instance Liferay
	 * @param groupId     ID du site
	 * @param erc         ERC de l'Object Definition
	 * @param filterString filtre OData validé
	 * @return liste des ObjectEntry correspondantes
	 */
	/**
	 * Recherche des entrées avec un filtre OData.
	 *
	 * <p>Passer {@code techUserId} (compte technique omniadmin) pour un accès
	 * complet indépendant des permissions VIEW, ou {@code contextUser.getUserId()}
	 * pour restreindre les résultats aux permissions de l'utilisateur connecté.</p>
	 *
	 * @param userId       ID de l'utilisateur dont les permissions seront actives
	 * @param companyId    ID de l'instance Liferay
	 * @param groupId      ID du site (0 = scope company)
	 * @param erc          ERC de l'Object Definition
	 * @param filterString filtre OData validé (construit via {@link #buildEqFilter})
	 * @return liste des ObjectEntry correspondantes
	 */
	private List<ObjectEntry> _searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString, Sort[] sorts, int start, int end)
			throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);

		_log.info(
				"[ObjectEntryHelper] searchByFilter : erc=" + erc +
						" filter='" + filterString + "' odId=" + odId);

		ObjectDefinition od =
				_objectDefinitionLocalService.getObjectDefinition(odId);

		PermissionChecker previousChecker = switchPermissionChecker(userId);

		try {
			Predicate predicate = filterString != null
					? _buildPredicate(filterString, od)
					: null;

			List<Map<String, Serializable>> maps =
					_objectEntryLocalService.getValuesList(
							groupId,
							companyId,
							userId,
							odId,
							predicate,
							null,
							start < 0 ? QueryUtil.ALL_POS : start,
							end   < 0 ? QueryUtil.ALL_POS : end,
							sorts);

			return _resolveObjectEntries(maps, od);
		}
		catch (Exception e) {
			_log.info(
					"[ObjectEntryHelper] FilterFactory non supporté pour le filtre '" +
							filterString + "' (erc=" + erc +
							") — fallback getValuesList in-memory. " +
							"Cause : " + e.getMessage());

			List<Map<String, Serializable>> allMaps =
					_objectEntryLocalService.getValuesList(
							groupId, companyId, userId, odId,
							null, null,
							QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			List<Map<String, Serializable>> filteredMaps =
					_filterMaps(allMaps, filterString);

			return _resolveObjectEntries(filteredMaps, od);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(previousChecker);
		}
	}

	/**
	 * Filtre in-memory une liste de maps selon une expression OData simplifiée.
	 *
	 * Supporte :
	 *   - field eq 'value'   (String avec quotes)
	 *   - field eq value     (valeur sans quotes)
	 *   - field eq null
	 *
	 * La comparaison est faite en String pour absorber les écarts de type
	 * (ex: champ de relation stocké en Long vs valeur passée en String).
	 *
	 * Supporte aussi les expressions composées avec "and" (insensible à la casse),
	 * ex: "field1 eq 'val1' and field2 eq 'val2'"
	 */
	private List<Map<String, Serializable>> _filterMaps(
			List<Map<String, Serializable>> allMaps, String filterString) {

		if (filterString == null || filterString.isBlank()) {
			return allMaps;
		}

		// Découper les clauses sur "and" (OData de base)
		String[] clauses = filterString.split("(?i)\\s+and\\s+");

		return allMaps.stream()
				.filter(map -> {
					for (String clause : clauses) {
						if (!_matchClause(map, clause.trim())) {
							return false;
						}
					}
					return true;
				})
				.collect(Collectors.toList());
	}

	/**
	 * Évalue une clause unitaire "field op value" sur une map.
	 * Opérateurs supportés : eq, ne, gt, ge, lt, le
	 */
	private boolean _matchClause(
			Map<String, Serializable> map, String clause) {

		// Regex : <champ> <op> <valeur>
		// La valeur peut être : 'texte', null, ou un nombre
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(
				"^(\\S+)\\s+(eq|ne|gt|ge|lt|le)\\s+(.+)$",
				java.util.regex.Pattern.CASE_INSENSITIVE);

		java.util.regex.Matcher m = p.matcher(clause);
		if (!m.matches()) {
			_log.warn("[ObjectEntryHelper] _filterMaps : clause non parseable : '" +
					clause + "' — ignorée.");
			return true; // clause inconnue → on ne filtre pas sur elle
		}

		String field    = m.group(1).trim();
		String operator = m.group(2).trim().toLowerCase();
		String rawValue = m.group(3).trim();

		// Retirer les quotes simples éventuelles : 'valeur' → valeur
		String expectedStr = rawValue.replaceAll("^'|'$", "");

		Serializable actual = map.get(field);

		// Cas null
		if ("null".equalsIgnoreCase(expectedStr)) {
			boolean isNull = (actual == null);
			return operator.equals("eq") ? isNull : !isNull;
		}

		if (actual == null) {
			return false;
		}

		String actualStr = String.valueOf(actual);

		switch (operator) {
			case "eq":
				return actualStr.equals(expectedStr);
			case "ne":
				return !actualStr.equals(expectedStr);
			case "gt":
			case "ge":
			case "lt":
			case "le":
				// Comparaison numérique si possible
				try {
					double actualNum   = Double.parseDouble(actualStr);
					double expectedNum = Double.parseDouble(expectedStr);
					switch (operator) {
						case "gt": return actualNum >  expectedNum;
						case "ge": return actualNum >= expectedNum;
						case "lt": return actualNum <  expectedNum;
						case "le": return actualNum <= expectedNum;
					}
				}
				catch (NumberFormatException nfe) {
					// Repli sur comparaison lexicographique
					int cmp = actualStr.compareTo(expectedStr);
					switch (operator) {
						case "gt": return cmp >  0;
						case "ge": return cmp >= 0;
						case "lt": return cmp <  0;
						case "le": return cmp <= 0;
					}
				}
				return false;
			default:
				return true;
		}
	}

	/** Variante paginée de {@link #searchByFilter(long, long, long, String, String)}. */
	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString, int start, int end)
		throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);

		_log.info(
			"[ObjectEntryHelper] searchByFilter paginé : erc=" + erc +
				" filter='" + filterString + "' start=" + start + " end=" + end);

		ObjectDefinition od =
			_objectDefinitionLocalService.getObjectDefinition(odId);

		PermissionChecker previousChecker = switchPermissionChecker(userId);

		try {
			Predicate predicate = _buildPredicate(filterString, od);

			List<Map<String, Serializable>> maps =
				_objectEntryLocalService.getValuesList(
					groupId, companyId, userId, odId,
					predicate, null,
					start, end, null);

			return _resolveObjectEntries(maps, od);
		}
		catch (Exception e) {
			_log.info(
				"[ObjectEntryHelper] FilterFactory non supporté pour le filtre '" +
					filterString + "' (erc=" + erc + ") — fallback getValuesList in-memory paginé. " +
					"Cause : " + e.getMessage());

			List<Map<String, Serializable>> allMaps =
				_objectEntryLocalService.getValuesList(
					groupId, companyId, userId, odId,
					null, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			List<Map<String, Serializable>> filteredMaps =
				_filterMaps(allMaps, filterString);

			List<ObjectEntry> filtered = _resolveObjectEntries(filteredMaps, od);

			if (start == QueryUtil.ALL_POS || end == QueryUtil.ALL_POS) {
				return filtered;
			}

			int size = filtered.size();

			return filtered.subList(Math.min(start, size), Math.min(end, size));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(previousChecker);
		}
	}

	/**
	 * Recherche avec filtre et tri.
	 *
	 * <p>Chemin optimal : filtre + tri poussés en base via {@code FilterFactory}
	 * et {@code Sort[]}. Si {@code FilterFactory} rejette le filtre (champ FK
	 * de relation non déclaré), fallback : filtre en mémoire scopé +
	 * tri SQL via DynamicQuery.</p>
	 *
	 * @param sortField  Nom du champ Liferay headless (ex : {@code "createDate"})
	 * @param ascending  {@code true} = ASC, {@code false} = DESC
	 * @param start      Indice de début (inclusif), {@link QueryUtil#ALL_POS} pour tout
	 * @param end        Indice de fin (exclusif), {@link QueryUtil#ALL_POS} pour tout
	 */
	/** Variante avec tri de {@link #searchByFilter(long, long, long, String, String)}. */
	public List<ObjectEntry> searchByFilterSorted(
			long userId, long companyId, long groupId,
			String erc, String filterString,
			String sortField, boolean ascending,
			int start, int end)
		throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);

		_log.info(
			"[ObjectEntryHelper] searchByFilterSorted : erc=" + erc +
				" filter='" + filterString + "' sort=" + sortField +
				" asc=" + ascending);

		ObjectDefinition od =
			_objectDefinitionLocalService.getObjectDefinition(odId);

		PermissionChecker previousChecker = switchPermissionChecker(userId);

		try {
			// Chemin optimal : filtre + tri délégués à la base.
			Predicate predicate = _buildPredicate(filterString, od);
			Sort sort = new Sort(sortField, !ascending);

			// start/end passés directement (ALL_POS = pas de borne).
			List<Map<String, Serializable>> maps =
				_objectEntryLocalService.getValuesList(
					groupId, companyId, userId, odId,
					predicate, null,
					start, end, null);

			return _resolveObjectEntries(maps, od);
		}
		catch (Exception e) {
			// Fallback : tri SQL via DynamicQuery + filtre en mémoire scopé.
			_log.info(
				"[ObjectEntryHelper] FilterFactory non supporté pour le filtre '" +
					filterString + "' (erc=" + erc + ") — fallback DynamicQuery+mémoire. " +
					"Cause : " + e.getMessage());

			DynamicQuery dq = _objectEntryLocalService.dynamicQuery();
			dq.add(RestrictionsFactoryUtil.eq("objectDefinitionId", odId));

			if (groupId > 0) {
				dq.add(RestrictionsFactoryUtil.eq("groupId", groupId));
			}

			dq.addOrder(
				ascending
					? OrderFactoryUtil.asc(sortField)
					: OrderFactoryUtil.desc(sortField));

			// Pagination SQL dans le DynamicQuery si des bornes sont demandées.
			@SuppressWarnings("unchecked")
			List<ObjectEntry> sorted = (start == QueryUtil.ALL_POS || end == QueryUtil.ALL_POS)
				? (List<ObjectEntry>) (List<?>) _objectEntryLocalService.dynamicQuery(dq)
				: (List<ObjectEntry>) (List<?>) _objectEntryLocalService.dynamicQuery(dq, start, end);

			List<ObjectEntry> filtered = _applyODataFilter(sorted, filterString);

			if (start == QueryUtil.ALL_POS || end == QueryUtil.ALL_POS) {
				return filtered;
			}

			int size = filtered.size();

			return filtered.subList(Math.min(start, size), Math.min(end, size));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(previousChecker);
		}
	}

	// -------------------------------------------------------------------------
	// Helpers internes — résolution SQL
	// -------------------------------------------------------------------------

	/**
	 * Compile une chaîne de filtre OData en {@link Predicate} SQL via
	 * {@link FilterFactory}. Retourne {@code null} si le filtre est vide
	 * (= pas de restriction, équivalent au SELECT sans WHERE).
	 */
	private Predicate _buildPredicate(String filterString, ObjectDefinition od)
		throws Exception {

		if (filterString == null || filterString.trim().isEmpty()) {
			return null;
		}

		return filterFactory.create(filterString.trim(), od);
	}

	/**
	 * Résout une liste de {@link Map} (issue de {@code getValuesList}) en
	 * {@link ObjectEntry} chargées via DynamicQuery.
	 *
	 * <p><strong>Pourquoi DynamicQuery et non {@code fetchObjectEntry} ?</strong><br>
	 * {@code getValuesList} retourne les Maps de la table d'extension Liferay
	 * (ex : {@code O_c_ExpertComptable}). La PK de cette table ({@code c_expertComptableId})
	 * n'est pas nécessairement égale à l'{@code objectEntryId} de la table
	 * {@code ObjectEntry}. Un appel à {@code fetchObjectEntry(c_expertComptableId)}
	 * retournerait {@code null} si les deux séquences divergent.
	 * DynamicQuery filtre directement sur {@code objectEntryId} du modèle
	 * {@link ObjectEntry}, ce qui est fiable quel que soit le stockage.</p>
	 *
	 * <p>Les IDs candidats sont extraits depuis les Maps dans cet ordre :</p>
	 * <ol>
	 *   <li>Clé dérivée : {@code c_<objectName>Id} (ex : {@code "c_clientId"})</li>
	 *   <li>{@code "id"}</li>
	 *   <li>{@code "objectEntryId"}</li>
	 * </ol>
	 */
	private List<ObjectEntry> _resolveObjectEntries(
		List<Map<String, Serializable>> maps, ObjectDefinition od) {

		if (maps == null || maps.isEmpty()) {
			return Collections.emptyList();
		}

		String derivedIdKey = _resolveIdKey(od);

		_log.info(
			"[ObjectEntryHelper] _resolveObjectEntries — od='" + od.getName() +
				"' derivedIdKey='" + derivedIdKey +
				"' count=" + maps.size() +
				"' mapKeys=" + maps.get(0).keySet());

		// 1. Extraction des IDs depuis toutes les Maps
		List<Long> ids = new ArrayList<>(maps.size());

		for (Map<String, Serializable> map : maps) {
			long id = _extractEntryId(map, derivedIdKey);

			if (id > 0) {
				ids.add(id);
			}
			else {
				_log.info(
					"[ObjectEntryHelper] _resolveObjectEntries — impossible d'extraire " +
						"l'ID de la map (derivedKey='" + derivedIdKey +
						"', mapKeys=" + map.keySet() + ")");
			}
		}

		if (ids.isEmpty()) {
			_log.info(
				"[ObjectEntryHelper] _resolveObjectEntries — aucun ID valide extrait " +
					"sur " + maps.size() + " map(s)");
			return Collections.emptyList();
		}

		_log.info(
			"[ObjectEntryHelper] _resolveObjectEntries — IDs extraits : " + ids);

		// 2. Chargement des ObjectEntry en bulk via DynamicQuery
		//    Fiable quelle que soit la relation PK extension ↔ objectEntryId
		DynamicQuery dq = _objectEntryLocalService.dynamicQuery();

		if (ids.size() == 1) {
			dq.add(RestrictionsFactoryUtil.eq("objectEntryId", ids.get(0)));
		}
		else {
			dq.add(RestrictionsFactoryUtil.in("objectEntryId", ids));
		}

		@SuppressWarnings("unchecked")
		List<ObjectEntry> entries =
			(List<ObjectEntry>) (List<?>) _objectEntryLocalService.dynamicQuery(dq);

		_log.info(
			"[ObjectEntryHelper] _resolveObjectEntries — " +
				entries.size() + " ObjectEntry(s) résolue(s) pour " +
				ids.size() + " ID(s)");

		return entries;
	}

	/**
	 * Extrait l'ID primaire depuis une map retournée par {@code getValuesList}.
	 *
	 * <p>Essaie les clés candidates dans l'ordre :</p>
	 * <ol>
	 *   <li>{@code derivedKey} — clé calculée (ex : {@code "c_clientId"})</li>
	 *   <li>{@code "id"}</li>
	 *   <li>{@code "objectEntryId"}</li>
	 * </ol>
	 *
	 * <p>La conversion tient compte du type réel de la valeur dans la Map
	 * ({@code Long}, {@code Integer}, {@code String}, etc.).</p>
	 *
	 * @return l'ID de l'entrée, ou {@code 0} si aucune clé ne correspond
	 */
	private long _extractEntryId(Map<String, Serializable> map, String derivedKey) {
		for (String key : new String[]{derivedKey, "id", "objectEntryId"}) {
			if (!map.containsKey(key)) {
				continue;
			}

			Serializable val = map.get(key);

			if (val == null) {
				continue;
			}

			long id;

			if (val instanceof Long) {
				id = (Long) val;
			}
			else if (val instanceof Number) {
				id = ((Number) val).longValue();
			}
			else {
				id = GetterUtil.getLong(String.valueOf(val));
			}

			if (id > 0) {
				_log.info(
					"[ObjectEntryHelper] _extractEntryId — key='" + key +
						"' type=" + val.getClass().getSimpleName() +
						" → id=" + id);
				return id;
			}
		}

		return 0L;
	}

	/**
	 * Reconstitue la clé ID telle que renvoyée par {@code getValuesList}.
	 * Convention Liferay : {@code "C_ExerciceN"} → {@code "c_exerciceNId"}.
	 */
	private String _resolveIdKey(ObjectDefinition od) {
		String idKey = od.getName() + "Id";

		if (idKey.length() >= 3) {
			idKey = idKey.substring(0, 3).toLowerCase() + idKey.substring(3);
		}

		return idKey;
	}

	// -------------------------------------------------------------------------
	// Filtrage de Maps (retournées par getValuesList) en mémoire
	// -------------------------------------------------------------------------

	/**
	 * Filtre une liste de Maps (issues de {@code getValuesList}) selon un
	 * filtre OData simple de la forme {@code fieldName eq 'value'} ou
	 * {@code fieldName ne 'value'}.
	 *
	 * <p>Contrairement à {@link #_applyODataFilter} qui opère sur des
	 * {@link ObjectEntry} et ne voit pas les champs de relation, cette méthode
	 * opère directement sur les Maps qui contiennent TOUS les champs y compris
	 * les FK de relation ({@code r_iDXxx_c_xxxId}).</p>
	 *
	 * <p>La comparaison est toujours textuelle ({@code String.valueOf(val)})
	 * pour rester cohérent avec {@link #buildEqFilter} qui encadre toujours
	 * la valeur entre apostrophes.</p>
	 */
	private List<Map<String, Serializable>> _filterMaps(
		List<Map<String, Serializable>> maps, String filterString) {

		if (maps == null || maps.isEmpty()) {
			return Collections.emptyList();
		}

		if (filterString == null || filterString.trim().isEmpty()) {
			return maps;
		}

		String trimmed = filterString.trim();

		// Gestion AND récursive
		List<String> andParts = _splitOnAnd(trimmed);

		if (andParts.size() > 1) {
			List<Map<String, Serializable>> result = maps;

			for (String part : andParts) {
				result = _filterMaps(result, part);
			}

			return result;
		}

		// Expression simple : fieldName eq 'value' ou fieldName ne 'value'
		trimmed = _stripOuterParens(trimmed);

		int eqIdx = trimmed.indexOf(" eq '");
		int neIdx = trimmed.indexOf(" ne '");

		if (eqIdx >= 0) {
			String fieldName = trimmed.substring(0, eqIdx).trim();
			String expected  = trimmed.substring(eqIdx + 5);

			if (expected.endsWith("'")) {
				expected = expected.substring(0, expected.length() - 1);
			}

			final String fField    = fieldName;
			final String fExpected = expected;

			return maps.stream()
				.filter(map -> {
					Object val = map.get(fField);
					return val != null && fExpected.equals(String.valueOf(val));
				})
				.collect(Collectors.toList());
		}

		if (neIdx >= 0) {
			String fieldName = trimmed.substring(0, neIdx).trim();
			String expected  = trimmed.substring(neIdx + 5);

			if (expected.endsWith("'")) {
				expected = expected.substring(0, expected.length() - 1);
			}

			final String fField    = fieldName;
			final String fExpected = expected;

			return maps.stream()
				.filter(map -> {
					Object val = map.get(fField);
					return val == null || !fExpected.equals(String.valueOf(val));
				})
				.collect(Collectors.toList());
		}

		_log.info(
			"[ObjectEntryHelper] _filterMaps : expression non parseable '" +
				filterString + "' — aucun filtrage appliqué.");

		return maps;
	}

	// -------------------------------------------------------------------------
	// Filtrage OData-lite en mémoire (conservé comme fallback interne)
	// -------------------------------------------------------------------------

	private List<ObjectEntry> _applyODataFilter(
		List<ObjectEntry> entries, String filterString) {

		if (filterString == null || filterString.isEmpty()) {
			return entries;
		}

		return entries.stream()
			.filter(e -> _matchesFilter(e, filterString.trim()))
			.collect(Collectors.toList());
	}

	private boolean _matchesFilter(ObjectEntry entry, String filter) {
		if (filter == null || filter.isEmpty()) {
			return true;
		}

		filter = filter.trim();

		// Découpe sur " and " en respectant la profondeur des parenthèses
		List<String> andParts = _splitOnAnd(filter);

		if (andParts.size() > 1) {
			for (String part : andParts) {
				if (!_matchesFilter(entry, part)) {
					return false;
				}
			}

			return true;
		}

		// Supprime les parenthèses extérieures si elles enveloppent tout
		filter = _stripOuterParens(filter);

		return _matchesSimpleExpression(entry, filter);
	}

	private List<String> _splitOnAnd(String filter) {
		List<String> parts = new ArrayList<>();
		int depth = 0;
		int start = 0;
		String lower = filter.toLowerCase();

		for (int i = 0; i < filter.length(); i++) {
			char c = filter.charAt(i);

			if (c == '(') {
				depth++;
			}
			else if (c == ')') {
				depth--;
			}
			else if ((depth == 0) && (i + 5 <= filter.length()) &&
					 lower.substring(i).startsWith(" and ")) {

				parts.add(filter.substring(start, i).trim());
				start = i + 5;
				i += 4;
			}
		}

		parts.add(filter.substring(start).trim());

		return parts;
	}

	private String _stripOuterParens(String s) {
		while (s.startsWith("(") && s.endsWith(")")) {
			int depth = 0;
			boolean allWrapped = true;

			for (int i = 0; i < s.length() - 1; i++) {
				if (s.charAt(i) == '(') {
					depth++;
				}
				else if (s.charAt(i) == ')') {
					depth--;

					if (depth == 0) {
						allWrapped = false;

						break;
					}
				}
			}

			if (!allWrapped) {
				break;
			}

			s = s.substring(1, s.length() - 1).trim();
		}

		return s;
	}

	/**
	 * Évalue une expression OData simple : {@code fieldName eq 'value'}
	 * ou {@code fieldName ne 'value'}.
	 * Les valeurs sont toujours entre apostrophes (convention buildEqFilter).
	 */
	private boolean _matchesSimpleExpression(ObjectEntry entry, String expr) {
		expr = _stripOuterParens(expr);

		int eqIdx = expr.indexOf(" eq '");
		int neIdx = expr.indexOf(" ne '");

		if (eqIdx >= 0) {
			String fieldName = expr.substring(0, eqIdx).trim();
			String expected = expr.substring(eqIdx + 5);

			if (expected.endsWith("'")) {
				expected = expected.substring(0, expected.length() - 1);
			}

			return _getValueAsString(entry, fieldName).equals(expected);
		}

		if (neIdx >= 0) {
			String fieldName = expr.substring(0, neIdx).trim();
			String expected = expr.substring(neIdx + 5);

			if (expected.endsWith("'")) {
				expected = expected.substring(0, expected.length() - 1);
			}

			return !_getValueAsString(entry, fieldName).equals(expected);
		}

		_log.info(
			"[ObjectEntryHelper] Expression OData non parseable, entrée " +
				"incluse par défaut : '" + expr + "'");

		return true;
	}

	private String _getValueAsString(ObjectEntry entry, String fieldName) {
		Map<String, Serializable> values = entry.getValues();

		if (values == null) {
			return "";
		}

		Object val = values.get(fieldName);

		return (val == null) ? "" : String.valueOf(val);
	}

	// -------------------------------------------------------------------------
	// Helpers de construction de filtres OData sécurisés
	// -------------------------------------------------------------------------

	/**
	 * Construit un filtre OData pour une égalité sur un champ String.
	 * Échappe les apostrophes dans la valeur pour éviter les injections.
	 *
	 * <p>Utilisation : {@code buildEqFilter("email", "user@example.com")}
	 * → {@code "email eq 'user@example.com'"}</p>
	 */
	public static String buildEqFilter(String fieldName, String value) {
		String escaped = (value == null) ? "" : value.replace("'", "''");
		return fieldName + " eq '" + escaped + "'";
	}

	/**
	 * Construit un filtre OData pour une égalité sur un champ numérique (Long).
	 */
	public static String buildEqFilter(String fieldName, long value) {
		return fieldName + " eq '" + value + "'";
	}

	/**
	 * Combine plusieurs filtres OData avec AND.
	 */
	public static String buildAndFilter(String... filters) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < filters.length; i++) {
			if (i > 0) {
				sb.append(" and ");
			}
			sb.append(filters[i]);
		}
		return sb.toString();
	}

	// -------------------------------------------------------------------------
	// Extraction de valeurs depuis ObjectEntry
	// -------------------------------------------------------------------------

	/**
	 * Extrait une valeur String depuis les valeurs d'une ObjectEntry.
	 * Retourne une chaîne vide si le champ est absent ou null.
	 */
	public static String getString(ObjectEntry entry, String fieldName) {
		if (entry == null) return "";

		Map<String, Serializable> values = entry.getValues();
		if (values == null || !values.containsKey(fieldName)) return "";

		Object val = values.get(fieldName);
		return (val == null) ? "" : String.valueOf(val);
	}

	/**
	 * Extrait une valeur Long depuis les valeurs d'une ObjectEntry.
	 */
	public static long getLong(ObjectEntry entry, String fieldName) {
		if (entry == null) return 0L;

		Map<String, Serializable> values = entry.getValues();
		if (values == null || !values.containsKey(fieldName)) return 0L;

		return GetterUtil.getLong(values.get(fieldName));
	}

	/**
	 * Extrait une valeur Double depuis les valeurs d'une ObjectEntry.
	 */
	public static double getDouble(ObjectEntry entry, String fieldName) {
		if (entry == null) return 0.0;

		Map<String, Serializable> values = entry.getValues();
		if (values == null || !values.containsKey(fieldName)) return 0.0;

		return GetterUtil.getDouble(values.get(fieldName));
	}

	/**
	 * Extrait une valeur Boolean depuis les valeurs d'une ObjectEntry.
	 */
	public static boolean getBoolean(ObjectEntry entry, String fieldName) {
		if (entry == null) return false;

		Map<String, Serializable> values = entry.getValues();
		if (values == null || !values.containsKey(fieldName)) return false;

		return GetterUtil.getBoolean(values.get(fieldName));
	}

	/**
	 * Copie les valeurs d'une ObjectEntry dans une nouvelle Map mutable.
	 * Utile pour préparer une mise à jour partielle.
	 */
	public static Map<String, Serializable> copyValues(ObjectEntry entry) {
		if (entry == null || entry.getValues() == null) {
			return new HashMap<>();
		}
		return new HashMap<>(entry.getValues());
	}

	// -------------------------------------------------------------------------
	// Construction du ServiceContext
	// -------------------------------------------------------------------------

	public ServiceContext createServiceContext(com.liferay.portal.kernel.model.User contextUser, Long userId, Long groupId) throws PortalException {
		ServiceContext sc = ServiceContextThreadLocal.getServiceContext();
		if (sc == null) {
			_log.info("ServiceContext est null, création d'un nouveau ServiceContext");
			sc = new ServiceContext();
			sc.setAttribute("ip", "127.0.0.1");
			_log.info("ServiceContext créé par défaut avec site groupId: ");
		}

		if(groupId == null ) {
			groupId = contextUser.getGroupId() > 0 ? contextUser.getGroupId() : 0L;
		}

		//Group group = groupLocalService.getGroup(groupId);

		_log.info(String.format("ContextUser : userId: %s - groupId: %s - group: %s", contextUser.getUserId(), contextUser.getGroupId(), contextUser.getGroup() != null ? contextUser.getGroup().getName() : null));
		_log.info(String.format("USERID: %s - GROUPID: %s - COMPANY ID: %s", userId, groupId, sc.getCompanyId() ));

		sc.setUserId(userId);
		sc.setScopeGroupId(groupId);
//		contextUser.setGroupId(groupId);
		contextUser.setUserId(userId);

		User user = userLocalService.getUser(userId);
		PermissionChecker pc = PermissionCheckerFactoryUtil.create(user);
		PermissionThreadLocal.setPermissionChecker(pc);

		return sc;
	}

	public ServiceContext _buildServiceContext(
		long userId, long groupId, long companyId) {

		ServiceContext sc = new ServiceContext();
		sc.setUserId(userId);
		sc.setScopeGroupId(groupId);
		sc.setCompanyId(companyId);
		return sc;
	}


	public <D> List<D> getFilteredObjectEntries(
			long objectDefinitionId, String odataFilter, ServiceContext sc, Class<D> cls) {
		try {
			ObjectDefinition objectDefinition = _objectDefinitionLocalService.getObjectDefinition(objectDefinitionId);
			_log.info("objectDefinition getFilteredObjectEntries: " + objectDefinition.getObjectDefinitionId());
			return getObjectEntriesAndFilter(objectDefinition, odataFilter, sc, cls, null, 0, 0);
		}
		catch (PortalException ex) {
			_log.error("Error while filtering object entries for ERC: " + objectDefinitionId + "not found", ex);
			return Collections.emptyList();
		}

	}

	public <D> List<D> getFilteredObjectEntries(
			long objectDefinitionId, String odataFilter, ServiceContext sc, Class<D> cls, Sort[] sorts, int start, int end) {

		try {
			ObjectDefinition objectDefinition = _objectDefinitionLocalService.getObjectDefinition(objectDefinitionId);
			return getObjectEntriesAndFilter(objectDefinition, odataFilter, sc, cls, sorts, start, end);
		}
		catch (PortalException ex) {
			_log.error("Error while filtering object entries for ERC: " + objectDefinitionId + "not found", ex);
			return Collections.emptyList();
		}
	}

	public <D> List<D> getFilteredObjectEntries(
			String objectDefinitionERC, String odataFilter, ServiceContext sc, Class<D> cls)  {

		try {
			ObjectDefinition objectDefinition = _objectDefinitionLocalService.getObjectDefinitionByExternalReferenceCode(objectDefinitionERC, sc.getCompanyId());
			return getObjectEntriesAndFilter(objectDefinition, odataFilter, sc, cls, null, 0, 0);
		}
		catch (PortalException ex) {
			_log.error("Error while filtering object entries for ERC: " + objectDefinitionERC + "not found", ex);
			return Collections.emptyList();
		}
	}

	public <D> List<D> getFilteredObjectEntries(
			String objectDefinitionERC, String odataFilter, ServiceContext sc, Class<D> cls,  Sort[] sorts, int start, int end)  {
		try {
			ObjectDefinition objectDefinition = _objectDefinitionLocalService.getObjectDefinitionByExternalReferenceCode(objectDefinitionERC, sc.getCompanyId());
			return getObjectEntriesAndFilter(objectDefinition, odataFilter, sc, cls, sorts, start, end);
		}
		catch (PortalException ex) {
			_log.error("Error while filtering object entries for ERC: " + objectDefinitionERC + "not found", ex);
			return Collections.emptyList();
		}
	}

	private <D> List<D> getObjectEntriesAndFilter(
			ObjectDefinition objectDefinition, String odataFilter, ServiceContext sc, Class<D> cls, Sort[] sorts, int start, int end) {

		try {
			// 1. Trouver la définition de l'objet via son ERC
			//ObjectDefinition objectDefinition = _objectDefinitionLocalService.getObjectDefinition(objectDefinitionId);

			if (objectDefinition == null) {
				_log.info("Object Definition with ERC not found.");
				return Collections.emptyList();
			}

			// 2. Convertir la chaîne de filtre OData en un objet Predicate interne
			// C'est ici que l'erreur 'Incompatible types' peut survenir
			Predicate predicate = filterFactory.create(odataFilter, objectDefinition);

			//_log.info("SIZE : " + start);
			//_log.info("END : " + end);
			// 3. Exécuter la requête via objectEntryLocalService
			// getValuesList est utilisé pour récupérer les données sous forme de Map
			_log.info(String.format("===========> sc.getScopeGroupId() : %s - sc.getCompanyId() : %s - sc.getUserId() : %s", sc.getScopeGroupId(), sc.getCompanyId(), sc.getUserId()));
			List<Map<String, Serializable>> results = _objectEntryLocalService.getValuesList(
					sc.getScopeGroupId(),
					sc.getCompanyId(),
					sc.getUserId(),// PortalUtil.getDefaultUserId(companyId), // Utilisateur courant ou par défaut
					objectDefinition.getObjectDefinitionId(),
					predicate, // Le filtre appliqué
					null, // searchKeyword (pas de recherche plein texte)
					start == QueryUtil.ALL_POS ? QueryUtil.ALL_POS : start, // begin (début de pagination, 0 = premier enregistrement)
					end == QueryUtil.ALL_POS ? QueryUtil.ALL_POS : end, // end (fin de pagination, ALL_POS=-1 pour tout récupérer)
					null // sorts (pas de tri)
			);

			_log.info(String.format("===========> results : %s", results));

			// --- Début de l'enrichissement pour l'ERC ---
			List<Map<String, Serializable>> enrichedResults = new ArrayList<>();
			String idKey = objectDefinition.getName() + "Id";
			if (idKey != null && idKey.length() >= 3) {
				// On passe les deux premiers caractères en minuscule ("C_" -> "c_")
				// ET la première lettre du nom de l'objet (ex: "S" -> "s")
				idKey = idKey.substring(0, 3).toLowerCase() + idKey.substring(3);
			}

			_log.info(String.format("===========> idKey: %s", idKey));
			for (Map<String, Serializable> map : results) {
				Map<String, Serializable> enrichedMap = new HashMap<>(map);

				if (map.containsKey(idKey)) {
					long objectEntryId = (long) map.get(idKey);
					try {
						ObjectEntry entry = _objectEntryLocalService.getObjectEntry(objectEntryId);
						// On injecte l'ERC pour le DTO
						enrichedMap.put("id", entry.getObjectEntryId());
						enrichedMap.put("externalReferenceCode", entry.getExternalReferenceCode());
						enrichedMap.put("createdAt", entry.getCreateDate());
					} catch (PortalException e) {
						_log.error("Impossible de trouver l'ObjectEntry pour l'ID: " + objectEntryId);
					}
				}
				enrichedResults.add(enrichedMap);
			}

			//_log.error(String.format("===========> getFilteredObjectEntries idKey %s", idKey));
			//_log.error(String.format("===========> getFilteredObjectEntries %s: - %s", cls, enrichedResults));
			//_log.error(String.format("===========> DTOCONVERTER %s: - %s", cls, DTOConverter.convertObject(enrichedResults, cls)));
			return DTOConverter.convertObject(enrichedResults, cls);

		} catch (PortalException e) {
			_log.error("Error while filtering object entries for ERC:  with filter: " + odataFilter, e);
			// Gérer spécifiquement InvalidFilterException si nécessaire
			return Collections.emptyList();
		}
	}


	/**
	 * Construit un JSON générique à partir d'une liste d'ObjectEntry.
	 * Si des {@code fields} sont précisés, seuls ces champs sont inclus.
	 *
	 * <p>Les champs {@code nestedFields} et {@code nestedFieldsDepth} sont
	 * transmis ici en tant que méta-informations dans la réponse ; leur
	 * résolution profonde nécessite un appel complémentaire aux services
	 * de relation Liferay (à implémenter selon les besoins du projet).</p>
	 */
	public JSONArray entriesToJson(
			List<ObjectEntry> entries,
			String fields,
			String nestedFields) {
		_log.info(">>>> entriesToJson <<<<");
		JSONArray arr = JSONFactoryUtil.createJSONArray();

		// Parse les champs à inclure
		Set<String> fieldSet = new HashSet<>();
		if (fields != null && !fields.isBlank()) {
			for (String f : fields.split(",")) {
				fieldSet.add(f.trim());
			}
		}

		// Parse les nestedFields (champs de relation)
		List<String> nestedFieldList = new ArrayList<>();
		if (nestedFields != null && !nestedFields.isBlank()) {
			for (String nf : nestedFields.split(",")) {
				nestedFieldList.add(nf.trim());
			}
		}

		for (ObjectEntry entry : entries) {
			JSONObject obj = JSONFactoryUtil.createJSONObject();
			obj.put("id", entry.getObjectEntryId());
			obj.put("externalReferenceCode", entry.getExternalReferenceCode());
			obj.put("dateCreated", entry.getCreateDate());
			obj.put("dateModified", entry.getModifiedDate());

			// Ajoute les valeurs normales
			Map<String, Serializable> values = entry.getValues();
			if (values != null) {
				for (Map.Entry<String, Serializable> kv : values.entrySet()) {
					if (fieldSet.isEmpty() || fieldSet.contains(kv.getKey())) {
						obj.put(kv.getKey(), kv.getValue() != null ? kv.getValue().toString() : null);
					}
				}
			}

			// Traite les nestedFields (relations)
			for (String nestedFieldName : nestedFieldList) {
				_log.info("NestedFields readen : "+nestedFieldName);
				// Récupère l'ID de l'entité liée via le champ de relation
				long relatedEntryId = getLong(entry, nestedFieldName);

				if (relatedEntryId != 0) {
					// Récupère l'ObjectEntry liée
					_log.info("nestedfield entry not null..");
					ObjectEntry relatedEntry = null;
					try {
						_log.info(">> Getting entry by ID");
						 relatedEntry = _objectEntryLocalService.fetchObjectEntry(relatedEntryId);
					}
					catch (Exception e) {
						_log.info(
								"[ObjectEntryHelper] getEntry : objectEntryId=" +
										relatedEntryId + " introuvable — " + e.getMessage());
						return null;
					}
					//ObjectEntry relatedEntry = getEntry(relatedEntryId);

					if (relatedEntry != null) {
						// Crée un JSONObject pour l'entité liée
						_log.info("NestedField entry get ID : "+relatedEntry.getObjectEntryId());
						JSONObject relatedObj = JSONFactoryUtil.createJSONObject();
						relatedObj.put("id", relatedEntry.getObjectEntryId());
						relatedObj.put("externalReferenceCode", relatedEntry.getExternalReferenceCode());

						// Ajoute toutes les valeurs de l'entité liée
						Map<String, Serializable> relatedValues = relatedEntry.getValues();
						if (relatedValues != null) {
							for (Map.Entry<String, Serializable> kv : relatedValues.entrySet()) {
								relatedObj.put(kv.getKey(), kv.getValue() != null ? kv.getValue().toString() : null);
							}
						}

						// Utilise le nom du champ de relation comme clé
						obj.put(nestedFieldName, relatedObj);
					} else
						_log.info(">> NestedFields entry can not be get!!!");
				}
			}

			arr.put(obj);
		}
		return arr;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;


	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	// Injecte la fabrique de filtre spécifique pour le stockage par défaut (SQL)
	@Reference(target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")")
	private FilterFactory<Predicate> filterFactory;

	@Reference
	UserLocalService userLocalService;
	/**
	 * Convertit un paramètre "sort" de type "field:asc" ou "field:desc"
	 * en tableau {@link Sort} Liferay.
	 *
	 * @param sortParam ex : {@code "dateCreated:desc"} ou {@code "nom:asc"}
	 * @return tableau de Sort (vide si null/blank)
	 */
	public Sort[] parseSorts(String sortParam) {
		if (sortParam == null || sortParam.isBlank()) {
			return new Sort[]{};
		}
		String[] parts = sortParam.split(":");
		String field   = parts[0].trim();
		boolean desc   = parts.length > 1 && parts[1].trim().equalsIgnoreCase("desc");
		return new Sort[]{ new Sort(field, desc) };
	}
}
