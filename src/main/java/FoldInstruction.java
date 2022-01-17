public class FoldInstruction {
    private char axis;
    private int foldline;

    public char getAxis() {
        return axis;
    }

    public void setAxis(char axis) {
        this.axis = axis;
    }

    public int getFoldline() {
        return foldline;
    }

    public void setFoldline(int foldline) {
        this.foldline = foldline;
    }

    public FoldInstruction(char axis, int foldline) {
        this.foldline = foldline;
        this.axis = axis;
    }
}
