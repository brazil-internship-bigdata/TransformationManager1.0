package httpManaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import dataManaging.Item;

public class HttpManager {

	private final String	url			= "http://localhost:8080/webapp/";
	private final String	savingsPath	= "savings/API/ids";

	private String			userName;
	private String			password;

	private boolean			connected;

	private class Result {
		private int					code;
		private ArrayList<String>	response;

		public Result(int code, ArrayList<String> response) {
			this.code = code;
			this.response = response;
		}

		public String getFileBody() {
			StringBuffer body = new StringBuffer();

			for (String line : response) {
				body.append(line);
			}

			return body.toString();
		}
	}

	public HttpManager() {
		// For now, we assume that the connection wasn't made. This might change
		// if the ids are correctly stored in the savings File
		connected = false;

		Path path = Paths.get(savingsPath);
		if (!Files.exists(path))
			return;

		try {
			List<String> content = Files.readAllLines(path);

			if (content.size() != 2) {
				// The file's format isn't normal. => we are not connected
				connected = false;
				return;
			}

			// Here, the savingsFiles exists, in the right format. So we use
			// the ID and password to create this object
			String idLine = content.get(0);
			String pwLine = content.get(1);

			userName = idLine.replaceAll("ID=", "");
			password = pwLine.replaceAll("PASSWORD=", "");
			connected = true;

		} catch (IOException e) {
			// If we don't manage to read the savings File, we are not
			// connected.
			connected = false;
			e.printStackTrace();
			return;
		}

	}

	/**
	 * Tests the connection ids to communicate with the API.
	 * 
	 * @param userName
	 *            of the company
	 * @param password
	 *            of the company
	 * @return true if the API returns a positive question to the company. false
	 *         if the response isn't.
	 * @throws IOException
	 *             In case of failure during the communication with the API
	 */
	public void connect(String userName, String password) throws IOException {

		String urlParameters = "company_name=" + userName + "&password_company=" + password;

		Result res = get(urlParameters, url + "loginAPI");

		if (res.code != HttpURLConnection.HTTP_OK) {
			connected = false;
			return;
		}

		this.userName = userName;
		this.password = password;

		System.out.println("I most likely am not a good piece of code");

		// write the ids to connect to the lab's API.
		File savingsFile = new File(savingsPath);
		if (!savingsFile.exists()) {
			savingsFile.getParentFile().mkdirs();
			savingsFile.createNewFile();
		}

		PrintWriter printer = null;
		try {
			printer = new PrintWriter(savingsFile);
			printer.print("ID=" + userName + "\nPASSWORD=" + password);
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
	 * 
	 * @param item
	 * @return the result returned by the post.
	 * @throws IOException
	 *             if the post method failed
	 */
	private Result getFileList(Item item) throws IOException {
		String itemIdentifier = item.getIdentifier();

		// TODO identifier is an ambiguous name, it should be replaced...
		// ItemIdentifier?
		String urlParameters = "company_name=" + userName + "/" + itemIdentifier; // TODO
																					// verify
																					// if
																					// necessary
																					// :
																					// +
																					// "&password="
																					// +
																					// password;

		Result res = get(urlParameters, url + "/fileList");
		return res;

	}

	/**
	 * post the paths of the files necessary to the transformation. This way the
	 * API will upload them
	 * 
	 * @param item
	 *            associated with this transformation
	 * @throws IOException
	 *             if one of the posts request failed.
	 */
	public void retrieveTransformationFiles(Item item) throws IOException {
		File localDir = new File(item.transformationFolder());
		if (!localDir.isDirectory()) {
			item.generateFolders();
		}

		Result res = getFileList(item);

		ArrayList<String> filesToAsk = res.response;

		for (String remoteFilePath : filesToAsk) {
			String parameters = "file=" + remoteFilePath + "&" + connectionParameters();
			Result fileReceived = post(parameters, url + "/download");

			// Check the http code result.
			if (fileReceived.code != HttpURLConnection.HTTP_OK) {
				throw new IOException("One of the file couldn't be retrieved");
			}

			// Get the fileReceived's body
			String fileBody = fileReceived.getFileBody();

			// get path of the file to download (fileName is in the
			// remoteFilePath. path is localDir/fileName)
			String fileName = new File(remoteFilePath).getName();
			File localFile = new File(localDir, fileName);
			localFile.createNewFile();

			// Create a print writer to set the body of the recently created
			// file
			PrintWriter pw = new PrintWriter(localFile);
			pw.write(fileBody);
			pw.close();
		}

	}

	private String connectionParameters() {
		return "company_name=" + userName + "&password=" + password;
	}

	private Result post(String parameters, String url) throws IOException {
		URL obj = new URL(url);

		byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add request header
		con.setRequestMethod("POST");
		// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Length", Integer.toString(postDataLength));

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		ArrayList<String> response = new ArrayList<String>();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + parameters);
		System.out.println("Response Code : " + responseCode);

		while ((inputLine = in.readLine()) != null) {
			response.add(inputLine);
		}
		in.close();

		// print result
		System.out.println("code = " + responseCode);

		for (String line : response) {
			System.out.println(line);
		}

		return new Result(responseCode, response);
	}

	// HTTP GET request
	private Result get(String parameters, String url) throws IOException {

		URL obj = new URL(url + "?" + parameters);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		con.setDoOutput(true);
		con.setInstanceFollowRedirects(false);
		con.setRequestProperty("Content-Type", "text/plain");
		con.setRequestProperty("charset", "utf-8");
		con.connect();

		BufferedReader in = null;

		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} catch (IOException e) {
			return new Result(con.getResponseCode(), null);
		}

		String inputLine;
		ArrayList<String> response = new ArrayList<String>();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		while ((inputLine = in.readLine()) != null) {
			response.add(inputLine);
		}
		in.close();

		int code = con.getResponseCode();

		// print result
		System.out.println("code = " + code);

		for (String line : response) {
			System.out.println(line);
		}

		return new Result(code, response);

	}

}
