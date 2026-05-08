<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Votre compte collaborateur a été créé</title>
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
            border-bottom: 2px solid #28a745;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .success-badge {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
        }
        .content {
            margin-bottom: 20px;
        }
        .approval-notice {
            background-color: #e7f7e7;
            border: 1px solid #b8e6b8;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .connection-button {
            display: inline-block;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 15px 30px;
            border-radius: 5px;
            margin: 15px 0;
            font-weight: bold;
            font-size: 16px;
        }
        .connection-button:hover {
            background-color: #218838;
        }
        .credentials-box {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .credential-item {
            margin: 10px 0;
            padding: 8px 0;
            border-bottom: 1px dotted #ccc;
        }
        .credential-item:last-child {
            border-bottom: none;
        }
        .credential-label {
            font-weight: bold;
            color: #495057;
            display: inline-block;
            width: 120px;
        }
        .credential-value {
            color: #007bff;
            font-family: 'Courier New', monospace;
            background-color: #e9ecef;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .security-notice {
            background-color: #fff3cd;
            border: 1px solid #ffeaa7;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            font-size: 14px;
        }
        .creator-info {
            background-color: #f0f8ff;
            border: 1px solid #b3d9e6;
            border-radius: 6px;
            padding: 15px;
            margin: 15px 0;
        }
        .role-badge {
            display: inline-block;
            padding: 4px 12px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
            margin: 0 5px;
        }
        .role-admin {
            background-color: #dc3545;
            color: white;
        }
        .role-moderateur {
            background-color: #fd7e14;
            color: white;
        }
        .role-assistant {
            background-color: #6f42c1;
            color: white;
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
            font-size: 20px;
            margin-right: 8px;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <h2><span class="icon">👥</span>Votre compte collaborateur a été créé</h2>
        </div>
        
        <div class="content">
            <p>Bonjour <strong>${nom_prenom!"M(e)."}</strong>,</p>
            
            <div class="success-badge">
                <span class="icon">🎉</span> Félicitations ! Votre compte collaborateur a été créé avec succès
            </div>
            
            <div class="approval-notice">
                <p><strong>Un compte collaborateur a été créé pour vous sur la plateforme.</strong></p>
                
                <#if date_creation??>
                <p><strong>Date de création :</strong> ${date_creation?string("dd/MM/yyyy à HH:mm")}</p>
                </#if>
                
                <p>Vous pouvez maintenant vous connecter à la plateforme et commencer à utiliser vos nouveaux accès.</p>
            </div>
            
            <div class="creator-info">
                <h4><span class="icon">👤</span>Compte créé par</h4>
                <div class="credential-item">
                    <span class="credential-label">Créateur :</span>
                    <span>${nom_createur!"Non spécifié"}</span>
                </div>
                <#if email_createur??>
                <div class="credential-item">
                    <span class="credential-label">Email :</span>
                    <span>${email_createur}</span>
                </div>
                </#if>
                <#if role_createur??>
                <div class="credential-item">
                    <span class="credential-label">Rôle :</span>
                    <#if role_createur == "admin">
                        <span class="role-badge role-admin">Administrateur</span>
                    <#elseif role_createur == "moderateur">
                        <span class="role-badge role-moderateur">Modérateur</span>
                    <#elseif role_createur == "assistant">
                        <span class="role-badge role-assistant">Assistant</span>
                    <#else>
                        <span class="role-badge" style="background-color: #6c757d; color: white;">${role_createur}</span>
                    </#if>
                </div>
                </#if>
                <#if role_demande?? && role_demande != role_assigne>
                <div class="credential-item">
                    <span class="credential-label">Rôle demandé :</span>
                    <#if role_demande == "admin">
                        <span class="role-badge role-admin">Administrateur</span>
                    <#elseif role_demande == "moderateur">
                        <span class="role-badge role-moderateur">Modérateur</span>
                    <#elseif role_demande == "assistant">
                        <span class="role-badge role-assistant">Assistant</span>
                    <#else>
                        <span class="role-badge" style="background-color: #6c757d; color: white;">${role_demande}</span>
                    </#if>
                    <small style="color: #6c757d; font-style: italic;">(modifié par le créateur)</small>
                </div>
                </#if>
            </div>
            
            <p style="text-align: center;">
                <a href="${lien_portail!"#"}" class="connection-button">
                    🔗 Se connecter à la plateforme
                </a>
            </p>
            
            <div class="security-notice">
                <h4><span class="icon">🔐</span>Informations importantes pour votre première connexion</h4>
                <p><strong>Définissez un nouveau mot de passe lors de votre première connexion, car celui-ci est temporaire :</strong></p>
            </div>
            
            <div class="credentials-box">
                <h4><span class="icon">👤</span>Vos identifiants de connexion :</h4>
                
                <div class="credential-item">
                    <span class="credential-label">Email :</span>
                    <span class="credential-value">${email_collaborateur!"[email_collaborateur]"}</span>
                </div>
                
                <div class="credential-item">
                    <span class="credential-label">Mot de passe :</span>
                    <span class="credential-value">${mot_de_passe!"[mot_de_passe]"}</span>
                </div>
                
                <#if role_assigne??>
                <div class="credential-item">
                    <span class="credential-label">Votre rôle :</span>
                    <#if role_assigne == "admin">
                        <span class="role-badge role-admin">Administrateur</span>
                    <#elseif role_assigne == "moderateur">
                        <span class="role-badge role-moderateur">Modérateur</span>
                    <#elseif role_assigne == "assistant">
                        <span class="role-badge role-assistant">Assistant</span>
                    <#else>
                        <span class="role-badge" style="background-color: #6c757d; color: white;">${role_assigne}</span>
                    </#if>
                </div>
                </#if>
            </div>
            
            <#if permissions_role??>
            <div style="background-color: #e8f4f8; border: 1px solid #b3d9e6; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <h4><span class="icon">🔐</span>Vos permissions et accès :</h4>
                <ul>
                <#list permissions_role as permission>
                    <li>${permission}</li>
                </#list>
                </ul>
            </div>
            </#if>
            
            <div style="background-color: #e3f2fd; border: 1px solid #90caf9; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">⚠️</span>Important :</strong> Pour des raisons de sécurité, nous vous recommandons fortement de modifier votre mot de passe temporaire lors de votre première connexion.</p>
            </div>
            
            <#if lien_guide??>
            <div style="background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">📚</span>Besoin d'aide ?</strong> Consultez notre <a href="${lien_guide}">guide d'utilisation</a> pour bien commencer sur la plateforme.</p>
            </div>
            </#if>
            
            <p>Si vous n'êtes pas à l'origine de cette création de compte ou si vous pensez qu'il y a une erreur, veuillez contacter immédiatement notre service support.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>L'équipe de gestion des collaborateurs</p>
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
                Merci de ne pas répondre directement à cet email.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Sécurité :</strong> Ne partagez jamais vos identifiants de connexion avec des tiers.
                </p>
            </div>
        </div>
    </div>
</body>
</html>