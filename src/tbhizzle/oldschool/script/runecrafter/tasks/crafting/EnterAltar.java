package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Task;

/**
 * Created by Tom on 11/15/2015.
 */
public class EnterAltar extends BinaryTask<ClientContext> {
    //public static final Tile ALTAR_ENTRANCE_TILE = new Tile(2986, 3296, 0);
    //public static final int AIR_ALTAR_ENTRANCE_ID = 14399;
    RuneCrafter airRunner;
    Altar altar;
    public EnterAltar(ClientContext clientContext, RuneCrafter r, Altar altar){
        super(clientContext,null,null);
        this.airRunner = r;
        this.altar = altar;

    }

    GameObject airAltar;
    @Override
    public boolean execute() {

        if(airAltar == null || !airAltar.valid()){
            airAltar = ctx.objects.select().action("Enter"). poll();
            //airAltar = ctx.objects.select().id(altar.getAltarEntranceId()).nearest().peek();
        }
        if(!airAltar.inViewport())
            ctx.camera.turnTo(airAltar);
        int count = 3;
        while(!airAltar.click()){
            count--;
            if(count == 0)
                return false;
        }
        return true;



    }
}
