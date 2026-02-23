public class IdUtil {
    private static int studentIdCounter = 0;

    public static String nextStudentId() {
        studentIdCounter++;
        String num = String.format("%04d", studentIdCounter);
        return "SST-2026-" + num;
    }
}
