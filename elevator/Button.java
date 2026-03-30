public abstract class Button {
    protected boolean pressed;
    protected final ButtonType buttonType;

    protected Button(ButtonType buttonType) {
        this.buttonType = buttonType;
        this.pressed = false;
    }

    public void press() {
        this.pressed = true;
    }

    public void reset() {
        this.pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }
}
