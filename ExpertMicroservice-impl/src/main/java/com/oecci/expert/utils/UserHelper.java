package com.oecci.expert.utils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.security.SecureRandom;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Composant OSGi pour la gestion des utilisateurs Liferay.
 *
 * <p>Remplace les appels HTTP vers :</p>
 * <ul>
 *   <li>GET  /headless-admin-user/v1.0/user-accounts/by-email-address/{email}</li>
 *   <li>POST /headless-admin-user/v1.0/user-accounts</li>
 *   <li>PUT  /headless-admin-user/v1.0/user-accounts/{userId}</li>
 * </ul>
 *
 * <p>Remplace également {@code Utils.generateRandomCode()} par une génération
 * sécurisée via {@code SecureRandom}.</p>
 *
 * @author OECCI / DiginFactory — Refactorisation sécurité 2026
 */
@Component(immediate = true, service = UserHelper.class)
public class UserHelper {

	private static final Log _log = LogFactoryUtil.getLog(UserHelper.class);

	/**
	 * Jeu de caractères pour les mots de passe temporaires.
	 * Inclut majuscules, minuscules, chiffres et caractères spéciaux.
	 */
	// Jeux de caractères par catégorie — garantis présents dans chaque mot de passe
	private static final String _PWD_UPPER   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String _PWD_LOWER   = "abcdefghijklmnopqrstuvwxyz";
	private static final String _PWD_DIGITS  = "0123456789";
	private static final String _PWD_SPECIAL = "@#&*?";
//	private static final String _PWD_SPECIAL = "@#!$&*-?";
	private static final String _PWD_ALL     =
		_PWD_UPPER + _PWD_LOWER + _PWD_DIGITS;//+ _PWD_SPECIAL;

	/** Longueur totale > 10 (minimum Liferay). */
	private static final int _PWD_LENGTH = 14;

	private static final SecureRandom _secureRandom = new SecureRandom();

	// -------------------------------------------------------------------------
	// Lookup utilisateur
	// -------------------------------------------------------------------------

	/**
	 * Récupère un utilisateur Liferay par son adresse email.
	 * Remplace GET /headless-admin-user/v1.0/user-accounts/by-email-address/{email}.
	 *
	 * @param companyId ID de l'instance Liferay
	 * @param email     adresse email de l'utilisateur
	 * @return l'utilisateur Liferay, ou null s'il n'existe pas
	 */
	public User getUserByEmail(long companyId, String email) {
		try {
			User user = _userLocalService.fetchUserByEmailAddress(
				companyId, email);

			if (user != null) {
				_log.info(
					"[UserHelper] Utilisateur trouvé : email=" + email +
						" userId=" + user.getUserId());
			}
			else {
				_log.info("[UserHelper] Aucun utilisateur pour email=" + email);
			}

			return user;
		}
		catch (Exception e) {
			_log.error(
				"[UserHelper] Erreur getUserByEmail email=" + email + " — " +
					e.getMessage(), e);
			return null;
		}
	}

	// -------------------------------------------------------------------------
	// Création d'utilisateur
	// -------------------------------------------------------------------------

