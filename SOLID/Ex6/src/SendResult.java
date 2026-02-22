public class SendResult {
  public final boolean ok;
  public final String error;

  private SendResult(boolean ok, String error) {
    this.ok = ok;
    this.error = error;
  }

  public static SendResult ok() {
    return new SendResult(true, null);
  }

  public static SendResult error(String msg) {
    return new SendResult(false, msg);
  }
}