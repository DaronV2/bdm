package fr.daron.louis.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.daron.louis.CustomPlayer;
import fr.daron.louis.Permissions;
import fr.daron.louis.Plugin;

public class StartGame implements CommandExecutor {

   private JavaPlugin jvPlugin;

   public StartGame(JavaPlugin jvPlugin) {
      this.jvPlugin = jvPlugin;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      List<CustomPlayer> players = ((Plugin) jvPlugin).getPlayers();
      Player p = Bukkit.getPlayer(sender.getName());
      boolean isHost = false;
      for (CustomPlayer pl : players) {
         if (pl.getPlayer() == p && pl.getPerms() == Permissions.HOST) {
            isHost = true;
         }
      }
      if (checkTeams() == false && isHost == true) {
         p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
         p.sendMessage("Team aren't full");
      }
      // else if (isHost == true){
      // p.sendMessage("starting...");
      // }
      displayCount(() -> {
         p.sendMessage("test");
      });
      return false;
   }

   public boolean checkTeams() {
      ScoreboardManager manager = Bukkit.getScoreboardManager();
      Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
      Set<Team> teams = board.getTeams();
      for (Team t : teams) {
         int nbInTeam = 0;
         for (String entry : t.getEntries()) {
            Player p = Bukkit.getPlayer(entry);
            if (p != null) {
               nbInTeam++;
            }
         }
         if (nbInTeam != 0 || nbInTeam != 3) {
            return false;
         }
      }
      return true;
   }

   public void displayCount(Runnable onFinish) {
      new BukkitRunnable() {
         int seconds = 10;

         @Override
         public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
               player.sendTitle("§cDébut dans", "§e" + seconds + " secondes", 0, 20, 0);
               player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
            }
            if (seconds <= 0) {
               Bukkit.broadcastMessage("§aLa partie commence !");
               this.cancel(); // ✅ MAINTENANT ÇA MARCHE
               return;
            }
            seconds--;
         }
      }.runTaskTimer(jvPlugin, 0L, 20L);
   }
}
