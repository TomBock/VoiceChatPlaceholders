package com.bocktom.phoenixVoiceChatIcon;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class PhoenixVoiceChatIcon extends JavaPlugin implements CommandExecutor {

	public static PhoenixVoiceChatIcon plugin;

	private VoiceChatIconPlugin voicechatPlugin;

	@Override
	public void onEnable() {
		plugin = this;

		BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
		if (service != null) {
			voicechatPlugin = new VoiceChatIconPlugin(this);
			service.registerPlugin(voicechatPlugin);
			getLogger().info("PhoenixVoiceChatIcon has been enabled!");
		}
		else {
			getLogger().severe("Could not load Voicechat service!");
		}

		saveDefaultConfig();
		reloadConfig();

		getCommand("voicechaticon").setExecutor(this);
		new VoiceChatIconExpansion().register();
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		sender.sendMessage("PhoenixVoiceChatIcon is active!");
		return true;
	}

	public String getStatusPlaceholder(@NotNull UUID uniqueId) {
		EStatus status = voicechatPlugin.getStatus(uniqueId);
		return getConfig().getString(status.key);
	}
}
