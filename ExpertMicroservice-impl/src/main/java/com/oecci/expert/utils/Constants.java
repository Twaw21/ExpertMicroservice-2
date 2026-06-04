package com.oecci.expert.utils;

import java.util.Date;

public class Constants {

	
	public static final Integer HTTP_ERROR_NOT_FOUND = 4004;
	public static final Integer HTTP_NOT_UPDATED = 6000;
	public static final Integer HTTP_SUCCESS = 2000;
	public static final Integer HTTP_INTERNAL_ERROR_CODE = 5000;
	public static final Integer HTTP_ACTION_NOT_ABLE = 4002;
	public static final Integer HTTP_RESOURCE_ALREADY_EXISTS = 2002;
	public static final Integer HTTP_QUANTITY_ERROR = 3000;
	public static final Integer HTTP_ERROR_DATA_MALFORMED = 4000;
	public static final Integer HTTP_RESOURCE_FORBIDEN = 3003;

	
	public static final String DEMANDE_APPROUVE_STATUT = "aPPROBATIONDEMANDEINSCPRIPTION";
	public static final String DEMANDE_REFUSE_STATUT = "rEJETDEMANDEINSCPRIPTION";
	public static final String VALIDATION_STATUT_ACTIF = "aCTIF";
	public static final String VALIDATION_STATUT_ATTENTE = "aTTENTEVALIDATION";
	public static final String VALIDATION_STATUT_SUSPENDU = "sUSPENDU";
	public static final String VALIDATION_STATUT_REJET = "rEJET";
	public static final String TRANSFERT_VALIDE_STATUS = "ACCEPTE";
	
	//DEV HEADLESS URLs
	public static final String LIFERAY_CREATE_COLLABADMIN_URL = "[baseUrl]/o/c/collabadmins/";
	public static final String LIFERAY_UPDATE_COLLABADMIN_URL = "[baseUrl]/o/c/collabadmins/[collabAdminId]";
	public static final String LIFERAY_GET_COLLABADMIN_BY_ID_URL = "[baseUrl]/o/c/collabadmins/[collabAdminId]";
	public static final String LIFERAY_GET_COLLABADMIN_BY_EMAIL_URL = "[baseUrl]/o/c/collabadmins/?filter=email eq [email]";
	
	public static final String LIFERAY_CREATE_COLLABMODERT_URL = "[baseUrl]/o/c/collabgestionnaires/";
	public static final String LIFERAY_UPDATE_COLLABMODERT_URL = "[baseUrl]/o/c/collabgestionnaires/[collabModerateurId]";
	public static final String LIFERAY_GET_COLLABMODERT_BY_ID_URL = "[baseUrl]/o/c/collabgestionnaires/[collabModerateurId]";
	public static final String LIFERAY_GET_COLLABMODERT_BY_EMAIL_URL = "[baseUrl]/o/c/collabgestionnaires/?filter=email eq [email]";
	
	public static final String LIFERAY_CREATE_COLLABASSIST_URL = "[baseUrl]/o/c/collabassistantses/";
	public static final String LIFERAY_UPDATE_COLLABASSIST_URL = "[baseUrl]/o/c/collabassistantses/[collabAssistantId]";
	public static final String LIFERAY_GET_COLLABASSIST_BY_ID_URL = "[baseUrl]/o/c/collabassistantses/[collabAssistantId]";
	public static final String LIFERAY_GET_COLLABASSIST_BY_EMAIL_URL = "[baseUrl]/o/c/collabassistantses/?filter=email eq [email]";

