package com.gamerduck.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.gamerduck.LifeStealMain;
import com.gamerduck.configs.values;
import com.gamerduck.objects.LifeStealPlayer;
import com.gamerduck.objects.LifeStealServer;

public class WithdrawCommand implements CommandExecutor, TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("Correct usage: /withdraw (amount)");
		} else {
			if (isInteger(args[0])) {
				int amount = Integer.valueOf(args[0]);
				LifeStealPlayer p = LifeStealServer.a().getPlayer((Player) sender);
				if (amount > (p.getHearts() - 2)) {
					return p.sendMessage(values.MESSAGES_LEFT_WITH_ONE_HEART);
				} else {
					p.subHearts(Double.valueOf(amount));
					for (int i = 1; i <= amount / values.HEARTCANASTER_HEARTS_AMOUNT; i++) p.getPlayer().getInventory().addItem(LifeStealMain.a().getCanaster().getItem());
					return p.sendMessage(values.MESSAGES_HEARTS_WITHDRAWN.replaceAll("%amount%", args[0]));
				}
			} else {
				sender.sendMessage(values.MESSAGES_HAS_TO_BE_WHOLE_NUMBER);
			}
		}
		
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean isInteger(String strNum) {
	    if (strNum == null) return false;
	    try { Integer.parseInt(strNum); }
	    catch (NumberFormatException nfe) { return false; }
	    return true;
	}

}
