package com.gamerduck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gamerduck.commands.LifeStealCommand;
import com.gamerduck.commands.WithdrawCommand;
import com.gamerduck.configs.FileResClassLoader;
import com.gamerduck.configs.UTF8PropertiesControl;
import com.gamerduck.crafting.HeartCanaster;
import com.gamerduck.crafting.HeartCanasterUse;
import com.gamerduck.listeners.DeathListener;
import com.gamerduck.listeners.EndCrystalPlaceListener;
import com.gamerduck.listeners.LastDamagerListener;
import com.gamerduck.listeners.ScaleDamageListener;
import com.gamerduck.objects.LifeStealServer;
import com.google.common.io.Files;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public class LifeStealMain extends JavaPlugin {
	
	@Getter static LifeStealMain instance;
	@Getter NamespacedKey canasterKey;
	@Getter LifeStealServer lifeStealServer;
	File file;
	@Getter FileConfiguration config;
	@Getter HeartCanaster canaster;
	@Getter ResourceBundle messagesBundle;
	
	@Override
	public void onEnable() {
		instance = this;
		loadConfigs();
		lifeStealServer = new LifeStealServer(getServer(), this, config, messagesBundle);
		TabExecutor cmd = new LifeStealCommand();
		getCommand("lifesteal").setExecutor(cmd);
		getCommand("lifesteal").setTabCompleter(cmd);
		canasterKey = new NamespacedKey(LifeStealMain.a(), "heart_canaster");
		canaster = new HeartCanaster(config);
		getServer().getPluginManager().registerEvents(new HeartCanasterUse(config), this);
		if (config.getBoolean("Defaults.ShouldWithdrawCommandExist")) getCommand("withdraw").setExecutor(new WithdrawCommand(config));
		if (config.getBoolean("HeartCanaster.RecipeEnabled")) canaster.loadRecipe(config);
		if (!config.getBoolean("Defaults.ShouldDamageScaleWithHealth")) getServer().getPluginManager().registerEvents(new ScaleDamageListener(), this);
		if (config.getBoolean("Defaults.ShouldKeepLastPlayerAsDamager")) getServer().getPluginManager().registerEvents(new LastDamagerListener(), this);
		getServer().getPluginManager().registerEvents(new DeathListener(config), this);
		getServer().getPluginManager().registerEvents(new EndCrystalPlaceListener(this), this);
	}
	
	@Override
	public void onDisable() {
		lifeStealServer.onDisable(this);
	}
	
	public static LifeStealMain a() {return instance;}
	
	private void loadConfigs() {
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			configFile.getParentFile().mkdir();
			saveResource("config.yml", false);
		}
		config = new YamlConfiguration();
		try { config.load(configFile); }
		 catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }

		File messagesFile = new File(getDataFolder(), "messages.properties");
		if (!messagesFile.exists()) {
			messagesFile.getParentFile().mkdir();
			saveResource("messages.properties", false);
		} else {
			try {updateMessages();}
			catch (IOException | URISyntaxException e) {e.printStackTrace();}
		}
		messagesBundle = ResourceBundle.getBundle("messages", Locale.ENGLISH, new FileResClassLoader(this.getClass().getClassLoader(), this), new UTF8PropertiesControl());
	}
	
	private void updateMessages() throws IOException, URISyntaxException {
		File tempFile = new File(getDataFolder(), "messages.properties");
		File tempCopy = new File(getDataFolder(), "messages_temp.properties");
		List<String> tempList = Files.readLines(getFileFromResourceAsStream("messages.properties", tempCopy), StandardCharsets.UTF_8);
		HashMap<String, String> masterlist = new HashMap<String, String>();
		tempList.stream().forEachOrdered(s -> {masterlist.put(s.split("=")[0], s);});
		tempCopy.delete();

		HashMap<String, String> clientlist = new HashMap<String, String>();
		tempList = Files.readLines(tempFile, StandardCharsets.UTF_8);
		tempList.stream().forEachOrdered(s -> clientlist.put(s.split("=")[0], s));

		List<String> missingKeys = masterlist.keySet().stream().filter(st -> !clientlist.containsKey(st)).collect(Collectors.toList());
		missingKeys.stream().forEachOrdered(st -> clientlist.put(st, masterlist.get(st)));

		List<String> excessKeys = clientlist.keySet().stream().filter(st -> !masterlist.containsKey(st)).collect(Collectors.toList());
		excessKeys.stream().forEachOrdered(st -> clientlist.remove(st));

		tempFile.delete();
		tempFile.createNewFile();
		FileWriter fw = new FileWriter(tempFile);
        BufferedWriter out = new BufferedWriter(fw);
        clientlist.keySet().stream().forEachOrdered(t -> {
			try {out.write(clientlist.get(t)); out.newLine();}
			catch (IOException e) {e.printStackTrace();}
		});
        out.flush();
        out.close();
	}

    private File getFileFromResourceAsStream(String fileName, File file) throws IOException {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        try (OutputStream output = new FileOutputStream(file, false)) {
            inputStream.transferTo(output);
        }
        return file;
    }
	
	public void saveConfig() {
		try {config.save(file);}
		catch (IOException e) {Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");}
	}
	public void reloadConfig() {config = YamlConfiguration.loadConfiguration(file);}
	
}
