package tbhizzle.scripts.cowkiller.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Item;

import tbhizzle.scripts.cowkiller.CowKiller;
import tbhizzle.util.Task;

public class CraftLeather extends Task<ClientContext> {
	//need to add leather vs hardleather
	private Item needle,thread;
	//1061 boots, 1059 gloves
	private Component leatherItemComponent;
	public static tbhizzle.scripts.cowkiller.data.Item craftingItem;
	public static boolean active = false;
	public CraftLeather(ClientContext ctx, tbhizzle.scripts.cowkiller.data.Item craftingItem) {
		super(ctx);
		this.craftingItem = craftingItem;
		active = true;
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28
			    && ctx.backpack.name(TanHides.tanningItem.getName()).count() > 0;
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
				if(c.itemId() == craftingItem.getId()){
				leatherItemComponent = c;
					break;
				}	
			}
		}
		
			//craft leather item;
		if(null == leatherItemComponent || (!leatherItemComponent.visible() && !ctx.widgets.component(1251, 13).valid())){//craft window not open or crafting window not open)
			needle.interact("Use");
			ctx.backpack.select().id(TanHides.tanningItem.getId()).poll().interact("Use");
		}else{
			if(ctx.widgets.component(1251, 13).valid()){//crafting window open
				Condition.wait(new Callable<Boolean>(){
					
					@Override
					public Boolean call() throws Exception {
						return ctx.widgets.component(1251, 0).visible();
					}
				} ,500, 20);

			}else{
				if(!ctx.widgets.component(1370, 37).tooltip().contains(craftingItem.getName())){//if the crafting option isnt right
					leatherItemComponent.click();
					
				//wait till crafting window opens 
				}
				ctx.widgets.component(1370, 37).click(true);//hit the craft button
			}
		}

	}
}
