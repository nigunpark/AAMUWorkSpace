package com.aamu.aamurest.user.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommuDTO {
	private String lno;
	private String id;
	private String ctitle;
	private String content;
	private java.sql.Date postdate;
	private String likecount;
	private Boolean islike;
	private String rcount;
	private String userprofile;
	private CommuCommentDTO commuComment;
	private List<CommuCommentDTO> commuCommentList;
	private List<String> photo;
	private String contentid;
	private String title;
	private int searchtotalcount;
	private List<String> tname;
	private String name;
	private int totalcount;
	private int followercount;
	private int followingcount;
}
