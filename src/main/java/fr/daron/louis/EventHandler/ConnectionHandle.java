package fr.daron.louis.EventHandler;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mvplugins.multiverse.core.MultiverseCoreApi;

import fr.daron.louis.CustomPlayer;
import fr.daron.louis.Permissions;
import fr.daron.louis.Plugin;
import fr.daron.louis.configGame.configItem.ConfigItemMain;

public class ConnectionHandle implements Listener {

    private JavaPlugin jvPlugin;

    public ConnectionHandle(JavaPlugin plugin) {
        this.jvPlugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        List<CustomPlayer> playersConnected = ((Plugin) jvPlugin).getPlayers();
        for (CustomPlayer customP : playersConnected) {
            if (customP.getPerms() == Permissions.HOST) {
                customP.getPlayer().getInventory().setItem(0, ConfigItemMain.getConfigItem());
            }
            if (customP.getPlayer().getName().equals(p.getName())) {
                return;
            }
        }
        if(((Plugin) jvPlugin).getNbTeams() > 1){
            p.getInventory().setItem(8, ConfigItemMain.getTeamSelector());
        }
        CustomPlayer play = new CustomPlayer(p);
        if (!((Plugin) jvPlugin).getPlayers().contains(play)) {
            ((Plugin) jvPlugin).appendPlayer(play);
            p.setGameMode(GameMode.ADVENTURE);
            System.err.println(p.getGameMode());
            MultiverseCoreApi coreApi = MultiverseCoreApi.get();
            String lobbyName = ((Plugin) jvPlugin).getLobbyName();
            // System.err.println("-------------------------------" + lobbyName);
            coreApi.getDestinationsProvider().parseDestination("e:" + lobbyName + ":5,93,-5:90:0").peek(destination -> {
                coreApi.getSafetyTeleporter().to(destination).checkSafety(false).teleport(p);
            });
        }

    }
}
