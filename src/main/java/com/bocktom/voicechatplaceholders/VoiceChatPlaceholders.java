package com.bocktom.voicechatplaceholders;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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

		getCommand("vcp").setExecutor(this);
	}

	public String getStatusPlaceholder(UUID uniqueId) {
		EStatus status = voicechatPlugin.getStatus(uniqueId);
		return getConfig().getString(status.key);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if ("vcp".equalsIgnoreCase(label)) {
			if (args.length == 0) {
				sender.sendMessage("\u00a7c/vcp reload \u00a77- Reload the configuration");
				return true;
			}

			if ("reload".equalsIgnoreCase(args[0])) {
				if (!sender.hasPermission("voicechatplaceholders.reload")) {
					sender.sendMessage("\u00a7cYou don't have permission to use this command!");
					return true;
				}

				reloadConfig();
				voicechatPlugin.reloadConfiguration(this.getConfig());
				sender.sendMessage("\u00a7aVoiceChatPlaceholders configuration reloaded!");
				getLogger().info("VoiceChatPlaceholders configuration reloaded by " + sender.getName());
				return true;
			}
		}

		return false;
	}

}
