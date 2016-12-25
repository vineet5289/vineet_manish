package enum_package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SchoolClassEnum {
	kindergarten("Nursery(Kindergarten)"),
	lkg("Lower Kindergarten(LKG)"),
	ukg("Upper Kindergarten(UKG)"),
	first("1st Standard"),
	second("2nd Standard"),
	third("3rd Standard"),
	fourth("4th Standard"),
	fifth("5th Standard"),
	sixth("6th Standard"),
	seventh("7th Standard"),
	eighth("8th Standard"),
	ninth("9th Standard"),
	tenth("10th Standard"),
	eleventh("11th Standard"),
	twelth("12th Standard");
	
	private String value;
	private SchoolClassEnum(String value) {
		this.value = value;
	}
  private static final List<String> classDisplayName = new ArrayList<String>(SchoolClassEnum.values().length);
  private static final Map<String, Integer> classNameWithSeq = new HashMap<String, Integer>(SchoolClassEnum.values().length);
  private static final Map<Integer, String> seqToClassName = new HashMap<Integer, String>(SchoolClassEnum.values().length);
  private static final Map<SchoolClassEnum, Integer> classEnumWithSeq = new HashMap<SchoolClassEnum, Integer>(SchoolClassEnum.values().length);
  private static final Map<Integer, SchoolClassEnum> seqToClassEnum = new HashMap<Integer, SchoolClassEnum>(SchoolClassEnum.values().length);

	static {
	  int seq = 1;
		for(SchoolClassEnum sce : SchoolClassEnum.values()) {
			classDisplayName.add(sce.value);
      classNameWithSeq.put(sce.value.toLowerCase(), seq);
      seqToClassName.put(seq, sce.value);
      classEnumWithSeq.put(sce, seq);
      seqToClassEnum.put(seq, sce);
      seq++;
		}
	}

	public static int of(SchoolClassEnum value) {
	  return classEnumWithSeq.containsKey(value) ? classEnumWithSeq.get(value) : -1;
  }

	public static int of(String className) {
		return classNameWithSeq.containsKey(className.trim().toLowerCase()) ?
        classNameWithSeq.get(className.trim().toLowerCase()) : -1;
	}

	public static String of(int seq) {
		return seqToClassName.containsKey(seq) ? seqToClassName.get(seq) : "";
	}

  public static boolean contains(String className) {
    return classNameWithSeq.containsKey(className.trim().toLowerCase());
  }

  public static boolean contains(int seq) {
    return seqToClassName.containsKey(seq);
  }

	public static List<String> getClassDisplayName() {
		return classDisplayName;
	}

  public static Map<Integer, String> seqToClassNameMapping() {
    return seqToClassName;
  }
}
