package dev.fmayoral.customermanagement.api_1_0;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.NotNull;

@JsonSerialize
public class Note {
	private Long id;
	private String text;

	public Note() {
	}

	public Note(final Long id, final String text) {
		this.id = id;
		this.text = text;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@NotNull(message = "note text is compulsory")
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	@Override public String toString() {
		return "Note{" +
				"id=" + this.id +
				", text='" + this.text + '\'' +
				'}';
	}
}
