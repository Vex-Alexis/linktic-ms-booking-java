package co.com.linktic.api.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Dateutil {
    // Formato estándar para las fechas
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // Convierte LocalDateTime a String con formato estándar
    public static String formatDate(LocalDateTime dateTime) {
        return Optional.ofNullable(dateTime)
                .map(DEFAULT_FORMATTER::format)
                .orElse(null); // Maneja fechas nulas
    }

    // Convierte un String con formato estándar a LocalDateTime
    public static LocalDateTime parseDate(String date) {
        return Optional.ofNullable(date)
                .map(d -> LocalDateTime.parse(d, DEFAULT_FORMATTER))
                .orElse(null); // Maneja cadenas nulas
    }

    // Metodo para agregar mas formatos
    public static String formatCustom(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(pattern);
        return Optional.ofNullable(dateTime)
                .map(customFormatter::format)
                .orElse(null);
    }

}
