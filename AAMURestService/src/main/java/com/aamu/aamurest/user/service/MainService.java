package com.aamu.aamurest.user.service;

public interface MainService{
	int placeInsert(AttractionDTO dto);

	int infoInsert(AttractionDTO dto);

	int hotelInsert(AttractionDTO dto);

	int dinerInsert(AttractionDTO dto);
	
	int eventInsert(AttractionDTO dto);
}
