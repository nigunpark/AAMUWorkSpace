package com.aamu.aamurest.user.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomDTO {
	private int roomno;
	private String fromid;
	private String toid;
	private String frompro;
	private String topro;
}

