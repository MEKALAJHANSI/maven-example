package com.dltk.examples;

import com.dltk.DltkAiClient;


import java.io.IOException;

public class VisionExamples {

    public static void main(String[] args) {

       DltkAiClient c = new DltkAiClient("xxx");
       String response = null;
        try {

          response = c.faceDetectionImage("img/fd-actual-img.jpg");
          System.out.println(response);
          //response = c.faceDetectionJson("img/fd-actual-img.jpg");
          //System.out.println(response.toString());
          //response = c.licensePlateDetectionImage("img/lp-actual-img.jpg");
          //System.out.println(response);
          //response = c.licensePlateDetectionJson("img/lp-actual-img.jpg");
          //System.out.println(response.toString());
          //response = c.eyeDetectionImage("img/fd-actual-img.jpg");
          //System.out.println(response);
          //response = c.eyeDetectionJson("img/fd-actual-img.jpg");
          //System.out.println(response.toString());
          //response = c.smileDetectionImage("img/fd-actual-img.jpg");
          //System.out.println(response);
          //response = c.smileDetectionJson("img/fd-actual-img.jpg");
          //System.out.println(response.toString());    
          //response = c.objectDetectionImage("img/obj_actual.jpg");
         // System.out.println(response);
        // response = c.objectDetectionJson("img/obj_actual.jpg");
        //System.out.println(response.toString());

       } catch (IOException e) {
            e.printStackTrace();
       }

    }

}
