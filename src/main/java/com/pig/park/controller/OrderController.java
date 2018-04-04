package com.pig.park.controller;

import com.pig.park.entity.Order;
import com.pig.park.entity.User;
import com.pig.park.repository.OrderRepository;
import com.pig.park.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository ;

    /**
     * 根据用户的ID查询自己的订单，包含租出或者租入
     * @param uid User 的 ID
     * @return 所有满足条件的订单
     */
    @RequestMapping(value = "/getOrderList",method = RequestMethod.GET)//根据用户ID查询用户的订单
    public @ResponseBody List<Order> getOrderList(@RequestParam("id") Long uid) {
        List<Order> orderList = orderRepository.findAllByRentIdOrTenantIdOrderByOrderDateDesc(uid, uid);
        Date now = new Date();           //当前时间
        Date orderDate;                  //订单时间\
        Iterator OrderIterator = orderList.iterator();
        while(OrderIterator.hasNext()) {
            Order order = (Order) OrderIterator.next();
            orderDate = order.getOrderDate();
            if (0 != order.getOrderState() && orderDate.compareTo(now) < 0 && null == order.getTenantId()) {
                order.setOrderState(0);   //订单时间小于当前时间，若租户ID为null，则标识过期
                orderRepository.save(order);
            }
        }
        return orderList;
    }

    /**
     * 查看目前可用车位（我要车位）
     * @param rentId 本人ID，不查看本人发布的订单，因为自己不能抢自己的订单
     * @return 检查订单状态，如果已经处于订单车位时间内，则放弃失败返回false，否则设置ID和时间为空，返回true
     */
    @RequestMapping(value = "/findAvailableOrder",method = RequestMethod.GET)//查看目前可用车位
    public @ResponseBody List<Order> findAvailableOrder(@RequestParam("rentId") Long rentId){
        List<Order> orderList = orderRepository.findAllByOrderStateAndRentIdNotOrderByOrderDateAsc(1,rentId);
        Date now = new Date();           //当前时间
        Date orderDate;                  //订单时间
        Iterator OrderIterator = orderList.iterator();
        while(OrderIterator.hasNext()) {
            Order order = (Order) OrderIterator.next();
            orderDate = order.getOrderDate();
            if (0 != order.getOrderState() && orderDate.compareTo(now) < 0 && null == order.getTenantId()) {
                order.setOrderState(0);   //订单时间小于当前时间，若租户ID为null，则标识过期
                orderRepository.save(order);
            }
        }
        List<Order> neworderList = orderRepository.findAllByOrderStateAndRentIdNotOrderByOrderDateAsc(1,rentId);
        return neworderList;
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
    public @ResponseBody Order editOrder(@RequestBody Order order) {
        if(1 != (orderRepository.findById(order.getOrderId()).get().getOrderState())){ //检查订单状态是否非发布中，若不是发布中则拒绝修改
            return null;
        }
        return orderRepository.save(order);
    }


    /**
     * 取消订单
     * @param orderId 待取消的订单ID
     * @return 检查订单状态 若订单处于发布中便可以取消 true修改成功 false表示修改失败
     */
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.PUT)//租主取消订单
    public @ResponseBody Order deleteOrderById(@RequestParam("orderId") Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
        System.out.println(orderId);
        if(1 != order.getOrderState()){//检查订单状态是否非发布中，若不是发布中则拒绝修改
            return null;
        }
        order.setOrderState(0);
        return orderRepository.save(order);
    }

    /**
     * 抢车位
     * @param orderId 待取消的订单ID
     * @param tenantId 抢车位的用户ID
     * @return 检查订单状态是否是发布状态 若订单处于发布中便可以抢单 设定时间与租户ID以及订单状态 null表示失败 成功则返回订单信息
     */
      @RequestMapping(value = "/grabOrder",method = RequestMethod.PUT)  //抢车位
      public @ResponseBody Order grabOrder(@RequestParam("orderId") Long orderId, @RequestParam("tenantId") Long tenantId){
          Order order = orderRepository.findByOrderId(orderId);
          User tenant = userRepository.findByid(tenantId);
          User rent = userRepository.findByid(order.getRentId());
          int price = order.getPrice();
          int rentPurse = rent.getPurse();
          int tenantPurse = tenant.getPurse();
          if (1 != order.getOrderState())  //检查订单状态是否非发布中，若不是发布中则拒绝修改
              return null;
          if (tenantId == order.getRentId()){
              return null;                //车主不能自己抢自己的订单
          }
          if ( tenantPurse < price)
          {
              return null;                //余额不足，无法抢单
          }
          tenant.setPurse(tenantPurse-price);
          rent.setPurse(rentPurse+price);
          userRepository.save(tenant);
          userRepository.save(rent);
          order.setTenantId(tenantId);
          order.setConfirmDate(new Date());
          order.setOrderState(2);
          return orderRepository.save(order);
      }

    //TODO 后面需要继续增强，比如返回具体的消息格式

    /**
     * 锁上订单
     * @param orderId 待锁订单ID
     * @return 检查订单是否上锁，如果已经上锁，则放弃失败返回false，否则锁上订单，返回true
     */
    @RequestMapping(value = "/lock",method = RequestMethod.PUT)//订单上锁
    public boolean Lock(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        if(1 ==order.getOrderLock())
            return false;
        order.setOrderLock(1);
        orderRepository.save(order);
        return true;
    }

    /**
     * 解锁订单
     * @param orderId 待解锁订单ID
     */
    @RequestMapping(value = "/unlock",method = RequestMethod.PUT)//订单解锁
    public void UnLock(Long orderId){
        Order order = orderRepository.findById(orderId).get();
        order.setOrderLock(0);
        orderRepository.save(order);
    }

    /** for test
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//删除
    public @ResponseBody void delete(@RequestParam("id")Long orderId){
        orderRepository.deleteByOrderId(orderId);
    }
}
