package hiber.dao;

import hiber.model.Car;
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
        TypedQuery<Car> carQuery = sessionFactory.getCurrentSession().createQuery("from Car where model = :car_model and series = :car_series")
                .setParameter("car_model", carModel)
                .setParameter("car_series", carSeries);

        Car car = carQuery.getSingleResult();

        if (car != null) {
            List<User> userList = listUsers();
            User getUser = userList.stream()
                    .filter(user -> user.getUserCar().equals(car))
                    .findAny()
                    .orElse(null);
            return getUser;
        }
        return null;
    }

}
