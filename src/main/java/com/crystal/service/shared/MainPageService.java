package com.crystal.service.shared;

import org.springframework.web.servlet.ModelAndView;

public interface MainPageService {
    ModelAndView generatePage(String s, ModelAndView model, Long userId);
}
