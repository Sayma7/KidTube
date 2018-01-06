package study.sayma.kidtube.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import study.sayma.kidtube.utils.U;

public class PlayListItem implements Serializable{

    private String id, name, thumbUrl;
    private ArrayList<VideoItem> videoList;

    public PlayListItem(String id, String name, String thumbUrl, ArrayList<VideoItem> videoList) {
        this.id = id;
        this.name = name;
        this.thumbUrl = thumbUrl;
        this.videoList = videoList;
    }

    public static ArrayList<PlayListItem> parseList(JSONArray ja) {
        ArrayList<PlayListItem> plList = new ArrayList<>();
        int l = ja.length();
        for (int i = 0; i < l; i++)
            try {
                plList.add(parseItem(ja.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return plList;
    }

    public static PlayListItem parseItem(JSONObject jo) {
        return new PlayListItem(
                U.getStringJ(jo, "id"),
                U.getStringJ(jo, "name"),
                U.getStringJ(jo, "thumb"),
                VideoItem.parseList(U.getJSONArrayJ(jo, "videos"))
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public ArrayList<VideoItem> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<VideoItem> videoList) {
        this.videoList = videoList;
    }

    @Override
    public String toString() {
        return "PlayListItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", videoList=" + videoList +
                '}';
    }
}
