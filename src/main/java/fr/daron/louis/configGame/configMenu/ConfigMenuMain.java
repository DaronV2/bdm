package fr.daron.louis.configGame.configMenu;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.daron.louis.Plugin;

public class ConfigMenuMain {

   public static void openConfigMenu(Player player, JavaPlugin jvPlugin) {
      Inventory inv = Bukkit.createInventory(null, 27, "§8Configuration");
      int nbTeams = ((Plugin) jvPlugin).getNbTeams();

      ItemStack glasspane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
      for(int i = 0 ; i < 13; i++){
         inv.setItem(i, glasspane);
      }

      for(int i = 15 ; i < 27; i++){
         inv.setItem(i, glasspane);
      }

      ItemStack option1 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
      ItemMeta meta1 = option1.getItemMeta();
      meta1.setDisplayName("§7Ajuster le nombre d'équipes ("+nbTeams+")");
      meta1.setMaxStackSize(1);
      option1.setItemMeta(meta1);

      ItemStack removeTeamHead = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta metaRemove = (SkullMeta) removeTeamHead.getItemMeta();
      metaRemove.setDisplayName("§4-1");
      removeTeamHead.setItemMeta(metaRemove);

      ItemStack addTeamHead = new ItemStack(Material.PLAYER_HEAD);
      SkullMeta metaAdd = (SkullMeta) addTeamHead.getItemMeta();
      metaAdd.setDisplayName("§2+1");
      addTeamHead.setItemMeta(metaAdd);

      inv.setItem(13, option1);
      inv.setItem(12, removeTeamHead);
      inv.setItem(14, addTeamHead);

      player.openInventory(inv);
   }

}
