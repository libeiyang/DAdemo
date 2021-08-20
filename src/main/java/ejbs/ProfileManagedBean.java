package ejbs;

import entities.*;

import entities.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "ProfileManagedBean")
@ViewScoped
public class ProfileManagedBean {
    private UserEntity user;

    private List<OrderEntity> orderList;
    private List<HouseEntity> houseList;

    private boolean success = true;
    private int location;
    private int price;
    private String description;
    private String type;
    private String returnedText;

    private int houseId;
    private String startDate;
    private String endDate;
    private String returnedTextOrder;
    private boolean successOrder = true;

    @EJB
    private ProfileBean profileBean;

    @EJB
    private HouseCounterSingletonBean HouseCounterSingletonBean;

    public ProfileManagedBean(){

    }

    @PostConstruct
    public void initHouseList(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = req.getSession(false);

        if(httpSession.getAttribute("user_id")!=null)
        {
            int uid = (int) httpSession.getAttribute("user_id");
            profileBean.showUser(uid);
            this.houseList=profileBean.getMyHouses(uid);
            this.user = profileBean.getUser();
        }
        else
        {
            try {
                context.getExternalContext().redirect("./login.xhtml");
            }
            catch(IOException exception){
                System.out.println("Logout error");
            }
        }
    }

    public void Upload(){

        HouseEntity house = new HouseEntity();
        house.setAreaId(location);
        house.setDescription(this.description);
        house.setType(this.type);
        house.setPrice(this.price);
        house.setUserId(user.getUserId());
        FacesContext fc = FacesContext.getCurrentInstance();

        if(type == null || description == null)
        {
            returnedText = "Type or description is null!";
            success = false;
        }
        else
        {
            boolean result = profileBean.uploadHouse(house);
            success = result;
            returnedText = "Upload successful!";
        }
    }

    public void CreateOrder(){

        OrderEntity order = new OrderEntity();
        order.setHouseId(this.houseId);
        order.setStartDate(java.sql.Date.valueOf(this.startDate));
        order.setEndDate(java.sql.Date.valueOf(this.endDate));

        FacesContext fc = FacesContext.getCurrentInstance();

        if(startDate == null || endDate == null)
        {
            returnedTextOrder = "Date is null!";
            successOrder = false;
        }
        else
        {
            boolean resultOrder = profileBean.createOrder(order);
            successOrder = resultOrder;
            returnedTextOrder = "Create order successful!";
        }
    }

    public void logout(){
        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        context.getExternalContext().invalidateSession();
        try {
            context.getExternalContext().redirect("./login.xhtml");
        }
        catch(IOException exception){
            System.out.println("Logout error");
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReturnedText() {
        return returnedText;
    }

    public void setReturnedText(String returnedText) {
        this.returnedText = returnedText;
    }

    public String getReturnedTextOrder() {
        return returnedTextOrder;
    }

    public void setReturnedTextOrder(String returnedTextOrder) {
        this.returnedTextOrder = returnedTextOrder;
    }

    public boolean getSuccessOrder() {
        return successOrder;
    }

    public void setSuccessOrder(boolean successOrder) {
        this.successOrder = successOrder;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public List<HouseEntity> getHouseList() {
        return houseList;
    }

    public void setHouseList(List<HouseEntity> houseList) {
        this.houseList = houseList;
    }


}
