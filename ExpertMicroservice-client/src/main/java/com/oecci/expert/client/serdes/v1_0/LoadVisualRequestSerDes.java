package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.LoadVisualRequest;
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
public class LoadVisualRequestSerDes {

	public static LoadVisualRequest toDTO(String json) {
		LoadVisualRequestJSONParser loadVisualRequestJSONParser =
			new LoadVisualRequestJSONParser();

		return loadVisualRequestJSONParser.parseToDTO(json);
	}

	public static LoadVisualRequest[] toDTOs(String json) {
		LoadVisualRequestJSONParser loadVisualRequestJSONParser =
			new LoadVisualRequestJSONParser();

		return loadVisualRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(LoadVisualRequest loadVisualRequest) {
		if (loadVisualRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (loadVisualRequest.getVisualContent() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visualContent\": ");

			sb.append("\"");

			sb.append(_escape(loadVisualRequest.getVisualContent()));

			sb.append("\"");
		}

		if (loadVisualRequest.getVisualName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"visualName\": ");

			sb.append("\"");

			sb.append(_escape(loadVisualRequest.getVisualName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		LoadVisualRequestJSONParser loadVisualRequestJSONParser =
			new LoadVisualRequestJSONParser();

		return loadVisualRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		LoadVisualRequest loadVisualRequest) {

		if (loadVisualRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (loadVisualRequest.getVisualContent() == null) {
			map.put("visualContent", null);
		}
		else {
			map.put(
				"visualContent",
				String.valueOf(loadVisualRequest.getVisualContent()));
		}

		if (loadVisualRequest.getVisualName() == null) {
			map.put("visualName", null);
		}
		else {
			map.put(
				"visualName",
				String.valueOf(loadVisualRequest.getVisualName()));
		}

		return map;
	}

	public static class LoadVisualRequestJSONParser
		extends BaseJSONParser<LoadVisualRequest> {

		@Override
		protected LoadVisualRequest createDTO() {
			return new LoadVisualRequest();
		}

		@Override
		protected LoadVisualRequest[] createDTOArray(int size) {
			return new LoadVisualRequest[size];
		}

		@Override
		protected void setField(
			LoadVisualRequest loadVisualRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "visualContent")) {
				if (jsonParserFieldValue != null) {
					loadVisualRequest.setVisualContent(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "visualName")) {
				if (jsonParserFieldValue != null) {
					loadVisualRequest.setVisualName(
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