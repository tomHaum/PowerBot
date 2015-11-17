package tbhizzle.util;

import org.powerbot.script.ClientContext;

/**
 * Created by Tom on 11/15/2015.
 */
public abstract class Callable<C extends ClientContext> implements java.util.concurrent.Callable<Boolean> {
    public  C ctx;
    public Callable(C ctx){
        this.ctx = ctx;
    }
}
