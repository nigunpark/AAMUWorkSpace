package com.aamu.aamurest.user.service.api;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class KakaoReview {
	public BasicInfo basicInfo;
	public List<CommentInfo> commentInfo = null;
	
	@Getter
	@Setter
	public static class BasicInfo {

		public String fullname;
		public double star;
		public int score;
		public int feedback;

	}
	@Getter
	@Setter
	public class CommentInfo {

		public String aamuUsername;
		public int aamuPoint;
		public String aamuContents;

	}
}