	public static final String LIFERAY_CREATE_COLLABORATOR_URL = "[baseUrl]/o/c/collaborateurs/";
	public static final String LIFERAY_GET_ALL_COLLABORATOR_URL = "[baseUrl]/o/c/collaborateurs/?nestedFields=r_iDCollaborateurAdmin_c_collabAdmin, r_iDCollaborateurGestionnaire_c_collabGestionnaire, r_iDCollaborateurAssistant_c_collabAssistants&pageSize=[pageSize]";
	public static final String LIFERAY_GET_ALL_MODRTANDASSIST_COLLABORATOR_URL = "[baseUrl]/o/c/collaborateurs/?filter=collaborateurType ne 'aDMIN'&nestedFields=r_iDCollaborateurGestionnaire_c_collabGestionnaire, r_iDCollaborateurAssistant_c_collabAssistants&pageSize=[pageSize]";
	public static final String LIFERAY_GET_ALL_ASSISTCOLLABO_URL = "[baseUrl]/o/c/collaborateurs/?filter=collaborateurType eq 'aSSISTANT'&nestedFields=r_iDCollaborateurAssistant_c_collabAssistants&pageSize=[pageSize]";
	public static final String LIFERAY_GET_COLLABORATOR_BY_ID_URL = "[baseUrl]/o/c/collaborateurs/[collaborateurID]";
	public static final String LIFERAY_GET_ADMIN_COLLABORATOR_BY_ID_URL = "[baseUrl]/o/c/collaborateurs/?filter=r_iDCollaborateurAdmin_c_collabAdminId eq [adminCollaboratorID]";
	public static final String LIFERAY_GET_MODERATOR_COLLABORATOR_BY_ID_URL = "[baseUrl]/o/c/collaborateurs/?filter=r_iDCollaborateurGestionnaire_c_collabGestionnaireId eq [modertorCollaboratorID]";

	public static final String LIFERAY_CREATE_EXPERT_ASSOC_URL = "[baseUrl]/o/c/expertassocies/";
	public static final String LIFERAY_UPDATE_EXPERT_ASSOC_URL = "[baseUrl]/o/c/expertassocies/[expertAssocieId]";
	public static final String LIFERAY_GET_EXPERT_ASSOC_BY_ID_URL = "[baseUrl]/o/c/expertassocies/[expertAssocieId]";
	public static final String LIFERAY_GET_EXPERT_ASSOC_BY_EMAIL_URL = "[baseUrl]/o/c/expertassocies/?filter=email eq [email]";

	public static final String LIFERAY_CREATE_EXPERT_COORD_URL = "[baseUrl]/o/c/expertcoordinateurs/";
	public static final String LIFERAY_UPDATE_EXPERT_COORD_URL = "[baseUrl]/o/c/expertcoordinateurs/[expertCoordinateurId]";
	public static final String LIFERAY_GET_EXPERT_COORD_BY_ID_URL = "[baseUrl]/o/c/expertcoordinateurs/[expertCoordinateurId]";
	public static final String LIFERAY_GET_EXPERT_COORD_BY_EMAIL_URL = "[baseUrl]/o/c/expertcoordinateurs/?filter=email eq [email]";
	
	public static final String LIFERAY_CREATE_EXPERT_ADJ_URL = "[baseUrl]/o/c/expertadjoints/";
	public static final String LIFERAY_UPDATE_EXPERT_ADJ_URL = "[baseUrl]/o/c/expertadjoints/[expertAdjointsId]";
	public static final String LIFERAY_GET_EXPERT_ADJ_BY_ID_URL = "[baseUrl]/o/c/expertadjoints/[expertAdjointsId]";
	public static final String LIFERAY_GET_EXPERT_ADJ_BY_EMAIL_URL = "[baseUrl]/o/c/expertadjoints/?filter=email eq [email]";
	
	public static final String LIFERAY_CREATE_EXPERT_COMPT_URL = "[baseUrl]/o/c/expertcomptables/"; 
	public static final String LIFERAY_UPDATE_EXPERT_COMPT_URL = "[baseUrl]/o/c/expertcomptables/[expertcomptableId]";
	public static final String LIFERAY_GET_EXPERT_COMPT_BY_ID_URL = "[baseUrl]/o/c/expertcomptables/[expertComptableID]"; 
	public static final String LIFERAY_GET_EXPERT_COMPT_BY_EMAIL_URL = "[baseUrl]/o/c/expertcomptables/?filter=numeroOrdre eq [numeroOrdre]"; 			

	public static final String LIFERAY_CHECK_CLIENT_EXPERT_LINK_URL = "[baseUrl]/o/c/clients/?filter=id eq [clientId] and r_iDExpertComptable_c_expertComptableId eq [expertComptableId]&nestedFields=r_iDExpertComptable_c_expertComptable, iDClientIntervenant";
	
