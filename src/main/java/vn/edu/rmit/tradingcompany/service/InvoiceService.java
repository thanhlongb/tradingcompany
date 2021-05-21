package vn.edu.rmit.tradingcompany.service;

import java.util.Date;

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
public class InvoiceService {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Invoice getInvoice(int id) {
        return (Invoice) sessionFactory.getCurrentSession().get(Invoice.class, id);
    }

    public InvoiceDetail getInvoiceDetail(int id) {
        return (InvoiceDetail) sessionFactory.getCurrentSession().get(InvoiceDetail.class, id);
    }

    public List<Invoice> getAllInvoices(Pageable pageable,
                                        Long from, Long to,
                                        Integer customerID,
                                        Integer staffID) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Invoice.class);
        ServiceUtil.addTimeRestriction(criteria, from, to);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.addCustomerRestriction(criteria, customerID);
        ServiceUtil.addStaffRestriction(criteria, staffID);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public List<InvoiceDetail> getAllInvoiceDetails(Pageable pageable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(InvoiceDetail.class);
        ServiceUtil.addPaginationRestriction(criteria, pageable);
        ServiceUtil.preventDuplicateRecord(criteria);
        return criteria.list();
    }

    public Invoice updateInvoice(Invoice invoice){
        sessionFactory.getCurrentSession().update(invoice);
        return invoice;
    }

    public InvoiceDetail updateInvoiceDetail(InvoiceDetail invoiceDetail){
        sessionFactory.getCurrentSession().update(invoiceDetail);
        return invoiceDetail;
    }

    public Invoice deleteInvoice(Invoice invoice){
        sessionFactory.getCurrentSession().delete(invoice);
        return invoice;
    }

    public InvoiceDetail deleteInvoiceDetail(InvoiceDetail invoiceDetail){
        sessionFactory.getCurrentSession().delete(invoiceDetail);
        return invoiceDetail;
    }

    public Invoice saveInvoice(Invoice invoice) {
        try {
            for (InvoiceDetail invoiceDetail: invoice.getInvoiceDetails()){
                invoiceDetail.setInvoice(invoice);
                Product product = (Product) sessionFactory.getCurrentSession()
                                                          .get(Product.class, invoiceDetail.getProduct().getId());
                invoiceDetail.setPrice(product.getPrice());
            }
            calculateTotalValue(invoice);
        } catch (NullPointerException e) {
            // (null pointer) pointing to non-exist note details
        }
        sessionFactory.getCurrentSession().save(invoice);
        return invoice;
    }

    public InvoiceDetail saveInvoiceDetail(InvoiceDetail invoiceDetail){
        try {
            Product product = (Product) sessionFactory.getCurrentSession()
                    .get(Product.class, invoiceDetail.getProduct().getId());
            invoiceDetail.setPrice(product.getPrice());
        } catch (Exception e) {}
        sessionFactory.getCurrentSession().save(invoiceDetail);
        return invoiceDetail;
    }

    private void calculateTotalValue(Invoice invoice) {
        float totalValue = 0;
        for (InvoiceDetail invoiceDetail: invoice.getInvoiceDetails()) {
            totalValue += invoiceDetail.getPrice() * invoiceDetail.getQuantity();
        }
        invoice.setTotalValue(totalValue);
    }
}
