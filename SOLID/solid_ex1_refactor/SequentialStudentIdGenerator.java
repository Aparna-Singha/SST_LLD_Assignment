public class SequentialStudentIdGenerator implements StudentIdGenerator {
    @Override
    public String nextId(int currentCount) {
        return IdUtil.nextStudentId(currentCount);
    }
}
