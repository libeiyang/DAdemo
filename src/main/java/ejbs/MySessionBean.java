package ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import entities.PersonEntity;

import java.util.List;

@jakarta.ejb.Stateless(name = "MySessionBeanEJB")
public class MySessionBean {
    @PersistenceContext(unitName = "DAdemoPU")
    EntityManager em;
    public MySessionBean() {
    }

    public int doSomethingReallyDifficult(int a,int b) {
        return a+b;
    }

    public List<PersonEntity> findPersonByname(String name) {
        //JPQL
        Query query = em.createQuery("SELECT p FROM PersonEntity p WHERE p.name LIKE :searchname", PersonEntity.class);
        query.setParameter("searchname", "%" + name + "%");
        return query.getResultList();
    }
}
