package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.item.Item;
import fr.vincentteam.larvattack.item.ItemStack;

public class Inventory {
	private ItemStack[] items;
	
	public Inventory(int size) {
		items = new ItemStack[size];
	}
	
	public boolean add(Item item, int quantity) {
		if (quantity > item.getMaxStackSize())
			return false;
		for (int i = 0; i < items.length; i++)
			if (items[i] != null && items[i].getId() == item.getId())
				if (items[i].add(quantity))
					return true;
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = new ItemStack(item, quantity);
				return true;
			}
		}
		return false;
	}
	
	public boolean add(Item item) {
		return add(item, 1);
	}
	
	public int getSize() {
		return items.length;
	}
}
