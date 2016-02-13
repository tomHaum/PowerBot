package tbhizzle.rt6.cowkiller.tasks;

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
	
	private Npc cow;
	
	private final Filter<Npc> cowFilter = new Filter<Npc>(){
		@Override
		public boolean accept(Npc npc) {
			return AttackCow.cowTile.distanceTo(npc) < 7//within 7 tiles of the cow tile
					&& npc.animation() != 23566//is not dying
					&& !npc.inCombat()
					&& npc.name().equals("Cow")
					&& ctx.backpack.select().count() < 28;
		}	
	};

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
		System.out.println("Attacking cows");
		//if there is no cow selected
		if(cow == null || !ctx.npcs.select().select(cowFilter).contains(cow)){
			System.out.println("finding new cow");
			cow = ctx.npcs.select().select(cowFilter).nearest().limit(2).shuffle().poll();
		}
		
		ctx.camera.turnTo(cow, 10);
		
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
                    	return !ctx.npcs.select().select(cowFilter).contains(cow);
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
