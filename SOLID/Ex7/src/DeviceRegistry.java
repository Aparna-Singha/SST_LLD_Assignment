import java.util.*;

public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();

    public void add(Object d) { devices.add(d); }

    public <T> T getFirstOfType(Class<T> type) {
        for (Object d : devices) {
            if (type.isInstance(d)) return type.cast(d);
        }
        throw new IllegalStateException("Missing: " + type.getSimpleName());
    }
}
