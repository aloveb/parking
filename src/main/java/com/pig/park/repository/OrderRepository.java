package com.pig.park.repository;

import com.pig.park.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    Iterable<Order> findAllByRentId(int rentId);

    Iterable<Order> findAllByOrderState(int orderState);
}