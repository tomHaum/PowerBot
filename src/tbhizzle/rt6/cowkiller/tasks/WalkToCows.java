package tbhizzle.rt6.cowkiller.tasks;

import org.powerbot.script.rt6.ClientContext;

import tbhizzle.util.Task;

public class WalkToCows extends Task<ClientContext>{

	public WalkToCows(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		
		return ctx.players.local().tile().distanceTo(AttackCow.cowTile) > 6
				&& ctx.backpack.select().count() < 28;
	}

	@Override
	public void execute() {
		System.out.println("Walking to cows");
		ctx.movement.step(AttackCow.cowTile);
	}
	
}
