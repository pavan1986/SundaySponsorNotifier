package org.ihfusa.ssn;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

// ...

public class GoogleFileRead {

	private static final String CLIENT_SECRET = "B-mL-e1yrWHLJ55-re8GU_BW";
	private static final String CLIENT_ID = "437177876571.apps.googleusercontent.com";
	private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

	
	public static void main(String[] args) {

		try {
			System.out.println(GoogleFileRead.downloadFile().length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	/**
	 * Print a file's metadata.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to print metadata for.
	 */
	private static void printFile(Drive service, String fileId) {

		try {
			File file = service.files().get(fileId).execute();

			System.out.println("Title: " + file.getTitle());
			System.out.println("Description: " + file.getDescription());
			System.out.println("MIME type: " + file.getMimeType());
		} catch (IOException e) {
			System.out.println("An error occured: " + e);
		}
	}

	/**
	 * Download a file's content.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param file
	 *            Drive File instance.
	 * @return InputStream containing the file's content if successful,
	 *         {@code null} otherwise.
	 * @throws
	 * @throws Exception
	 */

	
	
	public static java.io.File downloadFile() throws Exception {
		String fileId = "0Ap5qPFNBV2a0dHJKeTdFcUlqOVAzaDRVVUJFaW1LSVE";

		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET,
				Arrays.asList(DriveScopes.DRIVE)).setAccessType("online")
				.setApprovalPrompt("auto").build();

		String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI)
				.build();
		String code = AuthCodeRetriever.retrieveAuthCode(url);
		GoogleTokenResponse response = flow.newTokenRequest(code)
				.setRedirectUri(REDIRECT_URI).execute();
		GoogleCredential credential = new GoogleCredential()
				.setFromTokenResponse(response);

		// Create a new authorized API client
		Drive service = new Drive.Builder(httpTransport, jsonFactory,
				credential).build();
		FileList fileList=service.files().list().execute();
		fileList.get("Feast Sponsors 2014 Onwards");fileList.getItems().get(0).getExportLinks();
		InputStream inputStream=service.getRequestFactory().buildGetRequest(new GenericUrl(fileList.getItems().get(0).getExportLinks().get("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))).execute().getContent();
		java.io.File outputExcelSheet=new java.io.File("/Users/administrator/FEastSponsors.xls");

		java.io.OutputStream out=new java.io.FileOutputStream(outputExcelSheet);
		byte buf[]=new byte[1024];
		int len;
		while((len=inputStream.read(buf))>0)
		out.write(buf,0,len);
		out.close();
		inputStream.close();
		System.out.println("\nFile is created...................................");
//		if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//			try {
//				HttpResponse resp = service.getRequestFactory()
//						.buildGetRequest(new GenericUrl(file.getDownloadUrl()))
//						.execute();
//				return resp.getContent();
//			} catch (IOException e) {
//				// An error occurred.
//				e.printStackTrace();
//				return null;
//			}
//		} else {
//			System.out.println("The file doesn't have any content stored on Drive");
//			return null;
//		}
		return outputExcelSheet;
	}

	// ...
}