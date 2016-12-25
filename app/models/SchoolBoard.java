package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import dao.InstituteBoardDAO;
import dao.Tables;
import lombok.Data;
import play.db.Database;
import play.db.NamedDatabase;

@Data
public class SchoolBoard implements InstituteBoardDAO {
  private static Database db;

  @Inject
	public SchoolBoard(@NamedDatabase("srp") Database db) {
		System.out.println("***** SchoolBoard*****1");
		this.db = db;
    fetchBoardList();
		System.out.println("***** SchoolBoard*****2");
	}

  private List<String> schoolBoardList = new ArrayList<String>();

  private Map<String, String> affiliatedToBoardName = new TreeMap<String, String>();
  private Map<String, String> affiliatedToDisplayName = new TreeMap<String, String>();
  private Map<String, Long> affiliatedToId = new TreeMap<String, Long>();
  private Map<String, String> affiliatedToBoardCode = new TreeMap<String, String>();

  private Map<String, String> boardCodeToBoardName = new TreeMap<String, String>();
  private Map<String, String> boardCodeToDisplayName = new TreeMap<String, String>();
  private Map<String, Long> boardCodeToBoradId = new TreeMap<String, Long>();
  private Map<String, String> boardCodeToAffiliatedTo = new TreeMap<String, String>();

  private Map<Long, String> boardIdToBoardCode = new TreeMap<Long, String>();
  private Map<Long, String> boardIdToBoardName = new TreeMap<Long, String>();
  private Map<Long, String> boardIdToDisplayName = new TreeMap<Long, String>();
  private Map<Long, String> boardIdToAffiliatedTo = new TreeMap<Long, String>();

  private Map<String, String> displayNameToBoardName = new TreeMap<String, String>();
  private Map<String, String> displayNameToBoardCode = new TreeMap<String, String>();
  private Map<String, Long> displayNameToBoradId = new TreeMap<String, Long>();
  private Map<String, String> displayNameToAffiliatedTo = new TreeMap<String, String>();

  private Map<String, String> boardNameToBoardCode = new TreeMap<String, String>();
  private Map<String, Long> boardNameToId = new TreeMap<String, Long>();
  private Map<String, String> boardNameToDisplayName = new TreeMap<String, String>();
  private Map<String, String> boardNameToAffiliatedTo = new TreeMap<String, String>();

