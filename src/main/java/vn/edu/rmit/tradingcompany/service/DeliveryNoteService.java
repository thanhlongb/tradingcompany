package vn.edu.rmit.tradingcompany.service;

import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.rmit.tradingcompany.util.ServiceUtil;

import java.util.List;

@Transactional
@Service
public class DeliveryNoteService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DeliveryNote getDeliveryNote(int id) {
        return (DeliveryNote) sessionFactory.getCurrentSession().get(DeliveryNote.class, id);
    }

    public DeliveryNoteDetail getDeliveryNoteDetail(int id) {
        return (DeliveryNoteDetail) sessionFactory.getCurrentSession().get(DeliveryNoteDetail.class, id);
    }

    public List<DeliveryNote> getAllDeliveryNotes(Pageable pageable,
                                                  Long from, Long to){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeliveryNote.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public List<DeliveryNoteDetail> getAllDeliveryNoteDetails(Pageable pageable){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeliveryNoteDetail.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public DeliveryNote updateDeliveryNote(DeliveryNote deliveryNote){
        sessionFactory.getCurrentSession().update(deliveryNote);
        return deliveryNote;
    }

    public DeliveryNoteDetail updateDeliveryNoteDetail(DeliveryNoteDetail deliveryNoteDetail){
        sessionFactory.getCurrentSession().update(deliveryNoteDetail);
        return deliveryNoteDetail;
    }

    public DeliveryNote deleteDeliveryNote(DeliveryNote deliveryNote){
        sessionFactory.getCurrentSession().delete(deliveryNote);
        return deliveryNote;
    }

    public DeliveryNoteDetail deleteDeliveryNoteDetail(DeliveryNoteDetail deliveryNoteDetail){
        sessionFactory.getCurrentSession().delete(deliveryNoteDetail);
        return deliveryNoteDetail;
    }

    public DeliveryNote saveDeliveryNote(DeliveryNote deliveryNote) {
        try {
            for (DeliveryNoteDetail detail: deliveryNote.getDeliveryNoteDetails()){
                detail.setDeliveryNote(deliveryNote);
            }
        } catch (NullPointerException e) {
            // (null pointer) pointing to non-exist note details
        }
        sessionFactory.getCurrentSession().save(deliveryNote);
        return deliveryNote;
    }

    public DeliveryNoteDetail saveDeliveryNoteDetail(DeliveryNoteDetail deliveryNoteDetail){
        sessionFactory.getCurrentSession().save(deliveryNoteDetail);
        return deliveryNoteDetail;
    }
}
