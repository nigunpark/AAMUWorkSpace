package com.aamu.aamurest.admin.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Info {
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
		public Item item;
	}
	@Getter
	@Setter
	public static class Item{
		public String chkbabycarriage;
		public String chkcreditcard;
		public String chkpet;
		public int contentid;
		public int contenttypeid;
		public String expguide;
		public int heritage1;
		public int heritage2;
		public int heritage3;
		public String infocenter;
		public String parking;
		public String restdate;
		public String usetime;
		
		
		public int barbecue;
		public int beauty;
		public int benikia;
		public int beverage;
		public int bicycle;
		public int campfire;
		public String checkintime;
		public String checkouttime;
		public String chkcooking;
		public int fitness;
		public String foodplace;
		public int goodstay;
		public int hanok;
		public String infocenterlodging;
		public int karaoke;
		public String parkinglodging;
		public int publicbath;
		public int publicpc;
		public String reservationlodging;
		public String reservationurl;
		public String roomcount;
		public String roomtype;
		public int sauna;
		public String scalelodging;
		public int seminar;
		public int sports;
		public String subfacility;
		
		public String chkcreditcardfood;
		public String discountinfofood;
		public String firstmenu;
		public String infocenterfood;
		public int kidsfacility;
		public long lcnsno;
		public String opentimefood;
		public String parkingfood;
		public String reservationfood;
		public String restdatefood;
		public String smoking;
		public String treatmenu;
	}
}
