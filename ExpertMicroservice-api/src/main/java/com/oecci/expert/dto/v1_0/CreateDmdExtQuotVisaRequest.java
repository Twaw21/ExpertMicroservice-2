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
@GraphQLName("CreateDmdExtQuotVisaRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"expertID", "cgaExpediteurID", "ordreExpertID"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CreateDmdExtQuotVisaRequest")
public class CreateDmdExtQuotVisaRequest implements Serializable {

	public static CreateDmdExtQuotVisaRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(
			CreateDmdExtQuotVisaRequest.class, json);
	}

	public static CreateDmdExtQuotVisaRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateDmdExtQuotVisaRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getExpertID() {
		if (_expertIDSupplier != null) {
			expertID = _expertIDSupplier.get();

			_expertIDSupplier = null;
		}

		return expertID;
	}

	public void setExpertID(Long expertID) {
		this.expertID = expertID;

		_expertIDSupplier = null;
	}

	@JsonIgnore
	public void setExpertID(
		UnsafeSupplier<Long, Exception> expertIDUnsafeSupplier) {

		_expertIDSupplier = () -> {
			try {
				return expertIDUnsafeSupplier.get();
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
	protected Long expertID;

	@JsonIgnore
	private Supplier<Long> _expertIDSupplier;

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

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getOrdreExpertID() {
		if (_ordreExpertIDSupplier != null) {
			ordreExpertID = _ordreExpertIDSupplier.get();

			_ordreExpertIDSupplier = null;
		}

		return ordreExpertID;
	}

	public void setOrdreExpertID(Long ordreExpertID) {
		this.ordreExpertID = ordreExpertID;

		_ordreExpertIDSupplier = null;
	}

	@JsonIgnore
	public void setOrdreExpertID(
		UnsafeSupplier<Long, Exception> ordreExpertIDUnsafeSupplier) {

		_ordreExpertIDSupplier = () -> {
			try {
				return ordreExpertIDUnsafeSupplier.get();
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
	protected Long ordreExpertID;

	@JsonIgnore
	private Supplier<Long> _ordreExpertIDSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateDmdExtQuotVisaRequest)) {
			return false;
		}

		CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest =
			(CreateDmdExtQuotVisaRequest)object;

		return Objects.equals(
			toString(), createDmdExtQuotVisaRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		Long expertID = getExpertID();

		if (expertID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertID\": ");

			sb.append(expertID);
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

		Long ordreExpertID = getOrdreExpertID();

		if (ordreExpertID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ordreExpertID\": ");

			sb.append(ordreExpertID);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.CreateDmdExtQuotVisaRequest",
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
// LIFERAY-REST-BUILDER-HASH:1275254312