package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.Craft;
import tbhizzle.util.BinaryTask;

/**
 * Created by Tom on 11/15/2015.
 */
public class ExitAltar extends BinaryTask<ClientContext> {
    public static final int PORTAL_ID = 14841;
    AirRunner airRunner;
    public ExitAltar(ClientContext clientContext, AirRunner r){
        super(clientContext,null,null);
        this.airRunner = r;
    }


    GameObject exitPortal;
    @Override
    public boolean execute() {
        if(exitPortal == null || !exitPortal.valid()){
            exitPortal = ctx.objects.select().id(PORTAL_ID).nearest().peek();
        }
        if(airRunner.crafted()){
            airRunner.addCrafted(ctx.inventory.select().peek().stackSize());
            airRunner.crafted(false);
        }
        if(!exitPortal.inViewport())
            ctx.camera.turnTo(exitPortal);
        int count = 3;
        while(!exitPortal.click()){
            count--;
            if(count == 0)
                return false;
        }

        return true;
    }
}
