package hiber.dao;

import hiber.model.User;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> userQuery = sessionFactory.getCurrentSession().createQuery("from User");
        return userQuery.getResultList();
    }

    @Override
    public User getUserByCar(String carModel, int carSeries) {
        TypedQuery<User> owner = sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u WHERE u.userCar.model=:car_model and u.userCar.series= :car_series", User.class)
                .setParameter("car_model", carModel)
                .setParameter("car_series", carSeries);
        return owner.getSingleResult();
    }

}
