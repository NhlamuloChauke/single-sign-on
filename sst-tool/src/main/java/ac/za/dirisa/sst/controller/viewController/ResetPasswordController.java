package ac.za.dirisa.sst.controller.viewController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.model.User;
import ac.za.dirisa.sst.service.UserService;

@Controller
public class ResetPasswordController {
	private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordController.class);

	
	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute("user")
	public RegistrationRequest RegistrationRequest() {
		return new RegistrationRequest();
	}

	@GetMapping(path = "/subscribe/resetPassword")
	public String ShowResetPasswordPage(@Param(value = "token") String token, Model model) {
		RegistrationRequest user = new RegistrationRequest();
		model.addAttribute("token", token);
		model.addAttribute("user", user);
		return "accounts/reset";
	}

	@PostMapping(path = "/subscribe/resetPassword")
	public String updatePassword(@ModelAttribute("user") @Valid RegistrationRequest user, HttpServletRequest request,
			Model model) {
		
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		LOG.info("pasword {}", password);
		
		User userToken = userService.getByResetPasswordToken(token);
		if(userToken == null) {
			LOG.info("Token to reset password not found.");
			model.addAttribute("message", "The link to reset the password is not valid, the token to reset the password has expired.");
			return "accounts/reset";
		}
		String encodedPassword = bCryptPasswordEncoder.encode(password);

		userService.updatePassword(userToken, encodedPassword);

		return "redirect:/subscribe/resetPasswordSuccess";

	}
}
