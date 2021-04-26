package com.sample.fit13;

//Class to check format on workout object inputs
public class WorkoutHandler {

    //Makes sure title is less that 18 characters
    public static Boolean checkTitle(String title){
        if(title.length()>18){
            return false;
        }
        return true;
    }

    //Makes sure duration is in correct format hour hour: minute minute (HH:MM)
    public static boolean checkDuration(String d){
        if(d.length()<5)
            return false;
        if (d.charAt(2) == ':'){
            if(Character.isDigit(d.charAt(0)) && Character.isDigit(d.charAt(1))&& Character.isDigit(d.charAt(3))
                    && Character.isDigit(d.charAt(4))){
                return true;
            }
        }
        return false;
    }

    //Makes sure date is in correct format MM/DD/YYYY
    public static boolean checkDate(String d){
        if(d.length()<10)
            return false;
        if (d.charAt(2) == '/' && d.charAt(5) == '/'){
            if(Character.isDigit(d.charAt(0)) && Character.isDigit(d.charAt(1))&& Character.isDigit(d.charAt(3))
                    && Character.isDigit(d.charAt(4))&& Character.isDigit(d.charAt(6))&& Character.isDigit(d.charAt(7))
                    && Character.isDigit(d.charAt(8))&& Character.isDigit(d.charAt(9))){
                return true;
            }
        }
        return false;
    }

    //Makes sure description isn't more than 500 characters
    public static boolean checkDescription(String d){
        if(d.length()>500){
            return false;
        }
        return true;
    }
}


