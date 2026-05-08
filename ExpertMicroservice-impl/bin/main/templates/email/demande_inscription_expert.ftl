<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirmation de création - <#if type_creation == "cabinet">Cabinet<#elseif type_creation == "collaborateur">Collaborateur Expert-Comptable<#else>Expert-Comptable</#if></title>
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
        .confirmation-badge {
            background-color: #d1ecf1;
            border: 1px solid #bee5eb;
            color: #0c5460;
            padding: 15px;
            border-radius: 6px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
        }
        .content {
            margin-bottom: 20px;
        }
        .submission-notice {
            background-color: #e8f4f8;
            border: 1px solid #b3d9e6;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #17a2b8;
        }
        .status-info {
            background-color: #fff3cd;
            border: 1px solid #ffeaa7;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
        }
        .next-steps {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
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
            color: #007bff;
            font-weight: bold;
        }
        .order-reference {
            background-color: #e9ecef;
            padding: 8px 12px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            color: #495057;
            display: inline-block;
            margin: 5px 0;
        }
        .entity-info {
            background-color: #f0f9ff;
            border: 1px solid #bae6fd;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #0ea5e9;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <h2>
                <span class="icon"><#if type_creation == "cabinet">🏢<#elseif type_creation == "collaborateur">👥<#else>👨‍💼</#if></span>
                Confirmation de création - <#if type_creation == "cabinet">Cabinet<#elseif type_creation == "collaborateur">Collaborateur Expert-Comptable<#else>Expert-Comptable</#if>
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
            
            <div class="confirmation-badge">
                <span class="icon">✅</span> 
                <#if type_creation == "cabinet">
                    Demande de création de cabinet soumise avec succès
                <#elseif type_creation == "collaborateur">
                    Demande d'inscription en tant que collaborateur soumise avec succès
                <#else>
                    Demande d'inscription individuelle soumise avec succès
                </#if>
            </div>
            
            <div class="submission-notice">
                <#if type_creation == "cabinet">
                    <p><strong>Votre demande de création du cabinet <span class="highlight">${nom_cabinet}</span> avec inscription de l'expert-comptable <span class="highlight">${nom_expert_comptable}</span> a été soumise avec succès auprès de l'Ordre des Experts-Comptables.</strong></p>
                <#elseif type_creation == "collaborateur">
                    <p><strong>Votre demande d'inscription de <span class="highlight">${nom_expert_comptable}</span> en tant que collaborateur 
                    <#if statut_collaborateur??>
                        (<span class="highlight">${statut_collaborateur?upper_case}</span>)
                    </#if>
                     a été soumise avec succès auprès de l'Ordre des Experts-Comptables.</strong></p>
                <#else>
                    <p><strong>Votre demande d'inscription de <span class="highlight">${nom_expert_comptable}</span> en tant qu'expert-comptable individuel a été soumise avec succès auprès de l'Ordre des Experts-Comptables.</strong></p>
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
                      <#if numero_ordre??>
		                <p><strong>Votre numéro d'ordre :</strong> <span class="numero-ordre">${numero_ordre}</span></p>
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
            
            <div class="status-info">
                <h4><span class="icon">⏳</span>Validation en cours</h4>
                <#if type_creation == "cabinet">
                    <p><strong>Votre demande de création de cabinet est actuellement en cours de validation par un administrateur de l'Ordre des Experts-Comptables.</strong></p>
                <#elseif type_creation == "collaborateur">
                    <p><strong>Votre demande d'inscription en tant que collaborateur est actuellement en cours de validation par un administrateur de l'Ordre des Experts-Comptables.</strong></p>
                <#else>
                    <p><strong>Votre demande d'inscription individuelle est actuellement en cours de validation par un administrateur de l'Ordre des Experts-Comptables.</strong></p>
                </#if>
                
                <p><strong>📧 Vous recevrez vos identifiants d'accès par email dès que votre inscription sera validée et approuvée.</strong></p>
                
                <#if delai_traitement??>
                <p><em>Délai de validation estimé : ${delai_traitement}</em></p>
                <#else>
                <p><em>Délai de validation estimé : 3 à 5 jours ouvrés</em></p>
                </#if>
            </div>
            
            <div class="next-steps">
                <h4><span class="icon">📋</span>Processus de validation</h4>
                <ul>
                    <li><strong>Demande reçue</strong> ✅ Votre demande a été enregistrée avec succès</li>
                    <li><strong>En cours d'examen</strong> ⏳ Un administrateur examine actuellement votre dossier</li>
                    <li><strong>Vérification des documents</strong> 📄 Contrôle des pièces justificatives fournies</li>
                    <li><strong>Validation administrative</strong> 🔍 Vérification de conformité réglementaire</li>
                    <li><strong>Notification d'approbation</strong> 📧 Vous recevrez un email de confirmation avec vos accès</li>
                    <#if lien_suivi??>
                    <li><strong>Suivi en ligne</strong> 🔗 <a href="${lien_suivi}">Suivez l'état de votre demande ici</a></li>
                    </#if>
                </ul>
            </div>
            
            <div style="background-color: #fef3c7; border: 1px solid #fbbf24; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">⚠️</span>Important :</strong> 
                <#if type_creation == "cabinet">
                    Une fois votre demande validée, le cabinet sera officiellement habilité à exercer et l'expert-comptable responsable recevra ses identifiants d'accès pour commencer l'activité.
                <#elseif type_creation == "collaborateur">
                    Une fois votre inscription validée, vous recevrez vos identifiants d'accès et pourrez exercer en tant que collaborateur 
                    <#if statut_collaborateur??>
                        <#if statut_collaborateur?lower_case == "associe">
                            associé avec tous les droits et prérogatives associés à ce statut.
                        <#elseif statut_collaborateur?lower_case == "coordinateur">
                            coordinateur avec les responsabilités de supervision et de coordination.
                        <#elseif statut_collaborateur?lower_case == "adjoint">
                            adjoint sous supervision de l'expert-comptable responsable.
                        <#else>
                            selon les prérogatives définies par votre statut.
                        </#if>
                    <#else>
                        selon les prérogatives définies par votre statut.
                    </#if>
                <#else>
                    Une fois votre inscription validée, vous recevrez vos identifiants d'accès et serez habilité(e) à exercer toutes les missions d'expertise comptable en indépendant.
                </#if>
                </p>
            </div>
            
            <p><#if type_creation == "cabinet">Si vous avez des questions concernant votre demande de création de cabinet<#elseif type_creation == "collaborateur">Si vous avez des questions concernant votre demande d'inscription comme collaborateur<#else>Si vous avez des questions concernant votre demande d'inscription</#if> ou sur le processus de validation, n'hésitez pas à contacter notre service dédié.</p>
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
                <strong>Service <#if type_creation == "cabinet">Inscriptions Cabinets<#elseif type_creation == "collaborateur">Inscriptions Collaborateurs<#else>Inscriptions Individuelles</#if> - Ordre des Experts-Comptables</strong><br>
                Email: ${email_support!"<#if type_creation == 'cabinet'>cabinets<#elseif type_creation == 'collaborateur'>collaborateurs<#else>individuels</#if>@experts-comptables.fr"}<br>
                Téléphone: ${telephone_support!"01 44 15 60 00"}<br>
                <#if site_web??>Site web: <a href="${site_web}">${site_web}</a><#else>Site web: <a href="https://www.experts-comptables.fr">www.experts-comptables.fr</a></#if>
            </p>
            -->
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Ce message de confirmation a été envoyé automatiquement le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                Merci de ne pas répondre directement à cet email. Pour toute question, utilisez les coordonnées ci-dessus.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Merci pour votre confiance :</strong> 
                    <#if type_creation == "cabinet">
                        Votre demande de création de cabinet est en cours de traitement !
                    <#elseif type_creation == "collaborateur">
                        Votre demande d'inscription comme collaborateur est en cours de traitement !
                    <#else>
                        Votre demande d'inscription individuelle est en cours de traitement !
                    </#if>
                </p>
            </div>
        </div>
    </div>
</body>
</html>