package ac.za.dirisa.sst.utils;

import javax.servlet.http.HttpServletRequest;

public class GetSiteURLUtility {

	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
