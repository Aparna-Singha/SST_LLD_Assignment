import java.util.*;

public class DefaultProgramCatalog implements ProgramCatalog {
    private final Set<String> allowed;

    public DefaultProgramCatalog(Collection<String> allowedPrograms) {
        this.allowed = new HashSet<>(allowedPrograms);
    }

    @Override
    public boolean isAllowed(String program) {
        return allowed.contains(program);
    }
}
