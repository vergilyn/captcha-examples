package com.vergilyn.examples.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author VergiLyn
 * @blog http://www.cnblogs.com/VergiLyn/
 * @date 2018/4/30
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {
    private Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    private static final String SESSION_CAPTCHA_IMAGE_CODE = "SESSION_CAPTCHA_IMAGE_CODE";

    @RequestMapping("/index")
    public String greeting(Model model) {
        return "captcha";
    }

    @RequestMapping(value = "image", method = RequestMethod.GET)
    public void image(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        response.setContentType("image/jpeg"); //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        ImageCaptcha imageCaptcha = new ImageCaptcha(100, 34, 4, 20);
        session.setAttribute(SESSION_CAPTCHA_IMAGE_CODE, imageCaptcha.getCode());
        try {
            imageCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println();
            logger.error("验证码生成失败：{}", e.getMessage());
        }
    }

    @RequestMapping(value = "image/valid", method = RequestMethod.POST)
    @ResponseBody
    public String imageValid(HttpServletRequest request, String code, HttpSession session){
        Object sessionCode = session.getAttribute(SESSION_CAPTCHA_IMAGE_CODE);
        if(sessionCode != null && StringUtils.equalsIgnoreCase(sessionCode.toString(), code)){
            return "true";
        }else {
            return "false";
        }
    }
}
