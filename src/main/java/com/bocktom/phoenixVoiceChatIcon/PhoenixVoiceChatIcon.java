package com.bocktom.phoenixVoiceChatIcon;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class PhoenixVoiceChatIcon extends JavaPlugin implements CommandExecutor {

	public static PhoenixVoiceChatIcon plugin;

	private VoiceChatIconPlugin voicechatPlugin;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();
		reloadConfig();

		BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
		if (service != null) {
			voicechatPlugin = new VoiceChatIconPlugin(this);
			service.registerPlugin(voicechatPlugin);
			getLogger().info("PhoenixVoiceChatIcon has been enabled!");
		}
		else {
			getLogger().severe("Could not load Voicechat service!");
		}

		new VoiceChatIconExpansion().register();
		//new PlayerNameExpansion().register();
	}

	public String getStatusPlaceholder(UUID uniqueId) {
		EStatus status = voicechatPlugin.getStatus(uniqueId);
		return getConfig().getString(status.key);
	}
}
