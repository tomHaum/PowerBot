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
    Tile[] path;
    Tile[] pathArray;
    @Override
    public boolean execute() {
        /*if(path == null){
            if(altar == Altar.FIRE) {
                pathArray = new Tile[altar.getPathToAltar().length - 6];
                for (int i = 0; i < altar.getPathToAltar().length - 6; i++) {
                    pathArray[i] = altar.getPathToAltar()[i];
                    System.out.println("Tile: " + altar.getPathToAltar()[i]);
                }
                path = ctx.movement.newTilePath(pathArray).reverse();
            }else{
                path = ctx.movement.newTilePath(altar.getPathToAltar()).reverse();
            }
        }

        if(!path.traverse())
            ctx.movement.step(path.next());
        //return ctx.movement.step(altar.getBankTile());
        return true;
        */
        if(path == null){
            path = altar.getPathToAltar();
        }
        System.out.println();
        for(int i = 0; i < path.length; i++){
            System.out.print(path[i].x()+":"+path[i].y()+";");
            if(ctx.players.local().tile().distanceTo(path[i]) < 10){
                return ctx.movement.step(path[i]);
            }
        }
        ctx.movement.step(path[path.length]);
        return false;
    }
}
