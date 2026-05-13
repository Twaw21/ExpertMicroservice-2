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
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.oecci.expert.dto.v1_0.CreateDmdExtQuotVisaRequest;
import com.oecci.expert.dto.v1_0.DataResult;
import com.oecci.expert.dto.v1_0.StatutRequest;
import com.oecci.expert.resource.v1_0.VisaResource;

import com.oecci.expert.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Lenovo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/visa.properties",
	scope = ServiceScope.PROTOTYPE, service = VisaResource.class
)
public class VisaResourceImpl extends BaseVisaResourceImpl {
	@Override
	public Response validateDemandeExtQuotaVisa(Long demandeExtId, StatutRequest statutRequest) throws Exception {
		return null;
	}

	@Override
	public Response canSignVisa(Long expertId) throws Exception {
		return null;
	}

	public Response createDemandeExtensionQuotaVisa(CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest)
			throws Exception {
		/*
//      pour acc�s limiter � admin
//		// R�cup�rer le checker de permission
//	    PermissionChecker permissionChecker =
//	        PermissionThreadLocal.getPermissionChecker();
//
//	    // V�rifier si c'est un admin Liferay
//	    if (!permissionChecker.isOmniadmin() &&
//	        !permissionChecker.isCompanyAdmin()) {
//	        return Response.status(Response.Status.FORBIDDEN)
//	                       .entity("Acc�s refus�")
//	                       .build();
//	    }

		JSONObject client, expert_comptable_expediteur = null, expert_comptable_destinataire = null, result;
		if (_httpServletRequest == null ) {
			_log.info(">> Object request does not exist. ");
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Objet request introuvable");
			result.put("data", "");
			System.out.println("> Returning response");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		User user = PortalUtil.getUser(_httpServletRequest);

		if (user == null) {
			_log.debug("Aucun utilisateur connect� - fin du traitement.");
			_log.info(">> Object request does not exist. ");
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucun utilisateur connect�");
			result.put("data", "");
			System.out.println("> Returning response");
			return Response.status(Response.Status.OK).entity(result).build();
		}
		_log.info("User found by PortalUtil.getUser(request) : "+user.getFullName());

//		 // R�cup�rer l'utilisateur courant
//        User c_user = _userLocalService.getUser(
//            PrincipalThreadLocal.getUserId()
//        );
//        _log.info("User found by PrincipalThreadLocal.getUserId() : "+c_user.getFullName());
//

		String csrfToken = AuthTokenUtil.getToken(
				_httpServletRequest
		);

		_log.info("xcrsf by AuthTokenUtil.getToken : "+csrfToken);
		_log.info("headers names : "+_httpServletRequest.getHeaderNames());

		long companyId = user.getCompanyId();

		String roles [] = {"Regular EXPERTS Shared Object", "Regular EXPERTS ASSOCIE Shared Object"};
		// V�rifier si l'utilisateur a un r�le sp�cifique
		boolean hasRole = RoleLocalServiceUtil.hasUserRoles(
				user.getUserId(),
				companyId,
				roles,  // Nom du r�le dans Liferay
				true                  // true = inclure les r�les h�rit�s
		);

		if (!hasRole) {
			return Response
					.status(Response.Status.FORBIDDEN)
					.entity("{\"message\": \"R�le insuffisant\"}")
					.build();
		}



		System.out.println(">> Begining VISA QUOTA EXTENSION creation...");
		String baseURL = PropsUtil.get(PropsKeys.WEB_SERVER_PROTOCOL) + "://" +
				PropsUtil.get(PropsKeys.WEB_SERVER_HOST);// + ":" +PropsUtil.get(PropsKeys.WEB_SERVER_HTTPS_PORT);


//		String baseURL = "http://localhost:8081";
		System.out.println("> Base URL : " + baseURL);

		String request_type = null;
		boolean isToCreate = false;

		System.out.println(">> verifying accountant making request");

		String link = Constants.LIFERAY_GET_EXPERT_COMPT_BY_ID_URL
				.replace("[baseUrl]", baseURL)
				.replace("[expertComptableID]", "" + createDmdExtQuotVisaRequest.getExpertID());
		System.out.println("> headless URL to check RECIPIENT ACCOUNTANT link : " + link);
		expert_comptable_destinataire = Utils.executeHttpRequest(baseURL, link, null, Constants.GET_REQUEST, csrfToken);
		if (expert_comptable_destinataire == null) {
			System.out.println(">> Expert comptable destinataire ID " + createDmdExtQuotVisaRequest.getExpertID() + " n'existe pas");
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Expert comptable destinataire ID " + createDmdExtQuotVisaRequest.getExpertID() + " n'existe pas");
			result.put("data", "");
			System.out.println("> Returning response");
			return Response.status(Response.Status.OK).entity(result).build();
		}

		System.out.println(">> SENDER ACCOUNTANT FOUND. ID : "+expert_comptable_destinataire.getString("nom"));

//		System.out.println(">> Checking CLIENT FORWARDING infos...");
//		link = Constants.LIFERAY_GET_DEMANDE_TRANSFERT
//				.replace("[baseUrl]", baseURL)
//				.replace("[expertExpediteurId]", "'" + expert_comptable_expediteur.getLong("id") + "'")
//				.replace("[expertDestinataireId]", "'" + createForwardRequest.getExpertDestinataireID() + "'")
//				.replace("[clientId]", "'" + client.getLong("id") + "'");//Ajouter le statut pour traiter ceux qui sont en attente de validation
//		System.out.println("> headless URL to check DEMANDE TRANSFERT : " + link);
//		JSONObject transferts_clients = Utils.executeHttpRequest(baseURL, link, null, Constants.GET_REQUEST);
//		if (transferts_clients.getJSONArray("items").length() == 0) {
//
//			link = Constants.LIFERAY_CREATE_TRANSFERT_CLIENT_URL.replace("[baseUrl]", baseURL);
//			System.out.println("> headless URL to create DEMANDE TRANSFERT CLIENT : " + link);
//			request_type = Constants.POST_REQUEST;
//			isToCreate = true;
//		} else {
//			transferts_clients = transferts_clients.getJSONArray("items").getJSONObject(0);
//			//expert_comptable_destinataire = client.getJSONObject("r_iDExpertComptableDestinataire_c_expertComptableId");
//			System.out.println(">> DEMANDE TRANSFERT d�j� enregistr�. ID : "+transferts_clients.getLong("id")+"\nMise � jour de ses infos en cours...");
//			link = Constants.LIFERAY_UPDATE_TRANSFERT_CLIENT_URL
//					.replace("[baseUrl]", baseURL)
//					.replace("[demandeTransfertId]", "" + transferts_clients.getLong("id"));
//			System.out.println("> headless URL to update DEMANDE TRANSFERT : " + link);
//			request_type = Constants.PUT_REQUEST;
//
//		}
//
//
//		JSONObject data_to_post = JSONFactoryUtil.createJSONObject();
//		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//		String randomCode = Utils.generateRandomCode();
//		String reference = "DMD-TRF-"+timestamp.getTime()+"-"+randomCode;//Calendar.getInstance().getTimeInMillis();
//		data_to_post.put("code", reference);
//
//		data_to_post.put("r_iDClientTransfertClient_c_clientId", client.getLong("id"));
//		data_to_post.put("r_iDExpertComptableTransfertClient_c_expertComptableId", expert_comptable_expediteur.getLong("id"));
//		data_to_post.put("r_iDExpertComptableDestinataire_c_expertComptableId", expert_comptable_destinataire.getLong("id"));
//
//		JSONObject validationStatut = JSONFactoryUtil.createJSONObject();
//		validationStatut.put("name", "EN_COURS");
//		validationStatut.put("key", "eNCOURS");
//		data_to_post.put("transfertStatut", validationStatut);
//		data_to_post.put("motif", createForwardRequest.getMotif() != null ? createForwardRequest.getMotif() : "");
//
//
//		transferts_clients = Utils.executeHttpRequest(baseURL, link, data_to_post, request_type);
//		if (transferts_clients == null) {
//			result = JSONFactoryUtil.createJSONObject();
//			String message = null;
//			if (isToCreate) {
//				message = "La demande de transfert du client "+client.getString("raisonSociale")
//				+ " de l'expert comptable "+expert_comptable_expediteur.getString("nom")+ " "+ expert_comptable_expediteur.getString("prenoms")
//				+ " � l'expert comptable "+expert_comptable_destinataire.getString("nom")+ " "+ expert_comptable_destinataire.getString("prenoms")
//				+ " a �chou�. Veuillez r�essayer ou contacter l'administrateur si cela persiste. ";
//				System.out.println(message);
//				result.put("message", message);
//			} else {
//				message = "La mise � jour de la demande de transfert du client "+client.getString("raisonSociale")
//				+ " de l'expert comptable "+expert_comptable_expediteur.getString("nom")+ " "+ expert_comptable_expediteur.getString("prenoms")
//				+ " � l'expert comptable "+expert_comptable_destinataire.getString("nom")+ " "+ expert_comptable_destinataire.getString("prenoms")
//				+ " a �chou�. Veuillez r�essayer ou contacter l'administrateur si cela persiste. ";
//				System.out.println(message);
//				result.put("message", message);
//			}
//			result.put("code", Constants.HTTP_INTERNAL_ERROR_CODE);
//			result.put("data", "");
//			System.out.println("> Returning response..");
//			return Response.status(Response.Status.OK).entity(result).build();
//		}
//		System.out.println(">> CLIENT FORWARDING WELL CREATED / UPDATED!! ");
//		System.out.println(">> Starting client  notification..");
//		System.out.println("> Preparing template notification for CLIENT..");
//
//
//		JSONArray users_to_notify = JSONFactoryUtil.createJSONArray();
//		JSONArray intervenants = client.getJSONArray("iDClientIntervenant");
//		Map<String, Object> templateVariables = new HashMap<>();
//		for (int j = 0; j < intervenants.length(); j++) {
//			JSONObject intervenant = intervenants.getJSONObject(j);
//			templateVariables = new HashMap<>();
//			String templatePath = "/templates/email/transfert_client.ftl";
//
//			templateVariables.put("nom_client", client.getString("raisonSociale"));
//			templateVariables.put("nom_expert_expediteur", expert_comptable_expediteur.getString("prenoms") +" "+ expert_comptable_expediteur.getString("nom"));
//			templateVariables.put("email_expediteur", expert_comptable_expediteur.getString("email"));
//			templateVariables.put("nom_expert_destinataire", expert_comptable_destinataire.getString("prenoms") +" "+ expert_comptable_expediteur.getString("nom"));
//			templateVariables.put("email_destinataire", expert_comptable_destinataire.getString("email"));
//			templateVariables.put("reference_transfert", transferts_clients.getString("code"));
//			templateVariables.put("motif_transfert", transferts_clients.getString("motif") != null ? transferts_clients.getString("motif") : "");
//
//			// For full date-time with timezone
//			DateTimeFormatter date_formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//			ZonedDateTime zonedDateTime = ZonedDateTime.parse(transferts_clients.getString("dateCreated"), date_formatter);
//			LocalDate date_created_forwarding = zonedDateTime.toLocalDate();
//			System.out.println("> date_created_forwarding : "+date_created_forwarding);
//			// Formatter la LocalDate en String
//	        DateTimeFormatter output_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//	        System.out.println("> Client forwarding date formatted : "+date_created_forwarding.format(output_formatter));
//
//	        templateVariables.put("date_transfert", date_created_forwarding.format(output_formatter));
//
//			//////templateVariables.put("statut_visa", transferts_clients.getJSONObject("transfertStatut").getString("name"));
//			String mail_content = Utils.processMailTemplate(templatePath, this.getClass(), templateVariables);
//
//			System.out.println("mail content updated : "+mail_content);
//
//			data_to_post = JSONFactoryUtil.createJSONObject();
//			data_to_post.put("email", intervenant.getString("email"));
//			data_to_post.put("content", mail_content);
//			data_to_post.put("subject", "OECCI : Notification de demande d'inscription client");
//
//			users_to_notify.put(data_to_post);
//
//		}
//
//		System.out.println("> Preparing template notification for CLIENT EXPERT RECIPIENT..");
//		templateVariables = new HashMap<>();
//		String templatePath = "/templates/email/transfert_client_expert_notification.ftl";
//
//		templateVariables.put("nom_expert_expediteur", expert_comptable_expediteur.getString("prenoms") +" "+ expert_comptable_expediteur.getString("nom"));
//		templateVariables.put("nom_expert_destinataire", expert_comptable_destinataire.getString("prenoms") +" "+ expert_comptable_expediteur.getString("nom"));
//		templateVariables.put("nom_client", client.getString("raisonSociale"));
//		templateVariables.put("email_client", client.getString("email"));
//		templateVariables.put("reference_transfert", transferts_clients.getString("code"));
//		templateVariables.put("motif_transfert", transferts_clients.getString("motif") != null ? transferts_clients.getString("motif") : "");
//
//		// For full date-time with timezone
//		DateTimeFormatter date_formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//		ZonedDateTime zonedDateTime = ZonedDateTime.parse(transferts_clients.getString("dateCreated"), date_formatter);
//		LocalDate date_created_forwarding = zonedDateTime.toLocalDate();
//		System.out.println("> date_created_forwarding : "+date_created_forwarding);
//
//     // Formatter la LocalDate en String
//        DateTimeFormatter output_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//        System.out.println("> Client forwarding date formatted : "+date_created_forwarding.format(output_formatter));
//	    templateVariables.put("date_transfert", date_created_forwarding.format(output_formatter));
//
//		//////templateVariables.put("statut_visa", transferts_clients.getJSONObject("transfertStatut").getString("name"));
//		String mail_content = Utils.processMailTemplate(templatePath, this.getClass(), templateVariables);
//
//		System.out.println("mail content updated : "+mail_content);
//
//		data_to_post = JSONFactoryUtil.createJSONObject();
//		data_to_post.put("email", expert_comptable_destinataire.getString("email"));
//		data_to_post.put("content", mail_content);
//		data_to_post.put("subject", "OECCI : Notification de demande d'inscription client");
//
//		users_to_notify.put(data_to_post);
//
//		System.out.println();
//		System.out.println(">> Infos for notification ready to be used..");
//		link = Constants.LIFERAY_SEND_NOTIFICATION_EMAIL_URL
//				.replace("[baseUrl]", baseURL);
//		System.out.println("> headless URL to send CLIENT notification : " + link);
//		for (int i = 0; i < users_to_notify.length(); i++) {
//			System.out.println();
//			System.out.println(">> Sending email notification to : " + users_to_notify.getJSONObject(i).getString("email"));
//			JSONObject notification = Utils.executeHttpRequest(baseURL, link, users_to_notify.getJSONObject(i), Constants.POST_REQUEST);
//			if(notification != null)
//				System.out.println("Notifiation return code : "+notification.getInt("code"));
////				if(notification.getInt("code") == Constants.HTTP_SUCCESS) {
////
////				}
//		}
		result = JSONFactoryUtil.createJSONObject();
		result.put("message", "");
		result.put("code", Constants.HTTP_SUCCESS);
//		result.put("data", transferts_clients);
		result.put("data", "");

		System.out.println("> Returning response");
		return Response.status(Response.Status.OK).entity(result).build();*/
		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
			VisaResourceImpl.class);

	// -------------------------------------------------------------------------
	// ERC constants
	// -------------------------------------------------------------------------

	private static final String ERC_DEMANDE_VISA              = Constants.ERC_DEMANDE_VISA;

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

			JSONArray data = objH.entriesToJson(entries, fields, nestedFields);
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

			JSONArray data = objH.entriesToJson(entries, fields, nestedFields);
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

			ObjectEntryHelper objH = new ObjectEntryHelper();
			Sort[] sorts = objH.parseSorts(sort);

			List<ObjectEntry> entries = _objectEntryHelper.searchByFilter(
					technicalUser.getUserId(), companyId, groupId,
					ERC_DEMANDE_VISA,
					filter,
					sorts,
					(long) (page != null ? page : -1),
					(long) (pageSize != null ? pageSize : -1));

			JSONArray data = objH.entriesToJson(entries, fields, nestedFields);
			_log.info("entriesToJson : " + data.toString());

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