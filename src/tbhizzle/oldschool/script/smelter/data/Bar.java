package tbhizzle.oldschool.script.smelter.data;

/**
 * Created by Tom on 10/7/2015.
 */
public enum Bar implements Smeltable{

    BRONZE(438, 436, 14, 2349, 4),
    IRON(440, -1, 28, 2351, 6),
    SILVER(442, -1, 28, 2355, 7),
    STEEL(440, 453, 9, 2353, 8 ),
    GOLD(444, -1, 28, 2357, 9),
    MITHRIL(447, 453, 5, 2359, 10),
    ADAMANTITIE(449, 453, 4, 2361, 11),
    RUNITE(451, 453, 3, 2363, 12),
    TEST(438, -1, 14, 2349, 4);
    public static final int SMELTWIDGET = 311;

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