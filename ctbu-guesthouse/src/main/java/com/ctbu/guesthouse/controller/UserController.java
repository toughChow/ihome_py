package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.dao.RoomDao;
import com.ctbu.guesthouse.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    RoomDao roomDao;
    

    @PostMapping(value = "/guest")
    @ResponseBody
    public String guestIn(@RequestBody Map map) {
        System.out.println(map);
        Long roomId = Long.valueOf((String) map.get("room_id"));
        String time = (String) map.get("time");
        String username = (String) map.get("username");
        String userId = (String) map.get("user_id");
        Float guestMoney = Float.valueOf((String) map.get("guest_money"));
        String[] times = time.split("-");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date startTime = null;
        Date endTime = null;
        Integer guestTime = null;
        try {
            startTime = sdf.parse(times[0]);
            endTime = sdf.parse(times[1]);
            guestTime = Math.toIntExact((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Room room = roomDao.findById(roomId).get();
        room.setGuestName(username);
        room.setGuestId(userId);
        room.setStartTime(times[0]);
        room.setEndTime(times[1]);
        room.setGuestTime(guestTime);
        room.setGivedPrice(guestMoney);
        room.setRequiredPrice(room.getPrice() * guestTime);
        room.setStatus(1);

        roomDao.save(room);

        return "办理入住成功";
    }

    @PostMapping(value = "/guest/continue")
    @ResponseBody
    public String guestInContinue(@RequestBody Map map) {
        System.out.println(map);
        Long roomId = Long.valueOf((String) map.get("room_id"));
        String time = (String) map.get("time");
        Float guestMoney = Float.valueOf((String) map.get("guest_money"));
        String[] times = time.split("-");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date startTime = null;
        Date endTime = null;
        Integer guestTime = null;
        try {
            startTime = sdf.parse(times[0]);
            endTime = sdf.parse(times[1]);
            guestTime = Math.toIntExact((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
        }

        Room room = roomDao.findById(roomId).get();
        room.setEndTime(times[1]);
        room.setGuestTime(room.getGuestTime() + guestTime);
        room.setGivedPrice(room.getGivedPrice() + guestMoney);
        room.setRequiredPrice(room.getRequiredPrice() + room.getPrice() * guestTime);

        roomDao.saveAndFlush(room);

        return "办理入住成功";
    }

    @RequestMapping("/room/reuse")
    @ResponseBody
    public String reuseRoom(@RequestParam(name = "room_id") Long rooId) {
        Room room = roomDao.findById(rooId).get();
        room.setStatus(0);
        roomDao.saveAndFlush(room);
        return "成功";
    }


    public static void main(String[] args) {
        Date date = new Date("20190419");
        System.out.println(date.getTime());
    }
    @GetMapping("/guest/test")
    public String test(){
        return "6";
    }
}
