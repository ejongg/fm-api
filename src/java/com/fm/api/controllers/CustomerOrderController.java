
package com.fm.api.controllers;

import com.fm.api.classes.CustomerOrder;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fm.api.dao.CustomerOrderService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("orders")
public class CustomerOrderController {
    CustomerOrderService orderService = new CustomerOrderService();
    
    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerOrder> getAllOrders(){
        List<CustomerOrder> orders = orderService.getAllCustomerOrders();
        return orders;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public CustomerOrder getOrderById(@PathVariable int id){
        CustomerOrder order = orderService.getOrderById(id);
        return order;
    }
    
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public CustomerOrder addOrder(@RequestBody CustomerOrder order){
        CustomerOrder ord = orderService.addOrder(order);
        return ord;
    }
    
    @RequestMapping(value="/mark/delivered/{id}", method=RequestMethod.PUT)
    public CustomerOrder markAsDelivered(@PathVariable int id){
        CustomerOrder completedOrder = orderService.markOrderComplete(id);
        return completedOrder;
    }
    
    @RequestMapping(value="/mark/cancelled/{id}", method=RequestMethod.PUT)
    public CustomerOrder markAsCancelled(@PathVariable int id){
        CustomerOrder cancelledOrder = orderService.cancelOrder(id);
        return cancelledOrder;
    }
}
