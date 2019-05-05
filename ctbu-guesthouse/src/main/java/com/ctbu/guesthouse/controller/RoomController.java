package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("/list")
    public String book(ModelMap modelMap) {
        List<Room> rooms = roomService.findAll(null);
        modelMap.addAttribute("rooms",rooms);
        return "/room/list";
    }

    /**
     * 更新房间信息
     * @param map
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public String updateRoom(@RequestBody Map map) {
        return roomService.updateRoom(map);
    }

    @GetMapping("/money")
    public String money(ModelMap modelMap) {
        return "/room/money";
    }

    @GetMapping("/money/detail")
    @ResponseBody
    public Object moneyDetail(@RequestParam(name = "pn", defaultValue = "0") Integer pageNo) {
        return roomService.pageLogDetail(pageNo);
    }

    @GetMapping("/money/total")
    @ResponseBody
    public Object moneyTotal() {
        return roomService.findTotalIncom();
    }

    @GetMapping("/log")
    public String log(ModelMap modelMap) {
        return "/room/log";
    }

    @GetMapping("/consume")
    public String consume(ModelMap modelMap) {
        return "/room/consume";
    }

    @GetMapping("/consume/detail")
    @ResponseBody
    public Object consumeDetail(@RequestParam(name = "pn", defaultValue = "0") Integer pageNo) {
        return roomService.pageConsumeDetail(pageNo);
    }

    @GetMapping("/consume/gooods/list")
    @ResponseBody
    public Object goodsList() {
        return roomService.getAllGoods();
    }
}
