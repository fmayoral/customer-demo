package dev.fmayoral.customermanagement.repository;

import dev.fmayoral.customermanagement.api_1_0.CustomerQueryFilter;
import dev.fmayoral.customermanagement.api_1_0.OperationOutcome;
import dev.fmayoral.customermanagement.api_1_0.Status;
import dev.fmayoral.customermanagement.repository.domain.CustomerEntity;
import dev.fmayoral.customermanagement.repository.domain.NoteEntity;

import java.util.Set;

public interface CustomerRepository {

	/**
	 * Get all customers matching the given criteria.
	 *
	 * @param filter the filter criteria.
	 * @return the list of matching customers.
	 */
	Set<CustomerEntity> getCustomers(final CustomerQueryFilter filter);

	/**
	 * Get a single customer by Id.
	 *
	 * @param customerId the customer id.
	 * @return the matching customer or null if none found.
	 */
	CustomerEntity getCustomerById(final Long customerId);

	/**
	 * Updates a customer status or creates it if none exists.
	 *
	 * @param customerId the customer id to create/update the status for.
	 * @param status     the status to create/update.
	 * @return the operation outcome.
	 * @see OperationOutcome#CREATED
	 * @see OperationOutcome#UPDATED
	 * @see OperationOutcome#FAILED
	 */
	OperationOutcome createOrUpdateCustomerStatus(final Long customerId, final Status status);

	/**
	 * Updates or creates a customer note.
	 *
	 * @param customerId the customer id to create/update the note for.
	 * @param note       the note to create/update.
	 * @return the operation outcome.
	 * @see OperationOutcome#CREATED
	 * @see OperationOutcome#UPDATED
	 * @see OperationOutcome#FAILED
	 */
	OperationOutcome createOrUpdateCustomerNote(final Long customerId, final NoteEntity note);
}
