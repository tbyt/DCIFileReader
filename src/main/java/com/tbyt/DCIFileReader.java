package com.tbyt;

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.addon.AddonConfigRegistry;
import de.erdbeerbaerlp.dcintegration.common.addon.DiscordIntegrationAddon;
import de.erdbeerbaerlp.dcintegration.common.storage.CommandRegistry;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;


public class DCIFileReader implements DiscordIntegrationAddon, EventListener {
    static ReadCommandConfig cfg;
    DiscordIntegration discord;

    @Override
    public void load(DiscordIntegration dc) {
        cfg = AddonConfigRegistry.loadConfig(ReadCommandConfig.class, this);
        discord = dc;
        if (dc.getJDA() != null) {
            if (CommandRegistry.registerCommand(new ReadCommand()) && CommandRegistry.registerCommand(new FileReadSettings()))
                dc.getJDA().addEventListener(this);
        }
        DiscordIntegration.LOGGER.info("DCIFileReader Addon loaded");
    }

    @Override
    public void reload() {
        cfg = AddonConfigRegistry.loadConfig(ReadCommandConfig.class, this);
        DiscordIntegration.LOGGER.info("DCIFileReader Addon reloaded");
    }

    @Override
    public void unload(DiscordIntegration dc) {
        if (dc.getJDA() != null) {
            dc.getJDA().removeEventListener(this);
        }
        DiscordIntegration.LOGGER.info("DCIFileReader Addon unloaded");
    }

    @Override
    public void onEvent(GenericEvent genericEvent) {

    }
}
