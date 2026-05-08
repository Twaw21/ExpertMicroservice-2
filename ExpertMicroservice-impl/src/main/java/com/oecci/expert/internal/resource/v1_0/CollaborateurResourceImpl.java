package com.oecci.expert.internal.resource.v1_0;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.oecci.expert.dto.v1_0.CreateCollaboRequest;
import com.oecci.expert.resource.v1_0.CollaborateurResource;
import com.oecci.expert.utils.Constants;
import com.oecci.expert.utils.ObjectEntryHelper;
import com.oecci.expert.utils.SecurityUtil;
import com.oecci.expert.utils.UserHelper;
import com.oecci.expert.utils.Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author OECCI / DiginFactory — Refactorisation sécurité 2026
 *
 * <p>Remplace tous les appels {@code Utils.executeHttpRequest()} par des
 * appels natifs via {@link ObjectEntryHelper} et {@link UserHelper}.</p>
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/collaborateur.properties",
	scope = ServiceScope.PROTOTYPE, service = CollaborateurResource.class
)
public class CollaborateurResourceImpl extends BaseCollaborateurResourceImpl {

	private static final Log _log = LogFactoryUtil.getLog(
		CollaborateurResourceImpl.class);

	// -------------------------------------------------------------------------
	// ERC centralisés dans Constants
	// -------------------------------------------------------------------------

	private static final String ERC_COLLAB_ADMIN        = Constants.ERC_COLLAB_ADMIN;
	private static final String ERC_COLLAB_GESTIONNAIRE = Constants.ERC_COLLAB_GESTIONNAIRE;
	private static final String ERC_COLLAB_ASSISTANTS   = Constants.ERC_COLLAB_ASSISTANTS;
	private static final String ERC_COLLABORATEUR       = Constants.ERC_COLLABORATEUR;

	// -------------------------------------------------------------------------
	// createCollabos
	// -------------------------------------------------------------------------

