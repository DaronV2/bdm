package fr.daron.louis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.bukkit.BukkitWorld;

import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;

public class Lobby {

   private String name;

   private JavaPlugin plugin;

   public Lobby(String name, JavaPlugin plugin) {
      this.name = name;
      this.plugin = plugin;
   }

   public boolean generate() {
      WorldCreator worldCreat = new WorldCreator(name);
      String preset = "{\"layers\":[{\"block\":\"air\",\"height\":1}],\"biome\":\"the_void\"}";
      worldCreat.generateStructures(false);
      worldCreat.generatorSettings(preset);
      worldCreat.generator(new ChunkGenerator() {
         @Override
         public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
            return createChunkData(world);
         }
      });
      worldCreat.environment(World.Environment.NORMAL);
      World lobby = worldCreat.createWorld();
      if (lobby != null) {
         plugin.getLogger().severe("World created !");
         lobby.setPVP(false);
         lobby.setDifficulty(Difficulty.PEACEFUL);
      } else {
         plugin.getLogger().severe("Couldn't create lobby world !");
         return false;
      }
      pasteSchematic(new Location(lobby, 5, 93, -5), "teamspawn");
      return true;
   }

   private File extractSchematic(String internalPath, String outputName) {
      try {
         File outputFile = new File(plugin.getDataFolder(), outputName);

         if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

         if (!outputFile.exists()) {

            try (InputStream is = plugin.getResource(internalPath)) {

               if (is == null) {
                  plugin.getLogger().severe("Can't find schematic in plugin : " + internalPath);
                  return null;
               }

               Files.copy(is, outputFile.toPath());
            }
         }

         return outputFile;

      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private void pasteSchematic(Location location, String schematicName) {

      File schem = extractSchematic("schematics/" + schematicName + ".schem", schematicName + "_temp.schem");

      if (schem == null) {
         plugin.getLogger().severe("❌ Impossible d'extraire le schematic !");
         return;
      }

      try {
         ClipboardFormat format = ClipboardFormats.findByFile(schem);
         if (format == null) {
            plugin.getLogger().severe("❌ Format de schematic inconnu !");
            return;
         }

         Clipboard clipboard;
         try (FileInputStream fis = new FileInputStream(schem)) {
            clipboard = format.getReader(fis).read();
         }

         World bukkitWorld = location.getWorld();
         if (bukkitWorld == null) {
            plugin.getLogger().severe("❌ Monde null !");
            return;
         }

         com.sk89q.worldedit.world.World weWorld = new BukkitWorld(bukkitWorld);

         try (EditSession session = WorldEdit.getInstance().newEditSession(weWorld)) {

            Operation operation = new ClipboardHolder(clipboard).createPaste(session)
                  .to(BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ()))
                  .ignoreAirBlocks(true) // ⚠ recommandé
                  .build();

            Operations.complete(operation);
         }

         plugin.getLogger().info("✔ Schematic collé dans le monde : " + bukkitWorld.getName());

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
