package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.dao.RoomDao;
import com.ctbu.guesthouse.dao.SysLogDao;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.service.UserService;
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
    UserService userService;
    @Autowired
    RoomDao roomDao;
    @Autowired
    SysLogDao sysLogDao;


    /**
     * 办理入住
     *
     * @param map
     * @return
     */
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

    /**
     * 退房
     *
     * @param rooId
     * @return
     */
    @RequestMapping("/guest/out")
    @ResponseBody
    public String guestOut(@RequestParam(name = "room_id") Long rooId) {
        Room room = roomDao.findById(rooId).get();
        Float givedPrice = room.getGivedPrice();
        Float requiredPrice = room.getRequiredPrice();
        float money = requiredPrice - givedPrice;

        SysLog sysLog = new SysLog(room.getId(), room.getPrice(), room.getRequiredPrice(), room.getGuestName(), room.getGuestId(),
                room.getStartTime(), room.getEndTime(), room.getGuestTime());
        sysLogDao.save(sysLog);

        room.initRoom();
        roomDao.saveAndFlush(room);

        if (money > 0)
            return "退房成功,客人需补交" + money + "元";
        else if (money < 0)
            return "退房成功,需退还押金" + money + "元";
        else
            return "退房成功,无退补金额";
    }

    /**
     * 房屋重新使用
     *
     * @param rooId
     * @return
     */
    @RequestMapping("/room/reuse")
    @ResponseBody
    public String reuseRoom(@RequestParam(name = "room_id") Long rooId) {
        Room room = roomDao.findById(rooId).get();
        room.setStatus(0);
        roomDao.saveAndFlush(room);
        return "成功";
    }

    /**
     * 房屋保修
     *
     * @param rooId
     * @return
     */
    @RequestMapping("/room/broken")
    @ResponseBody
    public String brokenRoom(@RequestParam(name = "room_id") Long rooId, @RequestParam(name = "repair_msg") String repairMsg) {
        Room room = roomDao.findById(rooId).get();
        room.initRoom();
        room.setStatus(2);
        room.setRemark(repairMsg);
        roomDao.saveAndFlush(room);
        return "成功";
    }

    /**
     * 房屋保修
     *
     * @param rooId
     * @return
     */
    @RequestMapping("/book/confirm")
    @ResponseBody
    public String bookConfirm(@RequestParam(name = "room_id") Long rooId, @RequestParam(name = "money") String bookMoney) {
        Room room = roomDao.findById(rooId).get();
        room.setStatus(1);
        Float moneyAdd = Float.valueOf(bookMoney);
        room.setGivedPrice(room.getGivedPrice() + moneyAdd);
        roomDao.saveAndFlush(room);
        return "办理入住成功";
    }

    /**
     * 分析用户
     *
     * @return
     */
    @RequestMapping("/analytics")
    @ResponseBody
    public Object analytics() {
        return userService.analyticsUser();
    }


    /**
     * 预约房间
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/book")
    @ResponseBody
    public String bookRoom(@RequestBody Map map) {
        System.out.println(map);
        Long roomId = Long.valueOf((String) map.get("room_id"));
        String time = (String) map.get("time");
        String username = (String) map.get("username");
        Float guestMoney = Float.valueOf((String) map.get("guest_money"));
        String userId = (String) map.get("user_id");
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
        room.initRoom();
        room.setGuestName(username);
        room.setGuestId(userId);
        room.setStartTime(times[0]);
        room.setEndTime(times[1]);
        room.setGivedPrice(guestMoney);
        room.setGuestTime(guestTime);
        room.setRequiredPrice(room.getPrice() * guestTime);
        room.setStatus(3); // 设为已预约;

        roomDao.save(room);

        return "办理预约成功";
    }


    public static void main(String[] args) {
        Date date = new Date("20190419");
        System.out.println(date.getTime());
    }

    @GetMapping("/guest/test")
    public String test() {
        return "6";
    }
}