	public static final String LIFERAY_GET_WALLET_BY_EXPERT_ID_URL = "[baseUrl]/o/c/wallets/?filter=r_iDExpertWallet_c_expertComptableId eq [expertComptableID]";
	public static final String LIFERAY_CREATE_WALLET_URL = "[baseUrl]/o/c/wallets/";
	public static final String LIFERAY_UPDATE_WALLET_URL = "[baseUrl]/o/c/wallets/[walletId]";
	
	
	public static final String LIFERAY_GET_CLIENT_BY_CPT_CONTRIB_URL = "[baseUrl]/o/c/clients/?filter=compteContribuable eq [compteContribuable]";
	public static final String LIFERAY_UPDATE_CLIENT_URL = "[baseUrl]/o/c/clients/[clientId]";
	public static final String LIFERAY_GET_CLIENT_BY_ID_URL = "[baseUrl]/o/c/clients/[clientID]";
	public static final String LIFERAY_GET_CLIENT_BY_ID_NESTED_URL = "[baseUrl]/o/c/clients/?nestedFields=iDClientIntervenant,iDClientD,iDClientInfoFiscale,iDClientDocument&filter=id eq [clientID]";
	
	public static final String LIFERAY_GET_DIRIGEANT_BY_EMAIL_URL = "[baseUrl]/o/c/dirigeants/?filter=email eq [email]";
	public static final String LIFERAY_GET_DIRIGEANT_BY_ID = "[baseUrl]/o/c/dirigeants/[dirigeantId]";
	//public static final String LIFERAY_GET_ALL_DIRIGEANTS_URL = "[baseUrl]/o/c/dirigeants/?nestedFields=r_iDUserDirigeant_user";
	public static final String LIFERAY_CREATE_DIRIGEANT_URL = "[baseUrl]/o/c/dirigeants/";
	public static final String LIFERAY_UPDATE_DIRIGEANT_URL = "[baseUrl]/o/c/dirigeants/[dirigeantId]";
	public static final String LIFERAY_CHECK_DIRIGEANT_CLIENT_LINK_URL = "[baseUrl]/o/c/dirigeants/[dirigeantId]/iDDirigeantC";
	public static final String LIFERAY_CREATE_LINK_DIRIGEANT_CLIENT_URL = "[baseUrl]/o/c/clientsdirigeants/";
	public static final String LIFERAY_UPDATE_LINK_DIRIGEANT_CLIENT_URL = "[baseUrl]/o/c/clientsdirigeants/[clientsDirigeantId]";
	
	public static final String LIFERAY_GET_INTERVENANT_BY_MAIL_URL = "[baseUrl]/o/c/intervenants/?filter=email eq [email]";
	public static final String LIFERAY_GET_INTERVENANT_BY_ID_URL = "[baseUrl]/o/c/intervenants/[intervenantId]";
	public static final String LIFERAY_CREATE_INTERVENANT_URL = "[baseUrl]/o/c/intervenants/";
	public static final String LIFERAY_UPDATE_INTERVENANT_URL = "[baseUrl]/o/c/intervenants/[intervenantId]";
	public static final String LIFERAY_CHECK_INTERVENANT_CLIENT_LINK_URL = "[baseUrl]/o/c/clients/[clientId]/iDClientIntervenant";
	public static final String LIFERAY_LINK_INTERVENANT_CLIENT_URL = "[baseUrl]/o/c/intervenants/[intervenantId]";
	public static final String LIFERAY_GET_INTERVENANT_BY_CLIENT_ID_URL = "[baseUrl]/o/c/clients/[clientID]/iDClientIntervenant";
	
	public static final String LIFERAY_GET_INFOS_FISCALE_BY_CLIENTID_URL = "[baseUrl]/o/c/infofiscales/?filter=r_iDClientInfoFiscale_c_clientId eq [clientID]";
	public static final String LIFERAY_CREATE_INFOS_FISCALE_CLIENT_URL = "[baseUrl]/o/c/infofiscales/";
	public static final String LIFERAY_UPDATE_INFOS_FISCALE_CLIENT_URL = "[baseUrl]/o/c/infofiscales/[infoFiscaleId]";

	public static final String LIFERAY_GET_DOCUMENT_CLIENT_BY_CLIENTID_URL = "[baseUrl]/o/c/documentclients/?filter=r_iDClientDocument_c_clientId eq [clientID] and titreDocument eq [titreDocument] and typeDocument eq [typeDocument]";
	public static final String LIFERAY_CREATE_DOCUMENT_CLIENT_URL = "[baseUrl]/o/c/documentclients/";
	public static final String LIFERAY_UPDATE_DOCUMENT_CLIENT_URL = "[baseUrl]/o/c/documentclients/[documentClientId]";
																														
