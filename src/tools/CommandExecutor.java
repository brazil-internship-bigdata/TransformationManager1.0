package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import dataManaging.Item;

public class CommandExecutor {

	public final static String pathToPentaho = "transformation/pentaho/data-integration/";

	// TODO remove this main
	public void main(String[] args) throws InterruptedException, IOException {
		String pathToScript = "/home/ferreraalexandre/Desktop/data-integration/"; // This
																					// must
																					// be
																					// an
																					// absolute
																					// path
																					// =>
																					// no
																					// '~'
																					// allowed

		// execute("/bin/bash", pathToScript+"pan.sh", "/file",
		// "tutorial/Hello.ktr", "/norep");

		execute("/bin/bash", pathToScript + "kitchen.sh", "./kitchen.sh", "/file",
				"/home/ferreraalexandre/Desktop/data-integration/tutorial/Hello.kjb", "list2.csv", "/norep");
	}

	private static String[] buildCommandObsolete(Item item) {

		/*
		 * command format: 0->/bin/bash, 1->path to kitchen.sh(here path to
		 * data-integration/), 2->"/file" (precedes the file containing the
		 * job), 3-> path to .kjb 4 to X-2 -> arguments from
		 * Item.commandLineArguments, X-1 -> "/norep"
		 */
		String[] commandLineArguments = item.commandLineArguments();

		int x = commandLineArguments.length + 5; // commandLineArguments from
													// item + arguments

		String[] command = new String[x];

		for (int i = 4; i < x - 1; i++) {
			command[i] = commandLineArguments[i - 4]; // i-4 because i starts at
														// 4
		}

		// TODO check environment
		command[0] = "/bin/bash";

		String pathToScript = new File(pathToPentaho).getAbsolutePath(); // This
																			// must
																			// be
																			// an
																			// absolute
																			// path
																			// =>
																			// no
																			// '~'
																			// allowed
		command[1] = pathToScript + "/kitchen.sh";

		// command[2] = "./kitchen.sh";

		command[2] = "/file";

		command[3] = item.jobFile().getAbsolutePath();

		command[x - 1] = "/norep";

		return command;
	}

	private static String[] buildCommand(Item item) {
		String[] command = new String[5];

		String pathToJob = item.jobFile().getAbsolutePath();

		// TODO treat windows, Mac OSX and UNIX

		command[0] = "/bin/bash";
		command[1] = new File(pathToPentaho).getAbsolutePath() + "/kitchen.sh";
		command[2] = "/file";
		command[3] = pathToJob;
		command[4] = "/norep";

		return command;
	}

	/**
	 * execute the given command
	 * 
	 * @param item
	 *            that must be transformed.
	 */
	public static void execute(Item item) throws InterruptedException, IOException {

		File job = new File(item.transformationFolder() + "rootJob.kjb");
		if (!job.exists())
			throw new IOException();

		String[] command = buildCommand(item);
		ProcessBuilder pb = new ProcessBuilder(command);
		// Thank you :
		// https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/

		System.out.println("Run command: ");
		for (int i = 0; i < command.length; i++)
			System.out.print(command[i] + " ");

		Process process = pb.start();
		int errCode = process.waitFor();

		System.out.println("\nOutput:\n" + output(process.getInputStream()));
		if (errCode != 0) {
			JOptionPane.showMessageDialog(null, "error during the transformation. errcode: " + errCode);
		} else {
			JOptionPane.showMessageDialog(null, "Ecureuil!");
		}

		// item.save();
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
	 * 
	 * @param command
	 *            format must be: "bin/bash" (for UNIX), path+command, arg1,
	 *            arg2, etc...
	 */
	public static void execute(String... command) throws InterruptedException, IOException {
		// String [] command = buildCommand(item);
		ProcessBuilder pb = new ProcessBuilder(command);
		// Thank you :
		// https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/

		System.out.println("Run command: " + command.toString());
		Process process = pb.start();
		int errCode = process.waitFor();
		System.out.println("command executed, any errors? " + (errCode == 0 ? "No" : "Yes: " + errCode));
		System.out.println("Output:\n" + output(process.getInputStream()));
	}

}
