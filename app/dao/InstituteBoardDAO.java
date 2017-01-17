package dao;

import java.util.List;

public interface InstituteBoardDAO {
  public List<String> getSchoolboardList();
  public String getDisplayNameGivenBoardCode(String boardCode);
  public String getBoardNameGivenBoardCode(String boardCode);
  public Long getIdGivenBoardCode(String boardCode);
  public String getAffiliatedToGivenBoardCode(String boardCode);
  public String getBoardCodeGivenId(Long id);
  public String getBoardNameGivenId(Long id);
  public String getDisplayNameGivenId(Long id);
  public String getAffiliatedToGivenId(Long id);
  public String getDisplayNameGivenAffiliatedTo(String affiliatedTo);
  public String getBoardNameGivenAffiliatedTo(String affiliatedTo);
  public String getBoardCodeGivenAffiliatedTo(String affiliatedTo);
  public Long getBoardIdGivenAffiliatedTo(String affiliatedTo);
  public long getBoardIdGivenDisplayName(String displayName);
  public String getBoardNameGivenDisplayName(String displayName);
  public String getBoardCodeGivenDisplayName(String displayName);
  public String getAffiliatedToGivenDisplayName(String displayName);
  public Long getBoardIdGivenBoardName(String boardName);
  public String getDisplayNameGivenBoardName(String boardName);
  public String getBoardCodeGivenBoardName(String boardName);
  public String getAffiliatedToGivenBoardName(String boardName);
}
