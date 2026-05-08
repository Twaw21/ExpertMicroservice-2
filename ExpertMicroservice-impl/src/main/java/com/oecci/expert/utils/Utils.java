package com.oecci.expert.utils;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Utils {
	
	private static int httpResponseCode;
	private static boolean isDocWellUploaded;

	public static int getHttpResponseCode() {
		return httpResponseCode;
	}
	public static boolean isDocWellUploaded() {
		return isDocWellUploaded;
	}
	
	 public static URI buildUrl(String urlDoc) {
	        System.out.println("formatting the url for downloading.");

	        try {
	            URL urlPDF = new URL(urlDoc);
	            System.out.println("protocol :" + urlPDF.getProtocol());
	            System.out.println("host :" + urlPDF.getHost());
	            System.out.println("query :" + urlPDF.getQuery());
	            System.out.println("path :" + urlPDF.getPath());
	            URI uri = new URI(urlPDF.getProtocol(), urlPDF.getAuthority(), urlPDF.getPath(), urlPDF.getQuery(), (String)null);
	            return uri;
	        } catch (MalformedURLException var3) {
	            System.out.println("MalformedURLException : " + var3.getLocalizedMessage());
	            return null;
	        } catch (URISyntaxException var4) {
	            System.out.println("URISyntaxException : " + var4.getLocalizedMessage());
	            return null;
	        }
	    }

	 public static String getOAuth2Token(String baseUrl) throws IOException, ParseException {
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

				// Créer la requete HTTP POST
				System.out.println("> Getting token url : " + baseUrl + Constants.DEV_TOKEN_URL);
				HttpPost post = new HttpPost(baseUrl + Constants.DEV_TOKEN_URL);
				post.addHeader("Content-Type", "application/x-www-form-urlencoded");
				String credentials = Constants.DEV_CLIENT_ID + ":" + Constants.DEV_CLIENT_SECRET;
				String encodedCredentials = Base64.getEncoder()
						.encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

				post.addHeader("Authorization", "Basic " + encodedCredentials);

				// Ajouter le payload
				String body = "grant_type=client_credentials";
				post.setEntity(new StringEntity(body, StandardCharsets.UTF_8));

				// Ex�cuter la requ�te
				try (CloseableHttpResponse response = httpClient.execute(post)) {
					HttpEntity entity = response.getEntity();
					String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
					//System.out.println("> Token response : "+responseString);
					// Extraire le token de la r�ponse JSON
					JSONObject jsonResponse = null;
					try {
						jsonResponse = JSONFactoryUtil.createJSONObject(responseString);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
					return jsonResponse.getString("access_token");
				}
			}
		}

	private static HttpResponse httpResponse = null;
	
	 public static JSONObject executeHttpRequest(String baseURL, String link, JSONObject data_to_post, String typeRequest) {
		 JSONObject formated_response = null;
		 System.out.println("> headless URL to execute : " + link);
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				String access_token = getOAuth2Token(baseURL);
				System.out.println("> Headless access token : "
						+ access_token.substring(0, 100));
				
				URI url = Utils.buildUrl(link);
				switch (typeRequest) {
					case "GET":
						System.out.println("> It's a GET request");
						HttpGet get_request = new HttpGet(url);
						get_request.setHeader("Authorization",
								"Bearer " + access_token);
						get_request.setHeader("Accept", "application/json");
						get_request.setHeader("Content-Type" ,"application/json");
							
						httpResponse = httpClient.execute(get_request);
						break;
					case "POST":
						System.out.println("> It's a POST request : "+data_to_post.toString());
						HttpPost post_request = new HttpPost(url);
						post_request.setHeader("Authorization",
								"Bearer " + access_token);
						post_request.setHeader("Accept", "application/json");
						post_request.setHeader("Content-Type" ,"application/json");
						if(data_to_post != null)
							post_request.setEntity(new StringEntity(data_to_post.toString(), StandardCharsets.UTF_8));
						httpResponse = httpClient.execute(post_request);
						break;
					case "PUT":
						System.out.println("> It's a PUT request : "+data_to_post.toString());
						HttpPut put_request = new HttpPut(url);
						put_request.setHeader("Authorization",
								"Bearer " + access_token);
						put_request.setHeader("Accept", "application/json");
						put_request.setHeader("Content-Type", "application/json");
						if(data_to_post != null)
							put_request.setEntity(new StringEntity(data_to_post.toString(), StandardCharsets.UTF_8));

						httpResponse = httpClient.execute(put_request);
						break;
					case "DELETE":
						break;
				}
				
				System.out.println("> Reading result : "+httpResponse.toString());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String responseBody = EntityUtils.toString(httpResponse.getEntity());
					formated_response = JSONFactoryUtil.createJSONObject(responseBody);
					System.out.println(">> formated json response : " + formated_response.toString());
				} 
			} catch (Exception e) {
				System.err.println(
						"Erreur lors de l'ex�cution : " + e.getMessage());
				return null;
			}
			return formated_response;
	 }
	 
	public static JSONObject uploadDocumentToLiferay(String baseURL, String link, String documentJson, File fileToUplaod)
			throws IOException {
		JSONObject formated_response = null;
		System.out.println("> headless URL to execute uploading file : " + link);
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			
			// V�rifier que le fichier existe
	//		if (!fileToUplaod.exists()) {
	//			throw new IOException("Le fichier n'existe pas : " + fileToUplaod);
	//		}
			String access_token = getOAuth2Token(baseURL);
			System.out.println("> Headless access token for uploading file : "
					+ access_token.substring(0, 100));
			
			URI url = Utils.buildUrl(link);
			
			System.out.println("> It's a POST request : "+documentJson);
			HttpPost post_request = new HttpPost(url);
			post_request.setHeader("Authorization", "Bearer " + access_token);
			post_request.setHeader("Accept", "application/json");
			//post_request.setHeader("Content-Type" ,"multipart/form-data");
	
			
			/*MultipartEntity multipartEntity = new MultipartEntity();
	        // Ajouter le JSON document
	        multipartEntity.addPart("document", new StringBody(documentJson, "application/json", Charset.forName("UTF-8")));
	        // Ajouter le fichier
	        multipartEntity.addPart("file", new FileBody(fileToUplaod, "application/pdf"));
	        */
			
			
	        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
	        		.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	        		//.setContentType(ContentType.MULTIPART_FORM_DATA)
	                //.setBoundary("----WebKitFormBoundary" + System.currentTimeMillis());
	        
	        // Add JSON part
            builder.addTextBody("document", documentJson);//, ContentType.APPLICATION_JSON);
            
            // Add file part
            builder.addBinaryBody(
                    "file", 
                    fileToUplaod//, 
//                    ContentType.APPLICATION_OCTET_STREAM, 
//                    fileToUplaod.getName()
            );
            
//	        builder.addPart("document", new StringBody(documentJson, ContentType.APPLICATION_JSON));
//	        builder.addPart("file", new FileBody(fileToUplaod, ContentType.create("application/pdf")));
	        
	        
	        // D�finir l'entit� HTTP
	        post_request.setEntity(builder.build());
	        
	        
			httpResponse = httpClient.execute(post_request);
			System.out.println("> Reading result : "+httpResponse.toString());
			httpResponseCode = httpResponse.getStatusLine().getStatusCode(); 
			isDocWellUploaded = false;
			if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
				isDocWellUploaded = true;
	            System.out.println("Document upload� avec succ�s !");
				String responseBody = EntityUtils.toString(httpResponse.getEntity());
				formated_response = JSONFactoryUtil.createJSONObject(responseBody);
				System.out.println(">> formated json response : " + formated_response.toString());
			} 
		} catch (Exception e) {
			System.err.println(
					"Erreur lors de l'ex�cution de l'uploading du file : " + e.getMessage());
			return null;
		}
		return formated_response;
	}
	
	public static void linkUserToSiteAndOrganization(Long groupId, Long organizationId, Long userId) {
		boolean isGroupMember = UserLocalServiceUtil
				.hasGroupUser(groupId, userId);
		boolean isOrganizationMember = UserLocalServiceUtil
				.hasOrganizationUser(organizationId, userId);
		if (!isGroupMember && !isOrganizationMember) {
			// Add the user to the site
			UserLocalServiceUtil.addGroupUser(groupId,
					userId);
			System.out.println(
					">>> User " + userId + " added to site (group) "
							+ groupId);
			OrganizationLocalServiceUtil.addUserOrganization(userId, organizationId);
			System.out.println(
					">>> User " + userId + " added to organization "+ organizationId);

			// UserLocalServiceUtil.addOrganizationUser(liferay_group.getOrganizationId(),
			// userId);
			
		} else {
			System.out.println("User " + userId
					+ " is already a member of site (group) " + groupId);
		}
		
	}
	
	// Caract�res possibles pour le code
    public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARS.length());
            code.append(CHARS.charAt(index));
        }
        
        return code.toString();
    }

	public static String processMailTemplate(String templatePath, Class crclass, Map<String, Object> templateVariables) {
		// 1. Create template resource from path
        TemplateResource templateResource = new URLTemplateResource(
            templatePath, 
            crclass.getResource(templatePath)  // This resolves the actual file
        );
        
        // Check if template exists
        if (crclass.getResource(templatePath) == null) {
            throw new RuntimeException("Template not found: " + templatePath);
        }
        
        // 2. Create FreeMarker template
        Template template = null;
		try {
			template = TemplateManagerUtil.getTemplate(
			    TemplateConstants.LANG_TYPE_FTL,  // FreeMarker template type
			    templateResource,
			    false  // Don't restrict template
			);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        // 3. Add variables to template context
        for (Map.Entry<String, Object> entry : templateVariables.entrySet()) {
            template.put(entry.getKey(), entry.getValue());
        }
        
        // 4. Process template to generate final HTML
        StringWriter stringWriter = new StringWriter();
        try {
			template.processTemplate(stringWriter);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String mail_content = stringWriter.toString();
        
        return mail_content;
	}
}
