package me.prosl3nderman.prochatprefixsuffix;

import com.earth2me.essentials.Essentials;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ProChatPrefixSuffix extends JavaPlugin {

    public static ProChatPrefixSuffix plugin;

    public LuckPerms lpAPI = null;
    public Essentials ess = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        setupEssentials();
        setupLuckPermsAPI();

        getServer().getPluginManager().registerEvents(new OnChat(), plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupEssentials() {
        Plugin essentialsPlugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentialsPlugin.isEnabled() && (essentialsPlugin instanceof Essentials)) {
            this.ess = (Essentials) essentialsPlugin;
        } else {
            // Disable the plugin
            Bukkit.getLogger().log(Level.SEVERE, "Essentials not installed! Disabling NovscraftPvP.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void setupLuckPermsAPI() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null)
            lpAPI = provider.getProvider();
    }
}
