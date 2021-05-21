package vn.edu.rmit.tradingcompany.controller;

import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // CREATE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Staff createStaff(@RequestBody Staff staff){
        return staffService.saveStaff(staff);
    }

    // READ
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Staff getStaff(@PathVariable int id) {
        return staffService.getStaff(id);
    }

    @RequestMapping(path = "get", method = RequestMethod.GET)
    public Staff getStaff(@RequestParam String email) {
        return staffService.getStaff(email);
    }

    @RequestMapping(path = "{id}/revenue", method = RequestMethod.GET)
    public String getStaffRevenue(@PathVariable int id,
                                  @RequestParam(value = "from", required = false) Long from,
                                  @RequestParam(value = "to", required = false) Long to) {
        return staffService.getRevenue(id, from, to);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Staff> getAllStaff(Pageable pageable) {
        return staffService.getAllStaff(pageable);
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public List<Staff> searchStaff(@RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "phone", required = false) String phone,
                                   @RequestParam(value = "address", required = false) String address) {
        return staffService.searchStaff(name, phone, address);
    }

    // UPDATE
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Staff updateStaff(@RequestBody Staff staff) {
        return staffService.updateStaff(staff);
    }

    // DELETE
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public Staff deleteStaff(@RequestBody Staff staff) {
        return staffService.deleteStaff(staff);
    }
}
