package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandExecutor {

	public static void main(String[] args) throws InterruptedException,
	IOException {
		String pathToScript = "/home/ferreraalexandre/Desktop/data-integration/";  //This must be an absolute path => no '~' allowed

		//execute("/bin/bash", pathToScript+"pan.sh", "/file", "tutorial/Hello.ktr", "/norep");

		execute("/bin/bash", pathToScript+"kitchen.sh", "./kitchen.sh", "/file", "/home/ferreraalexandre/Desktop/data-integration/tutorial/Hello.kjb", "list2.csv", "/norep");
	}

	
	
	
	/**
	 * execute the given command
	 * @param command format must be: "bin/bash" (for UNIX), path+command, arg1, arg2, etc...
	 */
	public static void execute(String ...command) throws InterruptedException, IOException{
		ProcessBuilder pb = new ProcessBuilder(command);
		//Thank you : https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/

		System.out.println("Run command: " + command.toString());
		Process process = pb.start();
		int errCode = process.waitFor();
		System.out.println("command executed, any errors? " + (errCode == 0 ? "No" : "Yes: " + errCode));
		System.out.println("Output:\n" + output(process.getInputStream()));			
	}

	private static String output(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + System.getProperty("line.separator"));
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}
}
