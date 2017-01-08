package enum_package;

public enum TeacherAppointment {
  TEMPORARY("temporary"),
  PERMANENT("permanent"),
  GUEST("guest");

  private String value;
  private TeacherAppointment(String value) {
    this.value = value;
  }
}
