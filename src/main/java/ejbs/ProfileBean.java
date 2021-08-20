package ejbs;


import entities.*;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@jakarta.ejb.Stateful(name = "ProfileBean")

public class ProfileBean {
    private UserEntity user;
    private List<OrderEntity> orderList = new ArrayList<>();
    private List<HouseEntity> houseList = new ArrayList<>();

    @PersistenceContext(unitName = "DAdemoPU")
    private EntityManager em;

    public ProfileBean(){

    }

    //boolean result = ProfileBean.upload(location,description,price,type);
    public boolean uploadHouse(HouseEntity house){

        try {
            em.persist(house);
        } catch (PersistenceException e) {
            return false;
        }
        return true;
    }

    public boolean createOrder(OrderEntity order){

        try {
            em.persist(order);
        } catch (PersistenceException e) {
            return false;
        }
        return true;
    }


    public UserEntity getUser() {
        return user;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public void setHouseList(List<HouseEntity> houseList) {
        this.houseList = houseList;
    }

    public List<HouseEntity> getHouseList() {
        return houseList;
    }

    public void showUser(int searchId){
        this.user = em.find(UserEntity.class,searchId);
    }

    public List<HouseEntity> getMyHouses(int uid){
        List<HouseEntity> resultList;
        Query temp_query = em.createQuery("SELECT h FROM HouseEntity h WHERE h.userId = :searchID", HouseEntity.class);
        //Query temp_query = em.createQuery("SELECT  FROM OrderEntity o WHERE o.houseByHouseId = :searchHouseEntity AND (o.startDate <= :searchDate AND o.endDate >= :searchDate)", OrderEntity.class);
        temp_query.setParameter("searchID", uid);
        resultList = temp_query.getResultList();
        return resultList;
    }


}
