package tbhizzle.rt6.cowkiller.data;
/*
un-used
 */
public class Interaction {
    private int id;
    private String name;
    private String action;

    Interaction(int id, String name, String action){
        this.id = id;
        this.name = name;
        this.action = action;
    }
    public class GameObject extends Interaction{
        GameObject(int id, String name, String action){
            super(id,name,action);
        }
    }
    public class GroundItem extends Interaction {
        GroundItem(int id, String name, String action){
            super(id,name,action);
        }
    }

    public int id(){
        return id;
    }
    public String name(){
        return name;
    }
    public String action(){
        return action;
    }
    public void setAction(String action){
        this.action = action;
    }
}

