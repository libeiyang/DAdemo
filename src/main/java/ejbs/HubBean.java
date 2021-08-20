package ejbs;


import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.TemporalType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@jakarta.ejb.Stateless(name = "HubBean")
public class HubBean {
    @PersistenceContext(name="DAdemoPU")
    EntityManager em;


    @PostConstruct
    public void initCount(){
        getAllHouses();

    }

    public List<HouseEntity> getAllHouses(){
        Query query = em.createQuery("SELECT p FROM HouseEntity p", HouseEntity.class);
        return query.getResultList();
    }
    public List<HouseEntity> getRooms(){
        Query query = em.createQuery("SELECT p FROM HouseEntity p WHERE  p.type = :searchType", HouseEntity.class);
        query.setParameter("searchType", "room");
        return query.getResultList();
    }
    //Query query = em.createQuery("SELECT p FROM UserEntity p WHERE p.username = :searchUsername AND p.password = :searchPassword", UserEntity.class);
    //query.setParameter("searchPassword", password);
    public List<HouseEntity> getStudios(){
        Query query = em.createQuery("SELECT p FROM HouseEntity p WHERE p.type = :searchType", HouseEntity.class);
        query.setParameter("searchType", "studio");
        return query.getResultList();
    }

    public List<HouseEntity> getApartments(){
        Query query = em.createQuery("SELECT p FROM HouseEntity p WHERE p.type = :searchType", HouseEntity.class);
        query.setParameter("searchType", "apartment");
        return query.getResultList();
    }


    public List<HouseEntity> getAvailableHouses(List<HouseEntity> HouseEntities, Date searchDate, Date searchDateMonth){
        List<HouseEntity> resultList = new ArrayList<>(HouseEntities);

        for(HouseEntity h: HouseEntities) {
            //select * from t1 left join t5 on t1.id=t5.id;
            //Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.name LIKE :searchname", PersonEntity.class);
            Query temp_query = em.createQuery("SELECT o FROM OrderEntity o WHERE o.houseId = :searchID AND (o.startDate <= :searchDate AND o.endDate >= :searchDateMonth)", OrderEntity.class);
            //Query temp_query = em.createQuery("SELECT  FROM OrderEntity o WHERE o.houseByHouseId = :searchHouseEntity AND (o.startDate <= :searchDate AND o.endDate >= :searchDate)", OrderEntity.class);
            temp_query.setParameter("searchID", h.getHouseId());
            temp_query.setParameter("searchDate", searchDate);
            temp_query.setParameter("searchDateMonth", searchDateMonth);
            List<OrderEntity> orderEntities_notAvail = temp_query.getResultList();


            for(OrderEntity ord: orderEntities_notAvail)
            {
                if(ord.getHouseId() == h.getHouseId())
                {
                    resultList.remove(h);
                }
            }
        }
        return resultList;
    }

}
