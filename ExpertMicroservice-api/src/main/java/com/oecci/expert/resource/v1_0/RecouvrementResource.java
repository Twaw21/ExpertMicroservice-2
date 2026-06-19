/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.resource.v1_0;

import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import com.oecci.expert.dto.v1_0.UpdateRecouvrementRequest;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/ExpertMicroservice/v1.0
 *
 * @author OECCI / DiginFactory
 * @generated
 */
@Generated("")
@ProviderType
public interface RecouvrementResource {

    /**
     * POST /recouvrement/{recouvrementId}/valider
     * Valide un recouvrement : isRecouvre → true + upload preuve.
     */
    public Response updateRecouvrement(
            Long recouvrementId,
            UpdateRecouvrementRequest updateRecouvrementRequest)
        throws Exception;

    /**
     * GET /recouvrement/stats
     * Cards accueil : montantARestituer, expertsCrediteurs,
     * visasVepSignes, clientsConcernes.
     */
    public Response getRecouvrementStats() throws Exception;

    /**
     * GET /recouvrement/stats/global
     * Lecture comparative + statuts visa exclus + paiements non finalisés.
     */
    public Response getRecouvrementStatsGlobal() throws Exception;

    /**
     * GET /recouvrement/by-expert
     * Tableau récapitulatif : 1 ligne par expert avec montant, badge
     * isRecouvre et recouvrementId.
     */
    public Response getRecouvrementByExpert(
            Integer page, Integer pageSize,
            String expertFilter, String periodeDebut, String periodeFin)
        throws Exception;

    /**
     * GET /recouvrement/{recouvrementId}
     * Détail complet : résumé expert + liste des visas VEP + infos preuve.
     */
    public Response getRecouvrementDetail(Long recouvrementId)
        throws Exception;

    // -------------------------------------------------------------------------
    // Setters injectés par le framework Liferay Vulcan (ne pas modifier)
    // -------------------------------------------------------------------------

    public default void setContextAcceptLanguage(
        AcceptLanguage contextAcceptLanguage) {
    }

    public void setContextCompany(
        com.liferay.portal.kernel.model.Company contextCompany);

    public default void setContextHttpServletRequest(
        HttpServletRequest contextHttpServletRequest) {
    }

    public default void setContextHttpServletResponse(
        HttpServletResponse contextHttpServletResponse) {
    }

    public default void setContextUriInfo(UriInfo contextUriInfo) {
    }

    public void setContextUser(
        com.liferay.portal.kernel.model.User contextUser);

    public void setExpressionConvert(
        ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
            expressionConvert);

    public void setFilterParserProvider(
        FilterParserProvider filterParserProvider);

    public void setGroupLocalService(GroupLocalService groupLocalService);

    public void setResourceActionLocalService(
        ResourceActionLocalService resourceActionLocalService);

    public void setResourcePermissionLocalService(
        ResourcePermissionLocalService resourcePermissionLocalService);

    public void setRoleLocalService(RoleLocalService roleLocalService);

    public void setSortParserProvider(SortParserProvider sortParserProvider);

    public default com.liferay.portal.kernel.search.filter.Filter toFilter(
        String filterString) {

        return toFilter(
            filterString, Collections.<String, List<String>>emptyMap());
    }

    public default com.liferay.portal.kernel.search.filter.Filter toFilter(
        String filterString, Map<String, List<String>> multivaluedMap) {

        return null;
    }

    public default com.liferay.portal.kernel.search.Sort[] toSorts(
        String sortsString) {

        return new com.liferay.portal.kernel.search.Sort[0];
    }

    @ProviderType
    public interface Builder {

        public RecouvrementResource build();

        public Builder checkPermissions(boolean checkPermissions);

        public Builder httpServletRequest(
            HttpServletRequest httpServletRequest);

        public Builder httpServletResponse(
            HttpServletResponse httpServletResponse);

        public Builder preferredLocale(Locale preferredLocale);

        public Builder uriInfo(UriInfo uriInfo);

        public Builder user(
            com.liferay.portal.kernel.model.User user);
    }

    @ProviderType
    public interface Factory {

        public Builder create();
    }

}
// LIFERAY-REST-BUILDER-HASH:OECCI-RECOUVREMENT-2026