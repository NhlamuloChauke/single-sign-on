package ac.za.dirisa.sst.controller.viewController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ac.za.dirisa.sst.dto.RegistrationRequest;
import ac.za.dirisa.sst.model.Services;
import ac.za.dirisa.sst.repository.UserRepository;
import ac.za.dirisa.sst.service.ServicesService;
import ac.za.dirisa.sst.service.UserService;
import ac.za.dirisa.sst.utils.GetUsernameUtility;

@Controller
public class UserPortalController {
	private static final Logger LOG = LoggerFactory.getLogger(UserPortalController.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired 
	ServicesService servicesService;

	List<Services> services;

	@Autowired
	GetUsernameUtility usernameUtility;

	@ModelAttribute("user")
	public RegistrationRequest preLoad(){
		services = servicesService.services();
		return new RegistrationRequest();
	}

	@GetMapping(path = "/subscribe/portal")
	public String dashboard(ModelMap model, HttpServletRequest request, HttpSession session) {
		if (usernameUtility.getLoggedInUserName(request) != null) { 

			//Get user details and initials
			model.addAttribute("userName", getUserInitials(
				               userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getFirstName()
							   ) +  
							   capitalizeFirstLetter(
				               userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getLastName()
							   ));

			//Loading the list of the services
			model.addAttribute("services", services);

			return "dashboard/dashboard";
		} else {
			LOG.warn("user is not authenticated to proceed the LoginPage");
			return "redirect:/subscribe/login";
		}
	}

	@GetMapping(path = "/subscribe/profile")
	public String profile(ModelMap model, HttpServletRequest request, HttpSession session){
		if (usernameUtility.getLoggedInUserName(request) != null) { 
			model.addAttribute("userName", getUserInitials(
				userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getFirstName()
				) +  
				capitalizeFirstLetter(
				userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getLastName()
				));
			return "dashboard/profile";
		}
		else {
			LOG.warn("user is not authenticated to proceed the LoginPage");
			return "redirect:/subscribe/login";
		}
	}

	@PostMapping(path = "/subscribe/updateUserPassword")
	public String changePassword(@ModelAttribute("user") RegistrationRequest userDto, ModelMap model, HttpServletRequest request){
		//Find user by Email Address
		model.addAttribute("userName", getUserInitials(
				userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getFirstName()
				) +  
				capitalizeFirstLetter(
				userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get().getLastName()
				));
		model.addAttribute("message", "You have succefully changed your password.");

		//Change user password by Email Address
		userService.changeUserPassword(userService.findUserByEmailAddress(usernameUtility.getLoggedInUserName(request).getName()).get(), userDto.getPassword());
		return "dashboard/profile";
	}

	/**
	 * getUserInitials get initials from the name
	 * @param fullNames
	 * @return
	 */
	public static String getUserInitials(String fullNames){
        Pattern pattern = Pattern.compile("((^| )[A-Za-z])");
        Matcher matcher = pattern.matcher(fullNames);
        String initials = "";
        while (matcher.find()) {
            initials += matcher.group().trim();
        }
        return initials.toUpperCase();
    }
	
	/**
	 * capitalizeFirstLetter capitilize the first letter
	 * @param string
	 * @return
	 */
	public static String capitalizeFirstLetter(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
