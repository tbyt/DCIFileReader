package com.tbyt;

import de.erdbeerbaerlp.dcintegration.common.discordCommands.DiscordCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;

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
        addOption(OptionType.STRING,"search",DCIFileReader.cfg.searchCommandArgDescription,false);
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
        final OptionMapping optionSearch = event.getOption("search");

        // if whitelist is true set from the config.
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
                sb.append("File names in Whitelist: \n");
                for(String fileName : DCIFileReader.cfg.whitelist) {
                    sb.append(fileName).append("\n");
                }
                reply.setContent(sb.toString()).queue();
                return;
            }
        }
        // if directory escalation is not allowed, must put check here. Whitelist on, will bypass this option.
        else if(!DCIFileReader.cfg.allowDirectoryEscalation)
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
                if(optionSearch!=null)
                {
                    while (scanner.hasNextLine()) {
                        String nextLine=scanner.nextLine();
                        if(nextLine.contains(optionSearch.getAsString()))
                            sb.append(nextLine).append("\n");
                    }
                    scanner.close();
                    reply.setContent(sb.toString()).queue();
                    return;
                }
                // if no search is given, we send the contents of the file in a discord message.
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
        try {
            reply.setContent(sb.toString()).queue();
        }
        //Usually occurs when a file is larger than the character count, currently being larger than 2000.
        catch(IllegalArgumentException e)
        {
            reply.setContent(optionName.getAsString()+" is larger than 2000 characters! We've attached it here.").addFiles(FileUpload.fromData(Paths.get(RootDir + "/" + optionName.getAsString()),optionName.getAsString())).queue();
        }
    }
}