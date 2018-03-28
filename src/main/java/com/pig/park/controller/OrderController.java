package com.pig.park.controller;

import com.pig.park.entity.Order;
import com.pig.park.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @RequestMapping(value = "/getOrderList",method = RequestMethod.GET)//根据用户ID查询用户的订单
    public @ResponseBody List<Order> getOrderList(@RequestParam("id") Long uid) {
        List<Order> orderList = orderRepository.findAllByRentIdOrTenantId(uid, uid);
        Collections.sort(orderList, new Comparator<Order>(){
            @Override
            public int compare(Order o1, Order o2) {
                return o2.getOrderDate().compareTo(o1.getOrderDate());
            }
        });
        return orderList;
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
    @RequestMapping(value = "/edit",method = RequestMethod.PATCH)//修改订单
    public @ResponseBody Order editOrder(@RequestBody Order order) {
        if(order.getOrderId() == null) {
            return null;
        }
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
    @RequestMapping(value = "/cancelOrder",method = RequestMethod.PATCH)//取消订单
    public @ResponseBody Order deleteOrderById(@RequestParam("orderId") Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
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
      @RequestMapping(value = "/grabOrder",method = RequestMethod.PATCH)  //抢车位
      public @ResponseBody Order grabOrder(@RequestParam("orderId") Long orderId, @RequestParam("tenantId") Long tenantId){
          Order order = orderRepository.findByOrderId(orderId);
          if (1 != order.getOrderState())  //检查订单状态是否非发布中，若不是发布中则拒绝修改
              return null;
          order.setTenantId(tenantId);
          order.setConfirmDate(new Date());
          order.setOrderState(2);
          return orderRepository.save(order);
      }

    /**
     * 放弃车位
     * @param orderId 待放弃的订单ID
     * @return 检查订单状态，如果已经处于订单车位时间内，则放弃失败返回false，否则设置ID和时间为空，返回true
     */
    @RequestMapping(value = "/abandonOrder",method = RequestMethod.PATCH)//租户放弃车位
    public @ResponseBody boolean abandonOrder(@RequestParam("orderId") Long orderId){
        Order order = orderRepository.findByOrderId(orderId);
        if (2 != order.getOrderState())//检查订单状态是否非租借中，若不是租借中则拒绝修改
            return false;
        order.setTenantId(null);
        order.setConfirmDate(null);
        return true;
    }

    //TODO 后面需要继续增强，比如返回具体的消息格式
    /** for test
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)//获取用户账户内的猪猪币余额
    public @ResponseBody void delete(@RequestParam("id")Long orderId){
        orderRepository.deleteByOrderId(orderId);
    }

    /**
     * 检查订单
     */
      @RequestMapping(value = "/orderfresh",method = RequestMethod.PUT)  //检查订单是否过期，每日更新
      public @ResponseBody void CheckOrderIsExpire(){   //检查系统内订单是否结束
          Date now = new Date();           //当前时间
          Date orderDate;                  //订单时间
          Iterator OrderIterator = orderRepository.findAll().iterator();
          while(OrderIterator.hasNext()) {
              Order order = (Order) OrderIterator.next();
              orderDate = order.getOrderDate();
              if(orderDate.compareTo(now) > 0 ){
                  if(null == order.getTenantId()){
                      order.setOrderState(0);   //订单时间大于当前时间，若租户ID为null，则标识过期
                  }else
                      order.setOrderState(3);   //订单时间大于当前时间，若租户ID不为null,则标识完成
              }
              else if(orderDate.compareTo(now) == 0 && null != order.getTenantId()){
                  order.setOrderState(2);       //订单时间等于当前时间，若租户ID不为null，则标识订单租借中
              }
              orderRepository.save(order);
          }
      }
}
