package com.ctbu.guesthouse.security;

import com.ctbu.guesthouse.dao.UserDao;
import com.ctbu.guesthouse.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.findByUsername(s);
        if(!Objects.isNull(user)) {
            return user;
        } else {
            throw new UsernameNotFoundException("admin " + s + " not found");
        }
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("admin");
        System.out.println(encode);
        //结果：$2a$10$2UDBWkpUnplJJOWHFCkjweEBR06K7RJYwLaB/6yRwPQZALyMPkWDy
    }
}
