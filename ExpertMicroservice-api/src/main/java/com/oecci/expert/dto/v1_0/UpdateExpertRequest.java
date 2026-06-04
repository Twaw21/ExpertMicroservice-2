/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import javax.annotation.Generated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("UpdateExpertRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {
			"nom", "prenoms", "adressePostale", "matricule", "annee_inscription", "contact"
	}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "UpdateExpertRequest")
public class UpdateExpertRequest implements Serializable {

	public static UpdateExpertRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(UpdateExpertRequest.class, json);
	}

	public static UpdateExpertRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			UpdateExpertRequest.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema
	public String getAdressePostale() {
		if (_adressePostaleSupplier != null) {
			adressePostale = _adressePostaleSupplier.get();

			_adressePostaleSupplier = null;
		}

		return adressePostale;
	}

	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;

		_adressePostaleSupplier = null;
	}

	@JsonIgnore
	public void setAdressePostale(
		UnsafeSupplier<String, Exception> adressePostaleUnsafeSupplier) {

		_adressePostaleSupplier = () -> {
			try {
				return adressePostaleUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String adressePostale;

	@JsonIgnore
	private Supplier<String> _adressePostaleSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Integer getAnnee_inscription() {
		if (_annee_inscriptionSupplier != null) {
			annee_inscription = _annee_inscriptionSupplier.get();

			_annee_inscriptionSupplier = null;
		}

		return annee_inscription;
	}

	public void setAnnee_inscription(Integer annee_inscription) {
		this.annee_inscription = annee_inscription;

		_annee_inscriptionSupplier = null;
	}

	@JsonIgnore
	public void setAnnee_inscription(
		UnsafeSupplier<Integer, Exception> annee_inscriptionUnsafeSupplier) {

		_annee_inscriptionSupplier = () -> {
			try {
				return annee_inscriptionUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotNull
	protected Integer annee_inscription;

	@JsonIgnore
	private Supplier<Integer> _annee_inscriptionSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getContact() {
		if (_contactSupplier != null) {
			contact = _contactSupplier.get();

			_contactSupplier = null;
		}

		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;

		_contactSupplier = null;
	}

	@JsonIgnore
	public void setContact(
		UnsafeSupplier<String, Exception> contactUnsafeSupplier) {

		_contactSupplier = () -> {
			try {
				return contactUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String contact;

	@JsonIgnore
	private Supplier<String> _contactSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getMatricule() {
		if (_matriculeSupplier != null) {
			matricule = _matriculeSupplier.get();

			_matriculeSupplier = null;
		}

		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;

		_matriculeSupplier = null;
	}

	@JsonIgnore
	public void setMatricule(
		UnsafeSupplier<String, Exception> matriculeUnsafeSupplier) {

		_matriculeSupplier = () -> {
			try {
				return matriculeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String matricule;

	@JsonIgnore
	private Supplier<String> _matriculeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getNom() {
		if (_nomSupplier != null) {
			nom = _nomSupplier.get();

			_nomSupplier = null;
		}

		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;

		_nomSupplier = null;
	}

	@JsonIgnore
	public void setNom(UnsafeSupplier<String, Exception> nomUnsafeSupplier) {
		_nomSupplier = () -> {
			try {
				return nomUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String nom;

	@JsonIgnore
	private Supplier<String> _nomSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getPrenoms() {
		if (_prenomsSupplier != null) {
			prenoms = _prenomsSupplier.get();

			_prenomsSupplier = null;
		}

		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;

		_prenomsSupplier = null;
	}

	@JsonIgnore
	public void setPrenoms(
		UnsafeSupplier<String, Exception> prenomsUnsafeSupplier) {

		_prenomsSupplier = () -> {
			try {
				return prenomsUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	@NotEmpty
	protected String prenoms;

	@JsonIgnore
	private Supplier<String> _prenomsSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateExpertRequest)) {
			return false;
		}

		UpdateExpertRequest updateExpertRequest = (UpdateExpertRequest)object;

		return Objects.equals(toString(), updateExpertRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		String adressePostale = getAdressePostale();

		if (adressePostale != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(adressePostale));

			sb.append("\"");
		}

		Integer annee_inscription = getAnnee_inscription();

		if (annee_inscription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append("\"");

			sb.append(_escape(annee_inscription));

			sb.append("\"");
		}

		String contact = getContact();

		if (contact != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(contact));

			sb.append("\"");
		}

		String matricule = getMatricule();

		if (matricule != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(matricule));

			sb.append("\"");
		}

		String nom = getNom();

		if (nom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(nom));

			sb.append("\"");
		}

		String prenoms = getPrenoms();

		if (prenoms != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"prenoms\": ");

			sb.append("\"");

			sb.append(_escape(prenoms));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.UpdateExpertRequest",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Update_by")
	public static enum Update_by {

		BY_ADMIN("by_admin"), BY_MODERATEUR("by_moderateur"),
		BY_ASSISTANT("by_assistant");

		@JsonCreator
		public static Update_by create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Update_by update_by : values()) {
				if (Objects.equals(update_by.getValue(), value)) {
					return update_by;
				}
			}

			throw new IllegalArgumentException("Invalid enum value: " + value);
		}

		@JsonValue
		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Update_by(String value) {
			_value = value;
		}

		private final String _value;

	}

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
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
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

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-1659674926