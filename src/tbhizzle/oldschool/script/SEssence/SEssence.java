package tbhizzle.oldschool.script.SEssence;

import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.ClientContext;

import java.awt.*;

/**
 * Created by Tom on 1/30/2016.
 */
@Script.Manifest(name = "Honest Essence Miner", description = "Varrock East Bank", properties = "topic=1300244;client=4")
public class SEssence extends PollingScript<ClientContext> implements PaintListener {
    public static final Tile[] path = {new Tile(3254, 3420, 0), new Tile(3254, 3423, 0), new Tile(3254, 3426, 0), new Tile(3257, 3428, 0), new Tile(3260, 3427, 0), new Tile(3260, 3424, 0), new Tile(3260, 3421, 0), new Tile(3260, 3418, 0), new Tile(3260, 3415, 0), new Tile(3260, 3412, 0), new Tile(3260, 3409, 0), new Tile(3259, 3406, 0), new Tile(3259, 3403, 0), new Tile(3257, 3400, 0), new Tile(3254, 3398, 0), new Tile(3253, 3401, 0)};

    private int Aurbury = 637;
    private int portal = 5895;
    private String status = " nothing";
    @Override
    public void repaint(Graphics graphics) {
        graphics.fill3DRect(0,0,100,100,false);
        graphics.setColor(Color.blue);
        graphics.drawString(status, 10,10);

    }

    @Override
    public void poll() {
        if(ctx.players.local().tile().floor() == 1){
            ctx.objects.select().id(11793).nearest().peek().click();
            return;
        }
        if(ctx.players.local().tile().x() < 4000){//in the over world
            if(ctx.inventory.select().count() < 28){//walk to aurbury if inventory is not  full
                //walk to aubury
                status = "teleporting";
                Npc aubury = ctx.npcs.select().id(Aurbury).peek();
                int dist = ctx.movement.distance(aubury);
                if( dist != -1 && dist < 5){

                    ctx.camera.turnTo(aubury);
                    aubury.interact(true, "Teleport");
                }else{
                    status = "waling to aubury";
                    for(int i = 0; i < path.length; i++){
                        if(ctx.players.local().tile().distanceTo(path[path.length - i - 1]) < 10){
                            ctx.movement.step(path[path.length - i - 1]);
                            break;
                        }
                    }
                }
            }else{
                if(ctx.movement.distance(path[0]) > 3){
                    status = "walking to bank";
                    for(int i = 0; i < path.length; i++){
                        if(ctx.players.local().tile().distanceTo(path[i]) < 10){
                            ctx.movement.step(path[i]);
                            break;
                        }
                    }
                }else{
                    //bank
                    status = "banking";
                    GameObject bankBooth = ctx.objects.select().name("Bank booth").nearest().peek();
                    if(!ctx.bank.opened()){
                        bankBooth.interact(true, "Bank");
                        if(ctx.game.crosshair().compareTo(Game.Crosshair.ACTION) == 0)
                            Condition.sleep(400);
                        else
                            return;
                    }
                    if(ctx.bank.opened()) {
                        ctx.bank.depositInventory();
                    }

                }
            }
        }else {//in the mine dimension
            if (ctx.inventory.select().count() < 28) {
                status = "looking for rocks";
                GameObject runeEssence = ctx.objects.select().action("Mine").nearest().peek();
                if (runeEssence.valid()) {
                    status = status + " runeEssence valid" + runeEssence.name();
                    if(ctx.movement.distance(runeEssence) > 3){
                        ctx.movement.step(runeEssence);
                        return;
                    }
                    if(!runeEssence.inViewport())
                        ctx.camera.turnTo(runeEssence);
                    runeEssence.click(true);
                    status = "clicking rocks";
                    if (ctx.game.crosshair().compareTo(Game.Crosshair.ACTION) == 0) {
                        int count = 10;
                        int miningXP = ctx.skills.experience(Constants.SKILLS_MINING);
                        try {
                            while (count-- > 0) {
                                Thread.sleep(1000);
                                if (miningXP < ctx.skills.experience(Constants.SKILLS_MINING))
                                    break;
                            }
                            if (miningXP >= ctx.skills.experience(Constants.SKILLS_MINING)) {
                                return;
                            }
                            status = "mining...";
                            while (true) {
                                miningXP = ctx.skills.experience(Constants.SKILLS_MINING);

                                if (ctx.inventory.select().count() == 28)
                                    break;
                                count = 20;

                                while (count-- > 0) {
                                    Thread.sleep(400);
                                    if (miningXP < ctx.skills.experience(Constants.SKILLS_MINING))
                                        break;
                                    if(ctx.inventory.select().count() == 28)
                                        break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }else{
                        ctx.camera.pitch(ctx.camera.pitch() - 60);
                        if(ctx.camera.pitch() == 0){
                            ctx.movement.step(runeEssence);
                        }
                    }
                }
            }else{
                status = "leaving mine";
                GameObject portal = ctx.objects.select().id(7479, 7478, 7477, 7476, 7475,7474, 7473, 7472).nearest().peek();

                if(ctx.movement.distance(portal) < 3){


                        portal.click(false);
                        ctx.menu.click(new Filter<MenuCommand>() {
                            @Override
                            public boolean accept(MenuCommand menuCommand) {
                                System.out.println( menuCommand.action + ":" + menuCommand.option);
                                return menuCommand.action.compareTo("Use") == 0 || menuCommand.action.compareTo("Exit") == 0;
                            }
                        });
                        if(ctx.game.crosshair().compareTo(Game.Crosshair.ACTION) == 0){
                            status = "successfully clicked portal";
                            int count = 10;
                            while(count-- > 0 && ctx.players.local().inMotion()){
                                try{
                                    Thread.sleep(200);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }else{
                            status += " turing to portal";
                            ctx.camera.turnTo(portal);
                        }
                }else{
                    ctx.movement.step(portal);
                }
            }
        }
    }
}
