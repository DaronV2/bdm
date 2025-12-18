package fr.daron.louis.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.daron.louis.CustomPlayer;
import fr.daron.louis.Permissions;
import fr.daron.louis.Plugin;
import fr.daron.louis.configGame.configItem.ConfigItemMain;

public class SetHost implements CommandExecutor {

   private JavaPlugin jvPlugin;

   public SetHost(JavaPlugin plugin){
      this.jvPlugin = plugin;
   }

   
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (args.length == 0 || args.length >= 2) {
         sender.sendMessage("Usage : /sethost <playerName>");
         return false;
      }
      else if (args.length == 1){
         List<CustomPlayer> players = ((Plugin) jvPlugin).getPlayers();
         for (CustomPlayer p : players) {
            // sender.sendMessage(p.getPlayer().getName()+"--------"+ args[0]);
            if (args[0].equals(p.getPlayer().getName())){
               // sender.sendMessage("ca a fonctionné");
               p.setPerms(Permissions.HOST);
               p.getPlayer().getInventory().addItem(ConfigItemMain.getConfigItem());
            }
         }  
         return true;
      }
      return false;
   }
}
