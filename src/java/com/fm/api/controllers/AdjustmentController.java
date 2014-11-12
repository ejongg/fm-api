
package com.fm.api.controllers;

import com.fm.api.classes.BadOrder;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.dao.AdjustmentService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value="adjustments")
public class AdjustmentController {
    AdjustmentService adjustmentService = new AdjustmentService();
    
    @RequestMapping(method=RequestMethod.GET)
    public List<BadOrder> getAllAdjustments(){
        List<BadOrder> badOrders = adjustmentService.getAllBadOrders();
        return badOrders;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public BadOrder getBadOrderById(@PathVariable int id){
        BadOrder badOrder = adjustmentService.getBadOrderById(id);
        return badOrder;
    }
    
    /*
    {
    "name" : "Coke",
    "brand" : "Coca-cola",
    "size" : "1L",
    "bottles" : 10,
    "price" : 25,
    "expense" : 250,
    "date" : "2014-11-13"
    }
    */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public BadOrder addBadOrder(@RequestBody BadOrder badOrder){
        BadOrder bo = adjustmentService.addBadOrder(badOrder);
        return bo;
    }
}
