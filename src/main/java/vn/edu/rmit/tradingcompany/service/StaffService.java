package vn.edu.rmit.tradingcompany.service;

import com.google.gson.JsonObject;
import org.hibernate.HibernateException;
import org.hibernate.StaleStateException;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import org.hibernate.Criteria;
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

@Transactional
@Service
public class StaffService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Staff getStaff(int id) {
        return (Staff) sessionFactory.getCurrentSession().get(Staff.class, id);
    }

    public Staff getStaff(String email) {
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Staff> criteria = builder.createQuery(Staff.class);
        Root<Staff> staff = criteria.from(Staff.class);
        criteria.where(builder.equal(staff.get("email"), email));
        Query<Staff> query = sessionFactory.getCurrentSession().createQuery(criteria);
        List<Staff> results = query.getResultList();
        if (results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    public List<Staff> getAllStaff(Pageable pageable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Staff.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public String getRevenue(int staffID, Long from, Long to) {
        JsonObject response = new JsonObject();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.addStaffRestriction(criteria, staffID);
        criteria.setProjection(Projections.sum("totalValue"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (criteria.uniqueResult() != null) {
            response.addProperty("revenue", (double) criteria.uniqueResult());
        }
        return response.toString();
    }

    public List<Staff> searchStaff(String name, String phone, String address){
        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Staff> criteria = builder.createQuery(Staff.class);
        Root<Staff> staff = criteria.from(Staff.class);
        criteria.select(staff);
        List<Predicate> conditions = new ArrayList<>();
        if (name != null) {
            conditions.add(builder.like(staff.get("name"), "%" + name + "%"));
        }
        if (phone != null) {
            conditions.add(builder.like(staff.get("phone"), "%" + phone + "%"));
        }
        if (address != null) {
            conditions.add(builder.like(staff.get("address"), "%" + address + "%"));
        }
        criteria.where(builder.and(conditions.toArray(new Predicate[0])));
        org.hibernate.query.Query<Staff> query = sessionFactory.getCurrentSession().createQuery(criteria);
        return query.getResultList();
    }

    public Staff updateStaff(Staff staff){
        try {
            sessionFactory.getCurrentSession().update(staff);
        } catch (StaleStateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return staff;
    }

    public Staff deleteStaff(Staff staff){
        try {
            sessionFactory.getCurrentSession().delete(staff);
        } catch (StaleStateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return staff;
    }

    public Staff saveStaff(Staff staff){
        try {
            sessionFactory.getCurrentSession().save(staff);
        } catch (HibernateException e) {
            sessionFactory.getCurrentSession().clear();
            return null;
        }
        return staff;
    }
}
