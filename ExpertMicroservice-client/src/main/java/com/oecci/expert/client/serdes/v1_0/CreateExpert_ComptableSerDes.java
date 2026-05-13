/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.CreateExpert_Comptable;
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
public class CreateExpert_ComptableSerDes {

	public static CreateExpert_Comptable toDTO(String json) {
		CreateExpert_ComptableJSONParser createExpert_ComptableJSONParser =
			new CreateExpert_ComptableJSONParser();

		return createExpert_ComptableJSONParser.parseToDTO(json);
	}

	public static CreateExpert_Comptable[] toDTOs(String json) {
		CreateExpert_ComptableJSONParser createExpert_ComptableJSONParser =
			new CreateExpert_ComptableJSONParser();

		return createExpert_ComptableJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreateExpert_Comptable createExpert_Comptable) {
		if (createExpert_Comptable == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (createExpert_Comptable.getAdressePostale() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getAdressePostale()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getAnnee_inscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getAnnee_inscription()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getContact()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getEmail()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getExpertAssoID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertAssoID\": ");

			sb.append(createExpert_Comptable.getExpertAssoID());
		}

		if (createExpert_Comptable.getInscription_by() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_by\": ");

			sb.append("\"");
			sb.append(createExpert_Comptable.getInscription_by());
			sb.append("\"");
		}

		if (createExpert_Comptable.getInscription_mode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_mode\": ");

			sb.append("\"");
			sb.append(createExpert_Comptable.getInscription_mode());
			sb.append("\"");
		}

		if (createExpert_Comptable.getInscription_type() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_type\": ");

			sb.append("\"");
			sb.append(createExpert_Comptable.getInscription_type());
			sb.append("\"");
		}

		if (createExpert_Comptable.getMatricule() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getMatricule()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getNom()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getNomCabinet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nomCabinet\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getNomCabinet()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getNumeroCabinet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numeroCabinet\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getNumeroCabinet()));

			sb.append("\"");
		}

		if (createExpert_Comptable.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(createExpert_Comptable.getPrenoms()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreateExpert_ComptableJSONParser createExpert_ComptableJSONParser =
			new CreateExpert_ComptableJSONParser();

		return createExpert_ComptableJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreateExpert_Comptable createExpert_Comptable) {

		if (createExpert_Comptable == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (createExpert_Comptable.getAdressePostale() == null) {
			map.put("adressePostale", null);
		}
		else {
			map.put(
				"adressePostale",
				String.valueOf(createExpert_Comptable.getAdressePostale()));
		}

		if (createExpert_Comptable.getAnnee_inscription() == null) {
			map.put("annee_inscription", null);
		}
		else {
			map.put(
				"annee_inscription",
				String.valueOf(createExpert_Comptable.getAnnee_inscription()));
		}

		if (createExpert_Comptable.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(createExpert_Comptable.getContact()));
		}

		if (createExpert_Comptable.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(createExpert_Comptable.getEmail()));
		}

		if (createExpert_Comptable.getExpertAssoID() == null) {
			map.put("expertAssoID", null);
		}
		else {
			map.put(
				"expertAssoID",
				String.valueOf(createExpert_Comptable.getExpertAssoID()));
		}

		if (createExpert_Comptable.getInscription_by() == null) {
			map.put("inscription_by", null);
		}
		else {
			map.put(
				"inscription_by",
				String.valueOf(createExpert_Comptable.getInscription_by()));
		}

		if (createExpert_Comptable.getInscription_mode() == null) {
			map.put("inscription_mode", null);
		}
		else {
			map.put(
				"inscription_mode",
				String.valueOf(createExpert_Comptable.getInscription_mode()));
		}

		if (createExpert_Comptable.getInscription_type() == null) {
			map.put("inscription_type", null);
		}
		else {
			map.put(
				"inscription_type",
				String.valueOf(createExpert_Comptable.getInscription_type()));
		}

		if (createExpert_Comptable.getMatricule() == null) {
			map.put("matricule", null);
		}
		else {
			map.put(
				"matricule",
				String.valueOf(createExpert_Comptable.getMatricule()));
		}

		if (createExpert_Comptable.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(createExpert_Comptable.getNom()));
		}

		if (createExpert_Comptable.getNomCabinet() == null) {
			map.put("nomCabinet", null);
		}
		else {
			map.put(
				"nomCabinet",
				String.valueOf(createExpert_Comptable.getNomCabinet()));
		}

		if (createExpert_Comptable.getNumeroCabinet() == null) {
			map.put("numeroCabinet", null);
		}
		else {
			map.put(
				"numeroCabinet",
				String.valueOf(createExpert_Comptable.getNumeroCabinet()));
		}

		if (createExpert_Comptable.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(createExpert_Comptable.getPrenoms()));
		}

		return map;
	}

	public static class CreateExpert_ComptableJSONParser
		extends BaseJSONParser<CreateExpert_Comptable> {

		@Override
		protected CreateExpert_Comptable createDTO() {
			return new CreateExpert_Comptable();
		}

		@Override
		protected CreateExpert_Comptable[] createDTOArray(int size) {
			return new CreateExpert_Comptable[size];
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
			else if (Objects.equals(jsonParserFieldName, "email")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "expertAssoID")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_by")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_mode")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_type")) {
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
			CreateExpert_Comptable createExpert_Comptable,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setAdressePostale(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setAnnee_inscription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setEmail(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expertAssoID")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setExpertAssoID(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_by")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setInscription_by(
						CreateExpert_Comptable.Inscription_by.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_mode")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setInscription_mode(
						CreateExpert_Comptable.Inscription_mode.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_type")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setInscription_type(
						CreateExpert_Comptable.Inscription_type.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "matricule")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setMatricule(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nomCabinet")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setNomCabinet(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "numeroCabinet")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setNumeroCabinet(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					createExpert_Comptable.setPrenoms(
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
// LIFERAY-REST-BUILDER-HASH:-703033928