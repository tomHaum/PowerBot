package tbhizzle.rt6.cowkiller.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Identifiable;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Game;
import org.powerbot.script.rt6.GroundItem;
import org.powerbot.script.rt6.Interactive;

import tbhizzle.rt6.cowkiller.data.MyItem;
import tbhizzle.util.Task;

public class PickUpHides extends Task<ClientContext>{
	public static final MyItem COWHIDE = new MyItem(1739, "Cowhide");
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
			interact(hide, "Take");
			
	}
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
                    	return !ctx.groundItems.select().select(hideFilter).contains(hide);
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
