package fr.daron.louis.configGame.configItem;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfigItem {

   public static ItemStack getConfigItem() {
    ItemStack item = new ItemStack(Material.NETHER_STAR);
    ItemMeta meta = item.getItemMeta();

    meta.setDisplayName("§9Menu de configuration");
    meta.setLore(Arrays.asList(
            "§7Clic droit pour ouvrir",
            "§7le menu"
    ));

    item.setItemMeta(meta);
    return item;
}

}
