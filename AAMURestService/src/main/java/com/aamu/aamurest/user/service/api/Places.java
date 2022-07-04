package com.aamu.aamurest.user.service.api;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Places {
	public Response response;
	
	@Getter
	@Setter
	public static class Response{
		public Header header;
		public Body body;
	}
	@Getter
	@Setter
	public static class Header{
		public String resultCode;
		public String resultMsg;
	}
	@Getter
	@Setter
	public static class Body{
		public Items items;
		public int numOfRows;
		public int pageNo;
		public int totalCount;
	}
	@Getter
	@Setter
	public static class Items{
		public List<Item> item = null;
	}
	@Getter
	@Setter
	public static class Item{
		public String addr1;
		public String addr2;
		public int areacode;
		public String cat1;
		public String cat2;
		public String cat3;
		public int contentid;
		public int contenttypeid;
		public long createdtime;
		public String firstimage;
		public String firstimage2;
		public double mapx;
		public double mapy;
		public int mlevel;
		public long modifiedtime;
		public int readcount;
		public int sigungucode;
		public String title;
		public String zipcode;
		

		public int eventenddate;
		public int eventstartdate;
		public String tel;
		
	}
}
