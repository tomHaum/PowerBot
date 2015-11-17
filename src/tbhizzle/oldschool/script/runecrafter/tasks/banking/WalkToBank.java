package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Task;

/**
 * Created by Tom on 11/14/2015.
 */
public class WalkToBank extends BinaryTask<ClientContext> {
    AirRunner airRunner;
    public WalkToBank(ClientContext clientContext, AirRunner r){
        super(clientContext,null,null);
        this.airRunner = r;
    }

    @Override
    public boolean execute() {
        return ctx.movement.step(Bank.BANK_TILE);
    }
}
