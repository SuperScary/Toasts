# Toasts Mod

A Minecraft mod that adds customizable on-screen toast notifications to your game. Works with Fabric, Forge, and NeoForge!

## Features

- 📂 JSON-Based Toast Configuration
  - Load toasts from JSON files in `config/toasts/toasts/`
  - Customize title, subtitle, icon, colors, and duration
  - Hot-reload support via command

- 🧱 ToastBuilder API
  - Fluent API for programmatic toast creation
  - Easy to use in your mods or datapacks
  - Full customization options

- 🎨 Platform Support
  - Works with Fabric, Forge, and NeoForge
  - Consistent experience across platforms
  - Platform-specific optimizations

## Installation

1. Download the appropriate version for your mod loader (Fabric, Forge, or NeoForge)
2. Place the jar file in your mods folder
3. Launch Minecraft with the mod loader

## Configuration

### JSON Configuration

Create `.json` files in `config/toasts/toasts/` with the following structure:

```json
{
  "id": "welcome_toast",
  "title": "Welcome!",
  "subtitle": "Thanks for installing the mod!",
  "icon": "minecraft:book",
  "background": "#222222",
  "text_color": "#ffffff",
  "subtitle_color": "#cccccc",
  "duration": 80
}
```

### API Usage

```java
// Using the builder API
Toasts.builder()
  .id("achievement_unlocked")
  .title("Achievement Unlocked!")
  .subtitle("You tamed a fox")
  .icon("minecraft:fox_spawn_egg")
  .background(Color.DARK_GRAY)
  .textColor(Color.ORANGE)
  .duration(100)
  .show();

// Show a toast by ID
Toasts.show("welcome_toast");
```

## Commands

- `/toast fire <id>` - Fire a toast by ID
- `/toast reload` - Reload all toasts from disk
- `/toast list` - List all available toast IDs

## License

This mod is licensed under the MIT License. See the LICENSE file for details.
