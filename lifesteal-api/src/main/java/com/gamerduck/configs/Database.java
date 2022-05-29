package com.gamerduck.configs;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import com.gamerduck.objects.LifeStealServer;

public class Database {

    private Connection connection;

    public Database(Plugin main) throws Exception {
        Class.forName("org.sqlite.JDBC").newInstance();
        connection = DriverManager.getConnection("jdbc:sqlite:" + new File(main.getDataFolder(), "database.db"));
        
        createTable("CREATE TABLE IF NOT EXISTS heartdata (UUID VARCHAR(36) UNIQUE, HEARTS DOUBLE(64,2));");
    }

    public Database(Plugin main, boolean reconnect, String host, String database, String username, String password, int port) throws Exception {
        Properties info = new Properties();
        info.setProperty("useSSL", "true");

        if (reconnect) {
            info.setProperty("autoReconnect", "true");
        }
        info.setProperty("trustServerCertificate", "true");
        info.setProperty("user", username);
        info.setProperty("password", password);

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, info);
        
        createTable("CREATE TABLE IF NOT EXISTS heartdata (UUID VARCHAR(36) UNIQUE, HEARTS DOUBLE(64,2));");
    }

    public void createTable(String sqlURL) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sqlURL)) {
            statement.executeUpdate();
        }
    }
    
    public Connection connection() {return connection;}
    
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void storeHearts(String UUID, Double hearts) {
    	LifeStealServer.a().getExecutor().execute(() -> {
	    	try (PreparedStatement insert = connection.prepareStatement("INSERT OR REPLACE INTO heartdata VALUES (?, ?)")) {
	            insert.setString(1, UUID);
	            insert.setDouble(2, hearts);
	            insert.executeUpdate();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
    	});
    }
    
    public void retrieveHearts(String UUID, Consumer<Double> amount) {
    	LifeStealServer.a().getExecutor().execute(() -> {
	    	Double a = -1d;
	    	if (hasData(UUID, "heartdata")) {
		    	try (PreparedStatement select = connection.prepareStatement("SELECT HEARTS FROM heartdata WHERE UUID = ?")) {
		            select.setString(1, UUID);
		            try (ResultSet result = select.executeQuery()) {
		                if (result.next()) {
		                    a = result.getDouble(1);
		                }
		            }
		        } catch (SQLException ex) {ex.printStackTrace();}
	    	}
	    	amount.accept(a);
    	});
    }
    
    public boolean hasData(String UUID, String table) {
    	try (PreparedStatement select = connection.prepareStatement("SELECT UUID FROM " + table + " WHERE UUID = ?")) {
            select.setString(1, UUID);
            try (ResultSet result = select.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    } 
    
    public ArrayList<String> getAllUUIDS() {
    	ArrayList<String> uuids = new ArrayList<String>();
    	try (PreparedStatement select = connection.prepareStatement("SELECT UUID FROM heartdata")) {
            try (ResultSet result = select.executeQuery()) {
                while(result.next()) {
                	uuids.add(result.getString("UUID"));
                }
            }
        } catch (SQLException ex) {ex.printStackTrace();}
    	return uuids;
    }
    

}
