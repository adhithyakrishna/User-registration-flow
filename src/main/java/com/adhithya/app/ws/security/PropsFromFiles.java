package com.adhithya.app.ws.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "product.order")
public class PropsFromFiles {
	int limit;
	int displayCount;
	int shippingDays;
	
	public PropsFromFiles() {
		
	}

	public PropsFromFiles(int limit, int displayCount, int shippingDays) {
		super();
		this.limit = limit;
		this.displayCount = displayCount;
		this.shippingDays = shippingDays;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getDisplayCount() {
		return displayCount;
	}

	public void setDisplayCount(int displayCount) {
		this.displayCount = displayCount;
	}

	public int getShippingDays() {
		return shippingDays;
	}

	public void setShippingDays(int shippingDays) {
		this.shippingDays = shippingDays;
	}

}
