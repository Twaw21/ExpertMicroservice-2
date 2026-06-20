package com.oecci.expert.internal.resource.v1_0;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.oecci.expert.dto.v1_0.UpdateRecouvrementRequest;
import com.oecci.expert.resource.v1_0.RecouvrementResource;
import com.oecci.expert.utils.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author OECCI / DiginFactory
 *
 * Gestion du recouvrement VEP : calcul, suivi et validation des
 * remboursements dus aux experts comptables pour les visas VEP signés.
 *
 * Règle métier : seuls les dossiers VEP (categorieClient = "vEP") dont le
 * visa est au statut VISE (visaStatut.key = "vISE") entrent dans le calcul.
 */
@Component(
    properties = "OSGI-INF/liferay/rest/v1_0/recouvrement.properties",
    scope = ServiceScope.PROTOTYPE, service = RecouvrementResource.class
)
public class RecouvrementResourceImpl extends BaseRecouvrementResourceImpl {

    private static final Log _log = LogFactoryUtil.getLog(
        RecouvrementResourceImpl.class);

    // ERC des objets Liferay concernés
    private static final String ERC_RECOUVREMENT     = Constants.ERC_RECOUVREMENT;
    private static final String ERC_DEMANDE_VISA     = Constants.ERC_DEMANDE_VISA;

    // Constantes métier



    // =========================================================================
    //  POST /recouvrement/{recouvrementId}/valider
    //  Valider un recouvrement (isRecouvre → true + upload preuve)
    // =========================================================================

    @Override
    public Response updateRecouvrement(
            Long recouvrementId,
            UpdateRecouvrementRequest updateRecouvrementRequest)
        throws Exception {

        _log.info(">> updateRecouvrement — id=" + recouvrementId);

        long userId    = contextUser.getUserId();
        long companyId = PortalUtil.getDefaultCompanyId();
        long groupId   = 0L;
        long dlGroupId = GroupLocalServiceUtil.getCompanyGroup(companyId).getGroupId();

        JSONObject result = JSONFactoryUtil.createJSONObject();

        User user = SecurityUtil.checkUser(_httpServletRequest, "updateRecouvrement");
        if (user == null) {
            return Response.status(Response.Status.OK)
                .entity(SecurityUtil.getResult()).build();
        }

        String[] roles = {
            "Regular COLLABO ADMIN Shared Object",
            "Regular COLLABO MODERATOR Shared Object"
        };
        if (!SecurityUtil.checkAccess(_httpServletRequest, user, roles)) {
            result.put("code",    Constants.HTTP_RESOURCE_FORBIDEN);
            result.put("message", "Vous n'avez pas les permissions nécessaires.");
            result.put("data",    "");
            return Response.status(Response.Status.FORBIDDEN).entity(result).build();
        }

        User techUser;
        try {
            techUser = _userHelper.getTechnicalUser(companyId);
        } catch (Exception e) {
            _log.error("[updateRecouvrement] Compte technique introuvable.", e);
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "Compte technique manquant. Contacter l'administrateur.");
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }
        long techUserId = techUser.getUserId();

