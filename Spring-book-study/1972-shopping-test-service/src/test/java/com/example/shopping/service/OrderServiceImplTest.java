package com.example.shopping.service;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.example.shopping.input.CartItemInput;
import com.example.shopping.input.OrderInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.shopping.entity.Order;
import com.example.shopping.entity.Product;
import com.example.shopping.enumeration.PaymentMethod;
import com.example.shopping.exception.StockShortageException;
import com.example.shopping.input.CartInput;
import com.example.shopping.repository.OrderItemRepository;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    ProductRepository productRepository;
    
    
    @Test
    void test_placeOrder() {
        
        Product product1 = new Product();
        product1.setStock(10);
        Product product2 = new Product();
        product2.setStock(5);
        doReturn(product1).when(productRepository).selectById("p01");
        doReturn(product2).when(productRepository).selectById("p02");
                
        OrderInput orderInput = new OrderInput();
        orderInput.setName("김철수");
        orderInput.setAddress("서울시");
        orderInput.setPhone("010-0000-0000");
        orderInput.setEmailAddress("taro@example.com");
        orderInput.setPaymentMethod(PaymentMethod.CONVENIENCE_STORE);

        List<CartItemInput> cartItemInputs = new ArrayList<>();

        CartItemInput keshigom = new CartItemInput();
        keshigom.setProductId("p01");
        keshigom.setProductName("지우개");
        keshigom.setProductPrice(100);
        keshigom.setQuantity(3);
        cartItemInputs.add(keshigom);

        CartItemInput note = new CartItemInput();
        note.setProductId("p02");
        note.setProductName("노트");
        note.setProductPrice(200);
        note.setQuantity(4);
        cartItemInputs.add(note);

        CartInput cartInput = new CartInput();
        cartInput.setCartItemInputs(cartItemInputs);
        
        Order order = orderService.placeOrder(orderInput, cartInput);
        
        assertThat(order.getBillingAmount()).isEqualTo(1210);
        assertThat(order.getCustomerName()).isEqualTo("김철수");
        assertThat(order.getCustomerAddress()).isEqualTo("서울시");
        assertThat(order.getCustomerPhone()).isEqualTo("010-0000-0000");
        assertThat(order.getCustomerEmailAddress()).isEqualTo("taro@example.com");
        assertThat(order.getPaymentMethod()).isEqualTo(PaymentMethod.CONVENIENCE_STORE);

        verify(orderRepository).insert(any());
        verify(productRepository, times(2)).update(any());
        verify(orderItemRepository, times(2)).insert(any());        
    }
    
    @Test
    public void test_placeOrder_재고부족() {
        Product product = new Product();
        product.setStock(2);
        doReturn(product).when(productRepository).selectById("p01");

        OrderInput orderInput = new OrderInput();
        List<CartItemInput> cartItemInputs = new ArrayList<>();
        CartItemInput cartItemInput = new CartItemInput();
        cartItemInput.setProductId("p01");
        cartItemInput.setProductPrice(100);
        cartItemInput.setQuantity(3);
        cartItemInputs.add(cartItemInput);
        CartInput cartInput = new CartInput();
        cartInput.setCartItemInputs(cartItemInputs);
        assertThatThrownBy(() -> {
            orderService.placeOrder(orderInput, cartInput);
        }).isInstanceOf(StockShortageException.class);
    }
}
