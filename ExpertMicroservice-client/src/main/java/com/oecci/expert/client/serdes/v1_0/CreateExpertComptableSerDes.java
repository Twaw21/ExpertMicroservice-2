package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.CreateExpertComptable;
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
public class CreateExpertComptableSerDes {

	public static CreateExpertComptable toDTO(String json) {
		CreateExpertComptableJSONParser createExpertComptableJSONParser =
			new CreateExpertComptableJSONParser();

		return createExpertComptableJSONParser.parseToDTO(json);
	}

	public static CreateExpertComptable[] toDTOs(String json) {
		CreateExpertComptableJSONParser createExpertComptableJSONParser =
			new CreateExpertComptableJSONParser();

		return createExpertComptableJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreateExpertComptable createExpertComptable) {
		if (createExpertComptable == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (createExpertComptable.getAdressePostale() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getAdressePostale()));

			sb.append("\"");
		}

		if (createExpertComptable.getAnnee_inscription() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getAnnee_inscription()));

			sb.append("\"");
		}

		if (createExpertComptable.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getContact()));

			sb.append("\"");
		}

		if (createExpertComptable.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getEmail()));

			sb.append("\"");
		}

		if (createExpertComptable.getExpertAssoID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertAssoID\": ");

			sb.append(createExpertComptable.getExpertAssoID());
		}

		if (createExpertComptable.getInscription_by() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_by\": ");

			sb.append("\"");

			sb.append(createExpertComptable.getInscription_by());

			sb.append("\"");
		}

		if (createExpertComptable.getInscription_mode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_mode\": ");

			sb.append("\"");

			sb.append(createExpertComptable.getInscription_mode());

			sb.append("\"");
		}

		if (createExpertComptable.getInscription_type() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_type\": ");

			sb.append("\"");

			sb.append(createExpertComptable.getInscription_type());

			sb.append("\"");
		}

		if (createExpertComptable.getMatricule() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getMatricule()));

			sb.append("\"");
		}

		if (createExpertComptable.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getNom()));

			sb.append("\"");
		}

		if (createExpertComptable.getNomCabinet() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nomCabinet\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getNomCabinet()));

			sb.append("\"");
		}

		if (createExpertComptable.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(createExpertComptable.getPrenoms()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreateExpertComptableJSONParser createExpertComptableJSONParser =
			new CreateExpertComptableJSONParser();

		return createExpertComptableJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreateExpertComptable createExpertComptable) {

		if (createExpertComptable == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (createExpertComptable.getAdressePostale() == null) {
			map.put("adressePostale", null);
		}
		else {
			map.put(
				"adressePostale",
				String.valueOf(createExpertComptable.getAdressePostale()));
		}

		if (createExpertComptable.getAnnee_inscription() == null) {
			map.put("annee_inscription", null);
		}
		else {
			map.put(
				"annee_inscription",
				String.valueOf(createExpertComptable.getAnnee_inscription()));
		}

		if (createExpertComptable.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(createExpertComptable.getContact()));
		}

		if (createExpertComptable.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(createExpertComptable.getEmail()));
		}

		if (createExpertComptable.getExpertAssoID() == null) {
			map.put("expertAssoID", null);
		}
		else {
			map.put(
				"expertAssoID",
				String.valueOf(createExpertComptable.getExpertAssoID()));
		}

		if (createExpertComptable.getInscription_by() == null) {
			map.put("inscription_by", null);
		}
		else {
			map.put(
				"inscription_by",
				String.valueOf(createExpertComptable.getInscription_by()));
		}

		if (createExpertComptable.getInscription_mode() == null) {
			map.put("inscription_mode", null);
		}
		else {
			map.put(
				"inscription_mode",
				String.valueOf(createExpertComptable.getInscription_mode()));
		}

		if (createExpertComptable.getInscription_type() == null) {
			map.put("inscription_type", null);
		}
		else {
			map.put(
				"inscription_type",
				String.valueOf(createExpertComptable.getInscription_type()));
		}

		if (createExpertComptable.getMatricule() == null) {
			map.put("matricule", null);
		}
		else {
			map.put(
				"matricule",
				String.valueOf(createExpertComptable.getMatricule()));
		}

		if (createExpertComptable.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(createExpertComptable.getNom()));
		}

		if (createExpertComptable.getNomCabinet() == null) {
			map.put("nomCabinet", null);
		}
		else {
			map.put(
				"nomCabinet",
				String.valueOf(createExpertComptable.getNomCabinet()));
		}

		if (createExpertComptable.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(createExpertComptable.getPrenoms()));
		}

		return map;
	}

	public static class CreateExpertComptableJSONParser
		extends BaseJSONParser<CreateExpertComptable> {

		@Override
		protected CreateExpertComptable createDTO() {
			return new CreateExpertComptable();
		}

		@Override
		protected CreateExpertComptable[] createDTOArray(int size) {
			return new CreateExpertComptable[size];
		}

		@Override
		protected void setField(
			CreateExpertComptable createExpertComptable,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "adressePostale")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setAdressePostale(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "annee_inscription")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setAnnee_inscription(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setEmail(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expertAssoID")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setExpertAssoID(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_by")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setInscription_by(
						CreateExpertComptable.Inscription_by.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_mode")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setInscription_mode(
						CreateExpertComptable.Inscription_mode.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "inscription_type")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setInscription_type(
						CreateExpertComptable.Inscription_type.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "matricule")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setMatricule(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nomCabinet")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setNomCabinet(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					createExpertComptable.setPrenoms(
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

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}