package fr.daron.louis;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.mvplugins.multiverse.core.MultiverseCoreApi;
import org.mvplugins.multiverse.core.utils.result.Attempt;
import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.WorldManager;
import org.mvplugins.multiverse.core.world.entity.EntitySpawnConfig;
import org.mvplugins.multiverse.core.world.options.CreateWorldOptions;
import org.mvplugins.multiverse.core.world.reasons.CreateFailureReason;

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

   private MultiverseCoreApi coreApi;

   private JavaPlugin plugin;

   public Lobby(String name, JavaPlugin plugin) {
      this.name = name;
      this.plugin = plugin;
      this.coreApi = MultiverseCoreApi.get();
   }

   public boolean generate() {
      WorldManager worldManager = this.coreApi.getWorldManager();
      // if(worldManager.getWorld(name) == null){
         String superflatPreset = "{\"layers\":[{\"block\":\"air\",\"height\":1}],\"biome\":\"the_void\"}";
         Attempt<LoadedMultiverseWorld, CreateFailureReason> result = worldManager
               .createWorld(CreateWorldOptions.worldName(this.name).worldType(WorldType.FLAT).generateStructures(false)
                     .generatorSettings(superflatPreset));

         LoadedMultiverseWorld mvWorld = result.get();
         mvWorld.setDifficulty(Difficulty.PEACEFUL);
         mvWorld.setGameMode(GameMode.ADVENTURE);
         mvWorld.setPvp(false);

         // ConfigurationSection conf = 
         // EntitySpawnConfig spawnConfig = EntitySpawnConfig.fromSection(null);

         // mvWorld.setEntitySpawnConfig(EntitySpawnConfig.);
         // mvWorld.setSpawnAnimals(false);
         if (!result.isSuccess())
            return false;

         Bukkit.getScheduler().runTaskLater(plugin, () -> pasteSchematic(new Location(0, 100, 0), "teamspawn"), 3L);

         return true;
      // }
      // return false;
      
   }

   private File extractSchematic(String internalPath, String outputName) {
      try {
         File outputFile = new File(plugin.getDataFolder(), outputName);

         if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

         if (!outputFile.exists()) {

            try (InputStream is = plugin.getResource(internalPath)) {

               if (is == null) {
                  plugin.getLogger().severe("Schematic introuvable dans le plugin : " + internalPath);
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
         plugin.getLogger().severe("Impossible d'extraire le schematic !");
         return;
      }
      try {
         ClipboardFormat format = ClipboardFormats.findByFile(schem);
         Clipboard clipboard = format.getReader(new FileInputStream(schem)).read();

         com.sk89q.worldedit.world.World weWorld = new BukkitWorld(Bukkit.getWorld(name));

         try (EditSession session = WorldEdit.getInstance().newEditSession(weWorld)) {

            ClipboardHolder holder = new ClipboardHolder(clipboard);

            Operation operation = holder.createPaste(session)
                  .to(BlockVector3.at(location.getX(), location.getY(), location.getZ())).ignoreAirBlocks(false)
                  .build();

            Operations.complete(operation);

            plugin.getLogger().info("✔ Schematic collé dans : " + name);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
