package tbhizzle.oldschool.script.smelter.tasks;


import org.powerbot.script.*;
import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt4.*;

import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.smelter.Smelter;
import tbhizzle.oldschool.script.smelter.data.Bar;
import tbhizzle.oldschool.script.smelter.data.Cannonball;
import tbhizzle.oldschool.script.smelter.data.Jewelry;
import tbhizzle.oldschool.script.smelter.data.Smeltable;

import java.util.concurrent.Callable;

/**
 * Created by Tom on 10/7/2015.
 */
public class FurnaceSmelter extends ClientAccessor<ClientContext> {
    Smelter parent;
    public  Tile furnaceTile = new Tile(3275, 3186, 0);
    private int furnaceId = 24009;

    private static final int ENTERAMOUNTPARENT = 162;
    private static final int ENTERAMOUNTCHILD = 32;

    private final static int[] EdgevilleBounds = new int[]{-18, 37, -108, 7, -65, 50};
    private final static int[] AlKharidBounds = new int[]{-114, 128, -197, 0, -145, 161};

    private GameObject furnace;
    private int smeltWidget;//this is the parent component to bar smelting widgests
    private String location = "Al";
    public Smeltable smeltable;//
    public FurnaceSmelter(ClientContext ctx, Smelter s) {
        super(ctx);
        parent = s;
    }

    public void smelt() {
        System.out.println("smelting");
        if (furnace == null || !furnace.valid()) {
            furnace = ctx.objects.select().id(furnaceId).peek();
        }
        if(ctx.players.local().interacting() == null){
            System.out.println("not interacting");
        }else{
            if(ctx.players.local().interacting().valid())
                System.out.println("Interacting with something");
            else
                System.out.println("still not interacting");

        }
        //System.out.println("furnace: " + furnace.id());
        if (!furnace.inViewport()) {
            System.out.println("turning to camera");
            ctx.camera.turnTo(furnace);
        }

        /*need a reliable way to detect if the player is smelting*/
        if (playerIsSmelting()) {//wait till smelting is over
            System.out.println("Waiting for smelting to e finished");

        } else {

            if(ctx.widgets.component(ENTERAMOUNTPARENT, ENTERAMOUNTCHILD).visible()){
                System.out.println("Entering an amount");
                enterAmount(Random.nextInt(100,999));
            }else{
                if(ctx.widgets.component(smeltWidget, smeltable.getWidgetId()).visible()){//crafting menu is up

                    if (ctx.menu.opened()) {//right click menu is open
                        System.out.println("smelting x");
                        selectSmeltX();
                    } else {//right click menu is not open
                        System.out.println("right clicking smelt");
                        //right clicks smelt
                        rightClickSmelt();
                    }
                }else{//no menus are up, just sitting there
                    System.out.println("Widget: " + smeltWidget + "; Child: " + smeltable.getWidgetId());
                    System.out.println("Clicking furnace");
                    clickFurnace(furnace);
                }
            }

        }

    }

