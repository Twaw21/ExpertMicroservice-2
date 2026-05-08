<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfert de votre dossier - Information</title>
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
        .expert-info {
            background-color: #f0f9ff;
            border: 1px solid #bae6fd;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #0ea5e9;
        }
        .status-info {
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
            <h2><span class="icon">🔄</span>Transfert de votre dossier comptable</h2>
        </div>
        
        <div class="content">
            <p>Bonjour <strong>${nom_client!"M(e)."}</strong>,</p>
            
            <div class="notification-badge">
                <span class="icon">📨</span> Votre expert-comptable vous a transféré(e)
            </div>
            
            <div class="transfert-info">
                <p><strong>Votre expert-comptable actuel, <span class="highlight">${nom_expert_expediteur}</span>
                <#if nom_cabinet_expediteur??>
                    du cabinet <span class="highlight">${nom_cabinet_expediteur}</span>
                </#if>, a procédé au transfert de votre dossier vers un confrère expert-comptable.</strong></p>
                
                <#if reference_transfert??>
                <p><strong>Référence du transfert :</strong> <span class="reference-transfert">${reference_transfert}</span></p>
                </#if>
                
                <#if date_transfert??>
                <p><strong>Date du transfert :</strong> ${date_transfert}</p>
                </#if>
            </div>
            
            <div class="expert-info">
                <h4><span class="icon">👨‍💼</span>Votre nouvel expert-comptable</h4>
                <p><strong>Nom :</strong> ${nom_expert_destinataire!"En attente de validation"}</p>
                <#if nom_cabinet_destinataire??>
                <p><strong>Cabinet :</strong> ${nom_cabinet_destinataire}</p>
                </#if>
                <#if adresse_cabinet_destinataire??>
                <p><strong>Adresse :</strong> ${adresse_cabinet_destinataire}</p>
                </#if>
                <#if email_destinataire??>
                <p><strong>Email :</strong> <a href="mailto:${email_destinataire}">${email_destinataire}</a></p>
                </#if>
                <#if telephone_destinataire??>
                <p><strong>Téléphone :</strong> ${telephone_destinataire}</p>
                </#if>
            </div>
            
            <#if motif_transfert??>
            <div style="background-color: #fef5e7; border: 1px solid #ffeaa7; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <h4><span class="icon">📝</span>Motif du transfert</h4>
                <p>${motif_transfert}</p>
            </div>
            </#if>
            
            <#if message_client??>
            <div style="background-color: #e8f5e8; border: 1px solid #c3e6cb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <h4><span class="icon">💬</span>Message de votre expert-comptable</h4>
                <p><em>"${message_client}"</em></p>
            </div>
            </#if>
            
            <div class="status-info">
                <h3><span class="icon">⏳</span>Statut du transfert : En attente de validation</h3>
                <p><strong>Le nouvel expert-comptable doit maintenant valider ce transfert pour que celui-ci soit effectif.</strong></p>
                
                <p><strong>📧 Vous recevrez une notification par email dès que :</strong></p>
                <ul>
                    <li><strong>Le transfert est accepté :</strong> Votre nouveau suivi comptable commencera avec le nouvel expert</li>
                    <li><strong>Le transfert est refusé :</strong> Vous resterez sous le suivi de votre expert-comptable actuel</li>
                </ul>
            </div>
            
            <div class="next-steps">
                <h4><span class="icon">📋</span>Ce qui va se passer</h4>
                <ol>
                    <li><strong>Validation en cours :</strong> Le nouvel expert-comptable examine votre dossier</li>
                    <li><strong>Décision :</strong> Il acceptera ou refusera le transfert sous 
                    <#if delai_validation??>
                        ${delai_validation}
                    <#else>
                        7 jours ouvrés
                    </#if>
                    </li>
                    <li><strong>Notification :</strong> Vous serez informé(e) de sa décision par email</li>
                    <li><strong>Si accepté :</strong> Votre dossier complet sera transféré au nouvel expert</li>
                    <li><strong>Si refusé :</strong> Votre expert actuel continuera à gérer votre dossier</li>
                </ol>
            </div>
            
            <div style="background-color: #e8f5e8; border: 1px solid #c3e6cb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">✅</span>Ce qui est préservé lors du transfert :</strong></p>
                <ul>
                    <li>Tous vos documents comptables et justificatifs</li>
                    <li>L'historique complet de votre comptabilité</li>
                    <li>Vos déclarations fiscales précédentes</li>
                    <li>Les paramètres de votre dossier</li>
                    <li>La continuité de votre suivi comptable</li>
                </ul>
            </div>
            
            <div style="background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">💡</span>Bon à savoir :</strong></p>
                <ul>
                    <li><strong>Aucune action requise de votre part</strong> pour le moment</li>
                    <li>Votre dossier reste sécurisé pendant toute la procédure</li>
                    <li>En cas d'acceptation, vous serez contacté(e) par le nouvel expert</li>
                    <li>Vous pourrez poser toutes vos questions au nouvel expert-comptable</li>
                </ul>
            </div>
            
            <div style="background-color: #fff3cd; border: 1px solid #ffeaa7; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">📞</span>Questions ou préoccupations ?</strong></p>
                <p>Si vous avez des questions concernant ce transfert, vous pouvez contacter :</p>
                <ul>
                    <li><strong>Votre expert actuel :</strong> 
                    <#if nom_expert_expediteur??>
                        ${nom_expert_expediteur}
                    </#if>
                    <#if email_expediteur??>
                        - <a href="mailto:${email_expediteur}">${email_expediteur}</a>
                    </#if>
                    <#if telephone_expediteur??>
                        - ${telephone_expediteur}
                    </#if>
                    </li>
                    <li><strong>Le nouvel expert :</strong> 
                    <#if nom_expert_destinataire??>
                        ${nom_expert_destinataire}
                    </#if>
                    <#if email_destinataire??>
                        - <a href="mailto:${email_destinataire}">${email_destinataire}</a>
                    </#if>
                    <#if telephone_destinataire??>
                        - ${telephone_destinataire}
                    </#if>
                    </li>
                </ul>
            </div>
            
            <p>Nous vous tiendrons informé(e) de l'évolution de ce transfert dès que le nouvel expert-comptable aura pris sa décision.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>
                <#if nom_expert_expediteur??>
                <strong>${nom_expert_expediteur}</strong><br>
                </#if>
                <#if nom_cabinet_expediteur??>
                ${nom_cabinet_expediteur}<br>
                </#if>
                Expert-Comptable</p>
            </div>
            
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            
            <!--
            <p style="font-size: 12px; color: #888;">
                <strong>Service Client</strong><br>
                Email: ${email_support!"support@yourcompany.com"}<br>
                Téléphone: ${telephone_support!"01 23 45 67 89"}<br>
                <#if site_web??>Site web: <a href="${site_web}">${site_web}</a></#if>
            </p>
            -->
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Ce message a été envoyé automatiquement le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                <#if reference_transfert??>Référence du transfert : ${reference_transfert}<br></#if>
                Vous serez notifié(e) dès que le nouvel expert-comptable validera ou refusera ce transfert.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>En attente :</strong> Validation du transfert par le nouvel expert-comptable
                </p>
            </div>
        </div>
    </div>
</body>
</html>