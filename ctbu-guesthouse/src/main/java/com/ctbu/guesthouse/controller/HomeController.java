package com.ctbu.guesthouse.controller;

import com.ctbu.guesthouse.dao.PositionDao;
import com.ctbu.guesthouse.dao.RoomDao;
import com.ctbu.guesthouse.dao.UserDao;
import com.ctbu.guesthouse.domain.Position;
import com.ctbu.guesthouse.domain.Room;
import com.ctbu.guesthouse.domain.User;
import com.ctbu.guesthouse.service.PositionService;
import com.ctbu.guesthouse.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    RoomService roomService;
    @Autowired
    PositionService positionService;
    @Autowired
    UserDao userDao;
    @Autowired
    RoomDao roomDao;
    @Autowired
    PositionDao positionDao;

    @GetMapping("/login")
    public String loginGet() {
        System.out.println("get");
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails);
        return "redirect:home";
    }

    @RequestMapping("/reg")
    public String reg(User user) {
        System.out.println(user);
        String password = user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        user.setPassword(encode);

        userDao.save(user);
        /*User userDetails = (User) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        System.out.println(userDetails);*/
        return "redirect:home";
    }

    @RequestMapping("/home")
    public String home(@RequestParam(name = "pos", required = false) Integer posId,
                       ModelMap modelMap) {
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

    @RequestMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }


    @RequestMapping("/search")
    public String searchRoom(@RequestParam(name = "condition", required = false) Integer condition,
                             ModelMap modelMap) {
        List<Room> rooms = roomDao.findByRoomCodeLike("%" + condition + "%");
        List<Position> positions = positionDao.findAll();
        modelMap.addAttribute("rooms", rooms);
        modelMap.addAttribute("positions", positions);
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }


}
