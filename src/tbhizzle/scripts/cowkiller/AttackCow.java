package tbhizzle.scripts.cowkiller;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;
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
					&& npc.name().equals("Cow");
		}	
	};

	public AttackCow(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		//no cowhides and inv not full
		return 
				ctx.backpack.select().count() < 28
				&&
				ctx.groundItems.select().name("Cowhide").count() < 3
				&&
				!(ctx.players.local().tile().distanceTo(cowTile) > 6);
	}

	@Override
	public void execute() {
		System.out.println("Attacking cows");
//		cow = ctx.npcs.select().name("Cow").nearest().limit(3).shuffle().poll();
//		cow.interact("Attack");
		
		if(cow == null || !ctx.npcs.select().select(cowFilter).contains(cow)){
			System.out.println("finding new cow");
			cow = ctx.npcs.select().select(cowFilter).nearest().limit(2).shuffle().poll();
		}
		
		System.out.println("Turning to cow");
		ctx.camera.turnTo(cow, 10);
		
		System.out.println("Attacking");
		cow.interact(false,"Attack", "Cow");
		
		Condition.wait(new Callable<Boolean>(){

			@Override
			public Boolean call() throws Exception {
				System.out.println("Waiting");
				return !ctx.npcs.select().select(cowFilter).contains(cow);
			}
		} ,200, 5);
		
	}

}
