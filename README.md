# Soywiz Minecraft Plugin

## Build

```bash
./gradlew jar
```

Plugin `.jar` file should be in `build/libs`

## Commands

### Misc commands

* `/repair` - Repair all the items in the inventory. Works standalone and on a command block
* `/firework` - Gives you 64 Firework Rockets

### Command-block teleporters

* `/where` - Shows your current position and stores it to be used with `/teleporter`
* `/teleporter` - Creates a bidirectional teleporter between this position and the position stored with `/where`
* `/unteleporter` - Removes a near teleporter/command block

### Named teleport commands  

* `/remember <name>` - Saves the current position with a name
* `/forget <name>` - Removes the named place
* `/go <name>` - Teleports to the saved location
* `/places` - Lists all the saved places

## Demonstration

<https://www.youtube.com/watch?v=lSIjATnpEYk>

