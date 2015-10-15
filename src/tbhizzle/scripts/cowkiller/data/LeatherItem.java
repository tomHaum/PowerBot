package tbhizzle.scripts.cowkiller.data;

public enum LeatherItem {
	LEATHER_GLOVES(1059, "Leather Gloves"),
	LEATHER_BOOTS(1061, "Leather boots"),
	LEATHER_COWL(1167, "Leather Cowl"),
	LEATHER_VAMBRACES(1063, "Leather vambraces"),
	LEATHR_BODY(1129, "Leather body"),
	LEATHER_CHAPS(1095, "Leather Chaps"),
	LEATHER_SHIELD(25806, "Leather Shield"),
	COIF(1169,"Coif");
	
	private Item item;
	
	LeatherItem(int id, String name){
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
