package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.swing.JOptionPane;

import dataManaging.Item;

public class CommandExecutor {

	public static void main(String[] args) throws InterruptedException,
	IOException {
		String pathToScript = "/home/ferreraalexandre/Desktop/data-integration/";  //This must be an absolute path => no '~' allowed

		//execute("/bin/bash", pathToScript+"pan.sh", "/file", "tutorial/Hello.ktr", "/norep");

		execute("/bin/bash",
				pathToScript+"kitchen.sh",
				"./kitchen.sh",
				"/file",
				"/home/ferreraalexandre/Desktop/data-integration/tutorial/Hello.kjb",
				"list2.csv",
				"/norep");
	}

	
	private static String [] buildCommand(Item item) {
		
		/*command format: 
		0->/bin/bash,
		1->path to kitchen.sh(here path to data-integration/),
		2->"./kitchen.sh",
		3->"/file" (precedes the file containing the job),
		4 to X-1 -> arguments from Item.commandLineArguments,
		X+1 -> "/norep" */
		String [] commandLineArguments = item.commandLineArguments();
		
		int x = commandLineArguments.length + 5;
		
		String [] command = new String [x];
		
		for(int i = 4 ; i<x-1 ; i++) {
			command[i] = commandLineArguments[i-4]; //i-4 because i starts at 4
		}
		
		
		//TODO check environment
		command[0] = "/bin/bash";
		
		
		String pathToScript = "/home/ferreraalexandre/Desktop/data-integration/";  //This must be an absolute path => no '~' allowed
		command[1] = pathToScript;
		
		command[2] = "./kitchen.sh";
		
		command[3] = "/file";
		
		command[x-1] = "/norep";
		
		return command;
	}

	
		
	/**
	 * execute the given command
	 * @param item that must be transformed.
	 */
	public static void execute(Item item) throws InterruptedException, IOException{
		String [] command = buildCommand(item);
		ProcessBuilder pb = new ProcessBuilder(command);
		//Thank you : https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/

		System.out.println("Run command: " + command.toString());
		Process process = pb.start();
		int errCode = process.waitFor();

		
		System.out.println("Output:\n" + output(process.getInputStream()));			
		if(errCode != 0) {
			JOptionPane.showMessageDialog(null, "error during the transformation. errcode: " + errCode);
		}
		else {
			item.setLastTransformationDate(new Date());
		}
		
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
	
	
	
	
	
	/**
	 * execute the given command
	 * @param command format must be: "bin/bash" (for UNIX), path+command, arg1, arg2, etc...
	 */
	public static void execute(String ...command) throws InterruptedException, IOException{
		//String [] command = buildCommand(item);
		ProcessBuilder pb = new ProcessBuilder(command);
		//Thank you : https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/

		System.out.println("Run command: " + command.toString());
		Process process = pb.start();
		int errCode = process.waitFor();
		System.out.println("command executed, any errors? " + (errCode == 0 ? "No" : "Yes: " + errCode));
		System.out.println("Output:\n" + output(process.getInputStream()));			
	}

}
