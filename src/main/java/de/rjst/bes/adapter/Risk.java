package de.rjst.bes.adapter;

import lombok.Data;

@Data
public class Risk{
	private Boolean isVpn;
	private Boolean isMobile;
	private Integer riskScore;
	private Boolean isDatacenter;
	private Boolean isTor;
	private Boolean isProxy;
}
