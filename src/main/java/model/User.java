package model;

public class User {
	
	 private String id;
		private String login;
		private String email;
		private String password;
		private String preference;
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id=id;
		}
		
		public String getLogin() {
			return login;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getPreference() {
			return preference;
		}
		public void setPreference(String preference) {
			this.preference = preference;
		}
	    

}
