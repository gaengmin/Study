package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest public class MemberRepositoryTest {

    @Autowired
    private  MemberRepository memberRepository;

    private static Logger log = Logger.getLogger(MemberRepositoryTest.class.getName());

    @Test
    void saveAndFindById() {
        // given
        log.info("Test시작");
        Member member = new Member();
        member.setName("John Doe");

        log.info("MemberName : "+member.getName());

        // when
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).orElse(null);

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getName()).isEqualTo("John Doe");
    }
}

