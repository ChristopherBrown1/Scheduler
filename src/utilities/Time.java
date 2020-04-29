/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Array;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

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
      
      public static LocalDateTime stringToLocalDateTime(String time){
         DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
         Date utcTime = null;
           try {
               utcTime = utcFormat.parse(time);
           } catch (ParseException ex) {
               Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);
           }
         DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         localFormat.setTimeZone(TimeZone.getDefault());
         String lString = localFormat.format(utcTime);
         LocalDateTime l = LocalDateTime.parse(lString, f);
         
         return l;
         
      }
           
      
      public static String currentUTCDate(){
          
//        //ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
//        //ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("America")).forEach(System.out::println);
//        LocalDate parisDate = LocalDate.of(2020, 4, 8);
//        LocalTime parisTime = LocalTime.of(01, 00); // military time
//        ZoneId parisZone = ZoneId.of("Europe/Paris");
//        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZone);
//        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//        
//        Instant parisToGMTInstant = parisZDT.toInstant(); // gives us paris time converted to UTC
//        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId); // converts paris time to our local time
//        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId); // converts UTC to our local time
//        
////        System.out.println("Local: " + ZonedDateTime.now()); // My locations time
////        System.out.println("Paris: " + parisZDT); // Time in Paris
////        System.out.println("Paris to GMT: " + parisToGMTInstant);
////        System.out.println("GMT to Local: " + gmtToLocalZDT);
////        System.out.println("GMT to Local Date: " + gmtToLocalZDT.toLocalDate());
////        System.out.println("GMT to Local Time: " + gmtToLocalZDT.toLocalTime());
//        
//        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
//        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
//        String dateTime = date + " " + time; // date Time in format ready to be inserted into SQL Database
////        System.out.println(dateTime);


        LocalDateTime localdt = LocalDateTime.now();
        ZonedDateTime localZdt = ZonedDateTime.of(localdt, ZoneId.systemDefault());
        ZonedDateTime utcZdt = localZdt.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                
        return customFormatter.format(utcZdt);
      }
      
      
    public static String dateToUTCString(LocalDate date, int hour, int minute, String ampm) {
        
        if(ampm == "PM" && hour != 12) {
            hour = hour + 12;
        }
        else if(ampm =="AM" && hour == 12) {
            hour = hour - 12;
        }
        
                                       
        LocalDateTime localdt = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), hour, minute);
        ZonedDateTime locZdt = ZonedDateTime.of(localdt, ZoneId.systemDefault());
        ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        return customFormatter.format(utcZdt);
      }
    

    public static int[] calendarToArray(Calendar date) {
        
        int Year = date.get(Calendar.YEAR);
        int Month = date.get(Calendar.MONTH);
        int Day = date.get(Calendar.DAY_OF_MONTH);
        int Hour = date.get(Calendar.HOUR);
        int Minute = date.get(Calendar.MINUTE);
        int AM_PM = date.get(Calendar.AM_PM);
        
        int[] dates = {Year,Month,Day,Hour,Minute,AM_PM};
        
        return dates;
    }
    
    public static LocalDate arrayToLD(int[] date) {
        
        LocalDate yearMonthDay = LocalDate.of(date[0], date[1],date[2]);
        
        return yearMonthDay;
    }
    
        public static int arrayToHour(int[] date) {       
        int hour = date[3];       
        return hour;
    }

        public static int arrayToMin(int[] date) {       
        int min = date[4];       
        return min;
    }        

        public static String arrayToAMPM(int[] date) {       
        int ampm = date[5];
        
        String AMPM = "";
        if (ampm == 0) {
            AMPM = "AM";
        }
        else {
            AMPM = "PM";
        }
        return AMPM;
    }

    public static Date calendarToString(Calendar date) {
        int Year = date.get(Calendar.YEAR);
        int Month = date.get(Calendar.MONTH);
        int Day = date.get(Calendar.DAY_OF_MONTH);
        int Hour = date.get(Calendar.HOUR);
        int Minute = date.get(Calendar.MINUTE);
        int ampm = date.get(Calendar.AM_PM);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        
        
        String AMPM = "";
        if(ampm == 0){
            AMPM = "AM";
        }
        else{
            AMPM = "PM";
        }
        
//        String dateString = Day+"-"+Month+"-"+Year+" "+Hour+":"+Minute+AMPM;
        
        Date sdfDate = new Date();
        String dateString = Day+"-"+Month+"-"+Year+" "+Hour+":"+Minute;
           try {
               sdfDate = sdf.parse(dateString);
           } catch (ParseException ex) {
               Logger.getLogger(Time.class.getName()).log(Level.SEVERE, null, ex);
           }
        
        return sdfDate;        
        
    }
    
// These need to be converted back to UTC first....... FIX ME    
    public static LocalDateTime getNextWeek(){
        
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current date: " + today);

        //add 1 week to the current date
        LocalDateTime nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Next week: " + nextWeek);
        return nextWeek;
    }
    
 
    public static LocalDateTime getNextMonth(){       
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current date: " + today);

        //add 1 week to the current date
        LocalDateTime nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("Next month: " + nextMonth);
        return nextMonth;
    }    

        
}
