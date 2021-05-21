package vn.edu.rmit.tradingcompany.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "receivingnote")
public class ReceivingNote {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Staff staff;
    @OneToMany(mappedBy = "receivingNote", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReceivingNoteDetail> receivingNoteDetails;
    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    public ReceivingNote() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<ReceivingNoteDetail> getReceivingNoteDetails() {
        return receivingNoteDetails;
    }

    public void setReceivingNoteDetails(List<ReceivingNoteDetail> receivingNoteDetails) {
        this.receivingNoteDetails = receivingNoteDetails;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
