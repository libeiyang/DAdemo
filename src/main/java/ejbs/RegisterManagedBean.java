package ejbs;

import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@ManagedBean(name = "RegisterManagedBean")
@RequestScoped
public class RegisterManagedBean{

    private String returnedText;
    private String username;
    private String phone_number;
    private String password;
    private String confirmPassword;
    private String correct = "Correct";
    private boolean success = true;
    @EJB
    private RegisterBean registerBean;

    public void register(){
        returnedText  = new UserCheckSOAP().check(username, password, phone_number);
        if(returnedText.equals(correct))
        {
            UserEntity user = new UserEntity();
            user.setUsername(this.username);
            user.setPhoneNumber(this.phone_number);
            user.setPassword(this.password);
            FacesContext fc = FacesContext.getCurrentInstance();

            if(checkMatch(password,confirmPassword))
            {
                boolean result = registerBean.registerUser(user);
                success = result;
                returnedText = "This username was registered";
                if(result) {
                    try{
                        fc.getExternalContext().redirect("login.xhtml");}
                    catch (IOException exception){
                        exception.printStackTrace();
                    }
                }
            }
            else
            {
                returnedText = "Two passwords do not match";
                success = false;
            }
        }
        else
        {
            success = false;
        }
    }

    public boolean checkMatch(String password, String confirmedPassword)
    {
        return password.equals(confirmedPassword);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getReturnedText() {
        return returnedText;
    }

    public void setReturnedText(String returnedText) {
        this.returnedText = returnedText;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Encrypts the password along with salt
    private static Random random = new Random((new Date()).getTime());
    public static String encrypt(String passw){
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return encoder.encode(salt) + encoder.encode(passw.getBytes());
    }


}
