package fr.daron.louis.EventHandler;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
        // for (CustomPlayer customP : playersConnected) {
        //     if (customP.getPerms() == Permissions.HOST) {
        //         customP.getPlayer().getInventory().setItem(0, ConfigItemMain.getConfigItem());
        //     }
        //     if (customP.getPlayer().getName().equals(p.getName())) {
        //         return;
        //     }
        // }
        if(((Plugin) jvPlugin).getNbTeams() > 1){
            p.getInventory().setItem(8, ConfigItemMain.getTeamSelector());
        }
        CustomPlayer play = new CustomPlayer(p);
        if (!((Plugin) jvPlugin).getPlayers().contains(play)) {
            ((Plugin) jvPlugin).appendPlayer(play);
            p.setGameMode(GameMode.ADVENTURE);
            System.err.println(p.getGameMode());
            // MultiverseCoreApi coreApi = MultiverseCoreApi.get();
            String lobbyName = ((Plugin) jvPlugin).getLobbyName();
            World w = Bukkit.getWorld(lobbyName);
            Location loc = new Location(w, 10, 86, -10);
            p.teleport(loc);
        }

    }
}
