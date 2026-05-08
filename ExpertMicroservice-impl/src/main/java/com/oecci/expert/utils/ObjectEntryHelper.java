package com.oecci.expert.utils;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
			return _objectEntryLocalService.fetchObjectEntry(objectEntryId);
		}
		catch (Exception e) {
			_log.warn(
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
	// CRUD — Recherche par filtre OData
	// -------------------------------------------------------------------------

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
	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString)
		throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);

		_log.info(
			"[ObjectEntryHelper] searchByFilter : erc=" + erc +
				" filter='" + filterString + "'");

		List<ObjectEntry> allEntries =
			_objectEntryLocalService.getObjectEntries(
				groupId, odId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return _applyODataFilter(allEntries, filterString);
	}

	/**
	 * Variante paginée de searchByFilter.
	 */
	public List<ObjectEntry> searchByFilter(
			long userId, long companyId, long groupId,
			String erc, String filterString, int start, int end)
		throws Exception {

		long odId = resolveObjectDefinitionId(companyId, erc);

		List<ObjectEntry> allEntries =
			_objectEntryLocalService.getObjectEntries(
				groupId, odId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		List<ObjectEntry> filtered = _applyODataFilter(allEntries, filterString);

		if (start == QueryUtil.ALL_POS || end == QueryUtil.ALL_POS) {
			return filtered;
		}

		int size = filtered.size();

		return filtered.subList(Math.min(start, size), Math.min(end, size));
	}

	// -------------------------------------------------------------------------
	// Filtrage OData-lite en mémoire
	// Supporte : eq, ne, and (parenthèses incluses)
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

		_log.warn(
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

	private ServiceContext _buildServiceContext(
		long userId, long groupId, long companyId) {

		ServiceContext sc = new ServiceContext();
		sc.setUserId(userId);
		sc.setScopeGroupId(groupId);
		sc.setCompanyId(companyId);
		return sc;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}
