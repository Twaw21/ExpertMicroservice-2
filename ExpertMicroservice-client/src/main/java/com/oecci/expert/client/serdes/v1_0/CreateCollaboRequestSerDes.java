package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.CreateCollaboRequest;
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
public class CreateCollaboRequestSerDes {

	public static CreateCollaboRequest toDTO(String json) {
		CreateCollaboRequestJSONParser createCollaboRequestJSONParser =
			new CreateCollaboRequestJSONParser();

		return createCollaboRequestJSONParser.parseToDTO(json);
	}

	public static CreateCollaboRequest[] toDTOs(String json) {
		CreateCollaboRequestJSONParser createCollaboRequestJSONParser =
			new CreateCollaboRequestJSONParser();

		return createCollaboRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(CreateCollaboRequest createCollaboRequest) {
		if (createCollaboRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (createCollaboRequest.getContact() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(createCollaboRequest.getContact()));

			sb.append("\"");
		}

		if (createCollaboRequest.getCreatorID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorID\": ");

			sb.append(createCollaboRequest.getCreatorID());
		}

		if (createCollaboRequest.getCreatorLevel() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorLevel\": ");

			sb.append("\"");

			sb.append(createCollaboRequest.getCreatorLevel());

			sb.append("\"");
		}

		if (createCollaboRequest.getEmail() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(createCollaboRequest.getEmail()));

			sb.append("\"");
		}

		if (createCollaboRequest.getNom() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(createCollaboRequest.getNom()));

			sb.append("\"");
		}

		if (createCollaboRequest.getPrenoms() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(createCollaboRequest.getPrenoms()));

			sb.append("\"");
		}

		if (createCollaboRequest.getRole() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"role\": ");

			sb.append("\"");

			sb.append(createCollaboRequest.getRole());

			sb.append("\"");
		}

		if (createCollaboRequest.getStatut() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statut\": ");

			sb.append(createCollaboRequest.getStatut());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		CreateCollaboRequestJSONParser createCollaboRequestJSONParser =
			new CreateCollaboRequestJSONParser();

		return createCollaboRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		CreateCollaboRequest createCollaboRequest) {

		if (createCollaboRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (createCollaboRequest.getContact() == null) {
			map.put("contact", null);
		}
		else {
			map.put(
				"contact", String.valueOf(createCollaboRequest.getContact()));
		}

		if (createCollaboRequest.getCreatorID() == null) {
			map.put("creatorID", null);
		}
		else {
			map.put(
				"creatorID",
				String.valueOf(createCollaboRequest.getCreatorID()));
		}

		if (createCollaboRequest.getCreatorLevel() == null) {
			map.put("creatorLevel", null);
		}
		else {
			map.put(
				"creatorLevel",
				String.valueOf(createCollaboRequest.getCreatorLevel()));
		}

		if (createCollaboRequest.getEmail() == null) {
			map.put("email", null);
		}
		else {
			map.put("email", String.valueOf(createCollaboRequest.getEmail()));
		}

		if (createCollaboRequest.getNom() == null) {
			map.put("nom", null);
		}
		else {
			map.put("nom", String.valueOf(createCollaboRequest.getNom()));
		}

		if (createCollaboRequest.getPrenoms() == null) {
			map.put("prenoms", null);
		}
		else {
			map.put(
				"prenoms", String.valueOf(createCollaboRequest.getPrenoms()));
		}

		if (createCollaboRequest.getRole() == null) {
			map.put("role", null);
		}
		else {
			map.put("role", String.valueOf(createCollaboRequest.getRole()));
		}

		if (createCollaboRequest.getStatut() == null) {
			map.put("statut", null);
		}
		else {
			map.put("statut", String.valueOf(createCollaboRequest.getStatut()));
		}

		return map;
	}

	public static class CreateCollaboRequestJSONParser
		extends BaseJSONParser<CreateCollaboRequest> {

		@Override
		protected CreateCollaboRequest createDTO() {
			return new CreateCollaboRequest();
		}

		@Override
		protected CreateCollaboRequest[] createDTOArray(int size) {
			return new CreateCollaboRequest[size];
		}

		@Override
		protected void setField(
			CreateCollaboRequest createCollaboRequest,
			String jsonParserFieldName, Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "contact")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setContact(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creatorID")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setCreatorID(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "creatorLevel")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setCreatorLevel(
						CreateCollaboRequest.CreatorLevel.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "email")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setEmail((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "nom")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setNom((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "prenoms")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setPrenoms(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "role")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setRole(
						CreateCollaboRequest.Role.create(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statut")) {
				if (jsonParserFieldValue != null) {
					createCollaboRequest.setStatut(
						(Boolean)jsonParserFieldValue);
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