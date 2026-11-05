package com.library.domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatter{
    public static String DateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}