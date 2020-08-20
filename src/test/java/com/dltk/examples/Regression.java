package com.dltk.examples;

import com.dltk.Dataset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dltk.DltkAiClient;

import java.io.IOException;

public class Regression {
    public static void main(String[] args) {

        DltkAiClient c = new DltkAiClient("xxx");
        String trainData = "";
        String testData = "";
        JSONObject response = null;
        Integer jobId = 0;

        try {

            response = c.store("csv/market_train.csv", Dataset.TRAIN_DATA);
            trainData = response.get("fileUrl").toString();
            System.out.println(trainData);

            response = c.store("csv/market_test.csv", Dataset.TEST_DATA);
            testData = response.get("fileUrl").toString();
            System.out.println(testData);


            JSONArray features = new JSONArray();
            features.put("R&D Spend");
            features.put("Administration");
            features.put("Marketing Spend");
            JSONObject params = new JSONObject();
            params.put("lib", "weka");
            params.put("saveModel", true);
            params.put("trainPercentage", 80);
            params.put("modelName", "Market Model");
            response = c.train("regression", "LinearRegression", trainData, "Profit", features, params);
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());


            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String modelUrl = (String) ((JSONObject) response.get("output")).get("modelUrl");
           // JSONArray feature = new JSONArray();
          //  feature.put("R&D Spend");
          //  feature.put("Administration");
          //  feature.put("Marketing Spend");
            params = new JSONObject();
            params.put("lib", "weka");
            response = c.predict("regression", testData, modelUrl, params);
            System.out.println(response.toString());

            jobId = (Integer) ((JSONObject) response.get("data")).get("jobId");

            response = c.jobStatus(jobId);
            System.out.println(response.toString());

            response = c.jobOutput(jobId);
            System.out.println(response.toString());

            String predFileUrl = (String) ((JSONObject) response.get("output")).get("predFileUrl");
            String predictions = c.download(predFileUrl);
            System.out.println(predictions);


        } catch (IOException | JSONException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