	public static final String LIFERAY_GET_DEMANDE_INSCRIPTION_CLIENT_STATUT_URL = "[baseUrl]/o/c/demandeinscriptionclients/?filter=validationStatut eq '"+VALIDATION_STATUT_ACTIF+"' or validationStatut eq '"+VALIDATION_STATUT_ATTENTE+"' and r_client_c_clientId eq [clientId] and r_iDExpertComptableDemandeClient_c_expertComptableId eq [expertComptableId]&sort=dateCreated:desc";
	public static final String LIFERAY_GET_DEMANDE_INSCRIPTION_CLIENT_BY_ID_URL = "[baseUrl]/o/c/demandeinscriptionclients/[demandesInscriptionClientId]";
	public static final String LIFERAY_CREATE_DEMANDE_INSCRIPTION_CLIENT_URL = "[baseUrl]/o/c/demandeinscriptionclients/";
	public static final String LIFERAY_UPDATE_DEMANDE_INSCRIPTION_CLIENT_URL = "[baseUrl]/o/c/demandeinscriptionclients/[demandesInscriptionClientId]";

	public static final String LIFERAY_GET_SERVICE_IMPO_URL = "[baseUrl]/o/c/serviceimpositions/?filter=id eq [serviceImpoID] and r_directionImposition_c_directionImpositionId eq [directionImpoID]";
	public static final String LIFERAY_GET_SERVICE_IMPO_BY_ID_URL = "[baseUrl]/o/c/serviceimpositions/[serviceImpoID]";
			
	public static final String LIFERAY_GET_LIFERAY_USER_BY_EMAIL = "[baseUrl]/o/headless-admin-user/v1.0/user-accounts/by-email-address/[emailAddress]";
	public static final String LIFERAY_CREATE_LIFERAY_USER_URL = "[baseUrl]/o/headless-admin-user/v1.0/user-accounts";
	public static final String LIFERAY_UPDATE_LIFERAY_USER_URL = "[baseUrl]/o/headless-admin-user/v1.0/user-accounts/[userId]";

	public static final String LIFERAY_CREATE_VISA_DEMANDE_URL = "[baseUrl]/o/c/demandevisas/";
	public static final String LIFERAY_CREATE_VISA_EXERCICE_N_URL = "[baseUrl]/o/c/exercicens/";
	public static final String LIFERAY_GET_VISA_EXERCICE_N_BY_YEAR_URL = "[baseUrl]/o/c/exercicens/?filter=annee eq [year]&nestedFields=iDDemande";
	public static final String LIFERAY_CREATE_VISA_EXERCICE_N1_URL = "[baseUrl]/o/c/exercicen1s/";	

	public static final String LIFERAY_CREATE_DOCUMENT_VISA_URL = "[baseUrl]/o/c/documentvisas/";

	public static final String LIFERAY_GET_DEMANDES_VISA_BY_EXPCMPTID_URL = "[baseUrl]/o/c/expertcomptables?filter=r_iDUserExpertComptable_userId eq [liferayExpComptUserID]&nestedFields=iDExpertComptableDemandeClient";

	public static final String LIFERAY_GET_NOTIFICATIONS_EMAIL_CONTENT_URL = "[baseUrl]/o/c/emailnotifications/scopes/[oecciSiteScope]?filter=type eq [notificationType]";
	public static final String LIFERAY_SEND_NOTIFICATION_EMAIL_URL = "[baseUrl]/o/NotificationManagerMicroservice/v1.0/notification/email"; 

	public static final String LIFERAY_CHECK_VISA_SCALE_BY_CA_URL = "[baseUrl]/o/c/baremehonorairevisas/?filter=cAMin le [cAMin] and cAMax ge [cAMax]";
			
