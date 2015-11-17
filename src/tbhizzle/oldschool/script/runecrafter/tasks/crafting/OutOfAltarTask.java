package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;

/**
 * Created by Tom on 11/15/2015.
 */
public class OutOfAltarTask extends BinaryTask<ClientContext> {
    Altar altar;
    public OutOfAltarTask(ClientContext ctx, RuneCrafter r, Altar altar){
        super(ctx, new EnterAltar(ctx,r,altar), new WalkToAltar(ctx,r,altar));
        this.altar = altar;
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {
                int distance = this.ctx.movement.distance(OutOfAltarTask.this.altar.getAltarEntrance());
                return distance < 5 && distance != -1 ;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
