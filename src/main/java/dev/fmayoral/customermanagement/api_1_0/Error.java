package dev.fmayoral.customermanagement.api_1_0;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonSerialize
@JsonInclude(value = NON_EMPTY, content = NON_NULL) public class Error {
	private int status;
	private String detail;

	public Error(final int status, final String detail) {
		this.status = status;
		this.detail = detail;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(final String detail) {
		this.detail = detail;
	}
}
