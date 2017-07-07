package httpManaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import dataManaging.Item;
import main.Home;

public class HttpManager {

	String url = "localhost";
	
	

	private class Result{
		int code;
		String response;

		public Result(int code, String response) {
			this.code = code;
			this.response = response;
		}
		
		public String response() {
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
			
		return res.code() == HttpURLConnection.HTTP_OK;
	}


	
	
	
	
	boolean postIdentifier(Item item) {
		String identifier = item.getIdentifier();
		String companyName = Home.COMPANY_NAME;
		
		//TODO identifier is an ambiguous name, it should be replaced
		String urlParameters = "transformation?company="+companyName+"&identifier="+identifier;
		
		try {
			Result res = post(urlParameters);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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

		/** probably useless TODO **/
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + parameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		/** Probably useless TODO**/

		//print result
		System.out.println(response.toString());

		return new Result(responseCode, response.toString());
	}
}
