package fr.daron.louis.configGame.configTeam;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.daron.louis.configGame.configBdm.ConfigBdmMain;

public class ConfigTeamHandle implements Listener {

   private JavaPlugin jvPlugin;

   public ConfigTeamHandle(JavaPlugin jvPlugin) {
      this.jvPlugin = jvPlugin;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      Action action = event.getAction();
      ItemStack item = event.getItem();
      Player p = event.getPlayer();
      if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
         return;
      }
      if (item == null) {
         return;
      }
      if (!item.hasItemMeta()) {
         return;
      }
      if (!item.getItemMeta().hasDisplayName()) {
         return;
      }
      if (!item.getItemMeta().getDisplayName().equals("§0Choose your team")) {
         return;
      }

      event.setCancelled(true);
      ConfigTeamMain.openTeamMenu(p, jvPlugin);
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event) {
      if (event.getView().getTitle().equals("§0Choose your team")) {
         if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
            return;
         }
         Player p = Bukkit.getPlayer(event.getWhoClicked().getName());
         String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
         int teamLimit = 3;
         if (displayName.contains("Team ")) {
            ItemStack item = event.getCurrentItem();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(jvPlugin, "hidden_value");
            //
            String teamName = pdc.get(key, PersistentDataType.STRING).toString();
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            Team team = scoreboard.getTeam(teamName);
            //
            Set<String> playersTeam = scoreboard.getTeam(teamName).getEntries();
            boolean inThisTeam = false;
            for (String entry : playersTeam){
               if (entry == p.getName()){
                  inThisTeam = true;
               }
            }
            if(team != null){
               if(p != null){
                  if(team.getEntries().size() <= teamLimit){
                     if (inThisTeam == false){
                        team.addEntry(p.getName());
                        p.getInventory().addItem(ConfigBdmMain.getBdmItem());
                        p.closeInventory();
                     } else {
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                     }
                  }else{
                     p.sendMessage("Team full !");
                  }
               } else {
                  System.err.println("No player clicked");
               }
            } else{
               System.err.println("Team doesn't exists");
            }
         }

      }
      event.setCancelled(true);
      return;
   }
}
