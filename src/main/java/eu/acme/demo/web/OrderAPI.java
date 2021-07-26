package eu.acme.demo.web;

import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import eu.acme.demo.web.dto.OrderItemDto;
import org.springframework.web.bind.annotation.*;
import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderAPI {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    //list containing all orders
    List<Order> orders = new ArrayList<Order>();

    

    public OrderAPI(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    public List<OrderLiteDto> fetchOrders() {
        //TODO: fetch all orders in DB
        //This returns a JSON or XML with the users

        //gets all the orders in the repository and stores them in variable orders
        orders = orderRepository.findAll();
        //list containing all OrderLiteDto
        List<OrderLiteDto> orderLiteDtoList = new ArrayList<OrderLiteDto>(); 
        //loops through each order and sets the variables of an OrdeLiteDto object 	
        for (Order ord : orders) { 	
            OrderLiteDto orderLiteDto = new OrderLiteDto();
            ord.setId(UUID.randomUUID());
            orderLiteDto.setClientReferenceCode(ord.getClientReferenceCode());
            orderLiteDto.setDescription(ord.getDescription());
            orderLiteDto.setTotalAmount(ord.getItemTotalAmount());	
            orderLiteDto.setItemCount(ord.getItemCount());
            orderLiteDto.setStatus(ord.getStatus());	
            orderLiteDtoList.add(orderLiteDto);
       }
        return orderLiteDtoList;
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<?> fetchOrder(@PathVariable UUID orderId) {

        //TODO: fetch specific order from DB
        // if order id not exists then return an HTTP 400 (bad request) with a proper payload that contains an error code and an error message
        OrderDto orddto = new OrderDto(); 
        List<OrderItemDto> listOrderItemDto = new ArrayList<OrderItemDto>();
        OrderItem ord;
        OrderItemDto ordItemDto = new OrderItemDto();
        //checks if there is an order with the given id.
        Optional<OrderItem> optord = orderItemRepository.findById(orderId);
        if(optord.isPresent()){
            //if there is an order, then store its value to ord
            ord = optord.get();
            //create an ordItemDto with the details of the item ordered
            ordItemDto.setItemId(orderId);
            ordItemDto.setUnits(ord.getUnits());
            ordItemDto.setUnitPrice(ord.getUnitPrice());
            ordItemDto.setTotalPrice(ord.getTotalPrice());
            listOrderItemDto.add(ordItemDto);
            //add the list of the ordItemDto to the ordDto object
            orddto.setOrderItems(listOrderItemDto);
            //returns the orderDto object   
            return new ResponseEntity<>(orddto, HttpStatus.OK);
        } else {
            // returns an HTTP 400 bad request if optord has no value
           return ResponseEntity.badRequest().body("The order Id is incorrect! Please enter a correct Id.");
        }
        
    }

    @PostMapping
    public ResponseEntity<?> submitOrder(@RequestBody OrderRequest orderRequest) {
        //TODO: submit a new 
        //if client reference code already exist then return an HTTP 400 (bad request) with a proper payload that contains an error code and an error message
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        boolean sameReferenceCode = false;
        //loops through all existing orders to find if there is an order with the same client referece code
        for (Order ordersloop : orders) { 
            if (ordersloop.getClientReferenceCode().equals(orderRequest.getClientReferenceCode()))
            {
                sameReferenceCode = true;
            }
        }
        if (sameReferenceCode == false){
        //create an order using the varibles submitted with the orderRequest
        order.setClientReferenceCode(orderRequest.getClientReferenceCode());
        order.setDescription(orderRequest.getDescription());
        order.setItemTotalAmount(orderRequest.getItemTotalAmount());
        order.setItemCount(orderRequest.getItemCount());
        order.setStatus(OrderStatus.SUBMITTED);
        //add the order to the list of orders
        orders.add(order);

        //create an order orderdto object with the variables submitted 
        orderDto.setId(UUID.randomUUID());
        orderDto.setDescription(orderRequest.getDescription());
        orderDto.setClientReferenceCode(orderRequest.getClientReferenceCode());
        orderDto.setTotalAmount(orderRequest.getItemTotalAmount()); 
        orderDto.setItemCount(orderRequest.getItemCount());
        orderDto.setStatus(OrderStatus.SUBMITTED);
        orderRepository.saveAll(orders);
        //returns the orderDto object
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
        }
        else {
            // returns a HTTP 400 bad request if the client reference code exist already
            return ResponseEntity.badRequest().body("Client reference code already exist!");
        }
        
    }

}
