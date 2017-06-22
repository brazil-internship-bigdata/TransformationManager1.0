package transformationsManaging;

import java.io.File;
import java.io.IOException;

import tools.CommandExecutor;

public class Transformer{

	private String pathToKitchen;
	private String pathToKjb;
	private File fileToTransform;
	
	
	public Transformer(String pathToKitchen, String pathToKjb, File fileToTransform) {
		super();
		this.pathToKitchen = pathToKitchen;
		this.pathToKjb = pathToKjb;
		this.fileToTransform = fileToTransform;
	}
	
	
	
	public void transform() {
		try {
			CommandExecutor.execute("/bin/bash", pathToKitchen+"kitchen.sh", "./kitchen.sh", "/file", pathToKjb, fileToTransform.getName(), "/norep");
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}
}
