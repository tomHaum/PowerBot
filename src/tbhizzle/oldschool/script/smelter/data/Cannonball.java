package tbhizzle.oldschool.script.smelter.data;

/**
 * Created by Tom on 10/14/2015.
 */
public class Cannonball implements Smeltable{
    public final static int SMELTWIDGET = 309;//need an account to find widgets

    private final int primaryId = 2353;
    private final int secondaryId = -1;
    private final int primaryCount = 28;
    private final int ballId = 2;
    private final int ballWidget = 6;
    private final int mouldId = 4;
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
