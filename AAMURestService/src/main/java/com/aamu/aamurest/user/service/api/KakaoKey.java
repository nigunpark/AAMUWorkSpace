package com.aamu.aamurest.user.service.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
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
		@JsonProperty("address_name")
		public String addressName;
		@JsonProperty("category_group_code")
		public String categoryGroupCode;
		@JsonProperty("category_group_name")
		public String categoryGroupName;
		@JsonProperty("category_name")
		public String categoryName;
		public String distance;
		public String id;
		public String phone;
		@JsonProperty("place_name")
		public String placeName;
		@JsonProperty("place_url")
		public String placeUrl;
		@JsonProperty("road_address_name")
		public String roadAddressName;
		public String x;
		public String y;
	}
	@Getter
	@Setter
	public static class Meta{
		@JsonProperty("is_end")
		public Boolean isEnd;
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
