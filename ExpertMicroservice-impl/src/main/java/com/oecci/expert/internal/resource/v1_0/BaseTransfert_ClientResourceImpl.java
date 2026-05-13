/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.internal.resource.v1_0;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import com.oecci.expert.dto.v1_0.CreateForwardRequest;
import com.oecci.expert.dto.v1_0.StatutRequest;
import com.oecci.expert.resource.v1_0.Transfert_ClientResource;

import java.lang.reflect.Array;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@javax.ws.rs.Path("/v1.0")
public abstract class BaseTransfert_ClientResourceImpl
	implements Transfert_ClientResource {

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/ExpertMicroservice/v1.0/oecci/expert/transfert-client' -d $'{"clientID": ___, "expertDestinataireID": ___, "expertExpediteurID": ___, "motif": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Permet de transferer un client d'un expert comptable a un autre."
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(name = "Transfert_Client")
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path("/oecci/expert/transfert-client")
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Response createTransfertClient(
			CreateForwardRequest createForwardRequest)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/ExpertMicroservice/v1.0/oecci/expert/transfert-client/by-destinataire/{Expert_ComptableId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Demandes de transfert par expert destinataire"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "Expert_ComptableId"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "page"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "pageSize"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "filter"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "sort"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFieldsDepth"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(name = "Transfert_Client")
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
		"/oecci/expert/transfert-client/by-destinataire/{Expert_ComptableId}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Response getDemandesTransfertByDestinataire(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("Expert_ComptableId")
			Long Expert_ComptableId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("page")
			Integer page,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("pageSize")
			Integer pageSize,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("filter")
			String filter,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("sort")
			String sort,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("fields")
			String fields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFields")
			String nestedFields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFieldsDepth")
			Integer nestedFieldsDepth)
		throws Exception {

		return null;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/ExpertMicroservice/v1.0/oecci/expert/transfert-client/by-expediteur/{Expert_ComptableId}'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
			description = "Demandes de transfert par expert expediteur"
	)
	@io.swagger.v3.oas.annotations.Parameters(
			value = {
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
							name = "Expert_ComptableId"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "page"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "pageSize"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "filter"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "sort"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "fields"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "nestedFields"
					),
					@io.swagger.v3.oas.annotations.Parameter(
							in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
							name = "nestedFieldsDepth"
					)
			}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
			value = {
					@io.swagger.v3.oas.annotations.tags.Tag(name = "Transfert_Client")
			}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path(
			"/oecci/expert/transfert-client/by-expediteur/{Expert_ComptableId}"
	)
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Response getDemandesTransfertByExpediteur(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("Expert_ComptableId")
			Long Expert_ComptableId,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("page")
			Integer page,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("pageSize")
			Integer pageSize,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("filter")
			String filter,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("sort")
			String sort,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("fields")
			String fields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFields")
			String nestedFields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFieldsDepth")
			Integer nestedFieldsDepth)
		throws Exception {

		return null;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'GET' 'http://localhost:8080/o/ExpertMicroservice/v1.0/oecci/expert/transfert-client/list'  -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Demandes de transfert - endpoint generique"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "page"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "pageSize"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "filter"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "sort"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "fields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFields"
			),
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY,
				name = "nestedFieldsDepth"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(name = "Transfert_Client")
		}
	)
	@javax.ws.rs.GET
	@javax.ws.rs.Path("/oecci/expert/transfert-client/list")
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Response getDemandesTransfertClients(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("page")
			Integer page,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("pageSize")
			Integer pageSize,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("filter")
			String filter,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("sort")
			String sort,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("fields")
			String fields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFields")
			String nestedFields,
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.ws.rs.QueryParam("nestedFieldsDepth")
			Integer nestedFieldsDepth)
		throws Exception {

		return null;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -X 'POST' 'http://localhost:8080/o/ExpertMicroservice/v1.0/oecci/expert/transfert-client/validation/{demandeTransfertID}' -d $'{"motif_refus": ___, "statut": ___}' --header 'Content-Type: application/json' -u 'test@liferay.com:test'
	 */
	@io.swagger.v3.oas.annotations.Operation(
		description = "Valider ou invalider la demande de transfert d'un client"
	)
	@io.swagger.v3.oas.annotations.Parameters(
		value = {
			@io.swagger.v3.oas.annotations.Parameter(
				in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
				name = "demandeTransfertID"
			)
		}
	)
	@io.swagger.v3.oas.annotations.tags.Tags(
		value = {
			@io.swagger.v3.oas.annotations.tags.Tag(name = "Transfert_Client")
		}
	)
	@javax.ws.rs.Consumes({"application/json", "application/xml"})
	@javax.ws.rs.Path(
		"/oecci/expert/transfert-client/validation/{demandeTransfertID}"
	)
	@javax.ws.rs.POST
	@javax.ws.rs.Produces({"application/json", "application/xml"})
	@Override
	public Response validateDemandeTransfertClient(
			@io.swagger.v3.oas.annotations.Parameter(hidden = true)
			@javax.validation.constraints.NotNull
			@javax.ws.rs.PathParam("demandeTransfertID")
			Long demandeTransfertID,
			StatutRequest statutRequest)
		throws Exception {

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany) {

		this.contextCompany = contextCompany;
	}

	public void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {

		if ((contextHttpServletRequest != null) &&
			(contextHttpServletRequest.getAttribute(WebKeys.CTX) == null)) {

			contextHttpServletRequest.setAttribute(
				WebKeys.CTX, ServletContextPool.get(StringPool.BLANK));
		}

		this.contextHttpServletRequest = contextHttpServletRequest;
	}

	public void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {

		this.contextHttpServletResponse = contextHttpServletResponse;
	}

	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser) {

		this.contextUser = contextUser;
	}

	public void setExpressionConvert(
		ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
			expressionConvert) {

		this.expressionConvert = expressionConvert;
	}

	public void setFilterParserProvider(
		FilterParserProvider filterParserProvider) {

		this.filterParserProvider = filterParserProvider;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		this.resourceActionLocalService = resourceActionLocalService;
	}

	public void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		this.resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	public void setSortParserProvider(SortParserProvider sortParserProvider) {
		this.sortParserProvider = sortParserProvider;
	}

	protected Map<String, String> addAction(
		String actionName,
		com.liferay.portal.kernel.model.GroupedModel groupedModel,
		String methodName) {

		return ActionUtil.addAction(
			actionName, getClass(), groupedModel, methodName,
			contextScopeChecker, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName, Long ownerId,
		String permissionName, Long siteId) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			ownerId, permissionName, siteId, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, Long id, String methodName,
		ModelResourcePermission modelResourcePermission) {

		return ActionUtil.addAction(
			actionName, getClass(), id, methodName, contextScopeChecker,
			modelResourcePermission, contextUriInfo);
	}

	protected Map<String, String> addAction(
		String actionName, String methodName, String permissionName,
		Long siteId) {

		return addAction(
			actionName, siteId, methodName, null, permissionName, siteId);
	}

	protected <T, R, E extends Throwable> List<R> transform(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] transform(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] transformToArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
		Class<? extends R> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, E> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] transformToLongArray(
		Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction) {

		try {
			return unsafeTransformToLongArray(collection, unsafeFunction);
		}
		catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransform(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransform(collection, unsafeFunction);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransform(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransform(array, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> R[] unsafeTransformToArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
			Class<? extends R> clazz)
		throws E {

		return TransformUtil.unsafeTransformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R, E extends Throwable> List<R> unsafeTransformToList(
			T[] array, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return TransformUtil.unsafeTransformToList(array, unsafeFunction);
	}

	protected <T, R, E extends Throwable> long[] unsafeTransformToLongArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction)
		throws E {

		return (long[])_unsafeTransformToPrimitiveArray(
			collection, unsafeFunction, long[].class);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected com.liferay.portal.kernel.model.Company contextCompany;
	protected HttpServletRequest contextHttpServletRequest;
	protected HttpServletResponse contextHttpServletResponse;
	protected Object contextScopeChecker;
	protected UriInfo contextUriInfo;
	protected com.liferay.portal.kernel.model.User contextUser;
	protected ExpressionConvert<com.liferay.portal.kernel.search.filter.Filter>
		expressionConvert;
	protected FilterParserProvider filterParserProvider;
	protected GroupLocalService groupLocalService;
	protected ResourceActionLocalService resourceActionLocalService;
	protected ResourcePermissionLocalService resourcePermissionLocalService;
	protected RoleLocalService roleLocalService;
	protected SortParserProvider sortParserProvider;

	private <T, R, E extends Throwable> Object _unsafeTransformToPrimitiveArray(
			Collection<T> collection, UnsafeFunction<T, R, E> unsafeFunction,
			Class<?> clazz)
		throws E {

		List<R> list = unsafeTransform(collection, unsafeFunction);

		Object array = clazz.cast(
			Array.newInstance(clazz.getComponentType(), list.size()));

		for (int i = 0; i < list.size(); i++) {
			Array.set(array, i, list.get(i));
		}

		return array;
	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseTransfert_ClientResourceImpl.class);

}
// LIFERAY-REST-BUILDER-HASH:625365355