package fr.daron.louis.configGame.configTeam;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import fr.daron.louis.Plugin;

public class ConfigTeamMain {
   
   public static void openTeamMenu(Player player, JavaPlugin jvplugin){
      int nbTeams = ((Plugin) jvplugin).getNbTeams();
      Inventory inv = Bukkit.createInventory(null, 9, "$0 Choissisez votre équipe");

      List<Material> banners = new ArrayList<Material>();
      banners.add(Material.BLUE_BANNER);
      banners.add(Material.RED_BANNER);
      banners.add(Material.GREEN_BANNER);
      banners.add(Material.ORANGE_BANNER);
      banners.add(Material.PURPLE_BANNER);

      for (int i = 0; i < nbTeams ; i++){
         ItemStack banner = new ItemStack(banners.get(i));
         ItemMeta meta = banner.getItemMeta();
         meta.setDisplayName("Équipe "+(i+1));
         banner.setItemMeta(meta);
         inv.setItem(i, banner);
      }
      player.openInventory(inv);
   }

}
