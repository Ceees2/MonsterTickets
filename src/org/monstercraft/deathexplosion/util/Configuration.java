package org.monstercraft.deathexplosion.util;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.monstercraft.deathexplosion.DeathExplosion;

public class Configuration {
	
	private FileConfiguration config;
	
	public Configuration(DeathExplosion plugin) {
		config = plugin.getConfig();
		loadConfigs();
	}
	
	public void saveConfig() {
		try {
			config.set("TOMB.ENABLED", Variables.chest);
			config.set("TOMB.COLOR", Variables.colorCode);
			config.set("TOMB.ANNOUNCE_DEATH", Variables.announce);
			config.set("TOMB.PRIVATE.COST", Variables.pcost);
			config.set("TOMB.TIME.SEC", Variables.secs);
			config.set("TOMB.TIME.MIN", Variables.mins);
			config.set("TOMB.TIME.HOUR", Variables.hours);
			config.set("EXPLOSION.COST", Variables.cost);
			config.set("EXPLOSION.SIZE", Variables.size);
			config.save(new File(Constants.SETTINGS_PATH + Constants.SETTINGS_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfigs() {
		final File CONFIGURATION_FILE = new File(Constants.SETTINGS_PATH + Constants.SETTINGS_FILE);
		if (!CONFIGURATION_FILE.exists()) {
			new File(Constants.SETTINGS_PATH).mkdirs();
			saveConfig();
		} else {
			try {
				config.load(CONFIGURATION_FILE);
				Variables.chest = config.getBoolean("TOMB.ENABLED");
				Variables.colorCode = config.getString("TOMB.COLOR");
				Variables.announce = config.getBoolean("TOMB.ANNOUNCE_DEATH");
				Variables.cost = config.getInt("EXPLOSION.COST");
				Variables.size = config.getInt("EXPLOSION.SIZE");
				Variables.pcost = config.getInt("TOMB.PRIVATE.COST");
				Variables.secs = config.getInt("TOMB.TIME.SEC");
				Variables.mins = config.getInt("TOMB.TIME.MIN");
				Variables.hours = config.getInt("TOMB.TIME.HOUR");
			} catch (Exception e) {
				saveConfig();
				e.printStackTrace();
			}
		}
	}

}
