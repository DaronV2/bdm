package fr.daron.louis.configGame.configMenu;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.daron.louis.Plugin;
import fr.daron.louis.configGame.configItem.ConfigItemMain;
import fr.daron.louis.configGame.configTeam.ConfigTeamMain;

public class ConfigMenuHandle implements Listener {

   private JavaPlugin jvPlugin;

   public ConfigMenuHandle(JavaPlugin jvPlugin) {
      this.jvPlugin = jvPlugin;
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getView().getTitle().equals("§8Configuration")) {
         event.setCancelled(true);

         if (event.getCurrentItem() == null) {
            return;
         }

         Player player = (Player) event.getWhoClicked();

         // System.err.println(teams.getItemMeta().getDisplayName());
         String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
         if (displayName.equals("§2+1")) {
            ((Plugin) jvPlugin).addTeam();
            changeDisplayNameTeams(event);
         } else if (displayName.equals("§4-1") && ((Plugin) jvPlugin).getNbTeams() > 1) {
            ((Plugin) jvPlugin).removeTeam();
            changeDisplayNameTeams(event);
         }
         if (((Plugin) jvPlugin).getNbTeams() > 1) {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player p : players) {
               p.getInventory().setItem(8, ConfigTeamMain.getTeamSelector());
            }
         } else if (((Plugin) jvPlugin).getNbTeams() <= 1) {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for (Player p : players) {
               p.getInventory().remove(ConfigTeamMain.getTeamSelector());
            }
         }
         ScoreboardManager manager = Bukkit.getScoreboardManager();
         Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
         for (Team t : board.getTeams()) {
            for (String entry : t.getEntries()) {
               t.removeEntry(entry);
            }
         }
      }
   }

   private void changeDisplayNameTeams(InventoryClickEvent event) {
      ItemStack teams = event.getInventory().getItem(13);
      ItemMeta meta = teams.getItemMeta();
      int nbTeams = ((Plugin) jvPlugin).getNbTeams();
      meta.setDisplayName("§7Adjust teams number (" + nbTeams + ")");
      teams.setItemMeta(meta);
      event.getInventory().setItem(13, teams);
   }

}
