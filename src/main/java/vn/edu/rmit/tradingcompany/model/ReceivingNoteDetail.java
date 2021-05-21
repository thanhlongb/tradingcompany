package vn.edu.rmit.tradingcompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "receivingnotedetail")
public class ReceivingNoteDetail {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JsonIgnoreProperties({"receivingNoteDetails"})
    private ReceivingNote receivingNote;
    @ManyToOne
    private Product product;
    @Column
    private int quantity;

    public ReceivingNoteDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReceivingNote getReceivingNote() {
        return receivingNote;
    }

    public void setReceivingNote(ReceivingNote receivingNote) {
        this.receivingNote = receivingNote;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
