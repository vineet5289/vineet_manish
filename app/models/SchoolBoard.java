package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import dao.Tables;
import lombok.Data;
import play.db.Database;
import play.db.NamedDatabase;

@Data
public class SchoolBoard {
  @Inject
  @NamedDatabase("srp")
  private static Database db;

  private static List<String> schoolBoardList = new ArrayList<String>();

  private static Map<String, String> affiliatedToBoardName = new TreeMap<String, String>();
  private static Map<String, String> affiliatedToDisplayName = new TreeMap<String, String>();
  private static Map<String, Long> affiliatedToId = new TreeMap<String, Long>();
  private static Map<String, String> affiliatedToBoardCode = new TreeMap<String, String>();

  private static Map<String, String> boardCodeToBoardName = new TreeMap<String, String>();
  private static Map<String, String> boardCodeToDisplayName = new TreeMap<String, String>();
  private static Map<String, Long> boardCodeToBoradId = new TreeMap<String, Long>();
  private static Map<String, String> boardCodeToAffiliatedTo = new TreeMap<String, String>();

  private static Map<Long, String> boardIdToBoardCode = new TreeMap<Long, String>();
  private static Map<Long, String> boardIdToBoardName = new TreeMap<Long, String>();
  private static Map<Long, String> boardIdToDisplayName = new TreeMap<Long, String>();
  private static Map<Long, String> boardIdToAffiliatedTo = new TreeMap<Long, String>();

  private static Map<String, String> displayNameToBoardName = new TreeMap<String, String>();
  private static Map<String, String> displayNameToBoardCode = new TreeMap<String, String>();
  private static Map<String, Long> displayNameToBoradId = new TreeMap<String, Long>();
  private static Map<String, String> displayNameToAffiliatedTo = new TreeMap<String, String>();

  private static Map<String, String> boardNameToBoardCode = new TreeMap<String, String>();
  private static Map<String, Long> boardNameToId = new TreeMap<String, Long>();
  private static Map<String, String> boardNameToDisplayName = new TreeMap<String, String>();
  private static Map<String, String> boardNameToAffiliatedTo = new TreeMap<String, String>();

  private long id;
  private String boardName;
  private String boardCode;
  private String boardDisplayName;
  private String affiliatedTo;

  private static synchronized void fetchBoardList() {
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
      connection = db.getConnection();
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

  public static synchronized List<String> getSchoolboardList() {
    if (schoolBoardList == null || schoolBoardList.isEmpty())
      fetchBoardList();

    return schoolBoardList;
  }

  public static synchronized String getDisplayNameGivenBoardCode(String boardCode) {
    if (boardCodeToDisplayName == null || boardCodeToDisplayName.isEmpty())
      fetchBoardList();

    return boardCodeToDisplayName.get(boardCode.trim().toLowerCase());
  }

  public static synchronized String getBoardNameGivenBoardCode(String boardCode) {
    if (boardCodeToBoardName == null || boardCodeToBoardName.isEmpty())
      fetchBoardList();

    return boardCodeToBoardName.get(boardCode.trim().toLowerCase());
  }

  public static synchronized Long getIdGivenBoardCode(String boardCode) {
    if (boardCodeToBoradId == null || boardCodeToBoradId.isEmpty())
      fetchBoardList();

    return boardCodeToBoradId.get(boardCode.trim().toLowerCase());
  }

  public static synchronized String getAffiliatedToGivenBoardCode(String boardCode) {
    if (boardCodeToAffiliatedTo == null || boardCodeToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardCodeToAffiliatedTo.get(boardCode.trim().toLowerCase());
  }

  public static synchronized String getBoardCodeGivenId(Long id) {
    if (boardIdToBoardCode == null || boardIdToBoardCode.isEmpty())
      fetchBoardList();

    return boardIdToBoardCode.get(id);
  }

  public static synchronized String getBoardNameGivenId(Long id) {
    if (boardIdToBoardName == null || boardIdToBoardName.isEmpty())
      fetchBoardList();

    return boardIdToBoardName.get(id);
  }

  public static synchronized String getDisplayNameGivenId(Long id) {
    if (boardIdToDisplayName == null || boardIdToDisplayName.isEmpty())
      fetchBoardList();

    return boardIdToDisplayName.get(id);
  }

  public static synchronized String getAffiliatedToGivenId(Long id) {
    if (boardIdToAffiliatedTo == null || boardIdToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardIdToAffiliatedTo.get(id);
  }

  public static synchronized String getDisplayNameGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToDisplayName == null || affiliatedToDisplayName.isEmpty())
      fetchBoardList();

    return affiliatedToDisplayName.get(affiliatedTo.trim().toLowerCase());
  }

  public static synchronized String getBoardNameGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToBoardName == null || affiliatedToBoardName.isEmpty())
      fetchBoardList();

    return affiliatedToBoardName.get(affiliatedTo.trim().toLowerCase());
  }

  public static synchronized String getBoardCodeGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToBoardCode == null || affiliatedToBoardCode.isEmpty())
      fetchBoardList();

    return affiliatedToBoardCode.get(affiliatedTo.trim().toLowerCase());
  }

  public static synchronized Long getBoardIdGivenAffiliatedTo(String affiliatedTo) {
    if (affiliatedToId == null || affiliatedToId.isEmpty())
      fetchBoardList();

    return affiliatedToId.get(affiliatedTo.trim().toLowerCase());
  }

  public static synchronized long getBoardIdGivenDisplayName(String displayName) {
    if (displayNameToBoradId == null || displayNameToBoradId.isEmpty())
      fetchBoardList();

    return displayNameToBoradId.get(displayName.trim().toLowerCase());
  }

  public static synchronized String getBoardNameGivenDisplayName(String displayName) {
    if (displayNameToBoardName == null || displayNameToBoardName.isEmpty())
      fetchBoardList();

    return displayNameToBoardName.get(displayName.trim().toLowerCase());
  }

  public static synchronized String getBoardCodeGivenDisplayName(String displayName) {
    if (displayNameToBoardCode == null || displayNameToBoardCode.isEmpty())
      fetchBoardList();

    return displayNameToBoardCode.get(displayName.trim().toLowerCase());
  }

  public static synchronized String getAffiliatedToGivenDisplayName(String displayName) {
    if (displayNameToAffiliatedTo == null || displayNameToAffiliatedTo.isEmpty())
      fetchBoardList();

    return displayNameToAffiliatedTo.get(displayName.trim().toLowerCase());
  }

  public static synchronized Long getBoardIdGivenBoardName(String boardName) {
    if (boardNameToId == null || boardNameToId.isEmpty())
      fetchBoardList();

    return boardNameToId.get(boardName.trim().toLowerCase());
  }

  public static synchronized String getDisplayNameGivenBoardName(String boardName) {
    if (boardNameToDisplayName == null || boardNameToDisplayName.isEmpty())
      fetchBoardList();

    return boardNameToDisplayName.get(boardName.trim().toLowerCase());
  }

  public static synchronized String getBoardCodeGivenBoardName(String boardName) {
    if (boardNameToBoardCode == null || boardNameToBoardCode.isEmpty())
      fetchBoardList();

    return boardNameToBoardCode.get(boardName.trim().toLowerCase());
  }

  public static synchronized String getAffiliatedToGivenBoardName(String boardName) {
    if (boardNameToAffiliatedTo == null || boardNameToAffiliatedTo.isEmpty())
      fetchBoardList();

    return boardNameToAffiliatedTo.get(boardName.trim().toLowerCase());
  }
}





