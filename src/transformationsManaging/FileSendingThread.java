package transformationsManaging;

import java.io.File;

public class FileSendingThread implements Runnable {

	private File fileToTransform;
	
	public FileSendingThread(File fileToTransform) {
		this.fileToTransform = fileToTransform;
	}
	
	@Override
	public void run() {
		File kjb = null;
		//TODO get kjb
		Transformer t = new Transformer("path to kitchen", kjb.getAbsolutePath(), fileToTransform);
		File csvFile = null; //TODO récupérer le résultat
		//TODO post csv
		csvFile.delete();
	}

	
	//TODO faire un script pour récupérer le plus récent du dossier (avec la même structure de nom) Ce script sera personalisé pour chaque company
	//ce script sera utilisé pour la partie automatique - périodique

	
}
