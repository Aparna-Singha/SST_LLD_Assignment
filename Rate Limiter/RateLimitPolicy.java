import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RateLimitPolicy {

    private final String name;
    private final List<LimitRule> rules;

    public RateLimitPolicy(String name, List<LimitRule> rules) {
        if (name == null) throw new IllegalArgumentException("Policy name cannot be null");
        if (rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("Need at least one rule in the policy");
        }
        this.name = name;
        this.rules = Collections.unmodifiableList(new ArrayList<>(rules));
    }

    public String getName() {
        return name;
    }

    public List<LimitRule> getRules() {
        return rules;
    }

    @Override
    public String toString() {
        return "RateLimitPolicy[" + name + ", rules=" + rules + "]";
    }
}
