package com.pig.park.repository;

import com.pig.park.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Iterable<Order> findAllByRentId(int rentId);
    Order findById(long id);
    Order save(Order order);
    Iterable<Order> findAllByOrderState(int orderState);
    @Modifying
    @Query(value = "update Order o set o.parkArea=:parkArea,o.parkBuild=:parkBuild," +
            " o.parkNum=:parkNum, o.price=:price, o.orderDate=:orderDate where o.id=:id")
    void editOrderByid(long id, String parkArea,String parkBuild,String parkNum,int price, String orderDate);

}