package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;

/**
 * Created by Tom on 11/15/2015.
 */
public class OutOfAltarTask extends BinaryTask<ClientContext> {

    public OutOfAltarTask(ClientContext ctx,AirRunner r){
        super(ctx, new EnterAltar(ctx,r), new WalkToAltar(ctx,r));
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {
                int distance = this.ctx.movement.distance(EnterAltar.ALTAR_ENTRANCE_TILE);
                return distance < 5 && distance != -1 ;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
