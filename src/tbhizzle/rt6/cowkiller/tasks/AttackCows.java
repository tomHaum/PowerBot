package tbhizzle.rt6.cowkiller.tasks;

import org.powerbot.script.Filter;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.MobileIdNameQuery;
import org.powerbot.script.rt6.Npc;
import tbhizzle.rt6.cowkiller.util.InteractAndWait;

/**
 * Created by O120 on 3/27/2015.
 */
//todo: create general class for killing npcs
//weird generics to get this to work.
public class AttackCows extends InteractAndWait<Npc,MobileIdNameQuery<Npc>> {

    public static final Tile centerTile = new Tile(2885, 3487,0);
    public static final int[] cowIds = {1,2};
    int radius;
    private final Filter<Npc> cowFilter = new Filter<Npc>(){
        @Override
        public boolean accept(Npc npc) {
            return centerTile.distanceTo(npc) < 7//within 7 tiles of the cow tile
                    && npc.animation() != 23566//is not dying
                    && !npc.inCombat()
                    && npc.name().equals("Cow")
                    && ctx.backpack.select().count() < 28;
        }
    };

    public AttackCows(ClientContext ctx){
        super(ctx,ctx.npcs,"Attack", cowIds);
        setFilter(cowFilter);
    }

    @Override
    public boolean waitFor() {
        return !ctx.players.local().inCombat() && !ctx.players.local().inMotion();
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() < 28
                &&
                ctx.groundItems.select().select(PickUpHides.hideFilter).count() < 3
                &&
                !(ctx.players.local().tile().distanceTo(centerTile) > 6);
    }

}
