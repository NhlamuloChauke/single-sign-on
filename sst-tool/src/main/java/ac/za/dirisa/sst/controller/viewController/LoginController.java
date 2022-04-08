package ac.za.dirisa.sst.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping(path = "/subscribe/login")
	public String showLoginPage() {		
		return "accounts/login";
	}
}
