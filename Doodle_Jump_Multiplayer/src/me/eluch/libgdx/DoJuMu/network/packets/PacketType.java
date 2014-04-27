package me.eluch.libgdx.DoJuMu.network.packets;

public enum PacketType {
	UNKNOWN,
	DISCOVER_BROADCAST,
	SERVER_DATAS,
	NEED_VALIDATION,
	VALIDATING,
	ACCEPTED_VALIDATING,
	GAME_HAS_ALREADY_STARTED,
	WRONG_VERSION,
	SERVER_IS_FULL,
	NAME_IN_USE,
	REQUEST_ALL_PLAYERS,
	RESPONSE_ALL_PLAYERS,
	PING,
	PONG,
	PING_DATAS,
	ONE_PLAYER_CONNECTED,
	ONE_PLAYER_DISCONNECTED,
	GAME_STARTING,
	MY_DOODLE_DATAS,
	ALL_DOODLE_DATAS,
	I_DIED,
	A_PLAYER_DIED,
	NEW_FLOOR,
	NEW_ITEM,
	KEEP_ALIVE,
}
