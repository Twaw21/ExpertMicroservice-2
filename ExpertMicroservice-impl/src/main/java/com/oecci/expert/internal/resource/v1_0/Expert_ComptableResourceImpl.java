package com.oecci.expert.internal.resource.v1_0;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.oecci.expert.dto.v1_0.*;
import com.oecci.expert.dto.v1_0.CreateExpertComptable.Inscription_by;
import com.oecci.expert.dto.v1_0.CreateExpertComptable.Inscription_mode;
import com.oecci.expert.dto.v1_0.CreateExpertComptable.Inscription_type;
import com.oecci.expert.resource.v1_0.Expert_ComptableResource;
import com.oecci.expert.utils.*;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author OECCI / DiginFactory — Refactorisation sécurité 2026
 *
 * <p>Remplace tous les appels {@code Utils.executeHttpRequest()} /
 * {@code Utils.uploadDocumentToLiferay()} par des appels natifs via
 * {@link ObjectEntryHelper}, {@link DocumentHelper}, {@link UserHelper}
 * et {@link UserLocalService}.</p>
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/expert_-comptable.properties",
	scope = ServiceScope.PROTOTYPE, service = Expert_ComptableResource.class
)
public class Expert_ComptableResourceImpl
	extends BaseExpert_ComptableResourceImpl {

	private static final Log _log = LogFactoryUtil.getLog(
		Expert_ComptableResourceImpl.class);

	// -------------------------------------------------------------------------
	// ERC centralisés dans Constants
	// -------------------------------------------------------------------------

	private static final String ERC_EXPERT_ASSOC         = Constants.ERC_EXPERT_ASSOC;
	private static final String ERC_EXPERT_COORDINATEUR  = Constants.ERC_EXPERT_COORDINATEUR;
	private static final String ERC_EXPERT_ADJOINT       = Constants.ERC_EXPERT_ADJOINT;
	private static final String ERC_EXPERT_COMPTABLE     = Constants.ERC_EXPERT_COMPTABLE;
	private static final String ERC_WALLET               = Constants.ERC_WALLET;
	private static final String ERC_PAIEMENT             = Constants.ERC_PAIEMENT;
	private static final String ERC_DEMANDE_RECHARGEMENT = Constants.ERC_DEMANDE_RECHARGEMENT;
	private static final String ERC_WALLET_JOURNAL       = Constants.ERC_WALLET_JOURNAL;

	// -------------------------------------------------------------------------
	// createExpertComptable
	// -------------------------------------------------------------------------

	public Response createExpertComptable(
			CreateExpertComptable createExpertComptable)
		throws Exception {

		_log.info(">> Beginning EXPERT COMPTABLE creation..");

		long userId    = contextUser.getUserId();
//		long companyId = contextCompany.getCompanyId();
//		long companyId = 0L;
		long companyId = PortalUtil.getDefaultCompanyId();;
//		long groupId   = Constants.DEV_OECCI_SITE_ID;
		// groupId = 0 requis par Liferay pour les ObjectEntry de scope "company"
		long groupId = 0;

		User user = SecurityUtil.checkUser(_httpServletRequest, "getDemandeAttestationByExpertID");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"
				, "Regular COLLABO ASSISTANT Shared Object", "Regular COLLABO MODERATOR Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
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

		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
			+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info("> Base URL : " + baseURL);
		_log.info(
			"> Inscription type : " +
				createExpertComptable.getInscription_type().getValue());


		// ---- 1. Gestion du collaborateur (ASSOCIÉ / COORDINATEUR / ADJOINT) ----

		ObjectEntry expertCollaboEntry = null;

		if (createExpertComptable.getInscription_type().getValue()
			.equalsIgnoreCase(Inscription_type.COLLABORATEUR.getValue())) {

			String mode = createExpertComptable.getInscription_mode().getValue();
			_log.info("> Inscription mode : " + mode);

			String ercCollabo;
			if (mode.equalsIgnoreCase(Inscription_mode.FOR_ASSOC.getValue())) {
				_log.info(">> Checking EXPERT ASSOC data...");
				ercCollabo = ERC_EXPERT_ASSOC;
			}
			else if (mode.equalsIgnoreCase(Inscription_mode.FOR_COORD.getValue())) {
				_log.info(">> Checking EXPERT COORD data...");
				ercCollabo = ERC_EXPERT_COORDINATEUR;
			}
			else {
				_log.info(">> Checking EXPERT ADJOINT data...");
				ercCollabo = ERC_EXPERT_ADJOINT;
			}

			// Rechercher le collabo par email
			List<ObjectEntry> collaboEntries = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId, ercCollabo,
				ObjectEntryHelper.buildEqFilter(
					"email", createExpertComptable.getEmail()));

			Map<String, Serializable> collaboValues = new HashMap<>();
			collaboValues.put(
				"nomPrenoms",
				createExpertComptable.getPrenoms() + " " +
					createExpertComptable.getNom());
			collaboValues.put("email", createExpertComptable.getEmail());

			if (collaboEntries.isEmpty()) {
				_log.info(
					"> Creating EXPERT COLLABO : " + ercCollabo);
				expertCollaboEntry = _objectEntryHelper.addEntry(
					userId, groupId, companyId, ercCollabo, collaboValues);
			}
			else {
				expertCollaboEntry = collaboEntries.get(0);
				_log.info(
					">> EXPERT COLLABO with email " +
						ObjectEntryHelper.getString(
							expertCollaboEntry, "email") +
						" déjà enregistré. Mise à jour en cours...");
				expertCollaboEntry = _objectEntryHelper.updateEntry(
					userId, groupId, companyId,
					expertCollaboEntry.getObjectEntryId(), collaboValues);
			}

			if (expertCollaboEntry == null) {
				_log.info(
					"L'inscription/mise à jour de l'expert comptable collaborateur " +
						createExpertComptable.getPrenoms() + " " +
						createExpertComptable.getNom() +
						" a échoué.");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put(
					"message",
					"L'inscription/mise à jour de l'expert comptable collaborateur " +
						createExpertComptable.getPrenoms() + " " +
						createExpertComptable.getNom() +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			_log.info(">> EXPERT COLLABO WELL CREATED / UPDATED!! ");
		}

		// ---- 2. Création / mise à jour de l'utilisateur Liferay ----

		_log.info(">> Check if EXPERT COMPTABLE already saved like liferay user..");

		User liferayUser = _userHelper.getUserByEmail(
			companyId, createExpertComptable.getEmail());

		boolean isLiferayUserToCreate = (liferayUser == null);
		String  randomPass            = UserHelper.generateSecurePassword();

		if (isLiferayUserToCreate) {
			_log.info("> Creating EXPERT COMPTABLE as LIFERAY USER...");

			String jobTitle =
				"Expert comptable (" +
					(createExpertComptable.getInscription_mode().getValue()
						.equalsIgnoreCase(Inscription_mode.FOR_NA.getValue())
						? createExpertComptable.getInscription_type().getValue()
						: "Collaborateur") + ")";

			String screenName = UserHelper.buildScreenName(
				createExpertComptable.getPrenoms(),
				createExpertComptable.getNom());

			try {
				_log.warn("Password generated : " + randomPass);
				liferayUser = _userHelper.createUser(
					userId, companyId,
					createExpertComptable.getEmail(),
					createExpertComptable.getPrenoms(),
					createExpertComptable.getNom(),
					screenName,
					jobTitle, randomPass);

			}
			catch (Exception e) {
				e.printStackTrace();
				_log.info(
					">> L'inscription de l'expert comptable " +
						createExpertComptable.getNom() +
						" comme Liferay user a échoué. " + e.getMessage());
				result.put(
					"message",
					"La mise à jour de l'expert comptable " +
						createExpertComptable.getNom() +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}
		else {
			_log.info(
				">> User " + liferayUser.getFullName() +
					" déjà enregistré. Mise à jour en cours...");
			try {
				liferayUser = _userHelper.updateUserContact(
					userId,
					liferayUser.getUserId(),
					createExpertComptable.getPrenoms(),
					createExpertComptable.getNom(),
					"Expert comptable (" +
						(createExpertComptable.getInscription_mode().getValue()
							.equalsIgnoreCase(Inscription_mode.FOR_NA.getValue())
							? createExpertComptable.getInscription_type().getValue()
							: "Collaborateur") + ")");
			}
			catch (Exception e) {
				_log.info(
					">> La mise à jour de l'expert comptable " +
						createExpertComptable.getNom() +
						" comme Liferay user a échoué. " + e.getMessage());
				result.put(
					"message",
					"La mise à jour de l'expert comptable " +
						createExpertComptable.getNom() +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}

		_log.info(
			">> Liferay expert user well created / updated. userId=" +
				liferayUser.getUserId());

		// ---- 3. Affecter l'utilisateur au site et à l'organisation ----

		_log.info(">> Setting user group and organization...");

		Organization expert_org = null;
		Group oecci_expert_group = GroupLocalServiceUtil.getGroup(
			Constants.DEV_EXPERT_SITE_ID);
		_log.info("> Group : " + oecci_expert_group.getGroupKey());

		Organization global_accountant_org =
			OrganizationLocalServiceUtil.getOrganization(
				Constants.DEV_EXPERT_ORGANIZATION);

		if (createExpertComptable.getInscription_mode().getValue()
			.equalsIgnoreCase(Inscription_mode.FOR_NA.getValue())) {

			expert_org = global_accountant_org;
			_log.info("> Organization : " + expert_org.getName());
			_log.info("Setting user in ACCOUNTANT site and group..");
			_userHelper.linkUserToSiteAndOrganization(
				oecci_expert_group.getGroupId(),
				expert_org.getOrganizationId(),
				liferayUser.getUserId());
		}
		else if (createExpertComptable.getInscription_type().getValue()
			.equalsIgnoreCase(Inscription_type.COLLABORATEUR.getValue())) {

			_log.info("Treating COLLABORATOR");

			String mode = createExpertComptable.getInscription_mode().getValue();
			if (mode.equalsIgnoreCase(Inscription_mode.FOR_ASSOC.getValue())) {
				expert_org = OrganizationLocalServiceUtil.getOrganization(
					Constants.DEV_EXPERT_ASSOCIE_ORGANIZATION);
			}
			else if (mode.equalsIgnoreCase(
					Inscription_mode.FOR_COORD.getValue())) {
				expert_org = OrganizationLocalServiceUtil.getOrganization(
					Constants.DEV_EXPERT_COORDINATEUR_ORGANIZATION);
			}
			else if (mode.equalsIgnoreCase(
					Inscription_mode.FOR_ADJ.getValue())) {
				expert_org = OrganizationLocalServiceUtil.getOrganization(
					Constants.DEV_EXPERT_ADJOINT_ORGANIZATION);
			}

			if (expert_org != null) {
				_log.info("> Organization : " + expert_org.getName());
				_log.info("Setting user in ACCOUNTANT particular site and group..");
				_userHelper.linkUserToSiteAndOrganization(
					oecci_expert_group.getGroupId(),
					expert_org.getOrganizationId(),
					liferayUser.getUserId());
				OrganizationLocalServiceUtil.addUserOrganization(
					liferayUser.getUserId(), global_accountant_org);
				_log.info(
					">>> User " + liferayUser.getUserId() +
						" added too to GLOBAL ACCOUNTANT organization " +
						global_accountant_org);
			}
		}

		// ---- 4. Création / mise à jour de l'entité ExpertComptable ----

		_log.info(">> Beginning EXPERT COMPTABLE entity creation...");
		_log.info("> Starting by verify if expert comptable already exist");

		List<ObjectEntry> expertEntries = _objectEntryHelper.searchByFilter(
			technicalUser.getUserId(), companyId, groupId, ERC_EXPERT_COMPTABLE,
			ObjectEntryHelper.buildEqFilter(
				"numeroOrdre", createExpertComptable.getMatricule()));

		boolean isExpertToCreate = expertEntries.isEmpty();
		ObjectEntry existingExpertEntry =
			isExpertToCreate ? null : expertEntries.get(0);

		// Construire les valeurs de l'entité expert comptable
		Map<String, Serializable> updateValues = new HashMap<>();
		updateValues.put("nom",    createExpertComptable.getNom());
		updateValues.put("prenoms", createExpertComptable.getPrenoms());

		String categorieKey;
		Inscription_type inscType =
			createExpertComptable.getInscription_type();
		Inscription_mode inscMode =
			createExpertComptable.getInscription_mode();

		if (inscType.name().equalsIgnoreCase(
				Inscription_type.CABINET.getValue())) {
			categorieKey = "cabinet";
			updateValues.put("nomCabinet",    createExpertComptable.getNomCabinet());
			updateValues.put("numeroCabinet", createExpertComptable.getNumeroCabinet());
		}
		else if (inscType.name().equalsIgnoreCase(
				Inscription_type.INDIVIDUEL.getValue())) {
			categorieKey = "individuel";
		}
		else {
			// COLLABORATEUR
			if (inscMode.name().equalsIgnoreCase(
					Inscription_mode.FOR_ASSOC.getValue())) {
				categorieKey = "aSSOCIE";
				if (expertCollaboEntry != null) {
					updateValues.put(
						"r_iDExpertAssocie_c_expertAssocieId",
						expertCollaboEntry.getObjectEntryId());
				}
			}
			else if (inscMode.name().equalsIgnoreCase(
					Inscription_mode.FOR_COORD.getValue())) {
				categorieKey = "cOORDINATEUR";
				if (expertCollaboEntry != null) {
					updateValues.put(
						"r_iDExpertCoordinateur_c_expertCoordinateurId",
						expertCollaboEntry.getObjectEntryId());
				}
			}
			else {
				categorieKey = "aDJOINT";
				if (expertCollaboEntry != null) {
					updateValues.put(
						"r_iDExpertAdjoint_c_expertAdjointId",
						expertCollaboEntry.getObjectEntryId());
				}
			}
			updateValues.put(
				"r_iDExpertCollaborateur_c_expertComptableId",
				createExpertComptable.getExpertAssoID());
		}

		updateValues.put("categorie", categorieKey);
		updateValues.put("numeroOrdre",     createExpertComptable.getMatricule());
		updateValues.put("email",           createExpertComptable.getEmail());
		updateValues.put("contact",         createExpertComptable.getContact());
		updateValues.put("anneeInscription", createExpertComptable.getAnnee_inscription());
		updateValues.put("adressePostale",  createExpertComptable.getAdressePostale());

		String etatKey;
		if (!createExpertComptable.getInscription_by().name()
			.equalsIgnoreCase(Inscription_by.BY_ADMIN.getValue())) {
			etatKey = "aTRAITER";
		}
		else {
			etatKey = Constants.VALIDATION_STATUT_ACTIF; // "aCTIF"
		}
		updateValues.put("etat", etatKey);

		if (isExpertToCreate) {
			String passEncrypted = SecurityUtil.encrypt(randomPass, Constants.CRYPTO_KEY);
			updateValues.put("mDPTemporaire", passEncrypted);
		}
		updateValues.put(
			"r_iDUserExpertComptable_userId", liferayUser.getUserId());

		ObjectEntry expertComptableEntry;
		if (isExpertToCreate) {
			_log.info("> Creating EXPERT COMPTABLE entity...");
			expertComptableEntry = _objectEntryHelper.addEntry(
				liferayUser.getUserId(), groupId, companyId, ERC_EXPERT_COMPTABLE, updateValues);
		}
		else {
			_log.info(
				">> Expert comptable " +
					ObjectEntryHelper.getString(existingExpertEntry, "nom") +
					" déjà enregistré. Mise à jour en cours...");
			expertComptableEntry = _objectEntryHelper.updateEntry(
					liferayUser.getUserId(), groupId, companyId,
				existingExpertEntry.getObjectEntryId(), updateValues);
		}

		if (expertComptableEntry == null) {
			String msg = (inscType.name().equalsIgnoreCase(
					Inscription_type.CABINET.getValue())
					? "L'inscription du cabinet " +
						createExpertComptable.getNomCabinet() + " a échoué. "
					: "") +
				"L'expert comptable " + createExpertComptable.getNom() +
				" n'a pas pu être enregistré. Veuillez réessayer ou " +
				"contacter l'administrateur si cela persiste. ";
			_log.info(msg);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", msg);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String accountant_name =
			ObjectEntryHelper.getString(expertComptableEntry, "prenoms") +
				" " + ObjectEntryHelper.getString(expertComptableEntry, "nom");
		String currentEtatKey = ObjectEntryHelper.getString(
			expertComptableEntry, "etat");
		_log.info(
			">> Expert comptable " + accountant_name +
				" well created. etat=" + currentEtatKey);

		// ---- 5. Créer le Wallet si le statut est ACTIF ----

		if (Constants.VALIDATION_STATUT_ACTIF.equalsIgnoreCase(currentEtatKey)) {
			_log.info(
				"> Before accountant's notification, we got to create his wallet.");

			List<ObjectEntry> wallets = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId, ERC_WALLET,
				ObjectEntryHelper.buildEqFilter(
					"r_iDExpertWallet_c_expertComptableId",
					expertComptableEntry.getObjectEntryId()));

			if (wallets.isEmpty()) {
				_log.info("Accountant Wallet not found. We create..");

				Map<String, Serializable> walletValues = new HashMap<>();
				walletValues.put("solde", 0L);
				walletValues.put(
					"r_iDExpertWallet_c_expertComptableId",
					expertComptableEntry.getObjectEntryId());

				ObjectEntry walletEntry = _objectEntryHelper.addEntry(
					liferayUser.getUserId(), groupId, companyId, ERC_WALLET, walletValues);

				if (walletEntry == null) {
					_log.info(
						"> Wallet has not been created. retry or contact administrator.");
					result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
					result.put(
						"message",
						"le Wallet de l'expert comptable " + accountant_name +
							" n'a pas pu être créé. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
					result.put("data", "");
					return Response.status(Response.Status.OK).entity(result).build();
				}
				_log.info(
					">> Wallet for " + accountant_name + " well created ! ");
			}
			else {
				_log.info(
					">> Wallet for " + accountant_name +
						"'s accountant already exist and WALLET well FOUND ! ");
			}
		}

		// ---- 6. Notification par email ----
		if(liferayUser.getLastLoginDate() == null){
			_log.info(">> Starting sending notification");

			String templatePath = "/templates/email/demande_inscription_expert.ftl";
			if (createExpertComptable.getInscription_by().name()
					.equalsIgnoreCase(Inscription_by.BY_ADMIN.getValue())) {
				templatePath = "/templates/email/approbation_expert.ftl";
			}

			_log.info(
					">> Sending email notification to : " +
							ObjectEntryHelper.getString(expertComptableEntry, "email"));

			Map<String, Object> templateVariables = new HashMap<>();
			templateVariables.put(
					"type_creation",
					createExpertComptable.getInscription_type().getValue());
			templateVariables.put("nom_expert_comptable", accountant_name);

			if (createExpertComptable.getInscription_type().getValue()
					.equalsIgnoreCase(Inscription_type.COLLABORATEUR.getValue())) {
				templateVariables.put(
						"statut_collaborateur",
						_categorieKeyToName(categorieKey));
			}
			if (inscType.name().equalsIgnoreCase(
					Inscription_type.CABINET.getValue())) {
				templateVariables.put(
						"nom_cabinet",
						ObjectEntryHelper.getString(expertComptableEntry, "nomCabinet"));
			}

			templateVariables.put(
					"adresse_cabinet",
					ObjectEntryHelper.getString(expertComptableEntry, "adressePostale"));
			templateVariables.put(
					"numero_ordre",
					ObjectEntryHelper.getString(expertComptableEntry, "numeroOrdre"));

			if (createExpertComptable.getInscription_by().name()
					.equalsIgnoreCase(Inscription_by.BY_ADMIN.getValue())) {
				templateVariables.put(
						"date_validation", Calendar.getInstance().getTime());
				templateVariables.put(
						"email_access",
						ObjectEntryHelper.getString(expertComptableEntry, "email"));
				templateVariables.put("mot_de_passe_temporaire", randomPass);
				templateVariables.put(
						"url_connexion", baseURL + "/web/oecci_expert");
			}

			String mail_content = Utils.processMailTemplate(
					templatePath, this.getClass(), templateVariables);
			_log.info("mail content updated : " + mail_content);

			JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
			notifPayload.put(
					"email",
					ObjectEntryHelper.getString(expertComptableEntry, "email"));
			notifPayload.put(
					"subject",
					"OECCI : Notification de demande d'inscription d'expert comptable");
			notifPayload.put("content", mail_content);

			try {
				String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
						.replace("[baseUrl]", baseURL);
				_log.info(
						"> headless URL to send expert comptable DEMANDE INSCRIPTION notification : " +
								notifLink);
				JSONObject notification = Utils.executeHttpRequest(
						baseURL, notifLink, notifPayload, Constants.POST_REQUEST);
				if (notification != null)
					_log.info(
							"Notification return code : " +
									notification.getInt("code"));
			} catch (Exception e) {
				_log.info(
						">> Exception while sending notification : " +
								e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		result.put(
			"message",
			"La demande d'inscription de l'expert comptable " +
				ObjectEntryHelper.getString(expertComptableEntry, "raisonSociale") +
				" a été soumise. Vous recevrez un mail concernant le statut de votre demande.");
		result.put("code", Constants.HTTP_SUCCESS);
		result.put("data", _expertEntryToJson(expertComptableEntry));

		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public Response updateCabinet(
			Long cabinetId, UpdateCabinetRequest updateCabinetRequest)
			throws Exception {

		long userId    = contextUser.getUserId();
//		long companyId = contextCompany.getCompanyId();
		long companyId = PortalUtil.getDefaultCompanyId();;
//		long groupId   = Constants.DEV_OECCI_SITE_ID;
		long groupId = 0;
		User user = SecurityUtil.checkUser(_httpServletRequest, "updateCabinet	");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
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


		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
				+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info("> Base URL : " + baseURL);
		_log.info(">> Verifying if accountant box already exists... id=" + cabinetId);


		// 1. Récupérer le cabinet

		ObjectEntry accountantEntry;
		try {
			accountantEntry = _objectEntryHelper.getEntryOrThrow(
					cabinetId);
		}
		catch (Exception e) {
			_log.info(
					"Aucun cabinet n'existe avec cet ID : " +
							cabinetId + ".");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
					"message",
					"Aucun cabinet n'existe avec cet ID : " +
							cabinetId + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String cabinet_name =
				ObjectEntryHelper.getString(accountantEntry, "nomCabinet");
		_log.info(">> Accountant box found : " + cabinet_name);

		// 2. Mettre à jour les données de l'expert

		_log.info(">> Starting by updating accountant box datas...");

		// Construire les valeurs de l'entité expert comptable
		Map<String, Serializable> updateValues = new HashMap<>();
		updateValues.put("nom",    updateCabinetRequest.getNom());
		updateValues.put("prenoms", updateCabinetRequest.getPrenoms());

		String categorieKey;

		categorieKey = "cabinet";
		updateValues.put("nomCabinet",    updateCabinetRequest.getNomCabinet());
		updateValues.put("numeroCabinet", updateCabinetRequest.getNumeroCabinet());
		updateValues.put("categorie", updateCabinetRequest.getCategorie().getKey());
		updateValues.put("numeroOrdre",     updateCabinetRequest.getMatricule());
		updateValues.put("contact",         updateCabinetRequest.getContact());
		updateValues.put("anneeInscription", updateCabinetRequest.getAnnee_inscription());
		updateValues.put("adressePostale",  updateCabinetRequest.getAdressePostale());


		ObjectEntry updatedAccountantEntry = _objectEntryHelper.updateEntry(
				technicalUser.getUserId(), groupId, companyId,
				accountantEntry.getObjectEntryId(), updateValues);

		if (updatedAccountantEntry == null) {
			_log.info(
					"La mise à jour du cabinet " + cabinet_name +
							" a échoué.");
			result.put(
					"message",
					"La mise à jour du cabinet associé " +
							cabinet_name +
							" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info(
				"La mise à jour de l'expert " + cabinet_name +
						" a été effectuée avec succès : " +
						_expertEntryToJson(updatedAccountantEntry));

		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
				"message",
				"La mise à jour du cabinet " + cabinet_name +
						" a été effectuée avec succès.");
		result.put("data", _expertEntryToJson(updatedAccountantEntry));
		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	public Response updateExpertComptable(
			Long expertComptableId, UpdateExpertRequest updateExpertRequest)
			throws Exception{

		long userId    = contextUser.getUserId();
//		long companyId = contextCompany.getCompanyId();
		long companyId = PortalUtil.getDefaultCompanyId();;
//		long groupId   = Constants.DEV_OECCI_SITE_ID;
		long groupId = 0;
		User user = SecurityUtil.checkUser(_httpServletRequest, "updateExpertComptable	");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
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

		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
				+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info("> Base URL : " + baseURL);
		_log.info(">> Verifying if expert comptable already exists... id=" + expertComptableId);


		// 1. Récupérer l'expert comptable

		ObjectEntry accountantEntry;
		try {
			accountantEntry = _objectEntryHelper.getEntryOrThrow(
					expertComptableId);
		}
		catch (Exception e) {
			_log.info(
					"Aucun expert comptable n'existe avec cet ID : " +
							expertComptableId + ".");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
					"message",
					"Aucun expert comptable n'existe avec cet ID : " +
							expertComptableId + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String accountant_name =
				ObjectEntryHelper.getString(accountantEntry, "prenoms") + " " +
						ObjectEntryHelper.getString(accountantEntry, "nom");
		_log.info(">> Accountant found : " + accountant_name);

		// 2. Mettre à jour les données de l'expert

		_log.info(">> Starting by updating accountant datas...");

		Map<String, Serializable> updateValues = new HashMap<>();
		// Construire les valeurs de l'entité expert comptable
		updateValues.put("nom",    updateExpertRequest.getNom());
		updateValues.put("prenoms", updateExpertRequest.getPrenoms());

		String categorieKey;

	/*	if (inscType.name().equalsIgnoreCase(
				Inscription_type.CABINET.getValue())) {
			categorieKey = "cabinet";
			updateValues.put("nomCabinet",    createExpertComptable.getNomCabinet());
			updateValues.put("numeroCabinet", createExpertComptable.getNumeroCabinet());
		}
		else if (inscType.name().equalsIgnoreCase(
				Inscription_type.INDIVIDUEL.getValue())) {
			categorieKey = "individuel";
		}

		updateValues.put("categorie", categorieKey);
		*/
		updateValues.put("numeroOrdre",     updateExpertRequest.getMatricule());
		updateValues.put("contact",         updateExpertRequest.getContact());
		updateValues.put("anneeInscription", updateExpertRequest.getAnnee_inscription());
		updateValues.put("adressePostale",  updateExpertRequest.getAdressePostale());


		ObjectEntry updatedAccountantEntry = _objectEntryHelper.updateEntry(
				technicalUser.getUserId(), groupId, companyId,
				accountantEntry.getObjectEntryId(), updateValues);

		if (updatedAccountantEntry == null) {
			_log.info(
					"La mise à jour de l'expert comptable " + accountant_name +
							" a échoué.");
			result.put(
					"message",
					"La mise à jour de l'expert comptable associé " +
							accountant_name +
							" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info(
				"La mise à jour de l'expert " + accountant_name +
						" a été effectuée avec succès : " +
						_expertEntryToJson(updatedAccountantEntry));

		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
				"message",
				"La mise à jour de l'expert " + accountant_name +
						" a été effectuée avec succès.");
		result.put("data", _expertEntryToJson(updatedAccountantEntry));
		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}


	// -------------------------------------------------------------------------
	// validateExpertComptable
	// -------------------------------------------------------------------------

	public Response validateExpertComptable(
			Long expertComptableID, StatutRequest statutRequest)
		throws Exception {

		long userId    = contextUser.getUserId();
//		long companyId = contextCompany.getCompanyId();
		long companyId = PortalUtil.getDefaultCompanyId();;
//		long groupId   = Constants.DEV_OECCI_SITE_ID;
		long groupId = 0;
		User user = SecurityUtil.checkUser(_httpServletRequest, "getDemandeAttestationByExpertID");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
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

		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
			+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info("> Base URL : " + baseURL);
		_log.info(">> Verifying if expert comptable already exists... id=" + expertComptableID);


		// 1. Récupérer l'expert comptable

		ObjectEntry accountantEntry;
		try {
			accountantEntry = _objectEntryHelper.getEntryOrThrow(
				expertComptableID);
		}
		catch (Exception e) {
			_log.info(
				"Aucun expert comptable n'existe avec cet ID : " +
					expertComptableID + ".");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Aucun expert comptable n'existe avec cet ID : " +
					expertComptableID + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String accountant_name =
			ObjectEntryHelper.getString(accountantEntry, "prenoms") + " " +
				ObjectEntryHelper.getString(accountantEntry, "nom");
		_log.info(">> Accountant found : " + accountant_name);

		// 2. Mettre à jour le statut de validation

		_log.info(">> Starting by updating accountant validation statut...");

		Map<String, Serializable> updateValues = new HashMap<>();
		updateValues.put("etat", statutRequest.getStatut().getKey());

		ObjectEntry updatedAccountantEntry = _objectEntryHelper.updateEntry(
			technicalUser.getUserId(), groupId, companyId,
			accountantEntry.getObjectEntryId(), updateValues);

		if (updatedAccountantEntry == null) {
			_log.info(
				"La mise à jour de l'expert comptable " + accountant_name +
					" a échoué.");
			result.put(
				"message",
				"La mise à jour de l'expert comptable associé " +
					accountant_name +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String updatedEtatKey = ObjectEntryHelper.getString(
			updatedAccountantEntry, "etat");
		_log.info(
			">> Expert " + accountant_name + " well updated. Statut : " +
				_etatKeyToName(updatedEtatKey));

		// 3. Créer le Wallet si ACTIF
		String accountantEmail = ObjectEntryHelper.getString(
				updatedAccountantEntry, "email");

		User liferayUser = _userHelper.getUserByEmail(
				companyId, accountantEmail);
		if (Constants.VALIDATION_STATUT_ACTIF.equalsIgnoreCase(updatedEtatKey)) {
			_log.info(
				"> Before accountant's notification, we got to create his wallet.");

			List<ObjectEntry> wallets = _objectEntryHelper.searchByFilter(
				userId, companyId, groupId, ERC_WALLET,
				ObjectEntryHelper.buildEqFilter(
					"r_iDExpertWallet_c_expertComptableId",
					expertComptableID));

			if (wallets.isEmpty()) {
				_log.info("Accountant Wallet not found. We create..");

				Map<String, Serializable> walletValues = new HashMap<>();
				walletValues.put("solde", 0L);
				walletValues.put(
					"r_iDExpertWallet_c_expertComptableId",
					updatedAccountantEntry.getObjectEntryId());

				_log.info("Accountant found for wallet update : "+liferayUser.getFullName());
				ObjectEntry walletEntry = _objectEntryHelper.addEntry(
						liferayUser.getUserId(), groupId, companyId, ERC_WALLET, walletValues);

				if (walletEntry == null) {
					_log.info(
						"> Wallet has not been created. retry or contact administrator.");
					result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
					result.put(
						"message",
						"le Wallet de l'expert comptable " + accountant_name +
							" n'a pas pu être créé. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
					result.put("data", "");
					return Response.status(Response.Status.OK).entity(result).build();
				}
			}
			else {
				_log.info(
					">> Wallet for " + accountant_name +
						"'s accountant already exist and WALLET well FOUND ! ");
			}
		}
		else if (Constants.VALIDATION_STATUT_SUSPENDU.equalsIgnoreCase(
				updatedEtatKey)) {

			// 4. Désactiver l'utilisateur Liferay si SUSPENDU

			_log.info(">> Suspending Liferay user for accountant : " + accountant_name);

			if (liferayUser == null) {
				_log.info(
					"> User has not been created yet. retry or contact administrator.");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put(
					"message",
					"l'expert comptable " + accountant_name +
						" n'a pas encore été créé. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}

			try {
				ServiceContext sc = new ServiceContext();
				_userLocalService.updateStatus(
					liferayUser.getUserId(),
					WorkflowConstants.STATUS_INACTIVE, sc);
				_log.info(
					">> Liferay expert user successfully deactivated.");
			}
			catch (Exception e) {
				_log.error(
					">> La mise à jour de l'expert comptable " +
						ObjectEntryHelper.getString(
							updatedAccountantEntry, "nom") +
						" comme Liferay user a échoué.", e);
				result.put(
					"message",
					"La mise à jour de l'expert comptable " +
						ObjectEntryHelper.getString(
							updatedAccountantEntry, "nom") +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}

		// 5. Notification par email

		_log.info(
			">> Sending notification to : " +
				ObjectEntryHelper.getString(updatedAccountantEntry, "email"));

		String redirection_link = baseURL + "/web/oecci_expert";
		String templatePath     = null;
		Map<String, Object> templateVariables = new HashMap<>();

		if (Constants.VALIDATION_STATUT_ACTIF.equalsIgnoreCase(updatedEtatKey)) {
			templatePath = "/templates/email/approbation_expert.ftl";
			_log.info("> EXPERT's DEMANDE INSCRIPTION has been VALIDATED!");
			templateVariables.put(
				"email_access",
				ObjectEntryHelper.getString(updatedAccountantEntry, "email"));

			String encPass = ObjectEntryHelper.getString(updatedAccountantEntry, "mDPTemporaire");
			String decPass = (encPass != null && !encPass.isEmpty())
					? SecurityUtil.decrypt(encPass, Constants.CRYPTO_KEY)
					: "";
			templateVariables.put("mot_de_passe_temporaire", decPass);
			templateVariables.put("url_connexion", redirection_link);
		}
		else if (Constants.VALIDATION_STATUT_REJET.equalsIgnoreCase(
				updatedEtatKey)) {
			templatePath = "/templates/email/rejet_expert.ftl";
			_log.info("> EXPERT's DEMANDE INSCRIPTION has been REJECTED!");
			templateVariables.put(
				"motif_rejet",
				(statutRequest.getMotif_refus() != null &&
					!statutRequest.getMotif_refus().isEmpty())
					? statutRequest.getMotif_refus() : "");
		}
		else if (Constants.VALIDATION_STATUT_SUSPENDU.equalsIgnoreCase(
				updatedEtatKey)) {
			templatePath = "/templates/email/suspension_expert.ftl";
			_log.info("> EXPERT's DEMANDE INSCRIPTION has been SUSPENDED!");
			templateVariables.put(
				"motifs_suspension",
				(statutRequest.getMotif_refus() != null &&
					!statutRequest.getMotif_refus().isEmpty())
					? statutRequest.getMotif_refus() : "");
		}

		String updatedCategorieKey = ObjectEntryHelper.getString(
			updatedAccountantEntry, "categorie");

		templateVariables.put(
			"type_creation", _categorieKeyToName(updatedCategorieKey));
		templateVariables.put("nom_expert_comptable", accountant_name);

		if (_categorieKeyToName(updatedCategorieKey).equalsIgnoreCase(
				Inscription_type.COLLABORATEUR.getValue())) {
			templateVariables.put(
				"statut_collaborateur",
				_categorieKeyToName(updatedCategorieKey));
		}
		if (_categorieKeyToName(updatedCategorieKey).equalsIgnoreCase(
				Inscription_type.CABINET.getValue())) {
			templateVariables.put(
				"nom_cabinet",
				ObjectEntryHelper.getString(
					updatedAccountantEntry, "nomCabinet"));
		}

		templateVariables.put(
			"adresse_cabinet",
			ObjectEntryHelper.getString(
				updatedAccountantEntry, "adressePostale"));
		templateVariables.put(
			"numero_ordre",
			ObjectEntryHelper.getString(
				updatedAccountantEntry, "numeroOrdre"));
		templateVariables.put(
			"date_validation", Calendar.getInstance().getTime());

		if (templatePath != null) {
			String mail_content = Utils.processMailTemplate(
				templatePath, this.getClass(), templateVariables);
			_log.info("mail content updated : " + mail_content);

			JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
			notifPayload.put(
				"email",
				ObjectEntryHelper.getString(updatedAccountantEntry, "email"));
			notifPayload.put(
				"subject",
				"OECCI : Notification de demande d'inscription d'expert comptable");
			notifPayload.put("content", mail_content);

			try {
				String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
					.replace("[baseUrl]", baseURL);
				_log.info(
					"> headless URL to send expert comptable notification : " +
						notifLink);
				JSONObject notification = Utils.executeHttpRequest(
					baseURL, notifLink, notifPayload, Constants.POST_REQUEST);
				if (notification != null)
					_log.info(
						"Notification return code : " +
							notification.getInt("code"));
			}
			catch (Exception e) {
				_log.info(
					">> Exception while sending notification : " +
						e.getLocalizedMessage());
				e.printStackTrace();
			}
		}

		_log.info(
			"La mise à jour de l'expert " + accountant_name +
				" a été effectuée avec succès : " +
				_etatKeyToName(updatedEtatKey));

		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
			"message",
			"La mise à jour de l'expert " + accountant_name +
				" a été effectuée avec succès : " +
				_etatKeyToName(updatedEtatKey));
		result.put("data", _expertEntryToJson(updatedAccountantEntry));
		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// -------------------------------------------------------------------------
	// reloadWallet
	// -------------------------------------------------------------------------

	public Response reloadWallet(ReloadWalletRequest reloadWalletRequest)
			throws Exception {

		_log.info(">> EXPERT WALLET's update starting..");

		User user = SecurityUtil.checkUser(_httpServletRequest, "reloadWallet");
		if (user == null) {
			return Response.status(Response.Status.OK)
					.entity(SecurityUtil.getResult().toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		long userId    = user.getUserId();
		long companyId = PortalUtil.getDefaultCompanyId();
		long groupId   = 0;

		_log.info("[reloadWallet] >> Paramètres contexte — userId=" + userId
				+ " companyId=" + companyId + " groupId=" + groupId);
		_log.info("[reloadWallet] >> Requête reçue — paymentID=" + reloadWalletRequest.getPaymentID()
				+ " expertComptableID=" + reloadWalletRequest.getExpertComptableID()
				+ " amount=" + reloadWalletRequest.getAmount());

		// ── Utilisateur technique ────────────────────────────────────────────────

		_log.info("[reloadWallet] STEP 0 — Récupération de l'utilisateur technique...");

		User technicalUser;
		try {
			technicalUser = _userHelper.getTechnicalUser(companyId);
			_log.info("[reloadWallet] STEP 0 OK — technicalUser=" + technicalUser.getFullName()
					+ " (id=" + technicalUser.getUserId() + ")");
		}
		catch (Exception e) {
			_log.error("[reloadWallet] STEP 0 FAIL — Compte technique introuvable : "
					+ e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "Compte technique manquant. Contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		long technicalUserId = technicalUser.getUserId();

		// ── STEP 1 : Vérifier que le paiement existe ────────────────────────────

		_log.info("[reloadWallet] STEP 1 — Recherche du paiement id="
				+ reloadWalletRequest.getPaymentID() + "...");

		ObjectEntry paymentEntry;
		try {
			paymentEntry = _objectEntryHelper.getEntryOrThrow(
					reloadWalletRequest.getPaymentID());
			_log.info("[reloadWallet] STEP 1 OK — paiement trouvé, objectEntryId="
					+ paymentEntry.getObjectEntryId());
		}
		catch (Exception e) {
			_log.info("[reloadWallet] STEP 1 FAIL — Aucun paiement trouvé avec id="
					+ reloadWalletRequest.getPaymentID() + " : " + e.getMessage());
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message",
					"Aucune trace de paiement effectué avec ce ID : "
							+ reloadWalletRequest.getPaymentID()
							+ ". Le rechargement du wallet ne peut être effectué."
							+ " Veuillez reprendre la procédure ou contacter un administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		String payStatus = ObjectEntryHelper.getString(paymentEntry, "paystatus");
		_log.info("[reloadWallet] STEP 1 — paystatus='" + payStatus + "'");

		if (!payStatus.equalsIgnoreCase("ACCEPTED") &&
				!payStatus.equalsIgnoreCase("SUCCESS")   &&
				!payStatus.equalsIgnoreCase("succeeded") &&
				!payStatus.equalsIgnoreCase("ACCEPT")    &&
				!payStatus.equalsIgnoreCase("COMPLETED")) {
			_log.info("[reloadWallet] STEP 1 FAIL — Paiement id="
					+ reloadWalletRequest.getPaymentID()
					+ " non accepté (paystatus='" + payStatus + "'). Rechargement annulé.");
			result.put("code", Constants.HTTP_ACTION_NOT_ABLE);
			result.put("message",
					"Le paiement n'a pas pu être effectué avec succès."
							+ " Le rechargement du wallet ne peut être effectué."
							+ " Veuillez reprendre la procédure ou contacter un administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}
		_log.info("[reloadWallet] STEP 1 OK — Paiement validé (paystatus='" + payStatus + "').");

		// ── STEP 2 : Vérifier que l'expert comptable existe ─────────────────────

		_log.info("[reloadWallet] STEP 2 — Recherche de l'expert comptable id="
				+ reloadWalletRequest.getExpertComptableID() + "...");

		ObjectEntry accountantEntry;
		try {
			accountantEntry = _objectEntryHelper.getEntryOrThrow(
					reloadWalletRequest.getExpertComptableID());
			_log.info("[reloadWallet] STEP 2 OK — expert comptable trouvé, objectEntryId="
					+ accountantEntry.getObjectEntryId());
		}
		catch (Exception e) {
			_log.info("[reloadWallet] STEP 2 FAIL — Aucun expert comptable avec id="
					+ reloadWalletRequest.getExpertComptableID() + " : " + e.getMessage());
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message",
					"Aucun expert comptable n'existe avec cet ID : "
							+ reloadWalletRequest.getExpertComptableID() + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		String accountant_name =
				ObjectEntryHelper.getString(accountantEntry, "prenoms") + " "
						+ ObjectEntryHelper.getString(accountantEntry, "nom");
		_log.info("[reloadWallet] STEP 2 OK — Expert : " + accountant_name
				+ " (objectEntryId=" + accountantEntry.getObjectEntryId() + ")");

		// ── STEP 3 : Vérifier que le Wallet existe ───────────────────────────────

		String walletFilter = ObjectEntryHelper.buildEqFilter(
				"r_iDExpertWallet_c_expertComptableId",
				accountantEntry.getObjectEntryId());

		_log.info("[reloadWallet] STEP 3 — Recherche du wallet"
				+ " (ERC=" + ERC_WALLET + ")"
				+ " filtre='" + walletFilter + "'"
				+ " technicalUserId=" + technicalUserId
				+ " companyId=" + companyId
				+ " groupId=" + groupId + "...");

		List<ObjectEntry> wallets;
		try {
			_log.info("=================================================");
			_log.info("[DEBUG WALLET SEARCH]");
			_log.info("ERC_WALLET      = " + ERC_WALLET);
			_log.info("walletFilter    = " + walletFilter);
			_log.info("technicalUserId = " + technicalUserId);
			_log.info("companyId       = " + companyId);
			_log.info("groupId         = " + groupId);
			_log.info("=================================================");
			wallets = _objectEntryHelper.searchByFilter(
					technicalUserId, companyId, groupId, ERC_WALLET, walletFilter);
			_log.info("[reloadWallet] STEP 3 — searchByFilter retourné : "
					+ wallets.size() + " wallet(s).");
		}
		catch (Exception e) {
			_log.error("[reloadWallet] STEP 3 FAIL — Exception lors de la recherche du wallet : "
					+ e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"Erreur lors de la recherche du wallet de l'expert " + accountant_name
							+ ". Veuillez contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		if (wallets.isEmpty()) {
			_log.info("[reloadWallet] STEP 3 FAIL — Aucun wallet trouvé pour " + accountant_name
					+ " (expertComptableId=" + accountantEntry.getObjectEntryId() + ")."
					+ " Vérifier que le wallet a bien été créé lors de la validation de l'expert.");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message",
					"Aucun Wallet trouvé pour l'expert " + accountant_name
							+ ". Veuillez réessayer, ou activer son DEPOSIT,"
							+ " ou contacter l'administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		ObjectEntry walletEntry = wallets.get(0);
		long walletEntryId = walletEntry.getObjectEntryId();
		long currentSolde  = ObjectEntryHelper.getLong(walletEntry, "solde");
		long newSoldeExpected = currentSolde + reloadWalletRequest.getAmount();

		_log.info("[reloadWallet] STEP 3 OK — Wallet trouvé : objectEntryId=" + walletEntryId
				+ " soldeActuel=" + currentSolde
				+ " montantAjouter=" + reloadWalletRequest.getAmount()
				+ " nouveauSoldeAttendu=" + newSoldeExpected);

		// ── STEP 4 : Mettre à jour le solde du Wallet ────────────────────────────

		_log.info("[reloadWallet] STEP 4 — Mise à jour du solde du wallet id=" + walletEntryId
				+ " (" + currentSolde + " → " + newSoldeExpected + ")...");

		Map<String, Serializable> walletUpdateValues = new HashMap<>();
		walletUpdateValues.put("solde", newSoldeExpected);
		walletUpdateValues.put(
				"r_iDExpertWallet_c_expertComptableId",
				accountantEntry.getObjectEntryId());

		_log.info("[reloadWallet] STEP 4 — Valeurs pour updateEntry : " + walletUpdateValues);

		ObjectEntry updatedWalletEntry;
		try {
			updatedWalletEntry = _objectEntryHelper.updateEntry(
					technicalUserId, groupId, companyId, walletEntryId, walletUpdateValues);
		}
		catch (Exception e) {
			_log.error("[reloadWallet] STEP 4 FAIL — Exception lors du updateEntry wallet id="
					+ walletEntryId + " : " + e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"Le solde du wallet de l'expert " + accountant_name
							+ " n'a pas pu être mis à jour (exception)."
							+ " Veuillez réessayer ou contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		if (updatedWalletEntry == null) {
			_log.info("[reloadWallet] STEP 4 FAIL — updateEntry a retourné null pour wallet id="
					+ walletEntryId + ". Vérifier les droits du compte technique et les contraintes Liferay.");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"Le solde du wallet de l'expert " + accountant_name
							+ " n'a pas pu être mis à jour."
							+ " Veuillez réessayer ou contacter l'administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		long newSolde = ObjectEntryHelper.getLong(updatedWalletEntry, "solde");
		_log.info("[reloadWallet] STEP 4 OK — Wallet mis à jour. Nouveau solde confirmé : " + newSolde);

		// ── STEP 5 : Créer la trace de rechargement ──────────────────────────────

		_log.info("[reloadWallet] STEP 5 — Création de la trace de rechargement"
				+ " (ERC=" + ERC_DEMANDE_RECHARGEMENT + ")"
				+ " walletId=" + updatedWalletEntry.getObjectEntryId()
				+ " paiementId=" + paymentEntry.getObjectEntryId()
				+ " amount=" + reloadWalletRequest.getAmount() + "...");

		Map<String, Serializable> rechargementValues = new HashMap<>();
		rechargementValues.put("r_iDWallet_c_walletId",             updatedWalletEntry.getObjectEntryId());
		rechargementValues.put("r_iDPaymentReloading_c_paiementId", paymentEntry.getObjectEntryId());
		rechargementValues.put("amount",                             reloadWalletRequest.getAmount());

		ObjectEntry rechargementEntry;
		try {
			rechargementEntry = _objectEntryHelper.addEntry(
					technicalUserId, groupId, companyId,
					ERC_DEMANDE_RECHARGEMENT, rechargementValues);
		}
		catch (Exception e) {
			_log.error("[reloadWallet] STEP 5 FAIL — Exception lors de la création du rechargement : "
					+ e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"La trace de rechargement du wallet de l'expert " + accountant_name
							+ " n'a pas pu être créée (exception)."
							+ " Veuillez réessayer ou contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		if (rechargementEntry == null) {
			_log.info("[reloadWallet] STEP 5 FAIL — addEntry rechargement a retourné null."
					+ " Vérifier ERC=" + ERC_DEMANDE_RECHARGEMENT + " et les droits du compte technique.");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"La trace de rechargement du wallet de l'expert " + accountant_name
							+ " n'a pas pu être créée."
							+ " Veuillez réessayer ou contacter l'administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		_log.info("[reloadWallet] STEP 5 OK — Trace de rechargement créée, objectEntryId="
				+ rechargementEntry.getObjectEntryId());

		// ── STEP 6 : Créer l'entrée dans le journal du Wallet ───────────────────

		_log.info("[reloadWallet] STEP 6 — Création de l'entrée journal"
				+ " (ERC=" + ERC_WALLET_JOURNAL + ")"
				+ " walletId=" + updatedWalletEntry.getObjectEntryId()
				+ " typeMouvement=" + Constants.MOVMENT_TYPE_IN_KEY
				+ " typeOperation=" + Constants.OPERATION_TYPE_RELOAD_KEY
				+ " amount=" + reloadWalletRequest.getAmount() + "...");

		Map<String, Serializable> journalValues = new HashMap<>();
		journalValues.put("amount",                        reloadWalletRequest.getAmount());
		journalValues.put("typeMouvement",                 Constants.MOVMENT_TYPE_IN_KEY);
		journalValues.put("typeOperation",                 Constants.OPERATION_TYPE_RELOAD_KEY);
		journalValues.put("r_iDWalletJournal_c_walletId", updatedWalletEntry.getObjectEntryId());

		ObjectEntry journalEntry;
		try {
			journalEntry = _objectEntryHelper.addEntry(
					technicalUserId, groupId, companyId,
					ERC_WALLET_JOURNAL, journalValues);
		}
		catch (Exception e) {
			_log.error("[reloadWallet] STEP 6 FAIL — Exception lors de la création du journal : "
					+ e.getMessage(), e);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"L'entrée journal du wallet de l'expert " + accountant_name
							+ " n'a pas pu être créée (exception)."
							+ " Veuillez réessayer ou contacter l'administrateur.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		if (journalEntry == null) {
			_log.info("[reloadWallet] STEP 6 FAIL — addEntry journal a retourné null."
					+ " Vérifier ERC=" + ERC_WALLET_JOURNAL + " et les droits du compte technique.");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message",
					"L'entrée journal du wallet de l'expert " + accountant_name
							+ " n'a pas pu être créée."
							+ " Veuillez réessayer ou contacter l'administrateur si cela persiste.");
			result.put("data", "");
			return Response.status(Response.Status.OK)
					.entity(result.toString())
					.type(MediaType.APPLICATION_JSON)
					.build();
		}

		_log.info("[reloadWallet] STEP 6 OK — Entrée journal créée, objectEntryId="
				+ journalEntry.getObjectEntryId());

		// ── Réponse finale ───────────────────────────────────────────────────────

		_log.info("[reloadWallet] SUCCES — Wallet de " + accountant_name
				+ " rechargé avec succès. Ancien solde=" + currentSolde
				+ " Montant=" + reloadWalletRequest.getAmount()
				+ " Nouveau solde=" + newSolde);

		result.put("code", Constants.HTTP_SUCCESS);
		result.put("message",
				"Le wallet de l'expert comptable " + accountant_name + " a bien été mis à jour.");
		result.put("data",
				JSONFactoryUtil.createJSONObject().put("sold", newSolde));

		_log.info("[reloadWallet] >> Returning response");
		return Response.status(Response.Status.OK)
				.entity(result.toString())
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

	// -------------------------------------------------------------------------
	// loadSignVisual
	// -------------------------------------------------------------------------

	public Response loadSignVisual(
			Long expertComptableID, LoadVisualRequest loadVisualRequest)
		throws Exception {

		_log.info(">> EXPERT VISUAL's signature saving..");

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = Constants.DEV_OECCI_SITE_ID;

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// 1. Récupérer l'expert comptable

		_log.info(">> Accountant checking..");

		ObjectEntry accountantEntry;
		try {
			accountantEntry = _objectEntryHelper.getEntryOrThrow(
				expertComptableID);
		}
		catch (Exception e) {
			_log.info(
				"No accountant exists with this ID : " +
					expertComptableID + ".");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Aucun expert comptable n'existe avec cet ID : " +
					expertComptableID + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String accountant_name =
			ObjectEntryHelper.getString(accountantEntry, "prenoms") + " " +
				ObjectEntryHelper.getString(accountantEntry, "nom");
		_log.info(">> Accountant found : " + accountant_name);

		// 2. Valider le contenu de la requête

		if (loadVisualRequest.getVisualContent() == null ||
			loadVisualRequest.getVisualName() == null) {

			result.put("code", Constants.HTTP_ERROR_DATA_MALFORMED);
			result.put(
				"message",
				"Nom du visuel ou visuel absents. Veuillez charger un visuel " +
					"et donner un nom au visuel puis réessayez svp.");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		// 3. Décoder et uploader le fichier dans la Document Library

		_log.info(">> Starting visual's signature loading in DOCUMENT & MEDIA");
		_log.info("> Decoding file content from base64");

		byte[] decodedBytes = Base64.getDecoder().decode(
			loadVisualRequest.getVisualContent());

		String rawFileName =
			ObjectEntryHelper.getString(accountantEntry, "numeroOrdre") +
				"_" + loadVisualRequest.getVisualName();
		String safeFileName = DocumentHelper.sanitizeFileName(rawFileName);

		String tempDir = System.getProperty("java.io.tmpdir");
		File fileToUpload = new File(tempDir, safeFileName);

		long signatureVisuelID = 0L;
		boolean isNewUpload    = true;

		try {
			FileUtil.write(fileToUpload, decodedBytes);
			_log.info("> Decoded bytes well saved to file to upload");

			try {
				signatureVisuelID = _documentHelper.uploadFile(
					userId, Constants.DEV_EXPERT_SITE_ID, safeFileName,
					fileToUpload);
				_log.info(
					">> Document well uploaded to DOCUMENT & MEDIA. fileEntryId=" +
						signatureVisuelID);
			}
			catch (DuplicateFileEntryException dfe) {
				// Le fichier existe déjà — conserver l'ID existant
				_log.info(
					">> Visual already exists in DL. Keeping existing fileEntryId.");
				isNewUpload     = false;
				signatureVisuelID = ObjectEntryHelper.getLong(
					accountantEntry, "signatureVisuelID");
			}
			catch (Exception uploadEx) {
				_log.error(
					"L'enregistrement du visuel de l'expert " +
						accountant_name + " a échoué.", uploadEx);
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put(
					"message",
					"L'enregistrement du visuel de l'expert " +
						accountant_name +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}
		finally {
			if (fileToUpload.exists()) {
				fileToUpload.delete();
			}
		}

		// 4. Mettre à jour l'expert avec l'ID du visuel de signature

		if (signatureVisuelID == 0L &&
			!isNewUpload &&
			ObjectEntryHelper.getLong(accountantEntry, "signatureVisuelID") != 0L) {
			signatureVisuelID = ObjectEntryHelper.getLong(
				accountantEntry, "signatureVisuelID");
		}

		Map<String, Serializable> updateValues = new HashMap<>();
		updateValues.put("signatureVisuelID", signatureVisuelID);

		ObjectEntry updatedExpert = _objectEntryHelper.updateEntry(
			userId, groupId, companyId,
			accountantEntry.getObjectEntryId(), updateValues);

		if (updatedExpert == null) {
			_log.info(">> L'ajout du visuel de signature a échoué");
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", "L'ajout du visuel de signature a échoué");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info(">> L'ajout du visuel de signature a été effectué avec succès");
		result.put("code", Constants.HTTP_SUCCESS);
		result.put("message", "L'ajout du visuel de signature a été effectué avec succès");
		result.put("data", "");
		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	@Override
	public Response getExpertComptableById(Long expertComptableId, String fields, String nestedFields, Integer nestedFieldsDepth) throws Exception {
		_log.info(">> getExpertComptableById — expertComptableId=" + expertComptableId);

		User user = SecurityUtil.checkUser(_httpServletRequest, "getExpertComptableById");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}
		try {
			ObjectEntry entry = _objectEntryHelper.getEntryOrThrow(expertComptableId);

			JSONObject data = JSONFactoryUtil.createJSONObject();
			JSONObject entriesToJson = _objectEntryHelper.entriesToJson(Collections.singletonList(entry), fields
					, nestedFields).getJSONObject(0);
			_log.info("accountant get : "+entriesToJson);
			result.put("code",    Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			result.put("data",    entriesToJson);
		} catch (IllegalArgumentException e) {
			result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Expert comptable introuvable : id=" + expertComptableId);
		} catch (Exception e) {
			_log.error("getExpertComptableById error", e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", e.getMessage());
			result.put("data",    JSONFactoryUtil.createJSONObject());
		}
		return Response.ok(result.toString()).build();

	}

	@Override
	public Response getExpertComptablesByCategorie(String categorie, String etat, Integer page, Integer pageSize, String sort,
												   String fields, String nestedFields, Integer nestedFieldsDepth) throws Exception {
		_log.info(">> getExpertComptablesByCategorie — categorie=" + categorie + " etat=" + etat);
		User user = SecurityUtil.checkUser(_httpServletRequest, "getExpertComptablesByCategorie");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		_log.info("[ CurrentUser ] >>>>: " + user.getFullName());
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}
		try {

			long companyId = contextCompany.getCompanyId();
			long groupId   = 0L;

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

			// Filtre composé : categorie [and etat]
			String filter;
			if (categorie != null && !categorie.isBlank() && etat != null && !etat.isBlank()) {
				filter = ObjectEntryHelper.buildAndFilter(
						ObjectEntryHelper.buildEqFilter("categorie", categorie),
						ObjectEntryHelper.buildEqFilter("etat", etat));
			} else if (categorie != null && !categorie.isBlank()) {
				filter = ObjectEntryHelper.buildEqFilter("categorie", categorie);
			} else if (etat != null && !etat.isBlank()) {
				filter = ObjectEntryHelper.buildEqFilter("etat", etat);
			} else {
				filter = null;
			}

			Sort[] sorts = _objectEntryHelper.parseSorts(sort);

			List<ObjectEntry> entries = _objectEntryHelper.searchByFilter(
					technicalUser.getUserId(), companyId, groupId,
					ERC_EXPERT_COMPTABLE,
					filter,
					sorts,
					(long) (page != null ? page : -1),
					(long) (pageSize != null ? pageSize : -1));
			JSONArray entriesToJson = _objectEntryHelper.entriesToJson(entries, fields, nestedFields);
			_log.info("acountant list get : "+entriesToJson.toString());

			result.put("code",    Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			result.put("data",   entriesToJson);
			result.put("total",   entries.size());
		} catch (Exception e) {
			_log.error("getExpertComptablesByCategorie error", e);
			result.put("code",    500);
			result.put("message", e.getMessage());
			result.put("data",    JSONFactoryUtil.createJSONObject());
		}
		return Response.ok(result.toString()).build();
	}

	@Override
	public Response getExpertComptables(Integer page, Integer pageSize, String filter, String sort, String fields, String nestedFields, Integer nestedFieldsDepth) throws Exception {

		_log.info(">> getExpertComptables (générique) — filter=" + filter);
		User user = SecurityUtil.checkUser(_httpServletRequest, "getExpertComptables");
		if (user == null) {
			return Response.status(Response.Status.OK).entity(SecurityUtil.getResult()).build();
		}
		String[] roles = {"Regular EXPERTS Shared Object", "Regular COLLABO ADMIN Shared Object"};
		boolean hasAccess = SecurityUtil.checkAccess(_httpServletRequest, user, roles);
		JSONObject result = JSONFactoryUtil.createJSONObject();
		if (!hasAccess) {
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_RESOURCE_FORBIDEN);
			result.put("message", "Vous n'avez les permissions nécessaires.");
			result.put("data", "");
			return Response.status(Response.Status.FORBIDDEN).entity(result).build();
		}
		try {
			long userId    = contextUser.getUserId();
			long companyId = contextCompany.getCompanyId();
			long groupId   = 0L;

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

			Sort[] sorts = _objectEntryHelper.parseSorts(sort);

			List<ObjectEntry> entries = _objectEntryHelper.searchByFilter(
					technicalUser.getUserId(), companyId, groupId,
					ERC_EXPERT_COMPTABLE,
					filter,
					sorts,
					(long) (page != null ? page : -1),
					(long) (pageSize != null ? pageSize : -1));

			result.put("code",    Constants.HTTP_SUCCESS);
			result.put("message", "OK");
			JSONArray data = _objectEntryHelper.entriesToJson(entries, fields, nestedFields);
			_log.info("entriesToJson : "+data.toString());
			result.put("data",    data);
			result.put("total",   entries.size());
		} catch (Exception e) {
			_log.error("getExpertComptables error", e);
			result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", e.getMessage());
			result.put("data",    JSONFactoryUtil.createJSONObject());
		}
		return Response.ok(result.toString()).build();
	}



	// -------------------------------------------------------------------------
	// Helpers privés — conversion picklist key → display name
	// -------------------------------------------------------------------------

	private String _categorieKeyToName(String key) {
		if (key == null) return "";
		switch (key) {
			case "cabinet":       return "Cabinet";
			case "individuel":    return "Individuel";
			case "aSSOCIE":       return "ASSOCIE";
			case "cOORDINATEUR":  return "COORDINATEUR";
			case "aDJOINT":       return "ADJOINT";
			default:              return key.toUpperCase();
		}
	}

	private String _etatKeyToName(String key) {
		if (key == null) return "";
		switch (key) {
			case "aCTIF":     return "ACTIF";
			case "aTRAITER":  return "A_TRAITER";
			case "rEJET":     return "REJET";
			case "sUSPENDU":  return "SUSPENDU";
			default:          return key.toUpperCase();
		}
	}

	/**
	 * Construit un JSONObject de réponse à partir d'une ObjectEntry expert.
	 */
	private JSONObject _expertEntryToJson(ObjectEntry entry) {
		JSONObject json = JSONFactoryUtil.createJSONObject();
		if (entry == null) return json;

		json.put("id",             entry.getObjectEntryId());
		json.put("nom",            ObjectEntryHelper.getString(entry, "nom"));
		json.put("prenoms",        ObjectEntryHelper.getString(entry, "prenoms"));
		json.put("email",          ObjectEntryHelper.getString(entry, "email"));
		json.put("contact",        ObjectEntryHelper.getString(entry, "contact"));
		json.put("numeroOrdre",    ObjectEntryHelper.getString(entry, "numeroOrdre"));
		json.put("adressePostale", ObjectEntryHelper.getString(entry, "adressePostale"));
		json.put("nomCabinet",     ObjectEntryHelper.getString(entry, "nomCabinet"));
		json.put("numeroCabinet",  ObjectEntryHelper.getString(entry, "numeroCabinet"));
		json.put("raisonSociale",  ObjectEntryHelper.getString(entry, "raisonSociale"));
		json.put("anneeInscription",
			ObjectEntryHelper.getLong(entry, "anneeInscription"));
		json.put("signatureVisuelID",
			ObjectEntryHelper.getLong(entry, "signatureVisuelID"));

		String etatKey = ObjectEntryHelper.getString(entry, "etat");
		JSONObject etatJson = JSONFactoryUtil.createJSONObject();
		etatJson.put("key",  etatKey);
		etatJson.put("name", _etatKeyToName(etatKey));
		json.put("etat", etatJson);

		String categorieKey = ObjectEntryHelper.getString(entry, "categorie");
		JSONObject categorieJson = JSONFactoryUtil.createJSONObject();
		categorieJson.put("key",  categorieKey);
		categorieJson.put("name", _categorieKeyToName(categorieKey));
		json.put("categorie", categorieJson);

		return json;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private DocumentHelper _documentHelper;

	@Reference
	private UserHelper _userHelper;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	ObjectEntryLocalService _objectEntryLocalService;

	@Context
	private HttpServletRequest _httpServletRequest;

}
