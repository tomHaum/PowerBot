package tbhizzle.oldschool.script.smelter;

/**
 * Created by Tom on 10/14/2015.
 */
public class Cannonball implements Smeltable{
    public final static int SMELTWIDGET = -1;//need an account to find widgets
    private final int primaryId = -1;
    private final int secondaryId = -1;
    private final int primaryCount = -1;
    private final int ballId = -1;
    private final int ballWidget = -1;
    private final int mouldId = -1;
    public Cannonball(){
        super();
    }
    public int getPrimaryId(){
        return primaryId;
    }
    public int getSecondaryId(){
        return secondaryId;
    }
    public int getPrimaryCount(){
        return primaryCount;
    }
    public int getProductId(){
        return ballId;
    }
    public int getWidgetId(){
        return ballWidget;
    }
    public int getMouldId(){
        return mouldId;
    }
    public int getComponentId(){
        return SMELTWIDGET;
    }
}
