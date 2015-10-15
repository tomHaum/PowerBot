package tbhizzle.rt6.cowkiller.data;

public enum HardLeatherItem{
	HARD_LEATHER_GLOVES(25875, "Hard leather gloves"),
	HARD_LEATHER_BOOTS(25821, "Hard leather boots"),
	HARD_LEATHER_BODY(1131,"Hard leather body"),
	HARD_LEATHER_SHEILD(25808, "Hard leather shield");
	
	
	private MyItem myItem;
	
	HardLeatherItem(int id, String name){
		myItem = new MyItem(id, name);
	}
	
	@Override
	public String toString(){
		return myItem.getName();
	}
	public MyItem getMyItem(){
		return myItem;
	}
}
