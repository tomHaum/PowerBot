package tbhizzle.rt6.cowkiller.data;

public enum LeatherItem {
	LEATHER_GLOVES(1059, "Leather gloves"),
	LEATHER_BOOTS(1061, "Leather boots"),
	LEATHER_COWL(1167, "Leather cowl"),
	LEATHER_VAMBRACES(1063, "Leather vambraces"),
	LEATHR_BODY(1129, "Leather body"),
	LEATHER_CHAPS(1095, "Leather chaps"),
	LEATHER_SHIELD(25806, "Leather shield"),
	COIF(1169,"Coif");
	
	private MyItem myItem;
	
	LeatherItem(int id, String name){
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