	public static final String LIFERAY_GET_DEMANDE_TRANSFERT = "[baseUrl]/o/c/demandetransfertclients/?filter=r_iDExpertComptableTransfertClient_c_expertComptableId eq  [expertExpediteurId] and r_iDExpertComptableDestinataire_c_expertComptableId eq [expertDestinataireId] and r_iDClientTransfertClient_c_clientId eq [clientId]";//&nestedFields=r_iDClientTransfertClient_c_client";
	public static final String LIFERAY_GET_TRANSFERT_CLIENT_BY_ID_URL = "[baseUrl]/o/c/demandetransfertclients/[demandeTransfertId]";
	public static final String LIFERAY_GET_TRANSFERT_NESTED_CLIENT_BY_ID_URL = "[baseUrl]/o/c/demandetransfertclients/[demandeTransfertId]?nestedFields=r_iDClientTransfertClient_c_client, iDClientIntervenant, r_iDExpertComptableTransfertClient_c_expertComptableId, r_iDExpertComptableDestinataire_c_expertComptableId&nestedFieldsDepth=2";
	public static final String LIFERAY_CREATE_TRANSFERT_CLIENT_URL = "[baseUrl]/o/c/demandetransfertclients/";
	public static final String LIFERAY_UPDATE_TRANSFERT_CLIENT_URL = "[baseUrl]/o/c/demandetransfertclients/[demandeTransfertId]";

	public static final String LIFERAY_UPLOAD_FILE_URL = "[baseUrl]/o/headless-delivery/v1.0/sites/[siteId]/documents";
	
	public static final String LIFERAY_GET_PAYMENT_BY_ID_URL = "[baseUrl]/o/c/paiements/[paymentId]";

	public static final String LIFERAY_CREATE_RELOADING_URL = "[baseUrl]/o/c/demanderechargements/";

	public static final String LIFERAY_CREATE_RELOADING_JOUNRAL_URL = "[baseUrl]/o/c/walletjournals/";
	
	public static final String MOVMENT_TYPE_IN_KEY = "iN";
	public static final String MOVMENT_TYPE_IN_NAME = "IN";
	public static final String MOVMENT_TYPE_OUT_KEY = "oUT";
	public static final String MOVMENT_TYPE_OUT_NAME = "OUT";

	public static final String OPERATION_TYPE_RELOAD_KEY = "rECHARGE";
	public static final String OPERATION_TYPE_RELOAD_NAME = "RECHARGE";
	public static final String OPERATION_TYPE_SIGNATURE_KEY = "sIGNATURE";
	public static final String OPERATION_TYPE_SIGNATURE_NAME = "SIGNATURE";

	//DEV TOKEN APP
	public static final String DEV_TOKEN_URL = "/o/oauth2/token";
    public static final String DEV_CLIENT_ID = "id-fc7b9f5f-9928-3e13-955c-3ed1abd6ac20";
    public static final String DEV_CLIENT_SECRET = "secret-a3f8bed3-a5e7-4775-1bb9-c82cd575b87f";
    
    //REQ TYPE
	public static final String GET_REQUEST = "GET";
	public static final String POST_REQUEST = "POST";
	public static final String PUT_REQUEST = "PUT";
	
//	public static final Long DEV_CLIENT_SITE_ID = 37105L;//local
	public static final Long DEV_CLIENT_SITE_ID = 35135L;//dev
	public static final Long DEV_ORDRE_EXPERT_SITE_ID = 56323L;//56321L local
	public static final Long DEV_EXPERT_SITE_ID = 35140L;//dev
	public static final Long DEV_OECCI_SITE_ID = 35144L;

	public static final Long DEV_ORDRE_EXPERT_ORGANIZATION = 56321L;//56321L local
	public static final long DEV_ORDRE_EXPERT_ADMIN_ORGANIZATION = 58489L;
	public static final long DEV_ORDRE_EXPERT_MODRT_ORGANIZATION = 58493L;
	public static final long DEV_ORDRE_EXPERT_ASSIST_ORGANIZATION = 58497L;

	public static final long DEV_ORDRE_EXPERT_ASSOCIE_ORGANIZATION = 56321L;

	public static final long DEV_EXPERT_ORGANIZATION = 35138L;
	public static final long DEV_EXPERT_ASSOCIE_ORGANIZATION = 72728L;
	public static final long DEV_EXPERT_COORDINATEUR_ORGANIZATION = 72732L;
	public static final long DEV_EXPERT_ADJOINT_ORGANIZATION = 72736L;

