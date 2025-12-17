package fr.daron.louis.configGame.configItem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ConfigItemHandle implements Listener {

   @EventHandler
   public void onInteract(PlayerInteractEvent event) {
      if (event.getItem() == null)
         return;

      Action action = event.getAction();
      if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
         return;

      ItemStack item = event.getItem();
      if (!item.hasItemMeta())
         return;
      if (!item.getItemMeta().hasDisplayName())
         return;

      if (!item.getItemMeta().getDisplayName().equals("§9Menu de configuration"))
         return;

      event.setCancelled(true);

      event.getPlayer().sendMessage("§aMenu de configuration ouvert !");
   }

}
