package Core.Position;

public enum Position {
    RIGHT("On the right of the Leap Motion"),
    LEFT("On the left of the Leap Motion"),
    FRONT("In front of the Leap Motion"),
    BACK("Between you and the Leap Motion"),
    NONE("None")
    ;

    private String message;
    Position(String message){
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