        // 1. Récupérer la ligne de recouvrement
        ObjectEntry recouvrEntry;
        try {
            recouvrEntry = _objectEntryHelper.getEntryOrThrow(recouvrementId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
            result.put("message", "Aucun recouvrement trouvé avec l'ID : " + recouvrementId);
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }

        if (ObjectEntryHelper.getBoolean(recouvrEntry, "isRecouvre")) {
            result.put("code",    Constants.HTTP_ACTION_NOT_ABLE);
            result.put("message", "Ce recouvrement a déjà été effectué.");
            result.put("data",    _recouvrEntryToJson(recouvrEntry));
            return Response.status(Response.Status.OK).entity(result).build();
        }

        // 2. Upload de la preuve
        long fileEntryId = 0L;

        if (updateRecouvrementRequest.getDocument() != null
                && updateRecouvrementRequest.getDocument().getDocumentContent() != null) {

            byte[] decodedBytes = Base64.getDecoder().decode(
                updateRecouvrementRequest.getDocument().getDocumentContent());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String rawName = updateRecouvrementRequest.getDocument().getDocumentName() != null
                ? updateRecouvrementRequest.getDocument().getDocumentName() : ".pdf";
            String fileName = DocumentHelper.sanitizeFileName(
                "RECOUV_" + timestamp.getTime() + "_" + rawName);
            File fileToUpload = new File(System.getProperty("java.io.tmpdir"), fileName);

            try {
                FileUtil.write(fileToUpload, decodedBytes);
                long folderId = _documentHelper.getOrCreateFolder(
                    userId, dlGroupId,
                    DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
                    Constants.OECCI_RECOUV_FOLDER);
                fileEntryId = _documentHelper.uploadFile(
                    userId, dlGroupId, folderId, fileName, fileToUpload);
                _log.info(">> Preuve uploadée, fileEntryId=" + fileEntryId);
            } catch (Exception e) {
                _log.error("[updateRecouvrement] Upload échoué : " + e.getMessage(), e);
                result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
                result.put("message", "L'enregistrement de la preuve de recouvrement a échoué. "
                    + "Veuillez réessayer ou contacter l'administrateur si cela persiste.");
                result.put("data", "");
                return Response.status(Response.Status.OK).entity(result).build();
            } finally {
                if (fileToUpload.exists()) fileToUpload.delete();
            }
        } else {
            _log.warn("[updateRecouvrement] Aucune preuve fournie.");
        }

        // 3. Mettre à jour la ligne
        Map<String, Serializable> updateValues = new HashMap<>();
        updateValues.put("isRecouvre", true);
        if (fileEntryId > 0) {
            updateValues.put("docPreuve", fileEntryId);
            JSONObject preuveJson = JSONFactoryUtil.createJSONObject();
            preuveJson.put("fileEntryId", fileEntryId);
            preuveJson.put("fileName",
                updateRecouvrementRequest.getDocument() != null
                    ? updateRecouvrementRequest.getDocument().getDocumentName() : "");
            updateValues.put("preuve", preuveJson.toString());
        }

        ObjectEntry updated = _objectEntryHelper.updateEntry(
            techUserId, groupId, companyId,
            recouvrEntry.getObjectEntryId(), updateValues);

        if (updated == null) {
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "La validation du recouvrement a échoué. "
                + "Veuillez réessayer ou contacter l'administrateur.");
            result.put("data", "");
            return Response.status(Response.Status.OK).entity(result).build();
        }

