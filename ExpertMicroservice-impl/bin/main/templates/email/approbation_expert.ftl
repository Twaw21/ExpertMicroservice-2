<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inscription validée - <#if type_creation == "cabinet">Cabinet<#elseif type_creation == "collaborateur">Collaborateur<#else>Expert-Comptable</#if></title>
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
        .validation-badge {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
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
        .inscription-info {
            background-color: #e8f5e8;
            border: 1px solid #c3e6cb;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #28a745;
        }
        .access-credentials {
            background-color: #fff3cd;
            border: 2px solid #ffc107;
            border-radius: 8px;
            padding: 20px;
            margin: 25px 0;
            border-left: 6px solid #ffc107;
        }
        .credential-item {
            background-color: #ffffff;
            border: 1px solid #dee2e6;
            border-radius: 4px;
            padding: 12px;
            margin: 10px 0;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            word-break: break-all;
        }
        .security-warning {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #dc3545;
        }
        .next-steps {
            background-color: #e3f2fd;
            border: 1px solid #90caf9;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .entity-info {
            background-color: #f0f9ff;
            border: 1px solid #bae6fd;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #0ea5e9;
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
            color: #28a745;
            font-weight: bold;
        }
        .login-button {
            background-color: #007bff;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin: 15px 0;
            font-weight: bold;
        }
        .login-button:hover {
            background-color: #0056b3;
            color: white;
            text-decoration: none;
        }
        .numero-ordre {
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
            <h2>
                <span class="icon"><#if type_creation == "cabinet">🏢<#elseif type_creation == "collaborateur">👥<#else>👨‍💼</#if></span>
                <#if type_creation == "cabinet">
                    Cabinet validé - Accès Expert-Comptable
                <#elseif type_creation == "collaborateur">
                    Inscription Collaborateur validée - Vos accès
                <#else>
                    Inscription validée - Accès Expert-Comptable
                </#if>
            </h2>
        </div>
        
        <div class="content">
            <p>Bonjour <strong>
                <#if type_creation == "cabinet">
                    ${nom_prenom!"M(e)."}
                <#elseif type_creation == "collaborateur">
                    ${nom_expert_comptable!"M(e)."}
                <#else>
                    ${nom_expert_comptable!"M(e)."}
                </#if>
            </strong>,</p>
            
            <div class="validation-badge">
                <span class="icon">🎉</span> 
                <#if type_creation == "cabinet">
                    Félicitations ! Votre cabinet a été validé
                <#elseif type_creation == "collaborateur">
                    Félicitations ! Votre inscription comme collaborateur a été validée
                <#else>
                    Félicitations ! Votre inscription a été validée
                </#if>
            </div>
            
            <div class="inscription-info">
                <#if type_creation == "cabinet">
                    <p><strong>Nous avons le plaisir de vous confirmer que la création de votre cabinet <span class="highlight">${nom_cabinet}</span> et l'inscription de l'expert-comptable <span class="highlight">${nom_expert_comptable}</span> ont été <span class="highlight">officiellement validées</span>.</strong></p>
                <#elseif type_creation == "collaborateur">
                    <p><strong>Nous avons le plaisir de vous confirmer que votre inscription en tant que collaborateur 
                    <#if statut_collaborateur??>
                        (<span class="highlight">${statut_collaborateur?upper_case}</span>)
                    </#if>
                    <#if nom_cabinet??>
                    <span class="highlight"> au sein du cabinet ${nom_cabinet}</span> 
                    </#if>
                    a été <span class="highlight">officiellement validée</span>.</strong></p>
                <#else>
                    <p><strong>Nous avons le plaisir de vous confirmer que votre inscription en tant qu'expert-comptable individuel a été <span class="highlight">officiellement validée</span>.</strong></p>
                </#if>
                
                <#if numero_ordre??>
                <p><strong>Votre numéro d'ordre :</strong> <span class="numero-ordre">${numero_ordre}</span></p>
                </#if>
                
                <#if date_validation??>
                <p><strong>Date de validation :</strong> ${date_validation?string("dd/MM/yyyy")}</p>
                </#if>
                
                <#if type_creation == "cabinet">
                    <p>Le cabinet est désormais autorisé à exercer et l'expert-comptable peut commencer son activité professionnelle.</p>
                <#elseif type_creation == "collaborateur">
                    <p>Vous êtes désormais autorisé(e) à exercer en tant que collaborateur au sein du cabinet, selon les prérogatives de votre statut.</p>
                <#else>
                    <p>Vous êtes désormais autorisé(e) à exercer la profession d'expert-comptable en indépendant.</p>
                </#if>
            </div>
            
            <div class="entity-info">
                <#if type_creation == "cabinet">
                    <h4><span class="icon">🏢</span>Informations du cabinet</h4>
                    <p><strong>Nom du cabinet :</strong> ${nom_cabinet!"Non spécifié"}</p>
                    <#if siret_cabinet??>
                    <p><strong>SIRET :</strong> ${siret_cabinet}</p>
                    </#if>
                    <#if adresse_cabinet??>
                    <p><strong>Adresse :</strong> ${adresse_cabinet}</p>
                    </#if>
                    <p><strong>Expert-Comptable responsable :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
                    <#if diplome_expert??>
                    <p><strong>Diplôme :</strong> ${diplome_expert}</p>
                    </#if>
                    <#if numero_dec??>
                    <p><strong>Numéro DEC :</strong> ${numero_dec}</p>
                    </#if>
                <#elseif type_creation == "collaborateur">
                    <h4><span class="icon">👥</span>Informations du collaborateur</h4>
                    <p><strong>Nom du collaborateur :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
                    <#if statut_collaborateur??>
                    <p><strong>Statut :</strong> 
                        <#if statut_collaborateur?lower_case == "associe">
                            Associé Expert-Comptable
                        <#elseif statut_collaborateur?lower_case == "coordinateur">
                            Coordinateur Expert-Comptable
                        <#elseif statut_collaborateur?lower_case == "adjoint">
                            Adjoint Expert-Comptable
                        <#else>
                            ${statut_collaborateur}
                        </#if>
                    </p>
                    </#if>
                    <p><strong>Cabinet de rattachement :</strong> ${nom_cabinet!"Non spécifié"}</p>
                    <#if adresse_cabinet??>
                    <p><strong>Adresse du cabinet :</strong> ${adresse_cabinet}</p>
                    </#if>
                    <#if responsable_cabinet??>
                    <p><strong>Expert-Comptable responsable du cabinet :</strong> ${responsable_cabinet}</p>
                    </#if>
                    <#if diplome_expert??>
                    <p><strong>Diplôme :</strong> ${diplome_expert}</p>
                    </#if>
                    <#if numero_dec??>
                    <p><strong>Numéro DEC :</strong> ${numero_dec}</p>
                    </#if>
                <#else>
                    <h4><span class="icon">👨‍💼</span>Informations de l'expert-comptable</h4>
                    <p><strong>Nom complet :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
                    <#if adresse_cabinet??>
                    <p><strong>Adresse d'exercice :</strong> ${adresse_cabinet}</p>
                    </#if>
                    <#if diplome_expert??>
                    <p><strong>Diplôme :</strong> ${diplome_expert}</p>
                    </#if>
                    <#if numero_dec??>
                    <p><strong>Numéro DEC :</strong> ${numero_dec}</p>
                    </#if>
                    <#if siret_cabinet??>
                    <p><strong>SIRET individuel :</strong> ${siret_cabinet}</p>
                    </#if>
                </#if>
            </div>
            
            <div class="access-credentials">
                <h3><span class="icon">🔐</span>Vos identifiants d'accès</h3>
                <p><strong>Pour accéder à votre espace professionnel, utilisez les identifiants suivants :</strong></p>
                
                <p><strong>Email / Identifiant :</strong></p>
                <div class="credential-item">${email_access}</div>
                
                <p><strong>Mot de passe temporaire :</strong></p>
                <div class="credential-item">${mot_de_passe_temporaire}</div>
                
                <#if url_connexion??>
                <p><strong>Lien de connexion :</strong></p>
                <a href="${url_connexion}" class="login-button">🔗 Accéder à mon espace</a>
                </#if>
            </div>
            
            <div class="security-warning">
                <h4><span class="icon">⚠️</span>Important - Sécurité</h4>
                <ul>
                    <li><strong>Changez impérativement votre mot de passe</strong> lors de votre première connexion</li>
                    <li>Conservez ces informations de manière sécurisée</li>
                    <li>Ne partagez jamais vos identifiants</li>
                    <li>Utilisez un mot de passe complexe (8 caractères minimum, majuscules, minuscules, chiffres)</li>
                    <li>Activez l'authentification à deux facteurs si disponible</li>
                </ul>
            </div>
            
            <div class="next-steps">
                <h4><span class="icon">🚀</span>Prochaines étapes</h4>
                <ol>
                    <li><strong>Première connexion :</strong> Connectez-vous avec vos identifiants temporaires</li>
                    <li><strong>Changement de mot de passe :</strong> Définissez un nouveau mot de passe sécurisé</li>
                    <li><strong>Profil professionnel :</strong> Complétez votre profil et vos informations</li>
                    <#if type_creation == "cabinet">
                        <li><strong>Configuration du cabinet :</strong> Paramétrez les informations de votre cabinet</li>
                        <li><strong>Gestion des utilisateurs :</strong> Ajoutez d'éventuels collaborateurs</li>
                    <#elseif type_creation == "collaborateur">
                        <li><strong>Découverte de l'environnement :</strong> Familiarisez-vous avec l'espace du cabinet</li>
                        <li><strong>Droits et accès :</strong> Consultez vos permissions selon votre statut</li>
                    <#else>
                        <li><strong>Configuration individuelle :</strong> Personnalisez votre espace de travail</li>
                    </#if>
                    <li><strong>Formation continue :</strong> Consultez vos obligations de formation</li>
                    <li><strong>Assurance professionnelle :</strong> Vérifiez vos couvertures d'assurance</li>
                </ol>
            </div>
            
            <div style="background-color: #e8f5e8; border: 1px solid #c3e6cb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">📋</span>Accès à votre espace professionnel :</strong></p>
                <ul>
                    <#if type_creation == "cabinet">
                        <li>Gestion globale du cabinet et des collaborateurs</li>
                        <li>Supervision des dossiers clients</li>
                        <li>Tableaux de bord et statistiques</li>
                        <li>Administration et paramétrage</li>
                    <#elseif type_creation == "collaborateur">
                        <li>Gestion de vos dossiers clients assignés</li>
                        <li>Accès aux ressources communes du cabinet</li>
                        <li>Outils de collaboration et communication</li>
                        <li>Suivi de votre activité personnelle</li>
                    <#else>
                        <li>Gestion complète de vos dossiers clients</li>
                        <li>Espace de travail personnel</li>
                    </#if>
                    <li>Déclarations et obligations fiscales</li>
                    <li>Formation continue obligatoire</li>
                    <li>Actualités professionnelles</li>
                    <li>Outils et ressources métier</li>
                </ul>
            </div>
            
            <div style="background-color: #fef3c7; border: 1px solid #fbbf24; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">⚠️</span>Rappel important :</strong> 
                <#if type_creation == "cabinet">
                    Votre cabinet est maintenant habilité à exercer toutes les missions d'expertise comptable. En tant qu'expert-comptable responsable, vous pouvez signer tous les documents officiels.
                <#elseif type_creation == "collaborateur">
                    En tant que collaborateur 
                    <#if statut_collaborateur??>
                        <#if statut_collaborateur?lower_case == "associe">
                            associé, vous pouvez exercer toutes les missions d'expertise comptable et participer aux décisions stratégiques du cabinet.
                        <#elseif statut_collaborateur?lower_case == "coordinateur">
                            coordinateur, vous pouvez superviser des équipes et coordonner des missions d'expertise comptable au sein du cabinet.
                        <#elseif statut_collaborateur?lower_case == "adjoint">
                            adjoint, vous assistez dans les missions d'expertise comptable sous supervision de l'expert-comptable responsable.
                        <#else>
                            vous exercez selon les prérogatives définies par votre statut au sein du cabinet.
                        </#if>
                    <#else>
                        vous exercez selon les prérogatives définies par votre statut au sein du cabinet.
                    </#if>
                <#else>
                    Vous êtes maintenant habilité(e) à exercer toutes les missions d'expertise comptable en votre nom propre et pouvez signer tous les documents officiels.
                </#if>
                </p>
            </div>
            
            <div style="background-color: #d1ecf1; border: 1px solid #bee5eb; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">💡</span>Besoin d'aide ?</strong> Notre équipe support est à votre disposition pour vous accompagner dans la prise en main de votre espace professionnel.</p>
            </div>
            
            <p><#if type_creation == "cabinet">Nous vous souhaitons une excellente réussite dans la gestion de votre cabinet<#elseif type_creation == "collaborateur">Nous vous souhaitons une excellente intégration au sein de votre nouveau cabinet<#else>Nous vous souhaitons une excellente continuation dans l'exercice de votre profession</#if> d'expert-comptable.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>
                <strong>Service des <#if type_creation == "cabinet">Inscriptions de Cabinets<#elseif type_creation == "collaborateur">Inscriptions de Collaborateurs<#else>Inscriptions Individuelles</#if></strong><br>
                Ordre des Experts-Comptables</p>
            </div>
            
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            
            <!--
            <p style="font-size: 12px; color: #888;">
                <strong>Support Technique - Ordre des Experts-Comptables</strong><br>
                Email: ${email_support!"<#if type_creation == 'cabinet'>support-cabinets<#elseif type_creation == 'collaborateur'>support-collaborateurs<#else>support-individuels</#if>@experts-comptables.fr"}<br>
                Téléphone: ${telephone_support!"01 44 15 60 00"}<br>
                <#if site_web??>Site web: <a href="${site_web}">${site_web}</a><#else>Site web: <a href="https://www.experts-comptables.fr">www.experts-comptables.fr</a></#if>
            </p>
            -->
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Ce message a été envoyé automatiquement le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                Merci de ne pas répondre directement à cet email. Pour toute question, utilisez les coordonnées ci-dessus.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Bienvenue</strong> 
                    <#if type_creation == "cabinet">
                        dans la gestion de votre cabinet d'expertise comptable !
                    <#elseif type_creation == "collaborateur">
                        dans votre nouveau cabinet en tant que collaborateur !
                    <#else>
                        dans la communauté des experts-comptables indépendants !
                    </#if>
                </p>
            </div>
        </div>
    </div>
</body>
</html>