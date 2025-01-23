package com.tbyt;

import de.erdbeerbaerlp.dcintegration.common.discordCommands.DiscordCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public class FileReadSettings extends DiscordCommand {

    protected FileReadSettings()
    {
        super("filereader", "Commands that don't need input from user.");
        SubcommandData whitelistSubCommand = new SubcommandData("whitelist", DCIFileReader.cfg.whitelistCommandDescription);
        SubcommandData permissionsSubCommand = new SubcommandData("permissions",DCIFileReader.cfg.permissionsCommandDescription);
        super.addSubcommands(whitelistSubCommand,permissionsSubCommand);
    }

    public boolean isWhitelisted() {
        return DCIFileReader.cfg.whitelistNamesOnly;
    }

    @Override
    public boolean adminOnly() {
        return DCIFileReader.cfg.adminOnly;
    }

    public boolean isDirectoryEscalationAllowed() {
        return DCIFileReader.cfg.allowDirectoryEscalation;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, ReplyCallbackAction reply) {
        StringBuilder sb = new StringBuilder();
        // list permissions from in config.
        if(event.getSubcommandName().equals("permissions"))
        {
            sb.append("adminOnly = ").append(adminOnly()).append("\n").append("allowDirectoryEscalation = ").append(isDirectoryEscalationAllowed()).append("\n").append("whitelistNamesOnly = ").append(isWhitelisted()).append("\n");
        }
        // list whitelisted file name(s) in variable in config.
        if(event.getSubcommandName().equals("whitelist"))
        {
            sb.append("File names in Whitelist: \n");
            for(String fileName : DCIFileReader.cfg.whitelist) {
                sb.append(fileName).append("\n");
            }
        }
        reply.setContent(sb.toString()).queue();
    }
}
