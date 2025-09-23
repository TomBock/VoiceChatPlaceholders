package com.bocktom.phoenixVoiceChatIcon;

public enum EStatus {
	TALKING("talking"),
	WHISPERING("whispering"),
	QUIET("quiet"),
	DISABLED("disabled");

	public final String key;

	EStatus(String key) {
		this.key = key;
	}
}
