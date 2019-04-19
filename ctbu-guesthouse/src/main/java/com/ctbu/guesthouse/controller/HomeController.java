package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.User;
import com.ctbu.guesthouse.service.PositionService;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    RoomService roomService;
    @Autowired
    PositionService positionService;

    @GetMapping("/login")
    public String loginGet(){
        System.out.println("get");
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        User userDetails = (User) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        System.out.println(userDetails);
        return "redirect:home";
    }

    @RequestMapping("/home")
    public String home(@RequestParam(name = "pos",required = false) Integer posId,
                       ModelMap modelMap){
        List<Room> rooms = roomService.findAll(posId);
        List<Position> positions = positionService.findAll();
        modelMap.addAttribute("rooms", rooms);
        modelMap.addAttribute("positions", positions);
        return "index";
    }

    @RequestMapping("/analytics")
    public String analytics() {
        return "analytics";
    }

}
