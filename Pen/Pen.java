public class Pen implements Writable, Closable {
    private final String brand;
    private final PenType penType;
    private final boolean refillSupported;
    private final Ink ink;
    private PenState state;
    private WritingBehavior writingBehavior;

    public Pen(String brand, PenType penType, Ink ink, WritingBehavior writingBehavior, boolean refillSupported) {
        if (brand == null || brand.trim().isEmpty()) {
            throw new IllegalArgumentException("Brand cannot be empty.");
        }

        if (penType == null) {
            throw new IllegalArgumentException("Pen type cannot be null.");
        }

        if (ink == null) {
            throw new IllegalArgumentException("Ink cannot be null.");
        }

        if (writingBehavior == null) {
            throw new IllegalArgumentException("Writing behavior cannot be null.");
        }

        this.brand = brand.trim();
        this.penType = penType;
        this.refillSupported = refillSupported;
        this.ink = ink;
        this.writingBehavior = writingBehavior;
        this.state = PenState.CLOSED;
    }

    public String getBrand() {
        return brand;
    }

    public PenType getPenType() {
        return penType;
    }

    public InkColor getInkColor() {
        return ink.getColor();
    }

    public int getInkLevel() {
        return ink.getCurrentLevel();
    }

    public int getInkCapacity() {
        return ink.getCapacity();
    }

    public PenState getState() {
        return state;
    }

    public WritingBehavior getWritingBehavior() {
        return writingBehavior;
    }

    public void setWritingBehavior(WritingBehavior writingBehavior) {
        if (writingBehavior == null) {
            throw new IllegalArgumentException("Writing behavior cannot be null.");
        }

        this.writingBehavior = writingBehavior;
    }

    public boolean isInkEmpty() {
        return ink.isEmpty();
    }

    public boolean supportsRefill() {
        return refillSupported;
    }

    public void refill(int amount) {
        if (!refillSupported) {
            throw new UnsupportedOperationException("This pen is disposable and cannot be refilled.");
        }

        ink.refill(amount);
    }

    @Override
    public void open() {
        state = PenState.OPEN;
    }

    @Override
    public void close() {
        state = PenState.CLOSED;
    }

    @Override
    public String write(String text) {
        if (state != PenState.OPEN) {
            throw new IllegalStateException("Cannot write when the pen is closed.");
        }

        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text to write cannot be empty.");
        }

        if (ink.isEmpty()) {
            throw new IllegalStateException("Cannot write because ink is empty.");
        }

        int writableCharacters = writingBehavior.getWritableCharacterCount(ink.getCurrentLevel());

        if (writableCharacters <= 0) {
            throw new IllegalStateException("Current ink level is not enough to write.");
        }

        String actualText = text.substring(0, Math.min(text.length(), writableCharacters));
        int inkRequired = writingBehavior.calculateInkRequired(actualText);
        ink.consume(inkRequired);

        return actualText;
    }

    @Override
    public String toString() {
        return "Pen{"
            + "brand='" + brand + '\''
            + ", penType=" + penType
            + ", inkColor=" + ink.getColor()
            + ", inkLevel=" + ink.getCurrentLevel()
            + ", state=" + state
            + ", writingBehavior=" + writingBehavior.getName()
            + ", refillable=" + supportsRefill()
            + '}';
    }
}
