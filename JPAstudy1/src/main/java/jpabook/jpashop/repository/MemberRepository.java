package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 기본 제공되는 메서드:
    // - save(Member member): 엔티티 저장
    // - findById(Long id): ID로 엔티티 조회
    // - findAll(): 모든 엔티티 조회

    // 커스텀 쿼리 메서드
    @Query("SELECT m FROM Member m WHERE m.name = :name")
    List<Member> findByName(String name);
    // ID로 회원 검색 (기본 제공되는 메서드 사용)
    default Member findOne(Long id) {
        return findById(id).orElse(null);
    }}
