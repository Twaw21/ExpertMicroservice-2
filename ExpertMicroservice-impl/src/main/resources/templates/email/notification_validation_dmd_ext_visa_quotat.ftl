<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Décision sur votre demande d'extension de quota visa</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .email-container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header-accepted {
            border-bottom: 2px solid #28a745;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .header-refused {
            border-bottom: 2px solid #dc3545;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .badge-accepted {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
            font-size: 16px;
        }
        .badge-refused {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 15px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
            font-size: 16px;
        }
        .notice-accepted {
            background-color: #e7f7e7;
            border: 1px solid #b8e6b8;
            border-left: 4px solid #28a745;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .notice-refused {
            background-color: #fdf3f4;
            border: 1px solid #f5c6cb;
            border-left: 4px solid #dc3545;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .notice-accepted p, .notice-refused p { margin: 6px 0; }
        .info-box {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .info-item {
            margin: 10px 0;
            padding: 8px 0;
            border-bottom: 1px dotted #ccc;
        }
        .info-item:last-child { border-bottom: none; }
        .info-label {
            font-weight: bold;
            color: #495057;
            display: inline-block;
            width: 180px;
        }
        .order-reference {
            background-color: #e9ecef;
            padding: 4px 10px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            color: #495057;
        }
        .quota-highlight {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            text-align: center;
        }
        .quota-highlight .quota-number {
            font-size: 32px;
            font-weight: bold;
            color: #155724;
        }
        .quota-highlight .quota-label {
            font-size: 13px;
            color: #155724;
        }
        .motif-box {
            background-color: #f8f9fa;
            border-left: 4px solid #dc3545;
            padding: 12px 16px;
            border-radius: 0 6px 6px 0;
            margin: 15px 0;
            font-style: italic;
            color: #495057;
        }
        .info-tip {
            background-color: #e3f2fd;
            border: 1px solid #90caf9;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            font-size: 14px;
        }
        .action-button {
            display: inline-block;
            background-color: #007bff;
            color: #ffffff !important;
            text-decoration: none;
            padding: 14px 32px;
            border-radius: 5px;
            font-weight: bold;
            font-size: 15px;
        }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            font-size: 13px;
            color: #666;
        }
        .icon { font-size: 18px; margin-right: 6px; }
    </style>
</head>
<body>
    <div class="email-container">

        <#if is_accepted>
        <div class="header-accepted">
            <h2><span class="icon">✅</span>Demande d'extension de quota visa acceptée</h2>
        </div>
        <#else>
        <div class="header-refused">
            <h2><span class="icon">❌</span>Demande d'extension de quota visa refusée</h2>
        </div>
        </#if>

        <p>Bonjour <strong>${nom_expert!"M(e)."}</strong>,</p>

        <#if is_accepted>
        <div class="badge-accepted">
            <span class="icon">🎉</span> Votre demande d'extension de quota visa a été acceptée
        </div>

        <div class="notice-accepted">
            <p>
                <strong>${nom_admin_ordre!"L'administrateur de l'ordre"}</strong>
                a examiné votre demande d'extension de quota de visas signés
                et a rendu une décision favorable.
            </p>
            <p>
                Vous pouvez désormais signer un nombre étendu de visas sur la plateforme.
            </p>
        </div>

        <div class="quota-highlight">
            <div class="quota-number">${max_limit_visa!"—"}</div>
            <div class="quota-label">Nombre maximum de visas que vous pouvez désormais signer</div>
        </div>

        <div class="info-tip">
            <p>
                <span class="icon">💡</span>
                <strong>Bon à savoir :</strong> Votre quota étendu est maintenant actif.
                Connectez-vous à la plateforme pour continuer à traiter vos dossiers de visa.
            </p>
        </div>

        <#else>
        <div class="badge-refused">
            <span class="icon">⚠️</span> Votre demande d'extension de quota visa a été refusée
        </div>

        <div class="notice-refused">
            <p>
                <strong>${nom_admin_ordre!"L'administrateur de l'ordre"}</strong>
                a examiné votre demande d'extension de quota de visas signés
                et n'a pas pu y donner une suite favorable pour le moment.
            </p>
            <p>Vous pourrez soumettre une nouvelle demande ultérieurement.</p>
        </div>

        <#if motif_refus?? && motif_refus != "">
        <div>
            <h4><span class="icon">💬</span>Motif du refus</h4>
            <div class="motif-box">${motif_refus}</div>
        </div>
        </#if>

        <div class="info-tip">
            <p>
                <span class="icon">💡</span>
                <strong>Que faire maintenant ?</strong>
                Prenez connaissance du motif de refus ci-dessus et
                adressez-vous à l'administrateur de l'ordre pour plus d'informations
                avant de soumettre une nouvelle demande.
            </p>
        </div>
        </#if>

        <div class="info-box">
            <h4><span class="icon">📋</span>Détails de la demande traitée</h4>

            <div class="info-item">
                <span class="info-label">Référence :</span>
                <span class="info-value">
                    <span class="order-reference">${reference_demande!"—"}</span>
                </span>
            </div>

            <div class="info-item">
                <span class="info-label">Statut :</span>
                <span class="info-value">
                    <strong>${statut!"—"}</strong>
                </span>
            </div>

            <div class="info-item">
                <span class="info-label">Traité par :</span>
                <span class="info-value">${nom_admin_ordre!"—"}</span>
            </div>

            <div class="info-item">
                <span class="info-label">Date de décision :</span>
                <span class="info-value">${.now?string("dd/MM/yyyy à HH:mm")}</span>
            </div>
        </div>

        <p style="text-align: center; margin-top: 20px;">
            <a href="${lien_plateforme!"#"}" class="action-button">
                🔗 Accéder à la plateforme
            </a>
        </p>

        <p>
            Pour toute question, n'hésitez pas à contacter l'administrateur de l'ordre.
        </p>

        <div class="footer">
            <p>Cordialement.</p>
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            <p style="font-size: 11px; color: #aaa;">
                Ce message a été envoyé automatiquement le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                Merci de ne pas répondre directement à cet email.
            </p>
            <div style="text-align: center; margin-top: 15px; padding: 12px;
                        background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Sécurité :</strong> Ne partagez jamais vos identifiants de connexion.
                </p>
            </div>
        </div>

    </div>
</body>
</html>
