package tbhizzle.rt6.cowkiller;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

import tbhizzle.rt6.cowkiller.data.MyItem;
import tbhizzle.rt6.cowkiller.gui.*;
import tbhizzle.rt6.cowkiller.tasks.*;
import tbhizzle.util.Task;

@Script.Manifest(name = "Cow Killer", description = "Kills cows and collects hides", properties="topic=1301927;game=6;hidden=false")
public class CowKiller extends PollingScript<ClientContext> implements PaintListener{
	public static boolean banking;
	List<Task<ClientContext>> tasks = new ArrayList<Task<ClientContext>>();
	
	boolean runScript = false;
	private boolean crafting;
	private boolean tanning;
	
	private MyItem tanningMyItem;
	private MyItem craftingMyItem;
	
	CowKilerGui frame;
	private MyItem finalMyItem;
	private Paint paint;
	
	@Override
	public void poll() {
		if (runScript) {
			if(ctx.widgets.component(1622,8).component(1).valid()){
				ctx.widgets.component(1622,8).component(1).click();
			}
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new CowKilerGui(CowKiller.this);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void repaint(Graphics g) {
		if(paint != null)paint.repaint(g);
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

	public void setTanningMyItem(MyItem leather) {
		this.tanningMyItem = leather;
	}
	public void setCraftingMyItem(MyItem myItem) {
		craftingMyItem = myItem;
	}
	
	public void exitGui() {
		//tasks.add(new AttackCow(ctx));
		tasks.add(new AttackCow(ctx));
		tasks.add(new PickUpHides(ctx));
		tasks.add(new WalkToCows(ctx));
		paint = new Paint(ctx);
		
		if(tanning){
			tasks.add(new TanHides(ctx, tanningMyItem));
			if(crafting){
				tasks.add(new CraftLeather(ctx, craftingMyItem));
				finalMyItem = craftingMyItem;
			}else{
				finalMyItem = tanningMyItem;
			}
		}else{
			finalMyItem = PickUpHides.COWHIDE;
		}
		if(banking){
			tasks.add(new Bank(ctx, finalMyItem, paint));
		}else{
			tasks.add(new Sell(ctx, finalMyItem));
		}
		runScript = true;
		frame.setVisible(false);
		frame.dispose();
		paint = new Paint(ctx);
		
	}

	
}
