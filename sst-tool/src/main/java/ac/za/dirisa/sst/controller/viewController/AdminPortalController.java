package ac.za.dirisa.sst.controller.viewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ac.za.dirisa.sst.repository.UserRepository;
import ac.za.dirisa.sst.service.UserService;
import ac.za.dirisa.sst.utils.GetUsernameUtility;

@Controller
public class AdminPortalController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AdminPortalController.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	GetUsernameUtility usernameUtility;	


	@GetMapping(path = "/subscribe/adminPortal")
	public String adminDashboard(ModelMap model, HttpServletRequest request, HttpSession session) {
		if (usernameUtility.getLoggedInUserName(request) != null) { 

			//Get user details and initials
			model.addAttribute("userName", userService.findUserByEmailAddress(
					                       usernameUtility.getLoggedInUserName(request)
					                       .getName()).get().getUsername());
			
			model.addAttribute("users", userRepository.getUsersByRole());

			return "dashboard/adminDashboard";
		} else {
			LOG.warn("user is not authenticated to proceed the LoginPage");
			return "redirect:/subscribe/login";
		}
	}


}
