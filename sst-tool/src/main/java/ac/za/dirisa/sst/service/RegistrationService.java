package ac.za.dirisa.sst.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.model.ConfirmationToken;
import ac.za.dirisa.sst.model.Institution;
import ac.za.dirisa.sst.model.User;
import ac.za.dirisa.sst.model.enums.UserRole;
import ac.za.dirisa.sst.properties.ApplicationProperties;
import ac.za.dirisa.sst.service.email.EmailService;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.mail.MessagingException;

@Service
public class RegistrationService {
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationService.class);

	@Autowired
	UserService userService;

	@Autowired
	ConfirmationTokenService confirmationTokenService;

	@Autowired
	EmailService emailService;

	@Autowired
	InstitutionService institutionService;

	@Autowired
	ApplicationProperties applicationProperties;

	public String register(RegistrationRequest registrationRequest) {

		String token = userService.signUpUser(new User(registrationRequest.getFirstName(),
				registrationRequest.getLastName(), registrationRequest.getEmail(), registrationRequest.getPassword(),
				UserRole.USER, registrationRequest.getDescription(), registrationRequest.getInstitution()));

		String link = applicationProperties.getSendLink() + token;

		Optional<Institution> findInstitutionById = institutionService
				.findById(registrationRequest.getInstitution().getId());

		// send email confirmation.
		try {
			emailService.sendMail(registrationRequest.getFirstName(), registrationRequest.getLastName(),
					findInstitutionById.get().getName(), registrationRequest.getEmail(),
					registrationRequest.getDescription(), applicationProperties.getEmailFrom(),
					applicationProperties.getEmailTo(), link, applicationProperties.getSubjectSend());

		} catch (UnsupportedEncodingException | MessagingException e) {
			LOG.error("Email service trace: error message {}", e.getMessage());
		}

		return link;
	}

	@Transactional
	public void confirmToken(String token) {

		ConfirmationToken confirmationToken = confirmationTokenService.findToken(token)
				.orElseThrow(() -> new IllegalStateException("token is not found."));

		if (confirmationToken.getConfirmedAt() != null) {

			throw new IllegalStateException("email is already confirmed.");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {

			throw new IllegalStateException("token is expired.");
		}

		confirmationTokenService.setConfirmedAt(token);
		userService.enableUser(confirmationToken.getUser().getEmail());

		// send back response for a verified user
		String link = applicationProperties.getResponseLink();

		// send email for approval
		try {
			emailService.sendMail(confirmationToken.getUser().getFirstName(), confirmationToken.getUser().getLastName(),
					confirmationToken.getUser().getEmail(), applicationProperties.getEmailFrom(), link,
					applicationProperties.getSubjectResponse());
		} catch (UnsupportedEncodingException | MessagingException e) {
			LOG.error("Email service trace: error message {}", e.getMessage());
		}

	}
}