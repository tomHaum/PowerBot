package tbhizzle.rt6.cowkiller.data;

public class MyItem {
	private int id;
	private String name;
	public MyItem(int id, String name){
		this.id = id;
		this.name = name;
	}
	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	@Override
	public String toString(){
		return getName();
	}
}
