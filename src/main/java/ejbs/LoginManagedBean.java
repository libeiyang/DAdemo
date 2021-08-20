package ejbs;

import entities.UserEntity;
import jakarta.faces.bean.ManagedBean;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean(name = "LoginManagedBean")
@RequestScoped
public class LoginManagedBean {

    private String username;
    private String password;
    private boolean success = true;

    @EJB
    private LoginBean loginBean;

    public void loginValidation(){

        boolean result = loginBean.login(username,password);
        success = result;
        if(result)
        {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
            httpSession.setAttribute("user_id", loginBean.getUserId());
            httpSession.setAttribute("username", loginBean.getUsername());
            httpSession.setAttribute("phone_number", loginBean.getPhoneNumber());
            try{
                context.getExternalContext().redirect("./hub.xhtml");
            }
            catch(IOException exception){
                System.out.println("Login error");
            }

        }
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

}