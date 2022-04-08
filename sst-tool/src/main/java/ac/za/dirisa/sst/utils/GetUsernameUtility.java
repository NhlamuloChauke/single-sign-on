package ac.za.dirisa.sst.utils;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class GetUsernameUtility {
	
    public Principal getLoggedInUserName(HttpServletRequest request){
		Principal principal = request.getUserPrincipal();		
		return principal;
	}
}
