package com.pig.park.repository;

import com.pig.park.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); //时间格式
    @RequestMapping(value = "/addorder",method = RequestMethod.POST)//添加订单
    public boolean addOrder(@RequestParam("rentId") String rentId, @RequestParam("parkArea") String parkArea,
                           @RequestParam("parkBuild") String parkBuild, @RequestParam("parkNum") String parkNum,
                           @RequestParam("price") int price, @RequestParam("releaseDate") String releaseDate,
                           @RequestParam("orderDate") String orderDate) {
        if(null != orderRepository.findByParkAreaAndParkBuildAndParkNumAndOrderDate(parkArea,parkBuild,parkNum,orderDate))
            return false;//查找是否存在某个车位的订单，存在则返回false
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

    @RequestMapping(value = "/editorder",method = RequestMethod.POST)//修改订单
    public boolean editOrderById(@RequestParam("id") long id, @RequestParam("parkArea") String parkArea,
                                @RequestParam("parkBuild") String parkBuild, @RequestParam("parkNum") String parkNum,
                                @RequestParam("price") int price, @RequestParam("orderDate") String orderDate) {
        if(1 != (orderRepository.findById(id)).getOrderState()){ //检查订单状态是否非发布中，若不是发布中则拒绝修改
            return false;
        }

        orderRepository.editOrderByid(id,parkArea,parkBuild,parkNum,price,orderDate);
        return true;

    }

    @RequestMapping(value = "/cancelorder",method = RequestMethod.POST)//取消订单
    public boolean deleteOrderById(@RequestParam("id") long id){
        Order order = orderRepository.findById(id);
        if(1 != order.getOrderState()){//检查订单状态是否非发布中，若不是发布中则拒绝修改
            return false;
        }
        order.setOrderState(0);
        return true;
    }

    @RequestMapping(value = "/graborder",method = RequestMethod.POST)//抢订单
    public boolean grabOrder(@RequestParam("id") long id, @RequestParam("tenantId") String tenantId,
                            @RequestParam("confirmDate") String confirmDate){
        Order order = orderRepository.findById(id);
        if (1 != order.getOrderState())//检查订单状态是否非发布中，若不是发布中则拒绝修改
            return false;
        order.setTenantId(tenantId);
        order.setConfirmDate(confirmDate);
        order.setOrderState(2);
        return true;
    }

    @RequestMapping(value = "/abandonorder",method = RequestMethod.POST)//租户放弃订单
    public boolean abandonOrder(@RequestParam("id") long id){
        Order order = orderRepository.findById(id);
        if (2 != order.getOrderState())//检查订单状态是否非租借中，若不是租借中则拒绝修改
            return false;
        order.setTenantId(null);
        order.setConfirmDate(null);
        return true;
    }

    @RequestMapping(value = "/checkOrder",method = RequestMethod.POST)//检查订单是否过期，每日更新
    public boolean CheckOrderisExpire(){ //检查系统内订单是否结束
        Date now = new Date();         //当前时间
        Date orderDate = new Date();   //订单时间
        Iterator OrderIterator = orderRepository.findAll().iterator();
        while(OrderIterator.hasNext()) {
            Order order = (Order) OrderIterator.next();
            try {
                orderDate = sf.parse(order.getOrderDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(orderDate.compareTo(now) > 0 ){
                if(null == order.getTenantId()){
                    order.setOrderState(0);
                }else
                    order.setOrderState(3);
            }
        }
        return true;
    }
}
