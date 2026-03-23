public class Main {
    public static void main(String[] args) {
        Pen ballPen = new Pen(
            "Reynolds",
            PenType.BALL_PEN,
            new Ink(InkColor.BLUE, 100, 20),
            new SmoothWritingBehavior(),
            false
        );

        ballPen.open();
        System.out.println("Ball pen output: " + ballPen.write("This is a disposable ball pen."));
        ballPen.setWritingBehavior(new RoughWritingBehavior());
        System.out.println("Ball pen output after behavior change: " + ballPen.write("Behavior can change at runtime."));
        ballPen.close();

        Pen gelPen = new Pen(
            "Pilot",
            PenType.GEL_PEN,
            new Ink(InkColor.BLACK, 100, 6),
            new PressureBasedWritingBehavior(),
            true
        );

        gelPen.open();
        System.out.println("Gel pen output: " + gelPen.write("Pressure writing consumes more ink."));
        gelPen.refill(20);
        gelPen.setWritingBehavior(new SmoothWritingBehavior());
        System.out.println("Gel pen output after refill: " + gelPen.write("Now it writes more efficiently."));
        gelPen.close();
    }
}
