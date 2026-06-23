/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.internal.resource.v1_0;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.oecci.expert.dto.v1_0.CreateDmdExtQuotVisaRequest;
import com.oecci.expert.dto.v1_0.StatutRequest;
import com.oecci.expert.resource.v1_0.VisaResource;

import com.oecci.expert.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lenovo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/visa.properties",
	scope = ServiceScope.PROTOTYPE, service = VisaResource.class
)
public class VisaResourceImpl extends BaseVisaResourceImpl {

	private static final Log _log = LogFactoryUtil.getLog(
			VisaResourceImpl.class);

	// -------------------------------------------------------------------------
	// ERC constants
	// -------------------------------------------------------------------------

	private static final String ERC_DEMANDE_VISA              = Constants.ERC_DEMANDE_VISA;
	private static final String ERC_QUOTAT_VISA_CONFIGURATION = Constants.ERC_QUOTAT_VISA_CONFIGURATION;
	private static final String ERC_EXPERT_VISA_COUNT         = Constants.ERC_EXPERT_VISA_COUNT;
	private static final String ERC_DEMANDE_EXTENSION_QUOTA_VISA         = Constants.ERC_DEMANDE_EXTENSION_QUOTA_VISA;
	private static final String ERC_COLLABORATEUR       = Constants.ERC_COLLABORATEUR;


	@Override
	public Response createDemandeExtensionQuotaVisa(
			CreateDmdExtQuotVisaRequest demandeExtensionQuotaVisaRequest)
			throws Exception {

		_log.info(">> Début createDemandeExtensionQuotaVisa");

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = 0;
		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://" +
				PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// ------------------------------------------------------------------
		// Compte technique
		// ------------------------------------------------------------------
		User technicalUser;
		try {
			technicalUser = _userHelper.getTechnicalUser(companyId);
			_log.info("[ UserAdmin ] >>>>: " + technicalUser.getFirstName());
		}
		catch (Exception e) {
			_log.error("[createDemandeExtensionQuotaVisa] Compte technique introuvable : " +
					e.getMessage(), e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "Compte technique manquant. Contacter l'administrateur.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		long techUserId = technicalUser.getUserId();

		// ------------------------------------------------------------------
		// 1. Vérifier l'existence de l'expert comptable demandeur
		// ------------------------------------------------------------------
		ObjectEntry expertEntry;
		try {
			expertEntry = _objectEntryHelper.getEntryOrThrow(
					demandeExtensionQuotaVisaRequest.getExpertID());
		}
		catch (Exception e) {
			_log.warn("[createDemandeExtensionQuotaVisa] Expert introuvable : id=" +
					demandeExtensionQuotaVisaRequest.getExpertID());
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Expert comptable ID " +
					demandeExtensionQuotaVisaRequest.getExpertID() + " n'existe pas.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String expertNom     = ObjectEntryHelper.getString(expertEntry, "nom");
		String expertPrenoms = ObjectEntryHelper.getString(expertEntry, "prenoms");
		String expertEmail   = ObjectEntryHelper.getString(expertEntry, "email");
		String expertNomComplet = expertPrenoms + " " + expertNom;

		_log.info("[createDemandeExtensionQuotaVisa] Expert demandeur : " + expertNomComplet);

		// ------------------------------------------------------------------
		// 2. Récupérer TOUS les membres de l'ordre (sans filtre de profil)
		//    La demande leur est transmise à tous. Seuls les admins pourront
		//    valider, mais tous sont notifiés.
		// ------------------------------------------------------------------
		List<ObjectEntry> tousMembreOrdre = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_COLLABORATEUR, null);

		if (tousMembreOrdre.isEmpty()) {
			_log.warn("[createDemandeExtensionQuotaVisa] Aucun membre de l'ordre trouvé.");
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucun membre de l'ordre trouvé. " +
					"Impossible de soumettre la demande d'extension.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info("[createDemandeExtensionQuotaVisa] " + tousMembreOrdre.size() +
				" membre(s) de l'ordre trouvé(s).");


		// ------------------------------------------------------------------
		// 3. Récupérer la configuration de quota visa globale
		// ------------------------------------------------------------------
		List<ObjectEntry> quotaList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_QUOTAT_VISA_CONFIGURATION, null);

		if (quotaList.isEmpty()) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucune configuration de quota de visa trouvée.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		int minLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "minlimitevisa");
		int maxLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "maxlimitevisa");

		_log.info("[createDemandeExtensionQuotaVisa] Config quota — min=" + minLimitVisa +
				" max=" + maxLimitVisa);

