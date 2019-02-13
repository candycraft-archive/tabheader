package de.pauhull.tabheader;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by Paul
 * on 26.11.2018
 *
 * @author pauhull
 */
public class TabHeader extends Plugin implements Listener {

    @Override
    public void onEnable() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            updateTablist(player);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            updateTablist(player, ProxyServer.getInstance().getOnlineCount() - 1);
        }
    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent event) {
        ProxyServer.getInstance().getScheduler().schedule(this, () -> {
            updateTablist(event.getPlayer());
        }, 50, TimeUnit.MILLISECONDS);
    }

    private void updateTablist(ProxiedPlayer player, int onlineCount) {

        String server = player.getServer() == null ? "Lädt..." : player.getServer().getInfo().getName();

        BaseComponent[] header = TextComponent.fromLegacyText("\n§d§lCandyCraft\n");
        BaseComponent[] footer = TextComponent.fromLegacyText("\n§c" + server + "§8 * §fshop.candycraft.de\n§d" + onlineCount + "§f Spieler online");

        player.setTabHeader(header, footer);
    }

    private void updateTablist(ProxiedPlayer player) {
        updateTablist(player, ProxyServer.getInstance().getOnlineCount());
    }

}
