import java.util.*;

public class FakeDb implements StudentRepository {
    private final List<StudentRecord> rows = new ArrayList<>();

    public int count() {
        return rows.size();
    }

    public List<StudentRecord> all() {
        return Collections.unmodifiableList(rows);
    }

    public StudentRecord save(Map<String, String> kv) {
        String id = IdUtil.nextStudentId();
        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        rows.add(rec);
        return rec;
    }
}
