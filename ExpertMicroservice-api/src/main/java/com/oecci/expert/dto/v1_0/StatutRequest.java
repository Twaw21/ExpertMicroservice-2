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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("StatutRequest")
@io.swagger.v3.oas.annotations.media.Schema(requiredProperties = {"statut"})
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "StatutRequest")
public class StatutRequest implements Serializable {

	public static StatutRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(StatutRequest.class, json);
	}

	public static StatutRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(StatutRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getMotif_refus() {
		if (_motif_refusSupplier != null) {
			motif_refus = _motif_refusSupplier.get();

			_motif_refusSupplier = null;
		}

		return motif_refus;
	}

	public void setMotif_refus(String motif_refus) {
		this.motif_refus = motif_refus;

		_motif_refusSupplier = null;
	}

	@JsonIgnore
	public void setMotif_refus(
		UnsafeSupplier<String, Exception> motif_refusUnsafeSupplier) {

		_motif_refusSupplier = () -> {
			try {
				return motif_refusUnsafeSupplier.get();
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
	protected String motif_refus;

	@JsonIgnore
	private Supplier<String> _motif_refusSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public Option getStatut() {
		if (_statutSupplier != null) {
			statut = _statutSupplier.get();

			_statutSupplier = null;
		}

		return statut;
	}

	public void setStatut(Option statut) {
		this.statut = statut;

		_statutSupplier = null;
	}

	@JsonIgnore
	public void setStatut(
		UnsafeSupplier<Option, Exception> statutUnsafeSupplier) {

		_statutSupplier = () -> {
			try {
				return statutUnsafeSupplier.get();
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
	protected Option statut;

	@JsonIgnore
	private Supplier<Option> _statutSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StatutRequest)) {
			return false;
		}

		StatutRequest statutRequest = (StatutRequest)object;

		return Objects.equals(toString(), statutRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String motif_refus = getMotif_refus();

		if (motif_refus != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"motif_refus\": ");

			sb.append("\"");

			sb.append(_escape(motif_refus));

			sb.append("\"");
		}

		Option statut = getStatut();

		if (statut != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statut\": ");

			sb.append(String.valueOf(statut));
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.StatutRequest",
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
// LIFERAY-REST-BUILDER-HASH:-1897991340