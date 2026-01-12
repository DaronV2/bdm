package fr.daron.louis.configGame.configBdm;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfigBdmMain {

   public static ItemStack getBdmItem() {
      ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
      ItemMeta meta = item.getItemMeta();

      meta.setDisplayName("Choose your role");
      meta.setLore(Arrays.asList("§7CRight click to open"));

      item.setItemMeta(meta);
      return item;
   }

}
