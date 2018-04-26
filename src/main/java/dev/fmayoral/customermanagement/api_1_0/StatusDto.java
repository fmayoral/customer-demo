package dev.fmayoral.customermanagement.api_1_0;

import javax.validation.constraints.NotNull;

public class StatusDto {
	private Status status;

	@NotNull(message = "status is compulsory")
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	@Override public String toString() {
		return "StatusDto{" +
				"status=" + this.status +
				'}';
	}
}
