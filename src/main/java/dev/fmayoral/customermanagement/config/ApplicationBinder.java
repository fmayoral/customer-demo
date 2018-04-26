package dev.fmayoral.customermanagement.config;

import dev.fmayoral.customermanagement.repository.CustomerDatabaseRepository;
import dev.fmayoral.customermanagement.repository.CustomerRepository;
import dev.fmayoral.customermanagement.service.CustomerServiceImpl;
import dev.fmayoral.customermanagement.service.api_1_0.CustomerService;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		// service
		bind(CustomerServiceImpl.class).to(CustomerService.class).in(Singleton.class);
		// repository
		bind(CustomerDatabaseRepository.class).to(CustomerRepository.class).in(Singleton.class);
	}
}
