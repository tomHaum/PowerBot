package tbhizzle.scripts.cowkiller.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Npc;

import tbhizzle.scripts.cowkiller.data.Item;
import tbhizzle.util.Task;
import tbhizzle.util.WidgetTask;

public class SellLeatherItem extends Task<ClientContext> {
	private Npc jackOval;
	tbhizzle.scripts.cowkiller.data.Item finalItem;

	public SellLeatherItem(ClientContext ctx, tbhizzle.scripts.cowkiller.data.Item finalItem) {
		super(ctx);
		this.finalItem = finalItem;
	}

	@Override
	public boolean activate() {
		//need to check to make sure crafting is not going on
		
		return ctx.backpack.select().count() == 28
				&& ctx.backpack.id(finalItem.getId()).count() > 0
				&& !(CraftLeather.active && (ctx.backpack.select().id(TanHides.tanningItem.getId()).count() > 0)) ;//checks for if the scripts is crafting, and if it is checks to make sure there is no leather
	}

	@Override
	public void execute() {
		System.out.println("Sellin");
		if (shopTasks[0].activate() || shopTasks[1].activate()) {//true if window open
			if (shopTasks[0].activate()) {
				System.out.println("One");
				shopTasks[0].execute();
			} else {
				System.out.println("Two");
				shopTasks[1].execute();
			}
		} else {
			System.out.println("Trading Jack");
			if (null == jackOval || !jackOval.valid()){
				System.out.println("new jack");
				jackOval = ctx.npcs.select().name("Jack Oval").poll();
			}
				
			ctx.camera.turnTo(jackOval, 10);
			jackOval.interact("Trade");
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {

					return true;// true if window open
				}

			});
		}
	}

	WidgetTask[] shopTasks = {
			//hit sell items
			new WidgetTask(ctx, new int[] { 1265, 33 }, new int[] { 1265, 29 }) {

				@Override
				public boolean activate() {
					if (windowOpenWidget == null) {
						windowOpenWidget = findComponent(windowOpenIds);
					}
					if (windowOpenWidget == null)
						return false;

					return windowOpenWidget.valid() && !windowOpenWidget.visible();
				}

				@Override
				public boolean checkFor() {
					return windowOpenWidget.visible() && windowOpenWidget.valid();
				}

				@Override
				public boolean doStuff() {
					return clickComponent.click();
				}
			},
			//sell 50 items
			new WidgetTask(ctx, new int[] { 1265, 33 }, new int[] { 1265, 20 }) {
				Component realClickComponent;
				@Override
				public boolean activate() {
					if (windowOpenWidget == null) {
						windowOpenWidget = findComponent(windowOpenIds);
					}
					if (windowOpenWidget == null)
						return false;

					return windowOpenWidget.valid()
							&& windowOpenWidget.visible();
				}

				@Override
				public boolean checkFor() {
					// TODO Auto-generated method stub
					return ctx.backpack.select().count() < 28;
				}

				@Override
				public boolean doStuff() {
					if(realClickComponent == null || !realClickComponent.valid() || !realClickComponent.visible()){
						int index = ctx.backpack.indexOf(finalItem.getId());
						if(index == -1 )return false;
						realClickComponent = clickComponent.component(index);
					}
					realClickComponent.interact("Sell 50");
					return false;
				}

			} };
}
