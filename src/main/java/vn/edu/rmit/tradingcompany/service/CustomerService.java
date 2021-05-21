package vn.edu.rmit.tradingcompany.service;

import com.google.gson.JsonObject;
import org.hibernate.HibernateException;
import org.hibernate.StaleStateException;
import org.springframework.data.domain.*;
import vn.edu.rmit.tradingcompany.model.*;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.rmit.tradingcompany.util.ServiceUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CustomerService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Customer getCustomer(int id) {
        return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
    }

    public Customer getCustomer(String email) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.where(builder.equal(customer.get("email"), email));
        Query<Customer> query = sessionFactory.getCurrentSession().createQuery(criteria);
        List<Customer> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Customer> getAllCustomers(Pageable pageable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Customer.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public String getRevenue(int customerID, Long from, Long to) {
        JsonObject response = new JsonObject();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.addCustomerRestriction(criteria, customerID);
        criteria.setProjection(Projections.sum("totalValue"));
        ServiceUtil.preventDuplicateRecord(criteria);
        if (criteria.uniqueResult() != null) {
            response.addProperty("revenue", (double) criteria.uniqueResult());
        }
        return response.toString();
    }

    public List<Customer> searchCustomers(String name, String phone, String address) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.select(customer);
        List<Predicate> conditions = new ArrayList<>();
        if (name != null) {
            conditions.add(builder.like(customer.get("name"), "%" + name + "%"));
        }
        if (phone != null) {
            conditions.add(builder.like(customer.get("phone"), "%" + phone + "%"));
        }
        if (address != null) {
            conditions.add(builder.like(customer.get("address"), "%" + address + "%"));
        }
        criteria.where(builder.and(conditions.toArray(new Predicate[0])));
        Query<Customer> query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }

    public Customer updateCustomer(Customer customer) {
        try {
            sessionFactory.getCurrentSession().update(customer);
        } catch (StaleStateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return customer;
    }

    public Customer deleteCustomer(Customer customer) {
        try {
            sessionFactory.getCurrentSession().delete(customer);
        } catch (StaleStateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return customer;
    }

    public Customer saveCustomer(Customer customer) {
        try {
            sessionFactory.getCurrentSession().save(customer);
        } catch (HibernateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return customer;
    }
}
