package com.ctc.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestController {

    public static void main(String[] args){
        String startTime = "2018-09-04";
        String endTime = "2018-10-04";
        List<String> timeList = getBetweenDate(startTime,endTime);
        for (int i = 0; i < timeList.size(); i++) {
            System.out.println(timeList.get(i));
        }

    }

    public static List<String> getBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                boolean boo = isWeekend(startDate);
                if(!boo){
                    list.add(sdf.format(startDate));
                }
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isWeekend(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            return true;
        }
        else return false;
    }
}
