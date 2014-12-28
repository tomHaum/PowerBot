package tbhizzle.scripts.cowkiller;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

import tbhizzle.util.Task;
import tbhizzle.util.WidgetTask;

public class TanHides extends Task<ClientContext>{
	public static final Tile tannerTile = new Tile(2889,3501,0);
	public Npc tanner;
	public TanHides(ClientContext ctx) {
		super(ctx);
	}
	

	@Override
	public boolean activate() {
		return !CowKiller.banking
			   &&
			   ctx.backpack.select().count() == 28 
			   && 
			   ctx.backpack.name("Cowhide").count() > 0;//there is hides to tan
	}
	
	@Override
	public void execute() {
		//walk to him and tan.
		System.out.println("Tanning");
		if(ctx.players.local().tile().distanceTo(tannerTile) > 3){
			ctx.movement.step(tannerTile);
		}else{
			if(tanner == null || !tanner.valid()){
				System.out.println("New Tanner");
				tanner = ctx.npcs.select().name("Jack Oval").poll();
				
			}
			if(!(shopTasks[0].activate() || shopTasks[1].activate())){//if neither task is activated
				tanner.interact(false, "Tan hide");
				Condition.wait(new Callable<Boolean>(){

					@Override
					public Boolean call() throws Exception {
						System.out.println("Waiting");
						return !(ctx.backpack.select().name("Cowhide").count() > 0);
					}
				} ,200, 5);
			}else{
				if(shopTasks[0].activate()){
					System.out.println("One active");
					shopTasks[0].execute();
				}
				else {
					System.out.println("Two active");
					shopTasks[1].execute();
				}
			} 
			
			
			if(ctx.chat.chatting()){
				ctx.chat.clickContinue(true);
			}
		}
	}
	
	WidgetTask[] shopTasks = { new WidgetTask(ctx, new int[] { 1370, 37 },
			new int[] {1371,44,2}) {
//		COMPONENT 
		@Override
		public boolean activate() {
			if (windowOpenWidget == null) {
				windowOpenWidget = findComponent(windowOpenIds);
			}
			if (windowOpenWidget == null)
				return false;
			
			return (!checkFor() && windowOpenWidget.visible());
		}
		
		@Override
		public boolean checkFor() {
			String tooltip = windowOpenWidget.tooltip().toLowerCase();
			return tooltip.contains("leather") && !tooltip.contains("hard leather");
		}
		@Override
		public boolean doStuff() {
			return clickComponent.click();
		}
	}, new WidgetTask(ctx, new int[]{1370,37}, new int[]{1370,37}){

		@Override
		public boolean activate() {
			if (windowOpenWidget == null) {
				windowOpenWidget = findComponent(windowOpenIds);
			}
			if (windowOpenWidget == null)
				return false;
			return (windowOpenWidget.tooltip().contains("Leather") && windowOpenWidget.visible());
		}

		@Override
		public boolean checkFor() {
			// TODO Auto-generated method stub
			return windowOpenWidget.tooltip().contains("Leather");
		}
		@Override
		public boolean doStuff() {
			return clickComponent.click();
		}
		
	}
	};

}
