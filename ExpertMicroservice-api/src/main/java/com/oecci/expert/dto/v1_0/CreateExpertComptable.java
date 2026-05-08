package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
@GraphQLName("CreateExpertComptable")
@JsonFilter("Liferay.Vulcan")
@Schema(
	requiredProperties = {
		"nom", "prenoms", "email", "matricule", "inscription_type",
		"inscription_mode"
	}
)
@XmlRootElement(name = "CreateExpertComptable")
public class CreateExpertComptable implements Serializable {

	public static CreateExpertComptable toDTO(String json) {
		return ObjectMapperUtil.readValue(CreateExpertComptable.class, json);
	}

	public static CreateExpertComptable unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(
			CreateExpertComptable.class, json);
	}

	@Schema(example = "01 BP 3590 Abidjan 01")
	public String getAdressePostale() {
		return adressePostale;
	}

	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}

	@JsonIgnore
	public void setAdressePostale(
		UnsafeSupplier<String, Exception> adressePostaleUnsafeSupplier) {

		try {
			adressePostale = adressePostaleUnsafeSupplier.get();
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
	protected String adressePostale;

	@Schema(example = "2025-09-26T21:08:01Z")
	public String getAnnee_inscription() {
		return annee_inscription;
	}

	public void setAnnee_inscription(String annee_inscription) {
		this.annee_inscription = annee_inscription;
	}

	@JsonIgnore
	public void setAnnee_inscription(
		UnsafeSupplier<String, Exception> annee_inscriptionUnsafeSupplier) {

		try {
			annee_inscription = annee_inscriptionUnsafeSupplier.get();
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
	protected String annee_inscription;

	@Schema(example = "+2250707070707")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@JsonIgnore
	public void setContact(
		UnsafeSupplier<String, Exception> contactUnsafeSupplier) {

		try {
			contact = contactUnsafeSupplier.get();
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
	protected String contact;

	@Schema(example = "chantal.kouassi@expertci.com")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public void setEmail(
		UnsafeSupplier<String, Exception> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
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
	@NotEmpty
	protected String email;

	@Schema
	public Long getExpertAssoID() {
		return expertAssoID;
	}

	public void setExpertAssoID(Long expertAssoID) {
		this.expertAssoID = expertAssoID;
	}

	@JsonIgnore
	public void setExpertAssoID(
		UnsafeSupplier<Long, Exception> expertAssoIDUnsafeSupplier) {

		try {
			expertAssoID = expertAssoIDUnsafeSupplier.get();
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
	protected Long expertAssoID;

	@Schema
	@Valid
	public Inscription_by getInscription_by() {
		return inscription_by;
	}

	@JsonIgnore
	public String getInscription_byAsString() {
		if (inscription_by == null) {
			return null;
		}

		return inscription_by.toString();
	}

	public void setInscription_by(Inscription_by inscription_by) {
		this.inscription_by = inscription_by;
	}

	@JsonIgnore
	public void setInscription_by(
		UnsafeSupplier<Inscription_by, Exception>
			inscription_byUnsafeSupplier) {

		try {
			inscription_by = inscription_byUnsafeSupplier.get();
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
	protected Inscription_by inscription_by;

	@Schema
	@Valid
	public Inscription_mode getInscription_mode() {
		return inscription_mode;
	}

	@JsonIgnore
	public String getInscription_modeAsString() {
		if (inscription_mode == null) {
			return null;
		}

		return inscription_mode.toString();
	}

	public void setInscription_mode(Inscription_mode inscription_mode) {
		this.inscription_mode = inscription_mode;
	}

	@JsonIgnore
	public void setInscription_mode(
		UnsafeSupplier<Inscription_mode, Exception>
			inscription_modeUnsafeSupplier) {

		try {
			inscription_mode = inscription_modeUnsafeSupplier.get();
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
	@NotNull
	protected Inscription_mode inscription_mode;

	@Schema
	@Valid
	public Inscription_type getInscription_type() {
		return inscription_type;
	}

	@JsonIgnore
	public String getInscription_typeAsString() {
		if (inscription_type == null) {
			return null;
		}

		return inscription_type.toString();
	}

	public void setInscription_type(Inscription_type inscription_type) {
		this.inscription_type = inscription_type;
	}

	@JsonIgnore
	public void setInscription_type(
		UnsafeSupplier<Inscription_type, Exception>
			inscription_typeUnsafeSupplier) {

		try {
			inscription_type = inscription_typeUnsafeSupplier.get();
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
	@NotNull
	protected Inscription_type inscription_type;

	@Schema(example = "CEC-98721-B")
	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	@JsonIgnore
	public void setMatricule(
		UnsafeSupplier<String, Exception> matriculeUnsafeSupplier) {

		try {
			matricule = matriculeUnsafeSupplier.get();
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
	@NotEmpty
	protected String matricule;

	@Schema(example = "Kouassi")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@JsonIgnore
	public void setNom(UnsafeSupplier<String, Exception> nomUnsafeSupplier) {
		try {
			nom = nomUnsafeSupplier.get();
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
	@NotEmpty
	protected String nom;

	@Schema(example = "Cabinet SERAC")
	public String getNomCabinet() {
		return nomCabinet;
	}

	public void setNomCabinet(String nomCabinet) {
		this.nomCabinet = nomCabinet;
	}

	@JsonIgnore
	public void setNomCabinet(
		UnsafeSupplier<String, Exception> nomCabinetUnsafeSupplier) {

		try {
			nomCabinet = nomCabinetUnsafeSupplier.get();
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
	protected String nomCabinet;

//	@Schema(example = "Cabinet SERAC")
	public String getNumeroCabinet() {
		return numeroCabinet;
	}
	
	public void setNumeroCabinet(String numeroCabinet) {
		this.numeroCabinet = numeroCabinet;
	}
	
	@JsonIgnore
	public void setNumeroCabinet(
			UnsafeSupplier<String, Exception> numeroCabinetUnsafeSupplier) {
		
		try {
			numeroCabinet = numeroCabinetUnsafeSupplier.get();
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
	protected String numeroCabinet;

	@Schema(example = "Chantal")
	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	@JsonIgnore
	public void setPrenoms(
		UnsafeSupplier<String, Exception> prenomsUnsafeSupplier) {

		try {
			prenoms = prenomsUnsafeSupplier.get();
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
	@NotEmpty
	protected String prenoms;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateExpertComptable)) {
			return false;
		}

		CreateExpertComptable createExpertComptable =
			(CreateExpertComptable)object;

		return Objects.equals(toString(), createExpertComptable.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		if (adressePostale != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"adressePostale\": ");

			sb.append("\"");

			sb.append(_escape(adressePostale));

			sb.append("\"");
		}

		if (annee_inscription != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"annee_inscription\": ");

			sb.append("\"");

			sb.append(_escape(annee_inscription));

			sb.append("\"");
		}

		if (contact != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"contact\": ");

			sb.append("\"");

			sb.append(_escape(contact));

			sb.append("\"");
		}

		if (email != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"email\": ");

			sb.append("\"");

			sb.append(_escape(email));

			sb.append("\"");
		}

		if (expertAssoID != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"expertAssoID\": ");

			sb.append(expertAssoID);
		}

		if (inscription_by != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_by\": ");

			sb.append("\"");

			sb.append(inscription_by);

			sb.append("\"");
		}

		if (inscription_mode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_mode\": ");

			sb.append("\"");

			sb.append(inscription_mode);

			sb.append("\"");
		}

		if (inscription_type != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"inscription_type\": ");

			sb.append("\"");

			sb.append(inscription_type);

			sb.append("\"");
		}

		if (matricule != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matricule\": ");

			sb.append("\"");

			sb.append(_escape(matricule));

			sb.append("\"");
		}

		if (nom != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nom\": ");

			sb.append("\"");

			sb.append(_escape(nom));

			sb.append("\"");
		}

		if (nomCabinet != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"nomCabinet\": ");

			sb.append("\"");

			sb.append(_escape(nomCabinet));

			sb.append("\"");
		}

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

	@Schema(
		accessMode = Schema.AccessMode.READ_ONLY,
		defaultValue = "com.oecci.expert.dto.v1_0.CreateExpertComptable",
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