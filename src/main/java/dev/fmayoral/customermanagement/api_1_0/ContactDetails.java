package dev.fmayoral.customermanagement.api_1_0;

public class ContactDetails {
	private String phone;
	private String address;
	private String email;

	public ContactDetails(final String phone, final String address, final String email) {
		this.phone = phone;
		this.address = address;
		this.email = email;
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
}