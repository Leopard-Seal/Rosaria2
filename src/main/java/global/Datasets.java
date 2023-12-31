package global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Datasets {
    public static final List<Data> datasets = new ArrayList<>();

    public static void add(Data data){
        datasets.add(data);
    }

    public static void saveJson(String filename){
        JSONArray jsonArray = new JSONArray();
        for (Data data : datasets) {
            jsonArray.put(new JSONObject()
                    .put("title", data.title)
                    .put("tag" , new JSONArray(data.tag))
                    .put("Q&A", new JSONObject(data.qAndA))
                    .put("url", data.url));
        }

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonArray.toString(4)); // インデントを4に設定
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void refresh(){
        datasets.clear();
    }
}
