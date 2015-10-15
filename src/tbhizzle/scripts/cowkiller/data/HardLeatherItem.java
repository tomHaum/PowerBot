package tbhizzle.scripts.cowkiller.data;

public enum HardLeatherItem{
	HARD_LEATHER_GLOVES(25875, "Hard leather gloves"),
	HARD_LEATHER_BOOTS(25821, "Hard leather boots"),
	HARD_LEATHER_BODY(1131,"Hard leather body"),
	HARD_LEATHER_SHEILD(25808, "Hard leather shield");
	
	
	private Item item;
	
	HardLeatherItem(int id, String name){
		item = new Item(id, name);
	}
	
	@Override
	public String toString(){
		return item.getName();
	}
	public Item getItem(){
		return item;
	}
}
