package fr.daron.louis.EventHandler;

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
import fr.daron.louis.Plugin;

public class ConnectionHandle implements Listener {

    private JavaPlugin jvPlugin;

    public ConnectionHandle(JavaPlugin plugin) {
        this.jvPlugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        CustomPlayer play = new CustomPlayer(p);
        ((Plugin) jvPlugin).appendPlayer(play);
        // System.err.println(p.getDisplayName()+"----------------------------");
        // p.setGameMode(GameMode.ADVENTURE);
        System.err.println(p.getGameMode());
        MultiverseCoreApi coreApi = MultiverseCoreApi.get();
        String lobbyName = ((Plugin) jvPlugin).getLobbyName();
        System.err.println("-------------------------------"+lobbyName);
        coreApi.getDestinationsProvider().parseDestination("e:"+lobbyName+":5,100,-5:90:0")
            .peek(destination -> {
                coreApi.getSafetyTeleporter().to(destination)
                .checkSafety(false)
                .teleport(p);
            });
        
    }
}
