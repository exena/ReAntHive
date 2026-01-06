package com.anthive.article.adapter.webapi.member;

import com.anthive.article.application.member.provided.MemberRegister;
import com.anthive.article.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberPage {
    private final MemberRegister memberRegister;

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        // record의 경우 불변 객체이기 때문에 빈 생성자는 불가능하지만 빌더를 쓰면 빈 생성자와 같은 효과를 낼 수 있다.
        // 대신 NonNullApi에 위배된다.
        model.addAttribute("registerRequest", new MemberRegisterRequest());
        return "member/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") MemberRegisterRequest request,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/member/register";
        }
        memberRegister.register(request);
        return "redirect:/";
    }
}
