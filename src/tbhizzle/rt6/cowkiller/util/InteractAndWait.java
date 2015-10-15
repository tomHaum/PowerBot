package tbhizzle.rt6.cowkiller.util;



import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Game;
import org.powerbot.script.rt6.Interactive;
import tbhizzle.util.Task;

import java.util.Comparator;
import java.util.concurrent.Callable;

public abstract class InteractAndWait<T extends Interactive & Locatable & Identifiable & Nameable, Q extends AbstractQuery<Q,T,ClientContext>> extends Task<ClientContext> {

    int[] targetIds;
    String action;
    T target;
    Q query;
    Filter<T> filter;
    Comparator<T> distanceCompare =  new Comparator<T>() {
        @Override
        public int compare(T o1, T o2) {
            if (ctx.players.local().tile().distanceTo(o1) < ctx.players.local().tile().distanceTo(o2)) {
                return -1;
            } else if (ctx.players.local().tile().distanceTo(o1) < ctx.players.local().tile().distanceTo(o2)) {
                return 0;
            }
            return 1;
        }
    };

    public InteractAndWait(ClientContext ctx,  Q query, String action, int... ids){
        super(ctx);
        this.targetIds = ids;
        this.action = action;
        this.query = query;
    }

    public void execute(){
        target = findTarget();
        if(target != null){
            System.out.println("target not null");

            interact(target,"Attack");
//            if(interact(target,action))
//                Condition.wait(new Callable<Boolean>() {
//                    @Override
//                    public Boolean call() throws Exception {
//                        //return desired condition
//                        return waitFor();
//                    }
//                }, 250, 10);
        }
    }

    public <T extends Interactive & Locatable & Identifiable & Nameable> boolean interact(T target, String action) {
        //give it 3 tries before returning false
        for (int i = 0; i < 3; i++) {
            //attempt to interact with the target
            if (target.interact(action, target.name())) {
                //we misclicked
                if (ctx.game.crosshair() == Game.Crosshair.DEFAULT) {
                    //wait for 200-250ms (average human response time)
                    Condition.sleep(Random.nextInt(200, 250));
                    //skip the conditional sleep and try again
                    continue;
                }
                //we successfully interacted with the target; wait for required condition
                return Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        System.out.println("waiting for cow to die");
                        return waitFor();
                    }
                }, 250, 10);
            }
            //the interaction point was never found; turn the camera and try again
            else {
                ctx.camera.turnTo(target, Random.nextInt(-90, 90));
            }
        }
        return false;
    }

    public T findTarget(){
        if(filter != null)
            return query.select(filter).sort(distanceCompare).limit(3).shuffle().peek();
        return query.select(new Filter<T>() {
            @Override
            public boolean accept(T t) {
                int targetId = t.id();
                for (int id : targetIds) {
                    if (targetId == id) {
                        return true;
                    }
                }
                return false;
            }
        }).sort(distanceCompare).limit(3).shuffle().peek();
    }

    public void setFilter(Filter<T> filter) {
        this.filter = filter;
    }

    //waits until this method returns true;
    public abstract boolean waitFor();

    public abstract boolean activate();


}
