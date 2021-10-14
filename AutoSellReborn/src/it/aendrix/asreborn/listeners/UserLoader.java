package it.aendrix.asreborn.listeners;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import it.aendrix.asreborn.files.Config;
import it.aendrix.asreborn.main.Main;

public class UserLoader implements Listener {
	
	private static Config cfg = Main.getCfg();
	private static Statement sql = Main.getSQL();
	
	private static HashMap<String, User> users = new HashMap<String, User>();
	
	public static User getUser(String user) {
		return users.get(user);
	}
	
	public static void addUser(User user) {
		if (!users.containsKey(user.getName()))
			users.put(user.getName(), user);
		else users.replace(user.getName(), user);
	}
	
	public static void loadAll() {
		if (cfg.isUsemysql()) {
			//DATABASE
			ResultSet rs;
			try {
				rs = sql.executeQuery("SELECT * FROM "+cfg.getTable()+"");
				
				while(rs.next())
					addUser(new User(rs.getString("NAME"), rs.getInt("MULTIPLIER"), rs.getBoolean("AUTOSELL"), 
							rs.getLong("SELLED"), rs.getLong("SELLEDMONEY")));
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else {
			//FILE NON ORA
			
			
		}
	}
	
	public static void saveAll() {
		if (cfg.isUsemysql()) {
			//DATABASE
			try {
				sql.executeUpdate("TRUNCATE "+cfg.getTable());
			} catch (SQLException e) {}
			
			for (String u : users.keySet()) {
				User user = getUser(u);
				try {
					sql.executeUpdate("INSERT INTO "+cfg.getTable()+"(NAME,MULTIPLIER,AUTOSELL,SELLED,SELLEDMONEY"
							+ ") VALUES "
							+ "('"+user.getName()+"',"+user.getMultiplier()+","+user.isAutosell()+","+user.getSelled()+","+user.getSelledmoney()+")");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}else {
			//FILE NON ORA
			
			
		}
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (getUser(p.getName())==null)
			addUser(new User(p.getName(),cfg.getBasemultiplier(),false,0,0));
		
	}
	
	
}
