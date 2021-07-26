package eu.acme.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testOrderAPI() throws Exception {

        //TODO: submit order request
        // 1. create order request
        // 2. convert to json string using Jackson Object Mapper
        // 3. set json string to content param
        MvcResult orderResult = this.mockMvc.perform(post("http://api.okto-demo.eu/orders").
//                content(orderRequestAsString)
                contentType("application/json")
                .accept("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto orderDto;
        // TODO: retrieve order dto from response
        // convert orderResult.getResponse().getContentAsString() to OrderDto using Jackson Object Mapper
        // make sure OrderDto contains correct data

    }

    void testOrderDoubleSubmission() {
        //TODO: write a test to trigger validation error when submit the same order twice (same client reference code)
    }

    void testFetchAllOrders() {
        //TODO: create 2 orders (by directly saving to database) and then invoke API call to fetch all orders
        // check that response contains 2 orders
        Order o = new Order();
        o.setStatus(OrderStatus.SUBMITTED);
        o.setClientReferenceCode("ORDER-1");
        o.setDescription("first order");
        o.setItemCount(10);
        o.setItemTotalAmount(BigDecimal.valueOf(102.23));
        orderRepository.save(o);

        Order or = new Order();
        or.setStatus(OrderStatus.SUBMITTED);
        or.setClientReferenceCode("ORDER-2");
        or.setDescription("second order");
        or.setItemCount(10);
        or.setItemTotalAmount(BigDecimal.valueOf(101.40));
        orderRepository.save(or);
        /** 
        MvcResult orderResult = this.mockMvc.perform(post("http://api.okto-demo.eu/orders").
        contentType("application/json")
        .accept("application/json"))
        .andExpect(status().isOk())
        .andReturn();

         OrderDto orderDto;
        */

       
    }

    void testFetchCertainOrder() {
        //TODO: create 1 order (by directly saving to database) and then invoke API call to fetch order
        // check response contains the correct order
        Order o = new Order();
        o.setStatus(OrderStatus.SUBMITTED);
        o.setClientReferenceCode("ORDER-1");
        o.setDescription("first order");
        o.setItemCount(10);
        o.setItemTotalAmount(BigDecimal.valueOf(102.23));
        orderRepository.save(o);

        OrderDto orderdto = new OrderDto();
        orderdto.setId(UUID.randomUUID());
        UUID uuid = orderdto.getId();
        /** 
        MvcResult orderResult = this.mockMvc.perform(post("http://api.okto-demo.eu/orders/uuid").
        contentType("application/json")
        .accept("application/json"))
        .andExpect(status().isOk())
        .andReturn();

         OrderDto orderDto;
         */

        //TODO: write one more test to check that when an order not exists, server responds with http 400
    }
}

