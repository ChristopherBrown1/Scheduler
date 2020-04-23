/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author brown
 */
public class Time {
       public static Calendar stringToCalendar(String stringDate) throws ParseException{
       SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       Date date = simpleDate. parse(stringDate);
       Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
   } 
       
       
// I need to change both of these to the UTC time --------------------------- ??????????????????       
      public static String currentDateTime(){
          Date dateTime = new Date();
          return dateTime.toString();          
      }
           
      
      public static String currentDate(){
          
        //ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);
        LocalDate parisDate = LocalDate.of(2020, 4, 8);
        LocalTime parisTime = LocalTime.of(01, 00); // military time
        ZoneId parisZone = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZone);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        
        Instant parisToGMTInstant = parisZDT.toInstant(); // gives us paris time converted to UTC
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId); // converts paris time to our local time
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId); // converts UTC to our local time
        
//        System.out.println("Local: " + ZonedDateTime.now()); // My locations time
//        System.out.println("Paris: " + parisZDT); // Time in Paris
//        System.out.println("Paris to GMT: " + parisToGMTInstant);
//        System.out.println("GMT to Local: " + gmtToLocalZDT);
//        System.out.println("GMT to Local Date: " + gmtToLocalZDT.toLocalDate());
//        System.out.println("GMT to Local Time: " + gmtToLocalZDT.toLocalTime());
        
        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time; // date Time in format ready to be inserted into SQL Database
//        System.out.println(dateTime);  
        
        return dateTime;
      } 
    
}
