import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Writer {



    public Writer() {

    }
    public void writeDataset(Object obj, String outputName, boolean consolePrint, boolean append){


        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson(obj);
        StringBuilder sb = new StringBuilder(json);

        if (consolePrint){
            System.out.println(sb);
        }

        try (FileWriter file = new FileWriter(outputName, append)) {
            file.write(sb.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}







