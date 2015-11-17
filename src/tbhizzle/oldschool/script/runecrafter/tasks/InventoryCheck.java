package tbhizzle.oldschool.script.runecrafter.tasks;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class InventoryCheck extends BinaryTask<ClientContext> {
    RuneCrafter airRunner;
    public InventoryCheck(ClientContext ctx, RuneCrafter r, Altar altar){
        super(ctx, new CraftingTask(ctx, r, altar), new BankingTask(ctx,r,altar));

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
