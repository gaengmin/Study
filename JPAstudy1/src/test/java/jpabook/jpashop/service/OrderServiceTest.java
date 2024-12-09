package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ItemRepository itemRepository;
    @Test
    void order() {
        //given
        Member member = getMember();

        Book book = getBook();

        int orderCount = 4;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);


        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다");
        assertEquals(10000 * 4, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(6, book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
    }


    @Test
     public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = getMember();
        Book book = getBook();
        int orderCount = 11;

        // when & then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        }, "재고 수량 부족 예외가 발생해야 합니다.");
    }

    @Test
    void orderCancel() {
        //given
        //when
        //then
    }


    private Book getBook() {
        Book book = new Book();
        book.setName("시j");
        book.setPrice(10000);
        book.setStockQuantity(10);
        itemRepository.save(book);
        return book;
    }

    private Member getMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("seoul","river","123-123"));
        memberRepository.save(member);
        return member;
    }

}