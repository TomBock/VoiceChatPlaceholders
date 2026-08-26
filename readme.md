# Voice Chat Placeholders
Provides server-wide placeholders for servers using [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/).

These placeholders can be used in nametag plugins such as [UnlimitedNameTags](https://www.spigotmc.org/resources/unlimitednametags.117526/) 
which replaces the default nametags, or in any other plugin that supports PlaceholderAPI.

This plugin does **not** render icons by itself, it only exposes placeholders.

## Features
- Exposes the `%vcicon_%` placeholder
- For custom icons, create them in your server resource pack and use them in the `config.yml`
- Supported states:
  - talking
  - whispering
  - quiet
  - disabled

## Requirements
- Paper 1.21
- Server Resource Pack that includes the configured icons
- [Simple Voice Chat](https://modrinth.com/plugin/simple-voice-chat/)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

## Placeholders
| Placeholder       | Description                                     |
|-------------------|-------------------------------------------------|
| `%vcicon_%`       | Is replaced with the configured voice chat text |
| `%vcicon_icon%`   | Same thing, if you prefer a readable parameter  |
| `%vcicon_status%` | Same thing                                      |

> **Heads up:** PlaceholderAPI 2.12.3 stopped resolving placeholders that do not contain an
> underscore, so the old `%vcicon%` silently stays unparsed. Add the underscore: `%vcicon_%`.

> Not to be confused with `%voicechat_installed%` and friends - those belong to Simple Voice
> Chat itself, which registers its own `voicechat` expansion.

## Configs

### config.yml
```yaml
talking: "🔊"    # "Player is talking"
whispering: "🔉" # "Player is whispering"
quiet: "🔈"      # Default state when connected
disabled: "❌"   # Unconnected, if you don't want to show anything, leave it empty
```
