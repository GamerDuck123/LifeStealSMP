package com.gamerduck.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.gamerduck.LifeStealMain;
import com.gamerduck.configs.values;
import com.gamerduck.enums.LifeReason;
import com.gamerduck.events.LifeGainEvent;
import com.gamerduck.events.LifeLostEvent;
import com.gamerduck.objects.LifeStealPlayer;

public class LifeStealCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			LifeStealPlayer p = LifeStealMain.a().getLifeStealServer().getPlayer(player);
			if (!p.hasPermission("lifesteal.admin")) {return p.sendMessage(values.MESSAGES_NOPERMISSIONS);}
			if (args.length == 0) {
				p.sendMessage("&7[&cLifeStealSMP&7] LifeStealSMP made by GamerDuck123");
				p.sendMessage(" &c- /lifesteal reload");
				p.sendMessage(" &c- /lifesteal life (give | remove | set) (player) (amount)");
				p.sendMessage(" &c- /lifesteal convert (VoodooLifeSteal)");
			} else {
				if (args[0].equalsIgnoreCase("reload")) {
					LifeStealMain.a().getLifeStealServer().reload();
					p.sendMessage("&cConfig reloaded");
				} else if (args[0].equalsIgnoreCase("life")) {
					if (args.length < 4) return p.sendMessage("&cCorrect Usage: /lifesteal life (give | remove | set) (player) (amount)");
					LifeStealPlayer target = LifeStealMain.a().getLifeStealServer().getPlayer(args[2]);
					if (target == null) return p.sendMessage(values.MESSAGES_PLAYER_NOT_ONLINE);
					Double amount = (isNumeric(args[3])) ? Double.parseDouble(args[3]) : null;
					if (amount == null) return p.sendMessage(values.MESSAGES_NOT_A_NUMBER);
					if (args[1].equalsIgnoreCase("set")) {
						target.setHearts(amount);
						target.sendMessage(values.MESSAGES_HEARTS_SET.replaceAll("%amount%", args[3]));
					} else if (args[1].equalsIgnoreCase("remove")) {
						LifeLostEvent event = new LifeLostEvent(target.getPlayer(), LifeReason.COMMAND, amount);
						Bukkit.getServer().getPluginManager().callEvent(event);	
					} else if (args[1].equalsIgnoreCase("give")) {
						LifeGainEvent event = new LifeGainEvent(target.getPlayer(), LifeReason.COMMAND, amount);
						Bukkit.getServer().getPluginManager().callEvent(event);
					}
				} else if (args[0].equalsIgnoreCase("convert")) {
					if (args.length < 2) return p.sendMessage("&cCorrect Usage: /lifesteal convert (VoodooLifeSteal)");
					if (args[1].equalsIgnoreCase("VoodooLifeSteal")) {
						LifeStealMain.a().getAPI().getConfig().set("Defaults.ConvertFrom", "VoodooLifeSteal");
						LifeStealMain.a().getAPI().getServer().reload();
					    p.sendMessage("&cNow converting everyone from Voodoo's LifeStealSMP plugin");	
					    LifeStealMain.a().getAPI().getServer().getOnlinePlayers().forEach(pl -> pl.convertFrom("VoodooLifeSteal"));
					}
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
		if (args.length == 0) {
			cmds.add("reload");
			cmds.add("life");
		} else if (args.length == 1) {
			cmds.add("set");
			cmds.add("remove");
			cmds.add("give");
		} else if (args.length == 2) {
			Bukkit.getOnlinePlayers().forEach(p -> cmds.add(p.getName()));
		} else if (args.length == 3) {
			cmds.add("1");
			cmds.add("5");
			cmds.add("10");
			cmds.add("15");
			cmds.add("20");
		}
		return null;
	}
}
