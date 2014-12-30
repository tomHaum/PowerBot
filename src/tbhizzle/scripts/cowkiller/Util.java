package tbhizzle.scripts.cowkiller;


import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Identifiable;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Game;
import org.powerbot.script.rt6.Interactive;
/**
 * 
 * @author Coma
 */
public class Util {
    public static <T extends Interactive & Locatable & Identifiable & Nameable> boolean interact(ClientContext ctx, T target, String action) {
        //give it 3 tries before returning false
        for (int i = 0; i < 3; i++) {
            //attempt to interact with the target
            if (target.interact(action, target.name())) {
                //we misclicked
                if (ctx.game.crosshair() == Game.Crosshair.DEFAULT) {
                    //wait for 200-250ms (average human response time)
                    Condition.sleep(Random.nextInt(200, 250));
                    //skip the conditional sleep and try again
                    continue;
                }
                //we successfully interacted with the target; wait for required condition
                return Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return false;
                    }
                }, 250, 10);
            }
            //the interaction point was never found; turn the camera and try again
            else {
                ctx.camera.turnTo(target, Random.nextInt(-90, 90));
            }
        }
        return false;
    }
}
