package dev.fmayoral.customermanagement.service;

import dev.fmayoral.customermanagement.api_1_0.*;
import dev.fmayoral.customermanagement.repository.CustomerDatabaseRepository;
import dev.fmayoral.customermanagement.repository.domain.CustomerEntity;
import dev.fmayoral.customermanagement.repository.domain.NoteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import javax.ws.rs.NotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

	private CustomerDatabaseRepository customerDatabaseRepository;
	private CustomerServiceImpl customerService;

	@BeforeEach
	void setUp() {
		this.customerDatabaseRepository = Mockito.mock(CustomerDatabaseRepository.class);
		this.customerService = new CustomerServiceImpl();
		this.customerService.setCustomerRepository(this.customerDatabaseRepository);
	}

	@Test
	@DisplayName("Get customers returns empty set when no matches.")
	void getNoCustomers() {
		final CustomerQueryFilter filter= new CustomerQueryFilter();
		when(this.customerDatabaseRepository.getCustomers(filter)).thenReturn(new HashSet<>(Arrays.asList()));
		final Set<Customer> customers = this.customerService.getCustomers(filter);
		verify(this.customerDatabaseRepository, times(1)).getCustomers(filter);
		assertNotNull(customers);
		assertTrue(customers.isEmpty());
	}

	@Test
	@DisplayName("Get all customers.")
	void getCustomers() {
		final CustomerEntity customerEntity = createCustomerEntity(Status.CURRENT);
		final CustomerQueryFilter filter= new CustomerQueryFilter();
		when(this.customerDatabaseRepository.getCustomers(filter)).thenReturn(new HashSet<>(Arrays.asList(customerEntity)));
		final Set<Customer> customers = this.customerService.getCustomers(filter);
		verify(this.customerDatabaseRepository, times(1)).getCustomers(filter);
		assertNotNull(customers);
		assertFalse(customers.isEmpty());
		assertTrue(1 == customers.size());
	}

	@ParameterizedTest
	@EnumSource(value = Status.class)
	@DisplayName("Get customers filtered by status.")
	void getCustomersFilteredByStatus(final Status status) {
		final CustomerEntity customerEntity = createCustomerEntity(status);
		final CustomerQueryFilter filter= new CustomerQueryFilter(status);
		when(this.customerDatabaseRepository.getCustomers(filter)).thenReturn(new HashSet<>(Arrays.asList(customerEntity)));
		final Set<Customer> customers = this.customerService.getCustomers(filter);
		verify(this.customerDatabaseRepository, times(1)).getCustomers(filter);
		assertNotNull(customers);
		assertFalse(customers.isEmpty());
		assertTrue(1 == customers.size());
		assertEquals(status, customers.stream().findFirst().get().getStatus());
	}

	@Test
	@DisplayName("Get customer by id throws NotFoundException exception when customer doesn't exist")
	void getCustomersByIdThrowsNotFoundExceptionWhenCustomerDoesNotExist() {
		when(this.customerDatabaseRepository.getCustomerById(1L)).thenReturn(null);
		assertThrows(NotFoundException.class, () -> this.customerService.getCustomerById(1L));
	}

	@Test
	@DisplayName("Get customer by id.")
	void getCustomerById() {
		final CustomerEntity customerEntity = createCustomerEntity(Status.CURRENT);
		when(this.customerDatabaseRepository.getCustomerById(1L)).thenReturn(customerEntity);
		final Customer customer = this.customerService.getCustomerById(1L);
		verify(this.customerDatabaseRepository, times(1)).getCustomerById(1L);
		assertNotNull(customer);
	}

	@ParameterizedTest
	@EnumSource(value = Status.class)
	@DisplayName("DB Customer Entities are mapped properly.")
	void customerMappingTest(final Status status){
		final CustomerEntity customerEntity = createCustomerEntity(status);
		final Customer customer = this.customerService.mapFullCustomer(customerEntity);
		assertNotNull(customer);
		assertEquals("Fernando", customer.getFirstName());
		assertEquals("German", customer.getMiddleName());
		assertEquals("Mayoral", customer.getLastName());
		assertEquals("Fake street 1234", customer.getContactDetails().getAddress());
		assertEquals("1234-5678", customer.getContactDetails().getPhone());
		assertEquals("fernando@mayoral.dev", customer.getContactDetails().getEmail());
		assertEquals(status, customer.getStatus());
		assertEquals("text", customer.getNotes().stream().findFirst().get().getText());
		assertNotNull(customer.getCreated());
	}

	@Test
	@DisplayName("DB Customer Entity without notes is mapped properly.")
	void customerWithoutNotesMappingTest(){
		final CustomerEntity customerEntity = createCustomerEntity(null);
		customerEntity.setNotes(null);
		final Customer customer = this.customerService.mapFullCustomer(customerEntity);
		assertNotNull(customer);
		assertNull(customer.getNotes());
	}

	@ParameterizedTest
	@EnumSource(value = Status.class)
	@DisplayName("Status update invokes appropriate update method with the correct parameters.")
	void updateOrCreateCustomerStatus(final Status status) {
		final long customerId = 1L;
		when(this.customerDatabaseRepository.createOrUpdateCustomerStatus(customerId, status)).thenReturn(OperationOutcome.UPDATED);
		final OperationOutcome outcome = this.customerService.updateOrCreateCustomerStatus(customerId, status);
		verify(this.customerDatabaseRepository, times(1)).createOrUpdateCustomerStatus(customerId, status);
		assertEquals(OperationOutcome.UPDATED, outcome);
	}

	@Test
	@DisplayName("Note creation/update invokes appropriate update method with correct parameters.")
	void updateOrCreateCustomerNote() {
		final long customerId = 1L;
		final Note customerNote = new Note();
		when(this.customerDatabaseRepository.createOrUpdateCustomerNote(anyLong(), any(NoteEntity.class))).thenReturn(OperationOutcome.UPDATED);
		final OperationOutcome outcome = this.customerService.updateOrCreateCustomerNote(customerId, customerNote);
		verify(this.customerDatabaseRepository, times(1)).createOrUpdateCustomerNote(anyLong(), any(NoteEntity.class));
		assertEquals(OperationOutcome.UPDATED, outcome);
	}

	private CustomerEntity createCustomerEntity(final Status status) {
		final CustomerEntity customer = new CustomerEntity();
		customer.setId(1L);
		customer.setFirstName("Fernando");
		customer.setMiddleName("German");
		customer.setLastName("Mayoral");
		customer.setAddress("Fake street 1234");
		customer.setPhone("1234-5678");
		customer.setEmail("fernando@mayoral.dev");
		customer.setCreated(Timestamp.from(Instant.now()));
		customer.setNotes(createNotes());
		customer.setStatus(status == null? null : status.toString());
		return customer;
	}

	private Set<NoteEntity> createNotes(){
		final Set<NoteEntity> noteSet = new HashSet<>();
		noteSet.add(new NoteEntity(null, null, "text"));
		return noteSet;
	}
}