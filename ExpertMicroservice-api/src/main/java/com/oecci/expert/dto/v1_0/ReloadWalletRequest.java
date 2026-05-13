/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("ReloadWalletRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {
		"Expert_ComptableID", "walletID", "paymentID", "amount"
	}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "ReloadWalletRequest")
public class ReloadWalletRequest implements Serializable {

	public static ReloadWalletRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(ReloadWalletRequest.class, json);
	}

	public static ReloadWalletRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			ReloadWalletRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getExpert_ComptableID() {
		if (_Expert_ComptableIDSupplier != null) {
			Expert_ComptableID = _Expert_ComptableIDSupplier.get();

			_Expert_ComptableIDSupplier = null;
		}

		return Expert_ComptableID;
	}

	public void setExpert_ComptableID(Long Expert_ComptableID) {
		this.Expert_ComptableID = Expert_ComptableID;

		_Expert_ComptableIDSupplier = null;
	}

	@JsonIgnore
	public void setExpert_ComptableID(
		UnsafeSupplier<Long, Exception> Expert_ComptableIDUnsafeSupplier) {

		_Expert_ComptableIDSupplier = () -> {
			try {
				return Expert_ComptableIDUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Long Expert_ComptableID;

	@JsonIgnore
	private Supplier<Long> _Expert_ComptableIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getAmount() {
		if (_amountSupplier != null) {
			amount = _amountSupplier.get();

			_amountSupplier = null;
		}

		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;

		_amountSupplier = null;
	}

	@JsonIgnore
	public void setAmount(
		UnsafeSupplier<Long, Exception> amountUnsafeSupplier) {

		_amountSupplier = () -> {
			try {
				return amountUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Long amount;

	@JsonIgnore
	private Supplier<Long> _amountSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getPaymentID() {
		if (_paymentIDSupplier != null) {
			paymentID = _paymentIDSupplier.get();

			_paymentIDSupplier = null;
		}

		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;

		_paymentIDSupplier = null;
	}

	@JsonIgnore
	public void setPaymentID(
		UnsafeSupplier<Long, Exception> paymentIDUnsafeSupplier) {

		_paymentIDSupplier = () -> {
			try {
				return paymentIDUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Long paymentID;

	@JsonIgnore
	private Supplier<Long> _paymentIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getWalletID() {
		if (_walletIDSupplier != null) {
			walletID = _walletIDSupplier.get();

			_walletIDSupplier = null;
		}

		return walletID;
	}

	public void setWalletID(Long walletID) {
		this.walletID = walletID;

		_walletIDSupplier = null;
	}

	@JsonIgnore
	public void setWalletID(
		UnsafeSupplier<Long, Exception> walletIDUnsafeSupplier) {

		_walletIDSupplier = () -> {
			try {
				return walletIDUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Long walletID;

	@JsonIgnore
	private Supplier<Long> _walletIDSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReloadWalletRequest)) {
			return false;
		}

		ReloadWalletRequest reloadWalletRequest = (ReloadWalletRequest)object;

		return Objects.equals(toString(), reloadWalletRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long Expert_ComptableID = getExpert_ComptableID();

		if (Expert_ComptableID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"Expert_ComptableID\": ");

			sb.append(Expert_ComptableID);
		}

		Long amount = getAmount();

		if (amount != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amount\": ");

			sb.append(amount);
		}

		Long paymentID = getPaymentID();

		if (paymentID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentID\": ");

			sb.append(paymentID);
		}

		Long walletID = getWalletID();

		if (walletID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"walletID\": ");

			sb.append(walletID);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.ReloadWalletRequest",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-427465924