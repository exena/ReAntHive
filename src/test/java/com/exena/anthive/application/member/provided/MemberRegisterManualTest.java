package com.exena.anthive.application.member.provided;

import com.exena.anthive.application.member.MemberModifyService;
import com.exena.anthive.application.member.required.MemberRepository;
import com.exena.anthive.domain.shared.Email;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberFixture;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

    @Test
    void registerTestStub() {
        MemberRegister memberRegister = new MemberModifyService(
                new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createRegisterRequest());
        
        assertThat(member.getId()).isNotNull();
    }

    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();
        MemberRegister memberRegister = new MemberModifyService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createRegisterRequest());

        assertThat(member.getId()).isNotNull();
        
        assertThat(emailSenderMock.getTo()).hasSize(1);
        assertThat(emailSenderMock.getTo().getFirst()).isEqualTo(member.getEmail());
    }

    @Test
    void registerTestMockito() {
        EmailSenderMock emailSenderMock = Mockito.mock(EmailSenderMock.class);
        MemberRegister memberRegister = new MemberModifyService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = memberRegister.register(MemberFixture.createRegisterRequest());

        assertThat(member.getId()).isNotNull();

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
    }

    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }

        @Override
        public Optional<Member> findById(Long memberId) {
            return Optional.empty();
        }

    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {
        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> to = new ArrayList<>();

        public List<Email> getTo() {
            return to;
        }

        @Override
        public void send(Email email, String subject, String body) {
            to.add(email);
        }
    }
}