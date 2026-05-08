package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.StatutRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class StatutRequest implements Cloneable, Serializable {

	public static StatutRequest toDTO(String json) {
		return StatutRequestSerDes.toDTO(json);
	}

	public String getMotif_refus() {
		return motif_refus;
	}

	public void setMotif_refus(String motif_refus) {
		this.motif_refus = motif_refus;
	}

	public void setMotif_refus(
		UnsafeSupplier<String, Exception> motif_refusUnsafeSupplier) {

		try {
			motif_refus = motif_refusUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String motif_refus;

	public Option getStatut() {
		return statut;
	}

	public void setStatut(Option statut) {
		this.statut = statut;
	}

	public void setStatut(
		UnsafeSupplier<Option, Exception> statutUnsafeSupplier) {

		try {
			statut = statutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Option statut;

	@Override
	public StatutRequest clone() throws CloneNotSupportedException {
		return (StatutRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StatutRequest)) {
			return false;
		}

		StatutRequest statutRequest = (StatutRequest)object;

		return Objects.equals(toString(), statutRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return StatutRequestSerDes.toJSON(this);
	}

}