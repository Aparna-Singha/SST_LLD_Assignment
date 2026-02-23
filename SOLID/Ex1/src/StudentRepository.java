import java.util.List;
import java.util.Map;

public interface StudentRepository {

  public StudentRecord save(Map<String, String> kv);

  public int count();

  public List<StudentRecord> all();

}
