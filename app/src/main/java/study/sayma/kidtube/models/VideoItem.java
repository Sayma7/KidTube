package study.sayma.kidtube.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import study.sayma.kidtube.utils.U;


public class VideoItem implements Serializable {
    private String id, name, thumbUrl;
    private int length;

    public VideoItem(String id, String name, String thumbUrl, int length) {
        this.id = id;
        this.name = name;
        this.thumbUrl = thumbUrl;
        this.length = length;
    }

    public static ArrayList<VideoItem> parseList(JSONArray ja) {
        ArrayList<VideoItem> videoList = new ArrayList<>();
        int l = ja.length();
        for (int i = 0; i < l; i++)
            try {
                videoList.add(parseItem(ja.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return videoList;
    }

    private static VideoItem parseItem(JSONObject jo) {
        return new VideoItem(
                U.getStringJ(jo, "id"),
                U.getStringJ(jo, "name"),
                "https://img.youtube.com/vi/" + U.getStringJ(jo, "id") + "/mqdefault.jpg",
                U.getIntJ(jo, "length")
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", length=" + length +
                '}';
    }
}
