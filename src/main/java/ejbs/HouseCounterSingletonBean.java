package ejbs;


import entities.HouseEntity;
import entities.OrderEntity;
import entities.UserEntity;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@jakarta.ejb.Singleton(name = "HouseCounterSingletonBean")
public class HouseCounterSingletonBean {

    private int houseCount;

    private int rooms_ThisMonth;
    private int studios_ThisMonth;
    private int apartments_ThisMonth;

    private int rooms_NextMonth;
    private int studios_NextMonth;
    private int apartments_NextMonth;

    private int rooms_Month_after_NextMonth;
    private int studios_Month_after_NextMonth;
    private int apartments_Month_after_NextMonth;

    @PersistenceContext(name="DAdemoPU")
    EntityManager em;

    public HouseCounterSingletonBean() {
    }

    @PostConstruct
    public void init(){
        updateHouseCounters();

    }

    public int counter(List<HouseEntity> HouseEntities, Date searchDate){
        int count = 0;

        List<HouseEntity> resultList = new ArrayList<>(HouseEntities);
        for(HouseEntity h: HouseEntities) {
            //select * from t1 left join t5 on t1.id=t5.id;
            //Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.name LIKE :searchname", PersonEntity.class);
            Query temp_query = em.createQuery("SELECT o FROM OrderEntity o WHERE o.houseId = :searchID AND (o.startDate <= :searchDate AND o.endDate >= :searchDate)", OrderEntity.class);
            //Query temp_query = em.createQuery("SELECT  FROM OrderEntity o WHERE o.houseByHouseId = :searchHouseEntity AND (o.startDate <= :searchDate AND o.endDate >= :searchDate)", OrderEntity.class);
            temp_query.setParameter("searchID", h.getHouseId());
            temp_query.setParameter("searchDate", searchDate);
            List<OrderEntity> orderEntities_notAvail = temp_query.getResultList();

            for(OrderEntity ord: orderEntities_notAvail)
            {
                if(ord.getHouseByHouseId() == h)
                {
                    resultList.remove(h);
                }
            }
        }
        count = resultList.size();
        return count;
    }

    public void updateHouseCounters(){
        Query query = em.createQuery("SELECT p FROM HouseEntity p where p.type=:houseType", HouseEntity.class);

        query.setParameter("houseType","room");
        List<HouseEntity> rooms = query.getResultList();
        query.setParameter("houseType","studio");
        List<HouseEntity> studios = query.getResultList();
        query.setParameter("houseType","apartment");
        List<HouseEntity> apartments = query.getResultList();

        Date searchDate_ThisMonth = new Date();
        Date searchDate_NextMonth = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30));
        Date searchDate_Month_after_NextMonth = new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 2 * 30));

        rooms_ThisMonth = counter(rooms, searchDate_ThisMonth);
        studios_ThisMonth = counter(studios, searchDate_ThisMonth);
        apartments_ThisMonth = counter(apartments, searchDate_ThisMonth);

        rooms_NextMonth = counter(rooms, searchDate_NextMonth);
        studios_NextMonth = counter(studios, searchDate_NextMonth);
        apartments_NextMonth = counter(apartments, searchDate_NextMonth);

        rooms_Month_after_NextMonth = counter(rooms, searchDate_Month_after_NextMonth);
        studios_Month_after_NextMonth = counter(studios, searchDate_Month_after_NextMonth);
        apartments_Month_after_NextMonth = counter(apartments, searchDate_Month_after_NextMonth);


    }

    public int getHouseCount() {return houseCount;}

    public int getRooms_ThisMonth() {
        return rooms_ThisMonth;
    }

    public void setRooms_ThisMonth(int rooms_ThisMonth) {
        this.rooms_ThisMonth = rooms_ThisMonth;
    }

    public int getStudios_ThisMonth() {
        return studios_ThisMonth;
    }

    public void setStudios_ThisMonth(int studios_ThisMonth) {
        this.studios_ThisMonth = studios_ThisMonth;
    }

    public int getApartments_ThisMonth() {
        return apartments_ThisMonth;
    }

    public void setApartments_ThisMonth(int apartments_ThisMonth) {
        this.apartments_ThisMonth = apartments_ThisMonth;
    }

    public int getRooms_NextMonth() {
        return rooms_NextMonth;
    }

    public void setRooms_NextMonth(int rooms_NextMonth) {
        this.rooms_NextMonth = rooms_NextMonth;
    }

    public int getStudios_NextMonth() {
        return studios_NextMonth;
    }

    public void setStudios_NextMonth(int studios_NextMonth) {
        this.studios_NextMonth = studios_NextMonth;
    }

    public int getApartments_NextMonth() {
        return apartments_NextMonth;
    }

    public void setApartments_NextMonth(int apartments_NextMonth) {
        this.apartments_NextMonth = apartments_NextMonth;
    }

    public int getRooms_Month_after_NextMonth() {
        return rooms_Month_after_NextMonth;
    }

    public void setRooms_Month_after_NextMonth(int rooms_Month_after_NextMonth) {
        this.rooms_Month_after_NextMonth = rooms_Month_after_NextMonth;
    }

    public int getStudios_Month_after_NextMonth() {
        return studios_Month_after_NextMonth;
    }

    public void setStudios_Month_after_NextMonth(int studios_Month_after_NextMonth) {
        this.studios_Month_after_NextMonth = studios_Month_after_NextMonth;
    }

    public int getApartments_Month_after_NextMonth() {
        return apartments_Month_after_NextMonth;
    }

    public void setApartments_Month_after_NextMonth(int apartments_Month_after_NextMonth) {
        this.apartments_Month_after_NextMonth = apartments_Month_after_NextMonth;
    }
}