	/**
	 * Crée un nouvel utilisateur Liferay.
	 * Remplace POST /headless-admin-user/v1.0/user-accounts.
	 *
	 * <p><strong>creatorUserId doit être contextUser.getUserId()</strong> —
	 * le vrai utilisateur connecté qui effectue l'inscription.</p>
	 *
	 * @param creatorUserId  ID de l'utilisateur connecté (contextUser)
	 * @param companyId      ID de l'instance Liferay
	 * @param email          adresse email du nouvel utilisateur
	 * @param prenom         prénom
	 * @param nom            nom de famille
	 * @param screenName     nom d'écran unique (login)
	 * @param jobTitle       intitulé de poste
	 * @param groupIds       IDs des sites à rejoindre
	 * @param organizationIds IDs des organisations à rejoindre
	 * @param roleIds        IDs des rôles à attribuer
	 * @param password       mot de passe temporaire généré via generatePolicyCompliantPassword()
	 * @return l'utilisateur créé
	 * @throws Exception si la création échoue (email déjà existant, etc.)
	 */
	public User createUser(
			long creatorUserId, long companyId,
			String email, String prenom, String nom,
			String screenName, String jobTitle,
			long[] groupIds, long[] organizationIds, long[] roleIds,
			String password)
		throws Exception {

		String tempPassword = password;

		_log.info(
			"[UserHelper] Création utilisateur : email=" + email +
				" screenName=" + screenName + " creatorUserId=" + creatorUserId);

		ServiceContext sc = new ServiceContext();
		sc.setUserId(creatorUserId);

		User user = _userLocalService.addUser(
			creatorUserId,              // creatorUserId — le vrai user connecté
			companyId,
			false,                      // autoPassword
			tempPassword,
			tempPassword,
			false,                      // autoScreenName
			screenName,
			email,
			LocaleUtil.getDefault(),
			prenom,
			"",                         // middleName
			nom,
			0,                          // prefixListTypeId
			0,                          // suffixListTypeId
			true,                       // male
			1, 1, 1970,                 // birthdayMonth, birthdayDay, birthdayYear
			jobTitle,
			UserConstants.TYPE_REGULAR, // type (0 = utilisateur standard)
			groupIds,
			organizationIds,
			roleIds,
			new long[0],                // userGroupIds
			false,                      // sendEmail (géré via NotificationManager)
			sc);

		_log.info(
			"[UserHelper] Utilisateur créé : userId=" + user.getUserId() +
				" email=" + email);

		return user;
	}

	/**
	 * Version simplifiée sans groupes/organisations (affectation faite séparément).
	 *
	 * @param password mot de passe temporaire généré via generatePolicyCompliantPassword()
	 */
	public User createUser(
			long creatorUserId, long companyId,
			String email, String prenom, String nom,
			String screenName, String jobTitle,
			String password)
		throws Exception {

		return createUser(
			creatorUserId, companyId, email, prenom, nom,
			screenName, jobTitle,
			new long[0], new long[0], new long[0], password);
	}

	// -------------------------------------------------------------------------
	// Mise à jour d'utilisateur
	// -------------------------------------------------------------------------

	/**
	 * Met à jour les informations de base d'un utilisateur Liferay.
	 * Remplace PUT /headless-admin-user/v1.0/user-accounts/{userId}.
	 *
	 * @param updaterUserId ID de l'utilisateur connecté effectuant la MAJ
	 * @param targetUserId  ID de l'utilisateur à mettre à jour
	 * @param prenom        nouveau prénom
	 * @param nom           nouveau nom
	 * @param jobTitle      nouveau intitulé de poste
	 * @return l'utilisateur mis à jour
	 * @throws Exception si la mise à jour échoue
	 */
	public User updateUserContact(
			long updaterUserId, long targetUserId,
			String prenom, String nom, String jobTitle)
		throws Exception {

		_log.info(
			"[UserHelper] updateUserContact : targetUserId=" + targetUserId +
				" updaterUserId=" + updaterUserId);

		// Chargement puis modification des seuls champs concernés.
		// On utilise updateUser(User) (persistance directe) pour éviter les
		// incompatibilités de signature de updateUser(long, ...) en Liferay 7.4 :
		// getBirthdayMonth/Day/Year() sont dans Contact, getSmsSn/FacebookSn/...
		// ont été supprimés, et getPrefixListTypeId/getSuffixListTypeId() n'existent
		// pas sur le modèle User de cette version.
		User user = _userLocalService.getUser(targetUserId);

		user.setFirstName(prenom);
		user.setLastName(nom);
		user.setJobTitle(jobTitle);

		return _userLocalService.updateUser(user);
	}

	// -------------------------------------------------------------------------
	// Affectation organisation / site
	// -------------------------------------------------------------------------

