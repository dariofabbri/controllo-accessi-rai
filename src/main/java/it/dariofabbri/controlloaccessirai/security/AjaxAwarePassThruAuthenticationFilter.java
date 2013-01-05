package it.dariofabbri.controlloaccessirai.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

public class AjaxAwarePassThruAuthenticationFilter 
	extends PassThruAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) 
			throws Exception {

		if(request instanceof HttpServletRequest) {
			HttpServletRequest hsreq = (HttpServletRequest)request;
			
			String header = hsreq.getHeader("Faces-Request");
			if(header != null && header.equals("partial/ajax")) {
				HttpServletResponse hsres = (HttpServletResponse)response;
				hsres.addHeader("WWW-Authentication", "CUSTOM-AUTH");
				hsres.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied to JSF ajax request.");
				return false;
			}
		}
		
		return super.onAccessDenied(request, response);
	}
}
