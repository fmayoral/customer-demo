package dev.fmayoral.customermanagement.repository;

import dev.fmayoral.customermanagement.api_1_0.CustomerQueryFilter;
import dev.fmayoral.customermanagement.api_1_0.OperationOutcome;
import dev.fmayoral.customermanagement.api_1_0.Status;
import dev.fmayoral.customermanagement.repository.domain.CustomerEntity;
import dev.fmayoral.customermanagement.repository.domain.NoteEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDatabaseRepositoryTest {

	private static final long GET_CUSTOMER_ID = 1L;
	private static final long UPDATE_CUSTOMER_STATUS_ID = 2L;
	private static final long UPDATE_CUSTOMER_NOTE_ID = 3L;
	private static final long CREATE_CUSTOMER_NOTE_ID = 4L;
	private static final long NON_EXISTENT_CUSTOMER_ID = 100L;

	private static final int TOTAL_CUSTOMERS = 6;
	private static final int CURRENT_CUSTOMERS = 2;

	private final CustomerDatabaseRepository repository = new CustomerDatabaseRepository();

	@Test
	@DisplayName("Can retrieve all customers")
	void getAllCustomers() {
		final Set<CustomerEntity> customers = this.repository.getCustomers(new CustomerQueryFilter());
		assertEquals(TOTAL_CUSTOMERS, customers.size());
	}

	@Test
	@DisplayName("Customers can be filtered by status")
	void getCustomersByStatus() {
		final Set<CustomerEntity> customers = this.repository.getCustomers(new CustomerQueryFilter(Status.CURRENT));
		assertEquals(CURRENT_CUSTOMERS, customers.size());
	}

	@Test
	@DisplayName("Get customer by id retrieves the correct customer with its corresponding notes")
	void getCustomerById() {
		// given - there's a customer
		final CustomerEntity customer = this.repository.getCustomerById(GET_CUSTOMER_ID);
		assertNotNull(customer);
		// then - it has the correct information
		assertTrue(GET_CUSTOMER_ID == customer.getId());
		assertEquals("Fernando", customer.getFirstName());
		assertEquals("German", customer.getMiddleName());
		assertEquals("Mayoral", customer.getLastName());
		assertEquals("123-4567", customer.getPhone());
		assertEquals("123 Fake Street", customer.getAddress());
		assertEquals("fernando@mayoral.dev", customer.getEmail());
		assertEquals(Status.CURRENT.toString(), customer.getStatus());
		// and - it has the correct notes associated with it
		assertNotNull(customer.getNotes());
		assertFalse(customer.getNotes().isEmpty());
		final NoteEntity note = customer.getNotes().stream().findFirst().get();
		assertTrue(1L == note.getId());
		assertEquals("This is a customer", note.getText());
	}

	@Test
	@DisplayName("Get customer by id returns null when customer doesn't exist")
	void getNonExistentCustomerReturnsNull() {
		// given - a customer doesn't exist
		final CustomerEntity customer = this.repository.getCustomerById(NON_EXISTENT_CUSTOMER_ID);
		// then - the customer is null
		assertNull(customer);
	}

	@Test
	@DisplayName("Customer status can be updated")
	void updateCustomerStatus() {
		// given - there's a customer with a "non_active" status
		final CustomerEntity outdatedCustomer = this.repository.getCustomerById(UPDATE_CUSTOMER_STATUS_ID);
		assertEquals(Status.NON_ACTIVE.toString(), outdatedCustomer.getStatus());

		// when - the customer status is updated to be "current"
		final OperationOutcome operationOutcome = this.repository.createOrUpdateCustomerStatus(UPDATE_CUSTOMER_STATUS_ID, Status.CURRENT);

		// then - the operation outcome is updated
		assertEquals(OperationOutcome.UPDATED, operationOutcome);
		// and - the status is updated
		final CustomerEntity updatedCustomer = this.repository.getCustomerById(UPDATE_CUSTOMER_STATUS_ID);
		assertEquals(Status.CURRENT.toString(), updatedCustomer.getStatus());
	}

	@Test
	@DisplayName("Customer note can be updated")
	void updateCustomerNote() {
		// given - there's a customer
		final CustomerEntity customer = this.repository.getCustomerById(UPDATE_CUSTOMER_NOTE_ID);

		// and - it has a note
		final long noteId = 3L;
		assertNotNull(customer.getNotes());
		assertFalse(customer.getNotes().isEmpty());
		final NoteEntity note = customer.getNotes().stream().findFirst().get();
		assertTrue(noteId == note.getId());
		assertEquals("This could be a customer", note.getText());

		// when - the note is updated
		final String updatedText = "42";
		note.setText(updatedText);
		final OperationOutcome operationOutcome = this.repository.createOrUpdateCustomerNote(UPDATE_CUSTOMER_NOTE_ID, note);

		// then - the operation outcome is updated
		assertEquals(OperationOutcome.UPDATED, operationOutcome);
		// and - the note content changes
		final CustomerEntity customerWithUpdatedNote = this.repository.getCustomerById(UPDATE_CUSTOMER_NOTE_ID);
		assertNotNull(customerWithUpdatedNote.getNotes());
		assertFalse(customerWithUpdatedNote.getNotes().isEmpty());
		final NoteEntity updatedNote = customerWithUpdatedNote.getNotes().stream().findFirst().get();
		assertTrue(noteId == updatedNote.getId());
		assertEquals(updatedText, updatedNote.getText());
	}

	@Test
	@DisplayName("Customer note can be created")
	void createCustomerNote() {
		// given - there's a customer
		final CustomerEntity customer = this.repository.getCustomerById(CREATE_CUSTOMER_NOTE_ID);
		// and - it has one note
		assertNotNull(customer.getNotes());
		assertTrue(1 == customer.getNotes().size());

		// when - a note is created
		final String newNoteText = "my new note";
		final NoteEntity newNote = new NoteEntity(null, customer, newNoteText);
		final OperationOutcome operationOutcome = this.repository.createOrUpdateCustomerNote(CREATE_CUSTOMER_NOTE_ID, newNote);

		// then - the operation outcome is created
		assertEquals(OperationOutcome.CREATED, operationOutcome);
		// and - the note content changes
		final CustomerEntity customerWithCreatedNote = this.repository.getCustomerById(CREATE_CUSTOMER_NOTE_ID);
		assertNotNull(customerWithCreatedNote.getNotes());
		assertFalse(customerWithCreatedNote.getNotes().isEmpty());
		assertTrue(2 == customerWithCreatedNote.getNotes().size());
		assertTrue(customerWithCreatedNote.getNotes().stream().anyMatch(note -> newNoteText.equals(note.getText())));
	}

	@Test
	@DisplayName("Creating a note for a non existent customer fails")
	void createCustomerNoteForNonExistentCustomer() {
		// given - a customer doesn't exist
		final CustomerEntity customer = this.repository.getCustomerById(NON_EXISTENT_CUSTOMER_ID);
		assertNull(customer);

		// when - a note is created
		final String newNoteText = "my new note";
		final NoteEntity newNote = new NoteEntity(null, customer, newNoteText);
		final OperationOutcome operationOutcome = this.repository.createOrUpdateCustomerNote(NON_EXISTENT_CUSTOMER_ID, newNote);

		// then - the operation outcome is failed
		assertEquals(OperationOutcome.FAILED, operationOutcome);
	}

	@Test
	@DisplayName("Updating a note for a non existent customer fails")
	void updateCustomerNoteForNonExistentCustomer() {
		// given - a customer doesn't exist
		final CustomerEntity customer = this.repository.getCustomerById(NON_EXISTENT_CUSTOMER_ID);
		assertNull(customer);

		// when - a note is created
		final NoteEntity existingNote = new NoteEntity(1L, customer, "existing note");
		final OperationOutcome operationOutcome = this.repository.createOrUpdateCustomerNote(NON_EXISTENT_CUSTOMER_ID, existingNote);

		// then - the operation outcome is failed
		assertEquals(OperationOutcome.FAILED, operationOutcome);
	}
}