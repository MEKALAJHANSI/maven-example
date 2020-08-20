package com.dltk.examples;

import com.dltk.Dataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dltk.DltkAiClient;

import java.io.IOException;

public class Clustering {

    public static void main(String[] args) {

        DltkAiClient c = new DltkAiClient("xxx");
        String clusterData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            response = c.store("csv/airoplane_data.csv", Dataset.TRAIN_DATA);
            clusterData = response.get("fileUrl").toString();
            System.out.println(clusterData);


            JSONArray features = new JSONArray();
            features.put("Activity Period");
            features.put("Operating Airline");
            JSONObject params = new JSONObject();
            params.put("lib", "weka");
            params.put("saveModel", true);
            params.put("trainPercentage", 80);
            params.put("modelName", "Airoplane Model");
            response = c.cluster("clustering", "KMeansClustering", clusterData, features, params);
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String predFileUrl = (String) ((JSONObject) response.get("output")).get("clusterFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
