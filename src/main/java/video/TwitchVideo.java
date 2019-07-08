package video;

import com.google.gson.Gson;
import main.TwitchApplication;
import util.TwitchResult;
import values.Game;
import util.FileMerger;
import youtube.YoutubeUploader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;
import java.util.*;


public class TwitchVideo {
    public TwitchVideo(Game game) {
        System.out.println("Starting video production...");

        List<TwitchClip> resultList = getVideoList(game);
        if(resultList == null){
            throw new NullPointerException("Results cannot be null!");
        }

        System.out.println("Fetched " + resultList.size() + " clips!");
        System.out.println("Starting to download them...");
        Set<File> files = new HashSet<>();
        int errors = 0;
        for (TwitchClip video : resultList) {
            System.out.println("\nDownloading " + video.getTitle());
            String startURL = video.getthumbnail_url().replace("https://clips-media-assets2.twitch.tv/", "");
            String finalURL;
            if(startURL.contains("AT-")){
                finalURL = "AT-" + startURL.split("-")[1];
            }else {
                finalURL = startURL.split("-")[0];
            }
            

            File currentFile = null;
            try {
                URL url = new URL("https://clips-media-assets2.twitch.tv/" + finalURL + ".mp4");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                File file = new File(TwitchApplication.getMainDir() + "\\clips\\" + game.name(), UUID.randomUUID() + ".mp4");
                currentFile = file;
                files.add(file);
                writeFile(file, connection.getInputStream());
            }catch (IOException e){
                files.remove(currentFile);
                errors++;
            }
        }

        File source = setupSourceFile(files);
        if(source == null){
            throw new NullPointerException("Sources file cannot be null!");
        }

        System.out.println(files.size() + " videos were downloaded, while " + errors + " couldn't be downloaded!");
        File finalFile = new File(TwitchApplication.getMainDir() + "\\final\\", UUID.randomUUID().toString() + ".mp4");
        FileMerger.mergeFiles(source, finalFile);
        while (!FileMerger.hasDone()){
            //WAIT
        }

        files.forEach(File::delete);
    }

    private void writeFile(File file, InputStream in) throws IOException {
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));

        byte[] buffer = new byte[1024];

        int numRead;
        while ((numRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numRead);
        }

        in.close();
        out.close();
    }

    private List<TwitchClip> getVideoList(Game game) {
        try {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

            String formattedStartTime;
            String formattedEndTime;
            if(String.valueOf(todayMidnight.getMonthValue()).length() != 2) {
                formattedStartTime = todayMidnight.getYear() + "-" + 0 + todayMidnight.getMonthValue() + "-" + (todayMidnight.getDayOfMonth() - 1) + "T00:00:00Z";
                formattedEndTime = todayMidnight.getYear() + "-" + 0 + todayMidnight.getMonthValue() + "-" + todayMidnight.getDayOfMonth() + "T00:00:00Z";
            }else {
                formattedStartTime = todayMidnight.getYear() + "-" + todayMidnight.getMonthValue() + "-" + (todayMidnight.getDayOfMonth() - 1) + "T00:00:00Z";
                formattedEndTime = todayMidnight.getYear() + "-" + todayMidnight.getMonthValue() + "-" + todayMidnight.getDayOfMonth() + "T00:00:00Z";
            }

            URL url = new URL("https://api.twitch.tv/helix/clips?game_id=" + game.getId() + "&first=50&started_at=" + formattedStartTime + "&ended_at=" + formattedEndTime);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Client-ID", "la8b7xymkqmptyyhst16oxh74vop4x");

            File out = File.createTempFile(UUID.randomUUID().toString(), ".json");
            writeFile(out, connection.getInputStream());
            System.out.println("Downloaded clips data at " + out.getPath());

            Gson gson = new Gson();
            TwitchResult result = gson.fromJson(new FileReader(out), TwitchResult.class);
            System.out.println("Fetched " + result.getPagination().getCursor() +  " and " + result.getClips());
            return Arrays.asList(result.getClips());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private File setupSourceFile(Set<File> files) {
        try {
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            for (File file : files){
                writer.write("file '" + file.getPath() + "'");
                writer.newLine();
            }

            writer.close();
            System.out.println("Created temp source file at " + tempFile.getPath());
            return tempFile;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
