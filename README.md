# Discord Integration File Reader
## Addon for [Discord Integration](https://modrinth.com/plugin/dcintegration).
---
## Installation
Addons are installed to `SERVER/DiscordIntegration-Data/addons` so put the `DCIFIleReader-0.0.1.jar` there.

If you are using an older mod version with java 11 or higher and you are getting errors, please start the server with ``java --add-opens java.base/java.io=ALL-UNNAMED -jar SERVER.jar``

## New Commands in Discord!
- Use `/readfile <file-name>` to read a file from '/DiscordIntegration-Data/addons/' folder. By default it reads from inside this folder.
- Use `/filereader permissions` to lists all permissions currently set in the `DCIFileReader.toml` config.
- Use `filereader whitelist` to list all the file names in the whitelist. The whitelist can be found in the `DCIFileReader.toml` config as `whitelist = [""]`

## Config: `DCIFileReader.toml`
To reload config, use `/discord reload` in-game or in console as its a default command from Discord Integration.

# Security!
- By default, admin mode `adminOnly` equals `true` in the config.
- A feature in this addon is changing directories to search for files in. As such, You can go back a directory using `..` in the file name and forward a directory with `/`. By default, `allowDirectoryEscalation` is set to `false` in the config which disallows the direct use of `..` for changing directories/directory escalation.
- Allowing directory changing can expose the server's file tree structure, including the files of the computer it is running on.
- By default, `whitelistNamesOnly` is set to `false` in the config, set this to `true` if you'd like to prevent the above scenario and only allow files YOU want and trusted people to access.
- Again, `readfile` this is a read-only command. But it doesn't take much to discover endpoints and secrets.

## Building this Project with Gradle Wrapper (Developers)

### Prerequisites
Make sure the following tools are installed on your system:

When building use the corresponding supported Java Version that the Version of Minecraft Supports. Example: For Minecraft Java 1.21+, use Java jdk 21.
- **Java Development Kit (JDK)**: Install JDK 21 or later. You can download it from [AdoptOpenJDK](https://adoptopenjdk.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html).
- **Gradle (optional)**: Gradle installation is optional as the project’s Gradle Wrapper will handle it.

### Verify the Installations
Run the following commands to ensure the tools are installed correctly:

```bash
# Verify JDK installation
java -version
```

---

## Steps to Build

### 1. Download the Repository
- [Main Branch](https://github.com/tbyt/dcifilereader/archive/refs/heads/main.zip)

OR

![githubzipdl](https://github.com/user-attachments/assets/93737238-b15f-4c39-a90b-e648a468c960)

---

### 2. UNZIP and Navigate to the Project Directory
Move into the cloned repository:

#### On Windows:
1. Locate the downloaded ZIP file in File Explorer.
2. Right-click the ZIP file and select **Extract All...**.
3. Choose a destination folder and click **Extract**.

#### On macOS:
1. Locate the downloaded ZIP file in Finder.
2. Double-click the ZIP file to automatically extract its contents to a folder in the same directory.

#### On Linux:
Run the following command in the terminal:
```bash
unzip DCIFileReader.zip
```
Replace `DCIFileReader.zip` with the actual name of the ZIP file if it differs.

After extraction, navigate to the extracted folder:
```bash
cd DCIFileReader
```

---

### 3. Run the Gradle Wrapper
Use the Gradle Wrapper to build the project. This ensures that you don’t need a global Gradle installation.

#### On Windows:
```cmd
.\gradlew build
```

#### On macOS/Linux:
```bash
./gradlew build
```

---

### 4. Wait for the Build to Complete
Gradle will automatically download the required dependencies and compile the project. Once the build is complete, you should see:

```
BUILD SUCCESSFUL
```

---

### 5. Locate the Built Files
The output files, including the JAR file, will be located in the `build/libs` directory:

```bash
ls build/libs
```

Look for a file named something like `DCIFileReader-<version>.jar`.

---

## Troubleshooting

- **JDK Issues**: Ensure you have the correct JDK version installed and configured.
- **Gradle Errors**: Check the repository’s README file for any additional instructions or requirements.
- **Dependencies**: Gradle will handle most dependencies automatically, but ensure you have an active internet connection for the first build.

---

Feel free to reach out if you encounter any issues!
