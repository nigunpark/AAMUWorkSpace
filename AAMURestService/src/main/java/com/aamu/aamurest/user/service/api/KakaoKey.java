package com.aamu.aamurest.user.service.api;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoKey {
	public List<Document> documents = null;
	public Meta meta;
	@Getter
	@Setter
	public static class Document{
		public String addressName;
		public String categoryGroupCode;
		public String categoryGroupName;
		public String categoryName;
		public String distance;
		public String id;
		public String phone;
		public String placeName;
		public String placeUrl;
		public String roadAddressName;
		public String x;
		public String y;
	}
	@Getter
	@Setter
	public static class Meta{
		public boolean isEnd;
		public int pageableCount;
		public SameName sameName;
		public int totalCount;
	}
	@Getter
	@Setter
	public static class SameName{
		public String keyword;
		public List<Object> region = null;
		public String selectedRegion;
	}
}
