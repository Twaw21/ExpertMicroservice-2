package com.oecci.expert.utils;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Composant OSGi pour les opérations sur la Document Library Liferay.
 *
 * <p>Remplace :</p>
 * <ul>
 *   <li>{@code Utils.uploadDocumentToLiferay()} — POST /headless-delivery/v1.0/sites/{id}/documents</li>
 *   <li>{@code Utils.downloadImageAsBase64()} — GET /headless-delivery/v1.0/documents/{id}</li>
 * </ul>
 *
 * <p>Toutes les opérations utilisent le {@code userId} passé en paramètre
 * (contextUser.getUserId()) pour garantir la traçabilité des actions.</p>
 *
 * @author OECCI / DiginFactory — Refactorisation sécurité 2026
 */
@Component(immediate = true, service = DocumentHelper.class)
public class DocumentHelper {

	private static final Log _log = LogFactoryUtil.getLog(
			DocumentHelper.class);

	// -------------------------------------------------------------------------
	// Upload de fichier
	// -------------------------------------------------------------------------

	/**
	 * Upload un fichier dans la Document Library Liferay, à la racine du
	 * repository (pas de sous-dossier).
	 *
	 * <p><strong>userId doit être contextUser.getUserId()</strong></p>
	 *
	 * @param userId       ID de l'utilisateur connecté
	 * @param repositoryId groupId du site cible (= siteGroupId)
	 * @param fileName     nom du fichier (sanitisé par l'appelant)
	 * @param file         fichier temporaire à uploader
	 * @return fileEntryId du document créé dans la DL
	 * @throws Exception si l'upload échoue
	 */
	public long uploadFile(
			long userId, long repositoryId, String fileName, File file)
			throws Exception {

		return uploadFile(
				userId, repositoryId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				fileName, file);
	}

