package tbhizzle.scripts.cowkiller;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

import tbhizzle.util.Task;

public class Bank extends Task<ClientContext>{
	public static final Tile bankTile = new Tile(2876,3417,0);
	public int inventories = 0;
	public Bank(ClientContext ctx) {
		super(ctx);
	
	}

	@Override
	public boolean activate() {
		return 
				ctx.backpack.select().count() == 28
				||
				(ctx.players.local().tile().distanceTo(AttackCow.cowTile) > 6);
	}

	@Override
	public void execute() {
		System.out.println("Banking");
//		for(Item item:ctx.backpack.items()){
//			item.interact(false, "Drop");
//		} 
		if(ctx.backpack.select().count() == 28){
			System.out.println("full");
			if(!(ctx.players.local().tile().distanceTo(bankTile) > 4)){//not close to bank
				System.out.println("near Bank");
				ctx.bank.open();
				if(ctx.bank.opened()){
					ctx.bank.depositInventory();
					inventories++;
					ctx.bank.close();
				}
			}else{
				System.out.println("not near bank");
				
				ctx.movement.step(bankTile);
				/*
				 if(path == null || path.
				 */
			}
		}else{
			System.out.println("empty");
			ctx.movement.step(AttackCow.cowTile);
		}
	}

}
