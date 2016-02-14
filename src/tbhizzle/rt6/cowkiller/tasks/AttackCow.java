package tbhizzle.rt6.cowkiller.tasks;

import java.util.Comparator;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Identifiable;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Game;
import org.powerbot.script.rt6.Interactive;
import org.powerbot.script.rt6.Npc;

import tbhizzle.util.Task;

public class AttackCow extends Task<ClientContext>{
	public static final Tile cowTile = new Tile(2885, 3487,0);
	
	public AttackCow(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		//no cowhides and inv not full and close to the cows
		return 
				ctx.backpack.select().count() < 28
				&&
				ctx.groundItems.select().select(PickUpHides.hideFilter).count() < 3
				&&
				!(ctx.players.local().tile().distanceTo(cowTile) > 6);
	}

	@Override
	public void execute() {
		Npc cow;
		if(ctx.players.local().interacting().valid()){
			Condition.wait(new Callable<Boolean>() {
				int hp = ctx.players.local().interacting().healthPercent();
				@Override
				public Boolean call() throws Exception {
					return ctx.players.local().interacting().healthPercent() != hp;
				}
			}, 200, 10);//waits until the hp changed
		}
		if(ctx.npcs.select().select(new Filter<Npc>() {
			@Override
			public boolean accept(Npc npc) {
				return npc.interacting() == ctx.players.local();
			}
		}).isEmpty())
			ctx.npcs.select().name("Cow").select(new Filter<Npc>() {
				@Override
				public boolean accept(Npc npc) {
					return AttackCows.centerTile.distanceTo(npc) < 7 && !npc.inCombat();
				}
			});
		cow = ctx.npcs.sort(new Comparator<Npc>() {
			@Override
			public int compare(Npc o1, Npc o2) {
				return Double.compare(ctx.players.local().tile().distanceTo(o1), ctx.players.local().tile().distanceTo(o2));
		}
		}).limit(3).shuffle().peek();

		if(!cow.inViewport()){
			ctx.camera.turnTo(cow,10);
		}
		interact(cow, "Attack");
		
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
                    public Boolean call() throws Exception{
                    	return ctx.players.local().tile().distanceTo(ctx.players.local().interacting()) < 3;
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
