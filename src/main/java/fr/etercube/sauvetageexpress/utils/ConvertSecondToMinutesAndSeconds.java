package fr.etercube.sauvetageexpress.utils;

public class ConvertSecondToMinutesAndSeconds {
    public static String convertSecondsToMinutesAndSeconds(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d minutes %d secondes", minutes, seconds);
    }
}
