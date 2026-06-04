<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouvelle demande d'extension de quota visa</title>
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
        .header {
            border-bottom: 2px solid #007bff;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .notification-badge {
            background-color: #cce5ff;
            border: 1px solid #b8daff;
            color: #004085;
            padding: 15px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
            font-size: 16px;
        }
        .demande-notice {
            background-color: #e8f0fe;
            border: 1px solid #b3c8f5;
            border-left: 4px solid #007bff;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .demande-notice p {
            margin: 6px 0;
        }
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
        .info-item:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: bold;
            color: #495057;
            display: inline-block;
            width: 180px;
        }
        .info-value {
            color: #212529;
        }
        .order-reference {
            background-color: #e9ecef;
            padding: 4px 10px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            color: #495057;
        }
        .quota-box {
            background-color: #fff3cd;
            border: 1px solid #ffc107;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
        }
        .quota-bar-label {
            font-size: 13px;
            color: #666;
            margin-bottom: 4px;
        }
        .action-section {
            text-align: center;
            margin: 25px 0;
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
        .motif-box {
            background-color: #f8f9fa;
            border-left: 4px solid #6c757d;
            padding: 12px 16px;
            border-radius: 0 6px 6px 0;
            margin: 15px 0;
            font-style: italic;
            color: #495057;
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

        <div class="header">
            <h2><span class="icon">📋</span>Nouvelle demande d'extension de quota visa</h2>
        </div>

        <div class="notification-badge">
            <span class="icon">🔔</span> Une demande d'extension de quota requiert votre attention
        </div>

        <p>Bonjour <strong>${nom_admin_ordre!"M(e)."}</strong>,</p>

        <div class="demande-notice">
            <p>
                L'expert comptable <strong>${nom_expert!"—"}</strong>
                vient de soumettre une demande d'extension de son quota de visas signés.
            </p>
            <p>Cette demande est en attente de votre décision.</p>
        </div>

        <div class="info-box">
            <h4><span class="icon">👤</span>Informations sur la demande</h4>

            <div class="info-item">
                <span class="info-label">Référence :</span>
                <span class="info-value">
                    <span class="order-reference">${reference_demande!"—"}</span>
                </span>
            </div>

            <div class="info-item">
                <span class="info-label">Expert demandeur :</span>
                <span class="info-value">${nom_expert!"—"}</span>
            </div>

            <div class="info-item">
                <span class="info-label">Email de l'expert :</span>
                <span class="info-value">${email_expert!"—"}</span>
            </div>

            <div class="info-item">
                <span class="info-label">Date de la demande :</span>
                <span class="info-value">${date_demande!"—"}</span>
            </div>
        </div>

        <div class="quota-box">
            <h4><span class="icon">📊</span>Situation du quota actuel</h4>
            <p class="quota-bar-label">
                Visas déjà signés : <strong>${current_visa_count!"0"}</strong>
                &nbsp;|&nbsp;
                Plafond actuel (min) : <strong>${min_limit_visa!"—"}</strong>
                &nbsp;|&nbsp;
                Plafond étendu (max) : <strong>${max_limit_visa!"—"}</strong>
            </p>
            <p style="margin-top: 10px; font-size: 13px; color: #856404;">
                Si vous acceptez cette demande, l'expert pourra signer jusqu'à
                <strong>${max_limit_visa!"—"}</strong> visas au lieu de
                <strong>${min_limit_visa!"—"}</strong>.
            </p>
        </div>

        <#if motif?? && motif != "">
        <div>
            <h4><span class="icon">💬</span>Motif invoqué par l'expert</h4>
            <div class="motif-box">${motif}</div>
        </div>
        </#if>

        <div class="action-section">
            <p><strong><span class="icon">⚡</span>Action requise</strong></p>
            <p>Connectez-vous à la plateforme pour examiner la demande et rendre votre décision.</p>
            <a href="${lien_plateforme!"#"}" class="action-button">
                🔗 Accéder à la plateforme
            </a>
        </div>

        <p>Si vous pensez avoir reçu cet email par erreur, vous pouvez l'ignorer.</p>

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
