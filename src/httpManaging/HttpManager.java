package httpManaging;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dataManaging.Item;
import main.Home;

public class HttpManager {

	private final String url = "https://localhost";
	private final String savingsPath = "savings/API/ids";
	
	private String userName;
	private String password;
	
	private boolean connected;
	
	
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
	
	
	
	public HttpManager() {
		//For now, we assume that the connection wasn't made. This might change if the ids are correctly stored in the savings File
		connected = false;

		Path path = Paths.get(savingsPath);
		if( !Files.exists(path) ) {
			return;
		} else {

			try {
				List<String> content = Files.readAllLines(path);
			
				if(content.size() != 2) {
					//The file's format isn't normal. => we are not connected
					connected = false;
					return;
				}
				
				//Here, the savingsFiles exists, in the right format. So we use the ID and password to create this object
				String idLine = content.get(0);
				String pwLine = content.get(1);
				
				userName = idLine.replaceAll("ID=", "");
				password = pwLine.replaceAll("PASSWORD=", "");
				connected = true;
				
			} catch (IOException e) {
				//If we don't manage to read the savings File, we are not connected.
				connected = false;
				e.printStackTrace();
				return;
			}
			
			
		}
	}
	
	
	
	/**
	 * Tests the connection ids to communicate with the API.
	 * @param userName of the company
	 * @param password of the company
	 * @return true if the API returns a positive question to the company. false if the response isn't.
	 * @throws IOException In case of failure during the communication with the API
	 */
	public void connect(String userName, String password) throws IOException {
		
		String urlParameters = "connection?id=+"+userName+"&pw="+password;

		Result res = post(urlParameters);
			
		if( res.code() != HttpURLConnection.HTTP_OK ) {
			connected = false;
			return;
		}
		
		this.userName = userName;
		this.password = password;

		System.out.println("I most likely am not a good piece of code");

		//write the ids to connect to the lab's API.
		File savingsFile = new File(savingsPath);	
		if( !savingsFile.exists() ) {
			savingsFile.getParentFile().mkdirs();
			savingsFile.createNewFile();
		}
		
		PrintWriter printer = null;
		try {
			printer = new PrintWriter(savingsFile);
			printer.print("ID="+userName+"\nPASSWORD="+password);
		} catch (IOException e) {
			System.err.println("I didn't manage to print the ids in a file.");
			e.printStackTrace();
		} finally {
			printer.flush();
			printer.close();
		}
		
		connected = true;
		return;
	}


	public boolean connected() {
		return connected;
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
			String parameters = "something" + filePath + connectionParameters();//TODO

			System.out.println("I am a bad piece of code");
			
			post(parameters);
		}
		
	}
	
	
	
	
	//TODO replace pseudo code
	private String connectionParameters() {
		return "something" + userName + "anotherThing" + password;
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
