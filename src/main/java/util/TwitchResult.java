package util;

import video.TwitchClip;

public class TwitchResult {
    private TwitchClip[] data;
    private Pagination pagination;
    public TwitchResult(TwitchClip[] data, Pagination pagination){
        this.data = data;
        this.pagination = pagination;
    }

    public TwitchClip[] getClips() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
