package dev.fmayoral.customermanagement.repository.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "customer")
public class CustomerEntity implements Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "phone")
	private String phone;
	@Column(name = "address")
	private String address;
	@Column(name = "email")
	private String email;
	@Column(name = "status")
	private String status;
	@Column(name = "created")
	private Timestamp created;
	@OneToMany(targetEntity = NoteEntity.class, mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<NoteEntity> notes;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(final Timestamp created) {
		this.created = created;
	}

	public Set<NoteEntity> getNotes() {
		return this.notes;
	}

	public void setNotes(final Set<NoteEntity> notes) {
		this.notes = notes;
	}
}
