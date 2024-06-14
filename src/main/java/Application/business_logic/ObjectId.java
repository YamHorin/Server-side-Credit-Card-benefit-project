package Application.business_logic;

import org.springframework.beans.factory.annotation.Value;

public class ObjectId {
    private String superApp;
    private String id;

    public ObjectId() {
    	
    }
    
    public ObjectId(String superApp, String id) {
		this.superApp = superApp;
		this.id = id;
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
