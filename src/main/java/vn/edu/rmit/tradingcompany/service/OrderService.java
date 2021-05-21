package vn.edu.rmit.tradingcompany.service;

import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.util.ServiceUtil;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OrderService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Order getOrder(int id) {
        return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
    }

    public OrderDetail getOrderDetail(int id) {
        return (OrderDetail) sessionFactory.getCurrentSession().get(OrderDetail.class, id);
    }

    public List<Order> getAllOrders(Pageable pageable,
                                    Long from, Long to) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public List<OrderDetail> getAllOrderDetails(Pageable pageable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OrderDetail.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public Order updateOrder(Order order){
        sessionFactory.getCurrentSession().update(order);
        return order;
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail){
        sessionFactory.getCurrentSession().update(orderDetail);
        return orderDetail;
    }

    public Order deleteOrder(Order order){
        sessionFactory.getCurrentSession().delete(order);
        return order;
    }

    public OrderDetail deleteOrderDetail(OrderDetail orderDetail){
        sessionFactory.getCurrentSession().delete(orderDetail);
        return orderDetail;
    }

    public Order saveOrder(Order order){
        try {
            for(OrderDetail detail: order.getOrderDetails()){
                detail.setOrder(order);
            }
        } catch (NullPointerException e) {
            // (null pointer) pointing to non-exist order details
        }
        sessionFactory.getCurrentSession().save(order);
        return order;
    }

    public OrderDetail saveOrderDetail(OrderDetail orderDetail){
        sessionFactory.getCurrentSession().save(orderDetail);
        return orderDetail;
    }
}
