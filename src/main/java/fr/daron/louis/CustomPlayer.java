package fr.daron.louis;

import org.bukkit.entity.Player;

public class CustomPlayer {
   
   private Permissions perms;

   private Player player;

   public CustomPlayer(Player player){
      this.perms = Permissions.NORMAL;
      this.player = player;
   }

   public void setPerms(Permissions perms){
      this.perms = perms;
      if(perms == Permissions.HOST){
         player.sendMessage("Tes host la !");
      }
   }

   public Permissions getPerms(){
      return perms;
   }

   public Player getPlayer(){
      return this.player;
   }

}
