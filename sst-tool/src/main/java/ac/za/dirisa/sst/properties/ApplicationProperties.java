package ac.za.dirisa.sst.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app") 
public class ApplicationProperties {
	
	private String SqlDriver;
    private String sendLink;
    private String responseLink;
    private String emailFrom;
    private String emailTo;
    private String subjectSend;
    private String subjectResponse;
    private String resetSubjectResponse;
    private String respondLinkPassword;
    
	public String getSqlDriver() {
		return SqlDriver;
	}
	public void setSqlDriver(String sqlDriver) {
		SqlDriver = sqlDriver;
	}
	public String getSendLink() {
		return sendLink;
	}
	public void setSendLink(String sendLink) {
		this.sendLink = sendLink;
	}
	public String getResponseLink() {
		return responseLink;
	}
	public void setResponseLink(String responseLink) {
		this.responseLink = responseLink;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getSubjectSend() {
		return subjectSend;
	}
	public void setSubjectSend(String subjectSend) {
		this.subjectSend = subjectSend;
	}
	public String getSubjectResponse() {
		return subjectResponse;
	}
	public void setSubjectResponse(String subjectResponse) {
		this.subjectResponse = subjectResponse;
	}
	public String getResetSubjectResponse() {
		return resetSubjectResponse;
	}
	public void setResetSubjectResponse(String resetSubjectResponse) {
		this.resetSubjectResponse = resetSubjectResponse;
	}
	public String getRespondLinkPassword() {
		return respondLinkPassword;
	}
	public void setRespondLinkPassword(String respondLinkPassword) {
		this.respondLinkPassword = respondLinkPassword;
	}
}
