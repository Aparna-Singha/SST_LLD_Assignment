import java.util.Map;

public class Validator {
  public static void validate(Map<String, String> kv) {
    String name = kv.getOrDefault("name", "");
    String email = kv.getOrDefault("email", "");
    String phone = kv.getOrDefault("phone", "");
    String program = kv.getOrDefault("program", "");

    if (name.isBlank())
      throw new RuntimeException("name is required");
    if (email.isBlank() || !email.contains("@"))
      throw new RuntimeException("email is invalid");
    if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit))
      throw new RuntimeException("phone is invalid");
    if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE")))
      throw new RuntimeException("program is invalid");

  }
}
