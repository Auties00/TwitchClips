package main;

import values.Game;
import values.Schedule;
import video.TwitchVideo;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TwitchApplication {
    public static void main(String[] args) {
        System.out.println("Enter the time that each video should wait for: ");
        Scanner scan = new Scanner(System.in);
        String type = scan.next();

        Schedule schedule = Objects.requireNonNull(Schedule.valueOf(type), "The video schedule time is invalid");
        System.out.println("Time set!");

        long HOUR = 1000*60*60;
        long time = 0;
        switch (schedule) {
            case NOW:
                time = 1000;
                break;
            case HOUR:
                time = HOUR;
                System.out.println("You selected: HOUR");
                break;
            case DAY:
                time = HOUR * 24;
                System.out.println("You selected: DAY");
                break;
            case MONTH:
                time = HOUR * 24 * 30;
                System.out.println("You selected: MONTH");
               break;
            case YEAR:
                time = HOUR * 24 * 365;
                System.out.println("You selected: YEAR");
                break;
        }
        System.out.println("Starting runnable...");

        long started = System.currentTimeMillis();
        System.out.println("Video coming in " + TimeUnit.MILLISECONDS.toHours(time) + " hours!");
        Timer timer = new Timer();
        registerTask(timer, started, time);

        System.out.println("Runnable successfully started!");
    }

    private static void registerTask(Timer timer, long started, long time) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() == started + time || (started + time) - System.currentTimeMillis() < 0 ) {
                    Arrays.stream(Game.values()).forEach(TwitchVideo::new);
                    timer.cancel();
                } else {
                    System.out.println("The video will be published in " + ((started + time) - System.currentTimeMillis()) + " milliseconds!");
                    registerTask(timer, started, time);
                }
            }
        }, 0, 1);
    }

    public static String getMainDir(){
        try {
            return new File(TwitchApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath().replace("TwitchClip.jar", "");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
