package org.monstercraft.support.plugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.monstercraft.support.MonsterTickets;
import org.monstercraft.support.plugin.Configuration.Variables;
import org.monstercraft.support.plugin.command.GameCommand;
import org.monstercraft.support.plugin.util.Status;
import org.monstercraft.support.plugin.wrappers.HelpTicket;

public class Open extends GameCommand {

	@Override
	public boolean canExecute(CommandSender sender, String[] split) {
		return split[0].equalsIgnoreCase("request");
	}

	@Override
	public boolean execute(CommandSender sender, String[] split) {
		if (sender instanceof Player) {
			if (sender instanceof Player) {
				if (!MonsterTickets.getPermissionsHandler().hasCommandPerms(
						((Player) sender), this)) {
					sender.sendMessage("You don't have permission to preform this command.");
					return true;
				}
			}
		} else {
			sender.sendMessage("You must be ingame to open a ticket!");
			return true;
		}
		for (HelpTicket t : Variables.tickets) {
			if (t.getStatus().equals(Status.CLOSED)) {
				continue;
			}
			Player p = Bukkit.getPlayer(t.getNoobName());
			if (p == null) {
				continue;
			}
			if (p.equals(sender)) {
				p.sendMessage(ChatColor.GREEN
						+ "You can only have 1 ticket at a time!");
				return true;
			}
		}
		if (split.length < 2) {
			sender.sendMessage(ChatColor.RED
					+ "Invalid description of problem.");
			return true;
		}
		StringBuffer desc = new StringBuffer();
		for (int i = 1; i < split.length; i++) {
			desc.append(split[i]);
			desc.append(" ");
		}
		int id = 1;
		if (Variables.tickets.getLast() != null) {
			id = Variables.tickets.getLast().getID() + 1;
		}
		HelpTicket t = new HelpTicket(id, desc.toString().trim()
				.replace("|", "").replace("=", ""), sender.getName());
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (MonsterTickets.getPermissionsHandler().hasNode(p,
					"monstertickets.mod")) {
				p.sendMessage(ChatColor.GREEN + sender.getName()
						+ " opened ticket " + t.getID());
			}
		}
		sender.sendMessage(ChatColor.RED + "Help ticket successfully opened!");
		sender.sendMessage(ChatColor.GREEN + "" + t.getID() + " - "
				+ t.getNoobName() + " - " + t.getDescription());
		Variables.tickets.add(t);
		return true;
	}

	@Override
	public String getPermission() {
		return "monstertickets.help";
	}

}
