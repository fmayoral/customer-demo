package dev.fmayoral.customermanagement.api_1_0;

public class CustomerQueryFilter {
	private Status status;

	public CustomerQueryFilter() {
		this.status = null;
	}

	public CustomerQueryFilter(final Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

}