		// ------------------------------------------------------------------
		// 4. Récupérer le compteur de visas de l'expert
		// ------------------------------------------------------------------
		List<ObjectEntry> visaCountList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_EXPERT_VISA_COUNT,
				ObjectEntryHelper.buildEqFilter(
						"r_expertVisaCount_c_expertComptableId",
						String.valueOf(expertEntry.getObjectEntryId())));

		if (visaCountList.isEmpty()) {
			_log.warn("[createDemandeExtensionQuotaVisa] Aucun compteur visa trouvé pour expert id=" +
					expertEntry.getObjectEntryId());
			result.put("code",    Constants.HTTP_NOT_UPDATED);
			result.put("message", "Echec de la demande d'extension de quota visa. " +
					"Aucune configuration de comptage trouvée pour l'expert comptable " +
					expertNomComplet + ".");
			JSONObject countData = JSONFactoryUtil.createJSONObject();
			countData.put("current_visa_count", 0);
			result.put("data", countData);
			return Response.status(Response.Status.OK).entity(result).build();
		}

		ObjectEntry countEntry      = visaCountList.get(0);
		boolean isOnMinConfig       = ObjectEntryHelper.getBoolean(countEntry, "isOnMinConfig");
		int     currentVisaCount    = (int) ObjectEntryHelper.getLong(countEntry, "visaCount");

		// ------------------------------------------------------------------
		// 5. Vérifier si le quota est déjà étendu
		//    canBeCounted = l'expert est encore dans la plage min → il peut
		//    demander l'extension. S'il est déjà passé en maxConfig, son quota
		//    a déjà été étendu → on bloque.
		// ------------------------------------------------------------------
		boolean canRequestExtension = isOnMinConfig;

		if (!canRequestExtension) {
			_log.warn("[createDemandeExtensionQuotaVisa] Quota déjà étendu pour expert id=" +
					expertEntry.getObjectEntryId());
			result.put("code",    Constants.HTTP_NOT_UPDATED);
			result.put("message", "Echec de la demande d'extension de quota visa. " +
					"Le quota visa signé a déjà été étendu pour l'expert comptable " +
					expertNomComplet + ".");
			JSONObject countData = JSONFactoryUtil.createJSONObject();
			countData.put("current_visa_count", currentVisaCount);
			result.put("data", countData);
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info("[createDemandeExtensionQuotaVisa] Expert éligible à l'extension — " +
				"visaCount=" + currentVisaCount + " minLimit=" + minLimitVisa);

		// ------------------------------------------------------------------
		// 6. Chercher une demande d'extension existante (statut eNCOURS)
		//    pour éviter les doublons
		// ------------------------------------------------------------------
		String extensionFilter = ObjectEntryHelper.buildAndFilter(
				ObjectEntryHelper.buildEqFilter(
						"r_expertDemandeur_c_expertComptableId",
						String.valueOf(expertEntry.getObjectEntryId())),
				ObjectEntryHelper.buildEqFilter(
						"extensionQuotatStatus", "eNCOURS"));

		List<ObjectEntry> existingExtensions = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId, ERC_DEMANDE_EXTENSION_QUOTA_VISA, extensionFilter);

		// ------------------------------------------------------------------
		// 7. Créer ou mettre à jour la demande d'extension
		// ------------------------------------------------------------------
		String reference = "DMD-EXT-QUOTA-" + System.currentTimeMillis() +
				"-" + UserHelper.generateSecureCode();

		Map<String, Serializable> extensionValues = new HashMap<>();
		extensionValues.put("extensionQuotatStatus", "eNCOURS");
		extensionValues.put(
				"r_expertDemandeur_c_expertComptableId",
				expertEntry.getObjectEntryId());
		extensionValues.put(
				"motif",
				demandeExtensionQuotaVisaRequest.getMotif() != null ?
						demandeExtensionQuotaVisaRequest.getMotif() : "");

		ObjectEntry savedExtension;
		boolean isNewExtension = existingExtensions.isEmpty();

		if (isNewExtension) {
			_log.info("[createDemandeExtensionQuotaVisa] Création de la demande d'extension...");
			extensionValues.put("code", reference);
			savedExtension = _objectEntryHelper.addEntry(
					userId, groupId, companyId,
					ERC_DEMANDE_EXTENSION_QUOTA_VISA, extensionValues);
		}
		else {
			long existingId = existingExtensions.get(0).getObjectEntryId();
			_log.info("[createDemandeExtensionQuotaVisa] MAJ demande d'extension existante id=" +
					existingId);
			savedExtension = _objectEntryHelper.updateEntry(
					userId, groupId, companyId, existingId, extensionValues);
		}

		if (savedExtension == null) {
			String msg = (isNewExtension ? "La création" : "La mise à jour") +
					" de la demande d'extension de quota visa pour l'expert " +
					expertNomComplet +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste.";
			_log.error("[createDemandeExtensionQuotaVisa] " + msg);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", msg);
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String savedCode  = ObjectEntryHelper.getString(savedExtension, "code");
		String savedMotif = ObjectEntryHelper.getString(savedExtension, "motif");
		LocalDate dateCreated = savedExtension.getCreateDate()
				.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		String dateCreatedFormatted = dateCreated.format (DateTimeFormatter.ofPattern("dd MM yyyy"));

		_log.info("[createDemandeExtensionQuotaVisa] Demande persistée — code=" + savedCode);

		// ------------------------------------------------------------------
		// 8. Notification à TOUS les membres de l'ordre
		//    Chacun reçoit le même email l'informant qu'une demande est en
		//    attente de traitement. Seuls les admins pourront la valider,
		//    mais tous sont tenus informés.
		// ------------------------------------------------------------------
		String templatePath =
				"/templates/email/notification_expert_dmd_ext_visa_quotat.ftl";
		String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
				.replace("[baseUrl]", baseURL);

		for (ObjectEntry membre : tousMembreOrdre) {
			try {
				String membreEmail   = ObjectEntryHelper.getString(membre, "email");
				String membreNom     = ObjectEntryHelper.getString(membre, "nom");
				String membrePrenoms = ObjectEntryHelper.getString(membre, "prenoms");
				String membreNomComplet = membrePrenoms + " " + membreNom;

				if (membreEmail == null || membreEmail.isBlank()) {
					_log.warn("[createDemandeExtensionQuotaVisa] Membre id=" +
							membre.getObjectEntryId() + " sans email — ignoré.");
					continue;
				}

				Map<String, Object> tplVars = new HashMap<>();
				tplVars.put("nom_admin_ordre",    membreNomComplet);
				tplVars.put("nom_expert",         expertNomComplet);
				tplVars.put("email_expert",       expertEmail);
				tplVars.put("reference_demande",  savedCode);
				tplVars.put("motif",              savedMotif != null ? savedMotif : "");
				tplVars.put("date_demande",       dateCreatedFormatted);
				tplVars.put("current_visa_count", String.valueOf(currentVisaCount));
				tplVars.put("min_limit_visa",     String.valueOf(minLimitVisa));
				tplVars.put("max_limit_visa",     String.valueOf(maxLimitVisa));
				tplVars.put("lien_plateforme",    baseURL + "/web/oecci");

				String mailContent = Utils.processMailTemplate(
						templatePath, this.getClass(), tplVars);

				JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
				notifPayload.put("email",   membreEmail);
				notifPayload.put("content", mailContent);
				notifPayload.put("subject",
						"OECCI : Nouvelle demande d'extension de quota visa");

				Utils.executeHttpRequest(baseURL, notifLink, notifPayload,
						Constants.POST_REQUEST);

				_log.info("[createDemandeExtensionQuotaVisa] Notification envoyée à " +
						membreEmail);
			}
			catch (Exception e) {
				_log.warn("[createDemandeExtensionQuotaVisa] Erreur notification membre id=" +
						membre.getObjectEntryId() + " : " + e.getMessage(), e);
			}
		}
		// ------------------------------------------------------------------
		// 9. Réponse succès
		// ------------------------------------------------------------------
		JSONObject dataObj = JSONFactoryUtil.createJSONObject();
		dataObj.put("id",                    savedExtension.getObjectEntryId());
		dataObj.put("code",                  savedCode);
		dataObj.put("extensionQuotatStatus", "eNCOURS");
		dataObj.put("expertId",              expertEntry.getObjectEntryId());
		dataObj.put("expertNom",             expertNomComplet);
		dataObj.put("current_visa_count",    currentVisaCount);
		dataObj.put("min_limit_visa",        minLimitVisa);
		dataObj.put("max_limit_visa",        maxLimitVisa);
		dataObj.put("membres_notifies",      tousMembreOrdre.size());


		result.put("code",    Constants.HTTP_SUCCESS);
		result.put("message", "La demande d'extension de quota visa de l'expert " +
				expertNomComplet + " a été soumise avec succès. " +
				"L'administrateur de l'ordre a été notifié.");
		result.put("data", dataObj);

		_log.info("[createDemandeExtensionQuotaVisa] Traitement terminé.");
		return Response.status(Response.Status.OK).entity(result).build();
	}


	@Override
	public Response getDemandesExtensionQuotaVisa(
			long   ordreExpertId,
			String statut,
			int page,
			int pageSize)
			throws Exception {

		_log.info(">> Début getDemandesExtensionQuotaVisa — ordreExpertId=" + ordreExpertId +
				" statut=" + statut + " page=" + page + " pageSize=" + pageSize);

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = 0;

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// ------------------------------------------------------------------
		// Validation des paramètres
		// ------------------------------------------------------------------
		if (ordreExpertId <= 0) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Paramètre ordreExpertId manquant ou invalide.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		if (page < 1)     page     = 1;
		if (pageSize < 1) pageSize = 20;
		if (pageSize > 100) pageSize = 100;

		// ------------------------------------------------------------------
		// Compte technique
		// ------------------------------------------------------------------
		User technicalUser;
		try {
			technicalUser = _userHelper.getTechnicalUser(companyId);
		}
		catch (Exception e) {
			_log.error("[getDemandesExtensionQuotaVisa] Compte technique introuvable : " +
					e.getMessage(), e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "Compte technique manquant. Contacter l'administrateur.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		long techUserId = technicalUser.getUserId();

		// ------------------------------------------------------------------
		// 1. Vérifier l'existence de l'administrateur de l'ordre
		// ------------------------------------------------------------------
		ObjectEntry ordreEntry;
		try {
			ordreEntry = _objectEntryHelper.getEntryOrThrow(ordreExpertId);
		}
		catch (Exception e) {
			_log.warn("[getDemandesExtensionQuotaVisa] Admin ordre introuvable : id=" +
					ordreExpertId);
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Administrateur de l'ordre introuvable pour l'id : " +
					ordreExpertId + ".");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String ordreNom     = ObjectEntryHelper.getString(ordreEntry, "nom");
		String ordrePrenoms = ObjectEntryHelper.getString(ordreEntry, "prenoms");
		_log.info("[getDemandesExtensionQuotaVisa] Admin ordre : " + ordrePrenoms + " " + ordreNom);

		// ------------------------------------------------------------------
		// 2. Récupérer la configuration de quota visa globale
		//    (nécessaire pour enrichir chaque ligne avec les plafonds)
		// ------------------------------------------------------------------
		int minLimitVisa = 0;
		int maxLimitVisa = 0;

		List<ObjectEntry> quotaList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_QUOTAT_VISA_CONFIGURATION, null);

		if (!quotaList.isEmpty()) {
			minLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "minlimitevisa");
			maxLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "maxlimitevisa");
		}

		_log.info("[getDemandesExtensionQuotaVisa] Config quota — min=" + minLimitVisa +
				" max=" + maxLimitVisa);

		// ------------------------------------------------------------------
		// 3. Construction du filtre de recherche des demandes d'extension
		//
		//    Filtre de base : toutes les demandes adressées à cet admin.
		//    Filtre optionnel : statut si fourni et non vide.
		// ------------------------------------------------------------------
		String baseFilter = ObjectEntryHelper.buildEqFilter(
				"r_iDExpertCoordinateur_c_expertCoordinateurId",
				String.valueOf(ordreExpertId));

		String finalFilter;
		if (statut != null && !statut.isBlank()) {
			finalFilter = ObjectEntryHelper.buildAndFilter(
					baseFilter,
					ObjectEntryHelper.buildEqFilter("extensionQuotatStatus", statut.trim()));
		}
		else {
			finalFilter = baseFilter;
		}

		// ------------------------------------------------------------------
		// 4. Récupérer toutes les demandes correspondant au filtre
		// ------------------------------------------------------------------
		List<ObjectEntry> allDemandes = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId,
				ERC_DEMANDE_EXTENSION_QUOTA_VISA, finalFilter);

		_log.info("[getDemandesExtensionQuotaVisa] " + allDemandes.size() +
				" demande(s) trouvée(s).");

		// ------------------------------------------------------------------
		// 5. Pagination manuelle
		// ------------------------------------------------------------------
		int totalItems = allDemandes.size();
		int totalPages = (totalItems == 0) ? 1 : (int) Math.ceil((double) totalItems / pageSize);
		int fromIndex  = (page - 1) * pageSize;
		int toIndex    = Math.min(fromIndex + pageSize, totalItems);

		List<ObjectEntry> pageDemandes = (fromIndex >= totalItems)
				? new ArrayList<>()
				: allDemandes.subList(fromIndex, toIndex);

		// ------------------------------------------------------------------
		// 6. Enrichissement de chaque demande
		// ------------------------------------------------------------------
		JSONArray items = JSONFactoryUtil.createJSONArray();

		for (ObjectEntry demandeEntry : pageDemandes) {
			try {
				JSONObject item = JSONFactoryUtil.createJSONObject();

				// Champs propres à la demande
				item.put("id",                    demandeEntry.getObjectEntryId());
				item.put("code",                  ObjectEntryHelper.getString(demandeEntry, "code"));
				item.put("extensionQuotatStatus", ObjectEntryHelper.getString(demandeEntry, "extensionQuotatStatus"));
				item.put("motif",                 ObjectEntryHelper.getString(demandeEntry, "motif"));

				// Date de création formatée
				LocalDate dateCreated = demandeEntry.getCreateDate()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				item.put("dateCreated", dateCreated.format(DateTimeFormatter.ofPattern("dd MM yyyy")));

				// Date de dernière modification
				LocalDate dateModified = demandeEntry.getModifiedDate()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				item.put("dateModified", dateModified.format(DateTimeFormatter.ofPattern("dd MM yyyy")));

				// ── Données de l'expert demandeur ──────────────────────────
				long expertDemandeurId = ObjectEntryHelper.getLong(
						demandeEntry, "r_expertDemandeur_c_expertComptableId");

				JSONObject expertData = JSONFactoryUtil.createJSONObject();
				expertData.put("id", expertDemandeurId);

				ObjectEntry expertEntry = _objectEntryHelper.getEntry(expertDemandeurId);
				if (expertEntry != null) {
					String expertNom     = ObjectEntryHelper.getString(expertEntry, "nom");
					String expertPrenoms = ObjectEntryHelper.getString(expertEntry, "prenoms");
					expertData.put("nom",        expertNom);
					expertData.put("prenoms",    expertPrenoms);
					expertData.put("nomComplet", expertPrenoms + " " + expertNom);
					expertData.put("email",      ObjectEntryHelper.getString(expertEntry, "email"));
					expertData.put("contact",    ObjectEntryHelper.getString(expertEntry, "contact"));
				}
				item.put("expertDemandeur", expertData);

				// ── Compteur de visas de l'expert ──────────────────────────
				JSONObject visaCountData = JSONFactoryUtil.createJSONObject();
				visaCountData.put("minLimitVisa", minLimitVisa);
				visaCountData.put("maxLimitVisa", maxLimitVisa);

				List<ObjectEntry> visaCountList = _objectEntryHelper.searchByFilter(
						techUserId, companyId, groupId, ERC_EXPERT_VISA_COUNT,
						ObjectEntryHelper.buildEqFilter(
								"r_expertVisaCount_c_expertComptableId",
								String.valueOf(expertDemandeurId)));

				if (!visaCountList.isEmpty()) {
					ObjectEntry countEntry   = visaCountList.get(0);
					boolean isOnMinConfig    = ObjectEntryHelper.getBoolean(countEntry, "isOnMinConfig");
					int     currentVisaCount = (int) ObjectEntryHelper.getLong(countEntry, "visaCount");
					int     applicableLimit  = isOnMinConfig ? minLimitVisa : maxLimitVisa;
					int     remainingVisas   = Math.max(0, applicableLimit - currentVisaCount);

					visaCountData.put("currentVisaCount", currentVisaCount);
					visaCountData.put("isOnMinConfig",    isOnMinConfig);
					visaCountData.put("applicableLimit",  applicableLimit);
					visaCountData.put("remainingVisas",   remainingVisas);
					visaCountData.put("canSign",          remainingVisas > 0);
				}
				else {
					visaCountData.put("currentVisaCount", 0);
					visaCountData.put("isOnMinConfig",    true);
					visaCountData.put("applicableLimit",  minLimitVisa);
					visaCountData.put("remainingVisas",   minLimitVisa);
					visaCountData.put("canSign",          minLimitVisa > 0);
				}
				item.put("visaCount", visaCountData);

				items.put(item);
			}
			catch (Exception e) {
				_log.warn("[getDemandesExtensionQuotaVisa] Erreur enrichissement demande id=" +
						demandeEntry.getObjectEntryId() + " : " + e.getMessage());
			}
		}

		// ------------------------------------------------------------------
		// 7. Compteurs par statut pour le tableau de bord de l'admin
		// ------------------------------------------------------------------
		JSONObject statusCounts = JSONFactoryUtil.createJSONObject();
		statusCounts.put("total",    totalItems);
		statusCounts.put("eNCOURS",  Utils._countByStatut(allDemandes, "extensionQuotatStatus", "eNCOURS"));
		statusCounts.put("aCCEPTE",  Utils._countByStatut(allDemandes, "extensionQuotatStatus", "aCCEPTE"));
		statusCounts.put("rEFUSE",   Utils. _countByStatut(allDemandes, "extensionQuotatStatus", "rEFUSE"));

		// ------------------------------------------------------------------
		// 8. Construction de la réponse paginée
		// ------------------------------------------------------------------
		JSONObject pagination = JSONFactoryUtil.createJSONObject();
		pagination.put("page",       page);
		pagination.put("pageSize",   pageSize);
		pagination.put("totalItems", totalItems);
		pagination.put("totalPages", totalPages);
		pagination.put("hasNext",    page < totalPages);
		pagination.put("hasPrev",    page > 1);

		JSONObject data = JSONFactoryUtil.createJSONObject();
		data.put("items",        items);
		data.put("pagination",   pagination);
		data.put("statusCounts", statusCounts);

		result.put("code",    Constants.HTTP_SUCCESS);
		result.put("message", items.length() == 0
				? "Aucune demande d'extension de quota visa trouvée."
				: items.length() + " demande(s) trouvée(s).");
		result.put("data", data);

		_log.info("[getDemandesExtensionQuotaVisa] Réponse construite — " +
				items.length() + " item(s) retourné(s).");
		return Response.status(Response.Status.OK).entity(result).build();
	}


	@Override
	public Response getDemandesExtensionQuotaVisaByExpert(
			long   expertId,
			String statut,
			int    page,
			int    pageSize)
			throws Exception {

		_log.info(">> Debut getDemandesExtensionQuotaVisaByExpert — expertId=" + expertId
				+ " statut=" + statut + " page=" + page + " pageSize=" + pageSize);

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = 0;

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// ------------------------------------------------------------------
		// Validation des parametres
		// ------------------------------------------------------------------
		if (expertId <= 0) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Parametre expertId manquant ou invalide.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		if (page     < 1)   page     = 1;
		if (pageSize < 1)   pageSize = 20;
		if (pageSize > 100) pageSize = 100;

		// ------------------------------------------------------------------
		// Compte technique
		// ------------------------------------------------------------------
		User technicalUser;
		try {
			technicalUser = _userHelper.getTechnicalUser(companyId);
		} catch (Exception e) {
			_log.error("[getDemandesExtensionQuotaVisaByExpert] Compte technique introuvable : "
					+ e.getMessage(), e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "Compte technique manquant. Contacter l administrateur.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}
		long techUserId = technicalUser.getUserId();

		// ------------------------------------------------------------------
		// 1. Verifier l existence de l expert
		// ------------------------------------------------------------------
		ObjectEntry expertEntry;
		try {
			expertEntry = _objectEntryHelper.getEntryOrThrow(expertId);
		} catch (Exception e) {
			_log.warn("[getDemandesExtensionQuotaVisaByExpert] Expert introuvable id=" + expertId);
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Expert comptable introuvable pour l id : " + expertId + ".");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String expertNom      = ObjectEntryHelper.getString(expertEntry, "nom");
		String expertPrenoms  = ObjectEntryHelper.getString(expertEntry, "prenoms");
		String expertEmail    = ObjectEntryHelper.getString(expertEntry, "email");
		String expertContact  = ObjectEntryHelper.getString(expertEntry, "contact");
		String expertNomComplet = expertPrenoms + " " + expertNom;

		_log.info("[getDemandesExtensionQuotaVisaByExpert] Expert : " + expertNomComplet);

		// ------------------------------------------------------------------
		// 2. Configuration quota visa globale
		// ------------------------------------------------------------------
		int minLimitVisa = 0;
		int maxLimitVisa = 0;

		List<ObjectEntry> quotaList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_QUOTAT_VISA_CONFIGURATION, null);

		if (!quotaList.isEmpty()) {
			minLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "minlimitevisa");
			maxLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "maxlimitevisa");
		}

		_log.info("[getDemandesExtensionQuotaVisaByExpert] Config quota — min=" + minLimitVisa
				+ " max=" + maxLimitVisa);

		// ------------------------------------------------------------------
		// 3. Compteur de visas de l expert
		// ------------------------------------------------------------------
		int     currentVisaCount = 0;
		boolean isOnMinConfig    = true;
		int     applicableLimit  = minLimitVisa;
		int     remainingVisas   = minLimitVisa;
		boolean canSign          = minLimitVisa > 0;

		List<ObjectEntry> visaCountList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_EXPERT_VISA_COUNT,
				ObjectEntryHelper.buildEqFilter(
						"r_expertVisaCount_c_expertComptableId",
						String.valueOf(expertId)));

		if (!visaCountList.isEmpty()) {
			ObjectEntry countEntry = visaCountList.get(0);
			isOnMinConfig    = ObjectEntryHelper.getBoolean(countEntry, "isOnMinConfig");
			currentVisaCount = (int) ObjectEntryHelper.getLong(countEntry, "visaCount");
			applicableLimit  = isOnMinConfig ? minLimitVisa : maxLimitVisa;
			remainingVisas   = Math.max(0, applicableLimit - currentVisaCount);
			canSign          = remainingVisas > 0;
		}

		// ------------------------------------------------------------------
		// 4. Construction du filtre des demandes de l expert
		// ------------------------------------------------------------------
		String baseFilter = ObjectEntryHelper.buildEqFilter(
				"r_expertDemandeur_c_expertComptableId",
				String.valueOf(expertId));

		String finalFilter;
		if (statut != null && !statut.isBlank()) {
			finalFilter = ObjectEntryHelper.buildAndFilter(
					baseFilter,
					ObjectEntryHelper.buildEqFilter("extensionQuotatStatus", statut.trim()));
		} else {
			finalFilter = baseFilter;
		}

		// ------------------------------------------------------------------
		// 5. Recuperer toutes les demandes de l expert
		// ------------------------------------------------------------------
		List<ObjectEntry> allDemandes = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId,
				ERC_DEMANDE_EXTENSION_QUOTA_VISA, finalFilter);

		_log.info("[getDemandesExtensionQuotaVisaByExpert] " + allDemandes.size()
				+ " demande(s) trouvee(s).");

		// ------------------------------------------------------------------
		// 6. Pagination manuelle
		// ------------------------------------------------------------------
		int totalItems = allDemandes.size();
		int totalPages = (totalItems == 0) ? 1 : (int) Math.ceil((double) totalItems / pageSize);
		int fromIndex  = (page - 1) * pageSize;
		int toIndex    = Math.min(fromIndex + pageSize, totalItems);

		List<ObjectEntry> pageDemandes = (fromIndex >= totalItems)
				? new ArrayList<>()
				: allDemandes.subList(fromIndex, toIndex);

		// ------------------------------------------------------------------
		// 7. Serialisation des demandes
		// ------------------------------------------------------------------
		JSONArray items = JSONFactoryUtil.createJSONArray();

		for (ObjectEntry demandeEntry : pageDemandes) {
			try {
				JSONObject item = JSONFactoryUtil.createJSONObject();

				item.put("id",                    demandeEntry.getObjectEntryId());
				item.put("code",                  ObjectEntryHelper.getString(demandeEntry, "code"));
				item.put("extensionQuotatStatus", ObjectEntryHelper.getString(demandeEntry, "extensionQuotatStatus"));
				item.put("motif",                 ObjectEntryHelper.getString(demandeEntry, "motif"));

				// Dates formatees
				LocalDate dateCreated = demandeEntry.getCreateDate()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				item.put("dateCreated",
						dateCreated.format(DateTimeFormatter.ofPattern("dd MM yyyy")));

				LocalDate dateModified = demandeEntry.getModifiedDate()
						.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				item.put("dateModified",
						dateModified.format(DateTimeFormatter.ofPattern("dd MM yyyy")));

				// Label lisible du statut
				String statutKey = ObjectEntryHelper.getString(demandeEntry, "extensionQuotatStatus");
				item.put("statutLabel", _extensionStatutToLabel(statutKey));

				items.put(item);
			} catch (Exception e) {
				_log.warn("[getDemandesExtensionQuotaVisaByExpert] Erreur enrichissement demande id="
						+ demandeEntry.getObjectEntryId() + " : " + e.getMessage());
			}
		}

		// ------------------------------------------------------------------
		// 8. Compteurs par statut (pour les badges de l interface expert)
		// ------------------------------------------------------------------
		JSONObject statusCounts = JSONFactoryUtil.createJSONObject();
		statusCounts.put("total",   totalItems);
		statusCounts.put("eNCOURS", Utils._countByStatut(allDemandes, "extensionQuotatStatus", "eNCOURS"));
		statusCounts.put("aCCEPTE", Utils._countByStatut(allDemandes, "extensionQuotatStatus", "aCCEPTE"));
		statusCounts.put("rEFUSE",  Utils._countByStatut(allDemandes, "extensionQuotatStatus", "rEFUSE"));

		// ------------------------------------------------------------------
		// 9. Resume de l expert et de son quota (pour l en-tete de la vue)
		// ------------------------------------------------------------------
		JSONObject expertResume = JSONFactoryUtil.createJSONObject();
		expertResume.put("id",              expertId);
		expertResume.put("nomComplet",      expertNomComplet);
		expertResume.put("email",           expertEmail);
		expertResume.put("contact",         expertContact);

		JSONObject visaCountData = JSONFactoryUtil.createJSONObject();
		visaCountData.put("currentVisaCount", currentVisaCount);
		visaCountData.put("isOnMinConfig",    isOnMinConfig);
		visaCountData.put("minLimitVisa",     minLimitVisa);
		visaCountData.put("maxLimitVisa",     maxLimitVisa);
		visaCountData.put("applicableLimit",  applicableLimit);
		visaCountData.put("remainingVisas",   remainingVisas);
		visaCountData.put("canSign",          canSign);
		expertResume.put("visaCount", visaCountData);

		// ------------------------------------------------------------------
		// 10. Pagination
		// ------------------------------------------------------------------
		JSONObject pagination = JSONFactoryUtil.createJSONObject();
		pagination.put("page",       page);
		pagination.put("pageSize",   pageSize);
		pagination.put("totalItems", totalItems);
		pagination.put("totalPages", totalPages);
		pagination.put("hasNext",    page < totalPages);
		pagination.put("hasPrev",    page > 1);

		// ------------------------------------------------------------------
		// 11. Reponse
		// ------------------------------------------------------------------
		JSONObject data = JSONFactoryUtil.createJSONObject();
		data.put("expert",       expertResume);
		data.put("items",        items);
		data.put("pagination",   pagination);
		data.put("statusCounts", statusCounts);

		result.put("code",    Constants.HTTP_SUCCESS);
		result.put("message", items.length() == 0
				? "Aucune demande d extension de quota visa trouvee pour cet expert."
				: items.length() + " demande(s) trouvee(s).");
		result.put("data", data);

		_log.info("[getDemandesExtensionQuotaVisaByExpert] Reponse construite — "
				+ items.length() + " item(s).");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// ---------------------------------------------------------------------------
