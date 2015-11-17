package tbhizzle.oldschool.script.runecrafter;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Skills;
import tbhizzle.oldschool.script.runecrafter.tasks.InventoryCheck;
import tbhizzle.util.BinaryTask;

import java.awt.*;
/**
 * Created by Tom on 11/14/2015.
 */
@Script.Manifest(name = "Honest Air", description = "Crafts Air Runes", properties = "topic=1291090;client=4")
public class AirRunner extends PollingScript<ClientContext> implements PaintListener {

    Long startTime;
    BinaryTask<ClientContext> root;
    int runeCount;
    boolean crafted;
    int experience;
    @Override
    public void start(){
        root = new InventoryCheck(ctx, this);
        startTime = System.currentTimeMillis();
        crafted = false;
        runeCount = 0;
        experience = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
    }
    @Override
    public void poll() {
        root.activate();
    }

    @Override
    public void repaint(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(0,0,100,100);
        graphics.setColor(Color.BLACK);
        graphics.drawString("Runtime: " + getRunTime(), 10,30);
        graphics.drawString("Crafted: " + runeCount,10,50);
    }

    public String getRunTime(){
        long diff = System.currentTimeMillis() - startTime;
        int seconds = (int) (diff / 1000) % 60 ;
        int minutes = (int) ((diff / (1000*60)) % 60);
        int hours   = (int) ((diff / (1000*60*60)) % 24);
        String result = "";
        result = String.format("%02d:%02d", minutes,seconds);
        if(hours > 0)result = hours + ":" + result;
        return result;
    }

    public void addCrafted(int i){
        int currentXP = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
        System.out.println("Current XP: " + currentXP + "; Old XP: " + experience);
        if(currentXP > experience) {
            runeCount += i;
            experience = currentXP;
        }

    }
    public void crafted(boolean b){
        this.crafted = b;
    }
    public boolean crafted(){
        return crafted;
    }
}