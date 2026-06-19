package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author OECCI / DiginFactory
 * @generated
 */
@Generated("")
@XmlRootElement(name = "UpdateRecouvrementRequest")
public class UpdateRecouvrementRequest {

    /**
     * Document de preuve de remboursement (encodé en base64).
     * Obligatoire pour valider le recouvrement.
     */
    @JsonProperty("document")
    private DocumentRequest _document;

    public DocumentRequest getDocument() {
        return _document;
    }

    public void setDocument(DocumentRequest document) {
        _document = document;
    }

    @Override
    public String toString() {
        return "UpdateRecouvrementRequest{document=" + _document + "}";
    }

}