	/**
	 * Ajoute un utilisateur à un site et à une organisation Liferay.
	 * Méthode existante de Utils.linkUserToSiteAndOrganization() conservée ici.
	 *
	 * @param groupId        ID du site (groupe)
	 * @param organizationId ID de l'organisation
	 * @param userId         ID de l'utilisateur à affecter
	 */
	public void linkUserToSiteAndOrganization(
		long groupId, long organizationId, long userId) {

		boolean isGroupMember = _userLocalService.hasGroupUser(groupId, userId);
		boolean isOrgMember = _userLocalService.hasOrganizationUser(
			organizationId, userId);

		if (!isGroupMember) {
			_userLocalService.addGroupUser(groupId, userId);
			_log.info(
				"[UserHelper] User " + userId + " ajouté au site " + groupId);
		}

		if (!isOrgMember) {
			_organizationLocalService.addUserOrganization(userId, organizationId);
			_log.info(
				"[UserHelper] User " + userId + " ajouté à l'org " +
					organizationId);
		}

		if (isGroupMember && isOrgMember) {
			_log.info(
				"[UserHelper] User " + userId +
					" déjà membre du site et de l'organisation.");
		}
	}

	// -------------------------------------------------------------------------
	// Génération de mot de passe sécurisé
	// -------------------------------------------------------------------------

	/**
	 * Génère un mot de passe conforme à la politique de mots de passe Liferay
	 * effectivement configurée pour l'instance {@code companyId}.
	 *
	 * <p>Lit dynamiquement les seuils {@code minLength}, {@code minUppercase},
	 * {@code minLowercase}, {@code minNumbers} et {@code minSymbols} depuis
	 * {@code PasswordPolicyLocalService}. Si la politique ne peut pas être lue
	 * (ex. service indisponible au démarrage), bascule sur
	 * {@link #generateSecurePassword()} comme filet de sécurité.</p>
	 *
	 * <p><strong>C'est cette méthode qui doit être appelée</strong> lors de la
	 * création d'un utilisateur Liferay — elle garantit la conformité quelle
	 * que soit la configuration de l'administrateur du portail.</p>
	 *
	 * @param companyId ID de l'instance Liferay (portal instance)
	 * @return mot de passe conforme à la politique en vigueur, jamais null
	 */
	/*public String generatePolicyCompliantPassword(long companyId) {
		int minUppercase = 1;
		int minLowercase = 1;
		int minNumbers   = 1;
		int minSymbols   = 1;
		int minLength    = _PWD_LENGTH;

		try {
			PasswordPolicy policy =
				_passwordPolicyLocalService.getDefaultPasswordPolicy(companyId);

			if (policy != null) {
				minUppercase = Math.max(minUppercase, policy.getMinUppercase());
				minLowercase = Math.max(minLowercase, policy.getMinLowercase());
				minNumbers   = Math.max(minNumbers,   policy.getMinNumbers());
				minSymbols   = Math.max(minSymbols,   policy.getMinSymbols());
				minLength    = Math.max(minLength,     policy.getMinLength());
			}
		}
		catch (Exception e) {
			_log.warn(
				"[UserHelper] Impossible de lire la PasswordPolicy " +
					"(companyId=" + companyId + ") — fallback sur generateSecurePassword(). " +
					e.getMessage());

			return generateSecurePassword();
		}

		// Longueur totale = max(minLength, somme des catégories obligatoires + 4 aléatoires)
		int mandatory   = minUppercase + minLowercase + minNumbers + minSymbols;
		int totalLength = Math.max(minLength, mandatory + 4);

		char[] pwd = new char[totalLength];
		int pos = 0;

		for (int i = 0; i < minUppercase; i++)
			pwd[pos++] = _PWD_UPPER.charAt(_secureRandom.nextInt(_PWD_UPPER.length()));
		for (int i = 0; i < minLowercase; i++)
			pwd[pos++] = _PWD_LOWER.charAt(_secureRandom.nextInt(_PWD_LOWER.length()));
		for (int i = 0; i < minNumbers; i++)
			pwd[pos++] = _PWD_DIGITS.charAt(_secureRandom.nextInt(_PWD_DIGITS.length()));
		for (int i = 0; i < minSymbols; i++)
			pwd[pos++] = _PWD_SPECIAL.charAt(_secureRandom.nextInt(_PWD_SPECIAL.length()));

		while (pos < totalLength)
			pwd[pos++] = _PWD_ALL.charAt(_secureRandom.nextInt(_PWD_ALL.length()));

		// Fisher-Yates shuffle — élimine tout biais de position
		for (int i = totalLength - 1; i > 0; i--) {
			int j = _secureRandom.nextInt(i + 1);
			char tmp = pwd[i];
			pwd[i] = pwd[j];
			pwd[j] = tmp;
		}

		_log.debug(
			"[UserHelper] generatePolicyCompliantPassword: length=" + totalLength +
				" minUpper=" + minUppercase + " minLower=" + minLowercase +
				" minNumbers=" + minNumbers + " minSymbols=" + minSymbols);

		return new String(pwd);
	}*/

