package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.Tile;
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
    Tile[] path;
    @Override
    public boolean execute() {
        if(path == null){
            path = altar.getPathToAltar();
        }
        for(int i = 0; i < path.length; i++){
            if(ctx.players.local().tile().distanceTo(path[path.length-1-i]) < 5){
                System.out.print(path[path.length-1-i].x() + ":" + path[path.length-1-i].y() + ";");
                return ctx.movement.step(path[path.length-i-1]);
            }
        }
        ctx.movement.step(path[0]);
        return false;
        //return ctx.movement.step(altar.getAltarEntrance());
    }
}
