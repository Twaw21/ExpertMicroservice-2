package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("CreateForwardRequest")
@JsonFilter("Liferay.Vulcan")
@Schema(
	requiredProperties = {
		"nom", "prenoms", "email", "matricule", "inscription_type",
		"inscription_mode"
	}
)
@XmlRootElement(name = "CreateForwardRequest")
public class CreateForwardRequest implements Serializable {

	public static CreateForwardRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(CreateForwardRequest.class, json);
	}

	public static CreateForwardRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateForwardRequest.class, json);
	}

	@Schema
	public Long getClientID() {
		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}

	@JsonIgnore
	public void setClientID(
		UnsafeSupplier<Long, Exception> clientIDUnsafeSupplier) {

		try {
			clientID = clientIDUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long clientID;

	@Schema
	public Long getExpertDestinataireID() {
		return expertDestinataireID;
	}

	public void setExpertDestinataireID(Long expertDestinataireID) {
		this.expertDestinataireID = expertDestinataireID;
	}

	@JsonIgnore
	public void setExpertDestinataireID(
		UnsafeSupplier<Long, Exception> expertDestinataireIDUnsafeSupplier) {

		try {
			expertDestinataireID = expertDestinataireIDUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long expertDestinataireID;

	@Schema
	public Long getExpertExpediteurID() {
		return expertExpediteurID;
	}

	public void setExpertExpediteurID(Long expertExpediteurID) {
		this.expertExpediteurID = expertExpediteurID;
	}

	@JsonIgnore
	public void setExpertExpediteurID(
		UnsafeSupplier<Long, Exception> expertExpediteurIDUnsafeSupplier) {

		try {
			expertExpediteurID = expertExpediteurIDUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long expertExpediteurID;

	@Schema
	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	@JsonIgnore
	public void setMotif(
		UnsafeSupplier<String, Exception> motifUnsafeSupplier) {

		try {
			motif = motifUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String motif;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateForwardRequest)) {
			return false;
		}

		CreateForwardRequest createForwardRequest =
			(CreateForwardRequest)object;

		return Objects.equals(toString(), createForwardRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (clientID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clientID\": ");

			sb.append(clientID);
		}

		if (expertDestinataireID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertDestinataireID\": ");

			sb.append(expertDestinataireID);
		}

		if (expertExpediteurID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertExpediteurID\": ");

			sb.append(expertExpediteurID);
		}

		if (motif != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"motif\": ");

			sb.append("\"");

			sb.append(_escape(motif));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.CreateForwardRequest",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
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
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

}