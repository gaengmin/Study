package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {

    default Item findOne(Long id) {

        return findById(id).orElse(null);
    }
}
