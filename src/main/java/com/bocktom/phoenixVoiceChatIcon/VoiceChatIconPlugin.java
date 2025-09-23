package com.bocktom.phoenixVoiceChatIcon;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.Bukkit.getLogger;

public class VoiceChatIconPlugin implements VoicechatPlugin {

	private final PhoenixVoiceChatIcon plugin;
	private VoicechatServerApi api;

	private static final ConcurrentHashMap<UUID, Long> LAST_PACKET = new ConcurrentHashMap<>();
	private static final HashSet<UUID> IN_VC = new HashSet<>();

	public static final long TALK_TIMEOUT_MS = 300;

	public VoiceChatIconPlugin(PhoenixVoiceChatIcon plugin) {
		this.plugin = plugin;
	}

	@Override
	public String getPluginId() {
		return "phoenix_voicechat_icon";
	}

	@Override
	public void initialize(VoicechatApi voicechatApi) {
		api = (VoicechatServerApi) voicechatApi;

		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			long now = System.currentTimeMillis();
			LAST_PACKET.entrySet().removeIf(e -> now - e.getValue() > 10_000); // passive Säuberung
		}, 200L, 200L);
	}


	@Override
	public void registerEvents(EventRegistration registration) {
		getLogger().info("Registering Voicechat events...");
		/*
		registration.registerEvent(ServerEvent.class, this::onEvent);
		registration.registerEvent(EntitySoundPacketEvent.class, this::onEvent);
		registration.registerEvent(SoundPacketEvent.class, this::onEvent);
		*/
		// Registering all other events
		registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophoneEvent);
		registration.registerEvent(PlayerConnectedEvent.class, this::onJoinEvent);
		registration.registerEvent(PlayerDisconnectedEvent.class, this::onLeaveEvent);
		/*
		registration.registerEvent(StaticSoundPacketEvent.class, this::onEvent);
		registration.registerEvent(VoicechatServerStartedEvent.class, this::onEvent);
		registration.registerEvent(VoicechatServerStartingEvent.class, this::onEvent);
		registration.registerEvent(VoicechatServerStoppedEvent.class, this::onEvent);
		registration.registerEvent(VoiceHostEvent.class, this::onEvent);
		*/
	}

	private void onMicrophoneEvent(MicrophonePacketEvent event) {
		if(event.getSenderConnection() == null)
			return;

		ServerPlayer player = event.getSenderConnection().getPlayer();

		if(player == null)
			return;

		LAST_PACKET.put(player.getUuid(), System.currentTimeMillis());
	}

	private void onJoinEvent(PlayerConnectedEvent event) {
		IN_VC.add(event.getConnection().getPlayer().getUuid());
	}

	private void onLeaveEvent(PlayerDisconnectedEvent event) {
		LAST_PACKET.remove(event.getPlayerUuid());
		IN_VC.remove(event.getPlayerUuid());
	}

	private void onEvent(ServerEvent serverEvent) {
		getLogger().info("Voicechat event: " + serverEvent.getClass().getSimpleName());
	}

	public EStatus getStatus(UUID target) {
		VoicechatConnection connection = api.getConnectionOf(target);
		if(connection == null || connection.isDisabled()) {
			return EStatus.DISABLED;
		}

		Player player = Bukkit.getPlayer(target);
		if(player == null) {
			return EStatus.DISABLED;
		}


		if(!IN_VC.contains(target)) {
			return EStatus.DISABLED;
		}

		Long lastPacket = LAST_PACKET.get(target);
		boolean isTalking = lastPacket != null && (System.currentTimeMillis() - lastPacket) <= TALK_TIMEOUT_MS;
		if(!isTalking) {
			return EStatus.QUIET;
		}

		return player.isSneaking() ? EStatus.WHISPERING : EStatus.TALKING;
	}

}
