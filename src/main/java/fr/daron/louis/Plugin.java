package fr.daron.louis;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import fr.daron.louis.EventHandler.ConnectionHandle;
import fr.daron.louis.EventHandler.DisconnectHandle;
import fr.daron.louis.commands.SetHost;
import fr.daron.louis.commands.StartGame;
import fr.daron.louis.configGame.configItem.ConfigItemHandle;
import fr.daron.louis.configGame.configMenu.ConfigMenuHandle;
import fr.daron.louis.configGame.configTeam.ConfigTeamHandle;
import fr.daron.louis.TeamUtils;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

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
    this.lobbyName = "LobbyBDM38";
    setEvents();
    getCommand("sethost").setExecutor(new SetHost(this));
    getCommand("startgame").setExecutor(new StartGame(this));
    Lobby lobby = new Lobby(getLobbyName(), this);
    Boolean generated = lobby.generate();
    if (!generated) {
      generated = lobby.generate();
    }
    createTeams();
  }

  private void setEvents() {
    getServer().getPluginManager().registerEvents(new ConnectionHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigItemHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigMenuHandle(this), this);
    getServer().getPluginManager().registerEvents(new ConfigTeamHandle(this), this);
    getServer().getPluginManager().registerEvents(new DisconnectHandle(), this);
  }

  private void createTeams() {
    Map<String, Material> banners = new HashMap<>();
    banners.put("blue", Material.BLUE_BANNER);
    banners.put("red", Material.RED_BANNER);
    banners.put("green", Material.GREEN_BANNER);
    banners.put("orange", Material.ORANGE_BANNER);
    banners.put("purple", Material.PURPLE_BANNER);

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();

    for (Map.Entry<String, Material> entry : banners.entrySet()) {
      String teamName = entry.getKey();
      Team existingTeam = board.getTeam(teamName);
      if (existingTeam == null) {
        Team team = board.registerNewTeam(entry.getKey());
        team.setColor(TeamUtils.stringToChatColor(entry.getKey()));
        
        team.setAllowFriendlyFire(false);
      }
    }
  }

  public void onDisable() {
    LOGGER.info("bdm disabled");
  }

  public String getLobbyName() {
    return this.lobbyName;
  }

  public List<CustomPlayer> getPlayers() {
    return this.players;
  }

  public int getNbTeams() {
    return this.nbTeams;
  }

  public void addTeam() {
    this.nbTeams++;
  }

  public void removeTeam() {
    this.nbTeams--;
  }

  public void appendPlayer(CustomPlayer p) {
    this.players.add(p);
  }
}
