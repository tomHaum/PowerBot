package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Task;

/**
 * Created by Tom on 11/14/2015.
 */
public class WalkToBank extends BinaryTask<ClientContext> {
    RuneCrafter airRunner;
    Altar altar;
    public WalkToBank(ClientContext clientContext, RuneCrafter r, Altar altar){
        super(clientContext,null,null);
        this.airRunner = r;
        this.altar = altar;
    }

    @Override
    public boolean execute() {
        return ctx.movement.step(altar.getBankTile());
    }
}