	public Response createCollabos(CreateCollaboRequest createCollaboRequest)
		throws Exception {

		_log.info(">> Begining COLLABO creation...");

		long userId    = contextUser.getUserId();
//		long companyId = contextCompany.getCompanyId();
		long companyId = PortalUtil.getDefaultCompanyId();;
//		long groupId   = Constants.DEV_OECCI_SITE_ID;
		long groupId = 0; //requis par Liferay pour les ObjectEntry de scope "company"

		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
			+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info("> Base URL : " + baseURL);
		_log.info("> Starting by verify what profil want to create collabo..");

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// 1. Vérifier les droits du créateur

		boolean isAbleToCreate = false;
		ObjectEntry collabCreatorEntry = null;
		String creatorLevel = createCollaboRequest.getCreatorLevel().name();

		if (creatorLevel.equalsIgnoreCase("ADMIN")) {
			_log.info(">> Creator is an ADMIN..");
			try {
				collabCreatorEntry = _objectEntryHelper.getEntryOrThrow(
					createCollaboRequest.getCreatorID());
				_log.info(">> Creator ADMIN FOUND. He's able to create any COLLABO");
				isAbleToCreate = true;
			}
			catch (Exception e) {
				_log.info(
					"> Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
				result.put(
					"message",
					"Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}
		else if (creatorLevel.equalsIgnoreCase("MODERATEUR")) {
			_log.info(">> Creator is a MODERATOR..");
			try {
				collabCreatorEntry = _objectEntryHelper.getEntryOrThrow(
					createCollaboRequest.getCreatorID());
				_log.info(">> Creator MODERATOR FOUND. He's able to create MODERATOR and ASSISTANT");
				_log.info(
					"> So checking who MODERATOR want to create : " +
						createCollaboRequest.getRole().getValue());
				isAbleToCreate =
					!createCollaboRequest.getRole().name().equals("ADMIN");
			}
			catch (Exception e) {
				_log.info(
					"Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
				result.put(
					"message",
					"Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}
		else if (creatorLevel.equalsIgnoreCase("ASSISTANT")) {
			_log.info(">> Creator is an ASSISTANT..");
			try {
				collabCreatorEntry = _objectEntryHelper.getEntryOrThrow(
					createCollaboRequest.getCreatorID());
				_log.info(">> Creator ASSISTANT FOUND. He's able to create ASSISTANT");
				_log.info(
					"> So checking who ASSISTANT want to create : " +
						createCollaboRequest.getRole().getValue());
				isAbleToCreate =
					!createCollaboRequest.getRole().name().equals("ADMIN") &&
					!createCollaboRequest.getRole().name().equals("MODERATOR");
			}
			catch (Exception e) {
				_log.info(
					"Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
				result.put(
					"message",
					"Aucun collaborateur n'existe avec cet ID : " +
						createCollaboRequest.getCreatorID() + ".");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}

		if (!isAbleToCreate) {
			String creatorNom = ObjectEntryHelper.getString(
				collabCreatorEntry, "nom");
			_log.info(
				"COLLABO " + creatorNom + " (" + creatorLevel + ") " +
					"n'est pas habilité à créer un " +
					createCollaboRequest.getRole().getValue() + ".");
			result.put("code", Constants.HTTP_ACTION_NOT_ABLE);
			result.put(
				"message",
				"COLLABO " + creatorNom + " (" + creatorLevel + ") " +
					"n'est pas habilité à créer un " +
					createCollaboRequest.getRole().getValue() + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String creatorNom = ObjectEntryHelper.getString(
			collabCreatorEntry, "nom");
		String creatorEmail = ObjectEntryHelper.getString(
			collabCreatorEntry, "email");
		String creatorPrenoms = ObjectEntryHelper.getString(
			collabCreatorEntry, "prenoms");

		_log.info(
			"COLLABO " + creatorNom + " (" + creatorLevel + ") " +
				"est habilité à créer un " +
				createCollaboRequest.getRole().getValue() + ".");
		_log.info(">> Starting COLLABO creation...");

		// 2. Créer ou mettre à jour l'utilisateur Liferay

		_log.info(
			">> Check if COLLABO already saved like liferay user.. email=" +
				createCollaboRequest.getEmail());

		User liferayUser = _userHelper.getUserByEmail(
			companyId, createCollaboRequest.getEmail());

		boolean isToCreate = (liferayUser == null);
		String randomPass  = null;

		if (isToCreate) {
			_log.info(
				"> User with email " + createCollaboRequest.getEmail() +
					" does not exist. Creating...");
			randomPass = UserHelper.generateSecurePassword();

			String screenName = UserHelper.buildScreenName(
				createCollaboRequest.getPrenoms(), createCollaboRequest.getNom());

			String jobTitle = "Collaborateur - " +
				createCollaboRequest.getRole().name();

			try {
				liferayUser = _userHelper.createUser(
					userId, companyId,
					createCollaboRequest.getEmail(),
					createCollaboRequest.getPrenoms(),
					createCollaboRequest.getNom(),
					screenName, jobTitle, randomPass);
			}
			catch (Exception e) {
				_log.error(
					">> L'inscription de " + createCollaboRequest.getRole().name() +
						" " + createCollaboRequest.getNom() +
						" comme Liferay user a échoué.", e);
				result.put(
					"message",
					"La mise à jour de " + createCollaboRequest.getRole().name() +
						" " + createCollaboRequest.getNom() +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}
		else {
			_log.info(
				">> User " + liferayUser.getFullName() +
					" déjà enregistré. Mise à jour de ses infos en cours...");
			try {
				liferayUser = _userHelper.updateUserContact(
					userId,
					liferayUser.getUserId(),
					createCollaboRequest.getPrenoms(),
					createCollaboRequest.getNom(),
					"Collaborateur - " + createCollaboRequest.getRole().name());
			}
			catch (Exception e) {
				_log.error(
					">> La mise à jour de " + createCollaboRequest.getRole().name() +
						" " + createCollaboRequest.getNom() +
						" comme Liferay user a échoué.", e);
				result.put(
					"message",
					"La mise à jour de " + createCollaboRequest.getRole().name() +
						" " + createCollaboRequest.getNom() +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}

		_log.info(
			">> Liferay " + createCollaboRequest.getRole().name() +
				" user well created/updated. userId=" + liferayUser.getUserId());

		// 3. Créer ou mettre à jour l'entité spécifique (CollabAdmin / Gestionnaire / Assistant)
		//    et affecter l'utilisateur à l'organisation correspondante

		String role = createCollaboRequest.getRole().name();

		String erc;
		String collaborateurTypeKey;
		long   orgId;

		if (role.equalsIgnoreCase("ADMIN")) {
			_log.info(">> Collabo to create is an ADMIN..");
			erc = ERC_COLLAB_ADMIN;
			collaborateurTypeKey = "aDMIN";
			orgId = Constants.DEV_ORDRE_EXPERT_ADMIN_ORGANIZATION;
		}
		else if (role.equalsIgnoreCase("MODERATEUR")) {
			_log.info(">> Collabo to create is a MODERATOR..");
			erc = ERC_COLLAB_GESTIONNAIRE;
			collaborateurTypeKey = "mODERATEUR";
			orgId = Constants.DEV_ORDRE_EXPERT_MODRT_ORGANIZATION;
		}
		else {
			_log.info(">> Collabo to create is an ASSISTANT..");
			erc = ERC_COLLAB_ASSISTANTS;
			collaborateurTypeKey = "aSSISTANT";
			orgId = Constants.DEV_ORDRE_EXPERT_ASSIST_ORGANIZATION;
		}

		// Vérifier si l'entité spécifique existe déjà (recherche par email)
		List<ObjectEntry> specificCollaboEntries =
			_objectEntryHelper.searchByFilter(
				userId, companyId, groupId, erc,
				ObjectEntryHelper.buildEqFilter(
					"email", createCollaboRequest.getEmail()));

		boolean isCollaboToCreate = specificCollaboEntries.isEmpty();
		ObjectEntry existingSpecificEntry =
			isCollaboToCreate ? null : specificCollaboEntries.get(0);

		Map<String, Serializable> specificValues = new HashMap<>();
		specificValues.put(
			"nom", createCollaboRequest.getNom() + " " +
				createCollaboRequest.getPrenoms());
		specificValues.put(
			"prenoms", createCollaboRequest.getNom() + " " +
				createCollaboRequest.getPrenoms());
		specificValues.put("email", createCollaboRequest.getEmail());
		specificValues.put("contact", createCollaboRequest.getContact());
		if (isCollaboToCreate && randomPass != null) {
			String pass_encrypted = SecurityUtil.encrypt(randomPass, Constants.CRYPTO_KEY);
			specificValues.put("mDPTemporaire", pass_encrypted);
		}

		ObjectEntry specificCollaboEntry;
		if (isCollaboToCreate) {
			_log.info(
				"COLLABO " + createCollaboRequest.getNom() + " n'existe pas. Creating...");
			specificCollaboEntry = _objectEntryHelper.addEntry(
				userId, groupId, companyId, erc, specificValues);
		}
		else {
			_log.info(
				"Collabo existe. Updating ID=" +
					existingSpecificEntry.getObjectEntryId());
			specificCollaboEntry = _objectEntryHelper.updateEntry(
				userId, groupId, companyId,
				existingSpecificEntry.getObjectEntryId(), specificValues);
		}

		if (specificCollaboEntry == null) {
			String msg = isCollaboToCreate ?
				"L'inscription de " + role + " " +
					createCollaboRequest.getNom() +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. " :
				"La mise à jour de " + role + " " +
					createCollaboRequest.getNom() +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ";
			_log.info(msg);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", msg);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		// Affecter l'utilisateur au site de l'ordre des experts et à l'organisation

		Group oecci_ordre_expert = GroupLocalServiceUtil.getGroup(
			Constants.DEV_ORDRE_EXPERT_SITE_ID);
		Organization collab_org = OrganizationLocalServiceUtil.getOrganization(
			orgId);
		_userHelper.linkUserToSiteAndOrganization(
			oecci_ordre_expert.getGroupId(), collab_org.getOrganizationId(),
			liferayUser.getUserId());

		// 4. Créer l'entrée Collaborateur (entité agrégée)

		_log.info(">> Beginning to save Collaborateur...");

		Map<String, Serializable> collaboValues = new HashMap<>();
		collaboValues.put("collaborateurType", collaborateurTypeKey);
		collaboValues.put("isActive", createCollaboRequest.getStatut());
		collaboValues.put(
			"r_iDUserCollabo_userId", liferayUser.getUserId());

		if (role.equalsIgnoreCase("ADMIN")) {
			collaboValues.put(
				"r_iDCollaborateurAdmin_c_collabAdminId",
				specificCollaboEntry.getObjectEntryId());
		}
		else if (role.equalsIgnoreCase("MODERATEUR")) {
			collaboValues.put(
				"r_iDCollaborateurGestionnaire_c_collabGestionnaireId",
				specificCollaboEntry.getObjectEntryId());
		}
		else {
			collaboValues.put(
				"r_iDCollaborateurAssistant_c_collabAssistantsId",
				specificCollaboEntry.getObjectEntryId());
		}

		_log.info("> Creating COLLABORATEUR entity...");
		ObjectEntry collaboEntry = _objectEntryHelper.addEntry(
			userId, groupId, companyId, ERC_COLLABORATEUR, collaboValues);

		if (collaboEntry == null) {
			String msg =
				"L'inscription de " + role + " " +
					createCollaboRequest.getNom() +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ";
			_log.info(msg);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", msg);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		// 5. Notification (service externe NotificationManager)

		String templatePath = "/templates/email/demande_enregistrement_collabo.ftl";
		_log.info(
			">> Sending email notification to : " +
				createCollaboRequest.getEmail());

		Map<String, Object> templateVariables = new HashMap<>();
		templateVariables.put(
			"nom_createur", creatorPrenoms + " " + creatorNom);
		templateVariables.put("email_createur", creatorEmail);
		templateVariables.put("role_createur", creatorLevel);
		templateVariables.put(
			"nom_prenom",
			createCollaboRequest.getNom() + " " +
				createCollaboRequest.getPrenoms());
		templateVariables.put("mot_de_passe", randomPass != null ? randomPass : "");
		templateVariables.put("role_demande", role);
		templateVariables.put("email_collaborateur", createCollaboRequest.getEmail());
		templateVariables.put("role_assigne", role);
		String redirection_link = baseURL + "/web/oecci_expert_ordre";
		templateVariables.put("lien_portail", redirection_link);
		String guide_link = baseURL + "/web/oecci_expert_ordre/guide.pdf";
		templateVariables.put("lien_guide", guide_link);

		String mailContent = Utils.processMailTemplate(
			templatePath, this.getClass(), templateVariables);
		_log.info("mail content updated : " + mailContent);

		JSONObject data_to_post = JSONFactoryUtil.createJSONObject();
		data_to_post.put("email", createCollaboRequest.getEmail());
		data_to_post.put("subject", "OECCI : Notification d'enregistrement de collaborateur");
		data_to_post.put("content", mailContent);

		try {
			String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
				.replace("[baseUrl]", baseURL);
			_log.info(
				"> headless URL to send COLLABO DEMANDE ENREGISTREMENT notification : " +
					notifLink);
			JSONObject notification = Utils.executeHttpRequest(
				baseURL, notifLink, data_to_post, Constants.POST_REQUEST);
			if (notification != null)
				_log.info(
					"Notification return code : " + notification.getInt("code"));
		}
		catch (Exception e) {
			_log.warn(
				">> Exception while sending notification : " +
					e.getLocalizedMessage());
		}

		result.put(
			"message",
			"L'inscription du nouveau collabo " +
				createCollaboRequest.getNom() +
				" a été effectuée avec succès. Vous recevrez un mail de confirmation.");
		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
			"data",
			_collaboEntryToJson(specificCollaboEntry, collaborateurTypeKey));

		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// -------------------------------------------------------------------------
	// getAllCollabos
	// -------------------------------------------------------------------------

	public Response getAllCollabos(Long collaboID, Integer pageSize)
		throws Exception {

		_log.info(">> Begining COLLABO Reading...");

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = Constants.DEV_OECCI_SITE_ID;

		_log.info("> Starting by verify what profil want to read collabo..");

		// 1. Récupérer le collaborateur lecteur

		ObjectEntry collaboReaderEntry;
		try {
			collaboReaderEntry = _objectEntryHelper.getEntryOrThrow(collaboID);
		}
		catch (Exception e) {
			_log.info("Aucun collaborateur n'existe avec cet ID : " + collaboID + ".");
			JSONObject result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Aucun collaborateur n'existe avec cet ID : " + collaboID + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String readerType = ObjectEntryHelper.getString(
			collaboReaderEntry, "collaborateurType");
		_log.info(
			"> COLLABO reader found. His role : " +
				_collaborateurTypeKeyToName(readerType));

		// 2. Rechercher les collaborateurs selon le rôle du lecteur

		String filter;
		if ("aDMIN".equals(readerType)) {
			_log.info(">> Reader is an ADMIN..");
			filter = ""; // tous les collaborateurs
		}
		else if ("mODERATEUR".equals(readerType)) {
			_log.info(">> Reader is a MODERATOR..");
			filter = "collaborateurType ne 'aDMIN'";
		}
		else {
			_log.info(">> Reader is an ASSISTANT..");
			filter = ObjectEntryHelper.buildEqFilter(
				"collaborateurType", "aSSISTANT");
		}

		List<ObjectEntry> collaboEntries = _objectEntryHelper.searchByFilter(
			userId, companyId, groupId, ERC_COLLABORATEUR, filter,
			0, pageSize != null ? pageSize : 100);

		if (collaboEntries.isEmpty()) {
			_log.info("> Aucuns collaborateurs trouvés.");
			JSONObject result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucuns collaborateurs trouvés.");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		// 3. Construire le tableau de résultats

		JSONArray itemsArray = JSONFactoryUtil.createJSONArray();
		for (ObjectEntry entry : collaboEntries) {
			String type = ObjectEntryHelper.getString(entry, "collaborateurType");
			itemsArray.put(_collaboEntryToJson(entry, type));
		}

		_log.info(">> Collabos found. Count=" + collaboEntries.size());

		JSONObject result = JSONFactoryUtil.createJSONObject();
		result.put("code", Constants.HTTP_SUCCESS);
		result.put("message", "Les collaborateurs trouvés.");
		result.put("data", itemsArray);

		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// -------------------------------------------------------------------------
	// Helpers privés
	// -------------------------------------------------------------------------

	private String _collaborateurTypeKeyToName(String key) {
		if (key == null) return "";
		switch (key) {
			case "aDMIN":      return "ADMIN";
			case "mODERATEUR": return "MODERATEUR";
			case "aSSISTANT":  return "ASSISTANT";
			default:           return key.toUpperCase();
		}
	}

	private JSONObject _collaboEntryToJson(
		ObjectEntry entry, String collaborateurTypeKey) {

		JSONObject json = JSONFactoryUtil.createJSONObject();
		if (entry == null) return json;

		json.put("id",     entry.getObjectEntryId());
		json.put("nom",    ObjectEntryHelper.getString(entry, "nom"));
		json.put("prenoms", ObjectEntryHelper.getString(entry, "prenoms"));
		json.put("email",  ObjectEntryHelper.getString(entry, "email"));
		json.put("contact", ObjectEntryHelper.getString(entry, "contact"));

		JSONObject typeJson = JSONFactoryUtil.createJSONObject();
		typeJson.put("key",  collaborateurTypeKey);
		typeJson.put("name", _collaborateurTypeKeyToName(collaborateurTypeKey));
		json.put("collaborateurType", typeJson);

		json.put("isActive", ObjectEntryHelper.getBoolean(entry, "isActive"));

		return json;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private UserHelper _userHelper;

}
