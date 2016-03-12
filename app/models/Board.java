package models;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class Board {
	private String boardCode;
	private String boardName;
	private DateTime createdAt;
}
