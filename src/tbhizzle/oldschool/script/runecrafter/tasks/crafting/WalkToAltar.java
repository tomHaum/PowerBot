package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Path;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;

/**
 * Created by Tom on 11/15/2015.
 */
public class WalkToAltar extends BinaryTask<ClientContext> {
    RuneCrafter airRunner;
    Altar altar;
    public WalkToAltar(ClientContext context, RuneCrafter r, Altar altar){
        super(context,null,null);
        this.airRunner = r;
        this.altar = altar;
    }
    Path path;
    @Override
    public boolean execute() {
        if(path == null){
            path = ctx.movement.newTilePath(altar.getPathToAltar());
        }
        return path.traverse();
        //return ctx.movement.step(altar.getAltarEntrance());
    }
}
