package fr.daron.louis;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.WorldManager;
import org.mvplugins.multiverse.core.world.options.CreateWorldOptions;

import fr.daron.louis.EventHandler.ConnectionHandle;
import fr.daron.louis.EventHandler.DisconnectHandle;
import fr.daron.louis.commands.SetHost;
import fr.daron.louis.configGame.configItem.ConfigItemHandle;
import fr.daron.louis.configGame.configMenu.ConfigMenuHandle;
import fr.daron.louis.configGame.configTeam.ConfigTeamHandle;

import org.bukkit.plugin.java.JavaPlugin;

/*
 * bdm java plugin
 */
public class Plugin extends JavaPlugin {
  private static final Logger LOGGER = Logger.getLogger("bdm");

  private String lobbyName;

  private List<CustomPlayer> players;

  private int nbTeams;

  private boolean isLobbyCreated;

  public void onEnable() {
    this.nbTeams = 1;
    this.players = new ArrayList<CustomPlayer>();
    this.lobbyName = "LobbyBDM32";
    setEvents();
    getCommand("sethost").setExecutor(new SetHost(this));
    Lobby lobby = new Lobby(getLobbyName(), this);
    Boolean generated = lobby.generate();
    if (!generated){
      generated = lobby.generate();
    }
  }

  private void setEvents(){
    getServer().getPluginManager().registerEvents(new ConnectionHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigItemHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigMenuHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigTeamHandle(this), this);
    getServer().getPluginManager().registerEvents(new DisconnectHandle(), this);
  }

  public void onDisable() {
    LOGGER.info("bdm disabled");
  }

  public String getLobbyName(){
    return this.lobbyName;
  }

  public List<CustomPlayer> getPlayers(){
    return this.players;
  }

  public int getNbTeams(){
    return this.nbTeams;
  }

  public void addTeam(){
    this.nbTeams++;
  }

  public void removeTeam(){
    this.nbTeams--;
  }

  public void appendPlayer(CustomPlayer p){
    this.players.add(p);
  }
}
