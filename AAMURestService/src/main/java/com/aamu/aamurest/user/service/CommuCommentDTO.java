package com.aamu.aamurest.user.service;

import java.sql.Date;

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
	public String cno;
	public String id;
	public String lno;
	public String reply;
	public java.sql.Date replydate;
}
