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

        public static ItemStack getTeamSelector() {
                ItemStack item = new ItemStack(Material.BLACK_BANNER);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName("§0Choose your team");
                meta.setLore(Arrays.asList("§7Players : \r- \r- \r -"));

                item.setItemMeta(meta);
                return item;

        }

}
