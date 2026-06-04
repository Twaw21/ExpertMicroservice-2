/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.UpdateCollaboRequest;
import com.oecci.expert.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class UpdateCollaboRequestSerDes {

	public static UpdateCollaboRequest toDTO(String json) {
		UpdateCollaboRequestJSONParser updateCollaboRequestJSONParser =
			new UpdateCollaboRequestJSONParser();

		return updateCollaboRequestJSONParser.parseToDTO(json);
	}

	public static UpdateCollaboRequest[] toDTOs(String json) {
		UpdateCollaboRequestJSONParser updateCollaboRequestJSONParser =
			new UpdateCollaboRequestJSONParser();

		return updateCollaboRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UpdateCollaboRequest updateCollaboRequest) {
		if (updateCollaboRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (updateCollaboRequest.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(updateCollaboRequest.getContact()));

			sb.append("\"");
		}

		if (updateCollaboRequest.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(updateCollaboRequest.getNom()));

			sb.append("\"");
		}

		if (updateCollaboRequest.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(updateCollaboRequest.getPrenoms()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UpdateCollaboRequestJSONParser updateCollaboRequestJSONParser =
			new UpdateCollaboRequestJSONParser();

		return updateCollaboRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UpdateCollaboRequest updateCollaboRequest) {

		if (updateCollaboRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (updateCollaboRequest.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(updateCollaboRequest.getContact()));
		}

		if (updateCollaboRequest.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(updateCollaboRequest.getNom()));
		}

		if (updateCollaboRequest.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(updateCollaboRequest.getPrenoms()));
		}

		return map;
	}

	public static class UpdateCollaboRequestJSONParser
		extends BaseJSONParser<UpdateCollaboRequest> {

		@Override
		protected UpdateCollaboRequest createDTO() {
			return new UpdateCollaboRequest();
		}

		@Override
		protected UpdateCollaboRequest[] createDTOArray(int size) {
			return new UpdateCollaboRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "contact")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UpdateCollaboRequest updateCollaboRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					updateCollaboRequest.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					updateCollaboRequest.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					updateCollaboRequest.setPrenoms(
						(String)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1900854615