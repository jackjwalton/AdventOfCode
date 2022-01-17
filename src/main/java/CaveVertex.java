public class CaveVertex {


    private String name;
    private boolean isLargeCave;
    private boolean wasVisited;

    public CaveVertex(String name) {
        System.out.println("Creating vertex object: " + name);
        this.name = name;

        char c = name.charAt(0);
        this.isLargeCave = (65 <= c && c <= 90);
        this.wasVisited = false;
        System.out.println("isLargeCave: " + isLargeCave);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLargeCave() {
        return isLargeCave;
    }

    public void setLargeCave(boolean largeCave) {
        isLargeCave = largeCave;
    }

    public boolean hasBeenVisited() {
        return wasVisited;
    }

    public void setWasVisited(boolean wasVisited) {
        this.wasVisited = wasVisited;
    }
}
