package dev.fmayoral.customermanagement.service;

import dev.fmayoral.customermanagement.api_1_0.*;
import dev.fmayoral.customermanagement.repository.CustomerRepository;
import dev.fmayoral.customermanagement.repository.domain.CustomerEntity;
import dev.fmayoral.customermanagement.repository.domain.NoteEntity;
import dev.fmayoral.customermanagement.service.api_1_0.CustomerService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private CustomerRepository customerRepository;

	@Override
	public Set<Customer> getCustomers(final CustomerQueryFilter filter) {
		return this.customerRepository.getCustomers(filter).stream()
				.filter(Objects::nonNull)
				.map(this::mapBaseCustomer)
				.collect(Collectors.toSet());
	}

	@Override
	public Customer getCustomerById(final Long id) {
		return Optional.ofNullable(this.customerRepository.getCustomerById(id))
				.filter(Objects::nonNull)
				.map(this::mapFullCustomer)
				.orElseThrow(() -> new NotFoundException(String.format("Customer with id '%s' does not exist.", id)));
	}

	@Override
	public OperationOutcome updateOrCreateCustomerStatus(final Long id, final Status status) {

		return this.customerRepository.createOrUpdateCustomerStatus(id, status);
	}

	@Override public OperationOutcome updateOrCreateCustomerNote(final Long id, final Note note) {
		return this.customerRepository.createOrUpdateCustomerNote(id, mapNote(note));
	}

	private NoteEntity mapNote(final Note note){
	    final NoteEntity noteEntity = new NoteEntity();
	    noteEntity.setId(note.getId());
	    noteEntity.setText(note.getText());
	    return noteEntity;
    }

	Customer mapBaseCustomer(final CustomerEntity customerEntity) {
		return new Customer()
				.withId(customerEntity.getId())
				.withFirstName(customerEntity.getFirstName())
				.withMiddleName(customerEntity.getMiddleName())
				.withLastName(customerEntity.getLastName())
				.withStatus(mapStatus(customerEntity.getStatus()))
				.withCreated(formatInstant(customerEntity.getCreated().toInstant()));
	}

	Status mapStatus(final String statusString) {
		if (statusString == null){
			return null;
		}
		return Status.valueOf(statusString);
	}

	Customer mapFullCustomer(final CustomerEntity customerEntity) {
		return mapBaseCustomer(customerEntity)
				.withContactDetails(mapContactDetails(customerEntity))
				.withNotes(mapNotes(customerEntity.getNotes()));
	}

	private ContactDetails mapContactDetails(final CustomerEntity customerEntity) {
		return new ContactDetails(customerEntity.getPhone(),
				customerEntity.getAddress(),
				customerEntity.getEmail());
	}

	private Set<Note> mapNotes(final Set<NoteEntity> notes) {
		if (notes == null){
			return null;
		}
		return notes.stream()
				.map(entity -> new Note(entity.getId(), entity.getText()))
				.collect(Collectors.toSet());
	}

	private String formatInstant(final Instant instant){
		final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
		return formatter.format(instant);
	}

	void setCustomerRepository(final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
}
