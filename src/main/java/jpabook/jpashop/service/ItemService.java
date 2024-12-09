package jpabook.jpashop.service;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional // 쓰기 작업이므로 readOnly 설정 제거
    public void saveItem(Item item) {
        if (item.getId() == null) { // 새로 생성된 엔티티인 경우
            itemRepository.save(item);
        } else { // 기존 엔티티인 경우 변경 감지 사용
            Item existingItem = itemRepository.findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다. id=" + item.getId()));
            existingItem.update(existingItem.getName(), existingItem.getPrice(), existingItem.getStockQuantity());
        }
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다. id=" + itemId));
    }

}
