package ejbs;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService
public class UserCheckSOAP {

    @WebMethod
    public String check(String username, String password, String phone_number)
    {
        String returnText = "Correct";

        if (username==null || password==null || phone_number==null)
        {
            if(username==null)
                returnText = "Username is null!";
            else if(password==null)
                returnText = "Password is null!";
            else if(phone_number==null)
                returnText = "Phone number is null!";
            return returnText;
        }
        if(password.length()>30)
        {
            returnText = "The maximum length of password is 30";
            return returnText;
        }
        else if(username.length()>30)
        {
            returnText = "The maximum length of username is 30";
            return returnText;
        }
        else if(phone_number.length()>13)
        {
            returnText = "The format of phone number is not correct!";
            return returnText;
        }
        return returnText;
    }

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/UserCheckSOAP", new UserCheckSOAP());
    }
}
