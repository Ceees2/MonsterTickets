package org.monstercraft.support;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.monstercraft.support.plugin.Configuration;
import org.monstercraft.support.plugin.Configuration.Variables;
import org.monstercraft.support.plugin.command.commands.Close;
import org.monstercraft.support.plugin.managers.CommandManager;
import org.monstercraft.support.plugin.managers.SettingsManager;
import org.monstercraft.support.plugin.managers.handlers.PermissionsHandler;
import org.monstercraft.support.plugin.managers.listeners.MonsterTicketListener;
import org.monstercraft.support.plugin.util.Metrics;
import org.monstercraft.support.plugin.util.MySQL;
import org.monstercraft.support.plugin.util.Updater;

public final class MonsterTickets extends JavaPlugin {

	private CommandManager commandManager;
	private PermissionsHandler perms;
	private SettingsManager settings;
	private MySQL mysql;

	public void onEnable() {
		settings = new SettingsManager(this);
		commandManager = new CommandManager(this);
		perms = new PermissionsHandler();
		try {
			mysql = new MySQL();
		} catch (Exception e) {
			Configuration
					.log("Error connecting to database! Falling back to file backend!");
			Variables.useMYSQLBackend = false;
			settings.loadTickets();
		}
		getServer().getPluginManager().registerEvents(
				new MonsterTicketListener(this), this);
		Configuration.log("MonsterTickets has been enabled!");
		Configuration.log("Setting up metrics!");
		try {
			new Metrics(this).start();
		} catch (IOException e) {
		}
		this.getServer()
				.getScheduler()
				.scheduleAsyncDelayedTask(this,
						new Updater(this.getDescription().getVersion()));
	}

	public void onDisable() {
		Close.closeClaimed();
		settings.save();
		if (!Variables.useMYSQLBackend) {
			settings.saveTicketsConfig();
		}
		Configuration.log("MonsterTickets has been disabled.");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return commandManager.onGameCommand(sender, command, label, args);
	}

	public PermissionsHandler getPermissionsHandler() {
		return perms;
	}

	public SettingsManager getSettingsManager() {
		return settings;
	}

	public MySQL getMySQL() {
		return mysql;
	}

}