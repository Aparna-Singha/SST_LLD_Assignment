import java.util.*;

public class StudentValidator {
    private final ProgramCatalog programs;

    public StudentValidator(ProgramCatalog programs) {
        this.programs = programs;
    }

    public List<String> validate(StudentInput in) {
        List<String> errors = new ArrayList<>();

        if (in.name.isBlank()) errors.add("name is required");
        if (in.email.isBlank() || !in.email.contains("@")) errors.add("email is invalid");
        if (in.phone.isBlank() || !in.phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!programs.isAllowed(in.program)) errors.add("program is invalid");

        return errors;
    }
}
