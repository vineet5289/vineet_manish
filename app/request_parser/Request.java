package request_parser;

import java.util.List;

import play.data.validation.ValidationError;

public interface Request {
	public List<ValidationError> validate();
}
