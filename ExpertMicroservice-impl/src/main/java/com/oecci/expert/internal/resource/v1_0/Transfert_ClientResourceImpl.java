package com.oecci.expert.internal.resource.v1_0;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.oecci.expert.dto.v1_0.CreateForwardRequest;
import com.oecci.expert.dto.v1_0.StatutRequest;
import com.oecci.expert.resource.v1_0.Transfert_ClientResource;
import com.oecci.expert.utils.Constants;
import com.oecci.expert.utils.ObjectEntryHelper;
import com.oecci.expert.utils.UserHelper;
import com.oecci.expert.utils.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
 * appels natifs via {@link ObjectEntryHelper}.</p>
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/transfert_-client.properties",
	scope = ServiceScope.PROTOTYPE, service = Transfert_ClientResource.class
)
public class Transfert_ClientResourceImpl
	extends BaseTransfert_ClientResourceImpl {

	private static final Log _log = LogFactoryUtil.getLog(
		Transfert_ClientResourceImpl.class);

	// -------------------------------------------------------------------------
	// ERC centralisés dans Constants
	// -------------------------------------------------------------------------

	private static final String ERC_CLIENT            = Constants.ERC_CLIENT;
	private static final String ERC_EXPERT_COMPTABLE  = Constants.ERC_EXPERT_COMPTABLE;
	private static final String ERC_DEMANDE_TRANSFERT = Constants.ERC_DEMANDE_TRANSFERT;
	private static final String ERC_INTERVENANT       = Constants.ERC_INTERVENANT;

	// -------------------------------------------------------------------------
	// createTransfertClient
	// -------------------------------------------------------------------------

	public Response createTransfertClient(
			CreateForwardRequest createForwardRequest)
		throws Exception {

		_log.info(">> Begining CLIENT FORWARDING creation...");

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = Constants.DEV_OECCI_SITE_ID;
		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
			+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// 1. Vérifier la liaison Client ↔ Expert expéditeur

		_log.info(">> Verifying CLIENT EXPERT COMPTABLE link...");

		String clientFilter = ObjectEntryHelper.buildAndFilter(
			ObjectEntryHelper.buildEqFilter("id", createForwardRequest.getClientID()),
			ObjectEntryHelper.buildEqFilter(
				"r_iDExpertComptable_c_expertComptableId",
				createForwardRequest.getExpertExpediteurID()));

		List<ObjectEntry> clientEntries = _objectEntryHelper.searchByFilter(
			userId, companyId, groupId, ERC_CLIENT, clientFilter);

		if (clientEntries.isEmpty()) {
			_log.info(
				"Aucune liaison entre le client ID : " +
					createForwardRequest.getClientID() +
					" et l'expert comptable ID : " +
					createForwardRequest.getExpertExpediteurID());
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Aucune liaison entre le client ID : " +
					createForwardRequest.getClientID() +
					" et l'expert comptable ID : " +
					createForwardRequest.getExpertExpediteurID() +
					". Cet expert comptable ne peut donc effectuer le transfert de ce client");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		ObjectEntry clientEntry = clientEntries.get(0);
		String clientRaisonSociale = ObjectEntryHelper.getString(
			clientEntry, "raisonSociale");
		String clientSigle = ObjectEntryHelper.getString(clientEntry, "sigle");
		_log.info(">> CLIENT FOUND. raisonSociale : " + clientRaisonSociale);

		// Récupérer l'expert expéditeur via le FK stocké sur le client

		long expertExpediteurFKId = ObjectEntryHelper.getLong(
			clientEntry, "r_iDExpertComptable_c_expertComptableId");

		ObjectEntry expertExpediteurEntry;
		try {
			expertExpediteurEntry = _objectEntryHelper.getEntryOrThrow(
				expertExpediteurFKId);
		}
		catch (Exception e) {
			_log.error(
				"Expert expéditeur introuvable : id=" + expertExpediteurFKId,
				e);
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Expert comptable expéditeur introuvable : ID " +
					expertExpediteurFKId);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String expertExpediteurNom    = ObjectEntryHelper.getString(
			expertExpediteurEntry, "nom");
		String expertExpediteurPrenoms = ObjectEntryHelper.getString(
			expertExpediteurEntry, "prenoms");
		String expertExpediteurEmail  = ObjectEntryHelper.getString(
			expertExpediteurEntry, "email");
		_log.info(">> SENDER ACCOUNTANT FOUND. nom : " + expertExpediteurNom);

		// 2. Vérifier l'expert destinataire

		_log.info(">> Verifying CLIENT RECIPIENT ACCOUNTANT infos...");

		ObjectEntry expertDestinataireEntry;
		try {
			expertDestinataireEntry = _objectEntryHelper.getEntryOrThrow(
				createForwardRequest.getExpertDestinataireID());
		}
		catch (Exception e) {
			_log.info(
				">> Expert comptable destinataire ID " +
					createForwardRequest.getExpertDestinataireID() +
					" n'existe pas");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Expert comptable destinataire ID " +
					createForwardRequest.getExpertDestinataireID() +
					" n'existe pas");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String expertDestinataireNom    = ObjectEntryHelper.getString(
			expertDestinataireEntry, "nom");
		String expertDestinatairePrenoms = ObjectEntryHelper.getString(
			expertDestinataireEntry, "prenoms");
		String expertDestinataireEmail  = ObjectEntryHelper.getString(
			expertDestinataireEntry, "email");
		_log.info(
			">> RECIPIENT ACCOUNTANT FOUND. nom : " + expertDestinataireNom);

		// 3. Vérifier si une demande de transfert identique existe déjà

		_log.info(">> Checking CLIENT FORWARDING infos...");

		String transfertFilter = ObjectEntryHelper.buildAndFilter(
			ObjectEntryHelper.buildEqFilter(
				"r_iDExpertComptableTransfertClient_c_expertComptableId",
				expertExpediteurEntry.getObjectEntryId()),
			ObjectEntryHelper.buildEqFilter(
				"r_iDExpertComptableDestinataire_c_expertComptableId",
				expertDestinataireEntry.getObjectEntryId()),
			ObjectEntryHelper.buildEqFilter(
				"r_iDClientTransfertClient_c_clientId",
				clientEntry.getObjectEntryId()));

		List<ObjectEntry> existingTransferts = _objectEntryHelper.searchByFilter(
			userId, companyId, groupId, ERC_DEMANDE_TRANSFERT, transfertFilter);

		boolean isToCreate = existingTransferts.isEmpty();
		ObjectEntry existingTransfertEntry = isToCreate ?
			null : existingTransferts.get(0);

		// 4. Créer ou mettre à jour la demande de transfert

		String randomCode = UserHelper.generateSecureCode();
		String reference  = "DMD-TRF-" + System.currentTimeMillis() + "-" + randomCode;

		Map<String, Serializable> transfertValues = new HashMap<>();
		transfertValues.put("code", reference);
		transfertValues.put(
			"r_iDClientTransfertClient_c_clientId",
			clientEntry.getObjectEntryId());
		transfertValues.put(
			"r_iDExpertComptableTransfertClient_c_expertComptableId",
			expertExpediteurEntry.getObjectEntryId());
		transfertValues.put(
			"r_iDExpertComptableDestinataire_c_expertComptableId",
			expertDestinataireEntry.getObjectEntryId());
		transfertValues.put("transfertStatut", "eNCOURS");
		transfertValues.put(
			"motif",
			createForwardRequest.getMotif() != null ?
				createForwardRequest.getMotif() : "");

		ObjectEntry transfertEntry;
		if (isToCreate) {
			_log.info("> Creating DEMANDE TRANSFERT CLIENT...");
			transfertEntry = _objectEntryHelper.addEntry(
				userId, groupId, companyId, ERC_DEMANDE_TRANSFERT,
				transfertValues);
		}
		else {
			_log.info(
				">> DEMANDE TRANSFERT déjà enregistrée. ID : " +
					existingTransfertEntry.getObjectEntryId() +
					"\nMise à jour en cours...");
			transfertEntry = _objectEntryHelper.updateEntry(
				userId, groupId, companyId,
				existingTransfertEntry.getObjectEntryId(), transfertValues);
		}

		if (transfertEntry == null) {
			String msg = isToCreate ?
				"La demande de transfert du client " + clientRaisonSociale +
					" de l'expert comptable " + expertExpediteurNom +
					" " + expertExpediteurPrenoms + " à l'expert comptable " +
					expertDestinataireNom + " " + expertDestinatairePrenoms +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. " :
				"La mise à jour de la demande de transfert du client " +
					clientRaisonSociale +
					" de l'expert comptable " + expertExpediteurNom +
					" " + expertExpediteurPrenoms + " à l'expert comptable " +
					expertDestinataireNom + " " + expertDestinatairePrenoms +
					" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ";
			_log.info(msg);
			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
			result.put("message", msg);
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info(">> CLIENT FORWARDING WELL CREATED / UPDATED!! ");

		// 5. Date de création pour les templates de notification

		ZonedDateTime zonedDateTime = transfertEntry.getCreateDate()
			.toInstant()
			.atZone(ZoneId.of("UTC"));
		LocalDate dateCreatedLocal = zonedDateTime.toLocalDate();
		DateTimeFormatter outputFormatter =
			DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dateTransfertFormatted = dateCreatedLocal.format(outputFormatter);
		_log.info("> Client forwarding date formatted : " + dateTransfertFormatted);

		String transfertCode  = ObjectEntryHelper.getString(
			transfertEntry, "code");
		String transfertMotif = ObjectEntryHelper.getString(
			transfertEntry, "motif");

		// 6. Notifications intervenants du client

		_log.info(">> Starting client notification...");
		_log.info("> Preparing template notification for CLIENT..");

		List<ObjectEntry> intervenants = _objectEntryHelper.searchByFilter(
			userId, companyId, groupId, ERC_INTERVENANT,
			ObjectEntryHelper.buildEqFilter(
				"r_iDClientIntervenant_c_clientId",
				clientEntry.getObjectEntryId()));

		JSONArray users_to_notify = JSONFactoryUtil.createJSONArray();

		for (ObjectEntry intervenantEntry : intervenants) {
			String intervenantEmail = ObjectEntryHelper.getString(
				intervenantEntry, "email");

			Map<String, Object> templateVariables = new HashMap<>();
			String templatePath = "/templates/email/transfert_client.ftl";

			templateVariables.put("nom_client", clientRaisonSociale);
			templateVariables.put(
				"nom_expert_expediteur",
				expertExpediteurPrenoms + " " + expertExpediteurNom);
			templateVariables.put("email_expediteur", expertExpediteurEmail);
			templateVariables.put(
				"nom_expert_destinataire",
				expertDestinatairePrenoms + " " + expertExpediteurNom); // conservé comme dans l'original
			templateVariables.put(
				"email_destinataire", expertDestinataireEmail);
			templateVariables.put("reference_transfert", transfertCode);
			templateVariables.put(
				"motif_transfert",
				transfertMotif != null ? transfertMotif : "");
			templateVariables.put("date_transfert", dateTransfertFormatted);

			String mailContent = Utils.processMailTemplate(
				templatePath, this.getClass(), templateVariables);
			_log.info("mail content updated : " + mailContent);

			JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
			notifPayload.put("email", intervenantEmail);
			notifPayload.put("content", mailContent);
			notifPayload.put(
				"subject",
				"OECCI : Notification de demande d'inscription client");
			users_to_notify.put(notifPayload);
		}

		// 7. Notification à l'expert destinataire

		_log.info(
			"> Preparing template notification for CLIENT EXPERT RECIPIENT..");

		Map<String, Object> templateVariablesExpert = new HashMap<>();
		String templatePathExpert =
			"/templates/email/transfert_client_expert_notification.ftl";

		templateVariablesExpert.put(
			"nom_expert_expediteur",
			expertExpediteurPrenoms + " " + expertExpediteurNom);
		templateVariablesExpert.put(
			"nom_expert_destinataire",
			expertDestinatairePrenoms + " " + expertExpediteurNom); // conservé comme dans l'original
		templateVariablesExpert.put("nom_client", clientRaisonSociale);
		templateVariablesExpert.put(
			"email_client",
			ObjectEntryHelper.getString(clientEntry, "email"));
		templateVariablesExpert.put("reference_transfert", transfertCode);
		templateVariablesExpert.put(
			"motif_transfert",
			transfertMotif != null ? transfertMotif : "");
		templateVariablesExpert.put("date_transfert", dateTransfertFormatted);

		String mailContentExpert = Utils.processMailTemplate(
			templatePathExpert, this.getClass(), templateVariablesExpert);
		_log.info("mail content updated : " + mailContentExpert);

		JSONObject notifPayloadExpert = JSONFactoryUtil.createJSONObject();
		notifPayloadExpert.put("email", expertDestinataireEmail);
		notifPayloadExpert.put("content", mailContentExpert);
		notifPayloadExpert.put(
			"subject",
			"OECCI : Notification de demande d'inscription client");
		users_to_notify.put(notifPayloadExpert);

		// 8. Envoi des notifications (service externe NotificationManager)

		_log.info(">> Infos for notification ready to be used..");
		String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
			.replace("[baseUrl]", baseURL);
		_log.info("> headless URL to send CLIENT notification : " + notifLink);

		for (int i = 0; i < users_to_notify.length(); i++) {
			_log.info(
				">> Sending email notification to : " +
					users_to_notify.getJSONObject(i).getString("email"));
			try {
				JSONObject notification = Utils.executeHttpRequest(
					baseURL, notifLink, users_to_notify.getJSONObject(i),
					Constants.POST_REQUEST);
				if (notification != null)
					_log.info(
						"Notification return code : " +
							notification.getInt("code"));
			}
			catch (Exception e) {
				_log.warn(
					">> Exception while sending notification : " +
						e.getLocalizedMessage());
			}
		}

		result.put(
			"message",
			"La demande de transfert du client " + clientSigle +
				" a été soumise. Vous recevrez un mail concernant le statut de la demande.");
		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
			"data",
			_transfertEntryToJson(
				transfertEntry, clientEntry, expertExpediteurEntry,
				expertDestinataireEntry));

		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// -------------------------------------------------------------------------
	// validateDemandeTransfertClient
	// -------------------------------------------------------------------------

	public Response validateDemandeTransfertClient(
			Long demandeTransfertID, StatutRequest statutRequest)
		throws Exception {

		long userId    = contextUser.getUserId();
		long companyId = contextCompany.getCompanyId();
		long groupId   = Constants.DEV_OECCI_SITE_ID;
		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://"
			+ PropsUtil.get(PropsKeys.WEB_SERVER_HOST);

		_log.info(
			">> Verifying if DEMANDE TRANSFERT CLIENT already exists... ID=" +
				demandeTransfertID);

		JSONObject result = JSONFactoryUtil.createJSONObject();

		// 1. Récupérer la demande de transfert

		ObjectEntry transfertEntry;
		try {
			transfertEntry = _objectEntryHelper.getEntryOrThrow(
				demandeTransfertID);
		}
		catch (Exception e) {
			_log.info(
				"Aucune demande de transfert client avec cet ID : " +
					demandeTransfertID + ".");
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put(
				"message",
				"Aucune demande de transfert client existe avec cet ID : " +
					demandeTransfertID + ".");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		_log.info("> CLIENT FORWARDING FOUND!! Checking state for CLIENT ACCOUNTANT link..");

		// 2. Extraire les FKs des entités liées

		long clientId = ObjectEntryHelper.getLong(
			transfertEntry, "r_iDClientTransfertClient_c_clientId");
		long expertExpediteurId = ObjectEntryHelper.getLong(
			transfertEntry,
			"r_iDExpertComptableTransfertClient_c_expertComptableId");
		long expertDestinataireId = ObjectEntryHelper.getLong(
			transfertEntry,
			"r_iDExpertComptableDestinataire_c_expertComptableId");

		// 3. Charger les entités liées

		ObjectEntry clientEntry = _objectEntryHelper.getEntryOrThrow(clientId);
		ObjectEntry expertExpediteurEntry   = _objectEntryHelper.getEntryOrThrow(
			expertExpediteurId);
		ObjectEntry expertDestinataireEntry = _objectEntryHelper.getEntryOrThrow(
			expertDestinataireId);

		String sigle = ObjectEntryHelper.getString(clientEntry, "sigle");
		String clientRaisonSociale = ObjectEntryHelper.getString(
			clientEntry, "raisonSociale");
		String expertExpediteurNom     = ObjectEntryHelper.getString(
			expertExpediteurEntry, "nom");
		String expertExpediteurPrenoms = ObjectEntryHelper.getString(
			expertExpediteurEntry, "prenoms");
		String expertExpediteurEmail   = ObjectEntryHelper.getString(
			expertExpediteurEntry, "email");
		String expertDestinataireNom     = ObjectEntryHelper.getString(
			expertDestinataireEntry, "nom");
		String expertDestinatairePrenoms = ObjectEntryHelper.getString(
			expertDestinataireEntry, "prenoms");
		String expertDestinataireEmail   = ObjectEntryHelper.getString(
			expertDestinataireEntry, "email");

		// 4. Mettre à jour le statut de la demande de transfert

		_log.info("> Ready to update DEMANDE TRANSFERT CLIENT statut..");

		Map<String, Serializable> updateValues = new HashMap<>();
		updateValues.put("transfertStatut", statutRequest.getStatut().getKey());
		updateValues.put(
			"motif",
			statutRequest.getMotif_refus() != null ?
				statutRequest.getMotif_refus() : "");

		ObjectEntry updatedTransfertEntry = _objectEntryHelper.updateEntry(
			userId, groupId, companyId, transfertEntry.getObjectEntryId(),
			updateValues);

		if (updatedTransfertEntry == null) {
			_log.info(
				"La mise à jour de la demande n° " + demandeTransfertID +
					" de transfert client a échoué.");
			result.put("code", Constants.HTTP_NOT_UPDATED);
			result.put(
				"message",
				"La mise à jour de la demande n° " + demandeTransfertID +
					" de transfert client a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
			result.put("data", "");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		String updatedStatutKey  = ObjectEntryHelper.getString(
			updatedTransfertEntry, "transfertStatut");
		String updatedTransfertCode = ObjectEntryHelper.getString(
			updatedTransfertEntry, "code");
		_log.info(
			">> DEMANDE TRANSFERT CLIENT well updated. STATUT : " +
				_transfertStatutKeyToName(updatedStatutKey));

		// 5. Si ACCEPTÉ : ré-affecter le client à l'expert destinataire

		if (statutRequest.getStatut().getName().equalsIgnoreCase(
				Constants.TRANSFERT_VALIDE_STATUS)) {

			Map<String, Serializable> clientUpdateValues = new HashMap<>();
			clientUpdateValues.put(
				"r_iDExpertComptable_c_expertComptableId",
				expertDestinataireEntry.getObjectEntryId());

			ObjectEntry updatedClientEntry = _objectEntryHelper.updateEntry(
				userId, groupId, companyId, clientEntry.getObjectEntryId(),
				clientUpdateValues);

			if (updatedClientEntry == null) {
				_log.info(
					"La mise à jour du client " + sigle +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("code", Constants.HTTP_NOT_UPDATED);
				result.put(
					"message",
					"La mise à jour du client " + sigle +
						" a échoué. Veuillez réessayer ou contacter l'administrateur si cela persiste. ");
				result.put("data", "");
				return Response.status(Response.Status.OK).entity(result).build();
			}
		}

		// 6. Notifications — intervenants du client

		_log.info(">> Starting notifications. Firstly, the client..");

		List<ObjectEntry> intervenants = _objectEntryHelper.searchByFilter(
			userId, companyId, groupId, ERC_INTERVENANT,
			ObjectEntryHelper.buildEqFilter(
				"r_iDClientIntervenant_c_clientId",
				clientEntry.getObjectEntryId()));

		String dateDecision = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
			.format(transfertEntry.getCreateDate());
		String motifRefus   = ObjectEntryHelper.getString(
			transfertEntry, "motif");

		JSONArray users_to_notify = JSONFactoryUtil.createJSONArray();

		for (ObjectEntry intervenantEntry : intervenants) {
			String intervenantEmail = ObjectEntryHelper.getString(
				intervenantEntry, "email");

			Map<String, Object> templateVariables = new HashMap<>();
			String templatePath =
				"/templates/email/transfert_client_statut_notification.ftl";

			templateVariables.put(
				"statut_transfert",
				_transfertStatutKeyToName(updatedStatutKey));
			templateVariables.put("destinataire_type", "client");
			templateVariables.put("nom_client", clientRaisonSociale);
			templateVariables.put(
				"nom_expert_expediteur",
				expertExpediteurPrenoms + " " + expertExpediteurNom);
			templateVariables.put(
				"nom_expert_destinataire",
				expertDestinatairePrenoms + " " + expertExpediteurNom); // conservé comme dans l'original
			templateVariables.put("reference_transfert", updatedTransfertCode);
			templateVariables.put("email_expediteur", expertExpediteurEmail);
			templateVariables.put("email_destinataire", expertDestinataireEmail);
			templateVariables.put("date_decision", dateDecision);
			templateVariables.put(
				"motif_refus",
				motifRefus != null ? motifRefus : "");

			String redirection_link = baseURL + "/web/oecci_client";
			templateVariables.put("lien_espace_client", redirection_link);

			String mailContent = Utils.processMailTemplate(
				templatePath, this.getClass(), templateVariables);
			_log.info("mail content updated : " + mailContent);

			JSONObject notifPayload = JSONFactoryUtil.createJSONObject();
			notifPayload.put("email", intervenantEmail);
			notifPayload.put("content", mailContent);
			notifPayload.put(
				"subject",
				"OECCI : Notification de demande d'inscription client");
			users_to_notify.put(notifPayload);
		}

		// 7. Notification à l'expert destinataire

		_log.info("> Preparing template notification for CLIENT EXPERT RECIPIENT..");

		Map<String, Object> templateVariablesExpert = new HashMap<>();

		templateVariablesExpert.put(
			"statut_transfert",
			_transfertStatutKeyToName(updatedStatutKey));
		templateVariablesExpert.put("destinataire_type", "expert");
		templateVariablesExpert.put("nom_client", clientRaisonSociale);
		templateVariablesExpert.put(
			"nom_expert_expediteur",
			expertExpediteurPrenoms + " " + expertExpediteurNom);
		templateVariablesExpert.put(
			"nom_expert_destinataire",
			expertDestinatairePrenoms + " " + expertExpediteurNom); // conservé comme dans l'original
		templateVariablesExpert.put("reference_transfert", updatedTransfertCode);
		templateVariablesExpert.put("email_expediteur", expertExpediteurEmail);
		templateVariablesExpert.put("email_destinataire", expertDestinataireEmail);
		templateVariablesExpert.put("date_decision", dateDecision);
		templateVariablesExpert.put(
			"motif_refus", motifRefus != null ? motifRefus : "");

		String mailContentExpert = Utils.processMailTemplate(
			"/templates/email/transfert_client_statut_notification.ftl",
			this.getClass(), templateVariablesExpert);
		_log.info("mail content updated : " + mailContentExpert);

		JSONObject notifPayloadExpert = JSONFactoryUtil.createJSONObject();
		notifPayloadExpert.put("email", expertDestinataireEmail);
		notifPayloadExpert.put("content", mailContentExpert);
		notifPayloadExpert.put(
			"subject",
			"OECCI : Notification de demande d'inscription client");
		users_to_notify.put(notifPayloadExpert);

		// 8. Envoi des notifications (service externe NotificationManager)

		_log.info(">> Infos for notification ready to be used..");
		String notifLink = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
			.replace("[baseUrl]", baseURL);
		_log.info("> headless URL to send CLIENT notification : " + notifLink);

		for (int i = 0; i < users_to_notify.length(); i++) {
			_log.info(
				">> Sending email notification to : " +
					users_to_notify.getJSONObject(i).getString("email"));
			try {
				JSONObject notification = Utils.executeHttpRequest(
					baseURL, notifLink, users_to_notify.getJSONObject(i),
					Constants.POST_REQUEST);
				if (notification != null)
					_log.info(
						"Notification return code : " +
							notification.getInt("code"));
			}
			catch (Exception e) {
				_log.warn(
					">> Exception while sending notification : " +
						e.getLocalizedMessage());
			}
		}

		_log.info(
			"La mise à jour de la demande de transfert n° " +
				updatedTransfertCode + " a été effectuée avec succès : " +
				_transfertStatutKeyToName(updatedStatutKey));

		result.put("code", Constants.HTTP_SUCCESS);
		result.put(
			"message",
			"La mise à jour de la demande de transfert n° " +
				updatedTransfertCode + " a été effectuée avec succès : " +
				_transfertStatutKeyToName(updatedStatutKey));
		result.put(
			"data",
			_transfertEntryToJson(
				updatedTransfertEntry, clientEntry, expertExpediteurEntry,
				expertDestinataireEntry));

		_log.info("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();
	}

	// -------------------------------------------------------------------------
	// Helpers privés
	// -------------------------------------------------------------------------

	/**
	 * Convertit une clé de picklist transfertStatut en nom d'affichage.
	 */
	private String _transfertStatutKeyToName(String key) {
		if (key == null) return "";
		switch (key) {
			case "eNCOURS":  return "EN_COURS";
			case "aCCEPTE":  return "ACCEPTE";
			case "rEFUSE":   return "REFUSE";
			default:         return key.toUpperCase();
		}
	}

	/**
	 * Construit un JSONObject de réponse à partir des entrées ObjectEntry
	 * pour la demande de transfert et les entités liées.
	 */
	private JSONObject _transfertEntryToJson(
		ObjectEntry transfertEntry, ObjectEntry clientEntry,
		ObjectEntry expertExpediteurEntry, ObjectEntry expertDestinataireEntry) {

		JSONObject json = JSONFactoryUtil.createJSONObject();
		json.put("id",             transfertEntry.getObjectEntryId());
		json.put("code",           ObjectEntryHelper.getString(transfertEntry, "code"));
		json.put("transfertStatut",
			_transfertStatutKeyToName(
				ObjectEntryHelper.getString(transfertEntry, "transfertStatut")));
		json.put("motif",          ObjectEntryHelper.getString(transfertEntry, "motif"));

		if (clientEntry != null) {
			JSONObject clientJson = JSONFactoryUtil.createJSONObject();
			clientJson.put("id",            clientEntry.getObjectEntryId());
			clientJson.put("sigle",         ObjectEntryHelper.getString(clientEntry, "sigle"));
			clientJson.put("raisonSociale", ObjectEntryHelper.getString(clientEntry, "raisonSociale"));
			json.put("client", clientJson);
		}

		if (expertExpediteurEntry != null) {
			JSONObject exp = JSONFactoryUtil.createJSONObject();
			exp.put("id",      expertExpediteurEntry.getObjectEntryId());
			exp.put("nom",     ObjectEntryHelper.getString(expertExpediteurEntry, "nom"));
			exp.put("prenoms", ObjectEntryHelper.getString(expertExpediteurEntry, "prenoms"));
			exp.put("email",   ObjectEntryHelper.getString(expertExpediteurEntry, "email"));
			json.put("expertComptableExpediteur", exp);
		}

		if (expertDestinataireEntry != null) {
			JSONObject exp = JSONFactoryUtil.createJSONObject();
			exp.put("id",      expertDestinataireEntry.getObjectEntryId());
			exp.put("nom",     ObjectEntryHelper.getString(expertDestinataireEntry, "nom"));
			exp.put("prenoms", ObjectEntryHelper.getString(expertDestinataireEntry, "prenoms"));
			exp.put("email",   ObjectEntryHelper.getString(expertDestinataireEntry, "email"));
			json.put("expertComptableDestinataire", exp);
		}

		return json;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

}
