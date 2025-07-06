package com.ashutosh.order_service.repository;

import com.ashutosh.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//This tells the spring this is a DAO(Data Access Object) it means it deals with the database layer
public interface OrderRepository extends JpaRepository<Order, Long>{

}


//JpaRepository is a spring data interface that gives you a ready-made implementation of basic CRUD operations.