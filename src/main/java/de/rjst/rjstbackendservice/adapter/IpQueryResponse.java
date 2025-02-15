package de.rjst.rjstbackendservice.adapter;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class IpQueryResponse{
	private String ip;
	private Isp isp;
	private Location location;
	private Risk risk;
}
