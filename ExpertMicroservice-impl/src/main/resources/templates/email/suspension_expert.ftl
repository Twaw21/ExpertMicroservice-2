<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notification de suspension - <#if type_creation == "CABINET">Cabinet<#elseif type_creation == "COLLABORATEUR">Collaborateur<#elseif type_creation == "ASSOCIE">Associé<#elseif type_creation == "COORDINATEUR">Coordinateur<#elseif type_creation == "ADJOINT">Adjoint<#else>Expert-Comptable</#if></title>
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
            border-bottom: 3px solid #ff6b6b;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .suspension-badge {
            background-color: #fff3cd;
            border: 2px solid #ffc107;
            color: #856404;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
            text-align: center;
            font-weight: bold;
            font-size: 20px;
        }
        .content {
            margin-bottom: 20px;
        }
        .suspension-info {
            background-color: #fff3cd;
            border: 2px solid #ffc107;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 6px solid #ff9800;
        }
        .motifs-suspension {
            background-color: #ffebee;
            border: 2px solid #ef5350;
            border-radius: 6px;
            padding: 20px;
            margin: 25px 0;
            border-left: 6px solid #f44336;
        }
        .consequences-box {
            background-color: #fce4ec;
            border: 2px solid #f06292;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 6px solid #e91e63;
        }
        .recours-info {
            background-color: #e3f2fd;
            border: 2px solid #2196f3;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 6px solid #1976d2;
        }
        .entity-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            padding: 15px;
            margin: 20px 0;
            border-left: 4px solid #6c757d;
        }
        .alert-box {
            background-color: #fff3e0;
            border: 2px solid #ff9800;
            border-radius: 6px;
            padding: 20px;
            margin: 20px 0;
            border-left: 6px solid #f57c00;
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
            color: #d32f2f;
            font-weight: bold;
        }
        .contact-button {
            background-color: #ff6b6b;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin: 15px 0;
            font-weight: bold;
        }
        .contact-button:hover {
            background-color: #d63031;
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
        .numero-ordre {
            background-color: #fff3e0;
            padding: 8px 12px;
            border-radius: 4px;
            font-family: 'Courier New', monospace;
            font-weight: bold;
            color: #e65100;
            display: inline-block;
            margin: 5px 0;
            font-size: 16px;
        }
        .date-importante {
            background-color: #ffcdd2;
            padding: 10px 15px;
            border-radius: 4px;
            font-weight: bold;
            color: #b71c1c;
            display: inline-block;
            margin: 10px 0;
        }
        ul {
            padding-left: 20px;
        }
        ul li {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="email-container">
        <div class="header">
            <h2>
                <span class="icon">⚠️</span>
                <#if type_creation?? && type_creation == "CABINET">
                    Notification de suspension - Cabinet
                <#elseif type_creation?? && type_creation == "COLLABORATEUR">
                    Notification de suspension - Collaborateur
                <#elseif type_creation?? && type_creation == "ASSOCIE">
                    Notification de suspension - Associé
                <#elseif type_creation?? && type_creation == "COORDINATEUR">
                    Notification de suspension - Coordinateur
                <#elseif type_creation?? && type_creation == "ADJOINT">
                    Notification de suspension - Adjoint
                <#else>
                    Notification de suspension - Expert-Comptable
                </#if>
            </h2>
        </div>
        
        <div class="content">
            <p>Bonjour <strong>${nom_expert_comptable!"Madame, Monsieur"}</strong>,</p>
            
            <div class="suspension-badge">
                <span class="icon">🚫</span> 
                SUSPENSION D'INSCRIPTION
            </div>
            
            <div class="suspension-info">
                <p><strong>Nous vous informons par la présente que votre inscription 
                <#if type_creation?? && type_creation == "CABINET">
                    en tant que cabinet <span class="highlight">${nom_cabinet!""}</span>
                <#elseif type_creation?? && type_creation == "COLLABORATEUR">
                    en tant que collaborateur expert-comptable
                <#elseif type_creation?? && type_creation == "ASSOCIE">
                    en tant qu'associé expert-comptable
                <#elseif type_creation?? && type_creation == "COORDINATEUR">
                    en tant que coordinateur expert-comptable
                <#elseif type_creation?? && type_creation == "ADJOINT">
                    en tant qu'adjoint expert-comptable
                <#else>
                    en tant qu'expert-comptable
                </#if>
                au sein de l'Ordre des Experts-Comptables fait l'objet d'une <span class="highlight">SUSPENSION</span>.</strong></p>
                
                <#if numero_ordre??>
                <p><strong>Numéro d'ordre concerné :</strong> <span class="numero-ordre">${numero_ordre}</span></p>
                </#if>
                
                <#if numero_dossier??>
                <p><strong>Numéro de dossier :</strong> <span class="numero-dossier">${numero_dossier}</span></p>
                </#if>
                
                <#if date_validation??>
                <p><strong>Date d'effet de la suspension :</strong> <span class="date-importante">${date_validation?string("dd/MM/yyyy")}</span></p>
                </#if>
                
                <#if duree_suspension??>
                <p><strong>Durée de la suspension :</strong> <span class="highlight">${duree_suspension}</span></p>
                </#if>
                
                <#if date_fin_suspension??>
                <p><strong>Date de fin prévue :</strong> <span class="date-importante">${date_fin_suspension?string("dd/MM/yyyy")}</span></p>
                </#if>
            </div>
            
            <div class="entity-info">
                <h4><span class="icon">
                    <#if type_creation?? && type_creation == "CABINET">🏢
                    <#elseif type_creation?? && (type_creation == "COLLABORATEUR" || type_creation == "ASSOCIE" || type_creation == "COORDINATEUR" || type_creation == "ADJOINT")>👥
                    <#else>👨‍💼
                    </#if>
                </span>Informations de l'inscription suspendue</h4>
                
                <p><strong>Nom de l'expert-comptable :</strong> ${nom_expert_comptable!"Non spécifié"}</p>
                
                <#if numero_ordre??>
                <p><strong>Numéro d'ordre :</strong> ${numero_ordre}</p>
                </#if>
                
                <#if type_creation?? && type_creation == "CABINET">
                    <#if nom_cabinet??>
                    <p><strong>Nom du cabinet :</strong> ${nom_cabinet}</p>
                    </#if>
                    <#if siret_cabinet??>
                    <p><strong>SIRET :</strong> ${siret_cabinet}</p>
                    </#if>
                </#if>
                
                <#if type_creation?? && (type_creation == "COLLABORATEUR" || type_creation == "ASSOCIE" || type_creation == "COORDINATEUR" || type_creation == "ADJOINT")>
                    <p><strong>Statut :</strong> 
                        <#if type_creation == "ASSOCIE">Associé Expert-Comptable
                        <#elseif type_creation == "COORDINATEUR">Coordinateur Expert-Comptable
                        <#elseif type_creation == "ADJOINT">Adjoint Expert-Comptable
                        <#else>Collaborateur Expert-Comptable
                        </#if>
                    </p>
                    <#if nom_cabinet??>
                    <p><strong>Cabinet de rattachement :</strong> ${nom_cabinet}</p>
                    </#if>
                </#if>
                
                <#if adresse_cabinet??>
                <p><strong>Adresse d'exercice :</strong> ${adresse_cabinet}</p>
                </#if>
            </div>
            
            <#if motifs_suspension?? && motifs_suspension?has_content>
            <div class="motifs-suspension">
                <h4><span class="icon">⚠️</span>Motifs de la suspension</h4>
                <p><strong>Cette décision de suspension est motivée par les éléments suivants :</strong></p>
                <ul>
                <#list motifs_suspension as motif>
                    <li>${motifs_suspension}</li>
                </#list>
                </ul>
            </div>
            <#else>
            <div class="motifs-suspension">
                <h4><span class="icon">⚠️</span>Motifs de la suspension</h4>
                <p><strong>Cette suspension fait suite à un examen de votre situation par la commission de discipline et d'inscription.</strong></p>
                <p>Pour obtenir des informations détaillées sur les motifs spécifiques, nous vous invitons à contacter nos services dans les plus brefs délais.</p>
            </div>
            </#if>
            
            <div class="consequences-box">
                <h4><span class="icon">🚫</span>Conséquences de la suspension</h4>
                <p><strong>Durant la période de suspension, vous ne pouvez plus :</strong></p>
                <ul>
                    <li>Exercer la profession d'expert-comptable sous quelque forme que ce soit</li>
                    <li>Utiliser le titre d'expert-comptable</li>
                    <li>Signer des missions légales (commissariat aux comptes, attestations, certifications)</li>
                    <li>Représenter des clients devant l'administration fiscale en qualité d'expert-comptable</li>
                    <#if type_creation?? && type_creation == "CABINET">
                    <li>Exploiter le cabinet sous son numéro d'inscription suspendu</li>
                    <li>Embaucher de nouveaux collaborateurs pour le compte du cabinet</li>
                    </#if>
                    <#if type_creation?? && (type_creation == "COLLABORATEUR" || type_creation == "ASSOCIE" || type_creation == "COORDINATEUR" || type_creation == "ADJOINT")>
                    <li>Exercer au sein du cabinet mentionné avec votre statut actuel</li>
                    </#if>
                    <li>Percevoir des honoraires pour des prestations d'expertise comptable</li>
                </ul>
                
                <p style="margin-top: 15px;"><strong>⚠️ ATTENTION :</strong> L'exercice illégal de la profession durant la suspension est passible de sanctions pénales conformément à l'article 433-17 du Code pénal.</p>
            </div>
            
            <div class="alert-box">
                <h4><span class="icon">📋</span>Obligations durant la suspension</h4>
                <p><strong>Vous devez impérativement :</strong></p>
                <ul>
                    <li>Informer vos clients de votre suspension et les orienter vers un confrère</li>
                    <li>Cesser immédiatement toute activité d'expertise comptable</li>
                    <li>Retirer votre nom de tous les supports de communication (site web, plaque professionnelle, etc.)</li>
                    <#if type_creation?? && type_creation == "CABINET">
                    <li>Informer vos collaborateurs de la situation du cabinet</li>
                    <li>Mettre en place une solution de continuité pour vos clients (transfert de dossiers)</li>
                    </#if>
                    <li>Conserver tous les documents relatifs à vos dossiers en cours</li>
                    <li>Répondre aux sollicitations de l'Ordre concernant la suspension</li>
                </ul>
                
                <#if delai_mise_en_conformite??>
                <p style="margin-top: 15px;"><strong>Délai pour mise en conformité :</strong> <span class="highlight">${delai_mise_en_conformite}</span></p>
                </#if>
            </div>
            
            <div class="recours-info">
                <h4><span class="icon">⚖️</span>Voies de recours et réintégration</h4>
                <p><strong>Vous disposez des possibilités suivantes :</strong></p>
                
                <h5 style="margin-top: 15px;">🔹 Recours contre la décision de suspension :</h5>
                <ul>
                    <li><strong>Recours gracieux :</strong> Auprès du Président de l'Ordre dans un délai de <strong>2 mois</strong> à compter de la notification</li>
                    <li><strong>Recours contentieux :</strong> Saisine du tribunal administratif dans un délai de <strong>2 mois</strong></li>
                </ul>
                
                <h5 style="margin-top: 15px;">🔹 Demande de levée anticipée de la suspension :</h5>
                <ul>
                    <li>Possible après avoir régularisé les motifs de la suspension</li>
                    <li>Doit être accompagnée de justificatifs attestant de la mise en conformité</li>
                    <#if delai_levee_anticipee??>
                    <li>Délai minimal avant demande : <strong>${delai_levee_anticipee}</strong></li>
                    </#if>
                </ul>
                
                <h5 style="margin-top: 15px;">🔹 Réintégration automatique :</h5>
                <#if date_fin_suspension??>
                <p>À l'issue de la période de suspension (${date_fin_suspension?string("dd/MM/yyyy")}), votre inscription sera automatiquement rétablie si toutes les conditions sont remplies.</p>
                <#else>
                <p>À l'issue de la période de suspension, votre inscription pourra être rétablie après examen de votre situation.</p>
                </#if>
            </div>
            
            <div class="alert-box">
                <h4><span class="icon">📞</span>Contact et accompagnement</h4>
                <p><strong>Nos services sont à votre disposition pour :</strong></p>
                <ul>
                    <li>Vous expliquer en détail les motifs de cette suspension</li>
                    <li>Vous accompagner dans vos démarches de mise en conformité</li>
                    <li>Vous conseiller sur les modalités de recours</li>
                    <li>Vous informer sur les conditions de levée de la suspension</li>
                    <li>Répondre à toutes vos questions concernant cette décision</li>
                </ul>
                
                <#if lien_contact??>
                <a href="${lien_contact}" class="contact-button">📞 Prendre rendez-vous d'urgence</a>
                <#else>
                <p style="margin-top: 15px;"><strong>Contactez-nous immédiatement au :</strong></p>
                <p>📧 Email : suspension@experts-comptables.fr</p>
                <p>📞 Téléphone : 01 44 15 60 00</p>
                </#if>
            </div>
            
            <div style="background-color: #ffebee; border: 2px solid #f44336; border-radius: 6px; padding: 20px; margin: 20px 0;">
                <p><strong><span class="icon">⚠️</span>MISE EN GARDE IMPORTANTE :</strong></p>
                <p>Cette suspension est une mesure grave qui affecte directement votre droit d'exercer la profession. Toute violation de cette suspension (exercice illégal) peut entraîner :</p>
                <ul>
                    <li>Une radiation définitive du tableau de l'Ordre</li>
                    <li>Des poursuites pénales pour exercice illégal de la profession</li>
                    <li>Des poursuites civiles pour préjudice causé aux clients</li>
                    <li>L'impossibilité de se réinscrire ultérieurement</li>
                </ul>
                <p style="margin-top: 10px; font-weight: bold; color: #b71c1c;">
                    ⚠️ Respectez scrupuleusement cette suspension et prenez contact avec nos services dans les meilleurs délais.
                </p>
            </div>
            
            <p style="margin-top: 20px;">Nous restons à votre disposition pour vous accompagner durant cette période et faciliter votre retour à l'exercice de la profession dans les meilleures conditions.</p>
        </div>
        
        <div class="footer">
            <div class="signature">
                <p>Cordialement,<br>
                <strong>Commission de Discipline et d'Inscription<br>
                Ordre des Experts-Comptables</strong></p>
            </div>
            
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            
            <p style="font-size: 11px; color: #aaa; margin-top: 15px;">
                Cette notification de suspension a été envoyée le ${.now?string("dd/MM/yyyy à HH:mm")}.<br>
                Cette notification fait foi pour les délais de recours. Conservez-la précieusement.<br>
                <strong>Réf. dossier :</strong> <#if numero_dossier??>${numero_dossier}<#else>N/A</#if>
            </p>
            
            <div style="text-align: center; margin-top: 20px; padding: 15px; background-color: #ffebee; border: 2px solid #f44336; border-radius: 6px;">
                <p style="margin: 0; font-size: 13px; color: #b71c1c; font-weight: bold;">
                    ⚠️ SUSPENSION EN VIGUEUR - CESSATION IMMÉDIATE D'ACTIVITÉ OBLIGATOIRE
                </p>
                <p style="margin: 5px 0 0 0; font-size: 11px; color: #d32f2f;">
                    Tout exercice durant la suspension constitue un délit pénal
                </p>
            </div>
        </div>
    </div>
</body>
</html>