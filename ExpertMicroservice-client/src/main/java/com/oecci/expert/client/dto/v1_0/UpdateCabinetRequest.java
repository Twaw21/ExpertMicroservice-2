/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.UpdateCabinetRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class UpdateCabinetRequest implements Cloneable, Serializable {

	public static UpdateCabinetRequest toDTO(String json) {
		return UpdateCabinetRequestSerDes.toDTO(json);
	}

	public String getAdressePostale() {
		return adressePostale;
	}

	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}

	public void setAdressePostale(
		UnsafeSupplier<String, Exception> adressePostaleUnsafeSupplier) {

		try {
			adressePostale = adressePostaleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String adressePostale;

	public Long getAnnee_inscription() {
		return annee_inscription;
	}

	public void setAnnee_inscription(Long annee_inscription) {
		this.annee_inscription = annee_inscription;
	}

	public void setAnnee_inscription(
		UnsafeSupplier<Long, Exception> annee_inscriptionUnsafeSupplier) {

		try {
			annee_inscription = annee_inscriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long annee_inscription;

	public Option getCategorie() {
		return categorie;
	}

	public void setCategorie(Option categorie) {
		this.categorie = categorie;
	}

	public void setCategorie(
		UnsafeSupplier<Option, Exception> categorieUnsafeSupplier) {

		try {
			categorie = categorieUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Option categorie;

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setContact(
		UnsafeSupplier<String, Exception> contactUnsafeSupplier) {

		try {
			contact = contactUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contact;

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public void setMatricule(
		UnsafeSupplier<String, Exception> matriculeUnsafeSupplier) {

		try {
			matricule = matriculeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String matricule;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNom(UnsafeSupplier<String, Exception> nomUnsafeSupplier) {
		try {
			nom = nomUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String nom;

	public String getNomCabinet() {
		return nomCabinet;
	}

	public void setNomCabinet(String nomCabinet) {
		this.nomCabinet = nomCabinet;
	}

	public void setNomCabinet(
		UnsafeSupplier<String, Exception> nomCabinetUnsafeSupplier) {

		try {
			nomCabinet = nomCabinetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String nomCabinet;

	public String getNumeroCabinet() {
		return numeroCabinet;
	}

	public void setNumeroCabinet(String numeroCabinet) {
		this.numeroCabinet = numeroCabinet;
	}

	public void setNumeroCabinet(
		UnsafeSupplier<String, Exception> numeroCabinetUnsafeSupplier) {

		try {
			numeroCabinet = numeroCabinetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String numeroCabinet;

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public void setPrenoms(
		UnsafeSupplier<String, Exception> prenomsUnsafeSupplier) {

		try {
			prenoms = prenomsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String prenoms;

	@Override
	public UpdateCabinetRequest clone() throws CloneNotSupportedException {
		return (UpdateCabinetRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateCabinetRequest)) {
			return false;
		}

		UpdateCabinetRequest updateCabinetRequest =
			(UpdateCabinetRequest)object;

		return Objects.equals(toString(), updateCabinetRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UpdateCabinetRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-698298545