        result.put("code",    Constants.HTTP_SUCCESS);
        result.put("message", "Le recouvrement a été validé avec succès.");
        result.put("data",    _recouvrEntryToJson(updated));
        return Response.status(Response.Status.OK).entity(result).build();
    }


    // =========================================================================
    //  GET /recouvrement/stats
    //  Cards accueil : montant total, experts créditeurs, visas VEP, clients
    // =========================================================================

    @Override
    public Response getRecouvrementStats() throws Exception {

        _log.info(">> getRecouvrementStats");

        long companyId = PortalUtil.getDefaultCompanyId();
        long groupId   = 0L;
        JSONObject result = JSONFactoryUtil.createJSONObject();

        User user = SecurityUtil.checkUser(_httpServletRequest, "getRecouvrementStats");
        if (user == null) {
            return Response.status(Response.Status.OK)
                .entity(SecurityUtil.getResult()).build();
        }

        String[] roles = {
            "Regular COLLABO ADMIN Shared Object",
            "Regular COLLABO MODERATOR Shared Object",
            "Regular COLLABO ASSISTANT Shared Object"
        };
        if (!SecurityUtil.checkAccess(_httpServletRequest, user, roles)) {
            result.put("code",    Constants.HTTP_RESOURCE_FORBIDEN);
            result.put("message", "Vous n'avez pas les permissions nécessaires.");
            result.put("data",    "");
            return Response.status(Response.Status.FORBIDDEN).entity(result).build();
        }

        User techUser;
        try {
            techUser = _userHelper.getTechnicalUser(companyId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "Compte technique manquant.");
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }
        long techUserId = techUser.getUserId();

        // Lignes de recouvrement ouvertes → montant total + experts distincts
        List<ObjectEntry> openLines = _objectEntryHelper.searchByFilter(
            techUserId, companyId, groupId, ERC_RECOUVREMENT,
            "isRecouvre eq false");

        long totalMontantARestituer = 0L;
        Set<Long> expertIds = new HashSet<>();
        for (ObjectEntry line : openLines) {
            totalMontantARestituer += ObjectEntryHelper.getLong(line, "montant");
            long expertId = ObjectEntryHelper.getLong(
                line, "r_expertRecouvre_c_expertComptableId");
            if (expertId > 0) expertIds.add(expertId);
        }

        // Visas VEP VISÉS → comptage visas et clients distincts
        List<ObjectEntry> visasVise = _objectEntryHelper.searchByFilter(
            techUserId, companyId, groupId, ERC_DEMANDE_VISA,
            "visaStatut eq '" + Constants.VISA_STATUT_VISE_KEY + "'");

        long visasVepVise = 0L;
        Set<Long> clientsVep = new HashSet<>();
        for (ObjectEntry visa : visasVise) {
            long clientId = ObjectEntryHelper.getLong(
                visa, "r_iDClientDemandeVisa_c_clientId");
            ObjectEntry clientEntry = _objectEntryHelper.getEntry(clientId);
            if (clientEntry != null && Constants.CLIENT_CATEGORIE_VEP.equalsIgnoreCase(
                    ObjectEntryHelper.getString(clientEntry, "categorieClient"))) {
                visasVepVise++;
                clientsVep.add(clientId);
            }
        }

        JSONObject data = JSONFactoryUtil.createJSONObject();
        data.put("montantARestituer",  totalMontantARestituer);
        data.put("expertsCrediteurs",  expertIds.size());
        data.put("visasVepSignes",     visasVepVise);
        data.put("clientsConcernes",   clientsVep.size());

        result.put("code",    Constants.HTTP_SUCCESS);
        result.put("message", "Statistiques de recouvrement récupérées.");
        result.put("data",    data);
        return Response.status(Response.Status.OK).entity(result).build();
    }


    // =========================================================================
    //  GET /recouvrement/stats/global
    //  Lecture comparative (capture 2) + badges d'exclusion
    // =========================================================================

    @Override
    public Response getRecouvrementStatsGlobal() throws Exception {

        _log.info(">> getRecouvrementStatsGlobal");

        long companyId = PortalUtil.getDefaultCompanyId();
        long groupId   = 0L;
        JSONObject result = JSONFactoryUtil.createJSONObject();

        User user = SecurityUtil.checkUser(_httpServletRequest, "getRecouvrementStatsGlobal");
        if (user == null) {
            return Response.status(Response.Status.OK)
                .entity(SecurityUtil.getResult()).build();
        }

        String[] roles = {
            "Regular COLLABO ADMIN Shared Object",
            "Regular COLLABO MODERATOR Shared Object",
            "Regular COLLABO ASSISTANT Shared Object"
        };
        if (!SecurityUtil.checkAccess(_httpServletRequest, user, roles)) {
            result.put("code",    Constants.HTTP_RESOURCE_FORBIDEN);
            result.put("message", "Vous n'avez pas les permissions nécessaires.");
            result.put("data",    "");
            return Response.status(Response.Status.FORBIDDEN).entity(result).build();
        }

        User techUser;
        try {
            techUser = _userHelper.getTechnicalUser(companyId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "Compte technique manquant.");
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }
        long techUserId = techUser.getUserId();

        // Tous les visas — on filtre ceux dont le client est VEP
        List<ObjectEntry> allVisas = _objectEntryHelper.searchByFilter(
            techUserId, companyId, groupId, ERC_DEMANDE_VISA, null);

        long totalPaiementsVepMontant = 0L;  // somme TTC de tous visas VEP paiement positif
        long totalPaiementsVepCount   = 0L;  // nb dossiers VEP paiement positif
        long retenuMontant            = 0L;  // somme TTC visas VEP VISE paiement valide
        long retenuCount              = 0L;
        long excluMontant             = 0L;  // = total - retenu
        long excluCount               = 0L;

        long statutAttenteVisa  = 0L;
        long statutValide       = 0L;
        long statutAModifier    = 0L;
        long paiementProcessing = 0L;
        long paiementInitiated  = 0L;

        for (ObjectEntry visa : allVisas) {
            long clientId = ObjectEntryHelper.getLong(
                visa, "r_iDClientDemandeVisa_c_clientId");
            ObjectEntry clientEntry = _objectEntryHelper.getEntry(clientId);
            if (clientEntry == null) continue;

            if (!Constants.CLIENT_CATEGORIE_VEP.equalsIgnoreCase(
                    ObjectEntryHelper.getString(clientEntry, "categorieClient"))) continue;

            long paiementId = ObjectEntryHelper.getLong(visa, "r_iDPayment_c_paiementId");
            ObjectEntry paiementEntry = (paiementId > 0)
                ? _objectEntryHelper.getEntry(paiementId) : null;
            if (paiementEntry == null) continue;

            long montantTTC  = ObjectEntryHelper.getLong(paiementEntry, "montantTTC");
            String payStatus = ObjectEntryHelper.getString(paiementEntry, "paystatus");
            boolean paiementPositif = "SUCCESS".equalsIgnoreCase(payStatus) && montantTTC > 0;

            if (!paiementPositif) {
                if ("PROCESSING".equalsIgnoreCase(payStatus)) paiementProcessing++;
                else if ("INITIATED".equalsIgnoreCase(payStatus)) paiementInitiated++;
                continue;
            }

            totalPaiementsVepMontant += montantTTC;
            totalPaiementsVepCount++;

            String visaStatutKey = ObjectEntryHelper.getString(visa, "visaStatut");
            if (Constants.VISA_STATUT_VISE_KEY.equalsIgnoreCase(visaStatutKey)) {
                retenuMontant += montantTTC;
                retenuCount++;
            } else {
                excluMontant += montantTTC;
                excluCount++;
                if ("aTTENTEVISA".equalsIgnoreCase(visaStatutKey)
                        || "aTRAITER".equalsIgnoreCase(visaStatutKey)) {
                    statutAttenteVisa++;
                } else if ("vALIDE".equalsIgnoreCase(visaStatutKey)) {
                    statutValide++;
                } else if ("aMODIFIER".equalsIgnoreCase(visaStatutKey)) {
                    statutAModifier++;
                }
            }
        }

        JSONObject data = JSONFactoryUtil.createJSONObject();

        // ── Lecture comparative
        JSONObject lectureComparative = JSONFactoryUtil.createJSONObject();
        lectureComparative.put("totalPaiementsVepMontant", totalPaiementsVepMontant);
        lectureComparative.put("totalPaiementsVepCount",   totalPaiementsVepCount);
        lectureComparative.put("retenuMontant",            retenuMontant);
        lectureComparative.put("retenuCount",              retenuCount);
        lectureComparative.put("excluMontant",             excluMontant);
        lectureComparative.put("excluCount",               excluCount);
        data.put("lectureComparative", lectureComparative);

        // ── Statuts visa exclus
        JSONObject statutsExclus = JSONFactoryUtil.createJSONObject();
        statutsExclus.put("attenteVisa", statutAttenteVisa);
        statutsExclus.put("valide",      statutValide);
        statutsExclus.put("aModifier",   statutAModifier);
        data.put("statutsVisaExclus", statutsExclus);

        // ── Paiements non finalisés
        JSONObject paiementsNonFinalises = JSONFactoryUtil.createJSONObject();
        paiementsNonFinalises.put("processing", paiementProcessing);
        paiementsNonFinalises.put("initiated",  paiementInitiated);
        data.put("paiementsNonFinalises", paiementsNonFinalises);

        result.put("code",    Constants.HTTP_SUCCESS);
        result.put("message", "Statistiques globales de recouvrement récupérées.");
        result.put("data",    data);
        return Response.status(Response.Status.OK).entity(result).build();
    }


    // =========================================================================
    //  GET /recouvrement/by-expert
    //  Tableau récapitulatif : 1 ligne par expert avec total montant + badges
    // =========================================================================

    @Override
    public Response getRecouvrementByExpert(
            Integer page, Integer pageSize,
            String expertFilter, String periodeDebut, String periodeFin)
        throws Exception {

        _log.info(">> getRecouvrementByExpert");

        long companyId = PortalUtil.getDefaultCompanyId();
        long groupId   = 0L;
        JSONObject result = JSONFactoryUtil.createJSONObject();

        User user = SecurityUtil.checkUser(_httpServletRequest, "getRecouvrementByExpert");
        if (user == null) {
            return Response.status(Response.Status.OK)
                .entity(SecurityUtil.getResult()).build();
        }

        String[] roles = {
            "Regular COLLABO ADMIN Shared Object",
            "Regular COLLABO MODERATOR Shared Object",
            "Regular COLLABO ASSISTANT Shared Object"
        };
        if (!SecurityUtil.checkAccess(_httpServletRequest, user, roles)) {
            result.put("code",    Constants.HTTP_RESOURCE_FORBIDEN);
            result.put("message", "Vous n'avez pas les permissions nécessaires.");
            result.put("data",    "");
            return Response.status(Response.Status.FORBIDDEN).entity(result).build();
        }

        User techUser;
        try {
            techUser = _userHelper.getTechnicalUser(companyId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "Compte technique manquant.");
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }
        long techUserId = techUser.getUserId();

        // Toutes les lignes de recouvrement
        List<ObjectEntry> allLines = _objectEntryHelper.searchByFilter(
            techUserId, companyId, groupId, ERC_RECOUVREMENT, null,
            0, pageSize != null ? pageSize : 100);

        // Grouper par expert
        Map<Long, List<ObjectEntry>> byExpert = new LinkedHashMap<>();
        for (ObjectEntry line : allLines) {
            long expertId = ObjectEntryHelper.getLong(
                line, "r_expertRecouvre_c_expertComptableId");
            byExpert.computeIfAbsent(expertId, k -> new ArrayList<>()).add(line);
        }

        JSONArray itemsArray = JSONFactoryUtil.createJSONArray();

        for (Map.Entry<Long, List<ObjectEntry>> entry : byExpert.entrySet()) {
            long expertId = entry.getKey();
            List<ObjectEntry> lines = entry.getValue();

            // Infos expert
            ObjectEntry expertEntry = _objectEntryHelper.getEntry(expertId);
            String expertNom  = "";
            String nomCabinet = "";
            if (expertEntry != null) {
                expertNom  = ObjectEntryHelper.getString(expertEntry, "prenoms")
                    + " " + ObjectEntryHelper.getString(expertEntry, "nom");
                nomCabinet = ObjectEntryHelper.getString(expertEntry, "nomCabinet");
            }
            String expertLabel = (!nomCabinet.isEmpty()) ? nomCabinet : expertNom;

            // Calcul du montant total et de l'état
            long totalMontant = 0L;
            boolean hasOpenLine = false;
            ObjectEntry openLine = null;
            for (ObjectEntry line : lines) {
                totalMontant += ObjectEntryHelper.getLong(line, "montant");
                if (!ObjectEntryHelper.getBoolean(line, "isRecouvre")) {
                    hasOpenLine = true;
                    if (openLine == null) openLine = line;
                }
            }

            // Visas VEP VISÉS de cet expert
            List<ObjectEntry> expertVisas = _objectEntryHelper.searchByFilter(
                techUserId, companyId, groupId, ERC_DEMANDE_VISA,
                "r_iDExpert_c_expertComptableId eq '" + expertId + "'"
                + " and visaStatut eq '" + Constants.VISA_STATUT_VISE_KEY + "'");

            long paiementsVepCount = 0L;
            Set<Long> clientsSet = new HashSet<>();
            String dernierVisaDate = "";

            for (ObjectEntry visa : expertVisas) {
                long clientId = ObjectEntryHelper.getLong(
                    visa, "r_iDClientDemandeVisa_c_clientId");
                ObjectEntry clientEntry = _objectEntryHelper.getEntry(clientId);
                if (clientEntry != null && Constants.CLIENT_CATEGORIE_VEP.equalsIgnoreCase(
                        ObjectEntryHelper.getString(clientEntry, "categorieClient"))) {
                    paiementsVepCount++;
                    clientsSet.add(clientId);
                    if (visa.getCreateDate() != null) {
                        dernierVisaDate = visa.getCreateDate().toString();
                    }
                }
            }

            JSONObject expertJson = JSONFactoryUtil.createJSONObject();
            expertJson.put("expertComptableId",  expertId);
            expertJson.put("expertLabel",        expertLabel);
            expertJson.put("paiementsVep",       paiementsVepCount);
            expertJson.put("clientsConcernes",   clientsSet.size());
            expertJson.put("montantARestituer",  totalMontant);
            expertJson.put("dernierVisaSigne",   dernierVisaDate);
            // Badge clé : isRecouvre = true uniquement si TOUTES les lignes sont recouvrées
            expertJson.put("isRecouvre",         !hasOpenLine);
            expertJson.put("recouvrementId",
                openLine != null ? openLine.getObjectEntryId() : -1);

            itemsArray.put(expertJson);
        }

        result.put("code",    Constants.HTTP_SUCCESS);
        result.put("message", "Liste des recouvrements par expert récupérée.");
        result.put("data",    itemsArray);
        result.put("total",   byExpert.size());
        return Response.status(Response.Status.OK).entity(result).build();
    }


    // =========================================================================
    //  GET /recouvrement/{recouvrementId}
    //  Détail d'un recouvrement + résumé expert + liste visas VEP
    // =========================================================================

    @Override
    public Response getRecouvrementDetail(Long recouvrementId) throws Exception {

        _log.info(">> getRecouvrementDetail — id=" + recouvrementId);

        long companyId = PortalUtil.getDefaultCompanyId();
        long groupId   = 0L;
        JSONObject result = JSONFactoryUtil.createJSONObject();

        User user = SecurityUtil.checkUser(_httpServletRequest, "getRecouvrementDetail");
        if (user == null) {
            return Response.status(Response.Status.OK)
                .entity(SecurityUtil.getResult()).build();
        }

        String[] roles = {
            "Regular COLLABO ADMIN Shared Object",
            "Regular COLLABO MODERATOR Shared Object",
            "Regular COLLABO ASSISTANT Shared Object"
        };
        if (!SecurityUtil.checkAccess(_httpServletRequest, user, roles)) {
            result.put("code",    Constants.HTTP_RESOURCE_FORBIDEN);
            result.put("message", "Vous n'avez pas les permissions nécessaires.");
            result.put("data",    "");
            return Response.status(Response.Status.FORBIDDEN).entity(result).build();
        }

        User techUser;
        try {
            techUser = _userHelper.getTechnicalUser(companyId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_INTERNAL_ERROR_CODE);
            result.put("message", "Compte technique manquant.");
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }
        long techUserId = techUser.getUserId();

        // 1. Ligne de recouvrement
        ObjectEntry recouvrEntry;
        try {
            recouvrEntry = _objectEntryHelper.getEntryOrThrow(recouvrementId);
        } catch (Exception e) {
            result.put("code",    Constants.HTTP_ERROR_NOT_FOUND);
            result.put("message", "Aucun recouvrement trouvé avec l'ID : " + recouvrementId);
            result.put("data",    "");
            return Response.status(Response.Status.OK).entity(result).build();
        }

        long expertId = ObjectEntryHelper.getLong(
            recouvrEntry, "r_expertRecouvre_c_expertComptableId");

        // 2. Infos expert
        ObjectEntry expertEntry = _objectEntryHelper.getEntry(expertId);
        String expertNom  = "";
        String nomCabinet = "";
        if (expertEntry != null) {
            expertNom  = ObjectEntryHelper.getString(expertEntry, "prenoms")
                + " " + ObjectEntryHelper.getString(expertEntry, "nom");
            nomCabinet = ObjectEntryHelper.getString(expertEntry, "nomCabinet");
        }

        // 3. Visas VEP VISÉS de cet expert avec leurs détails paiement
        List<ObjectEntry> expertVisas = _objectEntryHelper.searchByFilter(
            techUserId, companyId, groupId, ERC_DEMANDE_VISA,
            "r_iDExpert_c_expertComptableId eq '" + expertId + "'"
            + " and visaStatut eq '" + Constants.VISA_STATUT_VISE_KEY + "'");

        JSONArray visasArray = JSONFactoryUtil.createJSONArray();
        long totalPaiements  = 0L;
        Set<Long> clientsSet = new HashSet<>();
        String dernierVisaDate = "";

        for (ObjectEntry visa : expertVisas) {
            long clientId = ObjectEntryHelper.getLong(
                visa, "r_iDClientDemandeVisa_c_clientId");
            ObjectEntry clientEntry = _objectEntryHelper.getEntry(clientId);
            if (clientEntry == null) continue;
            if (!Constants.CLIENT_CATEGORIE_VEP.equalsIgnoreCase(
                    ObjectEntryHelper.getString(clientEntry, "categorieClient"))) continue;

            clientsSet.add(clientId);
            totalPaiements++;

            long paiementId = ObjectEntryHelper.getLong(visa, "r_iDPayment_c_paiementId");
            ObjectEntry paiementEntry = (paiementId > 0)
                ? _objectEntryHelper.getEntry(paiementId) : null;
            long montantTTC  = (paiementEntry != null)
                ? ObjectEntryHelper.getLong(paiementEntry, "montantTTC") : 0L;
            String payStatus = (paiementEntry != null)
                ? ObjectEntryHelper.getString(paiementEntry, "paystatus") : "";

            String visaDate = visa.getCreateDate() != null
                ? visa.getCreateDate().toString() : "";
            if (!visaDate.isEmpty()) dernierVisaDate = visaDate;

            JSONObject visaJson = JSONFactoryUtil.createJSONObject();
            visaJson.put("numeroDemande",
                ObjectEntryHelper.getString(visa, "numeroDemande"));
            visaJson.put("client",
                ObjectEntryHelper.getString(clientEntry, "raisonSociale"));
            visaJson.put("paiementStatut",  payStatus);
            visaJson.put("visaStatut",      "Visé");
            visaJson.put("montantTTC",      montantTTC);
            visaJson.put("date",            visaDate);

            visasArray.put(visaJson);
        }

        // 4. Résumé expert (pour l'en-tête du panneau de détail)
        JSONObject expertJson = JSONFactoryUtil.createJSONObject();
        expertJson.put("expertComptableId", expertId);
        expertJson.put("expertLabel",       (!nomCabinet.isEmpty()) ? nomCabinet : expertNom);
        expertJson.put("paiementsVep",      totalPaiements);
        expertJson.put("clients",           clientsSet.size());

        // 5. Assembler la réponse
        JSONObject recouvrJson = _recouvrEntryToJson(recouvrEntry);
        recouvrJson.put("expert",           expertJson);
        recouvrJson.put("dernierVisaSigne", dernierVisaDate);
        recouvrJson.put("visas",            visasArray);

        result.put("code",    Constants.HTTP_SUCCESS);
        result.put("message", "Détails du recouvrement récupérés avec succès.");
        result.put("data",    recouvrJson);
        return Response.status(Response.Status.OK).entity(result).build();
    }


    // =========================================================================
    //  Helper privé
    // =========================================================================

    private JSONObject _recouvrEntryToJson(ObjectEntry entry) {
        JSONObject json = JSONFactoryUtil.createJSONObject();
        if (entry == null) return json;

        json.put("id",                  entry.getObjectEntryId());
        json.put("montant",             ObjectEntryHelper.getLong(entry, "montant"));
        json.put("isRecouvre",          ObjectEntryHelper.getBoolean(entry, "isRecouvre"));
        json.put("docPreuve",           ObjectEntryHelper.getLong(entry, "docPreuve"));
        json.put("preuve",              ObjectEntryHelper.getString(entry, "preuve"));
        json.put("expertComptableId",
            ObjectEntryHelper.getLong(entry, "r_expertRecouvre_c_expertComptableId"));
        json.put("dateCreated",
            entry.getCreateDate() != null ? entry.getCreateDate().toString() : "");
        json.put("dateModified",
            entry.getModifiedDate() != null ? entry.getModifiedDate().toString() : "");

        return json;
    }


    // =========================================================================
    //  Références OSGi
    // =========================================================================

    @Reference
    private ObjectEntryHelper _objectEntryHelper;

    @Reference
    private UserHelper _userHelper;

    @Reference
    private DocumentHelper _documentHelper;

    @Context
    private HttpServletRequest _httpServletRequest;

}