package Application.business_logic;

public class ObjectId {
    private String superApp;

    private String id;

    public ObjectId() {
    }

    public void setSuperApp(String superApp) {
        this.superApp = superApp;
    }

    public String getSuperApp() {
        return this.superApp;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ObjectId{" +
                "id='" + id + "/n" +
                ", superApp='" + superApp + "/n" +
                '}';
    }


}
