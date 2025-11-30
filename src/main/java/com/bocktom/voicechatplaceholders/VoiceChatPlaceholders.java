package com.bocktom.voicechatplaceholders;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class VoiceChatPlaceholders extends JavaPlugin implements CommandExecutor {

	public static VoiceChatPlaceholders plugin;
	private VoiceChatPlaceholdersPlugin voicechatPlugin;

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();
		reloadConfig();

		BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
		if (service != null) {
			voicechatPlugin = new VoiceChatPlaceholdersPlugin(this);
			service.registerPlugin(voicechatPlugin);
			getLogger().info("VoiceChatPlaceholders has successfully registered with VoiceChat!");
		}
		else {
			getLogger().severe("Could not load VoiceChat service!");
			getServer().getPluginManager().disablePlugin(this);
		}

		new VoiceChatIconExpansion().register();
		//new PlayerNameExpansion().register();
	}

	public String getStatusPlaceholder(UUID uniqueId) {
		EStatus status = voicechatPlugin.getStatus(uniqueId);
		return getConfig().getString(status.key);
	}
}
