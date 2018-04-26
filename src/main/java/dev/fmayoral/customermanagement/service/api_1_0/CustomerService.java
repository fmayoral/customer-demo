package dev.fmayoral.customermanagement.service.api_1_0;

import dev.fmayoral.customermanagement.api_1_0.*;

import java.util.Set;

public interface CustomerService {

	/**
	 * Get all customers matching the given criteria.
	 *
	 * @param filter the filter criteria.
	 * @return the list of matching customers.
	 */
	Set<Customer> getCustomers(final CustomerQueryFilter filter);

	/**
	 * Retrieves a customer by id.
	 *
	 * @param id the customer id to retrieve.
	 * @return the customer or empty if it doesn't exist.
	 */
	Customer getCustomerById(final Long id);

	/**
	 * Updates or creates a customer status if none is present.
	 *
	 * @param id     the customer id to update the status for.
	 * @param status the new status for the customer.
	 * @return the operation outcome.
	 * @see OperationOutcome#CREATED
	 * @see OperationOutcome#UPDATED
	 */
	OperationOutcome updateOrCreateCustomerStatus(final Long id, final Status status);

	/**
	 * Updates or creates a customer note.
	 *
	 * @param id   the customer id to update/create the note for.
	 * @param note the note to be updated/created.
	 * @return the operation outcome.
	 * @see OperationOutcome#CREATED
	 * @see OperationOutcome#UPDATED
	 */
	OperationOutcome updateOrCreateCustomerNote(final Long id, final Note note);
}
