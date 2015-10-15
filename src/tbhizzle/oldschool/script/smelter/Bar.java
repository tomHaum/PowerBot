package tbhizzle.oldschool.script.smelter;

/**
 * Created by Tom on 10/7/2015.
 */
public enum Bar implements Smeltable{
    BRONZE(438,436,14,2349,4),
    TEST(1,2,3,4,5);
    public static final int SMELTWIDGET = 331;
    private final int primaryId;
    private final int secondaryId;
    private final int primaryCount;
    private final int barId;
    private final int barWidget;
    Bar(int p, int s, int pc, int bId, int bw){
        this.primaryId = p;
        this.secondaryId = s;
        this.primaryCount = pc;
        this.barId = bId;
        this.barWidget = bw;
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
        return barId;
    }
    public int getWidgetId(){
        return barWidget;
    }
    public int getComponentId(){
        return SMELTWIDGET;
    }


}