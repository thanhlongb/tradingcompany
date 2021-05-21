package vn.edu.rmit.tradingcompany.service;

import vn.edu.rmit.tradingcompany.model.*;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.rmit.tradingcompany.util.ServiceUtil;

import java.util.HashMap;

@Transactional
@Service
public class BusinessService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HashMap<String, Object> getRevenue(Long from, Long to) {
        HashMap<String, Object> response = new HashMap<>();
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        criteria.setProjection(Projections.sum("totalValue"));
        ServiceUtil.preventDuplicateRecord(criteria);
        Object revenue = (criteria.uniqueResult() != null) ? criteria.uniqueResult() : 0;
        response.put("revenue", revenue);
        return response;
    }

    public HashMap<String, Object> getInventory(Long from, Long to) {
        String sql = "SELECT" +
                    "	p.name," +
                    "	coalesce(SUM(r.quantity), 0) AS received," +
                    "	coalesce(SUM(d.quantity), 0) AS delivery," +
                    "	coalesce((SUM(r.quantity) - SUM(d.quantity)), 0) AS balance " +
                    " FROM" +
                    "	Product p" +
                    "	LEFT JOIN (" +
                    "		SELECT" +
                    "			rnd.product_id," +
                    "			rnd.quantity" +
                    "		FROM" +
                    "			receivingnotedetail rnd" +
                    "			LEFT JOIN receivingnote rn ON rn.id = rnd.receivingnote_id" +
                    "		WHERE" +
                    "			rn.creationtime BETWEEN to_timestamp(:from)" +
                    "			AND to_timestamp(:to)) AS r ON p.id = r.product_id" +
                    "	LEFT JOIN (" +
                    "		SELECT" +
                    "			dnd.product_id, dnd.quantity" +
                    "		FROM" +
                    "			deliverynotedetail dnd" +
                    "			LEFT JOIN deliverynote dn ON dn.id = dnd.deliverynote_id" +
                    "		WHERE" +
                    "			dn.creationtime BETWEEN to_timestamp(:from)" +
                    "			AND to_timestamp(:to)) AS d ON p.id = d.product_id " +
                    "GROUP BY" +
                    "	p.id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        query.setParameter("from", from);
        query.setParameter("to", to);

        HashMap<String, Object> response = new HashMap<>();
        if (from != null)  response.put("from", ServiceUtil.timestampToDate(from));
        if (to != null) response.put("to", ServiceUtil.timestampToDate(to));
        response.put("products", query.list());
        return response;
    }
}
