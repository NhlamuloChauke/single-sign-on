package ac.za.dirisa.sst.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForgotPasswordSuccessController {
	@GetMapping(path = "/subscribe/forgotPasswordSuccess")
	public String ShowAAccountAddedPage() {
		return "accounts/forgotPasswordSuccess";
	}
}
