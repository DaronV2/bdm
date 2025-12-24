package fr.daron.louis.configGame.configTeam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import fr.daron.louis.TeamUtils;

import fr.daron.louis.Plugin;

public class ConfigTeamMain {

   public static void openTeamMenu(Player player, JavaPlugin jvplugin) {
      int nbTeams = ((Plugin) jvplugin).getNbTeams();
      Inventory inv = Bukkit.createInventory(null, 9, "§0 Choissisez votre équipe");

      ScoreboardManager manager = Bukkit.getScoreboardManager();
      Scoreboard board = manager.getMainScoreboard();
      Set<Team> teams = board.getTeams();
      int teamNumber = 0;
      for (Team t : teams) {
         if (teamNumber < nbTeams) {
            Material bannerColor = TeamUtils.ChatColorToBanner(t.getColor());
            if (bannerColor != Material.WHITE_BANNER) {
               ItemStack banner = new ItemStack(bannerColor);
               ItemMeta meta = banner.getItemMeta();
               meta.setDisplayName("Équipe " + (teamNumber + 1));
               List<String> lore = new ArrayList<>();
               lore.add("Joueurs : ");
               if (t.getEntries().isEmpty()) {
                  lore.add("§c- Aucun joueur");
               } else {
                  for (String entry : t.getEntries()) {
                     lore.add("§f- " + entry);
                  }
               }
               meta.setLore(lore);
               NamespacedKey key = new NamespacedKey(jvplugin, "hidden_value");
               meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, t.getName());
               banner.setItemMeta(meta);
               inv.addItem(banner);
               teamNumber++;
            }
         }
      }
      player.openInventory(inv);

   }

}
