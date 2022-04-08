package ac.za.dirisa.sst.service.email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	private static final String SPRING_LOGO_IMAGE = "static/images/dirisa.png";
	private static final String PNG_MIME = "image/png";

	public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(String firstName, String lastName, String institution, String emailId, String description,
			String from, String to, String url, String subject)
			throws MessagingException, UnsupportedEncodingException {
		Context context = new Context();
		context.setVariable("firstName", firstName);
		context.setVariable("lastName", lastName);
		context.setVariable("emailId", emailId);
		context.setVariable("institution", institution);
		context.setVariable("description", description);
		context.setVariable("url", url);
		context.setVariable("springLogo", SPRING_LOGO_IMAGE);

		String process = templateEngine.process("emails/welcome", context);
		javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setSubject(subject);
		helper.setText(process, true);
		helper.setTo(to);
		helper.setFrom(new InternetAddress(from, "Dirisa Team"));

		ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
		helper.addInline("springLogo", clr, PNG_MIME);

		javaMailSender.send(mimeMessage);
	}

	public void sendMail(String firstName, String lastName, String to, String from, String url, String subject)
			throws MessagingException, UnsupportedEncodingException {
		Context context = new Context();
		context.setVariable("firstName", firstName);
		context.setVariable("lastName", lastName);
		context.setVariable("url", url);
		context.setVariable("springLogo", SPRING_LOGO_IMAGE);

		String process = templateEngine.process("emails/confirm", context);
		javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setSubject(subject);
		helper.setText(process, true);
		helper.setTo(to);
		helper.setFrom(new InternetAddress(from, "Dirisa Team"));

		ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
		helper.addInline("springLogo", clr, PNG_MIME);

		javaMailSender.send(mimeMessage);
	}

	public void sendResetMail(String firstName, String lastName, String to, String from, String url, String subject)
			throws MessagingException, UnsupportedEncodingException {
		Context context = new Context();
		context.setVariable("firstName", firstName);
		context.setVariable("lastName", lastName);
		context.setVariable("url", url);
		context.setVariable("springLogo", SPRING_LOGO_IMAGE);

		String process = templateEngine.process("emails/reset", context);
		javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setSubject(subject);
		helper.setText(process, true);
		helper.setTo(to);
		helper.setFrom(new InternetAddress(from, "Dirisa Team"));

		ClassPathResource clr = new ClassPathResource(SPRING_LOGO_IMAGE);
		helper.addInline("springLogo", clr, PNG_MIME);

		javaMailSender.send(mimeMessage);
	}

}
