package vn.edu.rmit.tradingcompany.controller;

import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @RequestMapping(path = "revenue", method = RequestMethod.GET)
    public HashMap<String, Object> getRevenue(@RequestParam(value = "from", required = false) Long from,
                                              @RequestParam(value = "to", required = false) Long to) {
        return businessService.getRevenue(from, to);
    }

    @RequestMapping(path = "inventory", method = RequestMethod.GET)
    public HashMap<String, Object> getInventory(@RequestParam(value = "from", required = false) Long from,
                                                @RequestParam(value = "to", required = false) Long to) {
        return businessService.getInventory(from, to);
    }
}
