package com.tbyt;

import dcshadow.com.moandjiezana.toml.TomlComment;

public class ReadCommandConfig {
    @TomlComment("Command description of the readfile command")
    public String readCommandDescription = "Reads out a file from '/DiscordIntegration-Data/addons/' folder";
    public String readCommandArgDescription = "Name of the file in /DiscordIntegration-Data/addons/";

    @TomlComment("File names that are only accessible by whitelist if chosen so.")
    public String whitelistCommandDescription = "File names that are in the whitelist";
    public String[] whitelist = { "DCIFileReader.toml", "placeholder.txt" };

    @TomlComment("Permissions Section of Config")
    public String permissionsCommandDescription = "Lists all permissions set in the Config.";

    @TomlComment("Should certain file names only be accessible by whitelist?")
    public boolean whitelistNamesOnly = false;

    @TomlComment("Allowing this is not recommended!")
    public boolean allowDirectoryEscalation = false;

    @TomlComment({"Should this only be available to admin roles?","Giving it to non-admins is not recommended!"})
    public boolean adminOnly = true;
}
