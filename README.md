<div align="center">
  <img src="https://github.com/AstralDB/TrollageClient/raw/main/src/main/resources/assets/vertex/icon.png" width="100">
  <br>
  
</div>
<hr>

Trollage Client a/k/a "Project Vertex" is a 1.19 fabric mod with useful features for <i>"enforcing"</i> the <i>["Minecraft commercial use guidelines"](https://account.mojang.com/documents/commercial_guidelines#:~:text=sell%20entitlements%20that%20affect%20gameplay)</i> on certian Minecraft servers. 

<br>

<img src="https://img.shields.io/github/stars/AstralDB/TrollageClient?color=000000&style=for-the-badge"/><br>
<a href="https://github.com/AstralDB/TrollageClient/raw/main/bin/latest.jar">
  <img src="https://img.shields.io/github/downloads/AstralDB/TrollageClient/total?color=000000&style=for-the-badge&label=Downloads%20via%20releases">
</a>

## Installation

Lazy People:
Download in the `builds` folder, or [here](https://github.com/AstralDB/TrollageClient/raw/main/bin/latest.jar) for a direct download link


### GNU/Linux - Recommended 
1. Download java 16
   - `sudo apt install openjdk-16-jre` on ubuntu/debian
   - `sudo pacman -s jre-openjdk` on manjaro/arch
   - `echo "cope"` on gentoo

2. Extract java 16 to a folder of your choice
3. Tell your 1.19 minecraft instance to use that java 16, if it doesn't automatically do it
4. Install [fabric](https://fabricmc.net/use/) for 1.19
5. Drag the .jar into the `~/.minecraft/mods` folder, no libraries required. You may need to manually paste the path into your file manager. 
6. Launch fabric loader for 1.17 via the minecraft launcher


### Windows
The default launcher should already choose java 16 for the runtime, so you're free from steps 1-3
1. Install [fabric](https://fabricmc.net/use/) for 1.19
2. Drag the .jar into the `%appdata%/.minecraft/mods` folder, no libraries required
3. Launch fabric loader for 1.19 via the minecraft launcher


### Mac
1. Install [fabric](https://fabricmc.net/use/) for 1.19
2. Drag the .jar into the `~/Library/Application Support/minecraft/mods` folder
3. Launch fabric loader for 1.19 via the minecraft launcher


## Credits
[Orly](https://github.com/AstralDB) Maintaining the repository, main developer

[19MisterX98](https://github.com/19MisterX98) Client layout, module system

[Earthcomputer](https://github.com/Earthcomputer) Built Multi-connect non server-side protocol bridge
