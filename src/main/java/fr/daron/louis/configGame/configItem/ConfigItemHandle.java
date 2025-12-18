package fr.daron.louis.configGame.configItem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import fr.daron.louis.configGame.configMenu.ConfigMenuMain;

public class ConfigItemHandle implements Listener {

   private JavaPlugin jvPlugin;

   public ConfigItemHandle(JavaPlugin jvPlugin) {
      this.jvPlugin = jvPlugin;
   }

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      Action action = event.getAction();
      ItemStack item = event.getItem();
      Player p = event.getPlayer();
      if (item != null) {
         if (item.getItemMeta().getDisplayName().equals("§9Menu de configuration")) {
            if (event.getItem() == null) {
               return;
            } else if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
               return;
            } else if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
               return;
            }

            event.setCancelled(true);
            // p.sendMessage("§aMenu de configuration ouvert !");
            ConfigMenuMain.openConfigMenu(p, jvPlugin);
         }
      }
   }

}
