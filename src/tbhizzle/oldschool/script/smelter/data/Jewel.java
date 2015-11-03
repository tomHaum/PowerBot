package tbhizzle.oldschool.script.smelter.data;

/**
 * Created by Tom on 10/19/2015.
 */
public enum Jewel {
    NONE(-1,0),
    SAPPHIRE(1607,1),
    EMMERALD(1605,2),
    RUBY(1603,3),
    DIAMOND(1601,4);
    private int id,offset;
    Jewel(int id, int off){
        this.id = id;
        this.offset = off;
    }
    public int getOffset(){
        return offset;
    }
    public int getId(){
        return id;
    }
}