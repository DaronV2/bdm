package fr.daron.louis.configGame.configTeam;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigTeamHandle implements Listener {

   private JavaPlugin jvPlugin;

   public ConfigTeamHandle(JavaPlugin jvPlugin) {
      this.jvPlugin = jvPlugin;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      event.setCancelled(true);
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
      if (!item.getItemMeta().getDisplayName().equals("§0 Choissisez votre équipe")) {
         return;
      }

      ConfigTeamMain.openTeamMenu(p, jvPlugin);
   }

   @EventHandler
   public void onInventoryClick(InventoryClickEvent event){
      if(event.getView().getTitle().equals("$0 Choissisez votre équipe")){
         System.err.println("oue");
         event.setCancelled(true);
         if (event.getCurrentItem() == null) {
            return;
         }
         Player player = (Player) event.getWhoClicked();
         String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
         if( displayName.contains("Équipe ")){
            System.err.println(displayName.charAt(displayName.length()));
         }

      }
      return;
   }
}
