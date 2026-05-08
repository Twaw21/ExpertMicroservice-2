<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Demande non retenue - <#if type_creation == "cabinet">Cabinet<#elseif type_creation == "collaborateur">Collaborateur<#else>Expert-Comptable</#if></title>
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
            border-bottom: 2px solid #dc3545;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .refus-badge {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
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
        .refus-info {
            background-color: #fef5e7;
            border: 1px solid #ffeaa7;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #ffc107;
        }
        .motifs-refus {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 6px;
            padding: 20px;
            margin: 25px 0;
            border-left: 6px solid #dc3545;
        }
        .recours-info {
            background-color: #e3f2fd;
            border: 1px solid #90caf9;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 4px solid #2196f3;
        }
        .next-steps {
            background-color: #e8f5e8;
            border: 1px solid #c3e6cb;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
        }
        .entity-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #6c757d;
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
            color: #dc3545;
            font-weight: bold;
        }
        .contact-button {
            background-color: #007bff;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin: 15px 0;
            font-weight: bold;
        }
        .contact-button:hover {
            background-color: #0056b3;
            color: white;
            text-decoration: none;
        }
        .numero-dossier {
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
                    Demande de création de cabinet non retenue
                <#elseif type_creation == "collaborateur">
                    Demande d'inscription collaborateur non retenue
                <#else>
                    Demande d'inscription non retenue
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
            
            <div class="refus-badge">
                <span class="icon">❌</span> 
                <#if type_creation == "cabinet">
                    Demande de création de cabinet non retenue
                <#elseif type_creation == "collaborateur">
                    Demande d'inscription comme collaborateur non retenue
                <#else>
                    Demande d'inscription individuelle non retenue
                </#if>
            </div>
            
            <div class="refus-info">
                <#if type_creation == "cabinet">
                    <p><strong>Nous avons le regret de vous informer que votre demande de création du cabinet <span class="highlight">${nom_cabinet}</span> avec inscription de l'expert-comptable <span class="highlight">${nom_expert_comptable}</span> n'a pas pu être retenue par l'Ordre des Experts-Comptables.</strong></p>
                <#elseif type_creation == "collaborateur">
                    <p><strong>Nous avons le regret de vous informer que votre demande d'inscription de <span class="highlight">${nom_expert_comptable}</span> en tant que collaborateur 
                    <#if statut_collaborateur??>
                        (<span class="highlight">${statut_collaborateur?upper_case}</span>)
                    </#if>
                    au sein du cabinet <span class="highlight">${nom_cabinet}</span> n'a pas pu être retenue par l'Ordre des Experts-Comptables.</strong></p>
                <#else>
                    <p><strong>Nous avons le regret de vous informer que votre demande d'inscription de <span class="highlight">${nom_expert_comptable}</span> en tant qu'expert-comptable individuel n'a pas pu être retenue par l'Ordre des Experts-Comptables.</strong></p>
                </#if>
                
                <#if numero_dossier??>
                <p><strong>Numéro de dossier :</strong> <span class="numero-dossier">${numero_dossier}</span></p>
                </#if>
                
                <#if date_validation??>
                <p><strong>Date de la décision :</strong> ${date_decision?string("dd/MM/yyyy")}</p>
                </#if>
            </div>
            
            <div class="entity-info">
                <#if type_creation == "cabinet">
                    <h4><span class="icon">🏢</span>Informations de la demande</h4>
                    <p><strong>Nom du cabinet demandé :</strong> ${nom_cabinet!"Non spécifié"}</p>
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
                    <h4><span class="icon">👥</span>Informations de la demande</h4>
                    <p><strong>Nom du candidat :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
                    <#if statut_collaborateur??>
                    <p><strong>Statut demandé :</strong> 
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
                    <h4><span class="icon">👨‍💼</span>Informations de la demande</h4>
                    <p><strong>Nom du candidat :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
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
            
            <#if motif_rejet?? && motif_rejet?has_content>
            <div class="motifs-refus">
                <h4><span class="icon">⚠️</span>Motif de la décision</h4>
                <p><strong>Après examen de votre dossier, le motif suivant n'a pas permis de donner suite à votre demande :</strong></p>
                <p style="margin: 15px 0; padding: 15px; background-color: #fff; border-left: 4px solid #dc3545;">
                    ${motif_rejet}
                </p>
            </div>
            <#elseif motifs_refus?? && motifs_refus?has_content>
            <div class="motifs-refus">
                <h4><span class="icon">⚠️</span>Motifs de la décision</h4>
                <p><strong>Après examen de votre dossier, les points suivants n'ont pas permis de donner suite à votre demande :</strong></p>
                <ul>
                <#list motifs_refus as motif>
                    <li>${motif}</li>
                </#list>
                </ul>
            </div>
            <#else>
            <div class="motifs-refus">
                <h4><span class="icon">⚠️</span>Motifs de la décision</h4>
                <p><strong>Après examen approfondi de votre dossier par la commission compétente, certains éléments n'ont pas permis de donner suite favorablement à votre demande d'inscription.</strong></p>
                <p>Pour connaître les détails spécifiques, nous vous invitons à prendre contact avec notre service qui pourra vous fournir des explications détaillées.</p>
            </div>
            </#if>
            
            <div class="recours-info">
                <h4><span class="icon">⚖️</span>Possibilités de recours</h4>
                <p><strong>Cette décision peut faire l'objet d'un recours selon les modalités suivantes :</strong></p>
                <ul>
                    <li><strong>Recours gracieux :</strong> Vous pouvez adresser un recours motivé au Président de l'Ordre dans un délai de <strong>2 mois</strong> à compter de la notification</li>
                    <li><strong>Recours contentieux :</strong> Vous pouvez saisir le tribunal administratif dans un délai de <strong>2 mois</strong></li>
                    <li><strong>Nouvelle demande :</strong> Vous pouvez déposer une nouvelle demande après avoir corrigé les points soulevés</li>
                </ul>
                
                <#if delai_nouvelle_demande??>
                <p><strong>Délai pour une nouvelle demande :</strong> ${delai_nouvelle_demande}</p>
                <#else>
                <p><strong>Délai pour une nouvelle demande :</strong> Immédiatement après correction des points mentionnés</p>
                </#if>
            </div>
            
            <div class="next-steps">
                <h4><span class="icon">📞</span>Nous contacter</h4>
                <p><strong>Notre équipe reste à votre disposition pour :</strong></p>
                <ul>
                    <li>Vous expliquer en détail les motifs de cette décision</li>
                    <li>Vous accompagner dans la préparation d'un éventuel recours</li>
                    <li>Vous conseiller sur les corrections à apporter pour une nouvelle demande</li>
                    <li>Répondre à toutes vos questions concernant cette décision</li>
                </ul>
                
                <#if lien_contact??>
                <a href="${lien_contact}" class="contact-button">📞 Prendre rendez-vous</a>
                </#if>
            </div>
            
            <div style="background-color: #fef3c7; border: 1px solid #fbbf24; border-radius: 6px; padding: 15px; margin: 20px 0;">
                <p><strong><span class="icon">💡</span>Information importante :</strong> 
                <#if type_creation == "cabinet">
                    Cette décision concerne uniquement cette demande spécifique de création de cabinet. Les qualifications professionnelles de l'expert-comptable ne sont pas remises en question et une nouvelle demande peut être déposée après correction des points soulevés.
                <#elseif type_creation == "collaborateur">
                    Cette décision concerne uniquement cette demande spécifique d'inscription comme collaborateur dans ce cabinet. Vos qualifications professionnelles ne sont pas remises en question et vous pouvez déposer une nouvelle demande ou candidater dans un autre cabinet.
                <#else>
                    Cette décision concerne uniquement cette demande spécifique d'inscription individuelle. Vos qualifications professionnelles peuvent nécessiter des compléments et une nouvelle demande peut être déposée après correction des points soulevés.
                </#if>
                </p>
            </div>
            
            <p>Nous vous remercions de l'intérêt que vous portez à l'exercice de la profession d'expert-comptable et restons à votre disposition pour tout accompagnement.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>
                <strong>Commission d'<#if type_creation == "cabinet">Inscription des Cabinets<#elseif type_creation == "collaborateur">Inscription des Collaborateurs<#else>Inscription Individuelle</#if></strong><br>
                Ordre des Experts-Comptables</p>
            </div>
            
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
           
            <!--
            <p style="font-size: 12px; color: #888;">
                <strong>Service <#if type_creation == "cabinet">Inscriptions Cabinets<#elseif type_creation == "collaborateur">Inscriptions Collaborateurs<#else>Inscriptions Individuelles</#if> - Ordre des Experts-Comptables</strong><br>
                Email: ${email_support!"<#if type_creation == 'cabinet'>recours-cabinets<#elseif type_creation == 'collaborateur'>recours-collaborateurs<#else>recours-individuels</#if>@experts-comptables.fr"}<br>
                Téléphone: ${telephone_support!"01 44 15 60 00"}<br>
                <#if site_web??>Site web: <a href="${site_web}">${site_web}</a><#else>Site web: <a href="https://www.experts-comptables.fr">www.experts-comptables.fr</a></#if>
            </p>
            -->
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Ce message a été envoyé automatiquement le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                Cette notification fait foi pour les délais de recours. Conservez-la précieusement.
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 6px;">
                <p style="margin: 0; font-size: 12px; color: #6c757d;">
                    <strong>Rappel :</strong> 
                    Cette décision peut faire l'objet d'un recours dans un délai de 2 mois.
                </p>
            </div>
        </div>
    </div>
</body>
</html>