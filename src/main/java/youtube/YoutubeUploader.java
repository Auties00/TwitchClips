package youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YoutubeUploader {
    public void uploadVideoFromFile(File input){
        VideoE newEntry = new VideoEntry();

        YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
        mg.setTitle(new MediaTitle());
        mg.getTitle().setPlainTextContent("Title goes here");
        mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
        mg.setKeywords(new MediaKeywords());
        mg.getKeywords().addKeyword("keyword-here");
        mg.setDescription(new MediaDescription());
        mg.getDescription().setPlainTextContent("My description");
        mg.setPrivate(false);
        mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
        mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));

        newEntry.setGeoCoordinates(new GeoRssWhere(37.0,-122.0));
// alternatively, one could specify just a descriptive string
// newEntry.setLocation("Mountain View, CA");

        MediaFileSource ms = new MediaFileSource(new File("file.mov"), "video/quicktime");
        newEntry.setMediaSource(ms);

        String uploadUrl =
                "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";

        VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
    }
    public static void uploadVideo(File input) {
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        try {
            Credential credential = Auth.authorize(scopes, "uploadvideo");

            YouTube youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName("twitchchannel").build();
            System.out.println("Uploading: " + input.getName());

            // Add extra information to the video before uploading.
            Video videoObjectDefiningMetadata = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");
            videoObjectDefiningMetadata.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();
            
            Calendar cal = Calendar.getInstance();
            snippet.setTitle("*TODAY* Fortnite Highlights - " + cal.getTime().getDay() + "/" + cal.getTime().getMonth() + "/" + cal.getTime().getYear());
            snippet.setDescription(
                    "Welcome to my channel! \n" + 
                    "Here I'll upload daily twitch highlights video for a total of 3 videos a day! \n" +
                    "It takes so much time and effort to find the right clips for you guys, but I'm happy to do this! \n" +
                    "Please consider to drop a like on the video and to subscribe to the channel, thank you!");
            
            // Set the keyword tags that you want to associate with the video.
            List<String> tags = new ArrayList<String>();
            tags.add("Fortnite");
            tags.add("Fortnite Highlights");
            tags.add("Fortnite Clips");
            tags.add("Fortnite Funny");
            tags.add("Fortnite OMG");
            tags.add("Fortnite Trickshots");
            tags.add("Fortnite Rare");
            tags.add("Twitch");
            tags.add("Youtube");
            tags.add("Ninja");
            tags.add("Tfue");
            tags.add("MrSavageM");
            tags.add("Mongraal");
            tags.add("TimTheTatMan");
            tags.add("DrLupo");
            tags.add("TSM Myth");
            tags.add("TSM Daequan");
            tags.add("Dark");
            tags.add("Dakotaz");
            tags.add("NEW");
            tags.add("*TODAY*");
            tags.add("*INSANE*");
            tags.add("*OMG*");
            tags.add("*EXPOSED*");

            snippet.setTags(tags);

            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStreamContent mediaContent = new InputStreamContent("video/*", new FileInputStream(input));

            YouTube.Videos.Insert videoInsert = youtube.videos().insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = uploader1 -> {
                switch (uploader1.getUploadState()) {
                    case INITIATION_STARTED:
                        System.out.println("Initiation Started");
                        break;
                    case INITIATION_COMPLETE:
                        System.out.println("Initiation Completed");
                        break;
                    case MEDIA_IN_PROGRESS:
                        System.out.println("Upload in progress");
                        System.out.println("Upload percentage: " + uploader1.getProgress());
                        break;
                    case MEDIA_COMPLETE:
                        System.out.println("Upload Completed!");
                        break;
                    case NOT_STARTED:
                        System.out.println("Upload Not Started!");
                        break;
                }
            };

            uploader.setProgressListener(progressListener);

            Video returnedVideo = videoInsert.execute();

            System.out.println("\n================== Returned Video ==================\n");
            System.out.println("  - Id: " + returnedVideo.getId());
            System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
            System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
            System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
            System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
