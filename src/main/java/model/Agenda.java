package model;

import java.sql.Date;

public class Agenda {
	
	 private String id;
		private String title;
		private String description;
		private Date creationDate;
		private Date conclusionDate;
		private String status;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getCreationDate() {
			return creationDate;
		}
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}
		public Date getConclusionDate() {
			return conclusionDate;
		}
		public void setConclusionDate(Date conclusionDate) {
			this.conclusionDate = conclusionDate;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}

}
