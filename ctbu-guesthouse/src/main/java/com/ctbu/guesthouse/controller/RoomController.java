package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/book")
    public String book(ModelMap modelMap) {
        List<Room> rooms = roomService.findAll(null);
        modelMap.addAttribute("rooms",rooms);
        return "/room/list";
    }
}
