
package tbhizzle.oldschool.script.smelter;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.powerbot.bot.rt6.Con;
import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Menu;
import tbhizzle.oldschool.script.smelter.data.Bar;
import tbhizzle.oldschool.script.smelter.data.Cannonball;
import tbhizzle.oldschool.script.smelter.data.Jewelry;
import tbhizzle.oldschool.script.smelter.data.Smeltable;
import tbhizzle.oldschool.script.smelter.tasks.FurnaceSmelter;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;


@Script.Manifest(name = "Honest Smelter", description = "Uses the furnace in Al Khalid to smelt bars, and gold jewelry, and Cannonballs", properties = "topic=1287220;client=4")
public class Smelter extends PollingScript<ClientContext> implements PaintListener {
    private static final Tile[] BANKTILES = {new Tile(3269, 3167, 0), new Tile(3096, 3494, 0)};
    private static final Tile[] FURNACETILES = {new Tile(3275, 3186, 0), new Tile(3108, 3499, 0)};
    private static final int[] FURNACEIDS = {24009,16469};

    FurnaceSmelter smelter = new FurnaceSmelter(this, ctx);
    private Tile bankTile = BANKTILES[0];

    private static final int BANKBOOTHID = 11744;

    private Smeltable smeltable = null;

    @Override
    public void poll() {
        //basic skeleton
        if(smeltable == null)
            return;
        if (nearTeller()) {
            if (needToBank()) {
                bank();
            } else {
                walkToFurnace();
            }
        } else {
            if (needToBank()) {
                walkToBank();
            } else {
                if (nearFurnace()) {
                    smelter.smelt();
                } else {
                    walkToFurnace();
                }
            }
        }
    }

    private JFrame frame;
    private long startTime = 0;
    @Override
    public void start() {
        log("Version: 1.01");
        startTime = System.currentTimeMillis();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new SmelterGui(Smelter.this);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.BLUE);
        g.setColor(new Color(255,255,255,100));
        g.fill3DRect(0, 0, 170, 100, false);
        g.setColor(Color.BLACK);
        g.setFont(Font.getFont("Serif"));
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.drawString("Al Kharid Smelter",10,20);
        g.setFont(Font.getFont("SansSerif"));
        g.drawString("Runtime " + getRunTime(), 10,40);

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
    private boolean nearTeller() {
        //System.out.println("Near Teller?");
        return ctx.players.local().tile().distanceTo(bankTile) < 4;
    }

    private boolean needToBank() {
        //System.out.println("Need to Bank?");
        if (ctx.bank.opened())
            ctx.bank.close();
        if (!(ctx.game.tab() == Game.Tab.INVENTORY))
            ctx.game.tab(Game.Tab.INVENTORY);

        return ctx.inventory.select().id(smelter.smeltable.getPrimaryId()).count() == 0;
                //||(smeltable.getSecondaryId() == -1? true:ctx.inventory.select().id(smeltable.getSecondaryId()).count() < (28-smeltable.getPrimaryCount())/smeltable.getPrimaryCount());
    }

    private boolean nearFurnace() {
        //System.out.println("Near Furnace?");
        return ctx.players.local().tile().distanceTo(smelter.furnaceTile) < 4;
    }


    private GameObject booth;

    private void bank() {
        System.out.println("Banking");
        if (booth == null || !booth.valid()) {
            System.out.println("Getting new banker");
            booth = ctx.objects.select().id(BANKBOOTHID).nearest().peek();
        }
        if (!ctx.bank.opened()) {
            System.out.print("Opening tab:");
            for (String s : booth.actions()) {
                System.out.print(s + ", ");
            }
            System.out.println();
            if (!booth.inViewport())
                ctx.camera.turnTo(booth);
            booth.click();
        }
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.bank.opened();
            }
        }, 50, 50);
        /*
        Iterator<Item> iter = ctx.bank.select().iterator();
        while (iter.hasNext()) {
            Item i = iter.next();
            if (i.name() == "Members object" || i.id() == 6512)
                break;
            System.out.println(i.name() + ": " + i.id());
        }
        */
        if (ctx.bank.opened()) {
            if (smelter.smeltable instanceof Bar) {
                ctx.bank.depositInventory();
            }

            int primaryCount = ctx.bank.select().id(smeltable.getPrimaryId()).count();
            int secondaryCount = ctx.bank.select().id(smeltable.getSecondaryId()).count();
            System.out.println("primary: " + primaryCount + "; secondary: " + secondaryCount);
/*
            if(primaryCount < smeltable.getPrimaryCount() || secondaryCount < 28 - smeltable.getPrimaryCount()){
               System.out.println("Not enough ingredients");
               stop();
                ctx.bot().ctx.controller.stop();
            }else{
                System.out.println("good to go");
            }*/
            if (smelter.smeltable instanceof Jewelry) {
                ctx.bank.deposit(smelter.smeltable.getProductId(), Amount.ALL);
                if (ctx.inventory.select().id(((Jewelry) smelter.smeltable).getMouldId()).count() != 1) {
                    ctx.bank.depositInventory();
                    ctx.bank.withdraw(((Jewelry) smelter.smeltable).getMouldId(), 1);
                }
            }
            if(smelter.smeltable instanceof Cannonball){
                ctx.bank.deposit(smelter.smeltable.getProductId(), Amount.ALL);
                if (ctx.inventory.select().id(((Cannonball) smelter.smeltable).getMouldId()).count() != 1) {
                    ctx.bank.depositInventory();
                    ctx.bank.withdraw(((Cannonball) smelter.smeltable).getMouldId(), 1);
                }
            }

            //System.out.println(smelter.smeltable.getPrimaryCount());
            ctx.bank.withdraw(smelter.smeltable.getPrimaryId(), smelter.smeltable.getPrimaryCount());
            if(smelter.smeltable.getPrimaryCount() < 28) {
                ctx.bank.withdraw(smelter.smeltable.getSecondaryId(), Amount.ALL);
            }
        }
    }


    private void walkToFurnace() {
        System.out.println("walking to furnace");
        if(bankTile == BANKTILES[0])//if it is in al khadir
            openDoor();
        else
            System.out.println("not in al kharid");
        walk(smelter.furnaceTile);
    }

    private void walkToBank() {
        System.out.println("walking to bank");
        if(bankTile == BANKTILES[0])//if it is in al khadir
            openDoor();
        else
            System.out.println("not in al kharid");
        walk(bankTile);
    }
    private static final Tile DOOROPEN = new Tile(3279,3185,0);
    private static final Tile DOORCLOSE = new Tile(3280,3185,0);
    private void openDoor(){
        Iterator<GameObject> objects = ctx.objects.select().iterator();
        while(objects.hasNext()){
            GameObject object = objects.next();
            if(object.name().toLowerCase().compareTo("door") == 0){
                if(object.tile().x() == DOORCLOSE.x() && object.tile().y() == DOORCLOSE.y()){
                    object.interact(false, "Open");
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().inMotion();
                        }
                    }, 200, 20);
                    break;
                }

            }
        }
    }
    private void walk(Tile t) {
        if(ctx.movement.reachable(ctx.players.local().tile(), t))
        ctx.movement.step(t);
    }

    public void setSmeltable(Smeltable s) {
        this.smeltable = s;
        smelter.setSmeltable(s);
    }
    public void setLocation(int i){
        bankTile = BANKTILES[i];
        smelter.setFurnace(FURNACETILES[i],FURNACEIDS[i]);
    }

    public void log(String s){
        log.info(s);
    }


}
