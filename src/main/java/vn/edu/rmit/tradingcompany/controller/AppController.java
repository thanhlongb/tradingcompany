package vn.edu.rmit.tradingcompany.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class AppController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public String home(Pageable p) {
        return p.toString();
    }
}
