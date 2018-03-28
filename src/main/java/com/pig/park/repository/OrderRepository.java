package com.pig.park.repository;

import com.pig.park.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Iterator;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    List<Order> findAllByRentIdOrTenantId(Long rentId, Long tenantId); //通过租户订单或者用户订单查询订单
    List<Order> findAllByOrderState(int orderState); //查找订单根据状态
    Order findByOrderId(Long orderId);
    void deleteByOrderId(Long orderId);             //方便测试时删除数据
}