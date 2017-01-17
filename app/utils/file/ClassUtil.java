package utils.file;

import enum_package.SchoolClassEnum;

public class ClassUtil {
  public static boolean isValidClassRange(int classStartIndex, int classEndIndex) {
    if(classStartIndex < 1 || classEndIndex < 1 || classStartIndex > classEndIndex) {
      return false;
    }
    return SchoolClassEnum.contains(classStartIndex) &&  SchoolClassEnum.contains(classEndIndex);
  }
}
