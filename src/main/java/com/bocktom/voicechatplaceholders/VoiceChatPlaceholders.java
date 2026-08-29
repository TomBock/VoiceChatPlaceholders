package com.bocktom.voicechatplaceholders;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
			return;
		}

		if(!new VoiceChatIconExpansion().register()) {
			getLogger().severe("Could not register the %vcicon_% placeholder with PlaceholderAPI!");
		}

		getCommand("vcp").setExecutor(this);
	}

	public String getStatusPlaceholder(UUID uniqueId) {
		EStatus status = voicechatPlugin.getStatus(uniqueId);
		return getConfig().getString(status.key, "");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(args.length == 0) {
			sender.sendMessage("\u00a7e/vcp reload \u00a77- reload config.yml");
			return true;
		}

		if(!args[0].equalsIgnoreCase("reload")) {
			return false;
		}

		if(!sender.hasPermission("voicechatplaceholders.reload")) {
			sender.sendMessage("\u00a7cYou don't have permission to do that.");
			return true;
		}

		reloadConfig();
		if(voicechatPlugin != null) {
			voicechatPlugin.reloadSettings();
		}
		sender.sendMessage("\u00a7aVoiceChatPlaceholders reloaded.");
		getLogger().info("Configuration reloaded by " + sender.getName());
		return true;
	}


}