  private void fetchBoardList() {
    List<String> schoolBoardNameListTemp = new ArrayList<String>();

    Map<String, String> affiliatedToBoardNameTemp = new TreeMap<String, String>();
    Map<String, Long> affiliatedToIdTemp = new TreeMap<String, Long>();
    Map<String, String> affiliatedToDisplayNameTemp = new TreeMap<String, String>();
    Map<String, String> affiliatedToBoardCodeTemp = new TreeMap<String, String>();

    Map<String, String> boardCodeToBoardNameTemp = new TreeMap<String, String>();
    Map<String, String> boardCodeToDisplayNameTemp = new TreeMap<String, String>();
    Map<String, Long> boardCodeToBoradIdTemp = new TreeMap<String, Long>();
    Map<String, String> boardCodeToAffiliatedToTemp = new TreeMap<String, String>();

    Map<Long, String> boardIdToboardCodeTemp = new TreeMap<Long, String>();
    Map<Long, String> boardIdToBoardNameTemp = new TreeMap<Long, String>();
    Map<Long, String> boardIdToDisplayNameTemp = new TreeMap<Long, String>();
    Map<Long, String> boardIdToAffiliatedToTemp = new TreeMap<Long, String>();

    Map<String, String> displayNameToBoardNameTemp = new TreeMap<String, String>();
    Map<String, String> displayNameToBoardCodeTemp = new TreeMap<String, String>();
    Map<String, Long> displayNameToBoradIdTemp = new TreeMap<String, Long>();
    Map<String, String> displayNameToAffiliatedToTemp = new TreeMap<String, String>();

    Map<String, String> boardNameToBoardCodeTemp = new TreeMap<String, String>();
    Map<String, Long> boardNameToIdTemp = new TreeMap<String, Long>();
    Map<String, String> boardNameToDisplayNameTemp = new TreeMap<String, String>();
    Map<String, String> boardNameToAffiliatedToTemp = new TreeMap<String, String>();

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String query = String.format("SELECT %s, %s, %s, %s, %s FROM %s;", Tables.Board.id, Tables.Board.boardCode,
        Tables.Board.boardName, Tables.Board.boardDisplayName, Tables.Board.affiliatedTo, Tables.Board.table);
    try {
      System.out.println("---------------- 1 db = " + db);
      connection = db.getConnection();
      System.out.println("---------------- 2");
      preparedStatement = connection.prepareStatement(query);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        long id = resultSet.getLong(Tables.Board.id);
        String boardName = resultSet.getString(Tables.Board.boardName);
        String boardCode = resultSet.getString(Tables.Board.boardCode);
        String boardDisplayName = resultSet.getString(Tables.Board.boardDisplayName);
        String affiliatedTo = resultSet.getString(Tables.Board.affiliatedTo);

        schoolBoardNameListTemp.add(boardName);

        affiliatedToBoardNameTemp.put(affiliatedTo, boardName);
        affiliatedToDisplayNameTemp.put(affiliatedTo, boardDisplayName);
        affiliatedToIdTemp.put(affiliatedTo, id);
        affiliatedToBoardCodeTemp.put(affiliatedTo, boardCode);

        boardCodeToBoardNameTemp.put(boardCode, boardName);
        boardCodeToDisplayNameTemp.put(boardCode, boardDisplayName);
        boardCodeToBoradIdTemp.put(boardCode, id);
        boardCodeToAffiliatedToTemp.put(boardCode, affiliatedTo);

        boardIdToboardCodeTemp.put(id, boardCode);
        boardIdToBoardNameTemp.put(id, boardName);
        boardIdToDisplayNameTemp.put(id, boardDisplayName);
        boardIdToAffiliatedToTemp.put(id, affiliatedTo);

        displayNameToBoardNameTemp.put(boardDisplayName, boardName);
        displayNameToBoardCodeTemp.put(boardDisplayName, boardCode);
        displayNameToBoradIdTemp.put(boardDisplayName, id);
        displayNameToAffiliatedToTemp.put(boardDisplayName, affiliatedTo);

        boardNameToBoardCodeTemp.put(boardName, boardName);
        boardNameToIdTemp.put(boardName, id);
        boardNameToDisplayNameTemp.put(boardName, boardDisplayName);
        boardNameToAffiliatedToTemp.put(boardName, affiliatedTo);
      }

      schoolBoardList = schoolBoardNameListTemp;

      affiliatedToBoardName = affiliatedToBoardNameTemp;
      affiliatedToDisplayName = affiliatedToDisplayNameTemp;
      affiliatedToId = affiliatedToIdTemp;
      affiliatedToBoardCode = affiliatedToBoardCodeTemp;

      boardCodeToBoardName = boardCodeToBoardNameTemp;
      boardCodeToDisplayName = boardCodeToDisplayNameTemp;
      boardCodeToBoradId = boardCodeToBoradIdTemp;
      boardCodeToAffiliatedTo = boardCodeToAffiliatedToTemp;

      boardIdToBoardCode = boardIdToboardCodeTemp;
      boardIdToBoardName = boardIdToBoardNameTemp;
      boardIdToDisplayName = boardIdToDisplayNameTemp;
      boardIdToAffiliatedTo = boardIdToAffiliatedToTemp;

      displayNameToBoardName = displayNameToBoardNameTemp;
      displayNameToBoardCode = displayNameToBoardCodeTemp;
      displayNameToBoradId = displayNameToBoradIdTemp;
      displayNameToAffiliatedTo = displayNameToAffiliatedToTemp;

      boardNameToBoardCode = boardNameToBoardCodeTemp;
      boardNameToId = boardNameToIdTemp;
      boardNameToDisplayName = boardNameToDisplayNameTemp;
      boardNameToAffiliatedTo = boardNameToAffiliatedToTemp;
      System.out.println("<======= displayNameToBoardCode======>" + displayNameToBoardCode);
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {
      try {
        if (resultSet != null && !resultSet.isClosed())
          resultSet.close();

        if (preparedStatement != null && !preparedStatement.isClosed())
          preparedStatement.close();

        if (connection != null && !connection.isClosed())
          connection.close();

      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public List<String> getSchoolboardList() {
    if (schoolBoardList == null || schoolBoardList.isEmpty())
      fetchBoardList();

    return schoolBoardList;
  }

  public String getDisplayNameGivenBoardCode(String boardCode) {
    if (boardCodeToDisplayName == null || boardCodeToDisplayName.isEmpty())
      fetchBoardList();

    return boardCodeToDisplayName.get(boardCode.trim().toLowerCase());
  }

  public String getBoardNameGivenBoardCode(String boardCode) {
    if (boardCodeToBoardName == null || boardCodeToBoardName.isEmpty())
      fetchBoardList();

    return boardCodeToBoardName.get(boardCode.trim().toLowerCase());
  }

  public Long getIdGivenBoardCode(String boardCode) {
    if (boardCodeToBoradId == null || boardCodeToBoradId.isEmpty())
      fetchBoardList();

    return boardCodeToBoradId.get(boardCode.trim().toLowerCase());
  }

  public String getAffiliatedToGivenBoardCode(String boardCode) {
    if (boardCodeToAffiliatedTo == null || boardCodeToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardCodeToAffiliatedTo.get(boardCode.trim().toLowerCase());
  }

  public String getBoardCodeGivenId(Long id) {
    if (boardIdToBoardCode == null || boardIdToBoardCode.isEmpty())
      fetchBoardList();

    return boardIdToBoardCode.get(id);
  }

  public String getBoardNameGivenId(Long id) {
    if (boardIdToBoardName == null || boardIdToBoardName.isEmpty())
      fetchBoardList();

    return boardIdToBoardName.get(id);
  }

  public String getDisplayNameGivenId(Long id) {
    if (boardIdToDisplayName == null || boardIdToDisplayName.isEmpty())
      fetchBoardList();

    return boardIdToDisplayName.get(id);
  }

  public String getAffiliatedToGivenId(Long id) {
    if (boardIdToAffiliatedTo == null || boardIdToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardIdToAffiliatedTo.get(id);
  }

  public String getDisplayNameGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToDisplayName == null || affiliatedToDisplayName.isEmpty())
      fetchBoardList();

    return affiliatedToDisplayName.get(affiliatedTo.trim().toLowerCase());
  }

  public String getBoardNameGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToBoardName == null || affiliatedToBoardName.isEmpty())
      fetchBoardList();

    return affiliatedToBoardName.get(affiliatedTo.trim().toLowerCase());
  }

  public String getBoardCodeGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToBoardCode == null || affiliatedToBoardCode.isEmpty())
      fetchBoardList();

