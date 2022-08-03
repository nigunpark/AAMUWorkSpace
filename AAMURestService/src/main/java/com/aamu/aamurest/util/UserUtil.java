package com.aamu.aamurest.util;

public class UserUtil {
	public static String changeTheme(String theme) {
		String themeid= null;
		switch(theme) {
			case "봄":
				themeid = "1";
				break;
			case "여름":
				themeid = "2";
				break;
			case "가을":
				themeid = "3";
				break;
			case "겨울":
				themeid = "4";
				break;
			case "등산/트래킹":
				themeid = "5";
				break;
			case "바다/해수욕장":
				themeid = "6";
				break;
			case "호캉스":
				themeid = "7";
				break;
			case "섬":
				themeid = "8";
				break;
			case "맛집":
				themeid = "9";
				break;
			case "힐링":
				themeid = "10";
				break;
			case "드라이브":
				themeid = "11";
				break;
		}
		return themeid;
	}
}
