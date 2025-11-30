# Voice Chat Placeholders
Provides server-wide placeholders for servers using [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/).

These placeholders can be used in nametag plugins such as [UnlimitedNameTags](https://www.spigotmc.org/resources/unlimitednametags.117526/) 
which replaces the default nametags, or in any other plugin that supports PlaceholderAPI.

This plugin does **not** render icons by itself, it only exposes placeholders.

## Features
- Exposes `%vcicon%` placeholder
- For custom icons, create them in your server resource pack and use them in the `config.yml`
- Supported states:
  - talking
  - whispering
  - quiet
  - disabled

## Requirements
- Paper 1.20+
- Server Resource Pack that includes the configured icons
- [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Placeholders
| Placeholder | Description                                     |
|-------------|-------------------------------------------------|
| `%vcicon%`  | Is replaced with the configured voice chat text |

## Configs

### config.yml
```yaml
talking: "🔊"    # "Player is talking"
whispering: "🔉" # "Player is whispering"
quiet: "🔈"      # Default state when connected
disabled: "❌"   # Unconnected, if you don't want to show anything, leave it empty
```

## Installation
