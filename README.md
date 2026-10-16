# The Photography 0.1.0 — MVP prototype

Target: Minecraft 1.21.1 / NeoForge 21.1.249 / Java 21.

## What this prototype is intended to do
- Adds a handheld camera item.
- Sneak + right-click cycles 28 mm / 50 mm / 85 mm focal-length modes.
- Right-click captures the currently rendered game frame to a PNG in the instance's `thephotography/photos` folder.
- Opens an in-game viewer for the captured PNG.
- Captures are saved as ordinary PNG files, so they survive world reloads. (The image is stored on disk rather than embedded into the item stack in this first prototype.)

## Build and run
1. Install a 64-bit JDK 21.
2. Install IntelliJ IDEA Community (recommended).
3. Extract this folder.
4. In IntelliJ, choose **Open** and select this folder. Import as a Gradle project.
5. Wait for Gradle to download Minecraft/NeoForge dependencies.
6. Run the Gradle task `runClient`.
7. In a test world, use `/give @s thephotography:camera`.

On Windows, you can also run `gradlew.bat runClient` after a Gradle wrapper has been generated/added. This archive intentionally does not include a wrapper JAR; use IntelliJ's Gradle integration or generate a wrapper with a local Gradle installation.

## Current prototype limitations
- Focal-length modes are a first-pass digital crop/zoom approximation, not a physically modelled lens.
- PNGs are saved to the local game instance, not embedded in an item. The in-game viewer opens immediately after capture.
- This is a starter implementation, not yet verified by compiling against your local environment. If Gradle reports API/version errors, send the first error block and it can be corrected.
- Multiplayer sharing, item-bound photo albums, film/chemistry, camera optics, and advanced exposure controls are not implemented yet.

## Acceptance checklist
- [ ] Mod loads on NeoForge 1.21.1
- [ ] Camera item appears and can be given with command
- [ ] Sneak-right-click cycles focal-length modes
- [ ] Right-click saves a PNG
- [ ] In-game viewer shows the captured photo
- [ ] PNG remains after world restart