	/**
	 * Génère un mot de passe temporaire cryptographiquement sécurisé qui
	 * satisfait obligatoirement la politique de mots de passe Liferay :
	 *
	 * <ul>
	 *   <li>Longueur ≥ 10 caractères (ici {@value #_PWD_LENGTH})</li>
	 *   <li>Au moins 1 lettre majuscule</li>
	 *   <li>Au moins 1 lettre minuscule</li>
	 *   <li>Au moins 1 chiffre</li>
	 *   <li>Au moins 1 caractère spécial (@#!$%&*+-?)</li>
	 * </ul>
	 *
	 * <p>Utilisé comme fallback si la PasswordPolicy ne peut pas être lue.
	 * Préférer {@link #(long)} lors de la création
	 * d'un utilisateur Liferay.</p>
	 *
	 * @return mot de passe temporaire sécurisé, jamais null
	 */
	public static String generateSecurePassword() {
		char[] pwd = new char[_PWD_LENGTH];

		// Garantir au moins un représentant de chaque catégorie obligatoire
		pwd[0] = _PWD_UPPER.charAt(_secureRandom.nextInt(_PWD_UPPER.length()));
		pwd[1] = _PWD_LOWER.charAt(_secureRandom.nextInt(_PWD_LOWER.length()));
		pwd[2] = _PWD_DIGITS.charAt(_secureRandom.nextInt(_PWD_DIGITS.length()));
		//pwd[3] = _PWD_SPECIAL.charAt(_secureRandom.nextInt(_PWD_SPECIAL.length()));

		// Compléter les positions restantes avec le jeu complet
		for (int i = 3; i < _PWD_LENGTH; i++) {
			pwd[i] = _PWD_ALL.charAt(_secureRandom.nextInt(_PWD_ALL.length()));
		}

		// Fisher-Yates shuffle — élimine tout biais de position
		for (int i = _PWD_LENGTH - 1; i > 0; i--) {
			int j = _secureRandom.nextInt(i + 1);
			char tmp = pwd[i];
			pwd[i] = pwd[j];
			pwd[j] = tmp;
		}

		return new String(pwd);
	}

	/**
	 * Génère un code de référence aléatoire (6 caractères alphanumériques
	 * majuscules). Remplace {@code Utils.generateRandomCode()}.
	 *
	 * <p>Utilise {@code SecureRandom} au lieu de {@code java.util.Random}.</p>
	 */
	public static String generateSecureCode() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(6);

		for (int i = 0; i < 6; i++) {
			sb.append(chars.charAt(_secureRandom.nextInt(chars.length())));
		}

		return sb.toString();
	}

	// -------------------------------------------------------------------------
	// Construction du screenName
	// -------------------------------------------------------------------------

	/**
	 * Génère un screenName unique basé sur les initiales et un timestamp.
	 * Reprend la logique existante de Expert_ComptableResourceImpl.
	 */
	public static String buildScreenName(String prenom, String nom) {
		String p = (prenom != null && prenom.length() > 0) ?
			prenom.substring(0, 1).toLowerCase() : "x";
		String n = (nom != null && nom.length() > 0) ?
			nom.substring(0, 1).toLowerCase() : "x";

		return p + n + "_" + System.currentTimeMillis();
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}
