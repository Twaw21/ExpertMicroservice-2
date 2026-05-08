<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfert de client - Validation requise</title>
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
            border-bottom: 2px solid #17a2b8;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .notification-badge {
            background-color: #d1ecf1;
            border: 1px solid #bee5eb;
            color: #0c5460;
            padding: 20px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
            font-size: 18px;
        }
        .content {
            margin-bottom: 20px;
        }
        .transfert-info {
            background-color: #e8f4f8;
            border: 1px solid #b3d9e6;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #17a2b8;
        }
        .client-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #6c757d;
        }
        .action-required {
            background-color: #fff3cd;
            border: 2px solid #ffc107;
            border-radius: 8px;
            padding: 20px;
            margin: 25px 0;
            border-left: 6px solid #ffc107;
        }
        .next-steps {
            background-color: #e3f2fd;
            border: 1px solid #90caf9;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .footer {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
            font-size: 14px;
            color: #666;
        }
        .signature {
            margin-top: 20px;
            font-weight: bold;
        }
        .icon {
            font-size: 18px;
            margin-right: 8px;
        }
        .highlight {
            color: #17a2b8;
            font-weight: bold;
        }
        .action-button {
            background-color: #28a745;
            color: white;
            padding: 15px 30px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin: 15px 0;
            font-weight: bold;
            font-size: 16px;
        }
        .action-button:hover {
            background-color: #218838;
            color: white;
            text-decoration: none;
        }
        .reference-transfert {
            background-color: #e9ecef;
            padding: 8px 12px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            color: #495057;
            display: inline-block;
            margin: 5px 0;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <h2><span class="icon">ðŸ”„</span>Transfert de client - Validation requise</h2>
        </div>
        
        <div class="content">
            <p>Bonjour <strong>${nom_expert_destinataire!"M(e)."}</strong>,</p>
            
            <div class="notification-badge">
                <span class="icon">ðŸ“¨</span> Un confrÃ¨re vous a transfÃ©rÃ© un client
            </div>
            
            <div class="transfert-info">
                <p><strong>L'expert-comptable <span class="highlight">${nom_expert_expediteur}</span>
                <#if nom_cabinet_expediteur??>
                    du cabinet <span class="highlight">${nom_cabinet_expediteur}</span>
                </#if>
                vous a transfÃ©rÃ© son client <span class="highlight">${nom_client}</span>.</strong></p>
                
                <#if reference_transfert??>
                <p><strong>RÃ©fÃ©rence du transfert :</strong> <span class="reference-transfert">${reference_transfert}</span></p>
                </#if>
                
                <#if date_transfert??>
                <p><strong>Date du transfert :</strong> ${date_transfert?string("dd/MM/yyyy Ã  HH:mm")}</p>
                </#if>
            </div>
            
            <div class="client-info">
                <h4><span class="icon">ðŸ‘¤</span>Informations du client</h4>
                <p><strong>Nom du client :</strong> ${nom_client!"Non spÃ©cifiÃ©"}</p>
                <#if email_client??>
                <p><strong>Email :</strong> ${email_client}</p>
                </#if>
                <#if telephone_client??>
                <p><strong>TÃ©lÃ©phone :</strong> ${telephone_client}</p>
                </#if>
                <#if entreprise_client??>
                <p><strong>Entreprise :</strong> ${entreprise_client}</p>
                </#if>
                <#if siret_client??>
                <p><strong>SIRET :</strong> ${siret_client}</p>
                </#if>
                <#if adresse_client??>
                <p><strong>Adresse :</strong> ${adresse_client}</p>
                </#if>
            </div>
            
            <#if motif_transfert??>
            <div style="background-color: #f0f9ff; border: 1px solid #bae6fd; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <h4><span class="icon">ðŸ“�</span>Motif du transfert</h4>
                <p>${motif_transfert}</p>
            </div>
            </#if>
            
            <#if commentaire_expediteur??>
            <div style="background-color: #fef5e7; border: 1px solid #ffeaa7; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <h4><span class="icon">ðŸ’¬</span>Message de votre confrÃ¨re</h4>
                <p><em>"${commentaire_expediteur}"</em></p>
            </div>
            </#if>
            
            <div class="action-required">
                <h3><span class="icon">âš ï¸�</span>Action requise - Validation du transfert</h3>
                <p><strong>Pour accepter ce client dans votre portefeuille, vous devez vous connecter Ã  votre espace professionnel et valider ce transfert.</strong></p>
                
                <p><strong>Deux options s'offrent Ã  vous :</strong></p>
                <ul>
                    <li><strong>Accepter le transfert :</strong> Le client sera ajoutÃ© Ã  votre portefeuille et recevra une notification</li>
                    <li><strong>Refuser le transfert :</strong> Le client restera sous la responsabilitÃ© de l'expert-comptable actuel</li>
                </ul>
                
                <#if lien_validation??>
                <div style="text-align: center; margin-top: 20px;">
                    <a href="${lien_validation}" class="action-button">âœ… Valider ou refuser le transfert</a>
                </div>
                </#if>
            </div>
            
            <div class="next-steps">
                <h4><span class="icon">ðŸ“‹</span>Processus de validation</h4>
                <ol>
                    <li><strong>Connexion :</strong> Connectez-vous Ã  votre espace expert-comptable</li>
                    <li><strong>Consultation :</strong> Consultez les dÃ©tails complets du dossier client</li>
                    <li><strong>DÃ©cision :</strong> Acceptez ou refusez le transfert selon votre disponibilitÃ©</li>
                    <li><strong>Notification :</strong> Le client sera automatiquement notifiÃ© de votre dÃ©cision</li>
                    <#if delai_validation??>
                    <li><strong>DÃ©lai :</strong> ${delai_validation}</li>
                    <#else>
                    <li><strong>DÃ©lai :</strong> Veuillez traiter cette demande sous 7 jours ouvrÃ©s</li>
                    </#if>
                </ol>
            </div>
            
            <div style="background-color: #e8f5e8; border: 1px solid #c3e6cb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">ðŸ“Œ</span>Important :</strong></p>
                <ul>
                    <li>Le client a Ã©tÃ© informÃ© du transfert et attend votre validation</li>
                    <li>En acceptant, vous devenez responsable de la gestion comptable de ce client</li>
                    <li>Tous les documents et historiques seront transfÃ©rÃ©s si vous acceptez</li>
                    <li>Si vous refusez, l'expert-comptable actuel en sera informÃ©</li>
                </ul>
            </div>
            
            <div style="background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">ðŸ’¡</span>Besoin d'informations complÃ©mentaires ?</strong></p>
                <p>Vous pouvez contacter votre confrÃ¨re 
                <#if nom_expert_expediteur??>
                    <strong>${nom_expert_expediteur}</strong>
                </#if>
                <#if email_expediteur??>
                    Ã  l'adresse <a href="mailto:${email_expediteur}">${email_expediteur}</a>
                </#if>
                <#if telephone_expediteur??>
                    ou par tÃ©lÃ©phone au ${telephone_expediteur}
                </#if>
                pour obtenir plus de dÃ©tails sur ce client.</p>
            </div>
            
            <p>Nous vous remercions de traiter cette demande dans les meilleurs dÃ©lais.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>
                <strong>Service Gestion des Transferts</strong><br>
                Ordre des Experts-Comptables</p>
            </div>
            
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            
            <!--
            <p style="font-size: 12px; color: #888;">
                <strong>Service Transferts - Ordre des Experts-Comptables</strong><br>
                Email: ${email_support!"transferts@experts-comptables.fr"}<br>
                TÃ©lÃ©phone: ${telephone_support!"01 44 15 60 00"}<br>
                <#if site_web??>Site web: <a href="${site_web}">${site_web}</a></#if>
            </p>
            -->
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Ce message a Ã©tÃ© envoyÃ© automatiquement le ${.now?string("dd/MM/yyyy Ã  HH:mm")}.<br>
                <#if reference_transfert??>RÃ©fÃ©rence du transfert : ${reference_transfert}<br></#if>
                Merci de ne pas rÃ©pondre directement Ã  cet email.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Action requise :</strong> Veuillez valider ou refuser ce transfert
                </p>
            </div>
        </div>
    </div>
</body>
</html>