package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.StatutRequest;
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
public class StatutRequestSerDes {

	public static StatutRequest toDTO(String json) {
		StatutRequestJSONParser statutRequestJSONParser =
			new StatutRequestJSONParser();

		return statutRequestJSONParser.parseToDTO(json);
	}

	public static StatutRequest[] toDTOs(String json) {
		StatutRequestJSONParser statutRequestJSONParser =
			new StatutRequestJSONParser();

		return statutRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(StatutRequest statutRequest) {
		if (statutRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (statutRequest.getMotif_refus() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"motif_refus\": ");

			sb.append("\"");

			sb.append(_escape(statutRequest.getMotif_refus()));

			sb.append("\"");
		}

		if (statutRequest.getStatut() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statut\": ");

			sb.append(String.valueOf(statutRequest.getStatut()));
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		StatutRequestJSONParser statutRequestJSONParser =
			new StatutRequestJSONParser();

		return statutRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(StatutRequest statutRequest) {
		if (statutRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (statutRequest.getMotif_refus() == null) {
			map.put("motif_refus", null);
		}
		else {
			map.put(
				"motif_refus", String.valueOf(statutRequest.getMotif_refus()));
		}

		if (statutRequest.getStatut() == null) {
			map.put("statut", null);
		}
		else {
			map.put("statut", String.valueOf(statutRequest.getStatut()));
		}

		return map;
	}

	public static class StatutRequestJSONParser
		extends BaseJSONParser<StatutRequest> {

		@Override
		protected StatutRequest createDTO() {
			return new StatutRequest();
		}

		@Override
		protected StatutRequest[] createDTOArray(int size) {
			return new StatutRequest[size];
		}

		@Override
		protected void setField(
			StatutRequest statutRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "motif_refus")) {
				if (jsonParserFieldValue != null) {
					statutRequest.setMotif_refus((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "statut")) {
				if (jsonParserFieldValue != null) {
					statutRequest.setStatut(
						OptionSerDes.toDTO((String)jsonParserFieldValue));
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