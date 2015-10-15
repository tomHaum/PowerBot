package tbhizzle.scripts.cowkiller;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

import tbhizzle.scripts.cowkiller.data.Item;
import tbhizzle.scripts.cowkiller.gui.Gui;
import tbhizzle.scripts.cowkiller.tasks.*;
import tbhizzle.util.Task;

@Script.Manifest(name = "Cow Killer", description = "Kills cows and collects hides")
public class CowKiller extends PollingScript<ClientContext> implements PaintListener{
	public static boolean banking;
	List<Task> tasks = new ArrayList<Task>();
	AtomicInteger integ = new AtomicInteger();
	private long startTime;
	
	boolean runScript = false;
	private boolean crafting;
	private boolean tanning;
	
	private Item tanningItem;
	private Item craftingItem;
	
	Gui frame;
	private Item finalItem;
	
	@Override
	public void poll() {
		if (runScript) {
			for (Task task : tasks) {

				if (task.activate()) {
					task.execute();
					return;// so attack cows always comes before PickUpHides
				}
			}
		}
		
	}
	 
	@Override
	public void start(){
		startTime = System.currentTimeMillis();
		banking = false;//set by gui
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Gui(CowKiller.this);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(new Color(222, 16, 16, 128));
		g.fillRect(0,0,100,300);
		g.setColor(new Color(255,0,0,255));
		g.drawRect(1,1,100,300);
		g.drawString("Cow Killer", 20, 15);
//		g.drawString("Hides banked: " + (28*((Bank)(tasks[1])).inventories), 4, 25);
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

	public void setCrafting(boolean selected) {
		crafting = selected;
		
	}
	public void setTanning(boolean selected) {
		tanning = selected;
	}
	public void setBanking(boolean selected) {
		banking = selected;
	}

	public void setTanningItem(Item leather) {
		this.tanningItem = leather;
	}
	public void setCraftingItem(Item item) {
		craftingItem = item;
	}
	
	public void exitGui() {
		tasks.add(new AttackCow(ctx));
		tasks.add(new PickUpHides(ctx));
		tasks.add(new WalkToCows(ctx));
		
		if(tanning){
			tasks.add(new TanHides(ctx,tanningItem));
			if(crafting){
				tasks.add(new CraftLeather(ctx,craftingItem));
				finalItem = craftingItem;
			}else{
				finalItem = tanningItem;
			}
		}else{
			finalItem = PickUpHides.COWHIDE;
		}
		if(banking){
			tasks.add(new Bank(ctx, finalItem));
		}else{
			tasks.add(new SellLeatherItem(ctx, finalItem));
		}
		runScript = true;
		frame.setVisible(false);
		frame.dispose();
		
	}

	
}
