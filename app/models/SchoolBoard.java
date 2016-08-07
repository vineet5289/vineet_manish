package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import play.db.DB;

@Data
public class SchoolBoard {
	private static List<String> schoolBoardList = new ArrayList<String>();
	private static Map<String, String> affiliatedToBoardName = new HashMap<String, String>();
	private static Map<String, String> affiliatedToDisplayName = new HashMap<String, String>();
	private static Map<String, Long> affiliatedToId = new HashMap<String, Long>();

	private static Map<String, String> boardCodeToBoardName = new HashMap<String, String>();
	private static Map<String, String> boardCodeToDisplayName = new HashMap<String, String>();
	private static Map<String, Long> boardCodeToBoradId = new HashMap<String, Long>();
	private static Map<Long, String> boardIdToBoardCode = new HashMap<Long, String>();
	private static Map<Long, String> boardIdToBoardName = new HashMap<Long, String>();

	private long id;
	private String boardName;
	private String boardCode;
	private String boardDisplayName;
	private String affiliatedTo;

	public static synchronized List<String> getSchoolboardList() {
		if(schoolBoardList == null || schoolBoardList.isEmpty())
			fetchBoardList();

		return schoolBoardList;
	}

	public static synchronized String getDisplayNameGivenBoardCode(String boardCode) {
		if(boardCodeToDisplayName == null || boardCodeToDisplayName.isEmpty())
			fetchBoardList();

		return boardCodeToDisplayName.get(boardCode);
	}

	public static synchronized String getBoardNameGivenBoardCode(String boardCode) {
		if(boardCodeToBoardName == null || boardCodeToBoardName.isEmpty())
			fetchBoardList();

		return boardCodeToBoardName.get(boardCode);
	}

	public static synchronized String getDisplayNameGivenAffiliatedTo(String affiliatedTo){
		if(affiliatedToDisplayName == null || affiliatedToDisplayName.isEmpty())
			fetchBoardList();

		return affiliatedToDisplayName.get(affiliatedTo);
	}

	public static synchronized String getBoardNameGivenAffiliatedTo(String affiliatedTo){
		if(affiliatedToBoardName == null || affiliatedToBoardName.isEmpty())
			fetchBoardList();

		return affiliatedToBoardName.get(affiliatedTo);
	}

	public static synchronized Long getBoardIdGivenAffiliatedTo(String affiliatedTo){
		if(affiliatedToId == null || affiliatedToId.isEmpty())
			fetchBoardList();

		return affiliatedToId.get(affiliatedTo);
	}

	public static synchronized Long getIdGivenBoardCode(String boardCode){
		if(boardCodeToBoradId == null || boardCodeToBoradId.isEmpty())
			fetchBoardList();

		return boardCodeToBoradId.get(boardCode);
	}

	public static synchronized String getBoardCodeGivenId(Long id){
		if(boardIdToBoardCode == null || boardIdToBoardCode.isEmpty())
			fetchBoardList();

		System.out.println("boardIdToBoardCode=> " + boardIdToBoardCode);
		return boardIdToBoardCode.get(id);
	}

	public static synchronized String getBoardNameGivenId(Long id){
		if(boardIdToBoardName == null || boardIdToBoardName.isEmpty())
			fetchBoardList();

		return boardIdToBoardName.get(id);
	}

	private static synchronized void fetchBoardList(){
		List<String> schoolBoardNameListTemp = new ArrayList<String>();

		Map<String, String> affiliatedToBoardNameTemp = new HashMap<String, String>();
		Map<String, Long> affiliatedToIdTemp = new HashMap<String, Long>();
		Map<String, String> affiliatedToDisplayNameTemp = new HashMap<String, String>();

		Map<String, String> boardCodeToBoardNameTemp = new HashMap<String, String>();
		Map<String, String> boardCodeToDisplayNameTemp = new HashMap<String, String>();

		Map<String, Long> boardCodeToBoradIdTemp = new HashMap<String, Long>();
		Map<Long, String> boardIdToboardCodeTemp = new HashMap<Long, String>();
		Map<Long, String> boardIdToBoardNameTemp = new HashMap<Long, String>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String query = "SELECT id, board_code, board_name, board_display_name, affiliated_to FROM board;";
		try {
			connection = DB.getDataSource("srp").getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				schoolBoardNameListTemp.add(resultSet.getString("board_name"));

				affiliatedToBoardNameTemp.put(resultSet.getString("affiliated_to"), resultSet.getString("board_name"));
				affiliatedToDisplayNameTemp.put(resultSet.getString("affiliated_to"), resultSet.getString("board_display_name"));
				affiliatedToIdTemp.put(resultSet.getString("affiliated_to"), resultSet.getLong("id"));
				

				boardCodeToBoardNameTemp.put(resultSet.getString("board_code"), resultSet.getString("board_name"));
				boardCodeToDisplayNameTemp.put(resultSet.getString("board_code"), resultSet.getString("board_display_name"));

				boardCodeToBoradIdTemp.put(resultSet.getString("board_code"), resultSet.getLong("id"));
				boardIdToboardCodeTemp.put(resultSet.getLong("id"), resultSet.getString("board_code"));

				boardIdToBoardNameTemp.put(resultSet.getLong("id"), resultSet.getString("board_name"));
			}
			schoolBoardList = schoolBoardNameListTemp;
			affiliatedToBoardName = affiliatedToBoardNameTemp;
			affiliatedToDisplayName = affiliatedToDisplayNameTemp;
			affiliatedToId = affiliatedToIdTemp;

			boardCodeToBoardName = boardCodeToBoardNameTemp;
			boardCodeToDisplayName = boardCodeToDisplayNameTemp;

			boardCodeToBoradId = boardCodeToBoradIdTemp;
			boardIdToBoardCode = boardIdToboardCodeTemp;

			boardIdToBoardName = boardIdToBoardNameTemp;
		} catch(Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if(resultSet != null && !resultSet.isClosed())
					resultSet.close();
				
				if(preparedStatement != null && !preparedStatement.isClosed())
					preparedStatement.close();
				
				if(connection != null && !connection.isClosed())
					connection.close();
				
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}





