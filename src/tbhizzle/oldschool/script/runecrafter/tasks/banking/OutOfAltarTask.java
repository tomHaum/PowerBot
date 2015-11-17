package tbhizzle.oldschool.script.runecrafter.tasks.banking;

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
    final Altar altar;
    public OutOfAltarTask(ClientContext ctx, RuneCrafter r, final Altar altar){
        super(ctx, new Bank(ctx, r, altar), new WalkToBank(ctx, r, altar));
        this.altar = altar;
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {
                int distance = this.ctx.movement.distance(OutOfAltarTask.this.altar.getBankTile());
                return distance != -1  && distance  < 4;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
