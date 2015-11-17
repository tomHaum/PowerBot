package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.rt4.ClientAccessor;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class OutOfAltarTask extends BinaryTask<ClientContext> {

    public OutOfAltarTask(ClientContext ctx, AirRunner r){
        super(ctx, new Bank(ctx, r), new WalkToBank(ctx, r));
        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {
                int distance = this.ctx.movement.distance(Bank.BANK_TILE);
                return distance != -1  && distance  < 4;
            }
        });
    }
    @Override
    public boolean execute() {
        return false;
    }
}
