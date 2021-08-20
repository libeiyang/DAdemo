package ejbs;


import entities.*;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "HubManagedBean")
@RequestScoped
public class HubManagedBean {

    public List<HouseEntity> HouseList;
    public String selected = "All";
    public String selectedDate = "This month";
    private int houseCount = 0;
    private int studio_count;
    private int room_count;
    private int apartment_count;
    private int total;
    private String All = "All";
    private String room = "Room";
    private String studio = "Studio";
    private String apartment = "Apartment";
    private String ThisMonth = "This month";
    private String NextMonth = "Next month";
    private String MonthAfterNext = "The month after next";


    @EJB
    private HubBean HubBean;

    @EJB
    private HouseCounterSingletonBean HouseCounterSingletonBean;

    public HubManagedBean(){

    }

    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        if(httpSession.getAttribute("user_id")!=null)
        {
            updateCount_ThisMonth();
            Date searchDate_ThisMonth = new Date();
            HouseList=HubBean.getAvailableHouses(HubBean.getAllHouses(),searchDate_ThisMonth, searchDate_ThisMonth);
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

    public void updateCount_ThisMonth(){
        room_count = HouseCounterSingletonBean.getRooms_ThisMonth();
        studio_count = HouseCounterSingletonBean.getStudios_ThisMonth();
        apartment_count = HouseCounterSingletonBean.getApartments_ThisMonth();
        total = room_count + studio_count + apartment_count;
    }

    public void updateCount_NextMonth(){
        room_count = HouseCounterSingletonBean.getRooms_NextMonth();
        studio_count = HouseCounterSingletonBean.getStudios_NextMonth();
        apartment_count = HouseCounterSingletonBean.getApartments_NextMonth();
        total = room_count + studio_count + apartment_count;
    }

    public void updateCount_MonthAfterNext(){
        room_count = HouseCounterSingletonBean.getRooms_Month_after_NextMonth();
        studio_count = HouseCounterSingletonBean.getStudios_Month_after_NextMonth();
        apartment_count = HouseCounterSingletonBean.getApartments_Month_after_NextMonth();
        total = room_count + studio_count + apartment_count;
    }

    public void filterHouses(){

        long millis=System.currentTimeMillis();
        java.sql.Date searchDate_ThisMonth = new java.sql.Date(millis);

        java.sql.Date searchDate_NextMonth = new java.sql.Date(millis+(long)30*24*3600*1000);
        java.sql.Date searchDate_day_after_NextMonth = new java.sql.Date(millis+(long)2*30*24*3600*1000);
        java.sql.Date searchDate_day_after_NextNextMonth = new java.sql.Date(millis+(long)3*30*24*3600*1000);

        if(selected.equals(All) && selectedDate.equals(ThisMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getAllHouses(),searchDate_ThisMonth,searchDate_NextMonth);
            updateCount_ThisMonth();
        }
        else if(selected.equals(All) && selectedDate.equals(NextMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getAllHouses(),searchDate_NextMonth,searchDate_day_after_NextMonth);
            updateCount_NextMonth();
        }
        else if(selected.equals(All) && selectedDate.equals(MonthAfterNext))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getAllHouses(),searchDate_day_after_NextMonth,searchDate_day_after_NextNextMonth);
            updateCount_MonthAfterNext();
        }
        else if(selected.equals(room) && selectedDate.equals(ThisMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getRooms(),searchDate_ThisMonth,searchDate_NextMonth);
            updateCount_ThisMonth();
        }
        else if(selected.equals(room) && selectedDate.equals(NextMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getRooms(),searchDate_NextMonth,searchDate_day_after_NextMonth);
            updateCount_NextMonth();
        }
        else if(selected.equals(room) && selectedDate.equals(MonthAfterNext))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getRooms(),searchDate_day_after_NextMonth,searchDate_day_after_NextNextMonth);
            updateCount_MonthAfterNext();
        }
        else if(selected.equals(studio) && selectedDate.equals(ThisMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getStudios(),searchDate_ThisMonth,searchDate_NextMonth);
            updateCount_ThisMonth();
        }
        else if(selected.equals(studio) && selectedDate.equals(NextMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getStudios(),searchDate_NextMonth,searchDate_day_after_NextMonth);
            updateCount_NextMonth();
        }
        else if(selected.equals(studio) && selectedDate.equals(MonthAfterNext))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getStudios(),searchDate_day_after_NextMonth,searchDate_day_after_NextNextMonth);
            updateCount_MonthAfterNext();
        }
        else if(selected.equals(apartment) && selectedDate.equals(ThisMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getApartments(),searchDate_ThisMonth,searchDate_NextMonth);
            updateCount_ThisMonth();
        }
        else if(selected.equals(apartment) && selectedDate.equals(NextMonth))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getApartments(),searchDate_NextMonth,searchDate_day_after_NextMonth);
            updateCount_NextMonth();
        }
        else if(selected.equals(apartment) && selectedDate.equals(MonthAfterNext))
        {
            HouseList=HubBean.getAvailableHouses(HubBean.getApartments(),searchDate_day_after_NextMonth,searchDate_day_after_NextNextMonth);
            updateCount_MonthAfterNext();
        }
    }

    public List<HouseEntity> getHouseList() {
        return HouseList;
    }

    public void setHouseList(List<HouseEntity> HouseList) {
        this.HouseList = HouseList;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRoom_count() {
        return room_count;
    }

    public void setRoom_count(int room_count) {
        this.room_count = room_count;
    }

    public int getStudio_count() {
        return studio_count;
    }

    public void setStudio_count(int studio_count) {
        this.studio_count = studio_count;
    }

    public int getApartment_count() {
        return apartment_count;
    }

    public void setApartment_count(int apartment_count) {
        this.apartment_count = apartment_count;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
}
