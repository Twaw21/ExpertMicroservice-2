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

import javax.validation.constraints.NotEmpty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("LoadVisualRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"visualName", "visualContent"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "LoadVisualRequest")
public class LoadVisualRequest implements Serializable {

	public static LoadVisualRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(LoadVisualRequest.class, json);
	}

	public static LoadVisualRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(LoadVisualRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getVisualContent() {
		if (_visualContentSupplier != null) {
			visualContent = _visualContentSupplier.get();

			_visualContentSupplier = null;
		}

		return visualContent;
	}

	public void setVisualContent(String visualContent) {
		this.visualContent = visualContent;

		_visualContentSupplier = null;
	}

	@JsonIgnore
	public void setVisualContent(
		UnsafeSupplier<String, Exception> visualContentUnsafeSupplier) {

		_visualContentSupplier = () -> {
			try {
				return visualContentUnsafeSupplier.get();
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
	@NotEmpty
	protected String visualContent;

	@JsonIgnore
	private Supplier<String> _visualContentSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getVisualName() {
		if (_visualNameSupplier != null) {
			visualName = _visualNameSupplier.get();

			_visualNameSupplier = null;
		}

		return visualName;
	}

	public void setVisualName(String visualName) {
		this.visualName = visualName;

		_visualNameSupplier = null;
	}

	@JsonIgnore
	public void setVisualName(
		UnsafeSupplier<String, Exception> visualNameUnsafeSupplier) {

		_visualNameSupplier = () -> {
			try {
				return visualNameUnsafeSupplier.get();
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
	@NotEmpty
	protected String visualName;

	@JsonIgnore
	private Supplier<String> _visualNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LoadVisualRequest)) {
			return false;
		}

		LoadVisualRequest loadVisualRequest = (LoadVisualRequest)object;

		return Objects.equals(toString(), loadVisualRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String visualContent = getVisualContent();

		if (visualContent != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visualContent\": ");

			sb.append("\"");

			sb.append(_escape(visualContent));

			sb.append("\"");
		}

		String visualName = getVisualName();

		if (visualName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visualName\": ");

			sb.append("\"");

			sb.append(_escape(visualName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.LoadVisualRequest",
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
// LIFERAY-REST-BUILDER-HASH:1282501556