    return affiliatedToBoardCode.get(affiliatedTo.trim().toLowerCase());
  }

  public Long getBoardIdGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToId == null || affiliatedToId.isEmpty())
      fetchBoardList();

    return affiliatedToId.get(affiliatedTo.trim().toLowerCase());
  }

  public long getBoardIdGivenDisplayName(String displayName) {
    if (displayNameToBoradId == null || displayNameToBoradId.isEmpty())
      fetchBoardList();

    return displayNameToBoradId.get(displayName.trim().toLowerCase());
  }

  public String getBoardNameGivenDisplayName(String displayName) {
    if (displayNameToBoardName == null || displayNameToBoardName.isEmpty())
      fetchBoardList();

    return displayNameToBoardName.get(displayName.trim().toLowerCase());
  }

  public String getBoardCodeGivenDisplayName(String displayName) {
    if (displayNameToBoardCode == null || displayNameToBoardCode.isEmpty()) {
      System.out.println("againg fetching displayName");
      fetchBoardList();
    }
    System.out.println("********* " + displayNameToBoardCode.get(displayName.trim()));
    return displayNameToBoardCode.get(displayName.trim());
  }

  public String getAffiliatedToGivenDisplayName(String displayName) {
    if (displayNameToAffiliatedTo == null || displayNameToAffiliatedTo.isEmpty())
      fetchBoardList();

    return displayNameToAffiliatedTo.get(displayName.trim().toLowerCase());
  }

  public Long getBoardIdGivenBoardName(String boardName) {
    if (boardNameToId == null || boardNameToId.isEmpty())
      fetchBoardList();

    return boardNameToId.get(boardName.trim().toLowerCase());
  }

  public String getDisplayNameGivenBoardName(String boardName) {
    if (boardNameToDisplayName == null || boardNameToDisplayName.isEmpty())
      fetchBoardList();

    return boardNameToDisplayName.get(boardName.trim().toLowerCase());
  }

  public String getBoardCodeGivenBoardName(String boardName) {
    if (boardNameToBoardCode == null || boardNameToBoardCode.isEmpty())
      fetchBoardList();

    return boardNameToBoardCode.get(boardName.trim().toLowerCase());
  }

  public String getAffiliatedToGivenBoardName(String boardName) {
    if (boardNameToAffiliatedTo == null || boardNameToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardNameToAffiliatedTo.get(boardName.trim().toLowerCase());
  }
}





