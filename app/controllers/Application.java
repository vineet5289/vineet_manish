package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.NotNull;

import play.*;
import play.db.DB;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public Result index() throws SQLException {
//		ResultSet resultSet = null;
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		try{
//			connection = DB.getDataSource("default").getConnection();
//			String selectCommand = "SELECT * FROM board";
//			preparedStatement = connection.prepareStatement(selectCommand);
//			resultSet = preparedStatement.executeQuery();
//			if(resultSet == null)
//				System.out.println("=====> in null");
//			if (resultSet.next()){
//				System.out.println("in if ********");
//			}
//			System.out.println(resultSet.getFetchSize());
//		} catch(Exception exception) {
//			exception.printStackTrace();
//		} finally {
//			if(resultSet != null && !resultSet.isClosed())
//				resultSet.close();
//			if(preparedStatement != null && !preparedStatement.isClosed())
//				preparedStatement.close();
//			if(connection != null && !connection.isClosed())
//				connection.close();
//		}
		return ok("successful ");
	}

}
