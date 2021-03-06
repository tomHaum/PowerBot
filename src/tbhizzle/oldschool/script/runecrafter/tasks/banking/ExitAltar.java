package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;

import java.util.concurrent.Callable;

/**
 * Created by oTm on 11/15/2015.
 */
public class ExitAltar extends BinaryTask<ClientContext> {
    //public static final int PORTAL_ID = 14841;
    RuneCrafter airRunner;
    Altar altar;
    public ExitAltar(ClientContext clientContext, RuneCrafter r, Altar altar){
        super(clientContext,null,null);
        this.airRunner = r;
        this.altar = altar;
    }


    GameObject exitPortal;
    @Override
    public boolean execute() {
        if(exitPortal == null || !exitPortal.valid()){
            exitPortal = ctx.objects.select().id(altar.getPortalId()).nearest().peek();
        }
        if(airRunner.crafted()){
            airRunner.addCrafted(ctx.inventory.select().peek().stackSize());
            airRunner.crafted(false);
        }
        if(!exitPortal.inViewport())
            ctx.camera.turnTo(exitPortal);
        if(!exitPortal.inViewport())
            ctx.movement.step(exitPortal);
        int count = 3;
        exitPortal.click();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().inMotion();
            }
        },200, 10);

        return true;
    }
}
