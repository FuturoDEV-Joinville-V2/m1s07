package model;

public class Student {
	
	private Integer registration;
	private String name;
	private String email;
	
	public Integer getRegistration() {
		return registration;
	}
	
	public void setRegistration(Integer registration) {
		this.registration = registration;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Student [registration=" + registration + ", name=" + name + ", email=" + email + "]";
	}
	
	
	
}
