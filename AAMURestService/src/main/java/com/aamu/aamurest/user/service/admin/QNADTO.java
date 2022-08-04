package com.aamu.aamurest.user.service.admin;

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
public class QNADTO {

	private String qno;
	private String id;
	private String title;
	private String content;
	private String qcount;
	private java.sql.Date qdate;
	private String name; // 이름 저장
	private String answerCount; // 댓글 수 카운트

	private AnswerDTO answer;
	private List<AnswerDTO> answerList;

	public int searchTotalCount;

}