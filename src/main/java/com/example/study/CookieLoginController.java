package com.example.study;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cookie-login")
public class CookieLoginController {

    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(@CookieValue(name = "userId", required = false) String userId, Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        User loginUser = userService.getLoginUserByLoginId(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        if(userService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }

        if(userService.checkNicknameDuplicate(joinRequest.getNickname())) {
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }

        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "join";
        }

        userService.join(joinRequest);
        return "redirect:/cookie-login";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult, HttpServletResponse response, Model model) {
        model.addAttribute("loginType", "cookie-login");
        model.addAttribute("pageName", "쿠키 로그인");

        User user = userService.login(loginRequest);

        if(user==null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if(bindingResult.hasErrors()) {
            return "login";
        }

        Cookie cookie = new Cookie("userId", String.valueOf(user.getId()));
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return "redirect:/cookie-login";
    }
}