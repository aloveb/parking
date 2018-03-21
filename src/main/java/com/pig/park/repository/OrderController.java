package com.pig.park.repository;

import com.pig.park.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/addorder")
    public boolean addOrder(@RequestParam("rentId") String rentId, @RequestParam("parkArea") String parkArea,
                           @RequestParam("parkBuild") String parkBuild, @RequestParam("parkNum") String parkNum,
                           @RequestParam("price") int price, @RequestParam("releaseDate") String releaseDate,
                           @RequestParam("orderDate") String orderDate) {
        Order order = new Order();
        order.setRentId(rentId);
        order.setParkArea(parkArea);
        order.setParkBuild(parkBuild);
        order.setParkNum(parkNum);
        order.setPrice(price);
        order.setReleaseDate(releaseDate);
        order.setOrderDate(orderDate);
        order.setOrderState(1);
        orderRepository.save(order);
        return true;
    }

    @RequestMapping(value = "/editorder")
    public boolean editOrderByid(@RequestParam("id") long id, @RequestParam("parkArea") String parkArea,
                                @RequestParam("parkBuild") String parkBuild, @RequestParam("parkNum") String parkNum,
                                @RequestParam("price") int price, @RequestParam("orderDate") String orderDate) {
        if(1 != (orderRepository.findById(id)).getOrderState()){
            return false;
        }

        orderRepository.editOrderByid(id,parkArea,parkBuild,parkNum,price,orderDate);
        return true;

    }

    @RequestMapping(value = "/cancelorder")
    public boolean deleteOrderByid(@RequestParam("id") long id){
        Order order = orderRepository.findById(id);
        if(1 != order.getOrderState()){
            return false;
        }
        order.setOrderState(0);
        return true;
    }

    @RequestMapping(value = "/graborder")
    public boolean grabOrder(@RequestParam("id") long id, @RequestParam("tenantId") String tenantId,
                            @RequestParam("confirmDate") String confirmDate){
        Order order = orderRepository.findById(id);
        if (1 != order.getOrderState())
            return false;
        order.setTenantId(tenantId);
        order.setConfirmDate(confirmDate);
        order.setOrderState(2);
        return true;
    }

    @RequestMapping(value = "/abandonorder")
    public boolean abandonOrder(@RequestParam("id") long id){
        Order order = orderRepository.findById(id);
        if (2 != order.getOrderState())
            return false;
        order.setTenantId(null);
        order.setConfirmDate(null);
        return true;
    }
}
