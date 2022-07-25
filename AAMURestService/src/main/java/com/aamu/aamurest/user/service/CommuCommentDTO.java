package com.aamu.aamurest.user.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuCommentDTO {
	private String cno;
	private String id;
	private String lno;
	private String reply;
	private java.sql.Date replydate;
	public String userprofile;
}
