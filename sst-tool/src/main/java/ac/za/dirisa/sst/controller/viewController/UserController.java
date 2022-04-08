package ac.za.dirisa.sst.controller.viewController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.model.Institution;
import ac.za.dirisa.sst.repository.UserRepository;
import ac.za.dirisa.sst.service.InstitutionService;
import ac.za.dirisa.sst.service.RegistrationService;
import ac.za.dirisa.sst.service.reCapthaService.RecaptchaService;

@Controller
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	RegistrationService registrationService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	InstitutionService institutionService;
	
	@Autowired
	RecaptchaService captchaService;
	
	List<Institution> institutions;

	@ModelAttribute("user")
	public RegistrationRequest preLoad() {
		institutions = institutionService.findInstitutions();
		return new RegistrationRequest();
	}

	@GetMapping(path = "/subscribe/signup")
	public String showRegistrationPage(Model model) {		
		//Loading institutions 
		model.addAttribute("institutions", institutions);
		return "accounts/register";
	}

	@PostMapping(path = "/subscribe/signup")
	public String createUserAccount(@ModelAttribute("user") RegistrationRequest userDto,  
			@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
			HttpServletRequest request,
			Model model) {
		
		// recaptcha service
		String ip = request.getRemoteAddr();
		String captchaVerifyMessage = captchaService.verifyRecaptcha(ip, recaptchaResponse);

		if (StringUtils.isNotEmpty(captchaVerifyMessage)) {
			Map<String, Object> response = new HashMap<>();
			response.put("message", captchaVerifyMessage);
		}

		// user validation, the user must be unique
		if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
			LOG.info("Username should be unique.");
			model.addAttribute("message", "There is already an account registered with that email.");
			
			//reload/refresh institutions list after drop down 'POST'/submit
			model.addAttribute("institutions", institutions);
			return "accounts/register";
		}

		registrationService.register(userDto);

		return "redirect:/subscribe/accountRegistered";
	}

	@GetMapping(path = "/subscribe/confirm")
	public String confirm(@RequestParam("token") String token, Model model) {				
		try {
			registrationService.confirmToken(token);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			String error = e.getMessage();
			model.addAttribute("error", error);
			return "accounts/confirmDenied";
		}
		
		return "redirect:/subscribe/accountVerified";
	}
}
