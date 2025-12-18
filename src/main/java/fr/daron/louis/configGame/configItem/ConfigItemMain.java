package fr.daron.louis.configGame.configItem;

import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfigItemMain {

        public static ItemStack getConfigItem() {
                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName("§9Menu de configuration");
                meta.setLore(Arrays.asList("§7Clic droit pour ouvrir", "§7le menu"));

                item.setItemMeta(meta);
                return item;
        }

        public static ItemStack getTeamSelector() {
                ItemStack item = new ItemStack(Material.BLACK_BANNER);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName("§0 Choissisez votre équipe");
                meta.setLore(Arrays.asList("§7Joueurs : \r- \r- \r -"));

                item.setItemMeta(meta);
                return item;

        }

}
