package fr.dev1lroot.mcplugins.spawnprotect;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
    final String sPluginName = "SpawnProtect";

    // Функция запуска плагина
    @Override
    public void onEnable()
    {
        getLogger().info("The plugin has been enabled");

        // Перегружаем игровые события
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    // Игрок может ломать всё — ничего не отменяем
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        // просто заглушка для логики — но ничего не блокируем
    }

    // Отобразить красивое сообщение
    private void message(String string)
    {
        // Префикс
        Component msg = Component.text("[" + sPluginName + "] ", NamedTextColor.GOLD)
                .append(Component.text(string, NamedTextColor.WHITE));

        Bukkit.getServer().sendMessage(msg);
    }

    // Взрывы блоков (TNT, фейерболы, и т.д.)
    @EventHandler
    public void onBlockExplode(BlockExplodeEvent event)
    {
        boolean bHasProtectedBlocks = false;

        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext())
        {
            Block block = it.next();
            if (isBlockProtected(block))
            {
                it.remove(); // Убираем блок из списка взрыва
                bHasProtectedBlocks = true; // Отмечаем что была попытка уничтожить защищенный блок
            }
        }

        if(bHasProtectedBlocks)
        {
            message("блоки в пределах спавна не уничтожаются взрывами"); // Убрать на релизе чтобы не спамить
        }
    }

    // Взрывы от существ (в том числе визер, крипер, TNTMinecart)
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        boolean bHasProtectedBlocks = false;

        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext())
        {
            Block block = it.next();
            if (isBlockProtected(block))
            {
                it.remove(); // Блок не будет уничтожен
                bHasProtectedBlocks = true; // Отмечаем что была попытка уничтожить защищенный блок
            }
        }

        if(bHasProtectedBlocks)
        {
            message("блоки в пределах спавна не уничтожаются взрывами"); // Убрать на релизе чтобы не спамить
        }
    }

    // Проверяем защищен ли блок зоной спавна
    private boolean isBlockProtected(Block block)
    {
        int x = block.getX();
        int z = block.getZ();

        // Вычисляем реальное расстояние от точки (0, 0)
        double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

        // Проверяем радиус 1000 блоков
        return 1000 > distance;
    }

    // Функция отключения плагина
    @Override
    public void onDisable()
    {
        getLogger().info("The plugin has been disabled");
    }
}