	public static final String CRYPTO_API_KEY = "f1e12a-d652-a757-b968-4784-3b062142";
	public static final String CRYPTO_API_SECRET = "4a76-b456-c170-a774-410b-b0a5-9c67-b20c";

	// -------------------------------------------------------------------------
	// ERC — External Reference Codes des Object Definitions
	// -------------------------------------------------------------------------

	// Collaborateurs
	public static final String ERC_COLLAB_ADMIN        = "ca26cd5f-7c8a-c8d7-4d45-617f132e356b";
	public static final String ERC_COLLAB_GESTIONNAIRE = "9f0bab9d-9160-bd3e-61bf-1d622c953018";
	public static final String ERC_COLLAB_ASSISTANTS   = "695ac1be-16cf-8874-1d0c-ba541d309d54";
	public static final String ERC_COLLABORATEUR       = "70c33888-c0df-d996-5960-bae13932ae3f";

	// Experts comptables
	public static final String ERC_EXPERT_ASSOC         = "d7b38213-77b2-88dc-c390-99ff67aac31e";
	public static final String ERC_EXPERT_COORDINATEUR  = "c3c8ceaf-8226-1d6f-1a91-7dc3156db031";
	public static final String ERC_EXPERT_ADJOINT       = "264c517b-521c-34c3-c924-35984327d01c";
	public static final String ERC_EXPERT_COMPTABLE     = "93550d3d-9a50-5ed8-6691-18f0d524ccbb";

	// Wallet & paiement
	public static final String ERC_WALLET               = "5f086e7a-5284-2e30-8d37-76ac04ee141e";
	public static final String ERC_PAIEMENT             = "fd5c12cb-94a1-587a-097a-713cb17668ce";
	public static final String ERC_DEMANDE_RECHARGEMENT = "7fe50100-78ed-8547-743f-15d871d57739";
	public static final String ERC_WALLET_JOURNAL       = "5f086e7a-5284-2e30-8d37-76ac04ee141e";

	// Transfert client
	public static final String ERC_CLIENT              = "aeae620e-6379-5e1e-0968-1eaf2e86ed11";
	public static final String ERC_DEMANDE_TRANSFERT   = "20bb2346-6a08-a0de-5a94-381081b738ce";
	public static final String ERC_INTERVENANT         = "e3f94abf-ccb7-f2f8-230f-dcbf2b7448d1";
	public static final String CRYPTO_KEY = "hIhqkP8CgjQ56GtV5rQ09rEp8pFezzQEgECTN5JkWEk=";
	
	// -------------------------------------------------------------------------
		// Comptes techniques
		// -------------------------------------------------------------------------

		/**
		 * Email du compte technique utilisé comme creatorId pour les opérations
		 * de service (ServiceContext, ObjectEntry, etc.) qui nécessitent un
		 * utilisateur Liferay mais ne sont pas déclenchées par un utilisateur réel.
		 *
		 * <p>Ce compte doit exister dans l'instance Liferay cible.
		 * Remplace l'ID hardcodé 207867 précédemment utilisé dans _doCreateClient.</p>
		 */
		//public static final String TECHNICAL_ADMIN_EMAIL = "arouna@diginfactory.com";
		public static final String TECHNICAL_ADMIN_EMAIL = "devteam@diginfactory.com";
		//public static final String TECHNICAL_ADMIN_EMAIL = "devteam@diginfactory.com";
		public static final String TECHNICAL_ADMIN_EMAIL_PREPROD = "devteam@diginfactory.com";


	public static final String ERC_DEMANDE_VISA              = "4380b202-f551-91be-0aa8-901a2a63037e";

	public static final String ERC_QUOTAT_VISA_CONFIGURATION = "b8a05d7a-09d1-5894-4548-e65eff228f72";
	public static final String ERC_EXPERT_VISA_COUNT         = "67d35ce2-4b70-07eb-ed7a-be3a139a2397";

	// Quotas & compteurs
	public static final String ERC_QUOTA_CONFIG      = "07ae94ed-4ce5-5b59-057c-7afd6cc21785";

	public static final String ERC_DEMANDE_EXTENSION_QUOTA_VISA = "e7987dff-0d98-e1f1-d9ee-7492f072f695";
	public static final String EXTENSION_QUOTA_ACCEPTE_STATUS = "ACCEPTE";
}
