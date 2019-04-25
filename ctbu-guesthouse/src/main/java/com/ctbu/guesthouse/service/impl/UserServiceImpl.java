package com.ctbu.guesthouse.service.impl;

import com.ctbu.guesthouse.dao.SysLogDao;
import com.ctbu.guesthouse.domain.SysLog;
import com.ctbu.guesthouse.dto.SysUserDto;
import com.ctbu.guesthouse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysLogDao sysLogDao;


    @Override
    public Object analyticsUser() {
        List<SysLog> sysLogs = sysLogDao.findAll();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        SysUserDto sysUserDto = new SysUserDto(); // 传输数据对象
        Map<Integer,Float> guestMoneyMap = new HashMap<>(); // 每月金额map表

        for (SysLog sysLog : sysLogs) {
            String startTime = sysLog.getStartTime();
            try {
                Date inTime = simpleDateFormat.parse(startTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(inTime);
                int month = calendar.get(Calendar.MONTH) + 1;

                Float guestMoneyTemp = guestMoneyMap.get(month);
                if(Objects.isNull(guestMoneyTemp)){
                    guestMoneyMap.put(month,sysLog.getGuestPrice());
                } else {
                    guestMoneyTemp = guestMoneyTemp + sysLog.getGuestPrice();
                    guestMoneyMap.put(month, guestMoneyTemp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Set<Integer> monthSet = guestMoneyMap.keySet();
        Integer[] monthArr = new Integer[monthSet.size()];
        Iterator<Integer> iterator1 = monthSet.iterator();
        int i = 0;
        while(iterator1.hasNext()) {
            monthArr[i] = iterator1.next();
            i++;
        }

        List<Integer> monthList = Arrays.asList(monthArr);

        List<Float> monthlyMoney = new ArrayList<>();
        Collection<Float> values = guestMoneyMap.values();
        Iterator<Float> iterator = values.iterator();
        while(iterator.hasNext()) {
            monthlyMoney.add(iterator.next());
        }

        sysUserDto.setMonths(monthList);
        sysUserDto.setMonthlUserMoney(monthlyMoney);
        return sysUserDto;
    }

    /*private SysUserDto classifyDataByMonth(int month, SysLog sysLog) {
        switch (month) {
            case 1:

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
        }
    }*/
}
