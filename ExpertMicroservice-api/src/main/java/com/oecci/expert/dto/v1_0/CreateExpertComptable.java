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
@GraphQLName("CreateExpert_Comptable")
@io.swagger.v3.oas.annotations.media.Schema(
	requiredProperties = {
		"nom", "prenoms", "email", "matricule", "inscription_type",
		"inscription_mode"
	}
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "CreateExpertComptable")
public class CreateExpertComptable implements Serializable {

	public static CreateExpertComptable toDTO(String json) {
		return ObjectMapperUtil.readValue(CreateExpertComptable.class, json);
	}

	public static CreateExpertComptable unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateExpertComptable.class, json);
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
	public String getAnnee_inscription() {
		if (_annee_inscriptionSupplier != null) {
			annee_inscription = _annee_inscriptionSupplier.get();

			_annee_inscriptionSupplier = null;
		}

		return annee_inscription;
	}

	public void setAnnee_inscription(String annee_inscription) {
		this.annee_inscription = annee_inscription;

		_annee_inscriptionSupplier = null;
	}

	@JsonIgnore
	public void setAnnee_inscription(
		UnsafeSupplier<String, Exception> annee_inscriptionUnsafeSupplier) {

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
	protected String annee_inscription;

	@JsonIgnore
	private Supplier<String> _annee_inscriptionSupplier;

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
	public Long getExpertAssoID() {
		if (_expertAssoIDSupplier != null) {
			expertAssoID = _expertAssoIDSupplier.get();

			_expertAssoIDSupplier = null;
		}

		return expertAssoID;
	}

	public void setExpertAssoID(Long expertAssoID) {
		this.expertAssoID = expertAssoID;

		_expertAssoIDSupplier = null;
	}

	@JsonIgnore
	public void setExpertAssoID(
		UnsafeSupplier<Long, Exception> expertAssoIDUnsafeSupplier) {

		_expertAssoIDSupplier = () -> {
			try {
				return expertAssoIDUnsafeSupplier.get();
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
	protected Long expertAssoID;

	@JsonIgnore
	private Supplier<Long> _expertAssoIDSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("inscription_by")
	@Valid
	public Inscription_by getInscription_by() {
		if (_inscription_bySupplier != null) {
			inscription_by = _inscription_bySupplier.get();

			_inscription_bySupplier = null;
		}

		return inscription_by;
	}

	@JsonIgnore
	public String getInscription_byAsString() {
		Inscription_by inscription_by = getInscription_by();

		if (inscription_by == null) {
			return null;
		}

		return inscription_by.toString();
	}

	public void setInscription_by(Inscription_by inscription_by) {
		this.inscription_by = inscription_by;

		_inscription_bySupplier = null;
	}

	@JsonIgnore
	public void setInscription_by(
		UnsafeSupplier<Inscription_by, Exception>
			inscription_byUnsafeSupplier) {

		_inscription_bySupplier = () -> {
			try {
				return inscription_byUnsafeSupplier.get();
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
	protected Inscription_by inscription_by;

	@JsonIgnore
	private Supplier<Inscription_by> _inscription_bySupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("inscription_mode")
	@Valid
	public Inscription_mode getInscription_mode() {
		if (_inscription_modeSupplier != null) {
			inscription_mode = _inscription_modeSupplier.get();

			_inscription_modeSupplier = null;
		}

		return inscription_mode;
	}

	@JsonIgnore
	public String getInscription_modeAsString() {
		Inscription_mode inscription_mode = getInscription_mode();

		if (inscription_mode == null) {
			return null;
		}

		return inscription_mode.toString();
	}

	public void setInscription_mode(Inscription_mode inscription_mode) {
		this.inscription_mode = inscription_mode;

		_inscription_modeSupplier = null;
	}

	@JsonIgnore
	public void setInscription_mode(
		UnsafeSupplier<Inscription_mode, Exception>
			inscription_modeUnsafeSupplier) {

		_inscription_modeSupplier = () -> {
			try {
				return inscription_modeUnsafeSupplier.get();
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
	protected Inscription_mode inscription_mode;

	@JsonIgnore
	private Supplier<Inscription_mode> _inscription_modeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@JsonGetter("inscription_type")
	@Valid
	public Inscription_type getInscription_type() {
		if (_inscription_typeSupplier != null) {
			inscription_type = _inscription_typeSupplier.get();

			_inscription_typeSupplier = null;
		}

		return inscription_type;
	}

	@JsonIgnore
	public String getInscription_typeAsString() {
		Inscription_type inscription_type = getInscription_type();

		if (inscription_type == null) {
			return null;
		}

		return inscription_type.toString();
	}

	public void setInscription_type(Inscription_type inscription_type) {
		this.inscription_type = inscription_type;

		_inscription_typeSupplier = null;
	}

	@JsonIgnore
	public void setInscription_type(
		UnsafeSupplier<Inscription_type, Exception>
			inscription_typeUnsafeSupplier) {

		_inscription_typeSupplier = () -> {
			try {
				return inscription_typeUnsafeSupplier.get();
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
	protected Inscription_type inscription_type;

	@JsonIgnore
	private Supplier<Inscription_type> _inscription_typeSupplier;

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
	public String getNomCabinet() {
		if (_nomCabinetSupplier != null) {
			nomCabinet = _nomCabinetSupplier.get();

			_nomCabinetSupplier = null;
		}

		return nomCabinet;
	}

	public void setNomCabinet(String nomCabinet) {
		this.nomCabinet = nomCabinet;

		_nomCabinetSupplier = null;
	}

	@JsonIgnore
	public void setNomCabinet(
		UnsafeSupplier<String, Exception> nomCabinetUnsafeSupplier) {

		_nomCabinetSupplier = () -> {
			try {
				return nomCabinetUnsafeSupplier.get();
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
	protected String nomCabinet;

	@JsonIgnore
	private Supplier<String> _nomCabinetSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	public String getNumeroCabinet() {
		if (_numeroCabinetSupplier != null) {
			numeroCabinet = _numeroCabinetSupplier.get();

			_numeroCabinetSupplier = null;
		}

		return numeroCabinet;
	}

	public void setNumeroCabinet(String numeroCabinet) {
		this.numeroCabinet = numeroCabinet;

		_numeroCabinetSupplier = null;
	}

	@JsonIgnore
	public void setNumeroCabinet(
		UnsafeSupplier<String, Exception> numeroCabinetUnsafeSupplier) {

		_numeroCabinetSupplier = () -> {
			try {
				return numeroCabinetUnsafeSupplier.get();
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
	protected String numeroCabinet;

	@JsonIgnore
	private Supplier<String> _numeroCabinetSupplier;

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

		if (!(object instanceof CreateExpertComptable)) {
			return false;
		}

		CreateExpertComptable createExpert_Comptable =
			(CreateExpertComptable)object;

		return Objects.equals(toString(), createExpert_Comptable.toString());
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

		String annee_inscription = getAnnee_inscription();

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

		Long expertAssoID = getExpertAssoID();

		if (expertAssoID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertAssoID\": ");

			sb.append(expertAssoID);
		}

		Inscription_by inscription_by = getInscription_by();

		if (inscription_by != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_by\": ");

			sb.append("\"");
			sb.append(inscription_by);
			sb.append("\"");
		}

		Inscription_mode inscription_mode = getInscription_mode();

		if (inscription_mode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_mode\": ");

			sb.append("\"");
			sb.append(inscription_mode);
			sb.append("\"");
		}

		Inscription_type inscription_type = getInscription_type();

		if (inscription_type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_type\": ");

			sb.append("\"");
			sb.append(inscription_type);
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

		String nomCabinet = getNomCabinet();

		if (nomCabinet != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nomCabinet\": ");

			sb.append("\"");

			sb.append(_escape(nomCabinet));

			sb.append("\"");
		}

		String numeroCabinet = getNumeroCabinet();

		if (numeroCabinet != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"numeroCabinet\": ");

			sb.append("\"");

			sb.append(_escape(numeroCabinet));

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
		defaultValue = "com.oecci.expert.dto.v1_0.CreateExpert_Comptable",
		name = "x-class-name"
	)
	public String xClassName;

	@GraphQLName("Inscription_by")
	public static enum Inscription_by {

		BY_ADMIN("by_admin"), BY_MODERATEUR("by_moderateur"),
		BY_ASSISTANT("by_assistant");

		@JsonCreator
		public static Inscription_by create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Inscription_by inscription_by : values()) {
				if (Objects.equals(inscription_by.getValue(), value)) {
					return inscription_by;
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

		private Inscription_by(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Inscription_mode")
	public static enum Inscription_mode {

		FOR_ASSOC("for_assoc"), FOR_COORD("for_coord"), FOR_ADJ("for_adj"),
		FOR_NA("for_na");

		@JsonCreator
		public static Inscription_mode create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Inscription_mode inscription_mode : values()) {
				if (Objects.equals(inscription_mode.getValue(), value)) {
					return inscription_mode;
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

		private Inscription_mode(String value) {
			_value = value;
		}

		private final String _value;

	}

	@GraphQLName("Inscription_type")
	public static enum Inscription_type {

		INDIVIDUEL("individuel"), CABINET("cabinet"),
		COLLABORATEUR("collaborateur");

		@JsonCreator
		public static Inscription_type create(String value) {
			if ((value == null) || value.equals("")) {
				return null;
			}

			for (Inscription_type inscription_type : values()) {
				if (Objects.equals(inscription_type.getValue(), value)) {
					return inscription_type;
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

		private Inscription_type(String value) {
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
// LIFERAY-REST-BUILDER-HASH:-1584795778