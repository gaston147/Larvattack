package fr.vincentteam.larvattack.item;

public class Item {
	public static Item[] items = new Item[32768];
	
	public static final Item SWORD_WOOD = new Item(0, "Wood sword", 0, 1);
	public static final Item HAT_GUEST = new Item(0, "Guest's hat", 1, 1);
	
	public static void registerItem(Item item) {
		if (items[item.getId()] != null)
			throw new RuntimeException("An item is already at id " + item.getId());
		items[item.getId()] = item;
	}
	
	private int id, texCoord, maxStackSize;
	private String name;
	
	public Item(int id, String name, int texCoord, int maxStackSize) {
		this.id = id;
		this.name = name;
		this.texCoord = texCoord;
		this.maxStackSize = maxStackSize;
		
		registerItem(this);
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTexCoord() {
		return texCoord;
	}

	public int getMaxStackSize() {
		return maxStackSize;
	}
}
