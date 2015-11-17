package tbhizzle.oldschool.script.runecrafter.tasks;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.Craft;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.EnterAltar;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.OutOfAltarTask;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class CraftingTask extends BinaryTask<ClientContext> {
    Altar altar;
    public CraftingTask(ClientContext ctx, RuneCrafter r, Altar altar){
        super(ctx, new Craft(ctx,r,altar), new OutOfAltarTask(ctx,r,altar));
        this.altar = altar;
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {

                System.out.print(this.ctx.movement.distance(ctx.players.local().tile(), CraftingTask.this.altar.getAltarCrafting()));
                System.out.println(" tiles from the crafting altar");
                return this.ctx.movement.distance(ctx.players.local().tile(), CraftingTask.this.altar.getAltarCrafting()) != -1 &&
                        ctx.movement.distance(CraftingTask.this.altar.getAltarEntrance()) == -1;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