	/**
	 * Upload un fichier dans un dossier spécifique de la Document Library.
	 *
	 * @param userId       ID de l'utilisateur connecté
	 * @param repositoryId groupId du site cible
	 * @param folderId     ID du dossier DL cible (0 = racine)
	 * @param fileName     nom du fichier (sanitisé)
	 * @param file         fichier temporaire
	 * @return fileEntryId du document créé
	 * @throws Exception si l'upload échoue
	 */
	public long uploadFile(
			long userId, long repositoryId, long folderId,
			String fileName, File file)
			throws Exception {

		_log.info(
				"[DocumentHelper] uploadFile : fileName=" + fileName +
						" repositoryId=" + repositoryId + " folderId=" + folderId +
						" userId=" + userId);

		String mimeType = MimeTypesUtil.getContentType(file);

		ServiceContext sc = new ServiceContext();
		sc.setUserId(userId);
		sc.setScopeGroupId(repositoryId);

		try (FileInputStream fis = new FileInputStream(file)) {
			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
					null,           // externalReferenceCode
					userId,
					repositoryId,
					folderId,
					fileName,
					mimeType,
					fileName,       // title
					null,           // urlTitle
					"",             // description
					null,           // changeLog
					fis,
					file.length(),
					null, // DisplayDate
					null,           // expirationDate
					null,           // reviewDate
					sc);

			_log.info(
					"[DocumentHelper] Fichier uploadé avec succès — fileEntryId=" +
							fileEntry.getFileEntryId());

			return fileEntry.getFileEntryId();
		}
	}

	// -------------------------------------------------------------------------
	// Récupération de fichier
	// -------------------------------------------------------------------------

	/**
	 * Récupère les bytes bruts d'un fichier par son fileEntryId.
	 *
	 * @param fileEntryId ID du FileEntry dans la Document Library
	 * @return tableau de bytes du fichier
	 * @throws Exception si le fichier est introuvable
	 */
	public byte[] getFileBytes(long fileEntryId) throws Exception {
		_log.info("[DocumentHelper] getFileBytes : fileEntryId=" + fileEntryId);

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		try (InputStream is = fileEntry.getContentStream()) {
			return FileUtil.getBytes(is);
		}
	}

	/**
	 * Récupère un fichier encodé en Base64 avec le préfixe data URI.
	 * Remplace {@code Utils.downloadImageAsBase64()}.
	 *
	 * <p>Format retourné : {@code "data:image/png;base64,<données>"}</p>
	 *
	 * @param fileEntryId ID du FileEntry
	 * @return chaîne Base64 avec préfixe data URI, ou null si erreur
	 */
	public String getFileAsBase64DataUri(long fileEntryId) {
		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			byte[] bytes;
			try (InputStream is = fileEntry.getContentStream()) {
				bytes = FileUtil.getBytes(is);
			}

			String mimeType = fileEntry.getMimeType();
			String base64 = Base64.getEncoder().encodeToString(bytes);

			return "data:" + mimeType + ";base64," + base64;
		}
		catch (Exception e) {
			_log.error(
					"[DocumentHelper] Erreur getFileAsBase64DataUri : " +
							"fileEntryId=" + fileEntryId + " — " + e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Récupère uniquement les données Base64 brutes (sans préfixe data URI).
	 *
	 * @param fileEntryId ID du FileEntry
	 * @return chaîne Base64 ou null si erreur
	 */
	public String getFileAsBase64(long fileEntryId) {
		try {
			byte[] bytes = getFileBytes(fileEntryId);
			return Base64.getEncoder().encodeToString(bytes);
		}
		catch (Exception e) {
			_log.error(
					"[DocumentHelper] Erreur getFileAsBase64 : fileEntryId=" +
							fileEntryId + " — " + e.getMessage(), e);
			return null;
		}
	}

	// -------------------------------------------------------------------------
	// Gestion des dossiers
	// -------------------------------------------------------------------------

	/**
	 * Récupère ou crée un dossier par son nom dans le repository.
	 * Utile pour organiser les documents par client ou par type.
	 *
	 * @param userId       ID de l'utilisateur connecté
	 * @param repositoryId groupId du site
	 * @param parentFolderId ID du dossier parent (0 = racine)
	 * @param folderName   nom du dossier
	 * @return ID du dossier existant ou nouvellement créé
	 * @throws Exception si l'opération échoue
	 */
	public long getOrCreateFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName)
			throws Exception {

		try {
			Folder folder = _dlAppLocalService.getFolder(
					repositoryId, parentFolderId, folderName);

			_log.info(
					"[DocumentHelper] Dossier existant trouvé : " + folderName +
							" (id=" + folder.getFolderId() + ")");

			return folder.getFolderId();
		}
		catch (Exception e) {
			// Le dossier n'existe pas, on le crée
			_log.info(
					"[DocumentHelper] Création du dossier : " + folderName);

			ServiceContext sc = new ServiceContext();
			sc.setUserId(userId);
			sc.setScopeGroupId(repositoryId);

			Folder folder = _dlAppLocalService.addFolder(
					null,           // externalReferenceCode
					userId,
					repositoryId,
					parentFolderId,
					folderName,
					"",             // description
					sc);

			return folder.getFolderId();
		}
	}

	// -------------------------------------------------------------------------
	// Utilitaire — sanitisation du nom de fichier
	// -------------------------------------------------------------------------

	/**
	 * Sanitise un nom de fichier fourni par l'utilisateur pour éviter les
	 * attaques de type Path Traversal.
	 *
	 * <p>Supprime les séparateurs de chemin et les caractères non sûrs.
	 * Doit être appelé AVANT tout {@code new File(tempDir, fileName)}.</p>
	 *
	 * @param rawFileName nom de fichier brut fourni par l'appelant
	 * @return nom de fichier sanitisé, sans chemin ni caractères dangereux
	 */
	public static String sanitizeFileName(String rawFileName) {
		if (rawFileName == null || rawFileName.isEmpty()) {
			return "unnamed_file";
		}

		// Extraire uniquement le nom de fichier (supprime tout chemin)
		String name = java.nio.file.Paths.get(rawFileName)
				.getFileName()
				.toString();

		// Ne conserver que les caractères alphanumériques, tirets, underscores et point
		name = name.replaceAll("[^a-zA-Z0-9.\\-_]", "_");

		// Limiter la longueur
		if (name.length() > 200) {
			name = name.substring(0, 200);
		}

		return name.isEmpty() ? "unnamed_file" : name;
	}

	// -------------------------------------------------------------------------
	// Références OSGi
	// -------------------------------------------------------------------------

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

}
