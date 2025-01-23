package com.tbyt;

import de.erdbeerbaerlp.dcintegration.common.discordCommands.DiscordCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ReadCommand extends DiscordCommand {
    static Path RootDir = null;

    protected ReadCommand() {
        super("readfile", DCIFileReader.cfg.readCommandDescription);
        addOption(OptionType.STRING,"name",DCIFileReader.cfg.readCommandArgDescription,true);
    }

    @Override
    public boolean adminOnly() {
        return DCIFileReader.cfg.adminOnly;
    }

    /*
    FIleIO referenced: https://github.com/TheColdWorld/MCTextFileReader/blob/main/src/main/java/cn/thecoldworld/textfilereader/FileIO.java
     */
    @Override
    public void execute(SlashCommandInteractionEvent event, ReplyCallbackAction reply) {
        StringBuilder sb = new StringBuilder();
        final OptionMapping optionName = event.getOption("name");
        // if directory escalation is not allowed, must put check here.
        if(!DCIFileReader.cfg.allowDirectoryEscalation)
        {
            if(optionName.getAsString().contains(".."))
            {
                sb.append("File directory escalation is not allowed. Check '/readfile permissions' and use '/discord reload' if config changes are made.\n");
                reply.setContent(sb.toString()).queue();
                return;
            }
        }
        try {
            String JarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            // https://stackoverflow.com/questions/23325800/replace-the-last-occurrence-of-a-string-in-another-string
            RootDir = Paths.get(JarPath.substring(0, JarPath.lastIndexOf("DCIFileReader")));
            if (!RootDir.isAbsolute()) throw new RuntimeException("Cannot get Data path");
            try {
                Scanner scanner = new Scanner(Paths.get(RootDir + "/" + optionName.getAsString()), StandardCharsets.UTF_8);
                if(DCIFileReader.cfg.whitelistNamesOnly)
                {
                    boolean isEntryOnWhitelist = false;
                    for(String fileName : DCIFileReader.cfg.whitelist) {
                        if(optionName.getAsString().equals(fileName)) {
                            isEntryOnWhitelist = true;
                        }
                    }
                    if(!isEntryOnWhitelist)
                    {
                        sb.append("File name given is not on the Whitelist.\n");
                        reply.setContent(sb.toString()).queue();
                        return;
                    }
                }
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine()).append("\n");
                }
                scanner.close();
            } catch (IOException e) {
                sb.append("Could not find file: ").append(optionName.getAsString()).append("\n");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        reply.setContent(sb.toString()).queue();
    }
}