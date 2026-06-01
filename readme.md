# Voice Chat Placeholders
Provides server-wide placeholders for servers using [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/).

These placeholders can be used in nametag plugins such as [UnlimitedNameTags](https://www.spigotmc.org/resources/unlimitednametags.117526/) 
which replaces the default nametags, or in any other plugin that supports PlaceholderAPI.

This plugin does **not** render icons by itself, it only exposes placeholders.

## Features
- Exposes `%vcicon%` placeholder
- Real-time config reloading with `/vcp reload` command
- For custom icons, create them in your server resource pack and use them in the `config.yml`
- Supported states:
  - talking
  - whispering
  - quiet (no icon displayed)
  - disabled
  - not_installed

## Requirements
- Paper 1.21
- Server Resource Pack that includes the configured icons
- [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Placeholders
| Placeholder | Description                                     |
|-------------|-------------------------------------------------|
| `%vcicon%`  | Is replaced with the configured voice chat text |

## Commands
| Command | Permission | Description |
|---------|-----------|-------------|
| `/vcp reload` | `voicechatplaceholders.reload` | Reloads the configuration in real-time without restarting |

**Permission Note:** By default, only operators (OP) have access to the `/vcp reload` command.

## Configs

### config.yml
```yaml
talking: "🔊"           # Player is talking
whispering: "🔉"        # Player is whispering
quiet: ""               # Player is connected but not talking (no icon displayed)
disabled: "❌"          # Voice chat disabled
not_installed: "🛠️"    # Voice chat not installed (mod not installed)
talk_timeout_ms: 300    # Time in milliseconds after which the talking icon stops. 300 works well for most use cases.
```

### Configuration Notes
- **quiet**: Set to empty string (`""`) to display no icon when players are idle
- **talk_timeout_ms**: Adjust this value based on your server's needs. Lower values = faster response to talking state changes
- **Icons**: You can use emoji (as shown) or custom Unicode characters mapped through a server resource pack
