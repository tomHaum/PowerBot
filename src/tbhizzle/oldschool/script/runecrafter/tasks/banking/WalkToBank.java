package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Path;
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
    Path path;
    Tile[] pathArray;
    @Override
    public boolean execute() {
        if(path == null){
            pathArray = new Tile[altar.getPathToAltar().length - 2];
           for(int i = 0; i < altar.getPathToAltar().length - 2; i++){
               pathArray[i] = altar.getPathToAltar()[i];
           }
            path = ctx.movement.newTilePath(pathArray).reverse();
        }
        return path.traverse();
        //return ctx.movement.step(altar.getBankTile());
    }
}
