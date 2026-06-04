package com.oecci.expert.utils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.PortalUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtil {

	private static final Log _log = LogFactoryUtil.getLog(SecurityUtil.class);
	
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/GCM/NoPadding";

    private static final int AES_KEY_SIZE = 256;
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * Génère une clé AES.
     * À faire une seule fois, puis stocker la clé dans un endroit sécurisé.
     */
    public static String generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE);

        SecretKey secretKey = keyGenerator.generateKey();

        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * Chiffre une donnée texte.
     */
    public static String encrypt(String plainText, String base64SecretKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

        byte[] iv = new byte[GCM_IV_LENGTH];
        secureRandom.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);

        return Base64.getEncoder().encodeToString(byteBuffer.array());
    }

    /**
     * Déchiffre une donnée texte.
     */
    public static String decrypt(String encryptedText, String base64SecretKey) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedBytes);

        byte[] iv = new byte[GCM_IV_LENGTH];
        byteBuffer.get(iv);

        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String secretKey = generateSecretKey();

        System.out.println("Clé secrète : " + secretKey);

        String originalData = "Donnée sensible à protéger";

        String encryptedData = encrypt(originalData, secretKey);
        System.out.println("Donnée chiffrée : " + encryptedData);

        String decryptedData = decrypt(encryptedData, secretKey);
        System.out.println("Donnée déchiffrée : " + decryptedData);
    }

    private static String _getCookies(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
            }
        }
        return sb.toString();
    }
    
    private static String csrfToken = null;
    
    public static String getCrsfToken() {
    	return csrfToken;
    }
    
    private static String cookies = null;
    public static String getCookies() {
    	return cookies;
    }
    
	public static boolean checkAccess(HttpServletRequest _httpServletRequest, User user, String[] roles) {

		
		_log.info("User found by PortalUtil.getUser(request) : "+user.getFullName());
	
//		 // Récupérer l'utilisateur courant
//       User c_user = _userLocalService.getUser(
//           PrincipalThreadLocal.getUserId()
//       );
//       _log.info("User found by PrincipalThreadLocal.getUserId() : "+c_user.getFullName());
//       

       csrfToken = AuthTokenUtil.getToken(
               _httpServletRequest
           );
       
       cookies = _getCookies(_httpServletRequest);
       
//       _log.info("xcrsf by AuthTokenUtil.getToken : "+csrfToken);
//       _log.info("headers names : "+_httpServletRequest.getHeader("x-csrf-token"));
//       _log.info("Parcourir l'Enumeration des headers de la request");
//       while (_httpServletRequest.getHeaderNames().hasMoreElements()) {
//       	_log.info("headers names : "+_httpServletRequest.getHeaderNames());
       	
//       }
//      HttpSession session = _httpServletRequest.getSession(true);
//		Enumeration<String> enume = session.getAttributeNames();
//		System.out.println("session data : " + session.getAttributeNames().toString());

//		_log.info("Parcourir l'Enumeration des attributs de session");
//		while (enume.hasMoreElements()) {
//			String element = enume.nextElement();
//			System.out.println("Element: " + element);
//		}
       
       long companyId = user.getCompanyId();
       
       // Vérifier si l'utilisateur a un rôle spécifique
       boolean hasRole;
		try {
			hasRole = RoleLocalServiceUtil.hasUserRoles(
			       user.getUserId(),
			       companyId,
			       roles,  // Nom du rôle dans Liferay
			       true                  // true = inclure les rôles hérités
			   );
			return hasRole;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	private static	JSONObject result = null;
	public static JSONObject getResult() {
		return result;
	}
	
	public static User checkUser(HttpServletRequest _httpServletRequest, String resourceName) {
		_log.info(">>>> " + resourceName +" <<<<");
		JSONObject result = null;
		try {
		if (_httpServletRequest == null ) {
			 _log.info(">> Object request does not exist. ");
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Objet request introuvable");
			result.put("data", "");
			System.out.println("> Returning response");
	    }
		User user;
			user = PortalUtil.getUser(_httpServletRequest);
		
		if (user == null) {
			_log.debug("Aucun utilisateur connecté - fin du traitement.");
			_log.info(">> Object request does not exist. ");
			result = JSONFactoryUtil.createJSONObject();
			result.put("code", Constants.HTTP_ERROR_NOT_FOUND);
			result.put("message", "Aucun utilisateur connecté");
			result.put("data", "");
			System.out.println("> Returning response");
		}
		return user;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
public static ServiceContext getServiceContext(Long groupId) {
		
		try {
			Group group = GroupLocalServiceUtil.fetchGroup(groupId);
		} catch (Exception e) {
			System.out.println("group not found with id : "+groupId);
		}
		ServiceContext serviceContext = null;
//        if (group != null) {
        	System.out.println("Service context ready to be initialized by groupId");
        	serviceContext = ServiceContextThreadLocal.getServiceContext();
        	serviceContext.setScopeGroupId(groupId);
            ServiceContextThreadLocal.pushServiceContext(serviceContext);
            System.out.println("Service context well initialize with group");
//        }
		
		
//        if (serviceContext == null) {
//            serviceContext = new ServiceContext();
//        }
//
//        // Configurez le ServiceContext si nécessaire
//        serviceContext.setScopeGroupId(groupId);

        return serviceContext;
    }

}