/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.CreateDmdExtQuotVisaRequest;
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
public class CreateDmdExtQuotVisaRequestSerDes {

	public static CreateDmdExtQuotVisaRequest toDTO(String json) {
		CreateDmdExtQuotVisaRequestJSONParser
			createDmdExtQuotVisaRequestJSONParser =
				new CreateDmdExtQuotVisaRequestJSONParser();

		return createDmdExtQuotVisaRequestJSONParser.parseToDTO(json);
	}

	public static CreateDmdExtQuotVisaRequest[] toDTOs(String json) {
		CreateDmdExtQuotVisaRequestJSONParser
			createDmdExtQuotVisaRequestJSONParser =
				new CreateDmdExtQuotVisaRequestJSONParser();

		return createDmdExtQuotVisaRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(
		CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest) {

		if (createDmdExtQuotVisaRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (createDmdExtQuotVisaRequest.getExpertID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertID\": ");

			sb.append(createDmdExtQuotVisaRequest.getExpertID());
		}

		if (createDmdExtQuotVisaRequest.getMotif() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"motif\": ");

			sb.append("\"");

			sb.append(_escape(createDmdExtQuotVisaRequest.getMotif()));

			sb.append("\"");
		}

		if (createDmdExtQuotVisaRequest.getOrdreExpertID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"ordreExpertID\": ");

			sb.append(createDmdExtQuotVisaRequest.getOrdreExpertID());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreateDmdExtQuotVisaRequestJSONParser
			createDmdExtQuotVisaRequestJSONParser =
				new CreateDmdExtQuotVisaRequestJSONParser();

		return createDmdExtQuotVisaRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest) {

		if (createDmdExtQuotVisaRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (createDmdExtQuotVisaRequest.getExpertID() == null) {
			map.put("expertID", null);
		}
		else {
			map.put(
				"expertID",
				String.valueOf(createDmdExtQuotVisaRequest.getExpertID()));
		}

		if (createDmdExtQuotVisaRequest.getMotif() == null) {
			map.put("motif", null);
		}
		else {
			map.put(
				"motif",
				String.valueOf(createDmdExtQuotVisaRequest.getMotif()));
		}

		if (createDmdExtQuotVisaRequest.getOrdreExpertID() == null) {
			map.put("ordreExpertID", null);
		}
		else {
			map.put(
				"ordreExpertID",
				String.valueOf(createDmdExtQuotVisaRequest.getOrdreExpertID()));
		}

		return map;
	}

	public static class CreateDmdExtQuotVisaRequestJSONParser
		extends BaseJSONParser<CreateDmdExtQuotVisaRequest> {

		@Override
		protected CreateDmdExtQuotVisaRequest createDTO() {
			return new CreateDmdExtQuotVisaRequest();
		}

		@Override
		protected CreateDmdExtQuotVisaRequest[] createDTOArray(int size) {
			return new CreateDmdExtQuotVisaRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "expertID")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "motif")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "ordreExpertID")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "expertID")) {
				if (jsonParserFieldValue != null) {
					createDmdExtQuotVisaRequest.setExpertID(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "motif")) {
				if (jsonParserFieldValue != null) {
					createDmdExtQuotVisaRequest.setMotif(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "ordreExpertID")) {
				if (jsonParserFieldValue != null) {
					createDmdExtQuotVisaRequest.setOrdreExpertID(
						Long.valueOf((String)jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:579163549