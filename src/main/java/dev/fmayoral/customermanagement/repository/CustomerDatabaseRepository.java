package dev.fmayoral.customermanagement.repository;

import dev.fmayoral.customermanagement.api_1_0.CustomerQueryFilter;
import dev.fmayoral.customermanagement.api_1_0.OperationOutcome;
import dev.fmayoral.customermanagement.api_1_0.Status;
import dev.fmayoral.customermanagement.repository.domain.CustomerEntity;
import dev.fmayoral.customermanagement.repository.domain.NoteEntity;

import javax.inject.Singleton;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class CustomerDatabaseRepository implements CustomerRepository {

	final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");

	@Override public Set<CustomerEntity> getCustomers(final CustomerQueryFilter filter) {
		final EntityManager em = this.emf.createEntityManager();
		final Boolean applyFilter = filter.getStatus() != null;
		String queryString = "SELECT c FROM Customer c";
		if (applyFilter){ queryString = queryString.concat(" WHERE c.status=:status"); }
		final Query query = em.createQuery(queryString, CustomerEntity.class);
		if (applyFilter){ query.setParameter("status", filter.getStatus().toString()); }
		try {
			return new HashSet(query.getResultList());
		} finally {
			em.close();
		}
	}

	@Override public CustomerEntity getCustomerById(final Long customerId) {
		final EntityManager em = this.emf.createEntityManager();
		try {
			return em.find(CustomerEntity.class, customerId);
		} finally {
			em.close();
		}
	}

	@Override public OperationOutcome createOrUpdateCustomerStatus(final Long customerId, final Status status) {
        final CustomerEntity customer = getCustomerById(customerId);
		final String stringValue = status == null ? null : status.toString();
		customer.setStatus(stringValue);
		final EntityManager em = this.emf.createEntityManager();
		final EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.merge(customer);
			em.flush();
			transaction.commit();
			return OperationOutcome.UPDATED;
		} catch (final Exception e) {
			transaction.rollback();
			return OperationOutcome.FAILED;
		} finally {
			em.close();
		}
	}

	@Override public OperationOutcome createOrUpdateCustomerNote(final Long customerId, final NoteEntity note) {
        final boolean createNote = note.getId() == null;
		final EntityManager em = this.emf.createEntityManager();
		final EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			final CustomerEntity customer = getCustomerById(customerId);
			final Set<NoteEntity> notes = customer.getNotes();
			if (notes.contains(note)) {
				notes.remove(note);
			}
			notes.add(note);
			em.merge(customer);
			em.flush();
			transaction.commit();
			if (createNote) {
				return OperationOutcome.CREATED;
			} else {
				return OperationOutcome.UPDATED;
			}
		} catch (final Exception e) {
			transaction.rollback();
			return OperationOutcome.FAILED;
		} finally {
			em.close();
		}
	}

}
