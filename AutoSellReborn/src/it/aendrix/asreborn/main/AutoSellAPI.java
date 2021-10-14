package it.aendrix.asreborn.main;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import it.aendrix.asreborn.listeners.Prices;
import it.aendrix.asreborn.listeners.SellSystem;

public class AutoSellAPI {

	public HashMap<Material, Float> getPrices() {
		return Prices.all();
	}
	
	public float sellAll(Player p) {
		return SellSystem.sellAll(p);
	}
	
	public int countItem(Inventory inv) {
		int c = 0;
		for (int i = 0; i<36; i++) 
			if (inv.getItem(i) != null && Prices.exist(inv.getItem(i).getType()))
				c+=inv.getItem(i).getAmount();
		return c;
	}
	
	public boolean hasSpaceInventory(Inventory inv) {
		return SellSystem.hasSpaceInventory(inv);
	}
	
}
