package com.zhfly021.controller;

import com.zhfly021.utils.VerifyCode.GifCaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 费志浩 Email:33566183@qq.com
 * @Description
 * @create 2020-11-27 13:19
 */
@RestController
@RequestMapping("/verifycode")
public class VerifyCodeController {

    public static final String SESSION_KEY = "GIF_CODE";

    @GetMapping("/get")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        response.setContentType("image/gif");

        GifCaptcha gifCaptcha = new GifCaptcha(120, 40, 4);
        HttpSession session = request.getSession();
        gifCaptcha.out(response.getOutputStream());
        session.removeAttribute(SESSION_KEY);
        session.setAttribute("SESSION_KEY",gifCaptcha.text());
    }
}
