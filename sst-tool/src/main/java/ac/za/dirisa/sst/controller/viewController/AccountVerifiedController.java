package ac.za.dirisa.sst.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountVerifiedController {
	@GetMapping(path = "/subscribe/accountVerified")
	public String ShowAAccountAddedPage() {
		return "accounts/accountVerified";
	}
}
