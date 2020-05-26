package com.listener.event;

public class MyRunnableTask implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
  System.out.println("SCHEDULER RUNNING");
	SmseventDao smsdao=new SmseventDao();
	
	
	try {
		smsdao.sendSMS();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
