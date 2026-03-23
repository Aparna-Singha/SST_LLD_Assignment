public interface WritingBehavior {
    String getName();

    int calculateInkRequired(String text);

    int getWritableCharacterCount(int availableInkUnits);
}
