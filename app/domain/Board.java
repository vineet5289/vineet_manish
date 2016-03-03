package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {
	@Getter @Setter String boardCode;
	@Getter @Setter String boardName;
	public String getBoardCode() {return boardCode; }
	public String getBoardName() {return boardCode; }

	public void setBoardCode(String boardCode) {this.boardCode = boardCode; }
	public void setBoardName(String boardCode) {this.boardCode = boardCode; }
	
}
