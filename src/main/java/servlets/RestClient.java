package servlets;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

@ManagedBean(
        name = "RestClient"
)
@ViewScoped
public class RestClient {
    private String username;
    private String phoneNumber;

    public RestClient() {
    }

    public void searchPhonenumber() {
        Client client = ClientBuilder.newClient();
        String output = (String)client.target("http://localhost:8080/DAdemo8/resources/user/" + username).request().get(String.class);
        phoneNumber = output;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}