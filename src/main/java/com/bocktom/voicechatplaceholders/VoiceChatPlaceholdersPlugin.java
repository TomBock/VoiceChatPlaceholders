package com.bocktom.voicechatplaceholders;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.Bukkit.getLogger;

public class VoiceChatPlaceholdersPlugin implements VoicechatPlugin {

	private final VoiceChatPlaceholders plugin;
	private VoicechatServerApi api;

	/** Last microphone packet we saw from a player, and whether they were whispering. */
	private record Speaking(long timestamp, boolean whispering) {}

	private static final ConcurrentHashMap<UUID, Speaking> LAST_PACKET = new ConcurrentHashMap<>();

	private final long TALK_TIMEOUT_MS;

	public VoiceChatPlaceholdersPlugin(VoiceChatPlaceholders plugin) {
		this.plugin = plugin;
		TALK_TIMEOUT_MS = plugin.getConfig().getInt("talk_timeout_ms", 300);
	}

	@Override
	public String getPluginId() {
		return "voicechatplaceholders";
	}

	@Override
	public void initialize(VoicechatApi voicechatApi) {
		api = (VoicechatServerApi) voicechatApi;

		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			long now = System.currentTimeMillis();
			LAST_PACKET.entrySet().removeIf(e -> now - e.getValue().timestamp() > 10_000); // passive Säuberung
		}, 200L, 200L);
	}


	@Override
	public void registerEvents(EventRegistration registration) {
		getLogger().info("Registering Voicechat events...");

		registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophoneEvent);
		registration.registerEvent(PlayerDisconnectedEvent.class, this::onLeaveEvent);

		registration.registerEvent(VoicechatServerStoppedEvent.class, this::onVCStopped);
	}

	private void onMicrophoneEvent(MicrophonePacketEvent event) {
		if(event.getSenderConnection() == null)
			return;

		ServerPlayer player = event.getSenderConnection().getPlayer();

		if(player == null)
			return;

		LAST_PACKET.put(player.getUuid(), new Speaking(System.currentTimeMillis(), event.getPacket().isWhispering()));
	}

	private void onLeaveEvent(PlayerDisconnectedEvent event) {
		LAST_PACKET.remove(event.getPlayerUuid());
	}

	public EStatus getStatus(UUID target) {
		if(api == null) {
			// placeholder requested between onEnable and VoiceChat calling initialize()
			return EStatus.DISABLED;
		}

		VoicechatConnection connection = api.getConnectionOf(target);

		if(connection == null || connection.isDisabled()) {
			return EStatus.DISABLED;
		}

		if(!connection.isInstalled()) {
			return EStatus.NOT_INSTALLED;
		}

		if(!connection.isConnected()) {
			return EStatus.DISABLED;
		}

		Speaking lastPacket = LAST_PACKET.get(target);
		boolean isTalking = lastPacket != null && (System.currentTimeMillis() - lastPacket.timestamp()) <= TALK_TIMEOUT_MS;
		if(!isTalking) {
			return EStatus.QUIET;
		}

		return lastPacket.whispering() ? EStatus.WHISPERING : EStatus.TALKING;
	}

	private void onVCStopped(VoicechatServerStoppedEvent voicechatServerStoppedEvent) {
		getLogger().info("Voicechat event: " + voicechatServerStoppedEvent.getClass().getSimpleName());
	}

}
