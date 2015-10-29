package tbhizzle.oldschool.script.smelter.tasks;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Menu;

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

    private GameObject furnace;
    private int smeltWidget;//this is the parent component to bar smelting widgests

    public Smeltable smeltable;//
    public FurnaceSmelter(Smelter s, ClientContext ctx) {
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
        if (!furnace.inViewport())
           System.out.println("turning to camera");
            ctx.camera.turnTo(furnace);

        /*need a reliable way to detect if the player is smelting*/
        if (playerIsSmelting()) {//wait till smelting is over
            System.out.println("Waiting for smelting to e finished");
            /*Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    //put a check for level up here
                    return ctx.inventory.select().id(COPPERORE).count() == 0;//should be primary
                }
            }, 1000, 50);*/
        } else {

            if(ctx.widgets.component(ENTERAMOUNTPARENT, ENTERAMOUNTCHILD).visible()){
                System.out.println("Entering an amount");
                enterAmount(999);
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
            // old structure, didnt allow for
            /*Component smeltComponent = ctx.widgets.component(smeltWidget,smeltable.getWidgetId());
            if (smeltComponent.visible()) {//smelt menu is up

                if (ctx.menu.opened()) {//right click menu is open
                    System.out.println("smelting x");
                    selectSmeltX();
                } else {//right click menu is not open
                    System.out.println("right clicking smelt");
                    //right clicks smelt
                    rightClickSmelt();
                }
            } else {//smelt menu is not up
                //enter amount prompt is up
                if (ctx.widgets.component(ENTERAMOUNTPARENT, ENTERAMOUNTCHILD).visible()) {
                    System.out.println("Entering an amount");
                    enterAmount(999);
                } else {//no menus are up, just sitting there
                    System.out.println("Clicking furnace");
                    clickFurnace(furnace);
                }
            }*/
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
        }, 20, 50);
    }

    private void enterAmount(int amount){
        if(amount < 0 )
            amount *=-1;

        ctx.input.sendln(Integer.toString(amount));
        int skill = (smeltable instanceof Jewelry? Constants.SKILLS_CRAFTING: Constants.SKILLS_SMITHING);
        int smithXP = ctx.skills.experience(skill);
        //shorter wait to wait for one bar to craft
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.inventory.select().id(smeltable.getProductId()).count() != 0;
            }
        }, 200, 20);
        //if we crafted one then we should wait for the rest, other wise it will try again-
        if(ctx.inventory.select().id(smeltable.getProductId()).count() > 0){
            //this is the actual smelting wait, like waiting for all the bars to finish
            int count = 20;
            int newXp;
            int sleep = (smeltable instanceof Cannonball? 15000: 4000);
            Condition.sleep(sleep);
            if(smeltable instanceof Cannonball){
                while(count-- > 0 && ctx.inventory.select().id(smeltable.getPrimaryId()).count() > 0){
                    Condition.sleep(sleep);
                    if(ctx.widgets.component(233,0).visible()){
                        break;
                    }
                    parent.log(ctx.inventory.count() + " steel bars in inventory");
                }
            }else{
                while(count-- > 0 && smithXP !=  ctx.skills.experience(skill)){
                    parent.log("smith XP: " + smithXP + "; new xp: " + ctx.skills.experience(skill));

                    System.err.println("Bar smelting");
                    smithXP = ctx.skills.experience(skill);
                    Condition.sleep(sleep);
                }
            }
            parent.log("done sleeping");
        }else{
            System.out.println("We have " + ctx.inventory.count() + " amultets");
        }
        if(ctx.widgets.component(233,0).visible()){
            ctx.input.send(" ");
        }

    }

    private void clickFurnace(GameObject furnace){
        if(smeltable instanceof Bar) {
            System.out.println("Bar smelting");
            furnace.interact(false, "Smelt");
        }
        if(smeltable instanceof Jewelry || smeltable instanceof Cannonball){
            System.out.println(ctx.inventory.selectedItemIndex());
            if(!ctx.inventory.selectedItem().valid())
                ctx.inventory.select().id(smeltable.getPrimaryId()).peek().click();
            if(!ctx.inventory.selectedItem().name().toLowerCase().equals("gold bar"))
                System.out.println(ctx.inventory.selectedItemIndex());
            if(!ctx.inventory.selectedItem().equals(ctx.inventory.nil()))
                furnace.interact(false,"Use","Furnace");

        }
        //waits until smelting interface is up
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.widgets.component(smeltWidget, smeltable.getWidgetId()).visible();
            }
        }, 20, 50);
    }

    private void selectSmeltX(){
        //smelt x
        //clicks smelt x
        ctx.menu.click(new Filter<Menu.Command>() {
            @Override
            public boolean accept(Menu.Command command) {
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

}
