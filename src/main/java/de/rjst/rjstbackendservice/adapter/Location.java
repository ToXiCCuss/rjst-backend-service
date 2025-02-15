package de.rjst.rjstbackendservice.adapter;

import lombok.Data;

@Data
public class Location{
	private String zipcode;
	private String localtime;
	private String country;
	private String countryCode;
	private String city;
	private String timezone;
	private Object latitude;
	private String state;
	private Object longitude;
}
