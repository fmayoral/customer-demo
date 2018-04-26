package dev.fmayoral.customermanagement.resource.util;

import dev.fmayoral.customermanagement.api_1_0.OperationOutcome;

import javax.ws.rs.core.Response.Status;

public class OperationOutcomeMapper {
	/**
	 * Utility method to map
	 */
	public static Status map(final OperationOutcome outcome) {
		switch (outcome) {
			case UPDATED:
				return Status.NO_CONTENT;
			case CREATED:
				return Status.CREATED;
			case FAILED:
				return Status.INTERNAL_SERVER_ERROR;
			default:
				throw new IllegalArgumentException("Unknown operation outcome");
		}
	}
}
