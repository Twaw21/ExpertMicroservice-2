package com.oecci.expert.client.serdes.v1_0;

import com.oecci.expert.client.dto.v1_0.ReloadWalletRequest;
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
public class ReloadWalletRequestSerDes {

	public static ReloadWalletRequest toDTO(String json) {
		ReloadWalletRequestJSONParser reloadWalletRequestJSONParser =
			new ReloadWalletRequestJSONParser();

		return reloadWalletRequestJSONParser.parseToDTO(json);
	}

	public static ReloadWalletRequest[] toDTOs(String json) {
		ReloadWalletRequestJSONParser reloadWalletRequestJSONParser =
			new ReloadWalletRequestJSONParser();

		return reloadWalletRequestJSONParser.parseToDTOs(json);
	}

	public static String toJSON(ReloadWalletRequest reloadWalletRequest) {
		if (reloadWalletRequest == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (reloadWalletRequest.getAmount() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"amount\": ");

			sb.append(reloadWalletRequest.getAmount());
		}

		if (reloadWalletRequest.getExpertComptableID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertComptableID\": ");

			sb.append(reloadWalletRequest.getExpertComptableID());
		}

		if (reloadWalletRequest.getPaymentID() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"paymentID\": ");

			sb.append(reloadWalletRequest.getPaymentID());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		ReloadWalletRequestJSONParser reloadWalletRequestJSONParser =
			new ReloadWalletRequestJSONParser();

		return reloadWalletRequestJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(
		ReloadWalletRequest reloadWalletRequest) {

		if (reloadWalletRequest == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (reloadWalletRequest.getAmount() == null) {
			map.put("amount", null);
		}
		else {
			map.put("amount", String.valueOf(reloadWalletRequest.getAmount()));
		}

		if (reloadWalletRequest.getExpertComptableID() == null) {
			map.put("expertComptableID", null);
		}
		else {
			map.put(
				"expertComptableID",
				String.valueOf(reloadWalletRequest.getExpertComptableID()));
		}

		if (reloadWalletRequest.getPaymentID() == null) {
			map.put("paymentID", null);
		}
		else {
			map.put(
				"paymentID",
				String.valueOf(reloadWalletRequest.getPaymentID()));
		}

		return map;
	}

	public static class ReloadWalletRequestJSONParser
		extends BaseJSONParser<ReloadWalletRequest> {

		@Override
		protected ReloadWalletRequest createDTO() {
			return new ReloadWalletRequest();
		}

		@Override
		protected ReloadWalletRequest[] createDTOArray(int size) {
			return new ReloadWalletRequest[size];
		}

		@Override
		protected void setField(
			ReloadWalletRequest reloadWalletRequest, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "amount")) {
				if (jsonParserFieldValue != null) {
					reloadWalletRequest.setAmount(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "expertComptableID")) {
				if (jsonParserFieldValue != null) {
					reloadWalletRequest.setExpertComptableID(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "paymentID")) {
				if (jsonParserFieldValue != null) {
					reloadWalletRequest.setPaymentID(
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