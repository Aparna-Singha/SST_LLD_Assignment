public class RoughWritingBehavior implements WritingBehavior {
    private static final int CHARACTERS_PER_INK_UNIT = 8;

    @Override
    public String getName() {
        return "Rough Writing";
    }

    @Override
    public int calculateInkRequired(String text) {
        validateText(text);
        return (text.length() + CHARACTERS_PER_INK_UNIT - 1) / CHARACTERS_PER_INK_UNIT;
    }

    @Override
    public int getWritableCharacterCount(int availableInkUnits) {
        if (availableInkUnits < 0) {
            throw new IllegalArgumentException("Available ink units cannot be negative.");
        }

        return availableInkUnits * CHARACTERS_PER_INK_UNIT;
    }

    private void validateText(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty.");
        }
    }
}
