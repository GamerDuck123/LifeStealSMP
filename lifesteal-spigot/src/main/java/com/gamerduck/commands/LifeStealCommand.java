package com.gamerduck.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.gamerduck.GlobalMethods;
import com.gamerduck.LifeStealMain;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.events.LifeLostEvent;
import com.gamerduck.gui.DatabaseEditor;
import com.gamerduck.objects.LifeStealPlayer;

public class LifeStealCommand implements CommandExecutor, TabExecutor, GlobalMethods {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			LifeStealPlayer p = LifeStealMain.a().getLifeStealServer().getPlayer(player);
			if (!p.hasPermission("lifesteal.admin")) {return p.sendMessage(tl("NoPermissions"));}
			if (args.length == 0) {
				p.sendMessage("&7[&cLifeStealSMP&7] LifeStealSMP made by GamerDuck123");
				p.sendMessage(" &c- /lifesteal reload");
				p.sendMessage(" &c- /lifesteal dbeditor");
				p.sendMessage(" &c- /lifesteal life (give | remove | set) (player) (amount)");
				p.sendMessage(" &c- /lifesteal convert (VoodooLifeSteal)");
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					p.sendMessage("&cNeeds to be redone!");
				} else if (args[0].equalsIgnoreCase("life")) {
					if (args.length < 4) return p.sendMessage("&cCorrect Usage: /lifesteal life (give | remove | set) (player) (amount)");
					LifeStealPlayer target = LifeStealMain.a().getLifeStealServer().getPlayer(args[2]);
					if (target == null) return p.sendMessage(tl("PlayerNotOnline"));
					Double amount = (isNumeric(args[3])) ? Double.parseDouble(args[3]) : null;
					if (amount == null) return p.sendMessage(tl("NotANumber"));
					if (args[1].equalsIgnoreCase("set")) {
						target.setHearts(amount);
						target.sendMessage(tl("HeartsSet", args[3]));
					} else if (args[1].equalsIgnoreCase("remove")) {
						LifeLostEvent event = new LifeLostEvent(target, LifeReason.COMMAND, amount);
						Bukkit.getServer().getPluginManager().callEvent(event);	
						target.setHearts(target.getHearts() - amount);
					} else if (args[1].equalsIgnoreCase("give")) {
						LifeGainEvent event = new LifeGainEvent(target, LifeReason.COMMAND, amount);
						Bukkit.getServer().getPluginManager().callEvent(event);
						target.setHearts(target.getHearts() + amount);
					}
				} else if (args[0].equalsIgnoreCase("convert")) {
					if (args.length < 2) return p.sendMessage("&cCorrect Usage: /lifesteal convert (VoodooLifeSteal)");
					if (args[1].equalsIgnoreCase("VoodooLifeSteal")) {
						LifeStealMain.a().getConfig().set("Defaults.ConvertFrom", "VoodooLifeSteal");
						LifeStealMain.a().saveConfig();
						LifeStealMain.a().reloadConfig();
					    p.sendMessage("&cNow converting everyone from Voodoo's LifeStealSMP plugin");	
					    LifeStealMain.a().getLifeStealServer().getPlayers().forEach(pl -> pl.convertFrom("VoodooLifeSteal"));
					}
				} else if (args[0].equalsIgnoreCase("dbeditor")) {
					if (args.length < 1) return p.sendMessage("&cCorrect Usage: /lifesteal dbeditor");
					DatabaseEditor dbEditor = new DatabaseEditor();
					p.getHandle().openInventory(dbEditor.openPage(1));
				}
			}
		}
		return true;
	}
	private boolean isNumeric(String strNum) {
	    if (strNum == null) return false;
	    try { Double.parseDouble(strNum); }
	    catch (NumberFormatException nfe) { return false; }
	    return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> cmds = new ArrayList<String>();
		if (args.length == 1) {
			cmds.add("reload");
			cmds.add("life");
			cmds.add("dbeditor");
			cmds.add("convert");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("life")) {
				cmds.add("set");
				cmds.add("remove");
				cmds.add("give");
			} 
			if (args[0].equalsIgnoreCase("convert")) cmds.add("VoodooLifeSteal");
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("life")) Bukkit.getOnlinePlayers().forEach(p -> cmds.add(p.getName()));
		} else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("life")) {
				cmds.add("1");
				cmds.add("5");
				cmds.add("10");
				cmds.add("15");
				cmds.add("20");
			}
		}
		return cmds;
	}
}
