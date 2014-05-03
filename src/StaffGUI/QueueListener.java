package StaffGUI;

import java.rmi.RemoteException;

import userInterfaces.SandwichQueueInterface;




public class QueueListener extends Thread
{

	private StaffMain staffMain;
	private SandwichQueueInterface queue;
	
	public QueueListener(StaffMain main, SandwichQueueInterface sQueue)
	{
		this.staffMain = main;
		this.queue = sQueue;
	}
	
	public void run()
	{
		while(true)
		{
			try {
				if(!this.queue.getQueue().isEmpty())
				{
						this.staffMain.runMain(staffMain, queue);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
