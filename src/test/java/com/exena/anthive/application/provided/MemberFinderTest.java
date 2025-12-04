package com.exena.anthive.application.provided;

import com.exena.anthive.AnthiveTestConfiguration;
import com.exena.anthive.domain.Member;
import com.exena.anthive.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void find() {
        Member member = memberRegister.register(MemberFixture.createRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member found = memberFinder.find(member.getId());

        assertThat(member.getId()).isEqualTo(found.getId());
    }
    
    @Test
    void findFail() {
        assertThatThrownBy(()-> memberFinder.find(999L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}