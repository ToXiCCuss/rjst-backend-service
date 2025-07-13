package de.rjst.bes.adapter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Isp {
	private String org;

	@JsonProperty("isp")
	private String ispName;
	private String asn;
}
