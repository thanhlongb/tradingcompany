package vn.edu.rmit.tradingcompany.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "deliverynote")
public class DeliveryNote {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "deliveryNote", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DeliveryNoteDetail> deliveryNoteDetails;
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    public DeliveryNote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<DeliveryNoteDetail> getDeliveryNoteDetails() {
        return deliveryNoteDetails;
    }

    public void setDeliveryNoteDetails(List<DeliveryNoteDetail> deliveryNoteDetails) {
        this.deliveryNoteDetails = deliveryNoteDetails;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
