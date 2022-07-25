package com.aamu.aamurest.user.service.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class KakaoReview {
	@JsonProperty("basic_info")
	public BasicInfo basicInfo;
	@JsonProperty("comment_info")
	public List<CommentInfo> commentInfo = null;

	@Getter
	@Setter
	public static class BasicInfo {

		public String name;
		public double star;
		public int score;
		public int feedback;

	}
	@Getter
	@Setter
	public static class CommentInfo {

		public String username;
		public int point;
		public String review;

	}
}
