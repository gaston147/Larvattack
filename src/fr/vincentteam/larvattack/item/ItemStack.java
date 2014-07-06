package fr.vincentteam.larvattack.item;

public class ItemStack {
	private Item item;
	private int quantity;
	
	public ItemStack(Item item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	
	public int getId() {
		return item.getId();
	}
	
	public String getName() {
		return item.getName();
	}
	
	public int getTexCoord() {
		return item.getTexCoord();
	}
	
	public boolean addOne() {
		return add(1);
	}
	
	public boolean add(int quantity) {
		if (this.quantity + quantity <= item.getMaxStackSize()) {
			this.quantity += quantity;
			return true;
		}
		return false;
	}
}
