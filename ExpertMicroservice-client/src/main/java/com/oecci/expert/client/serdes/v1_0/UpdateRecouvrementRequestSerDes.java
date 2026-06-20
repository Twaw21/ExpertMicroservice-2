/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.UpdateRecouvrementRequest;
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
public class UpdateRecouvrementRequestSerDes {

	public static UpdateRecouvrementRequest toDTO(String json) {
		UpdateRecouvrementRequestJSONParser
			updateRecouvrementRequestJSONParser =
				new UpdateRecouvrementRequestJSONParser();

		return updateRecouvrementRequestJSONParser.parseToDTO(json);
	}

	public static UpdateRecouvrementRequest[] toDTOs(String json) {
		UpdateRecouvrementRequestJSONParser
			updateRecouvrementRequestJSONParser =
				new UpdateRecouvrementRequestJSONParser();

		return updateRecouvrementRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		UpdateRecouvrementRequest updateRecouvrementRequest) {

		if (updateRecouvrementRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (updateRecouvrementRequest.getDocument() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"document\": ");

			sb.append(String.valueOf(updateRecouvrementRequest.getDocument()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UpdateRecouvrementRequestJSONParser
			updateRecouvrementRequestJSONParser =
				new UpdateRecouvrementRequestJSONParser();

		return updateRecouvrementRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UpdateRecouvrementRequest updateRecouvrementRequest) {

		if (updateRecouvrementRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (updateRecouvrementRequest.getDocument() == null) {
			map.put("document", null);
		}
		else {
			map.put(
				"document",
				String.valueOf(updateRecouvrementRequest.getDocument()));
		}

		return map;
	}

	public static class UpdateRecouvrementRequestJSONParser
		extends BaseJSONParser<UpdateRecouvrementRequest> {

		@Override
		protected UpdateRecouvrementRequest createDTO() {
			return new UpdateRecouvrementRequest();
		}

		@Override
		protected UpdateRecouvrementRequest[] createDTOArray(int size) {
			return new UpdateRecouvrementRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "document")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UpdateRecouvrementRequest updateRecouvrementRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "document")) {
				if (jsonParserFieldValue != null) {
					updateRecouvrementRequest.setDocument(
						DocumentRequestSerDes.toDTO(
							(String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:-621907295