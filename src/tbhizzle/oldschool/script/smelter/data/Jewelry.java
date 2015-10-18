package tbhizzle.oldschool.script.smelter.data;

/**
 * Created by Tom on 10/7/2015.
 */
public enum Jewelry implements Smeltable{
    RING(2357,-1,28,1636,7,1592),//need to check ids
    NECKLACE(2357,-1,28,1654,20,1597),
    AMULET(2357,-1,28,1674,32,1595);


    public final static int SMELTWIDGET = 446;
    private final int primaryId;
    private final int secondaryId;
    private final int primaryCount;
    private final int productId;
    private final int productWidget;
    private final int mouldId;

    Jewelry(int p, int s, int pc, int pId, int pw, int m){
        this.primaryId = p;
        this.secondaryId = s;
        this.primaryCount = pc;
        this.productId = pId;
        this.productWidget = pw;
        this.mouldId = m;
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
        return productId;
    }
    public int getWidgetId(){
        return productWidget;
    }
    public int getComponentId(){
        return SMELTWIDGET;
    }


    public int getMouldId(){
        return mouldId;
    }


}