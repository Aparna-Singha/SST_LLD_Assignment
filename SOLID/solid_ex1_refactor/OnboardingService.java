import java.util.*;

public class OnboardingService {
    private final StudentRawParser parser;
    private final StudentValidator validator;
    private final StudentIdGenerator idGenerator;
    private final StudentRepository repo;
    private final OnboardingPresenter presenter;

    public OnboardingService(
            StudentRawParser parser,
            StudentValidator validator,
            StudentIdGenerator idGenerator,
            StudentRepository repo,
            OnboardingPresenter presenter
    ) {
        this.parser = parser;
        this.validator = validator;
        this.idGenerator = idGenerator;
        this.repo = repo;
        this.presenter = presenter;
    }

    public void registerFromRawInput(String raw) {
        presenter.showInput(raw);

        StudentInput input = parser.parse(raw);
        List<String> errors = validator.validate(input);
        if (!errors.isEmpty()) {
            presenter.showErrors(errors);
            return;
        }

        String id = idGenerator.nextId(repo.count());
        StudentRecord rec = new StudentRecord(id, input.name, input.email, input.phone, input.program);
        repo.save(rec);
        presenter.showSuccess(rec, repo.count());
    }
}
