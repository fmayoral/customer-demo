package dev.fmayoral.customermanagement.api_1_0;

public enum Status {
	PROSPECTIVE("PROSPECTIVE"),
	CURRENT("CURRENT"),
	NON_ACTIVE("NON_ACTIVE");

	final String statusName;
	Status(final String statusName){
		this.statusName = statusName;
	}
}
