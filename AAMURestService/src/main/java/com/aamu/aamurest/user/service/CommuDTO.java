package com.aamu.aamurest.user.service;

import java.util.List;

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
public class CommuDTO {
	public String lno;
	public String id;
	public String ctitle;
	public String content;
	public java.sql.Date postdate;
	public String likecount;
	public Boolean islike;
	public String rcount;
	public String userprofile;
	public CommuCommentDTO commuComment;
	public List<CommuCommentDTO> commuCommentList;
	public List<String> photo;
	public String contentid;
	public String title;
}
