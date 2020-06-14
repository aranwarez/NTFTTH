package com.listener.event;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class Config
 *
 */
@WebListener
public class Config implements ServletContextListener {

	// private TimerTask timer1;
	// private TimerTask timer2;
	// private TimerTask timer3;
	private ScheduledExecutorService scheduler1;
	private ScheduledExecutorService scheduler2;
	private ScheduledExecutorService scheduler3;

	/**
	 * Default constructor.
	 */
	public Config() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		// timer1.cancel();
		// timer2.cancel();
		// timer3.cancel();
		scheduler1.shutdown();
		scheduler2.shutdown();
		scheduler3.shutdown();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		// Data data = new Data();
		System.out.println("starting");
		// Calendar today = Calendar.getInstance();
//		today.set(Calendar.HOUR_OF_DAY, 1);
//		today.set(Calendar.MINUTE, 05);
		// today.set(Calendar.SECOND, 0);

		// every night at 2am you run your task
		// Timer timer = new Timer();
		// timer.schedule(new YourTask(), today.getTime(),
		// TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
//-----
//		LocalTime now = LocalTime.now();
//		LocalTime nextRun = now.withHour(02).withMinute(15).withSecond(0);
//		if (now.compareTo(nextRun) > 0)
//			nextRun = nextRun.plusHours(24);
//
//		Duration duration = Duration.between(now, nextRun);
//		long initalDelay = duration.getSeconds();
//		System.out.println(initalDelay/60);
//		System.out.println(initalDelay/60/60);

		
	//---------	

		LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(10).withMinute(15).withSecond(0);
        if(zonedNow.compareTo(zonedNextTarget) > 0)
            zonedNextTarget = zonedNextTarget.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
		long initalDelay = duration.getSeconds();
	//	System.out.println(initalDelay/60);
	//	System.out.println(initalDelay/60/60);
        

		//second timer
		ZonedDateTime zonedNextTarget2 = zonedNow.withHour(14).withMinute(30).withSecond(0);
        if(zonedNow.compareTo(zonedNextTarget2) > 0)
            zonedNextTarget2 = zonedNextTarget2.plusDays(1);

        Duration duration2 = Duration.between(zonedNow, zonedNextTarget2);
		long initalDelay2 = duration2.getSeconds();
	//	System.out.println(initalDelay2/60);
	//	System.out.println(initalDelay2/60/60);
		
		//third timer
		//second timer
		ZonedDateTime zonedNextTarget3 = zonedNow.withHour(19).withMinute(00).withSecond(0);
        if(zonedNow.compareTo(zonedNextTarget3) > 0)
            zonedNextTarget3 = zonedNextTarget3.plusDays(1);

        Duration duration3 = Duration.between(zonedNow, zonedNextTarget3);
		long initalDelay3 = duration3.getSeconds();
		System.out.println(initalDelay3/60);
		System.out.println(initalDelay3/60/60);

		

		
		ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
		ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(1);
		ScheduledExecutorService scheduler3 = Executors.newScheduledThreadPool(1);
		scheduler1.scheduleAtFixedRate(new MyRunnableTask(), initalDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
		scheduler2.scheduleAtFixedRate(new MyRunnableTask(), initalDelay2, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
		scheduler3.scheduleAtFixedRate(new MyRunnableTask(), initalDelay3, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
	}

}
