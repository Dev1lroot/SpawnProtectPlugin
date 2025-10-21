package fr.dev1lroot.mcplugins.spawnprotect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.block.Block;
import java.util.Iterator;

public class Main extends JavaPlugin implements Listener
{
    private int centerX;
    private int centerZ;
    private int protectedRadius;
    final String sPluginName = "SpawnProtect";

    // Функция запуска плагина
    @Override
    public void onEnable()
    {
        getLogger().info("The plugin has been enabled");

        // copy default config.yml from plugin
        saveDefaultConfig();

        // setting up positions and radius
        centerX = getConfig().getInt("center-x");
        centerZ = getConfig().getInt("center-z");
        protectedRadius = getConfig().getInt("protected-radius");

        // enabling game events override
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // TNT and e.t.c.
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event)
    {
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext())
        {
            Block block = it.next();
            if (isBlockProtected(block))
            {
                it.remove();
            }
        }
    }

    // Creeper explosions, Wither
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext())
        {
            Block block = it.next();
            if (isBlockProtected(block))
            {
                it.remove();
            }
        }
    }

    // Check whether block is protected or not
    private boolean isBlockProtected(Block block)
    {
        // If radius set to zero - skipping at all
        if(protectedRadius == 0) return false;

        // Getting block coordinates
        int x = block.getX();
        int z = block.getZ();

        // Distance from center point
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(z - centerZ, 2));

        // Check whether block is within protected radius
        return protectedRadius > distance;
    }

    // On plugin disabled
    @Override
    public void onDisable()
    {
        getLogger().info("The plugin has been disabled");
    }
}