package fr.daron.louis;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TeamUtils {

  public static ChatColor stringToChatColor(String color) {
    switch (color) {
    case "red":
      return ChatColor.RED;
    case "blue":
      return ChatColor.BLUE;
    case "green":
      return ChatColor.GREEN;
    case "orange":
      return ChatColor.GOLD;
    case "purple":
      return ChatColor.LIGHT_PURPLE;
    default:
      return ChatColor.WHITE;
    }
  }

  public static Material ChatColorToBanner(ChatColor chatCol) {
    switch (chatCol) {
    case RED:
      return Material.RED_BANNER;
    case BLUE:
      return Material.BLUE_BANNER;
    case GREEN:
      return Material.GREEN_BANNER;
    case GOLD:
      return Material.ORANGE_BANNER;
    case LIGHT_PURPLE:
      return Material.PURPLE_BANNER;
    default:
      return Material.WHITE_BANNER;
    }
  }

}
