package transformationsManaging;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JobScheduler {

	public JobScheduler() {
		ScheduledExecutorService scheduledExecutorService =
		        Executors.newSingleThreadScheduledExecutor();

		
		scheduledExecutorService.scheduleAtFixedRate (new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("coucou");
			}			
		},
				1,
				5,
				TimeUnit.SECONDS);

	
		scheduledExecutorService.scheduleAtFixedRate (new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("'Cureuil!");
			}			
		},
				1,
				2,
				TimeUnit.SECONDS);

	}
	
	
	public static void main(String [] args) {
		new JobScheduler();
	}
}
