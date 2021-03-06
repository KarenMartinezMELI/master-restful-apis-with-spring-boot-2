package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.dtos.order.OrderMmDTO;
import com.stacksimplify.restservices.dtos.order.OrderMmWithIdDTO;
import com.stacksimplify.restservices.exceptions.OrderNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.IOrderService;
import com.stacksimplify.restservices.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
public class OrderController {

    private final IOrderService orderService;
    private final IUserService userService;

    @Autowired
    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderMmWithIdDTO>> getAllOrdersByUserId(@Min(1) @PathVariable("id") Long userId) {
        try {
            return new ResponseEntity<>(userService.getAllOrders(userId), HttpStatus.OK);
        }catch (UserNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<OrderMmWithIdDTO> getOrderByOrderId(@Min(1) @PathVariable("id") Long userId, @Min(1) @PathVariable("orderId") Long orderId) {
        try {
            return new ResponseEntity<>(orderService.getOrderByUserIdAndOrderId(userId, orderId), HttpStatus.OK);
        }catch (UserNotFoundException | OrderNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderMmWithIdDTO> createOrder(@Min(1) @PathVariable("id") Long userId, @Valid @RequestBody OrderMmDTO order, UriComponentsBuilder builder) {
        try {
            OrderMmWithIdDTO userDetails=orderService.createOrder(userId,order);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/"+userId+"/orders/{id}").buildAndExpand(userDetails.getId()).toUri());
            return new ResponseEntity<>(userDetails, headers, HttpStatus.CREATED);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}
