package com.bocktom.phoenixVoiceChatIcon;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VoiceChatIconExpansion extends PlaceholderExpansion {

	@Override
	public @NotNull String getIdentifier() {
		return "vcicon";
	}

	@Override
	public @NotNull String getAuthor() {
		return "TomBock";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0";
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String raw) {
		if(player == null) {
			return "";
		}

		// vcicon_status_playerName
		if(!raw.startsWith("status_")) {
			return "";
		}
		String targetName = raw.substring(7);
		Player target = player.getServer().getPlayerExact(targetName);
		if(target == null) {
			return "offline";
		}
		return PhoenixVoiceChatIcon.plugin.getStatusPlaceholder(target.getUniqueId());
	}
}
