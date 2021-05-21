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
public class ReceivingNoteService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ReceivingNote getReceivingNote(int id) {
        return (ReceivingNote) sessionFactory.getCurrentSession().get(ReceivingNote.class, id);
    }

    public ReceivingNoteDetail getReceivingNoteDetail(int id) {
        return (ReceivingNoteDetail) sessionFactory.getCurrentSession().get(ReceivingNoteDetail.class, id);
    }

    public List<ReceivingNote> getAllReceivingNotes(Pageable pageable,
                                                    Long from, Long to){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNote.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public List<ReceivingNoteDetail> getAllReceivingNoteDetails(Pageable pageable){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ReceivingNoteDetail.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public ReceivingNote updateReceivingNote(ReceivingNote receivingNote){
        sessionFactory.getCurrentSession().update(receivingNote);
        return receivingNote;
    }

    public ReceivingNoteDetail updateReceivingNoteDetail(ReceivingNoteDetail receivingNoteDetail){
        sessionFactory.getCurrentSession().update(receivingNoteDetail);
        return receivingNoteDetail;
    }

    public ReceivingNote deleteReceivingNote(ReceivingNote receivingNote){
        sessionFactory.getCurrentSession().delete(receivingNote);
        return receivingNote;
    }

    public ReceivingNoteDetail deleteReceivingNoteDetail(ReceivingNoteDetail receivingNoteDetail){
        sessionFactory.getCurrentSession().delete(receivingNoteDetail);
        return receivingNoteDetail;
    }

    public ReceivingNote saveReceivingNote(ReceivingNote receivingNote) {
        try {
            for (ReceivingNoteDetail detail: receivingNote.getReceivingNoteDetails()){
                detail.setReceivingNote(receivingNote);
            }
        } catch (NullPointerException e) {
            // (null pointer) pointing to non-exist note details
        }
        sessionFactory.getCurrentSession().save(receivingNote);
        return receivingNote;
    }

    public ReceivingNoteDetail saveReceivingNoteDetail(ReceivingNoteDetail receivingNoteDetail){
        sessionFactory.getCurrentSession().save(receivingNoteDetail);
        return receivingNoteDetail;
    }
}
