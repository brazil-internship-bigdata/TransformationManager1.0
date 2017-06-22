package dataManaging;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractItem implements Item {

	protected boolean jobRunning;
	protected Date lastTransformation;

	private DateFormat format = new SimpleDateFormat("dd.MM.yyyy-hh:mm:ss");
	
	public AbstractItem() {
		this.jobRunning = false;
		lastTransformation = null;		
	}
	
	public AbstractItem(String jobRunning, String lastTransformationDate) {
		this.jobRunning = jobRunning.equals("true");
		
	}
	
	
	@Override
	public boolean isJobRunning() {
		// TODO check if the job is scheduled on the computer
		return jobRunning;
	}



	@Override
	public String lastTransformationDate() {
		return format.format(lastTransformation);
	}


	
	@Override
	public void parseLastTransformationDate(String lastTransformationDate) {
		try {
			lastTransformation = format.parse(lastTransformationDate);
		} catch (ParseException e) {
			lastTransformation = null;
			System.err.println("date of last transformation could not be read. The date is now null");
			e.printStackTrace();
		}
	}

	
	
}
