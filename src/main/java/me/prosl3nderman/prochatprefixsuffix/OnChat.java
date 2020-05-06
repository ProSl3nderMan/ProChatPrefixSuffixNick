package me.prosl3nderman.prochatprefixsuffix;

import com.earth2me.essentials.Essentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.logging.Level;

public class OnChat implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        e.setCancelled(true);

        ChatColor chatColor = ChatColor.WHITE;
        String divider = ChatColor.translateAlternateColorCodes('&', "&7»");
        sendMessage(divider, e.getMessage(), p, chatColor);
    }

    private void sendMessage(String divider, String msg, Player p, ChatColor chatColor) {
        LuckPerms lpAPI = ProChatPrefixSuffix.plugin.lpAPI;
        User user = lpAPI.getUserManager().getUser(p.getUniqueId());

        String prefix = "";
        String suffix = "";

        QueryOptions queryOptions = lpAPI.getContextManager().getQueryOptions(p);
        CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);

        if (metaData.getPrefix() != null)
            prefix = metaData.getPrefix().equalsIgnoreCase("null") ? "" : metaData.getPrefix();
        if (metaData.getSuffix() != null)
            suffix = metaData.getSuffix().equalsIgnoreCase("null") ? "" : metaData.getSuffix() + " ";

        Essentials ess = ProChatPrefixSuffix.plugin.ess;
        String name;
        if (ess.getUser(p.getName()).getNickname() == null || ess.getUser(p.getName()).getNickname().equalsIgnoreCase(p.getName()))
            name = ChatColor.GRAY + p.getName();
        else
            name = ChatColor.translateAlternateColorCodes('&', ProChatPrefixSuffix.plugin.ess.getUser(p.getUniqueId()).getNickname());

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix) + name + " " + ChatColor.translateAlternateColorCodes('&', "&r" + suffix) + divider + " " + chatColor + msg);
        Bukkit.getLogger().log(Level.INFO, ChatColor.translateAlternateColorCodes('&', prefix) +  name + " " + suffix + divider + " "  + chatColor + msg);
    }
}
