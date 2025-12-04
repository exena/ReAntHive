package com.exena.anthive.adapter.webapi;

import com.exena.anthive.domain.MemberRegisterRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberPage {

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
    public String register(@Validated @ModelAttribute("registerRequest") MemberRegisterRequest request,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/member/register";
        }
        return "redirect:/";
    }
}
