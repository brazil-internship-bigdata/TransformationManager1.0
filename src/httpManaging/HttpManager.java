package httpManaging;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import dataManaging.Item;
import main.Home;

public class HttpManager {

	private String url = "https://localhost";
	
	private String userName;
	private String password;
	private String connectionParameters;
	
	private class Result{
		int code;
		ArrayList<String> response;

		public Result(int code, ArrayList<String> response) {
			this.code = code;
			this.response = response;
		}
		
		public ArrayList<String> response() {
			return response;
		}
		public int code() {
			return code;
		}
	}
	
	
	boolean connect(String userName, String password) {
		
		String urlParameters = "connection?id=+"+userName+"&pw="+password;

		Result res;
		try {
			res = post(urlParameters);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
			
		if( res.code() == HttpURLConnection.HTTP_OK ) {
			//these 2 are probably useless
			this.userName = userName;
			this.password = password;

			this.connectionParameters = urlParameters;
			System.out.println("I most likely am not a good piece of code");
			return true;
		}
		else return false;

	}


	
	
	
	/**
	 * post the item identifier and give back the result.
	 * @param item
	 * @return the result returned by the post.
	 * @throws IOException if the post method failed
	 */
	private Result postIdentifier(Item item) throws IOException {
		String itemIdentifier = item.getIdentifier();
		String companyName = Home.COMPANY_NAME;
		
		//TODO identifier is an ambiguous name, it should be replaced... ItemIdentifier?
		String urlParameters = "transformation?company="+companyName+"&identifier="+itemIdentifier;
		
		Result res = post(urlParameters);
		return res;
		
	}

	
	
	/**
	 * post the paths of the files necessary to the transformation. This way the API will upload them
	 * @param item associated with this transformation
	 * @throws IOException if one of the posts request failed.
	 */
	void retrieveTransformationFiles(Item item) throws IOException {
		File dir = new File(item.transformationFolder());
		if( !dir.isDirectory() ) {
			item.generateFolders();
		}
		
		Result res = postIdentifier(item);
		
		ArrayList<String> filesToAsk = res.response;
		
		for(String filePath : filesToAsk) {
			String parameters = "something" + filePath + connectionParameters;//TODO

			System.out.println("I am a bad piece of code");
			
			post(parameters);
		}
		
	}
	
	
	
	
	
	private Result post(String parameters) throws IOException {
		URL obj = new URL(url);

		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		//String urlParameters = "connection?id=+"+userName+"&pw="+password;// = "search?q=aa&oq=aa&aqs=chrome..69i57j0l5.970j0j7&client=ubuntu&sourceid=chrome&ie=UTF-8";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();

		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + parameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		ArrayList<String> response = new ArrayList<String>();

		while ((inputLine = in.readLine()) != null) {
			response.add(inputLine);
		}
		in.close();


		//print result
		for(String line : response) {
			System.out.println(line);
			
		}

		return new Result(responseCode, response);
	}

}
