package com.gamerduck.gui;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class Page {
	int number;
	ArrayList<ItemStack> items;
	public Page(int number, ArrayList<ItemStack> items) {
		this.number = number;
		this.items = items;
	}
	
	public int getNumber() {return number;}
	public ArrayList<ItemStack> getItems() {return items;}
}