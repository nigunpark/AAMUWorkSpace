package com.aamu.aamurest.user.service;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BBSDTO {
	public String rbn;
	public String title;
	public String content;
	public String themeid;
	public double rateavg;
	public List<String> photo;
}
