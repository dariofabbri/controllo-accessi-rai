package it.dariofabbri.controlloaccessirai.jsf.managedbean;

import it.dariofabbri.controlloaccessirai.model.Utente;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@SessionScoped
public class LoginInfoBean implements Serializable {

	private static final Logger logger = LoggerFactory.getLogger(LoginInfoBean.class);
	
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	
	private Utente utente;

	public String cleanLoginInfo() {
		
		logger.debug("Cleaning up login info.");
		this.reset();
		return null;
	}
	
	public String doLogoff() {
		
		this.reset();
		
		Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
            logger.debug("Exception caught while logging out current user.", e);
        }
        
		return "/public/login?faces-redirect=true";
	}
	
	public String doLogin() {
		
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject currentUser = SecurityUtils.getSubject();
 
        try {
        	
            currentUser.login(token);
            
        } catch (AuthenticationException e) {
        	
            logger.debug("Exception caught while attempting login. Reason: " + e.getMessage());
            this.reset();
    		FacesMessage message = new FacesMessage(
    				FacesMessage.SEVERITY_ERROR, 
    				"Login fallito", 
    				"Impossibile accedere al sistema, controllare username e password");
    		
    		FacesContext.getCurrentInstance().addMessage(null, message);
    		return null;
        }

        // Extract some useful data from current user.
 		//
 		for(Object principal : currentUser.getPrincipals()) {
 			if(principal instanceof Utente) {
 				
 				this.utente = (Utente)principal;
 				break;
 			}
 		}

        return "/private/index?faces-redirect=true";
	}
	
	private void reset() {

		username = null;
		password = null;
		utente = null;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}