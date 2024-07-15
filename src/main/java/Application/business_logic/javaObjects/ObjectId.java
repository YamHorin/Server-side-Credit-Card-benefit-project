package Application.business_logic.javaObjects;

import org.springframework.beans.factory.annotation.Value;

public class ObjectId {
    private String superapp;
    private String id;

    public ObjectId() {
    	
    }
    
    public ObjectId(String superapp, String id) {
		this.superapp = superapp;
		this.id = id;
	}

	public ObjectId(String string) {
		this.id = string;	
		
	}

	public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getSuperapp() {
        return this.superapp;
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
                ", superapp='" + superapp + "/n" +
                '}';
    }

}
