package tbhizzle.oldschool.script.runecrafter.tasks.banking;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.*;
import tbhizzle.oldschool.script.runecrafter.RuneCrafter;
import tbhizzle.oldschool.script.runecrafter.data.Altar;
import tbhizzle.util.BinaryTask;

import java.util.concurrent.Callable;

/**
 * Created by Tom on 11/14/2015.
 */
public class Bank extends BinaryTask<ClientContext> {

    //public static final Tile BANK_TILE = new Tile(3012, 3356, 0);
    RuneCrafter airRunner;
    private int RUNE_ESSENCE = -1;
    Altar altar;
    public Bank(ClientContext clientContext, RuneCrafter r, Altar altar){
        super(clientContext, null,null);
        this.airRunner = r;
        this.altar = altar;

    }

    Interactive bank;
    Npc fadli;
    private static final int fadliId = 3340;
    @Override
    public boolean execute() {
        if(RUNE_ESSENCE == -1) {
            //airRunner.log("Bank is using pure ess: " + airRunner.isUsingPureEss());
            RUNE_ESSENCE = (airRunner.isUsingPureEss()? 7936 : 1436);//pure ? normal
            airRunner.log("Essence ID: " + RUNE_ESSENCE);
        }
        airRunner.log("Made it Here");
        if(!ctx.movement.running())
            ctx.movement.running(true);

        if(bank == null || !bank.valid()){
            if(altar != Altar.FIRE)
                bank = ctx.npcs.select().action("Bank").poll();
            else
                bank = ctx.npcs.select().id(fadliId).peek();
            airRunner.log(bank.toString());
        }


        int count = 3;
        if(!ctx.bank.opened()) {
                if (!bank.interact(false, "Bank"))
                    return false;
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                }, 200, 3);
                if (!ctx.bank.opened())
                    return false;

        }
        if(ctx.inventory.select().count() != 0) {
            count = 3;
            while (!ctx.bank.depositInventory() && count > 0) {
                count--;
            }
            if (ctx.inventory.select().count() != 0)
                return false;
        }
        if(ctx.bank.select().id(RUNE_ESSENCE).peek().stackSize() < 1){
            System.out.println("out of runes");
            ctx.controller.stop();
        }
        if(ctx.inventory.select().count() != 28){
            System.out.println("withdrawing from bank");
            while(!ctx.bank.withdraw(RUNE_ESSENCE, org.powerbot.script.rt4.Bank.Amount.ALL)){
                System.out.println("withdrawing from bank loop");
                count--;
                if(count == 0){
                    return false;
                }
            }
            if(ctx.inventory.select().count() != 28){
                if(ctx.inventory.count() > 0 )
                    airRunner.log("Not enough essence. Quitting");
                    ctx.controller.stop();
                return false;
            }

        }
        return true;

    }
}
