package com.gamerduck.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class WithdrawCommand implements CommandExecutor, TabExecutor, GlobalMethods {
	
	private final double minimumHearts;
	
	public WithdrawCommand(FileConfiguration config) {
		minimumHearts = config.getDouble("Defaults.WithdrawMininum");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) sender.sendMessage("Correct usage: /withdraw (amount)");
		else {
			if (isInteger(args[0])) {
				double amount = Double.valueOf(args[0]);
				LifeStealPlayer p = LifeStealServer.a().getPlayer((Player) sender);
				if (amount < 0) return p.sendMessage(tl("CannotBeNegativeNumber"));
				if (p.getHearts() - (amount * 2) < (minimumHearts)) return p.sendMessage(tl("CantBeLeftWithLessThanOneHeart", minimumHearts));
				else {
					amount *= 2;
					p.subHearts(Double.valueOf(amount));
					for (int i = 1; i <= amount / 2; i++) {
						if (p.getHandle().getInventory().firstEmpty() != -1) p.getHandle().getInventory().addItem(LifeStealMain.a().getCanaster());
						else p.getHandle().getWorld().dropItem(p.getHandle().getLocation(), LifeStealMain.a().getCanaster());
					}
					return p.sendMessage(tl("HeartsWithdrawn", args[0]));
				}
			} else sender.sendMessage(tl("HasToBeWholeNumber"));
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> cmds = new ArrayList<String>();
		if (args.length == 1) {
			cmds.add("1");
			cmds.add("5");
			cmds.add("10");
			cmds.add("15");
			cmds.add("20");
		} 
		return cmds;
	}
	
	private boolean isInteger(String strNum) {
	    if (strNum == null) return false;
	    try { Integer.parseInt(strNum); }
	    catch (NumberFormatException nfe) { return false; }
	    return true;
	}

}
