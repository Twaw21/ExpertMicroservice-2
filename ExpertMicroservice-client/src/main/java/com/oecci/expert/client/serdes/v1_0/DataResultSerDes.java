package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.DataResult;
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
public class DataResultSerDes {

	public static DataResult toDTO(String json) {
		DataResultJSONParser dataResultJSONParser = new DataResultJSONParser();

		return dataResultJSONParser.parseToDTO(json);
	}

	public static DataResult[] toDTOs(String json) {
		DataResultJSONParser dataResultJSONParser = new DataResultJSONParser();

		return dataResultJSONParser.parseToDTOs(json);
	}

	public static String toJSON(DataResult dataResult) {
		if (dataResult == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (dataResult.getDesignation() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"designation\": ");

			sb.append("\"");

			sb.append(_escape(dataResult.getDesignation()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		DataResultJSONParser dataResultJSONParser = new DataResultJSONParser();

		return dataResultJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(DataResult dataResult) {
		if (dataResult == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (dataResult.getDesignation() == null) {
			map.put("designation", null);
		}
		else {
			map.put("designation", String.valueOf(dataResult.getDesignation()));
		}

		return map;
	}

	public static class DataResultJSONParser
		extends BaseJSONParser<DataResult> {

		@Override
		protected DataResult createDTO() {
			return new DataResult();
		}

		@Override
		protected DataResult[] createDTOArray(int size) {
			return new DataResult[size];
		}

		@Override
		protected void setField(
			DataResult dataResult, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "designation")) {
				if (jsonParserFieldValue != null) {
					dataResult.setDesignation((String)jsonParserFieldValue);
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