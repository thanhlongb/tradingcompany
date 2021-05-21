package vn.edu.rmit.tradingcompany.controller;

import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/deliveryNote")
public class DeliveryNoteController {

    @Autowired
    private DeliveryNoteService deliveryNoteService;

    public DeliveryNoteController(DeliveryNoteService deliveryNoteService) {
        this.deliveryNoteService = deliveryNoteService;
    }

    // CREATE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public DeliveryNote createDeliveryNote(@RequestBody DeliveryNote deliveryNote){
        return deliveryNoteService.saveDeliveryNote(deliveryNote);
    }

    @RequestMapping(path = "detail", method = RequestMethod.POST)
    public DeliveryNoteDetail createDeliveryNoteDetail(@RequestBody DeliveryNoteDetail deliveryNoteDetail){
        return deliveryNoteService.saveDeliveryNoteDetail(deliveryNoteDetail);
    }

    // READ
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public DeliveryNote getDeliveryNote(@PathVariable int id) {
        return deliveryNoteService.getDeliveryNote(id);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<DeliveryNote> getAllDeliveryNotes(Pageable pageable,
                                                  @RequestParam(value = "from", required = false) Long from,
                                                  @RequestParam(value = "to", required = false) Long to) {
        return deliveryNoteService.getAllDeliveryNotes(pageable, from, to);
    }

    @RequestMapping(path = "detail/{id}", method = RequestMethod.GET)
    public DeliveryNoteDetail getDeliveryNoteDetail(@PathVariable int id) {
        return deliveryNoteService.getDeliveryNoteDetail(id);
    }

    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public List<DeliveryNoteDetail> getAllDeliveryNoteDetails(Pageable pageable) {
        return deliveryNoteService.getAllDeliveryNoteDetails(pageable);
    }

    // UPDATE
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public DeliveryNote updateDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return deliveryNoteService.updateDeliveryNote(deliveryNote);
    }

    @RequestMapping(path = "detail", method = RequestMethod.PUT)
    public DeliveryNoteDetail updateDeliveryNoteDetail(@RequestBody DeliveryNoteDetail deliveryNoteDetail) {
        return deliveryNoteService.updateDeliveryNoteDetail(deliveryNoteDetail);
    }

    // DELETE
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public DeliveryNote deleteDeliveryNote(@RequestBody DeliveryNote deliveryNote) {
        return deliveryNoteService.deleteDeliveryNote(deliveryNote);
    }

    @RequestMapping(path = "detail", method = RequestMethod.DELETE)
    public DeliveryNoteDetail deleteDeliveryNoteDetail(@RequestBody DeliveryNoteDetail deliveryNoteDetail) {
        return deliveryNoteService.deleteDeliveryNoteDetail(deliveryNoteDetail);
    }
}
