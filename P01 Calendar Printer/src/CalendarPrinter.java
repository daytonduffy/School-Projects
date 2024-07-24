import java.util.Arrays;
import java.util.Scanner;

public class CalendarPrinter {
    private final static String[] DAYS_OF_WEEK = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private final static String[] MONTHS_OF_YEAR =
        {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    /**
    * Calculates the number of centuries (rounded down) that is represented by
    * the specified year (ie. the integer part of year/100).
    * @param year to compute the century of (based on the Gregorian Calendar AD)
    * String must contain the digits of a single non-negative int for year.
    * @return number of centuries in the specified year
    */
    public static int getCentury(String year) {
        Integer century = Integer.parseInt(year);
        century = century / 100;

        return century;
    }

    /**
    * Calculates the number of years between the specified year, and the first
    * year in the specified year's century. This number is always between 0 - 99.
    * @param year to compute the year within century of (Gregorian Calendar AD)
    * String must contain the digits of a single non-negative int for year.
    * @return number of years since first year in the current century
    */
    public static int getYearWithinCentury(String year) {
        Integer yearWithin = Integer.parseInt(year);
        int century = getCentury(year);
        century = century * 100; // gives full century value

        yearWithin = yearWithin - century;// takes the full year and removes the century

        return yearWithin;
    }

    /**
    * This method computes whether the specified year is a leap year or not.
    * @param year is the year that is being checked for leap-year-ness
    * String must contain the digits of a single non-negative int for year.
    * @return true when the specified year is a leap year, and false otherwise
    */

    public static boolean getIsLeapYear(String year) {
        // Note implementation tips in Appendix I below.
        boolean leap = false;
        Integer yearNum = Integer.parseInt(year);

        if (yearNum % 4 != 0) {
            leap = false;
        } else if (yearNum % 100 != 0) {
            leap = true;
        } else if (yearNum % 400 != 0) {
            leap = false;
        } else {
            leap = true;
        }

        return leap;
    }

    /**
    * Converts the name or abbreviation for any month into the index of that
    * month's abbreviation within MONTHS_OF_YEAR. Matches the specified month
    * based only on the first three characters, and is case in-sensitive.
    * @param month which may or may not be abbreviated to 3 or more characters
    * @return the index within MONTHS_OF_YEAR that a match is found at
    * and returns -1, when no match is found
    */

    public static int getMonthIndex(String month) {
        int monthNum = -1;
        String monthName = month.substring(0, 3);

        for (int i = 0; i <= 11; ++i) {
            if (monthName.equalsIgnoreCase(MONTHS_OF_YEAR[i])) {
                monthNum = i;
            }
        }

        return monthNum;
    }

    /**
    * Calculates the number of days in the specified month, while taking into
    * consideration whether or not the specified year is a leap year.
    * @param month which may or may not be abbreviated to 3 or more characters
    * @param year of month that days are being counted for (Gregorian Calendar AD)
    * String must contain the digits of a single non-negative int for year.
    * @return the number of days in the specified month (between 28-31)
    */

    public static int getNumberOfDaysInMonth(String month, String year) {
        int days = 0;
        int monthNum = getMonthIndex(month);

        if (monthNum == 0) {
            days = 31;
        } else if (monthNum == 1) {
            if (getIsLeapYear(year) == true) {
                days = 29;
            } else {
                days = 28;
            }
        } else if (monthNum == 2) {
            days = 31;
        } else if (monthNum == 3) {
            days = 30;
        } else if (monthNum == 4) {
            days = 31;
        } else if (monthNum == 5) {
            days = 30;
        } else if (monthNum == 6) {
            days = 31;
        } else if (monthNum == 7) {
            days = 31;
        } else if (monthNum == 8) {
            days = 30;
        } else if (monthNum == 9) {
            days = 31;
        } else if (monthNum == 10) {
            days = 30;
        } else if (monthNum == 11) {
            days = 31;
        }

        return days;
    }

    /**
    * Calculates the index of the first day of the week in a specified month.
    * The index returned corresponds to position of this first day of the week
    * within the DAYS_OF_WEEK class field.
    * @param month which may or may not be abbreviated to 3 or more characters
    * @param year of month to determine the first day from (Gregorian Calendar AD)
    * String must contain the digits of a single non-negative int for year.
    * @return index within DAYS_OF_WEEK of specified month's first day
    */

    public static int getFirstDayOfWeekInMonth(String month, String year) {
        // Note implementation tips in Appendix I below.
        int q = 1;
        int m = getMonthIndex(month) + 1;// add one for the algorithm that counts months like they
                                         // are in the year
        int k = getYearWithinCentury(year);
        int j = getCentury(year);
        int firstDay = -1;


        if (m == 1) { // if jan or feb change the month number and decrease the year to work with
                      // the formula
            m = 13;
            k = k - 1;
        } else if (m == 2) {
            m = 14;
            k = k - 1;
        }

        int partOne = 13 * (m + 1) / 5; // helps me see whats happening

        firstDay = (q + partOne + k + (k / 4) + (j / 4) + (5 * j)) % 7;

        firstDay = firstDay - 2;// make it work with our index
        if (firstDay == -2) {
            firstDay = 5;
        } else if (firstDay == -1) {
            firstDay = 6;
        }


        return firstDay;
    }

    /**
    * Creates and initializes a 2D String array to reflect the specified month.
    * The first row of this array [0] should contain labels representing the days
    * of the week, starting with Monday, as abbreviated in DAYS_OF_WEEK. Every
    * later row should contain dates under the corresponding days of week.
    * Entries with no corresponding date in the current month should be filled
    * with a single period. There should not be any extra rows that are either
    * blank, unused, or completely filled with periods.
    * For example, the contents for September of 2019 should look as follows,
    * where each horizontal row is stored in different array within the 2d result:
    *
    * MON TUE WED THU FRI SAT SUN
    * . . . . . . 1
    * 2 3 4 5 6 7 8
    * 9 10 11 12 13 14 15
    * 16 17 18 19 20 21 22
    * 23 24 25 26 27 28 29
    * 30 . . . . . .
    *
    * @param month which may or may not be abbreviated to 3 or more characters
    * @param year of month generate calendar for (Gregorian Calendar AD)
    * String must contain the digits of a single non-negative int for year.
    * @return 2d array of strings depicting the contents of a calendar
    */
    public static String[][] generateCalendar(String month, String year){
        int numberDays = getNumberOfDaysInMonth(month, year);
        int firstDay = getFirstDayOfWeekInMonth(month, year);
        int firstDots = 0;
        int numDays = numberDays;
        int dayCount = 1;
        
        int numWeeks = 0;
        if(firstDay > 0) {    
            
            if(firstDay == 1) {// take away days from the first week
                numDays = numDays - 6;
                firstDots = 1;
            }else if(firstDay == 2) {
                numDays = numDays - 5;
                firstDots = 2;
            }else if(firstDay == 3) {
                numDays = numDays - 4;
                firstDots = 3;
            }else if(firstDay == 4) {
                numDays = numDays - 3;
                firstDots = 4;
            }else if(firstDay == 5) {
                numDays = numDays - 2;
                firstDots = 5;
            }else if(firstDay == 6) {
                numDays = numDays - 1;
                firstDots = 6;
            }
            
            if(numDays%7 == 0) {
                numWeeks = numDays/7 + 1;//one for the week I removed  
            }else {
                numWeeks = numDays/7 + 2;//one more for if there is extra days 
            }
        }else {//month starts on a monday
            numWeeks = numDays/7 + 2;// one for rounding's sake and one for the days
        }
        
        
        String[][] wholeMonth = new String[numWeeks + 1][7];
 
        for(int i = 0; i < 7; ++i ) {
            wholeMonth[0][i] = DAYS_OF_WEEK[i];
        }
        
        for(int j = 1; j < wholeMonth.length; ++j) {
            if(j == 1) {
                int l = 0;
                while(l < firstDots) {
                    wholeMonth[j][l] = ".";
                    l = l + 1;
                }
                while(l < 7) {
                    wholeMonth[j][l] = Integer.toString(dayCount);
                    l = l + 1;
                    dayCount = dayCount + 1;
                }
            }else if(j == wholeMonth.length - 1) {
                int n = 0;
                while(n < 7) {
                    wholeMonth[j][n] = Integer.toString(dayCount);
                    n = n + 1;
                    if(dayCount == numberDays) {
                        break;
                    }
                    dayCount = dayCount + 1;
                }
                while(n < 7) {
                    wholeMonth[j][n] = ".";
                    n = n + 1;
                } 
            }else {
                for(int m = 0; m < 7; ++m){
                    wholeMonth[j][m] = Integer.toString(dayCount);
                    if(dayCount == numberDays) {
                        break;
                    }
                    dayCount = dayCount + 1; 
                }
            }
            
        }

        return wholeMonth;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String userMonth;
        int monthDex = -1;
        String userYear;
        boolean yearVal = false;
        
        
        System.out.println("Welcome to the Calendar Printer.");
        System.out.println("================================");
        
        do {
            System.out.println("Enter the month to print: ");
            userMonth = scnr.nextLine();
            monthDex = getMonthIndex(userMonth);
        }while(monthDex == -1);
        
        do {
            System.out.println("Enter the year to print: ");
            yearVal = scnr.hasNextInt();
        }while(!yearVal);
        userYear = scnr.nextLine();
        
        String[][] wholeMonth = generateCalendar(userMonth, userYear);
        
        for(int i = 0; i < wholeMonth.length; ++i) {
            for(int j = 0; j < wholeMonth[i].length; ++j) {
                System.out.print(wholeMonth[i][j]);
                System.out.print(" ");
                if(i > 0) {
                    System.out.print("  ");
                }
            }
            System.out.println("");
        }
        System.out.println("================================");
        System.out.println("Thanks, and have a nice day.");
        
      }
}
