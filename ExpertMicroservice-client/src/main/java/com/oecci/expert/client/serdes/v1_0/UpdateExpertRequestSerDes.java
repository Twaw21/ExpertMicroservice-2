/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.UpdateExpertRequest;
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
public class UpdateExpertRequestSerDes {

	public static UpdateExpertRequest toDTO(String json) {
		UpdateExpertRequestJSONParser updateExpertRequestJSONParser =
			new UpdateExpertRequestJSONParser();

		return updateExpertRequestJSONParser.parseToDTO(json);
	}

	public static UpdateExpertRequest[] toDTOs(String json) {
		UpdateExpertRequestJSONParser updateExpertRequestJSONParser =
			new UpdateExpertRequestJSONParser();

		return updateExpertRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UpdateExpertRequest updateExpertRequest) {
		if (updateExpertRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (updateExpertRequest.getAdressePostale() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getAdressePostale()));

			sb.append("\"");
		}

		if (updateExpertRequest.getAnnee_inscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getAnnee_inscription()));

			sb.append("\"");
		}

		if (updateExpertRequest.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getContact()));

			sb.append("\"");
		}

		if (updateExpertRequest.getMatricule() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getMatricule()));

			sb.append("\"");
		}

		if (updateExpertRequest.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getNom()));

			sb.append("\"");
		}

		if (updateExpertRequest.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(updateExpertRequest.getPrenoms()));

			sb.append("\"");
		}

		if (updateExpertRequest.getUpdate_by() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"update_by\": ");

			sb.append("\"");
			sb.append(updateExpertRequest.getUpdate_by());
			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UpdateExpertRequestJSONParser updateExpertRequestJSONParser =
			new UpdateExpertRequestJSONParser();

		return updateExpertRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UpdateExpertRequest updateExpertRequest) {

		if (updateExpertRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (updateExpertRequest.getAdressePostale() == null) {
			map.put("adressePostale", null);
		}
		else {
			map.put(
				"adressePostale",
				String.valueOf(updateExpertRequest.getAdressePostale()));
		}

		if (updateExpertRequest.getAnnee_inscription() == null) {
			map.put("annee_inscription", null);
		}
		else {
			map.put(
				"annee_inscription",
				String.valueOf(updateExpertRequest.getAnnee_inscription()));
		}

		if (updateExpertRequest.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(updateExpertRequest.getContact()));
		}

		if (updateExpertRequest.getMatricule() == null) {
			map.put("matricule", null);
		}
		else {
			map.put(
				"matricule",
				String.valueOf(updateExpertRequest.getMatricule()));
		}

		if (updateExpertRequest.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(updateExpertRequest.getNom()));
		}

		if (updateExpertRequest.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(updateExpertRequest.getPrenoms()));
		}

		if (updateExpertRequest.getUpdate_by() == null) {
			map.put("update_by", null);
		}
		else {
			map.put(
				"update_by",
				String.valueOf(updateExpertRequest.getUpdate_by()));
		}

		return map;
	}

	public static class UpdateExpertRequestJSONParser
		extends BaseJSONParser<UpdateExpertRequest> {

		@Override
		protected UpdateExpertRequest createDTO() {
			return new UpdateExpertRequest();
		}

		@Override
		protected UpdateExpertRequest[] createDTOArray(int size) {
			return new UpdateExpertRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "contact")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "matricule")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "update_by")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UpdateExpertRequest updateExpertRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setAdressePostale(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setAnnee_inscription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "matricule")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setMatricule(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setPrenoms(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "update_by")) {
				if (jsonParserFieldValue != null) {
					updateExpertRequest.setUpdate_by(
						UpdateExpertRequest.Update_by.create(
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
// LIFERAY-REST-BUILDER-HASH:823187999