// Helper prive : conversion cle statut → label lisible
// (a ajouter avec les autres helpers prives de la classe)
// ---------------------------------------------------------------------------
	private String _extensionStatutToLabel(String key) {
		if (key == null) return "";
		switch (key) {
			case "eNCOURS": return "En cours";
			case "aCCEPTE": return "Accepte";
			case "rEFUSE":  return "Refuse";
			default:        return key;
		}
	}

		@Override
		public Response validateDemandeExtQuotaVisa(
				Long           demandeExtId,
				StatutRequest  statutRequest)
        throws Exception {

			_log.info(">> Début validateDemandeExtQuotaVisa — id=" + demandeExtId);

			//long userId    = contextUser.getUserId();
			String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://" +
					PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

			JSONObject result = JSONFactoryUtil.createJSONObject();

			long groupId   = 0;
			long companyId = contextCompany.getCompanyId();
			User user = SecurityUtil.checkUser(_httpServletRequest, "validateDemandeExtQuotaVisa");
			if (user == null) {
				return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
			}
			long userId = user.getUserId();

			_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
			String[] roles = {"Regular COLLABO ADMIN Shared Object"};
			boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);

			if (!hasAccess) {
				result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
				result.put("message", "Vous n'avez les permissions nécessaires.");
				result.put("data", "");
				return Response.status(Response.Status.FORBIDDEN).entity(result).build();
			}
			// ------------------------------------------------------------------
			// Compte technique
			// ------------------------------------------------------------------
			User technicalUser;
			try {
				technicalUser = _userHelper.getTechnicalUser(companyId);
				_log.info("[ UserAdmin ] >>>>: " + technicalUser.getFirstName());
			}
			catch (Exception e) {
				_log.error("[validateDemandeExtQuotaVisa] Compte technique introuvable : " +
						e.getMessage(), e);
				result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("message", "Compte technique manquant. Contacter l'administrateur.");
				result.put("data",    "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			long techUserId = technicalUser.getUserId();

			// ------------------------------------------------------------------
			// 1. Récupérer la demande d'extension
			// ------------------------------------------------------------------
			ObjectEntry extensionEntry;
			try {
				extensionEntry = _objectEntryHelper.getEntryOrThrow(demandeExtId);
			}
			catch (Exception e) {
				_log.warn("[validateDemandeExtQuotaVisa] Demande introuvable : id=" + demandeExtId);
				result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
				result.put("message", "Aucune demande d'extension de quota visa trouvée pour l'id : " +
						demandeExtId + ".");
				result.put("data",    "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			_log.info("[validateDemandeExtQuotaVisa] Demande trouvée — statut actuel=" +
					ObjectEntryHelper.getString(extensionEntry, "extensionQuotatStatus"));

			// ------------------------------------------------------------------
			// 2. Extraire les FKs et charger les entités liées
			// ------------------------------------------------------------------
			long expertDemandeurId = ObjectEntryHelper.getLong(
					extensionEntry, "r_expertDemandeur_c_expertComptableId");

			ObjectEntry expertEntry;
			try {
				expertEntry = _objectEntryHelper.getEntryOrThrow(expertDemandeurId);
			}
			catch (Exception e) {
				_log.error("[validateDemandeExtQuotaVisa] Expert demandeur introuvable : id=" +
						expertDemandeurId);
				result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
				result.put("message", "Expert comptable demandeur introuvable (id=" +
						expertDemandeurId + ").");
				result.put("data",    "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			String expertNom       = ObjectEntryHelper.getString(expertEntry, "nom");
			String expertPrenoms   = ObjectEntryHelper.getString(expertEntry, "prenoms");
			String expertEmail     = ObjectEntryHelper.getString(expertEntry, "email");
			String expertNomComplet = expertPrenoms + " " + expertNom;

			// ------------------------------------------------------------------
			// 3. Résoudre le membre admin traitant (l'utilisateur connecté)
			//    On cherche son entrée dans ERC_EXPERT_COORDINATEUR via son userId
			// ------------------------------------------------------------------
			List<ObjectEntry> adminEntries = _objectEntryHelper.searchByFilter(
					techUserId, companyId, groupId, ERC_COLLABORATEUR,
					ObjectEntryHelper.buildEqFilter(
							"r_iDUserCollabo_userId",
							String.valueOf(userId)));
			if(adminEntries.isEmpty() || adminEntries == null){
				_log.error("[validateDemandeExtQuotaVisa] Administrateur de l'ordre introuvable : id=" +
						expertDemandeurId);
				result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
				result.put("message", "Aucun administrateur de l'ordre connecté. Utilisateur introuvable (id liferay =" +
						userId + ").");
				result.put("data",    "");
				return Response.status(Response.Status.OK).entity(result).build();

			}

			String adminNomComplet;
			ObjectEntry adminEntry = null;
			if (!adminEntries.isEmpty()) {
				adminEntry = adminEntries.get(0);
				adminNomComplet = ObjectEntryHelper.getString(adminEntry, "prenoms") +
						" " + ObjectEntryHelper.getString(adminEntry, "nom");
			}
			else {
				// Fallback : utiliser le nom de l'utilisateur Liferay connecté
				adminNomComplet = contextUser.getFullName();
				_log.warn("[validateDemandeExtQuotaVisa] Membre ordre introuvable pour userId=" +
						userId + " — fallback nom Liferay : " + adminNomComplet);
			}

			String ordreNomComplet = (adminNomComplet != null)
					? adminNomComplet
					: "L'administrateur de l'ordre";

			String extensionCode = ObjectEntryHelper.getString(extensionEntry, "code");
			String motifRefus    = statutRequest.getMotif_refus() != null ?
					statutRequest.getMotif_refus() : "";

			_log.info("[validateDemandeExtQuotaVisa] Expert=" + expertNomComplet +
					" | Admin traitant=" + adminNomComplet +
					" | Nouveau statut=" + statutRequest.getStatut().getKey());


			// ------------------------------------------------------------------
			// 3. Mettre à jour le statut de la demande d'extension
			// ------------------------------------------------------------------
			Map<String, Serializable> updateValues = new HashMap<>();
			updateValues.put("extensionQuotatStatus", statutRequest.getStatut().getKey());
			updateValues.put("motif",                 motifRefus);
			updateValues.put("r_ordreTraiteur_c_collaborateurId", adminEntry.getObjectEntryId());

			ObjectEntry updatedExtension = _objectEntryHelper.updateEntry(
					userId, groupId, companyId,
					extensionEntry.getObjectEntryId(), updateValues);

			if (updatedExtension == null) {
				_log.error("[validateDemandeExtQuotaVisa] Echec mise à jour statut pour id=" +
						demandeExtId);
				result.put("code",    Constants.HTTP_NOT_UPDATED);
				result.put("message", "La mise à jour de la demande d'extension n° " +
						extensionCode + " a échoué. Veuillez réessayer ou contacter " +
						"l'administrateur si cela persiste.");
				result.put("data",    "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			String updatedStatutKey = ObjectEntryHelper.getString(
					updatedExtension, "extensionQuotatStatus");
			_log.info("[validateDemandeExtQuotaVisa] Statut mis à jour : " + updatedStatutKey);

			// ------------------------------------------------------------------
			// 4. Si ACCEPTÉ : passer le compteur visa de l'expert de min à max
			// ------------------------------------------------------------------
			int maxLimitVisa = 0;

			if (statutRequest.getStatut().getName()
					.equalsIgnoreCase(Constants.EXTENSION_QUOTA_ACCEPTE_STATUS)) {

				// Récupérer la config globale pour avoir maxlimitevisa
				List<ObjectEntry> quotaList = _objectEntryHelper.searchByFilter(
						techUserId, companyId, groupId, ERC_QUOTAT_VISA_CONFIGURATION, null);

				if (quotaList.isEmpty()) {
					_log.warn("[validateDemandeExtQuotaVisa] Config quota introuvable — " +
							"impossible de mettre à jour le compteur.");
				}
				else {
					maxLimitVisa = (int) ObjectEntryHelper.getLong(
							quotaList.get(0), "maxlimitevisa");

					// Récupérer le compteur de l'expert
					List<ObjectEntry> visaCountList = _objectEntryHelper.searchByFilter(
							techUserId, companyId, groupId, ERC_EXPERT_VISA_COUNT,
							ObjectEntryHelper.buildEqFilter(
									"r_expertVisaCount_c_expertComptableId",
									String.valueOf(expertDemandeurId)));

					if (visaCountList.isEmpty()) {
						_log.warn("[validateDemandeExtQuotaVisa] Compteur visa introuvable pour " +
								"expert id=" + expertDemandeurId);
					}
					else {
						ObjectEntry visaCountEntry = visaCountList.get(0);

						Map<String, Serializable> countUpdateValues = new HashMap<>();
						// isOnMinConfig passe à false : l'expert est maintenant
						// en configuration max — il peut signer jusqu'à maxlimitevisa.
						countUpdateValues.put("isOnMinConfig", false);

						ObjectEntry updatedCount = _objectEntryHelper.updateEntry(
								userId, groupId, companyId,
								visaCountEntry.getObjectEntryId(), countUpdateValues);

						if (updatedCount == null) {
							_log.warn("[validateDemandeExtQuotaVisa] Echec MAJ compteur visa pour " +
									"expert id=" + expertDemandeurId);
						}
						else {
							_log.info("[validateDemandeExtQuotaVisa] Compteur expert passé en " +
									"maxConfig (isOnMinConfig=false) — maxLimit=" + maxLimitVisa);
						}
					}
				}
			}

			// ------------------------------------------------------------------
			// 5. Notification à l'expert comptable
			// ------------------------------------------------------------------
			try {
				String templatePath =
						"/templates/email/notification_validation_dmd_ext_visa_quotat.ftl";

				boolean isAccepted = statutRequest.getStatut().getName()
						.equalsIgnoreCase(Constants.EXTENSION_QUOTA_ACCEPTE_STATUS);

				Map<String, Object> tplVars = new HashMap<>();
				tplVars.put("nom_expert",        expertNomComplet);
				tplVars.put("nom_admin_ordre",   ordreNomComplet);
				tplVars.put("reference_demande", extensionCode);
				tplVars.put("statut",            statutRequest.getStatut().getName());
				tplVars.put("is_accepted",       isAccepted);
				tplVars.put("max_limit_visa",    String.valueOf(maxLimitVisa));
				tplVars.put("motif_refus",       motifRefus);
				tplVars.put("lien_plateforme",   baseURL + "/web/oecci");

				String mailContent = Utils.processMailTemplate(
						templatePath, this.getClass(), tplVars);

				JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
				notifPayload.put("email",   expertEmail);
				notifPayload.put("content", mailContent);
				notifPayload.put("subject", isAccepted
						? "OECCI : Votre demande d'extension de quota visa a été acceptée"
						: "OECCI : Votre demande d'extension de quota visa a été refusée");

				String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
						.replace("[baseUrl]", baseURL);

				JSONObject notifResult = Utils.executeHttpRequest(
						baseURL, notifLink, notifPayload, Constants.POST_REQUEST);
				if (notifResult != null) {
					_log.info("[validateDemandeExtQuotaVisa] Notification envoyée à " +
							expertEmail + " — code=" + notifResult.getInt("code"));
				}
			}
			catch (Exception e) {
				_log.warn("[validateDemandeExtQuotaVisa] Erreur notification expert : " +
						e.getMessage(), e);
			}

			// ------------------------------------------------------------------
			// 6. Réponse succès
			// ------------------------------------------------------------------
			boolean isAccepted = statutRequest.getStatut().getName()
					.equalsIgnoreCase(Constants.EXTENSION_QUOTA_ACCEPTE_STATUS);

			JSONObject dataObj = JSONFactoryUtil.createJSONObject();
			dataObj.put("id",                    updatedExtension.getObjectEntryId());
			dataObj.put("code",                  extensionCode);
			dataObj.put("extensionQuotatStatus", updatedStatutKey);
			dataObj.put("expertId",              expertDemandeurId);
			dataObj.put("expertNom",             expertNomComplet);
			if (isAccepted) {
				dataObj.put("max_limit_visa", maxLimitVisa);
			}
			if (!motifRefus.isEmpty()) {
				dataObj.put("motif_refus", motifRefus);
			}

			String message = isAccepted
					? "La demande d'extension de quota visa n° " + extensionCode +
					" de l'expert " + expertNomComplet +
					" a été acceptée. Il peut désormais signer jusqu'à " +
					maxLimitVisa + " visas."
					: "La demande d'extension de quota visa n° " + extensionCode +
					" de l'expert " + expertNomComplet + " a été refusée.";

			result.put("code",    Constants.HTTP_SUCCESS);
			result.put("message", message);
			result.put("data",    dataObj);

			_log.info("[validateDemandeExtQuotaVisa] Traitement terminé — " + message);
			return Response.status(Response.Status.OK).entity(result).build();
		}

	@Override
	public Response canSignVisa(Long expertId)
			throws Exception {

		_log.info(">> Début canSignVisa — expertId=" + expertId);

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = 0;

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// ------------------------------------------------------------------
		// Validation du paramètre
		// ------------------------------------------------------------------
		if (expertId == null || expertId <= 0) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Paramètre expertId manquant ou invalide.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		// ------------------------------------------------------------------
		// Compte technique
		// ------------------------------------------------------------------
		User technicalUser;
		try {
			technicalUser = _userHelper.getTechnicalUser(companyId);
		}
		catch (Exception e) {
			_log.error("[canSignVisa] Compte technique introuvable : " + e.getMessage(), e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "Compte technique manquant. Contacter l'administrateur.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		long techUserId = technicalUser.getUserId();

		// ------------------------------------------------------------------
		// 1. Vérifier l'existence de l'expert comptable
		// ------------------------------------------------------------------
		ObjectEntry expertEntry;
		try {
			expertEntry = _objectEntryHelper.getEntryOrThrow(expertId);
		}
		catch (Exception e) {
			_log.warn("[canSignVisa] Expert introuvable : id=" + expertId);
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Expert comptable introuvable pour l'id : " + expertId + ".");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String expertNom     = ObjectEntryHelper.getString(expertEntry, "nom");
		String expertPrenoms = ObjectEntryHelper.getString(expertEntry, "prenoms");
		String expertNomComplet = expertPrenoms + " " + expertNom;

		// ------------------------------------------------------------------
		// 2. Récupérer la configuration de quota visa globale
		// ------------------------------------------------------------------
		List<ObjectEntry> quotaList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_QUOTAT_VISA_CONFIGURATION, null);

		if (quotaList.isEmpty()) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucune configuration de quota de visa trouvée.");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		int minLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "minlimitevisa");
		int maxLimitVisa = (int) ObjectEntryHelper.getLong(quotaList.get(0), "maxlimitevisa");

		// ------------------------------------------------------------------
		// 3. Récupérer le compteur de visas de l'expert
		// ------------------------------------------------------------------
		List<ObjectEntry> visaCountList = _objectEntryHelper.searchByFilter(
				techUserId, companyId, groupId, ERC_EXPERT_VISA_COUNT,
				ObjectEntryHelper.buildEqFilter(
						"r_expertVisaCount_c_expertComptableId",
						String.valueOf(expertId)));

		if (visaCountList.isEmpty()) {
			_log.warn("[canSignVisa] Aucun compteur visa trouvé pour expert id=" + expertId);
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucune configuration de comptage de visa trouvée pour " +
					"l'expert comptable " + expertNomComplet + ".");
			result.put("data",    "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		ObjectEntry countEntry   = visaCountList.get(0);
		boolean isOnMinConfig    = ObjectEntryHelper.getBoolean(countEntry, "isOnMinConfig");
		int     currentVisaCount = (int) ObjectEntryHelper.getLong(countEntry, "visaCount");

		// ------------------------------------------------------------------
		// 4. Calcul de la capacité applicable et du reste disponible
		//
		//    isOnMinConfig = true  → plafond = minlimitevisa (config de base)
		//    isOnMinConfig = false → plafond = maxlimitevisa (quota étendu)
		// ------------------------------------------------------------------
		int applicableLimit  = isOnMinConfig ? minLimitVisa : maxLimitVisa;
		int remainingVisas   = Math.max(0, applicableLimit - currentVisaCount);
		boolean canSign      = remainingVisas > 0;

		_log.info("[canSignVisa] Expert=" + expertNomComplet +
				" | visaCount=" + currentVisaCount +
				" | applicableLimit=" + applicableLimit +
				" | remaining=" + remainingVisas +
				" | canSign=" + canSign);

		// ------------------------------------------------------------------
		// 5. Construction de la réponse
		// ------------------------------------------------------------------
		JSONObject data = JSONFactoryUtil.createJSONObject();
		data.put("expertId",         expertId);
		data.put("expertNom",        expertNomComplet);
		data.put("canSign",          canSign);
		data.put("currentVisaCount", currentVisaCount);
		data.put("applicableLimit",  applicableLimit);
		data.put("remainingVisas",   remainingVisas);
		data.put("isOnMinConfig",    isOnMinConfig);
		data.put("minLimitVisa",     minLimitVisa);
		data.put("maxLimitVisa",     maxLimitVisa);

		String message = canSign
				? "L'expert " + expertNomComplet + " peut encore signer " +
				remainingVisas + " visa(s) sur un maximum de " + applicableLimit + "."
				: "L'expert " + expertNomComplet +
				" a atteint son quota de visas signés (" + currentVisaCount +
				"/" + applicableLimit + "). " +
				(isOnMinConfig
						? "Il peut soumettre une demande d'extension de quota."
						: "Son quota étendu est également atteint.");

		result.put("code",    Constants.HTTP_SUCCESS);
		result.put("message", message);
		result.put("data",    data);

		return Response.status(Response.Status.OK).entity(result).build();
	}


	// -------------------------------------------------------------------------
	// getDemandeVisasByDate
	// -------------------------------------------------------------------------

	@Override
	public Response getDemandeVisasByDate(
			String dateDebut, String dateFin, Integer page, Integer pageSize,
			String sort, String fields, String nestedFields,
			Integer nestedFieldsDepth)
			throws Exception {

		_log.info(">> getDemandeVisasByDate — dateDebut=" + dateDebut +
				" dateFin=" + dateFin);
		long companyId = contextCompany.getCompanyId();
		long groupId = 0L;
		User user = SecurityUtil.checkUser(_httpServletRequest, "getDemandeVisasByDate");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		long userId = user.getUserId();

		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		if (!hasAccess) {
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}
		User technicalUser = null;

		// Ne jamais utiliser comme propriétaire d'ObjectEntry.
		try{
			technicalUser = _userHelper.getTechnicalUser(companyId);
			_log.info("[ UserAdmin ] >>>>: " + technicalUser.getFirstName());

		}
		catch (Exception e) {
			_log.error("[getExpertClients] Compte technique introuvable : " +
					e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"Compte technique manquant. Contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		try {


			// Construction du filtre OData date
			String filter = null;
			if (dateDebut != null && !dateDebut.isBlank() &&
					dateFin != null && !dateFin.isBlank()) {
				filter = "dateCreated ge '" + dateDebut +
						"' and dateCreated le '" + dateFin + "'";
			}

			ObjectEntryHelper objH = new ObjectEntryHelper();
			Sort[] sorts = objH.parseSorts(sort);

			List<ObjectEntry> entries;
			try {
				entries = _objectEntryHelper.searchByFilter(
						technicalUser.getUserId(), companyId, groupId,
						ERC_DEMANDE_VISA,
						filter,
						sorts,
						(long) (page != null ? page : -1),
						(long) (pageSize != null ? pageSize : -1));
			} catch (Exception filterException) {
				// Fallback si le filtre date n'est pas supporté
				_log.warn("getDemandeVisasByDate — fallback sans filtre date : " +
						filterException.getMessage());
				entries = _objectEntryHelper.searchByFilter(
						technicalUser.getUserId(), companyId, groupId,
						ERC_DEMANDE_VISA,
						null,
						sorts,
						(long) (page != null ? page : -1),
						(long) (pageSize != null ? pageSize : -1));
			}

			JSONArray data = _objectEntryHelper.entriesToJson(entries, fields, nestedFields);
			_log.info("entriesToJson : " + data.toString());

			result.put("code", Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			result.put("data", data);
			result.put("total", entries.size());
		} catch (Exception e) {
			_log.error("getDemandeVisasByDate error", e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", e.getMessage());
			result.put("data", JSONFactoryUtil.createJSONArray());
		}
		return Response.ok(result.toString()).build();
	}

	// -------------------------------------------------------------------------
	// getDemandeVisasByExpert
	// -------------------------------------------------------------------------

	@Override
	public Response getDemandeVisasByExpert(
			Long expertId, Integer page, Integer pageSize, String sort,
			String fields, String nestedFields, Integer nestedFieldsDepth)
			throws Exception {

		_log.info(">> getDemandeVisasByExpert — expertId=" + expertId);

		User user = SecurityUtil.checkUser(_httpServletRequest, "getDemandeVisasByExpert");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}

		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		if (!hasAccess) {
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}

		try {
			long userId = user.getUserId();
			long companyId = contextCompany.getCompanyId();
			long groupId = 0L;
			User technicalUser = null;

			// Ne jamais utiliser comme propriétaire d'ObjectEntry.
			try{
				technicalUser = _userHelper.getTechnicalUser(companyId);
				_log.info("[ UserAdmin ] >>>>: " + technicalUser.getFirstName());

			}
			catch (Exception e) {
				_log.error("[getExpertClients] Compte technique introuvable : " +
						e.getMessage(), e);
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("message",
						"Compte technique manquant. Contacter l'administrateur.");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			String filter = ObjectEntryHelper.buildEqFilter(
					"r_iDExpert_c_expertComptableId", expertId);

			ObjectEntryHelper objH = new ObjectEntryHelper();
			Sort[] sorts = objH.parseSorts(sort);

			List<ObjectEntry> entries = _objectEntryHelper.searchByFilter(
					technicalUser.getUserId(), companyId, groupId,
					ERC_DEMANDE_VISA,
					filter,
					sorts,
					(long) (page != null ? page : -1),
					(long) (pageSize != null ? pageSize : -1));

			JSONArray data = _objectEntryHelper.entriesToJson(entries, fields, nestedFields);
			_log.info("entriesToJson : " + data.toString());

			result.put("code", Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			result.put("data", data);
			result.put("total", entries.size());
		} catch (Exception e) {
			_log.error("getDemandeVisasByExpert error", e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", e.getMessage());
			result.put("data", JSONFactoryUtil.createJSONArray());
		}
		return Response.ok(result.toString()).build();
	}

	// -------------------------------------------------------------------------
	// getDemandeVisas (générique)
	// -------------------------------------------------------------------------

	@Override
	public Response getDemandeVisas(
			Integer page, Integer pageSize, String filter, String sort,
			String fields, String nestedFields, Integer nestedFieldsDepth)
			throws Exception {

		_log.info(">> getDemandeVisas (générique) — filter=" + filter);

		User user = SecurityUtil.checkUser(_httpServletRequest, "getDemandeVisas");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}

		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"
				, "Regular COLLABO ASSISTANT Shared Object", "Regular COLLABO MODERATOR Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		if (!hasAccess) {
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}

		try {
			long userId = user.getUserId();
			long companyId = contextCompany.getCompanyId();
			long groupId = 0L;

			User technicalUser = null;

			// Ne jamais utiliser comme propriétaire d'ObjectEntry.
			try{
				technicalUser = _userHelper.getTechnicalUser(companyId);
				_log.info("[ UserAdmin ] >>>>: " + technicalUser.getFirstName());

			}
			catch (Exception e) {
				_log.error("[getExpertClients] Compte technique introuvable : " +
						e.getMessage(), e);
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("message",
						"Compte technique manquant. Contacter l'administrateur.");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			ObjectEntryHelper objH = new ObjectEntryHelper();
			Sort[] sorts = objH.parseSorts(sort);

			List<ObjectEntry> entries = _objectEntryHelper.searchByFilter(
					technicalUser.getUserId(), companyId, groupId,
					ERC_DEMANDE_VISA,
					filter,
					sorts,
					(long) (page != null ? page : -1),
					(long) (pageSize != null ? pageSize : -1));

			JSONArray data = _objectEntryHelper.entriesToJson(entries, fields, nestedFields);
			_log.info(">> entriesToJson : " + data.toString());

			result.put("code", Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			result.put("data", data);
			result.put("total", entries.size());
		} catch (Exception e) {
			_log.error("getDemandeVisas error", e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", e.getMessage());
			result.put("data", JSONFactoryUtil.createJSONArray());
		}
		return Response.ok(result.toString()).build();
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private UserHelper _userHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Context
	private HttpServletRequest _httpServletRequest;
}
// LIFERAY-REST-BUILDER-HASH:370914523