package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.util.BinaryTask;

/**
 * Created by Tom on 11/15/2015.
 */
public class WalkToAltar extends BinaryTask<ClientContext> {
    AirRunner airRunner;
    public WalkToAltar(ClientContext context, AirRunner r){
        super(context,null,null);
        this.airRunner = r;
    }

    @Override
    public boolean execute() {

        return ctx.movement.step(EnterAltar.ALTAR_ENTRANCE_TILE);
    }
}
