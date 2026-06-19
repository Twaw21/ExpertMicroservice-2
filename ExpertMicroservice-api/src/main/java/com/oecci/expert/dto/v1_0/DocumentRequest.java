package com.oecci.expert.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO générique pour le transport d'un document en base64.
 * Réutilisable pour tout upload de fichier dans l'API.
 *
 * @author OECCI / DiginFactory
 * @generated
 */
@Generated("")
@XmlRootElement(name = "DocumentRequest")
public class DocumentRequest {

    /**
     * Nom du fichier avec extension (ex : "preuve_remboursement.pdf").
     */
    @JsonProperty("documentName")
    private String _documentName;

    /**
     * Contenu du fichier encodé en Base64 (sans le préfixe data URI).
     * Ex : "JVBERi0xLjQK..."
     */
    @JsonProperty("documentContent")
    private String _documentContent;

    public String getDocumentName() {
        return _documentName;
    }

    public void setDocumentName(String documentName) {
        _documentName = documentName;
    }

    public String getDocumentContent() {
        return _documentContent;
    }

    public void setDocumentContent(String documentContent) {
        _documentContent = documentContent;
    }

    @Override
    public String toString() {
        return "DocumentRequest{"
            + "documentName=" + _documentName
            + ", documentContent=[base64, length="
            + (_documentContent != null ? _documentContent.length() : 0)
            + "]}";
    }

}