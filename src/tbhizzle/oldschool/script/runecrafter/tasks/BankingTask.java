package tbhizzle.oldschool.script.runecrafter.tasks;

import org.powerbot.bot.rt4.client.Client;
import org.powerbot.script.rt4.ClientContext;
import tbhizzle.oldschool.script.runecrafter.AirRunner;
import tbhizzle.oldschool.script.runecrafter.tasks.banking.ExitAltar;
import tbhizzle.oldschool.script.runecrafter.tasks.banking.OutOfAltarTask;
import tbhizzle.oldschool.script.runecrafter.tasks.crafting.Craft;
import tbhizzle.util.BinaryTask;
import tbhizzle.util.Callable;


/**
 * Created by Tom on 11/15/2015.
 */
public class BankingTask extends BinaryTask<ClientContext>{

    public BankingTask(ClientContext ctx, AirRunner r){
        super(ctx, new ExitAltar(ctx, r), new OutOfAltarTask(ctx, r));

        this.setActivator(new Callable<ClientContext>(ctx) {
            @Override
            public Boolean call() throws Exception {
                System.out.print(this.ctx.movement.distance(ctx.players.local().tile(), Craft.ALTAR_TILE));
                System.out.println(" tiles from the crafting altar");
                return this.ctx.movement.distance(ctx.players.local().tile(), Craft.ALTAR_TILE) != -1;
            }
        });
    }

    @Override
    public boolean execute() {
        return false;
    }
}
