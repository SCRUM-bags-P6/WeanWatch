package WeanWatch.model;

public class Personnel {
 
    private String name;
    private String id;

    public Personnel(String name, String id) {
        this.name = name;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public String getID() {
        return id;
    }

}
