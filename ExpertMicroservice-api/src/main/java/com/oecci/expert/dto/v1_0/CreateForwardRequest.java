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
@GraphQLName("CreateForwardRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"expertExpediteurID", "expertDestinataireID"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CreateForwardRequest")
public class CreateForwardRequest implements Serializable {

	public static CreateForwardRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(CreateForwardRequest.class, json);
	}

	public static CreateForwardRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateForwardRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getClientID() {
		if (_clientIDSupplier != null) {
			clientID = _clientIDSupplier.get();

			_clientIDSupplier = null;
		}

		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;

		_clientIDSupplier = null;
	}

	@JsonIgnore
	public void setClientID(
		UnsafeSupplier<Long, Exception> clientIDUnsafeSupplier) {

		_clientIDSupplier = () -> {
			try {
				return clientIDUnsafeSupplier.get();
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
	protected Long clientID;

	@JsonIgnore
	private Supplier<Long> _clientIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getExpertDestinataireID() {
		if (_expertDestinataireIDSupplier != null) {
			expertDestinataireID = _expertDestinataireIDSupplier.get();

			_expertDestinataireIDSupplier = null;
		}

		return expertDestinataireID;
	}

	public void setExpertDestinataireID(Long expertDestinataireID) {
		this.expertDestinataireID = expertDestinataireID;

		_expertDestinataireIDSupplier = null;
	}

	@JsonIgnore
	public void setExpertDestinataireID(
		UnsafeSupplier<Long, Exception> expertDestinataireIDUnsafeSupplier) {

		_expertDestinataireIDSupplier = () -> {
			try {
				return expertDestinataireIDUnsafeSupplier.get();
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
	protected Long expertDestinataireID;

	@JsonIgnore
	private Supplier<Long> _expertDestinataireIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getExpertExpediteurID() {
		if (_expertExpediteurIDSupplier != null) {
			expertExpediteurID = _expertExpediteurIDSupplier.get();

			_expertExpediteurIDSupplier = null;
		}

		return expertExpediteurID;
	}

	public void setExpertExpediteurID(Long expertExpediteurID) {
		this.expertExpediteurID = expertExpediteurID;

		_expertExpediteurIDSupplier = null;
	}

	@JsonIgnore
	public void setExpertExpediteurID(
		UnsafeSupplier<Long, Exception> expertExpediteurIDUnsafeSupplier) {

		_expertExpediteurIDSupplier = () -> {
			try {
				return expertExpediteurIDUnsafeSupplier.get();
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
	protected Long expertExpediteurID;

	@JsonIgnore
	private Supplier<Long> _expertExpediteurIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getMotif() {
		if (_motifSupplier != null) {
			motif = _motifSupplier.get();

			_motifSupplier = null;
		}

		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;

		_motifSupplier = null;
	}

	@JsonIgnore
	public void setMotif(
		UnsafeSupplier<String, Exception> motifUnsafeSupplier) {

		_motifSupplier = () -> {
			try {
				return motifUnsafeSupplier.get();
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
	protected String motif;

	@JsonIgnore
	private Supplier<String> _motifSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateForwardRequest)) {
			return false;
		}

		CreateForwardRequest createForwardRequest =
			(CreateForwardRequest)object;

		return Objects.equals(toString(), createForwardRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long clientID = getClientID();

		if (clientID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientID\": ");

			sb.append(clientID);
		}

		Long expertDestinataireID = getExpertDestinataireID();

		if (expertDestinataireID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertDestinataireID\": ");

			sb.append(expertDestinataireID);
		}

		Long expertExpediteurID = getExpertExpediteurID();

		if (expertExpediteurID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertExpediteurID\": ");

			sb.append(expertExpediteurID);
		}

		String motif = getMotif();

		if (motif != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"motif\": ");

			sb.append("\"");

			sb.append(_escape(motif));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.CreateForwardRequest",
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
// LIFERAY-REST-BUILDER-HASH:-1704498125