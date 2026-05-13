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
@GraphQLName("CreateCollaboRequest")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {"nom", "prenoms", "email", "role", "statut"}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CreateCollaboRequest")
public class CreateCollaboRequest implements Serializable {

	public static CreateCollaboRequest toDTO(String json) {
		return ObjectMapperUtil.readValue(CreateCollaboRequest.class, json);
	}

	public static CreateCollaboRequest unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateCollaboRequest.class, json);
	}

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
	protected String contact;

	@JsonIgnore
	private Supplier<String> _contactSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Long getCreatorID() {
		if (_creatorIDSupplier != null) {
			creatorID = _creatorIDSupplier.get();

			_creatorIDSupplier = null;
		}

		return creatorID;
	}

	public void setCreatorID(Long creatorID) {
		this.creatorID = creatorID;

		_creatorIDSupplier = null;
	}

	@JsonIgnore
	public void setCreatorID(
		UnsafeSupplier<Long, Exception> creatorIDUnsafeSupplier) {

		_creatorIDSupplier = () -> {
			try {
				return creatorIDUnsafeSupplier.get();
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
	protected Long creatorID;

	@JsonIgnore
	private Supplier<Long> _creatorIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("creatorLevel")
	@Valid
	public CreatorLevel getCreatorLevel() {
		if (_creatorLevelSupplier != null) {
			creatorLevel = _creatorLevelSupplier.get();

			_creatorLevelSupplier = null;
		}

		return creatorLevel;
	}

	@JsonIgnore
	public String getCreatorLevelAsString() {
		CreatorLevel creatorLevel = getCreatorLevel();

		if (creatorLevel == null) {
			return null;
		}

		return creatorLevel.toString();
	}

	public void setCreatorLevel(CreatorLevel creatorLevel) {
		this.creatorLevel = creatorLevel;

		_creatorLevelSupplier = null;
	}

	@JsonIgnore
	public void setCreatorLevel(
		UnsafeSupplier<CreatorLevel, Exception> creatorLevelUnsafeSupplier) {

		_creatorLevelSupplier = () -> {
			try {
				return creatorLevelUnsafeSupplier.get();
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
	protected CreatorLevel creatorLevel;

	@JsonIgnore
	private Supplier<CreatorLevel> _creatorLevelSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getEmail() {
		if (_emailSupplier != null) {
			email = _emailSupplier.get();

			_emailSupplier = null;
		}

		return email;
	}

	public void setEmail(String email) {
		this.email = email;

		_emailSupplier = null;
	}

	@JsonIgnore
	public void setEmail(
		UnsafeSupplier<String, Exception> emailUnsafeSupplier) {

		_emailSupplier = () -> {
			try {
				return emailUnsafeSupplier.get();
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
	protected String email;

	@JsonIgnore
	private Supplier<String> _emailSupplier;

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

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("role")
	@Valid
	public Role getRole() {
		if (_roleSupplier != null) {
			role = _roleSupplier.get();

			_roleSupplier = null;
		}

		return role;
	}

	@JsonIgnore
	public String getRoleAsString() {
		Role role = getRole();

		if (role == null) {
			return null;
		}

		return role.toString();
	}

	public void setRole(Role role) {
		this.role = role;

		_roleSupplier = null;
	}

	@JsonIgnore
	public void setRole(UnsafeSupplier<Role, Exception> roleUnsafeSupplier) {
		_roleSupplier = () -> {
			try {
				return roleUnsafeSupplier.get();
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
	protected Role role;

	@JsonIgnore
	private Supplier<Role> _roleSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public Boolean getStatut() {
		if (_statutSupplier != null) {
			statut = _statutSupplier.get();

			_statutSupplier = null;
		}

		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;

		_statutSupplier = null;
	}

	@JsonIgnore
	public void setStatut(
		UnsafeSupplier<Boolean, Exception> statutUnsafeSupplier) {

		_statutSupplier = () -> {
			try {
				return statutUnsafeSupplier.get();
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
	protected Boolean statut;

	@JsonIgnore
	private Supplier<Boolean> _statutSupplier;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateCollaboRequest)) {
			return false;
		}

		CreateCollaboRequest createCollaboRequest =
			(CreateCollaboRequest)object;

		return Objects.equals(toString(), createCollaboRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

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

		Long creatorID = getCreatorID();

		if (creatorID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorID\": ");

			sb.append(creatorID);
		}

		CreatorLevel creatorLevel = getCreatorLevel();

		if (creatorLevel != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"creatorLevel\": ");

			sb.append("\"");
			sb.append(creatorLevel);
			sb.append("\"");
		}

		String email = getEmail();

		if (email != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(email));

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

		Role role = getRole();

		if (role != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"role\": ");

			sb.append("\"");
			sb.append(role);
			sb.append("\"");
		}

		Boolean statut = getStatut();

		if (statut != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"statut\": ");

			sb.append(statut);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.CreateCollaboRequest",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("CreatorLevel")
	public static enum CreatorLevel {

		ADMIN("admin"), MODERATEUR("moderateur"), ASSISTANT("assistant");

		@JsonCreator
		public static CreatorLevel create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (CreatorLevel creatorLevel : values()) {
				if (Objects.equals(creatorLevel.getValue(), value)) {
					return creatorLevel;
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

		private CreatorLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Role")
	public static enum Role {

		ADMIN("admin"), MODERATEUR("moderateur"), ASSISTANT("assistant");

		@JsonCreator
		public static Role create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Role role : values()) {
				if (Objects.equals(role.getValue(), value)) {
					return role;
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

		private Role(String value) {
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
// LIFERAY-REST-BUILDER-HASH:653468964