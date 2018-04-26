package dev.fmayoral.customermanagement.resource;

import dev.fmayoral.customermanagement.api_1_0.*;
import dev.fmayoral.customermanagement.service.api_1_0.CustomerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.logging.Logger;

import static dev.fmayoral.customermanagement.resource.util.OperationOutcomeMapper.map;
import static java.lang.String.format;

@Path("/customers")
public class CustomerResource {
	private final Logger LOG = Logger.getLogger(CustomerResource.class.getName());

	private final static String ID_NEGATIVE_MESSAGE = "id parameter can't be negative";
	private final static String ID_REQUIRED_PARAMETER_MESSAGE = "id is a required parameter";
	private final static String STATUS_REQUIRED_PARAMETER_MESSAGE = "status is a required parameter";

	@Inject
	private CustomerService customerService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomers(@QueryParam("status") final Status status) {
		this.LOG.fine(format("getCustomers - filter: [status:%s]", status));
		final CustomerQueryFilter filter = new CustomerQueryFilter(status);
		final Set<Customer> output = this.customerService.getCustomers(filter);
		return Response.ok(output).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(
			@PathParam("id")
			@NotNull(message = ID_REQUIRED_PARAMETER_MESSAGE)
			@Min(value = 0, message = ID_NEGATIVE_MESSAGE) final Long id) {
		this.LOG.fine(format("getCustomer - id:%s]", id));
		return Response.ok(this.customerService.getCustomerById(id)).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrCreateCustomerStatus(
			@PathParam("id")
			@NotNull(message = ID_REQUIRED_PARAMETER_MESSAGE)
			@Min(value = 0, message = ID_NEGATIVE_MESSAGE) final Long id,
			@NotNull(message = STATUS_REQUIRED_PARAMETER_MESSAGE)
			@Valid final StatusDto statusDto) {
		this.LOG.fine(format("updateOrCreateCustomerStatus - id:%s, status:%s]", id, statusDto));
		return Response.
				status(map(this.customerService.updateOrCreateCustomerStatus(id, statusDto.getStatus())))
				.build();
	}

	@PUT
	@Path("/{id}/note")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOrCreateCustomerNote(
			@PathParam("id")
			@NotNull(message = ID_REQUIRED_PARAMETER_MESSAGE)
			@Min(value = 0, message = ID_NEGATIVE_MESSAGE) final Long id,
			@Valid final Note note) {
		this.LOG.fine(format("updateOrCreateCustomerNote - id:%s, note:%s]", id, note.toString()));
		return Response
				.status(map(this.customerService.updateOrCreateCustomerNote(id, note)))
				.build();
	}

}
