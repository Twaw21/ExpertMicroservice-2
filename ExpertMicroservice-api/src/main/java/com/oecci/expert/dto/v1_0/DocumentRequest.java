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
@GraphQLName("DocumentRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"documentName", "documentContent"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "DocumentRequest")
public class DocumentRequest implements Serializable {

	public static DocumentRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(DocumentRequest.class, json);
	}

	public static DocumentRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(DocumentRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getDocumentContent() {
		if (_documentContentSupplier != null) {
			documentContent = _documentContentSupplier.get();

			_documentContentSupplier = null;
		}

		return documentContent;
	}

	public void setDocumentContent(String documentContent) {
		this.documentContent = documentContent;

		_documentContentSupplier = null;
	}

	@JsonIgnore
	public void setDocumentContent(
		UnsafeSupplier<String, Exception> documentContentUnsafeSupplier) {

		_documentContentSupplier = () -> {
			try {
				return documentContentUnsafeSupplier.get();
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
	protected String documentContent;

	@JsonIgnore
	private Supplier<String> _documentContentSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getDocumentName() {
		if (_documentNameSupplier != null) {
			documentName = _documentNameSupplier.get();

			_documentNameSupplier = null;
		}

		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;

		_documentNameSupplier = null;
	}

	@JsonIgnore
	public void setDocumentName(
		UnsafeSupplier<String, Exception> documentNameUnsafeSupplier) {

		_documentNameSupplier = () -> {
			try {
				return documentNameUnsafeSupplier.get();
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
	protected String documentName;

	@JsonIgnore
	private Supplier<String> _documentNameSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentRequest)) {
			return false;
		}

		DocumentRequest documentRequest = (DocumentRequest)object;

		return Objects.equals(toString(), documentRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String documentContent = getDocumentContent();

		if (documentContent != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentContent\": ");

			sb.append("\"");

			sb.append(_escape(documentContent));

			sb.append("\"");
		}

		String documentName = getDocumentName();

		if (documentName != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"documentName\": ");

			sb.append("\"");

			sb.append(_escape(documentName));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.DocumentRequest",
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
// LIFERAY-REST-BUILDER-HASH:1167581015