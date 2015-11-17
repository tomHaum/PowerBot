package tbhizzle.util;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Tom on 11/15/2015.
 */
public abstract class BinaryTask<C extends ClientContext> extends ClientAccessor<C>{
    private final BinaryTask<C> trueTask;
    private final BinaryTask<C> fasleTask;
    private Callable<Boolean> activator;

    public BinaryTask(C context, BinaryTask a, BinaryTask b){
        super(context);
        trueTask = a;
        fasleTask = b;
    }
    public boolean activate(){
        System.out.print("In task " + getClass().getSimpleName());
        if(activator == null) {
            System.out.println(" executing");
            return execute();
        }
        try {
            if(activator.call()){
                System.out.println(" doing task " + trueTask.getClass().getSimpleName());
                return trueTask.activate();
            }else{
                System.out.println(" doing task " + fasleTask.getClass().getSimpleName());
                return fasleTask.activate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public abstract boolean execute();

    public void setActivator(Callable<Boolean> activator){
        this.activator = activator;
    }
}
