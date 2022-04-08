package ac.za.dirisa.sst.controller.viewController;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.model.User;
import ac.za.dirisa.sst.properties.ApplicationProperties;
import ac.za.dirisa.sst.repository.UserRepository;
import ac.za.dirisa.sst.service.UserService;
import ac.za.dirisa.sst.service.email.EmailService;
import ac.za.dirisa.sst.utils.GetSiteURLUtility;
import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForgotPasswordController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ApplicationProperties appProperties;
	
    @ModelAttribute("user")
    public RegistrationRequest RegistrationRequest() {
       return new RegistrationRequest();
    }
	
	@GetMapping(path = "/subscribe/forgotPassword")
	public String showResetForgotPasswordPage(Model model) {
		RegistrationRequest registrationRequest = new RegistrationRequest();
	    model.addAttribute("user", registrationRequest);
		return "accounts/forgot";
	}
	
	@PostMapping(path = "/subscribe/forgotPassword")
	public String ForgotPassword(@ModelAttribute("user") @Valid RegistrationRequest userDto, Model model, HttpServletRequest request) 
			throws UnsupportedEncodingException, MessagingException {
    	String token = RandomString.make(30);
    	
    	Optional<User> findExistingUserByEmail = userRepository.findByEmail(userDto.getEmail());
    	
		if (!findExistingUserByEmail.isPresent()) {
			LOG.info("Username not found.");
			model.addAttribute("message", "Unable to find the account, there is no account registered with that email.");
			return "accounts/forgot";
		}

		userService.updateResetPasswordToken(token, userDto.getEmail());
    	String resetPasswordLink = GetSiteURLUtility.getSiteURL(request) + appProperties.getRespondLinkPassword() + token;
    	
    	 // send email to reset password	             
   	     emailService.sendResetMail(findExistingUserByEmail.get().getFirstName(), 
   	    		                    findExistingUserByEmail.get().getLastName(), 
   	    		                    userDto.getEmail(), appProperties.getEmailFrom(), 
   	    		                    resetPasswordLink, appProperties.getResetSubjectResponse());
   	 
    	
    	LOG.info(resetPasswordLink);
    	
		return "redirect:/subscribe/forgotPasswordSuccess";
	}
	

}
