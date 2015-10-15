package tbhizzle.scripts.cowkiller.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

import tbhizzle.scripts.cowkiller.data.Item;
import tbhizzle.util.Task;

public class PickUpHides extends Task<ClientContext>{
	public static final Item COWHIDE = new Item(1739, "Cowhide");
	private GroundItem hide;
	public static final Filter<GroundItem> hideFilter = new Filter<GroundItem>(){
		@Override
		public boolean accept(GroundItem g) {
			return AttackCow.cowTile.distanceTo(g) < 7
					&& g.name().equals(COWHIDE.getName());
		}
	};
	public PickUpHides(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		
		return 
				ctx.backpack.select().count() < 28
				&&
				!(ctx.groundItems.select().select(hideFilter).count() < 3)
				&&
				!(ctx.players.local().tile().distanceTo(AttackCow.cowTile) > 6);

	}

	@Override
	public void execute() {
			System.out.println("Picking up");
			System.out.println(ctx.backpack.select().count());
			
			if(hide == null || !ctx.groundItems.select().select(hideFilter).contains(hide)){
				System.out.println("finding new hide");
				hide = ctx.groundItems.select().select(hideFilter).nearest().limit(2).shuffle().poll();
			}
			if(!hide.inViewport()){
				System.out.println("Turning to hide");
				ctx.camera.turnTo(hide, 10);
			}
			System.out.println("picking up");
			hide.interact(false,"Take", COWHIDE.getName());
			
			Condition.wait(new Callable<Boolean>(){

				@Override
				public Boolean call() throws Exception {
					System.out.println("waiting");
					return !ctx.groundItems.select().select(hideFilter).contains(hide);
				}
			} ,200, 5);
	}
}
