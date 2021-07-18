package com.epam.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "swagger.ui")
public class SwaggerCustomUIConfig {

		private String title;
		private String description;
		private String version;
		private String contactname;
		private String contacturl;
		private String contactemail;
		private String licence;
		private String licenceurl;
		
		
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
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getContactname() {
			return contactname;
		}
		public void setContactname(String contactname) {
			this.contactname = contactname;
		}
		public String getContacturl() {
			return contacturl;
		}
		public void setContacturl(String contacturl) {
			this.contacturl = contacturl;
		}
		public String getContactemail() {
			return contactemail;
		}
		public void setContactemail(String contactemail) {
			this.contactemail = contactemail;
		}
		public String getLicence() {
			return licence;
		}
		public void setLicence(String licence) {
			this.licence = licence;
		}
		public String getLicenceurl() {
			return licenceurl;
		}
		public void setLicenceurl(String licenceurl) {
			this.licenceurl = licenceurl;
		}
		
		
		
		
}
