package com.bocktom.voicechatplaceholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerNameExpansion extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "player";
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
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		if(params.equals("name")) {
			return player.getName();
		}
		return null;
	}
}
