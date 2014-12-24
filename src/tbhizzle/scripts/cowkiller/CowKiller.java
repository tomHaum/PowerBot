package tbhizzle.scripts.cowkiller;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

import tbhizzle.util.Task;

@Script.Manifest(name = "Cow Killer", description = "Kills cows and collects hides")
public class CowKiller extends PollingScript<ClientContext> implements PaintListener{
	Task[] tasks = {new AttackCow(ctx), new Bank(ctx), new PickUpHides(ctx)};
	AtomicInteger integ = new AtomicInteger();
	private long startTime;
	@Override
	public void poll() {
		for(Task task: tasks){
			if(task.activate()){
				task.execute();
				return;//so attack cows always comes before PickUpHides
			}
		}
		
	}
	 
	@Override
	public void start(){
		startTime = System.currentTimeMillis();
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(new Color(222, 16, 16, 128));
		g.fillRect(0,0,100,300);
		g.setColor(new Color(255,0,0,255));
		g.drawRect(1,1,100,300);
		g.drawString("Cow Killer", 20, 15);
		g.drawString("Hides banked: " + (28*((Bank)(tasks[1])).inventories), 4, 25);
		g.drawString("Run Time", 20, 35);
		g.drawString(getRunTime(), 20, 45);
	}
	
	private String getRunTime(){
		long diff = System.currentTimeMillis() - startTime;
		int seconds = (int) (diff / 1000) % 60 ;
		int minutes = (int) ((diff / (1000*60)) % 60);
		int hours   = (int) ((diff / (1000*60*60)) % 24);
		String result = "";
		result = String.format("%02d:%02d", minutes,seconds);
		if(hours > 0)result = hours + ":" + result;
		return result;
	}
}
