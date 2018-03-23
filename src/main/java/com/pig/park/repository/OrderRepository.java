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

    List<Order> findAllByRentIdOrTenantId(String rentId, String tenantId);
    Iterable<Order> findAll();
    Order findByParkAreaAndParkBuildAndParkNumAndOrderDate(String parkArea,String parkBuild,String parkNum,String orderDate);
    Order findById(long id);
    void CheckOrderisExpire();
    Order save(Order order);
    List<Order> findAllByOrderState(int orderState);
    @Modifying
    @Query(value = "update Order o set o.parkArea=:parkArea,o.parkBuild=:parkBuild," +
            " o.parkNum=:parkNum, o.price=:price, o.orderDate=:orderDate where o.id=:id")
    void editOrderByid(long id, String parkArea,String parkBuild,String parkNum,int price, String orderDate);

}