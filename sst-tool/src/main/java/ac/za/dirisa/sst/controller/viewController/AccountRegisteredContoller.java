package ac.za.dirisa.sst.controller.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountRegisteredContoller {

	@GetMapping(path = "/subscribe/accountRegistered")
	public String ShowAAccountAddedPage() {
		return "accounts/accountRegistered";
	}
}
