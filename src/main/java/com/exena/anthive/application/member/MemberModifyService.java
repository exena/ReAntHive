package com.exena.anthive.application.member;

import com.exena.anthive.application.member.provided.EmailSender;
import com.exena.anthive.application.member.provided.MemberRegister;
import com.exena.anthive.application.member.required.MemberRepository;
import com.exena.anthive.domain.member.DuplicateEmailException;
import com.exena.anthive.domain.member.Member;
import com.exena.anthive.domain.member.MemberRegisterRequest;
import com.exena.anthive.domain.shared.Email;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
//    private final MemberFinder memberFinder; // 다른 포트를 가져와서 사용해도 괜찮다
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest){
        checkDuplicateEmail(registerRequest);
        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);
        sendWelcomeEmail(member);
        return member;
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "회원가입 확인 이메일입니다", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.getEmail())).isPresent()){
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.getEmail());
        }
    }

}
