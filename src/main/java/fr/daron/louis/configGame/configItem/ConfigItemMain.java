package fr.daron.louis.configGame.configItem;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfigItemMain {

        public static ItemStack getConfigItem() {
                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName("§9Setup Menu");
                meta.setLore(Arrays.asList("§7CRight click to open"));

                item.setItemMeta(meta);
                return item;
        }
}
