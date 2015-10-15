
package tbhizzle.oldschool.script.smelter;

import java.awt.*;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.powerbot.script.*;
import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Bank.Amount;
import org.powerbot.script.rt4.ClientContext;

import javax.swing.*;


@Script.Manifest(name = "My Smelter", description = "Kills cows and collects hides")
public class Smelter extends PollingScript<ClientContext> implements PaintListener {
    private static final Tile BANKTILE = new Tile(3269, 3167, 0);

    FurnaceSmelter smelter = new FurnaceSmelter(ctx);

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

    @Override
    public void start() {
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
        g.fill3DRect(0, 0, 100, 100, false);
        return;
    }

    private boolean nearTeller() {
        //System.out.println("Near Teller?");
        return ctx.players.local().tile().distanceTo(BANKTILE) < 4;
    }

    private boolean needToBank() {
        //System.out.println("Need to Bank?");
        if (ctx.bank.opened())
            ctx.bank.close();
        if (!(ctx.game.tab() == Game.Tab.INVENTORY))
            ctx.game.tab(Game.Tab.INVENTORY);
        return ctx.inventory.select().id(smelter.smeltable.getPrimaryId()).count() == 0;
    }

    private boolean nearFurnace() {
        //System.out.println("Near Furnace?");
        return ctx.players.local().tile().distanceTo(FurnaceSmelter.FURNACETILE) < 4;
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
            if (smelter.smeltable instanceof Jewelry) {
                ctx.bank.deposit(smelter.smeltable.getProductId(), Amount.ALL);
                if (ctx.inventory.select().id(((Jewelry) smelter.smeltable).getMouldId()).count() != 1) {
                    ctx.bank.depositInventory();

                    ctx.bank.withdraw(((Jewelry) smelter.smeltable).getMouldId(), 1);
                }
            }
            if(smelter.smeltable instanceof  Cannonball){
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
        walk(FurnaceSmelter.FURNACETILE);
    }

    private void walkToBank() {
        System.out.println("walking to bank");
        walk(BANKTILE);
    }

    private void walk(Tile t) {
        ctx.movement.step(t);
    }

    public void setSmeltable(Smeltable s) {
        this.smeltable = s;
        smelter.setSmeltable(s);
    }




}
