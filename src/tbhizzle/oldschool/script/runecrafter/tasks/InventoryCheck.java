package tbhizzle.oldschool.script.runecrafter.tasks;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class InventoryCheck extends BinaryTask<ClientContext> {
    AirRunner airRunner;
    public InventoryCheck(ClientContext ctx, AirRunner r){
        super(ctx, new CraftingTask(ctx, r), new BankingTask(ctx,r));

        this.setActivator(new Callable<ClientContext>(ctx) {
        @Override
            public Boolean call() throws Exception {
                if(this.ctx.game.tab() != Game.Tab.INVENTORY){
                    this.ctx.game.tab(Game.Tab.INVENTORY);
                }
                return this.ctx.inventory.select().count() == 28;
            }
        });
        airRunner = r;
    }
    @Override
    public boolean execute() {
        return false;
    }
}
