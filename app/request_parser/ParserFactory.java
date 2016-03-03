package request_parser;

public class ParserFactory {
	public static Parser getParser(String name) {
		Parser parser = null;
		if(name.equalsIgnoreCase("student")) {
			parser = new StudentParent();
		} else if(name.equalsIgnoreCase("school")) {
			parser = new SchoolPrinciple();
		} else if(name.equalsIgnoreCase("employee")) {
			parser = new Employee();
		}
		return parser;
	}
}
