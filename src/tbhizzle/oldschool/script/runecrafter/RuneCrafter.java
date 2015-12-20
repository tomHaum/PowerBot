package tbhizzle.oldschool.script.runecrafter;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Skills;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.oldschool.script.runecrafter.tasks.InventoryCheck;
import tbhizzle.util.BinaryTask;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Tom on 11/14/2015.
 */
@Script.Manifest(name = "Honest Runecrafter", description = "Only Crafts air, earth, fire, and body runes for now", properties = "topic=1291090;client=4")
public class RuneCrafter extends PollingScript<ClientContext> implements PaintListener {

    Long startTime;
    BinaryTask<ClientContext> root;
    int runeCount;
    boolean crafted;
    int experience;
    RuneCrafterGui frame;
    boolean usingPureEss = false;
    long endTime = Long.MAX_VALUE;
    @Override
    public void start(){
        //root = new InventoryCheck(ctx, this, Altar.AIR);
        startTime = System.currentTimeMillis();
        crafted = false;
        runeCount = 0;
        experience = ctx.skills.experience(Constants.SKILLS_RUNECRAFTING);
        final File storage = this.getStorageDirectory();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    File preferences = new File(storage,"HonestSmelter.pref");
                    log(preferences.getAbsolutePath());
                    frame = new RuneCrafterGui(RuneCrafter.this,preferences);
                    frame.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void poll() {
        if(root != null)
            if(System.currentTimeMillis() < endTime) {
                log("Start: " + startTime + "\nEnd:   "+ endTime);
                root.activate();
            }else{
                log("Script stopped running after " + frame.getTime()  + " minutes");
                ctx.controller.stop();
            }
        else{
            if(frame != null && frame.getAltar() != null){
                root = new InventoryCheck(ctx,this,frame.getAltar());
                usingPureEss = frame.getPureEss();
                if(frame.isUsingTimer()){
                    log("using a break timer for " + frame.getTime() + " minutes");
                    startTime = System.currentTimeMillis();
                    //endTime = startTime + ((long)frame.getTime())*60000L;
                    endTime = startTime + 60000;
                    log("Start: " + startTime + "\nEnd:   " + endTime);
                }
                //log("Crafter is using pure ess: " + usingPureEss);
            }
        }
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
    public void log(String s){
        log.info(s);
        System.out.println(s);
    }
    public boolean isUsingPureEss(){
        return usingPureEss;
    }
}