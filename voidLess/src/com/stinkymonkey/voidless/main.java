package com.stinkymonkey.voidless;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		loadConfig();
		setUpConfig();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public void loadConfig() {
		File pluginFolder = new File("plugins" + System.getProperty("file.separator") + this.getDescription().getName());
		if (pluginFolder.exists() == false) {
    		pluginFolder.mkdir();
    		System.out.println("MADE FOLDER FOR VOIDLESS");
    	}
		
		File configFile = new File("plugins" + System.getProperty("file.separator") + this.getDescription().getName() + System.getProperty("file.separator") + "config.yml");
		if (configFile.exists() == false) {
    		this.saveDefaultConfig();
    		System.out.println("CREATED A CONFIG FOR VOIDLESS");
		}
    	
    	try {
    		this.getConfig().load(configFile);
    		System.out.println("LOADED CONFIG FOR VOIDLESS");
    	} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILED TO LOAD CONFIG FOR VOIDLESS");
    	}
	}
	int n = 0;
	List<String> worldNames = new ArrayList<String>();
	List<Integer> worldX = new ArrayList<Integer>();
	List<Integer> worldY = new ArrayList<Integer>();
	List<Integer> worldZ = new ArrayList<Integer>();
	List<String> worldMessage = new ArrayList<String>();
	List<String> worldCmd = new ArrayList<String>();
	public void setUpConfig() {
		while (this.getConfig().contains("world" + Integer.toString(n))) {
			worldNames.add(this.getConfig().getString("world" + Integer.toString(n) + ".world"));
			worldX.add(this.getConfig().getInt("world" + Integer.toString(n) + ".x"));
			worldY.add(this.getConfig().getInt("world" + Integer.toString(n) + ".y"));
			worldZ.add(this.getConfig().getInt("world" + Integer.toString(n) + ".z"));
			worldMessage.add(this.getConfig().getString("world" + Integer.toString(n) + ".message"));
			worldCmd.add(this.getConfig().getString("world" + Integer.toString(n) + ".message"));
			n++;
		}
	}
	
	@EventHandler
	public void onVoid(EntityDamageEvent event) {
		Entity e = event.getEntity();
		if (event.getCause().equals(DamageCause.VOID)) {
			if (e instanceof Player) {
					int index = worldNames.indexOf(e.getWorld().getName());
					Location spawnPoint = new Location(e.getWorld(), (double) worldX.get(index), (double) worldY.get(index), (double) worldZ.get(index));
					event.setCancelled(true);
					if (worldCmd.get(index).equalsIgnoreCase("")) {
						e.teleport(spawnPoint);
						e.sendMessage(ChatColor.translateAlternateColorCodes('&', worldMessage.get(index)));
					} else {
						Player p = (Player) e;
						p.performCommand(worldCmd.get(index));
						e.sendMessage(ChatColor.translateAlternateColorCodes('&', worldMessage.get(index)));
					}
					
				}
			}
		}
	}

