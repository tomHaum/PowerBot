package tbhizzle.rt6.cowkiller.tasks;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import tbhizzle.rt6.cowkiller.data.MyItem;
import tbhizzle.rt6.cowkiller.gui.Paint;
import tbhizzle.util.Task;

import java.util.concurrent.Callable;


public class Bank extends Task<ClientContext>{
	public static final Tile bankTile = new Tile(2888,3537,0);
	public int inventories = 0;
	private final MyItem finalMyItem;
	private final Paint paint;
	public Bank(ClientContext ctx, MyItem finalMyItem, Paint p) {
		super(ctx);
		this.finalMyItem = finalMyItem;
		this.paint = p;
	}

	@Override
	public boolean activate() {
		//add checks for if tanning check for cowhide if crafting check for leather
		return ctx.backpack.select().count() == 28
				&& ctx.backpack.id(finalMyItem.getId()).count() > 0
				&& !(CraftLeather.active && (ctx.backpack.select().id(TanHides.tanningMyItem.getId()).count() > 0));//doesn't contain leather if its crafting

	}

	@Override
	public void execute() {
		System.out.println("Banking");

		System.out.println("full");
		if(!(ctx.players.local().tile().distanceTo(bankTile) > 4)){//not close to bank
			System.out.println("near Bank");
			ctx.bank.open();
			if(ctx.bank.opened()){
				int items = ctx.backpack.select().id(finalMyItem.getId()).count();
				ctx.bank.deposit(finalMyItem.getId(), org.powerbot.script.rt6.Bank.Amount.ALL);

				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						System.out.println("Waiting to bank");
						return ctx.backpack.select().count() == 28;
					}
				}, 250, 5);
				paint.banked += items - ctx.backpack.select().id(finalMyItem.getId()).count();
				ctx.bank.close();
			}
		}else{
			System.out.println("not near bank");
			
			ctx.movement.step(bankTile);

		}

	}

}
