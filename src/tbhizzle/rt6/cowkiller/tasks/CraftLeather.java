package tbhizzle.rt6.cowkiller.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Identifiable;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Game;
import org.powerbot.script.rt6.Interactive;
import org.powerbot.script.rt6.Item;

import tbhizzle.rt6.cowkiller.data.MyItem;
import tbhizzle.util.Task;
import tbhizzle.rt6.cowkiller.util.WidgetTask;

public class CraftLeather extends Task<ClientContext> {
	//need to add leather vs hardleather
	private Item needle,thread;
	//1061 boots, 1059 gloves
	private Component leatherItemComponent;
	public static MyItem craftingMyItem;
	public static boolean active = false;
	public CraftLeather(ClientContext ctx, MyItem craftingMyItem) {
		super(ctx);
		this.craftingMyItem = craftingMyItem;
		active = true;
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28
			    && ctx.backpack.name(TanHides.tanningMyItem.getName()).count() > 0;
	}

	@Override
	public void execute() {
		System.out.println("Crafting");
		if(ctx.chat.chatting()){
			ctx.chat.clickContinue(true);
		}
		
		if(needle == null || !needle.valid()){
			if(ctx.backpack.select().name("Needle").count() > 0){
				needle = ctx.backpack.poll();
			}else{
				ctx.controller.stop();
			}
		}
		if(thread == null || !thread.valid()){
			if(ctx.backpack.select().name("Thread").count() > 0){
				thread = ctx.backpack.poll();
			}else{
				ctx.controller.stop();
			}
		}
		if(null == leatherItemComponent){
			//gets the right crafting component
			for(Component c: ctx.widgets.component(1371, 44).components()){
				System.out.println(c.itemId());
				if(c.itemId() == craftingMyItem.getId()){
				leatherItemComponent = c;
					break;
				}	
			}
		}
		if(!craftingTasks[0].activate() && !craftingTasks[1].activate() && !craftingTasks[2].activate()){
			needle.interact("Use");
			ctx.backpack.select().id(TanHides.tanningMyItem.getId()).poll().interact("Use");
		}else{
			for(Task t: craftingTasks){
				if(t.activate()){
					System.out.println(t.toString());
					t.execute();
				}
			}
		}

	}
	
	WidgetTask[] craftingTasks = {
			//checks for the crafting window and waits
			new WidgetTask(ctx, new int[] {1251, 13}, null) {

				@Override
				public boolean activate() {
					if (windowOpenWidget == null) {
						windowOpenWidget = findComponent(windowOpenIds);
					}
					if (windowOpenWidget == null)
						return false;

					return windowOpenWidget.valid() && windowOpenWidget.visible();
				}
				@Override
				public boolean checkFor() {
					return !windowOpenWidget.visible();
				}
				@Override
				public boolean doStuff() {
					return false;
				}
				@Override
				public String toString(){
					return "Crafting wait";
				}
			},
			//checks the correct item being selected
			new WidgetTask(ctx, new int[] { 1370, 56 }, null) {
				@Override
				public boolean activate() {
					if(null == clickComponent){
						//gets the right crafting component
						for(Component c: ctx.widgets.component(1371, 44).components()){//componet that holds all the leather crafting recipies
							System.out.println(c.itemId());
							if(c.itemId() == craftingMyItem.getId()){
							clickComponent = c;
								break;
							}	
						}
					}
					
					if (windowOpenWidget == null) {
						windowOpenWidget = findComponent(windowOpenIds);
					}
					if (windowOpenWidget == null)
						return false;

					return windowOpenWidget.valid() && windowOpenWidget.visible() && !checkFor();
				}

				@Override
				public boolean checkFor() {
					System.out.println(windowOpenWidget.text());
					return windowOpenWidget.text().equals(craftingMyItem.getName());
				}

				@Override
				public boolean doStuff() {
					return clickComponent.click();
				}
				@Override
				public String toString(){
					return "Click type";
				}
			},
			//clicks the leatherwork button
			new WidgetTask(ctx, new int[] { 1370, 56 }, new int[] {1370, 38}) {
				@Override
				public boolean activate() {					
					
					if (windowOpenWidget == null)
						return false;

					return windowOpenWidget.valid() && windowOpenWidget.visible() && windowOpenWidget.text().equals(craftingMyItem.getName());
				}

				@Override
				public boolean checkFor() {
					return !windowOpenWidget.visible();
				}

				@Override
				public boolean doStuff() {
					return clickComponent.click();
				}
				@Override
				public String toString(){
					return "click leatherwork";
				}
			} 
	};
	
    public <T extends Interactive & Locatable & Identifiable & Nameable> boolean interact(T target, String action) {
        //give it 3 tries before returning false
        for (int i = 0; i < 3; i++) {
            //attempt to interact with the target
            if (target.interact(action, target.name())) {
                //we misclicked
                if (ctx.game.crosshair() == Game.Crosshair.DEFAULT) {
                    //wait for 200-250ms (average human response time)
                    Condition.sleep(Random.nextInt(200, 250));
                    //skip the conditional sleep and try again
                    continue;
                }
                //we successfully interacted with the target; wait for required condition
                return Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                    	return !craftingTasks[1].activate();
                    }
                }, 250, 10);
            }
            //the interaction point was never found; turn the camera and try again
            else {
                ctx.camera.turnTo(target, Random.nextInt(-90, 90));
            }
        }
        return false;
    }
}
