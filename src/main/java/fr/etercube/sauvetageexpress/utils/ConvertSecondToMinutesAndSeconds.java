package fr.etercube.sauvetageexpress.utils;

public class ConvertSecondToMinutesAndSeconds {
    public static String convertSecondsToMinutesAndSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        String minuteString = (minutes <= 1) ? "minute" : "minutes";
        String secondString = (seconds <= 1) ? "seconde" : "secondes";
        return String.format("%d %s %d %s", minutes, minuteString, seconds, secondString);
    }
}