    private boolean playerIsSmelting(){
        //return ctx.players.local().animation() != 0 && !(ctx.players.local().interacting() == null);
        return false;
    }
    private void rightClickSmelt(){
        ctx.widgets.component(smeltWidget, smeltable.getWidgetId()).click(false);

        //waits until the menu is up
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.menu.opened();
            }
        }, 200, 20);
    }

    private void enterAmount(int amount){
        if(amount < 0 )
            amount *=-1;

        ctx.input.sendln(Integer.toString(amount));
        smeltWait();
    }

    private void clickFurnace(GameObject furnace){

        if(smeltable instanceof Bar) {
            System.out.println("Bar smelting");
            furnace.interact(false, "Smelt");
        }
        if(smeltable instanceof Jewelry || smeltable instanceof Cannonball){
            parent.log("selecting inventory");
            ctx.inventory.select().id(smeltable.getPrimaryId()).peek().hover();
            ctx.input.click(true);
            parent.log("clicking furnace");

            furnace.interact(false,"Use","Furnace");
        }

        //waits until smelting interface is up
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(smeltWidget, smeltable.getWidgetId()).visible();
            }
        }, 200, 10);
    }

    private void selectSmeltX(){
        //smelt x
        //clicks smelt x
        ctx.menu.click(new Filter<MenuCommand>() {
            @Override
            public boolean accept(MenuCommand command) {
                if(smeltable instanceof Bar)
                    return command.toString().toLowerCase().startsWith("smelt x");
                if(smeltable instanceof Jewelry) {
                    System.out.println("hiting make-x");
                    return command.toString().toLowerCase().startsWith("make-x");
                }
                if(smeltable instanceof Cannonball) {
                    System.out.println("making all cannon balls");
                    return command.toString().toLowerCase().startsWith("make all");
                }
                return false;
            }
        });
        //waits until ENTER AMOUNT is visible
        if(smeltable instanceof Cannonball)
            smeltWait();
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(ENTERAMOUNTPARENT, ENTERAMOUNTCHILD).visible();
            }
        }, 200, 50);

    }

    public void setSmeltable(Smeltable s){
        this.smeltable = s;
        this.smeltWidget = s.getComponentId();
    }

    public void setFurnace(Tile tile, int id){
        furnaceTile = tile;
        furnaceId = id;
    }

    public void setLocation(String location){
        this.location = location;
    }

    private void smeltWait() {
        final int skill = (smeltable instanceof Jewelry ? Constants.SKILLS_CRAFTING : Constants.SKILLS_SMITHING);
        int smithXP = ctx.skills.experience(skill);
        //shorter wait to wait for one bar to craft
        /*Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().id(smeltable.getProductId()).count() != 0;
            }
        }, 200, 20);*/
        final int initialCount = ctx.inventory.select().id(smeltable.getPrimaryId()).count();
        parent.log("Inventory count: " + initialCount + ". Waiting till change in count");
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().id(smeltable.getPrimaryId()).count() != initialCount;
            }
        }, 200, 20);
        //if we crafted one then we should wait for the rest, other wise it will try again-
        if (ctx.inventory.select().id(smeltable.getPrimaryId()).count() < initialCount) {
            //this is the actual smelting wait, like waiting for all the bars to finish
            int count = 28;
            int newXp;
            int sleep = (smeltable instanceof Cannonball ? 15000 : 4000);
            Condition.sleep(sleep);
            if (smeltable instanceof Cannonball) {
                parent.log("We are doing cannonballs");
            } else if (smeltable instanceof Jewelry) {
                parent.log("We are doing Jewelery");
            } else if (smeltable instanceof Bar) {
                parent.log("We are doing a bar");
            }else{
                parent.log("Im fucked");
            }
            parent.log("what is going on");
            /*if (smeltable instanceof Cannonball) {
                parent.log("should have made at least one cannon ball");
                parent.log("there are " + ctx.inventory.select().id(smeltable.getPrimaryId()).count() + "steel bars");
                while (count-- > 0) {
                    final int cannonballs = ctx.inventory.select().id(smeltable.getProductId()).peek().stackSize();
                    parent.log("there are " + cannonballs + " Cannonballs in the inventory");
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {

                            return ctx.inventory.select().id(smeltable.getProductId()).peek().stackSize() <= cannonballs;
                        }
                    }, 600, 13);
                    int newCbs = ctx.inventory.select().id(smeltable.getProductId()).peek().stackSize();
                    parent.log("there are " + newCbs + " Cannonballs in the inventory");
                    if (newCbs > cannonballs) {
                        parent.log("Made more cannonballs, Great");
                    } else {
                        parent.log("didnt make more cannonballs, must be out of steel bars");
                        parent.log("Steel bars: " + ctx.inventory.select().id(smeltable.getPrimaryId()).count());
                        break;
                    }
                }
            } else {*/
               /* parent.log("not a cannonball");
                while (count-- > 0 && smithXP != ctx.skills.experience(skill)) {
                    parent.log("smith XP: " + smithXP + "; new xp: " + ctx.skills.experience(skill));

                    System.err.println("Bar smelting");
                    smithXP = ctx.skills.experience(skill);
                    Condition.sleep(sleep);
                }
            }*/

            if(smeltable instanceof Cannonball) {
                parent.log("There should be " + ctx.inventory.select().id(smeltable.getPrimaryId()).count() + " materials left");
                while (true) {
                    //parent.log("Condition.wait");
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().animation() == 899 || ctx.widgets.component(233, 0).visible() || ctx.inventory.select().id(smeltable.getPrimaryId()).count() == 0;
                        }
                    }, 60, 110);
                    if (ctx.players.local().animation() != 899 || ctx.widgets.component(233, 0).visible() || ctx.inventory.select().id(smeltable.getPrimaryId()).count() == 0) {
                        break;
                    }
                }
            }else if(smeltable.equals(Bar.IRON)){
                //do it by inventory count
                int primaryCount;
                do{
                    primaryCount = ctx.inventory.select().id(smeltable.getPrimaryId()).count();
                    parent.log("Iron Sleeping. Iron count: " + primaryCount);
                    if(count == 0)
                        break;
                    Condition.sleep(sleep);
                    if(ctx.widgets.component(233, 0).visible())
                        break;
                }while(count-- >0 && primaryCount != ctx.inventory.select().id(smeltable.getPrimaryId()).count());
            }
            else{
                while (count-- > 0 && smithXP != ctx.skills.experience(skill)) {
                    parent.log("XP: " + smithXP + "; new xp: " + ctx.skills.experience(skill));

                    System.err.println("Bar smelting");
                    smithXP = ctx.skills.experience(skill);
                    Condition.sleep(sleep);
                }
            }

            parent.log("done sleeping");
        } else {
            System.out.println("We have " + ctx.inventory.count() + " amultets");
        }
        if (ctx.widgets.component(233, 0).visible()) {
        ctx.input.send(" ");
        }
    }

}

/*
}


private int[] EdgevilleBounds = new int[]{-18, 37, -108, 7, -65, 50};
private int[] AlKharidBounds = new int[]{-114, 128, -197, 0, -145, 161};

final GameObject FURNACE = ctx.objects.select().id(FURNACE_ID).nearest().poll();
if (location = edge) {
    FURNACE.bounds(EdgevilleBounds);
} else if (location = al kharid) {
        FURNACE.bounds(AlKharidBounds);
    }
 */
