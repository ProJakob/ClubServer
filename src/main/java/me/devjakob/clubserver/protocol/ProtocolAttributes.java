package me.devjakob.clubserver.protocol;

import io.netty.util.AttributeKey;

public class ProtocolAttributes {

	public static final AttributeKey<Integer> PROTOCOL_STATE = AttributeKey.valueOf("CS:PS");
	public static final AttributeKey<ConnectionInfo> CONNECTION_INFO = AttributeKey.valueOf("CS:CI");

}
