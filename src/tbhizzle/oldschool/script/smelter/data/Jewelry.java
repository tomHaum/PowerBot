package tbhizzle.oldschool.script.smelter.data;

/**
 * Created by Tom on 10/7/2015.
 */
public enum Jewelry implements Smeltable{
    RING(2357,-1,28,1635,7,1592),//need to check ids
    NECKLACE(2357,-1,28,1654,20,1597),
    AMULET(2357,-1,28,1673,32,1595),
    BRACELET(2357,-1,28,11069,44,11065);


    public final static int SMELTWIDGET = 446;
    private final int primaryId;
    private final int secondaryId;
    private final int primaryCount;
    private final int productId;
    private final int productWidget;
    private final int mouldId;
    private Jewel jewel;
    Jewelry(int p, int s, int pc, int pId, int pw, int m){
        this.primaryId = p;
        this.secondaryId = s;
        this.primaryCount = pc;
        this.productId = pId;
        this.productWidget = pw;
        this.mouldId = m;
        this.jewel = Jewel.NONE;
    }
    Jewelry(Jewelry j, Jewel gem){
       this(j.getPrimaryId(),
               gem.getId(),
               (j.getPrimaryCount()-1)/2,
               j.getProductId(),
               j.getWidgetId() + gem.getOffset(),
               j.getMouldId());
    }

    public void setJewel(Jewel j){
        this.jewel = j;
    }
    public int getPrimaryId(){
        return primaryId;
    }
    public int getSecondaryId(){
        return jewel.getId();
    }
    public int getPrimaryCount(){
        return (jewel == Jewel.NONE? 28:13);
    }
    public int getProductId(){
        return productId;
    }
    public int getWidgetId(){
        return productWidget + jewel.getOffset();
    }
    public int getComponentId(){
        return SMELTWIDGET;
    }


    public int getMouldId(){
        return mouldId;
    }


}