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

/*
[7:48:56 PM] Az: 827
[7:48:58 PM] Az: uhm
[7:49:02 PM] Az: it does like
[7:49:16 PM] Az: one normal smelt animation, followed by instant cb animation
[7:49:27 PM] Az: then sleeps for like 2.5 secs
[7:49:31 PM] Az: and another cb animation
[7:49:39 PM] Az: lemme stream it for you
*/
