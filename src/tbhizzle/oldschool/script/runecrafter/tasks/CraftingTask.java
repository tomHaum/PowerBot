package tbhizzle.oldschool.script.runecrafter.tasks;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.Craft;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.EnterAltar;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.OutOfAltarTask;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class CraftingTask extends BinaryTask<ClientContext> {
    public CraftingTask(ClientContext ctx, AirRunner r){
        super(ctx, new Craft(ctx,r), new OutOfAltarTask(ctx,r));
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {

                System.out.print(this.ctx.movement.distance(ctx.players.local().tile(), Craft.ALTAR_TILE));
                System.out.println(" tiles from the crafting altar");
                return this.ctx.movement.distance(ctx.players.local().tile(), Craft.ALTAR_TILE) != -1 &&
                        ctx.movement.distance(EnterAltar.ALTAR_ENTRANCE_TILE) == -1;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
