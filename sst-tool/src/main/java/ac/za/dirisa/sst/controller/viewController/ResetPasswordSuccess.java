package ac.za.dirisa.sst.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetPasswordSuccess {
	@GetMapping(path = "/subscribe/resetPasswordSuccess")
	public String ShowAAccountAddedPage() {
		return "accounts/resetPasswordSuccess";
	}
}
