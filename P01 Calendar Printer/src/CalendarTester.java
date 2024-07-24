import java.util.Arrays;

public class CalendarTester {
    
    public static boolean testGetCentury() {
        boolean test = true;
        
        if(CalendarPrinter.getCentury("2") != 0) test = false;
        if(CalendarPrinter.getCentury("2019") != 20) test = false;
        if(CalendarPrinter.getCentury("44444") != 444) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 1");
        }else {
            System.out.println("HERO - 1");
        }
        
        return test;
        
       }
    public static boolean testGetYearWithinCentury() {
        boolean test = true;
        
        if(CalendarPrinter.getYearWithinCentury("2") != 2) test = false;
        if(CalendarPrinter.getYearWithinCentury("2019") != 19) test = false;
        if(CalendarPrinter.getYearWithinCentury("44444") != 44) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 2");
        }else {
            System.out.println("HERO - 2");
        }
        
        return test;
        
       }
    public static boolean testGetIsLeapYear() {
        boolean test = true;
        
        if(CalendarPrinter.getIsLeapYear("2") != false) test = false;
        if(CalendarPrinter.getIsLeapYear("2000") != true) test = false;
        if(CalendarPrinter.getIsLeapYear("1900") != false) test = false;
        if(CalendarPrinter.getIsLeapYear("2156") != true) test = false;
        
        
        if(test == false) {
            System.out.println("FAILURE - 3");
        }else {
            System.out.println("HERO - 3");
        }
        
        return test;
        
       }
    public static boolean testGetMonthIndex() {
        boolean test = true;
        
        if(CalendarPrinter.getMonthIndex("feb") != 1) test = false;
        if(CalendarPrinter.getMonthIndex("FeBuRatry") != 1) test = false;
        if(CalendarPrinter.getMonthIndex("Jan") != 0) test = false;
        if(CalendarPrinter.getMonthIndex("Oops") != -1) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 4");
        }else {
            System.out.println("HERO - 4");
        }
        
        return test;
        
       }
    public static boolean testGetNumberOfDaysInMonth() {
        boolean test = true;
        
        if(CalendarPrinter.getNumberOfDaysInMonth("feb", "1900") != 28) test = false;
        if(CalendarPrinter.getNumberOfDaysInMonth("FeBuRatry", "2000") != 29) test = false;
        if(CalendarPrinter.getNumberOfDaysInMonth("Jan", "2000") != 31) test = false;
        if(CalendarPrinter.getNumberOfDaysInMonth("noveMber", "2000") != 30) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 5");
        }else {
            System.out.println("HERO - 5");
        }
        
        return test;
        
       }
    public static boolean testGetFirstDayOfWeekInMonth() {
        boolean test = true;
        
        if(CalendarPrinter.getFirstDayOfWeekInMonth("feb", "2019") != 4) test = false;
        if(CalendarPrinter.getFirstDayOfWeekInMonth("september", "2019") != 6) test = false;
        if(CalendarPrinter.getFirstDayOfWeekInMonth("Jan", "2019") != 1) test = false;
        if(CalendarPrinter.getFirstDayOfWeekInMonth("noveMber", "2019") != 4) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 6");
        }else {
            System.out.println("HERO - 6");
        }
        
        return test;
        
       }
    public static boolean testGenerateCalendar() {
        boolean test = true;

        String[][] wholeMonth = CalendarPrinter.generateCalendar("sep", "2019");
        String[][] wholeMonth2 = CalendarPrinter.generateCalendar("feb", "2020");
        
        if(!wholeMonth[1][1].equals(".")) test = false;
        if(!wholeMonth[1][6].equals("1")) test = false;
        if(!wholeMonth[6][1].equals(".")) test = false;
        if(!wholeMonth2[0][3].equals("THU")) test = false;
        if(!wholeMonth2[5][0].equals("24")) test = false;
        if(!wholeMonth2[5][6].equals(".")) test = false;
        
        if(test == false) {
            System.out.println("FAILURE - 7");
        }else {
            System.out.println("HERO - 7");
        }
        
        return test;
        
       }
    public static void main(String[] args) {
        testGetCentury();
        testGetYearWithinCentury();
        testGetIsLeapYear();
        testGetMonthIndex();
        testGetNumberOfDaysInMonth();
        testGetFirstDayOfWeekInMonth();
        testGenerateCalendar();
    }
}
