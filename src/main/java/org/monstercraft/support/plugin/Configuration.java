package org.monstercraft.support.plugin;

import java.util.LinkedList;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.monstercraft.support.plugin.wrappers.HelpTicket;

/**
 * This class holds all of the configuration data used within the plugin.
 * 
 * @author fletch_to_99 <fletchto99@hotmail.com>
 * 
 */
public class Configuration {

	public static String shortenString(String str) {
		if (str.length() > 20) {
			str = str.substring(0, 20);
			str = str + "...";
		}
		return str;
	}

	/**
	 * Checks if a string is a valid integer.
	 * 
	 * @param message
	 *            The string to parse.
	 * @return True if the string is a valid number; otherwise false.
	 */
	public static boolean canParse(String message) {
		try {
			Integer.parseInt(message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Logs a message to the console.
	 * 
	 * @param msg
	 *            The message to print.
	 */
	public static void log(String msg) {
		Bukkit.getLogger().log(Level.INFO, "[MonsterTickets] " + msg);
	}

	/**
	 * Logs debugging messages to the console.
	 * 
	 * @param error
	 *            The message to print.
	 */
	public static void debug(Exception error) {
		Bukkit.getLogger().log(Level.WARNING,
				"[MonsterTickets - Critical error detected!]");
		error.printStackTrace();
	}

	public static class Variables {

		public static LinkedList<HelpTicket> tickets = new LinkedList<HelpTicket>();
		public static boolean overridehelp = false;
		public static boolean useMYSQLBackend = false;
		public static String db_host;
		public static String db_username;
		public static String db_password;
		public static String db_name;

	}

}
