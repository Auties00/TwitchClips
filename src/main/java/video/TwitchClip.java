package video;

public class TwitchClip {
    private String id;
    private String url;
    private String embed_url;
    private String broadcaster_id;
    private String broadcaster_name;
    private String creator_id;
    private String creator_name;
    private String video_id;
    private String game_id;
    private String language;
    private String title;
    private Integer view_count;
    private String created_at;
    private String thumbnail_url;

    public TwitchClip(String id, String url, String embed_url, String broadcaster_id, String broadcaster_name, String creator_id, String creator_name, String video_id, String game_id, String language, String title, Integer view_count, String created_at, String thumbnail_url){
        this.id = id;
        this.url = url;
        this.embed_url = embed_url;
        this.broadcaster_id = broadcaster_id;
        this.broadcaster_name = broadcaster_name;
        this.creator_id = creator_id;
        this.video_id = video_id;
        this.creator_name = creator_name;
        this.game_id = game_id;
        this.language = language;
        this.title = title;
        this.view_count = view_count;
        this.created_at = created_at;
        this.thumbnail_url = thumbnail_url;
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getembed_url() {
        return this.embed_url;
    }

    public String getbroadcaster_id() {
        return this.broadcaster_id;
    }

    public String getcreator_id() {
        return this.creator_id;
    }

    public String getvideo_id() {
        return this.video_id;
    }

    public String getgame_id() {
        return this.game_id;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getview_count() {
        return this.view_count;
    }

    public String getcreated_at() {
        return this.created_at;
    }

    public String getthumbnail_url() {
        return this.thumbnail_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setembed_url(String embed_url) {
        this.embed_url = embed_url;
    }

    public void setbroadcaster_id(String broadcaster_id) {
        this.broadcaster_id = broadcaster_id;
    }

    public void setcreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public void setvideo_id(String video_id) {
        this.video_id = video_id;
    }

    public void setgame_id(String game_id) {
        this.game_id = game_id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setview_count(Integer view_count) {
        this.view_count = view_count;
    }

    public void setcreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setthumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getBroadcaster_name() {
        return broadcaster_name;
    }

    public void setBroadcaster_name(String broadcaster_name) {
        this.broadcaster_name = broadcaster_name;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }
}
