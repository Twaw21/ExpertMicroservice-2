/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.UpdateCabinetRequest;
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
public class UpdateCabinetRequestSerDes {

	public static UpdateCabinetRequest toDTO(String json) {
		UpdateCabinetRequestJSONParser updateCabinetRequestJSONParser =
			new UpdateCabinetRequestJSONParser();

		return updateCabinetRequestJSONParser.parseToDTO(json);
	}

	public static UpdateCabinetRequest[] toDTOs(String json) {
		UpdateCabinetRequestJSONParser updateCabinetRequestJSONParser =
			new UpdateCabinetRequestJSONParser();

		return updateCabinetRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(UpdateCabinetRequest updateCabinetRequest) {
		if (updateCabinetRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (updateCabinetRequest.getAdressePostale() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getAdressePostale()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getAnnee_inscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append(updateCabinetRequest.getAnnee_inscription());
		}

		if (updateCabinetRequest.getCategorie() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"categorie\": ");

			sb.append(String.valueOf(updateCabinetRequest.getCategorie()));
		}

		if (updateCabinetRequest.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getContact()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getMatricule() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getMatricule()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getNom()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getNomCabinet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nomCabinet\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getNomCabinet()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getNumeroCabinet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numeroCabinet\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getNumeroCabinet()));

			sb.append("\"");
		}

		if (updateCabinetRequest.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(updateCabinetRequest.getPrenoms()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		UpdateCabinetRequestJSONParser updateCabinetRequestJSONParser =
			new UpdateCabinetRequestJSONParser();

		return updateCabinetRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		UpdateCabinetRequest updateCabinetRequest) {

		if (updateCabinetRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (updateCabinetRequest.getAdressePostale() == null) {
			map.put("adressePostale", null);
		}
		else {
			map.put(
				"adressePostale",
				String.valueOf(updateCabinetRequest.getAdressePostale()));
		}

		if (updateCabinetRequest.getAnnee_inscription() == null) {
			map.put("annee_inscription", null);
		}
		else {
			map.put(
				"annee_inscription",
				String.valueOf(updateCabinetRequest.getAnnee_inscription()));
		}

		if (updateCabinetRequest.getCategorie() == null) {
			map.put("categorie", null);
		}
		else {
			map.put(
				"categorie",
				String.valueOf(updateCabinetRequest.getCategorie()));
		}

		if (updateCabinetRequest.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(updateCabinetRequest.getContact()));
		}

		if (updateCabinetRequest.getMatricule() == null) {
			map.put("matricule", null);
		}
		else {
			map.put(
				"matricule",
				String.valueOf(updateCabinetRequest.getMatricule()));
		}

		if (updateCabinetRequest.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(updateCabinetRequest.getNom()));
		}

		if (updateCabinetRequest.getNomCabinet() == null) {
			map.put("nomCabinet", null);
		}
		else {
			map.put(
				"nomCabinet",
				String.valueOf(updateCabinetRequest.getNomCabinet()));
		}

		if (updateCabinetRequest.getNumeroCabinet() == null) {
			map.put("numeroCabinet", null);
		}
		else {
			map.put(
				"numeroCabinet",
				String.valueOf(updateCabinetRequest.getNumeroCabinet()));
		}

		if (updateCabinetRequest.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(updateCabinetRequest.getPrenoms()));
		}

		return map;
	}

	public static class UpdateCabinetRequestJSONParser
		extends BaseJSONParser<UpdateCabinetRequest> {

		@Override
		protected UpdateCabinetRequest createDTO() {
			return new UpdateCabinetRequest();
		}

		@Override
		protected UpdateCabinetRequest[] createDTOArray(int size) {
			return new UpdateCabinetRequest[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "categorie")) {
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
			else if (Objects.equals(jsonParserFieldName, "nomCabinet")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "numeroCabinet")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			UpdateCabinetRequest updateCabinetRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setAdressePostale(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setAnnee_inscription(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "categorie")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setCategorie(
						OptionSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "matricule")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setMatricule(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nomCabinet")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setNomCabinet(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numeroCabinet")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setNumeroCabinet(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					updateCabinetRequest.setPrenoms(
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
// LIFERAY-REST-BUILDER-HASH:103396394