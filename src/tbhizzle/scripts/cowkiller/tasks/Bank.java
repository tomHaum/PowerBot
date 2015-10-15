package tbhizzle.scripts.cowkiller.tasks;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

import tbhizzle.scripts.cowkiller.CowKiller;
import tbhizzle.util.Task;

public class Bank extends Task<ClientContext>{
	public static final Tile bankTile = new Tile(2891,3536,0);
	public int inventories = 0;
	private tbhizzle.scripts.cowkiller.data.Item finalItem;
	public Bank(ClientContext ctx, tbhizzle.scripts.cowkiller.data.Item finalItem) {
		super(ctx);
	
	}

	@Override
	public boolean activate() {
		//add checks for if tanning check for cowhide if crafting check for leather
		return ctx.backpack.select().count() == 28// inv full
				&& ctx.backpack.id(finalItem.getId()).count() > 0
				&& !(CraftLeather.active && !(ctx.backpack.select().id(TanHides.tanningItem.getId()).count() > 0));//doesn't contain leather if its crafting

	}

	@Override
	public void execute() {
		System.out.println("Banking");
//		for(Item item:ctx.backpack.items()){
//			item.interact(false, "Drop");
//		} 

		System.out.println("full");
		if(!(ctx.players.local().tile().distanceTo(bankTile) > 4)){//not close to bank
			System.out.println("near Bank");
			ctx.bank.open();
			if(ctx.bank.opened()){
				ctx.bank.deposit(finalItem.getId(), org.powerbot.script.rt6.Bank.Amount.ALL);
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

	}

}
