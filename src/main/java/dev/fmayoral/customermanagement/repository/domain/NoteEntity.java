package dev.fmayoral.customermanagement.repository.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Note")
@Table(name = "note")
public class NoteEntity implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;
	@Column(name = "text")
	private String text;

	public NoteEntity() {
	}

	public NoteEntity(final Long id, final CustomerEntity customer, final String text) {
		this.id = id;
		this.customer = customer;
		this.text = text;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public CustomerEntity getCustomer() {
		return this.customer;
	}

	public void setCustomer(final CustomerEntity customer) {
		this.customer = customer;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final NoteEntity that = (NoteEntity) o;
		return Objects.equals(this.id, that.id) &&
				Objects.equals(this.customer, that.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.customer);
	}
}
