package com.gamerduck.configs;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class values {
	
	public static Boolean MYSQL_ENABLED, MYSQL_AUTORECONNECT, DEFAULT_LOSE_HEARTS_ON_NON_PLAYER_DEATH, TAB_ENABLED, HEARTCANASTER_ENABLED, SHOULD_DAMAGE_SCALE_WITH_HEALTH;
	public static String MYSQL_HOST, MYSQL_DATABASE, MYSQL_USERNAME, MYSQL_PASSWORD, DEFAULT_CONVERT_FROM, MESSAGES_NOPERMISSIONS, MESSAGES_PLAYER_NOT_ONLINE, 
						 MESSAGES_NOT_A_NUMBER, MESSAGES_HEARTS_SET, MESSAGES_HEARTS_LOST, MESSAGES_HEARTS_GAINED, HEARTCANASTER_RECIPE_TYPE, 
						 HEARTCANASTER_ITEM_DISPLAYNAME, HEARTCANASTER_ITEM_MATERIAL;
	public static Integer MYSQL_PORT, HEARTCANASTER_ITEM_AMOUNT;
	public static Double DEFAULT_HEART_AMOUNT, DEFAULT_HEARTS_LOST_ON_DEATH, DEFAULT_HEARTS_GAINED_ON_KILL, DEFAULT_HEARTS_ZEROED_AMOUNT, HEARTCANASTER_HEARTS_AMOUNT;
	public static List<String> DEFAULT_ZEROED_COMMANDS, TAB_COLORS, HEARTCANASTER_RECIPE_CONTENTS, HEARTCANASTER_RECIPE_SHAPE, HEARTCANASTER_ITEM_LORE;
	
	private static FileConfiguration config;
	
	public static void load(FileConfiguration cf) {
		config = cf;
		MYSQL_ENABLED = getBoolean("MySQL.Enabled");
		if (MYSQL_ENABLED) {
			MYSQL_AUTORECONNECT = getBoolean("MySQL.AutoReconnect");
			MYSQL_HOST = getString("MySQL.Host");
			MYSQL_DATABASE = getString("MySQL.Database");
			MYSQL_USERNAME = getString("MySQL.Username");
			MYSQL_PASSWORD = getString("MySQL.Password");
			MYSQL_PORT = getInteger("MySQL.Port");
		}
		DEFAULT_CONVERT_FROM = getString("Defaults.ConvertFrom");
		SHOULD_DAMAGE_SCALE_WITH_HEALTH = getBoolean("ShouldDamageScaleWithHealth");
		DEFAULT_HEART_AMOUNT = getDouble("Defaults.StartHeartAmount");
		DEFAULT_LOSE_HEARTS_ON_NON_PLAYER_DEATH = getBoolean("Defaults.LoseHeartsOnNonPlayerDeath");
		DEFAULT_HEARTS_LOST_ON_DEATH = getDouble("Defaults.HeartsLostOnDeath");
		DEFAULT_HEARTS_GAINED_ON_KILL = getDouble("Defaults.HeartsGainedOnKill");
		DEFAULT_HEARTS_ZEROED_AMOUNT = getDouble("Defaults.HeartsZeroedAmount");
		DEFAULT_ZEROED_COMMANDS = getStringList("Defaults.CommandsAfterZeroing");
		MESSAGES_NOPERMISSIONS = getString("Messages.NoPermissions");
		MESSAGES_PLAYER_NOT_ONLINE = getString("Messages.PlayerNotOnline");
		MESSAGES_NOT_A_NUMBER = getString("Messages.NotANumber");
		MESSAGES_HEARTS_SET = getString("Messages.HeartsSet");
		MESSAGES_HEARTS_LOST = getString("Messages.HeartsLost");
		MESSAGES_HEARTS_GAINED = getString("Messages.HeartsGained");
		
		TAB_ENABLED = getBoolean("TAB.Enabled");
		if (TAB_ENABLED) TAB_COLORS = getStringList("TAB.Colors");
		
		HEARTCANASTER_ENABLED = getBoolean("HeartCanaster.Enabled");
		if (HEARTCANASTER_ENABLED == true) {
			HEARTCANASTER_RECIPE_TYPE = getString("HeartCanaster.Recipe.Type");	
			HEARTCANASTER_RECIPE_CONTENTS = getStringList("HeartCanaster.Recipe.Contents");
			if (HEARTCANASTER_RECIPE_TYPE.equalsIgnoreCase("Shaped")) {HEARTCANASTER_RECIPE_SHAPE = getStringList("HeartCanaster.Recipe.Shape");}
			HEARTCANASTER_ITEM_DISPLAYNAME = getString("HeartCanaster.Item.DisplayName");
			HEARTCANASTER_ITEM_MATERIAL = getString("HeartCanaster.Item.Material");
			HEARTCANASTER_ITEM_AMOUNT = getInteger("HeartCanaster.Item.Amount");
			HEARTCANASTER_ITEM_LORE = getStringList("HeartCanaster.Item.Lore");
			HEARTCANASTER_HEARTS_AMOUNT = getDouble("HeartCanaster.AmountGive");
		}
	}
	
	private static String getString(String s) {return config.getString(s);}
	private static Boolean getBoolean(String s) {return config.getBoolean(s);}
	private static Integer getInteger(String s) {return config.getInt(s);}
	private static Double getDouble(String s) {return config.getDouble(s);}
	private static List<String> getStringList(String s) {return config.getStringList(s);}
	
}
