package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원가입테스트")
    //@Rollback(value = false)
    @Test
    public void join () throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        // then
        Member foundMember = memberRepository.findById(saveId).orElse(null);
        assertThat(foundMember).isNotNull(); // 저장된 회원이 null이 아닌지 확인
        assertThat(foundMember.getName()).isEqualTo("kim"); // 저장된 회원 이름 확인
    }

    @DisplayName("중복회원예외")
    @Test
    public void 중복_회원_예외() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");
        //When
        memberService.join(member1);
        memberService.join(member2);


        //Then
        fail("예외가 발생해야 한다.");
    }
}
