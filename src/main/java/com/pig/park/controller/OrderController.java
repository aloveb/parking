package com.pig.park.controller;

import com.pig.park.entity.Order;
import com.pig.park.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * 根据用户的ID查询自己的订单，包含租出或者租入
     * @param uid User 的 ID
     * @return 所有满足条件的订单
     */
    @RequestMapping(value = "/getOrderListByUser",method = RequestMethod.GET)//根据用户ID查询用户的订单
    public @ResponseBody List<Order> getOrderListByUserId(@RequestParam("id") String uid) {
        return orderRepository.findAllByRentIdOrTenantId(uid, uid);
    }

    /**
     * 新增订单
     * @param order 新增的订单参数，前端需要做校验
     * @return 新增成功后返回的数据库的订单
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)//添加订单
    public @ResponseBody Order addOrder(@RequestBody Order order) {
        order.setReleaseDate(new Date());
        order.setOrderState(1);
        return orderRepository.save(order);
    }

    /**
     * 更新订单
     * @param order 需要更新订单的信息
     * @return 修改状态 true修改成功
     */
    @RequestMapping(value = "/edit",method = RequestMethod.PUT)//修改订单
    public @ResponseBody boolean editOrder(@RequestBody Order order) {
        if(order.getOrderId() == null) {
            return false;
        }
        if(1 != (orderRepository.findById(order.getOrderId()).get().getOrderState())){ //检查订单状态是否非发布中，若不是发布中则拒绝修改
            return false;
        }

        orderRepository.save(order);
        return true;

    }

    //TODO 后面需要继续增强，比如返回具体的消息格式
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.POST)//取消订单
    public @ResponseBody boolean deleteOrderById(@RequestParam("id") long id){
        Order order = orderRepository.findById(id).get();
        if(1 != order.getOrderState()){//检查订单状态是否非发布中，若不是发布中则拒绝修改
            return false;
        }
        order.setOrderState(0);
        return true;
    }

//    @RequestMapping(value = "/grabOrder",method = RequestMethod.POST)//抢订单
//    public @ResponseBody boolean grabOrder(@RequestParam("id") long id, @RequestParam("tenantId") String tenantId,
//                            @RequestParam("confirmDate") String confirmDate){
//        Order order = orderRepository.findById(id).get();
//        if (1 != order.getOrderState())//检查订单状态是否非发布中，若不是发布中则拒绝修改
//            return false;
//        order.setTenantId(tenantId);
//        order.setConfirmDate(confirmDate);
//        order.setOrderState(2);
//        return true;
//    }

    @RequestMapping(value = "/abandonOrder",method = RequestMethod.POST)//租户放弃订单
    public @ResponseBody boolean abandonOrder(@RequestParam("id") long id){
        Order order = orderRepository.findById(id).get();
        if (2 != order.getOrderState())//检查订单状态是否非租借中，若不是租借中则拒绝修改
            return false;
        order.setTenantId(null);
        order.setConfirmDate(null);
        return true;
    }

//    @RequestMapping(value = "/checkOrder",method = RequestMethod.POST)//检查订单是否过期，每日更新
//    public @ResponseBody boolean CheckOrderIsExpire(){ //检查系统内订单是否结束
//        Date now = new Date();         //当前时间
//        Date orderDate = new Date();   //订单时间
//        Iterator OrderIterator = orderRepository.findAll().iterator();
//        while(OrderIterator.hasNext()) {
//            Order order = (Order) OrderIterator.next();
//            try {
//                orderDate = sf.parse(order.getOrderDate());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if(orderDate.compareTo(now) > 0 ){
//                if(null == order.getTenantId()){
//                    order.setOrderState(0);
//                }else
//                    order.setOrderState(3);
//            }
//        }
//        return true;
//    }
}
