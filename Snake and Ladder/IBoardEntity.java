public interface IBoardEntity {
    int getStart();

    int getEnd();

    default int moveTo() {
        return getEnd();
    }
}
