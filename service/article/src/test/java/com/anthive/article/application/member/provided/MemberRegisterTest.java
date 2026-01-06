package com.anthive.article.application.member.provided;

import com.anthive.article.AnthiveTestConfiguration;
import com.anthive.article.domain.member.DuplicateEmailException;
import com.anthive.article.domain.member.Member;
import com.anthive.article.domain.member.MemberFixture;
import com.anthive.article.domain.member.MemberRegisterRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(AnthiveTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createRegisterRequest());

        assertThat(member.getId()).isNotNull();
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void memberRegisterFail() {
        checkValidation(new MemberRegisterRequest("test01@test.com", "짧아", "my_password", new HashSet<>()));
        checkValidation(new MemberRegisterRequest("test01@test.com", "테스트닉", "짧아", new HashSet<>()));
        checkValidation(new MemberRegisterRequest("illegalEmail", "테스트닉", "my_password", new HashSet<>()));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
