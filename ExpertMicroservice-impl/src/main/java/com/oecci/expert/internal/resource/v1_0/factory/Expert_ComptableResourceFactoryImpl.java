/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.internal.resource.v1_0.factory;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;

import com.oecci.expert.internal.security.permission.LiberalPermissionChecker;
import com.oecci.expert.resource.v1_0.Expert_ComptableResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Lenovo
 * @generated
 */
@Component(
	property = "resource.locator.key=/ExpertMicroservice/v1.0/Expert_Comptable",
	service = Expert_ComptableResource.Factory.class
)
@Generated("")
public class Expert_ComptableResourceFactoryImpl
	implements Expert_ComptableResource.Factory {

	@Override
	public Expert_ComptableResource.Builder create() {
		return new Expert_ComptableResource.Builder() {

			@Override
			public Expert_ComptableResource build() {
				if (_user == null) {
					throw new IllegalArgumentException("User is not set");
				}

				Function<InvocationHandler, Expert_ComptableResource>
					expert_ComptableResourceProxyProviderFunction =
						ResourceProxyProviderFunctionHolder.
							_expert_ComptableResourceProxyProviderFunction;

				return expert_ComptableResourceProxyProviderFunction.apply(
					(proxy, method, arguments) -> _invoke(
						method, arguments, _checkPermissions,
						_httpServletRequest, _httpServletResponse,
						_preferredLocale, _uriInfo, _user));
			}

			@Override
			public Expert_ComptableResource.Builder checkPermissions(
				boolean checkPermissions) {

				_checkPermissions = checkPermissions;

				return this;
			}

			@Override
			public Expert_ComptableResource.Builder httpServletRequest(
				HttpServletRequest httpServletRequest) {

				_httpServletRequest = httpServletRequest;

				return this;
			}

			@Override
			public Expert_ComptableResource.Builder httpServletResponse(
				HttpServletResponse httpServletResponse) {

				_httpServletResponse = httpServletResponse;

				return this;
			}

			@Override
			public Expert_ComptableResource.Builder preferredLocale(
				Locale preferredLocale) {

				_preferredLocale = preferredLocale;

				return this;
			}

			@Override
			public Expert_ComptableResource.Builder uriInfo(UriInfo uriInfo) {
				_uriInfo = uriInfo;

				return this;
			}

			@Override
			public Expert_ComptableResource.Builder user(User user) {
				_user = user;

				return this;
			}

			private boolean _checkPermissions = true;
			private HttpServletRequest _httpServletRequest;
			private HttpServletResponse _httpServletResponse;
			private Locale _preferredLocale;
			private UriInfo _uriInfo;
			private User _user;

		};
	}

	private static Function<InvocationHandler, Expert_ComptableResource>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			Expert_ComptableResource.class.getClassLoader(),
			Expert_ComptableResource.class);

		try {
			Constructor<Expert_ComptableResource> constructor =
				(Constructor<Expert_ComptableResource>)
					proxyClass.getConstructor(InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private Object _invoke(
			Method method, Object[] arguments, boolean checkPermissions,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Locale preferredLocale,
			UriInfo uriInfo, User user)
		throws Throwable {

		String name = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (checkPermissions) {
			PermissionThreadLocal.setPermissionChecker(
				_defaultPermissionCheckerFactory.create(user));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(
				new LiberalPermissionChecker(user));
		}

		Expert_ComptableResource expert_ComptableResource =
			_componentServiceObjects.getService();

		expert_ComptableResource.setContextAcceptLanguage(
			new AcceptLanguageImpl(httpServletRequest, preferredLocale, user));

		Company company = _companyLocalService.getCompany(user.getCompanyId());

		expert_ComptableResource.setContextCompany(company);

		expert_ComptableResource.setContextHttpServletRequest(
			httpServletRequest);
		expert_ComptableResource.setContextHttpServletResponse(
			httpServletResponse);
		expert_ComptableResource.setContextUriInfo(uriInfo);
		expert_ComptableResource.setContextUser(user);
		expert_ComptableResource.setExpressionConvert(_expressionConvert);
		expert_ComptableResource.setFilterParserProvider(_filterParserProvider);
		expert_ComptableResource.setGroupLocalService(_groupLocalService);
		expert_ComptableResource.setResourceActionLocalService(
			_resourceActionLocalService);
		expert_ComptableResource.setResourcePermissionLocalService(
			_resourcePermissionLocalService);
		expert_ComptableResource.setRoleLocalService(_roleLocalService);
		expert_ComptableResource.setSortParserProvider(_sortParserProvider);

		try {
			return method.invoke(expert_ComptableResource, arguments);
		}
		catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getTargetException();
		}
		finally {
			_componentServiceObjects.ungetService(expert_ComptableResource);

			PrincipalThreadLocal.setName(name);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<Expert_ComptableResource>
		_componentServiceObjects;

	@Reference
	private PermissionCheckerFactory _defaultPermissionCheckerFactory;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private UserLocalService _userLocalService;

	private static class ResourceProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, Expert_ComptableResource>
				_expert_ComptableResourceProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private class AcceptLanguageImpl implements AcceptLanguage {

		public AcceptLanguageImpl(
			HttpServletRequest httpServletRequest, Locale preferredLocale,
			User user) {

			_httpServletRequest = httpServletRequest;
			_preferredLocale = preferredLocale;
			_user = user;
		}

		@Override
		public List<Locale> getLocales() {
			return Arrays.asList(getPreferredLocale());
		}

		@Override
		public String getPreferredLanguageId() {
			return LocaleUtil.toLanguageId(getPreferredLocale());
		}

		@Override
		public Locale getPreferredLocale() {
			if (_preferredLocale != null) {
				return _preferredLocale;
			}

			if (_httpServletRequest != null) {
				Locale locale = (Locale)_httpServletRequest.getAttribute(
					WebKeys.LOCALE);

				if (locale != null) {
					return locale;
				}
			}

			return _user.getLocale();
		}

		@Override
		public boolean isAcceptAllLanguages() {
			return false;
		}

		private final HttpServletRequest _httpServletRequest;
		private final Locale _preferredLocale;
		private final User _user;

	}

}
// LIFERAY-REST-BUILDER-HASH:-491391752