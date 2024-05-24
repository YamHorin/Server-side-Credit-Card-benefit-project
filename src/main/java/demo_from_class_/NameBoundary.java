package demo_from_class_;

public class NameBoundary {
	private String firstName;
	private String lastName;

	public NameBoundary() {
	}

	public NameBoundary(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "NameBoundary [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
