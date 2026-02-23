import java.util.*;

public class OnboardingService {
    private final StudentRepository db;
    private final Logger logger;

    public OnboardingService(StudentRepository repo, Logger logger) {
        this.db = repo;
        this.logger = logger;
    }

    public void registerFromRawInput(String raw) {
        logger.debugArgs(raw);

        Map<String, String> kv = Parser.parse(raw);
        Validator.validate(kv);
        
        StudentRecord saved = db.save(kv);
        logger.successLog(saved);
    }
}
