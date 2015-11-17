package tbhizzle.oldschool.script.runecrafter.tasks.crafting;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Task;

import java.util.concurrent.Callable;

/**
 * Created by Tom on 11/15/2015.
 */
public class Craft extends BinaryTask<ClientContext> {
    //public static final Tile ALTAR_TILE = new Tile(2843, 4832, 0);
    //private static final int AIR_ALTAR_ID = 14897;
    private int[] bounds = new int[]{-167, 187, -90, 72, -152, 152};
    RuneCrafter airRunner;
    Altar altar;

    public Craft(ClientContext context, RuneCrafter r, Altar altar) {
        super(context, null, null);
        this.airRunner = r;
        this.altar = altar;
    }


    private GameObject airAltar;
    boolean crafted = true;

    @Override
    public boolean execute() {
        if (airAltar == null || !airAltar.valid()) {
            airAltar = ctx.objects.select().id(altar.getAltarId()).nearest().peek();
            airAltar.doSetBounds(bounds);
        }
        airRunner.crafted(true);
        if (!airAltar.inViewport())
            ctx.camera.turnTo(airAltar);
        if (!airAltar.inViewport())
            ctx.movement.step(airAltar);
        if (!airAltar.inViewport())
            ctx.camera.turnTo(airAltar);
        int count = 3;
        while (!airAltar.click()) {
            count--;
            if (count == 0)
                return false;
        }
        if (Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().count() < 28;
            }
        }, 200, 10)) {
            airRunner.addCrafted(ctx.inventory.itemAt(0).stackSize());
            System.out.println("successfully crafted runes");
        } else
            System.out.println("waiting didnt work");
        return true;
    }

}
