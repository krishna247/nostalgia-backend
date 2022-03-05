package base.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RedirectToReactController {

    @RequestMapping(value = "/finishAuth")
    public String getIndex(HttpServletRequest request) {
        return "/index.html";
    }
 }
