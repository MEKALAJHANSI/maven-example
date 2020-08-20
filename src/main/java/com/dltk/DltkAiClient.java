package com.dltk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DltkAiClient {
	private static final String USER_AGENT = "Mozilla/5.0";
	String baseUrl = null;
	String apiKey = null;

	/**
	 * This is DltkAi Java SDK Client for v1.0.0. 
	 *
	 *
	 * @param apiKey API Key Generated for an app in Dltk.
	 */

	public DltkAiClient(String apiKey) {

		this.apiKey = apiKey;
		this.baseUrl = "https://prod-kong.dltk.ai";
	}

	/**
	 * The function to call sentiment analysis service in Dltk Language. 
	 *
	 * @param text The text on which sentiment analysis is to be applied.
	 * @return A JSONObjectect containing sentiment analysis response.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject sentimentAnalysis(String text) throws IOException, JSONException {

		String url = baseUrl + "/core/nlp/sentiment/";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new JSONObject(sendPost(url, headers, body));

	}

	/**
	 * The function to call pos tagger service in Dltk Language. 
	 *
	 * @param text The text on which POS analysis is to be applied.
	 * @return A JSONObject containing POS tagger response.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject posTagger(String text) throws IOException, JSONException {
		String url = baseUrl + "/core/nlp/pos/";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new JSONObject(sendPost(url, headers, body));
	}

	/**
	 * The function to call ner tagger service in Dltk Language. 
	 *
	 * @param text The text on which ner tagger is to be applied.
	 * @return A JSONObject containing ner tagger response.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject nerTagger(String text) throws IOException, JSONException {
		String url = baseUrl + "/core/nlp/ner/";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new JSONObject(sendPost(url, headers, body));
	}

	/**
	 * The function to call dependency-parser service in Dltk Language. 
	 *
	 * @param text The text on which dependency-parser is to be applied.
	 * @return A JSONObject containing dependency-parser response.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject dependencyParser(String text) throws IOException, JSONException {
		String url = baseUrl + "/core/nlp/dependency-parser/";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new JSONObject(sendPost(url, headers, body));
	}

	/**
	 * The function to call tags service in Dltk Language. 
	 *
	 * @param text The text on which tags is to be applied.
	 * @return A JSONObject containing tags response.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject tags(String text) throws IOException, JSONException {
		String url = baseUrl + "/core/nlp/tags/";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new JSONObject(sendPost(url, headers, body));
	}

	/**
	 * The function call to train service in Dltk machinelearning. 
	 *
	 * @param service    Valid parameter values are classification, regression.
	 * @param algorithm  algorithm by which model will be trained.
	 * @param datasetUrl dataset file location in dltk storage.
	 * @param label      label of the column in dataset file.
	 * @param features   column name list which is used to train classification
	 *                   model.
	 * @param params     JSONObjectect containing additional parameters like lib,
	 *                   modelName, saveModel, trainPercentage. each of the
	 *                   parameter have default values as mentioned below.
	 *                   <ul>
	 *                   <li>lib: valid values for this params are weka, dltk,
	 *                   H2O, scikit (default: weka)</li>
	 *                   <li>modelName: name by which model will be saved. (default:
	 *                   Algorithm Name will be used.)</li>
	 *                   <li>saveModel: boolean param indicating saving the model in
	 *                   dltk storage. (default: true)</li>
	 *                   <li>trainPercentage: % dataset splitting for training and
	 *                   testing. (default: 80%)</li>
	 *                   </ul>
	 *
	 * @return JSONObjectect containing the training job details.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject train(String service, String algorithm, String datasetUrl, String label, JSONArray features,
			JSONObject params) throws IOException, JSONException {

		if (!params.has("modelName"))
			params.put("modelName", algorithm);
		if (!params.has("trainPercentage"))
			params.put("trainPercentage", 80);
		if (!params.has("saveModel"))
			params.put("saveModel", true);
		if (!params.has("lib"))
			params.put("lib", "weka");

		String url = baseUrl + "/machine/" + service + "/train";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("library", params.remove("lib"));
		body.put("task", "train");
		JSONObject config = new JSONObject();
		config.put("name", params.remove("modelName"));
		config.put("algorithm", algorithm);
		config.put("datasetUrl", datasetUrl);
		config.put("label", label);
		config.put("trainPercentage", params.remove("trainPercentage"));
		config.put("features", features);
		config.put("saveModel", params.remove("saveModel"));
		config.put("params", params);
		body.put("config", config);
		return new JSONObject(sendPost(url, headers, body));

	}

	/**
	 * The function call to cluster service in Dltk machinelearning. 
	 *
	 * @param service    Valid parameter values are CLUSTER.
	 * @param algorithm  algorithm by which model will be trained.
	 * @param datasetUrl dataset file location in dltk storage.
	 * @param features   column name list which is used for clustering.
	 * @param params     JSONObjectect containing additional parameters like lib,
	 *                   modelName, saveModel, numOfClusters. each of the parameter
	 *                   have default values as mentioned below.
	 *                   <ul>
	 *                   <li>lib: valid values for this params are weka, dltk,
	 *                   H2O, scikit (default: weka)</li>
	 *                   <li>modelName: name by which model will be saved. (default:
	 *                   Algorithm Name will be used.)</li>
	 *                   <li>saveModel: boolean param indicating saving the model in
	 *                   dltk storage. (default: true)</li>
	 *                   <li>numOfClusters: number of cluster, (default: 2)</li>
	 *                   </ul>
	 *
	 * @return A JSONObject containing model info.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject cluster(String service, String algorithm, String datasetUrl, JSONArray features,
			JSONObject params) throws IOException, JSONException {

		if (!params.has("modelName"))
			params.put("modelName", algorithm);
		if (!params.has("numOfClusters"))
			params.put("numOfClusters", 2);
		if (!params.has("saveModel"))
			params.put("saveModel", true);
		if (!params.has("lib"))
			params.put("lib", "weka");

		String url = baseUrl + "/machine/cluster";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("library", params.remove("lib"));
		body.put("task", "CLUSTER");
		body.put("service", service);
		JSONObject config = new JSONObject();
		config.put("name", params.remove("modelName"));
		config.put("algorithm", algorithm);
		config.put("datasetUrl", datasetUrl);
		config.put("epsilon", 0.1);
		config.put("numOfClusters", params.remove("numOfClusters"));
		config.put("features", features);
		config.put("saveModel", params.remove("saveModel"));
		config.put("params", params);
		body.put("config", config);
		return new JSONObject(sendPost(url, headers, body));

	}

	/**
	 * The function call to feedback service in Dltk machinelearning.
	 *
	 * @param service            Trained model's service.
	 * @param algorithm          Trained model's algorithm.
	 * @param datasetUrl         Trained model's dataset url.
	 * @param feedbackDatasetUrl feedback_data: a)Dataset (used for feedback) file
	 *                           location in dltk storage. b)Feedback dataset
	 *                           upload. IMP: Please ensure the dataset has all
	 *                           features used for training the model.
	 * @param jobId              Job_id from training API response.
	 * @param modelUrl           Model file location in dltk storage.
	 * @param label              Trained model's label.
	 * @param features           Trained model's features.
	 * @param params             JSONObjectect containing additional parameters like
	 *                           lib, modelName, saveModel, trainPercentage. each of
	 *                           the parameter have default values as mentioned
	 *                           below.
	 *                           <ul>
	 *                           <li>lib: valid values for this params are weka,
	 *                           dltk, H2O, scikit (default: weka)</li>
	 *                           <li>modelName: name by which model will be saved.
	 *                           (default: Algorithm Name will be used.)</li>
	 *                           <li>saveModel: boolean param indicating saving the
	 *                           model in dltk storage. (default: true)</li>
	 *                           <li>trainPercentage: % dataset splitting for
	 *                           training and testing. (default: 80%)</li>
	 *                           </ul>
	 * @return A JSONObject containing feedback model info.
	 * @throws IOException
	 * @throws JSONException
	 */

	public JSONObject feedback(String service, String algorithm, String datasetUrl, String feedbackDatasetUrl,
			int jobId, String modelUrl, String label, JSONArray features, JSONObject params)
			throws IOException, JSONException {

		if (!params.has("modelName"))
			params.put("modelName", algorithm);
		if (!params.has("trainPercentage"))
			params.put("trainPercentage", 80);
		if (!params.has("saveModel"))
			params.put("saveModel", true);
		if (!params.has("lib"))
			params.put("lib", "weka");

		String url = baseUrl + "/machine/" + service + "/feedback";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
//        headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("library", params.remove("lib"));
		body.put("task", "FEEDBACK");
		body.put("service", service);
		JSONObject config = new JSONObject();
		config.put("name", params.remove("modelName"));
		config.put("jobId", jobId);
		config.put("algorithm", algorithm);
		config.put("datasetUrl", datasetUrl);
		config.put("feedbackDatasetUrl", feedbackDatasetUrl);
		config.put("modelUrl", modelUrl);
		config.put("label", label);
		config.put("trainPercentage", params.remove("trainPercentage"));
		config.put("features", features);
		config.put("saveModel", params.remove("saveModel"));
		config.put("params", params);
		body.put("config", config);
		return new JSONObject(sendPost(url, headers, body));

	}

	/**
	 *
	 * The function call to predict service in Dltk machinelearning.
	 * @param service    Valid parameter values are classification, regression.
	 * @param datasetUrl dataset file location in dltk storage.
	 * @param modelUrl   trained model location in dltk storage.
	 * @param params     JSONObjectect containing additional parameters like lib,
	 *                   modelName, saveModel, trainPercentage. each of the
	 *                   parameter have default values as mentioned below.
	 *                   <ul>
	 *                   <li>lib: valid values for this params are weka, dltk,
	 *                   H2O, scikit (default: weka)</li>
	 *                   </ul>
	 * @return A JSONObject containing the file info which has the predictions.
	 * @throws IOException
	 */

	public JSONObject predict(String service, String datasetUrl, String modelUrl, JSONArray features, JSONObject params)
			throws IOException {

		if (!params.has("lib"))
			params.put("lib", "weka");

		String url = this.baseUrl + "/machine/" + service + "/predict";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("library", params.remove("lib"));
		JSONObject config = new JSONObject();
		config.put("params", params);
		config.put("features", features);
		config.put("datasetUrl", datasetUrl);
		config.put("modelUrl", modelUrl);
		body.put("config", config);
		return new JSONObject(sendPost(url, headers, body));

	}

	public JSONObject predict(String service, String datasetUrl, String modelUrl, JSONObject params)
			throws IOException {

		if (!params.has("lib"))
			params.put("lib", "weka");

		String url = this.baseUrl + "/machine/" + service + "/predict";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("ApiKey", this.apiKey);
		headers.put("User-Agent", USER_AGENT);
		headers.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("library", params.remove("lib"));
		JSONObject config = new JSONObject();
		config.put("params", params);
		config.put("datasetUrl", datasetUrl);
		config.put("modelUrl", modelUrl);
		body.put("config", config);
		return new JSONObject(sendPost(url, headers, body));

	}	
	
	/**
	 * The function call to store in Dltk machinelearning. 
	 *
	 * @param filePath The path of the dataset file.
	 * @param type The type of dataset (can be TRAIN_DATA or TEST_DATA).
	 * @return A JSONObject containing the file path in storage.
	 * @throws IOException
	 */

	public JSONObject store(String filePath, Dataset type) throws IOException {
		String response = "";
		String requestURL = this.baseUrl + "/s3/file";
		String charset = "UTF-8";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("label", type.getValue());
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return new JSONObject(response);
	}

	/**
	 * The function call to get the job status in Dltk machinelearning. 
	 *
	 * @param jobId jobId from the train api response.
	 * @return A JSONObject containing the status details.
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public JSONObject jobStatus(Integer jobId) throws IOException, InterruptedException {

		String url = this.baseUrl + "/machine/job/status?id=" + jobId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		String res = sendGet(url, headersMap);
		JSONObject resJson = new JSONObject(res);
		String state = (String) resJson.get("state");
		if (state.equals("FAIL")) {
			throw new RuntimeException("Prediction job failed! ");
		}
		while (state.equals("RUN")) {
			res = sendGet(url, headersMap);
			resJson = new JSONObject(res);
			state = (String) resJson.get("state");
			Thread.sleep(5000);
		}
		return new JSONObject(res);

	}

	/**
	 * The function call to get the job output in Dltk machinelearning. 
	 *
	 * @param jobId jobId from the train api response.
	 * @return A JSONObject containing the job output details.
	 * @throws IOException
	 */

	public JSONObject jobOutput(Integer jobId) throws IOException {

		String url = this.baseUrl + "/machine/output/findBy?jobId=" + jobId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		return new JSONObject(sendGet(url, headersMap));

	}

	/**
	 * The function call to download the prediction file in Dltk machinelearning. 
	 *
	 * @param fileUrl location url of file stored in dltk storage.
	 * @return file content in simple text format.
	 * @throws IOException
	 */

	public String download(String fileUrl) throws IOException {

		String url = this.baseUrl + "/s3/file/download?url=" + fileUrl;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		return sendGet(url, headersMap);

	}

	private String sendPost(String urlStr, HashMap<String, String> headersMap, Object bodyJson)
			throws IOException, JSONException {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL url = null;
		url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		for (Map.Entry<String, String> header : headersMap.entrySet()) {
			con.setRequestProperty(header.getKey(), header.getValue());
		}
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		String bodyStr = bodyJson.toString();
		wr.write(bodyStr);
		wr.flush();
		wr.close();
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		Reader streamReader = null;
		int status = con.getResponseCode();
		if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
			streamReader = new InputStreamReader(con.getInputStream());
		} else {
			streamReader = new InputStreamReader(con.getErrorStream());
		}
		BufferedReader in = new BufferedReader(streamReader);
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		return content.toString();
	}

	private String sendGet(String urlStr, HashMap<String, String> headersMap) throws IOException, JSONException {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL url = null;
		url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod("GET");
		for (Map.Entry<String, String> header : headersMap.entrySet()) {
			con.setRequestProperty(header.getKey(), header.getValue());
		}

		con.setConnectTimeout(5000 * 4);
		con.setReadTimeout(5000 * 4);
		Reader streamReader = null;
		int status = con.getResponseCode();
		if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
			streamReader = new InputStreamReader(con.getInputStream());
		} else {
			streamReader = new InputStreamReader(con.getErrorStream());
		}
		BufferedReader in = new BufferedReader(streamReader);
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		return content.toString();
	}

	private String sendGet(String urlStr, HashMap<String, String> headersMap, JSONObject bodyJson)
			throws IOException, JSONException {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL url = null;
		url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod("GET");
		for (Map.Entry<String, String> header : headersMap.entrySet()) {
			con.setRequestProperty(header.getKey(), header.getValue());
		}

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		String bodyStr = bodyJson.toString();
		wr.write(bodyStr);
		wr.flush();
		wr.close();

		con.setConnectTimeout(5000 * 4);
		con.setReadTimeout(5000 * 4);
		Reader streamReader = null;
		int status = con.getResponseCode();
		if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
			streamReader = new InputStreamReader(con.getInputStream());
		} else {
			streamReader = new InputStreamReader(con.getErrorStream());
		}
		BufferedReader in = new BufferedReader(streamReader);
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		return content.toString();
	}

	private String sendPut(String urlStr, HashMap<String, String> headersMap, JSONObject bodyJson)
			throws IOException, JSONException {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		URL url = null;
		url = new URL(null, urlStr, new sun.net.www.protocol.https.Handler());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setDoOutput(true);
		con.setRequestMethod("PUT");
		for (Map.Entry<String, String> header : headersMap.entrySet()) {
			con.setRequestProperty(header.getKey(), header.getValue());
		}
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		String bodyStr = bodyJson.toString();
		wr.write(bodyStr);
		wr.flush();
		wr.close();
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		Reader streamReader = null;
		int status = con.getResponseCode();
		if (status < HttpURLConnection.HTTP_BAD_REQUEST) {
			streamReader = new InputStreamReader(con.getInputStream());
		} else {
			streamReader = new InputStreamReader(con.getErrorStream());
		}
		BufferedReader in = new BufferedReader(streamReader);
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		con.disconnect();
		return content.toString();
	}

	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with face detected.
	 * @throws IOException
	 */

	public String faceDetectionImage(String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/face-detection/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of co-ordinates for all faces detected in the
	 *         image.
	 * @throws IOException
	 */

	public String faceDetectionJson(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/face-detection/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return response;
	}
	
	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with face detected.
	 * @throws IOException
	 */

	public String eyeDetectionImage(String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/eye-detection/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of co-ordinates for all faces detected in the
	 *         image.
	 * @throws IOException
	 */

	public String eyeDetectionJson(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/eye-detection/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return response;
	}
	
	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with face detected.
	 * @throws IOException
	 */

	public String smileDetectionImage(String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/smile-detection/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of co-ordinates for all faces detected in the
	 *         image.
	 * @throws IOException
	 */

	public String smileDetectionJson(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/smile-detection/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return response;
	}	
	
	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with face detected.
	 * @throws IOException
	 */

	public String licensePlateDetectionImage(String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/license-plate/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call face detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of co-ordinates for all faces detected in the
	 *         image.
	 * @throws IOException
	 */

	public String licensePlateDetectionJson(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/license-plate/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return response;
	}	
	
	/**
	 * The function to call object detection service in Dltk computervision.
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with face detected.
	 * @throws IOException
	 */

	public String objectDetectionImage(String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/core/computervision/object-detection/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();

		return response;
	}
	
	/**
	 * The function to call object detection service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of co-ordinates for all faces detected in the
	 *         image.
	 * @throws IOException
	 */

	public JSONObject objectDetectionJson(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/core/computervision/object-detection/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return new JSONObject(response);
	}
	
	/**
	 * The function to call image classification service in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return  A list(JSONArray) of top 3 classes predicted.
	 * @throws IOException
	 */

	public JSONObject imageClassification(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/image-classification";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return new JSONObject(response);
	}
	
	/**
	 * The function to call custom image classification service in Dltk computervision. 
	 *
	 * @param zipFolderUrl The url of the zip image file stored in spotflock storage.
	 * 		Note: Folder containing images should be compressed but not images directly
	 *            folder should also contain a csv file containing image column and labels column
	 * @param imagesColumn Column name of the images in csv file.
	 * @param labelsColumn Column name of the labels in csv file.
	 * @param trainPercentage The ratio into which the dataset is splitted for train and test.
	 * @param epochs The number of iterations should on the whole dataset.
	 * @param stepsPerEpoch The batch(number of images) should take into consideration for every iteration.
	 * @return  A (JSONArray) of the job created for training images.
	 * @throws IOException
	 */	

	public JSONObject CustomImageClassificationTrain(String name, String zipFolderUrl, String imagesColumn,
			String labelsColumn, String trainPercentage, int epochs, int stepsPerEpoch) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-image-classification/train";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("service", "classification");
		body.put("zipFolderUrl", zipFolderUrl);
		body.put("imagesColumn", imagesColumn);
		body.put("labelsColumn", labelsColumn);
		body.put("trainPercentage", trainPercentage);
		body.put("epochs", epochs);
		body.put("stepsPerEpoch", stepsPerEpoch);
		return new JSONObject(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call custom image classification service in Dltk computervision.
	 *
	 * @param zipFolderUrl The url of the zip image file stored in dltk storage.
	 * 		Note: Folder containing images should be compressed but not images directly
	 * @param modelUrl Url of the trained model from custom image classification.
	 * @return  A (JSONArray) containing job info .
	 * @throws IOException
	 */	
	
	public JSONObject CustomImageClassificationPredict(String name, String zipFolderUrl, String modelUrl)
			throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-image-classification/predict";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("service", "classification");
		body.put("zipFolderUrl", zipFolderUrl);
		body.put("modelUrl", modelUrl);
		return new JSONObject(sendPost(requestURL, headersMap, body));
	}

	/**
	 * The function to call custom image classification in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of classes predicted.
	 * @throws IOException
	 */
	
	public String CustomImageClassificationImage(String modelUrl, String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-image-classification/predict/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("image", new File(filePath));
		multipart.addFormField("modelUrl", modelUrl);
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call custom object Detection service in Dltk computercomputervision. 
	 *
	 * @param zipFolderUrl The url of the zip image file stored in spotflock storage.
	 * 		Note: Folder containing images should be compressed but not images directly
	 *            folder should also contain a csv file containing image column and labels column
	 * @param imagesColumn Column name of the images in csv file.
	 * @param labelsColumn Column name of the labels in csv file.
	 * @param trainPercentage The ratio into which the dataset is splitted for train and test.
	 * @param steps The batch(number of images) should take into consideration for every iteration
	 * @return  A (JSONArray) of the job created for training images.
	 * @throws IOException
	 */	
	
	public JSONObject CustomObjectDetectionTrain(String name, String zipFolderUrl, String imagesColumn,
			String labelsColumn, String trainPercentage, int steps) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-object_detection/train";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("service", "obj_detection");
		body.put("zipFolderUrl", zipFolderUrl);
		body.put("imagesColumn", imagesColumn);
		body.put("labelsColumn", labelsColumn);
		body.put("trainPercentage", trainPercentage);
		body.put("steps", steps);
		return new JSONObject(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call custom object Detection service in Dltk computervision.
	 *
	 * @param zipFolderUrl The url of the zip image file stored in spotflock storage.
	 * 		Note: Folder containing images should be compressed but not images directly
	 * @param modelUrl Url of the trained model from custom image classification.
	 * @return  A (JSONArray) containing job info .
	 * @throws IOException
	 */	

	public JSONObject CustomObjectDetectionPredict(String name, String zipFolderUrl, String modelUrl)
			throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-object_detection/predict";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("service", "classification");
		body.put("zipFolderUrl", zipFolderUrl);
		body.put("modelUrl", modelUrl);
		return new JSONObject(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call custom object Detection in Dltk computervision. 
	 *
	 * @param filePath The path of the image file.
	 * @return A base64 decoded image with objects detected.
	 * @throws IOException
	 */
	
	public String CustomObjectDetectionImage(String modelUrl, String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-object_detection/predict/image";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("image", new File(filePath));
		multipart.addFormField("modelUrl", modelUrl);
		response = multipart.finish();

		return response;
	}

	/**
	 * The function to call custom object Detection in Dltk computervision.
	 *
	 * @param filePath The path of the image file.
	 * @return A list(JSONArray) of coordinates of the objects predicted.
	 * @throws IOException
	 */
	
	public String CustomObjectDetectionJson(String modelUrl, String filePath) throws IOException {
		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/computervision/custom-object_detection/predict/json";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("image", new File(filePath));
		multipart.addFormField("modelUrl", modelUrl);
		response = multipart.finish();

		return response;
	}
	
	/**
	 * The function to call status of the job in Dltk computervision. 
	 *
	 * @param jobId The of the job.
	 * @return A JSONObject containing info the job.
	 * @throws IOException
	 */

	public JSONObject computervisionJobStatus(Integer jobId) throws IOException, InterruptedException {

		String url = this.baseUrl + "/computervision/status/" + jobId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		String res = sendGet(url, headersMap);
		JSONObject resJson = new JSONObject(res);
		String state = (String) resJson.get("status");
		if (state.equals("FAIL")) {
			throw new RuntimeException("job failed! ");
		}
		while (state.equals("RUN")) {
			res = sendGet(url, headersMap);
			resJson = new JSONObject(res);
			state = (String) resJson.get("status");
			Thread.sleep(5000);
		}
		return new JSONObject(res);
	}
	
	/**
	 * The function to call textToSpeech in Dltk Conversation. 
	 *
	 * @param text The text which is converted to speech.
	 * @param gender MALE , FEMALE, NEUTRAL
	 * @return audio content of the speech is returned as byte object .
	 * @throws IOException
	 */

	public String textToSpeech(String text, String gender) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/conversation-service/dltk-conversation/text-to-speech/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		body.put("gender", gender);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call SpeechToText in Dltk Conversation. 
	 *
	 * @param filePath The path of the audio file.
	 * @return A JSONObject containing transcript of the audio.
	 * @throws IOException
	 */

	public String SpeechToText(String filePath) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/conversation-service/dltk-conversation/speech-to-text/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("audio", new File(filePath));
		response = multipart.finish();
		return response;
	}
	
	/**
	 * The function to call Translation in Dltk Conversation. 
	 *
	 * @param text The text to be translated into another language.
	 * @param language The language code to which text to be translated.
	 * @return A JSONObject containing translated text.
	 * @throws IOException
	 */

	public String translation(String text, String language) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/conversation-service/dltk-conversation/translation/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		body.put("language", language);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call Emotion Analysis in Dltk Tape. 
	 *
	 * @param fileUrl The url of the video uploaded to dltk storage.
	 * @param name Name of the report
	 * @param description Description of the report
	 * @return JsonObject containing info of the job
	 * @throws IOException
	 */

	public String emotionAnalysis(String fileUrl, String name, String description) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/tape-service/Dltk-tape/emotion-analysis/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("fileUrl", fileUrl);
		body.put("name", name);
		body.put("description", description);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call Emotion Analysis in Dltk Tape. 
	 *
	 * @param jobId The url of the video uploaded to dltk storage.
	 * @return A JSONObject containing averages of emotions.
	 * @throws IOException
	 */

	public JSONObject tapeStatus(Integer jobId) throws IOException, InterruptedException {

		String url = this.baseUrl + "/tape-service/Dltk-tape/job/get/" + jobId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		String res = sendGet(url, headersMap);
		JSONObject resJson = new JSONObject(res);
		String state = (String) resJson.get("status");
		if (state.equals("FAIL")) {
			throw new RuntimeException("job failed! ");
		}
		while (state.equals("RUN")) {
			res = sendGet(url, headersMap);
			resJson = new JSONObject(res);
			state = (String) resJson.get("status");
			Thread.sleep(5000);
		}
		return new JSONObject(res);
	}

	/**
	 * The function to call Sarcasm Analysis in Dltk Content Inspector. 
	 *
	 * @param text The text on which sarcasm analysis is done.
	 * @return A JSONObject containing sarcasm percentage of the text
	 * @throws IOException
	 */
	
	public String sarcasmAnalysis(String text) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/content-interpretor-service/content-interpretor/sarcasm/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call Abuse Analysis in Dltk Content Inspector. 
	 *
	 * @param text The text on which abuse analysis is done.
	 * @return  A JSONObject representing class into which text is classified. This api supports hate, offensive speech and neutral classes.
	 * @throws IOException
	 */

	public String abuseAnalysis(String text) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/content-interpretor-service/content-interpretor/abuse-analysis/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("text", text);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
    /** 
     * The function to call chatbot service in dltk chatbot.
     * 
     * @param name the name of the chatbot.
     * @param type_ type of the chatbot. 'flow' or 'default'.
     * @return A JSONObject of the created model.
     * 
     */

	public String createChatbot(String name, String type_)
			throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("type", type_);
		JSONObject basicConversation = new JSONObject();
		basicConversation.put("doubtful_text", "Sorry. I didn't get you.Could you rephrase that?");
		basicConversation.put("welcome_text", "Hey buddy! How can I help you?");
		basicConversation.put("seeoff_text", "Happy to help you.Will see you soon!");
		body.put("basic_conversation", basicConversation);
		body.put("social_webhooks", new JSONObject());
		body.put("facebook_recipient_id", "");

		if (type_ == "flow") {
			JSONObject flowConversation = new JSONObject();
			JSONObject apiConfig = new JSONObject();
			apiConfig.put("url", "http://someurl.here/api/v1/api_path");
			List<JSONObject> questions = new ArrayList<JSONObject>();
			JSONObject question1 = new JSONObject();
			question1.put("question", "Hello, I'm flow based chatbot. May I know your name?");
			question1.put("jsonKey", "name");
			question1.put("order", 1);
			question1.put("responseType", "input_text");
			JSONObject question2 = new JSONObject();
			question2.put("question",
					"I can be useful for collecting information from user. Sometimes filling a long form can be tiresome, right?");
			question2.put("jsonKey", "boolean_value");
			question2.put("order", 2);
			question2.put("responseType", "input_text");
			JSONObject question3 = new JSONObject();
			question2.put("question", "I can make that process quick and easy.");
			question2.put("jsonKey", "process_info");
			question2.put("order", 3);
			question2.put("responseType", "input_text");

			questions.add(question1);
			questions.add(question2);
			questions.add(question3);

			flowConversation.put("api_config", apiConfig);
			flowConversation.put("questions", questions);
			body.put("flow_conversation", flowConversation);
		} else if (type_ == "default") {
			List<JSONObject> entities = new ArrayList<JSONObject>();
			List<JSONObject> intents = new ArrayList<JSONObject>();
			body.put("entities", entities);
			body.put("intents", intents);
		}

		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     *  Note: only one chatbot is created per app.
     * 
	 * @return A JSONObject containing chatbot info.
	 * @throws IOException
	 */

	public String getChatbotById() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param name  the name of the entity.
	 * @param items list of items to be added.
	 * @return A JSONObject stating either entities added successfully or not.
	 * @throws IOException
	 */

	public String addEntity(String name, List<JSONObject> items) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/entity/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("items", items);

		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param intent intent to be added.
	 * @param promptEntity name of prompt entity.
	 * @param promptResponse response of prompt entity.
	 * @param response_ default response.
	 * @return  A JSONObject stating either intents added successfully or not.
	 * @throws IOException
	 */

	public String addIntent(String intent, String promptEntity, String promptResponse, String response_)
			throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/intent/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("intent", intent);
		body.put("prompt-entity", promptEntity);
		body.put("prompt-response", promptResponse);
		body.put("response", response_);

		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param questions the list of questions to be added.
	 * question: question to be added.
     * options: list of options for questions
     * input: type of input for question like number ,text etc.,
     * jsonKey: key to which question is mapped.
     * order: order of question.
	 * @param app_config_url the url of the app config.
	 * @param method method of the app url.
	 * @return  JSONObject stating either questions added successfully or not.
	 * @throws IOException
	 */

	public String addFlow(List<JSONObject> questions, String app_config_url, String method) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/flow/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("questions", questions);
		JSONObject apiConfig = new JSONObject();
		apiConfig.put("url", app_config_url);
		apiConfig.put("method", method);

		body.put("api-config", apiConfig);

		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @return  A JSONObject containing flow is returned.
	 * @throws IOException
	 */

	public String getFlow() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/flow/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param item item to be added.
	 * @param entity the entity of the item.
	 * @return  A JSONObject stating either converse is updated or not.
	 * @throws IOException
	 */

	public String converseSuggestion(String item, String entity) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/converse/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("item", item);
		body.put("entity", entity);
		return new String(sendGet(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param welcomeText updating welcome text.
	 * @param doubtfulText updating doubtful text.
	 * @param seeoffText updating seeoff text. 
	 * @return A JSONObject stating either converse is updated or not.
	 * @throws IOException
	 */

	public String updateConverseSuggestion(String welcomeText, String doubtfulText, String seeoffText)
			throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/converse/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("welcome_text", welcomeText);
		body.put("doubtful_text", doubtfulText);
		body.put("seeoff_text", seeoffText);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param query query for converse.
	 * @return A JSONObject containing response for the query.
	 * @throws IOException
	 */

	public String converse(String query) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/converse/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("query", query);
		return new String(sendGet(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param accessToken access token of the platform .
	 * @param recipientId recipient id .
	 * @param platform currently supports 'facebook'.
	 * @return  A JSONObject stating whether chatbot is enabled for platform or not.
	 * @throws IOException
	 */

	public String enableSocialWebhooks(String accessToken, String recipientId, String platform) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/social/enable/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("access_token", accessToken);
		body.put("recipient_id", recipientId);
		body.put("platform", platform);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function to call chatbot service in dltk chatbot.
     * 
	 * @param platform  currently supports 'facebook'.
	 * @return A JSONObject stating whether chatbot is disabled for platform or not.
	 * @throws IOException
	 */

	public String disableSocialWebhooks(String platform) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/chatbot-service/dltk-chatbot/machinelearning/chatbot/social/disable/";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("platform", platform);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to create stream in Dltk.
	 * 
	 * @param name name of the stream.
	 * @param description  description of the stream.
	 * @return A JSONObject containing info of the stream created.
	 * @throws IOException
	 */

	public String createStream(String name, String description) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/process-service/dltk-process/stream";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to configure dapi events in Dltk.
	 * 
	 * @param events The events to be configured.
	 * eventId: Field Identifier
	 * field: Event Name
	 * type: Field Type( text/Integer/Long/Float)
	 * description: Field description
	 * @return A JSONObject containing details of dapi events.
	 * @throws IOException
	 */

	public String configureDapiEvents(List<?> events) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ingester-service/dltk-ingester/ingester/events/add";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendPost(requestURL, headersMap, events));
	}
	
	/**
	 * The function call to configure ingester in Dltk.
	 * 
	 * @param source Source to ingest from (TWIITER/FACEBOOK/DAPI/INSTAGRAM/YOUTUBE).
	 * @param tags  list of properties of the source.
	 * configData: Metadata and properties of the source
	 * @return A JSONObject containing details of ingester created.
	 * @throws IOException
	 */

	public String configureIngester(String source, List<JSONObject> tags) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ingester-service/dltk-ingester/config/add";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("source", source);
		JSONObject configData = new JSONObject();
		configData.put("tags", tags);
		body.put("configData", configData);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to configure transformer in Dltk.
	 * 
	 * @param name Name of the transformer.
	 * @param description  Description of the transformer.
	 * @param schema schema of the jolt transformer object.
	 * transformer: Jolt Transformer Object.
	 * @return  A JSONObject containing details of transformer.
	 * @throws IOException
	 */

	public String configureTransformer(String name, String description, JSONObject schema) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/transformer-service/dltk-transformer/transform";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		JSONObject transformer = new JSONObject();
		transformer.put("schema", schema);
		body.put("transformer", transformer);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to configure sink in Dltk.
	 * 
	 * @param destination Destination to dump data(POSTGRES/INFLUXDB).
	 * @param isEnabled True or False.
	 * @return  A JSONObject containing details of sink.
	 * @throws IOException
	 */

	public String configureSink(String destination, Boolean isEnabled) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/sink-service/dltk-sink/sink/config";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("destination", destination);
		body.put("isEnabled", isEnabled);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 *  The function call to get sink status in Dltk.
	 * 
	 * @return A JSONObject containing details of sink.
	 * @throws IOException
	 */

	public String sinkStatus() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/sink-service/dltk-sink/sink/config/status/all";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function call to create process definition in Dltk.
	 * 
	 * @param name The path of the dataset file.
	 * @param description Description of the Process Definition
	 * @param repeat True or False based on if repeating
	 * @param repeatInterval Interval of repeating
	 * @param from
	 * @param to
	 * @param workflow List of nodes
	 * @return A JSONObject containing details of process.
	 * @throws IOException
	 */

	public String createProcessDefinition(String name, String description, Boolean repeat, String repeatInterval,
			String from, String to, List<JSONObject> workflow) throws IOException {
		if (from == null) {
			from = "";
		}
		if (to == null) {
			to = "";
		}
		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/process-service/dltk-process/process-definition";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		body.put("repeat", repeat);
		body.put("repeatInterval", repeatInterval);
		body.put("from", from);
		body.put("to", to);
		body.put("workflow", workflow);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to schedule process execution in Dltk.
	 * 
	 * @return  A JSONObject status of scheduled process.
	 * @throws IOException
	 */

	public String scheduleProcessExecution() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/process-service/dltk-process/process-execution/schedule";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to start process in Dltk.
	 *  
	 * @return A JSONObject status of process started.
	 * @throws IOException
	 */

	public String startProcessExecution() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/process-service/dltk-process/process-execution/start";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to stop process in Dltk.
	 * 
	 * @return A JSONObject status of process stopped.
	 * @throws IOException
	 */

	public String stopProcessExecution() throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/process-service/dltk-process/process-execution/stop";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to query data  in Dltk.
	 * 
	 * @param query sql query on the dataset
	 * @param Destination destination of the dump data.
	 * @return A JSONObject containing details of fetched data by above query.
	 * @throws IOException
	 */

	public String queryData(String query, String Destination) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "process-service/dltk-process/process-sink";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("query", query);
		body.put("Destination", Destination);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to create ipfs node in block-ipfs
	 *  
	 * @param name Name of the node
	 * @param service Description of the node
	 * @return A JSONObject containing details of node created.
	 * @throws IOException
	 */

	public String createIpfsNode(String name, String service) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ipfs-service/dltk-ipfs/node";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("service", service);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to get node info in block-ipfs
	 *  
	 * @param nodeId Id of the node
	 * @return A JSONObject containing details of node.
	 * @throws IOException
	 */

	public String getNodeInfo(String nodeId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ipfs-service/dltk-ipfs/node/" + nodeId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function call to get peers info in block-ipfs
	 *  
	 * @param nodeId Id of the node
	 * @return  A JSONObject containing details of peers connected to this node.
	 * @throws IOException
	 */

	public String getPeersInfo(String nodeId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ipfs-service/dltk-ipfs/peers-info/" + nodeId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function call to add file in block-ipfs
	 *  
	 * @param filePath Path of the file.
	 * @param nodeId Id of the node
	 * @return  A JSONObject containing details of file created.
	 * @throws IOException
	 */

	public String addFile(String filePath, String nodeId) throws IOException {

		String response = "";
		String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ipfs-service/dltk-ipfs/file/" + nodeId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		MultipartUtility multipart = new MultipartUtility(requestURL, charset, headersMap);
		multipart.addFilePart("file", new File(filePath));
		response = multipart.finish();
		return response;
	}
	
	/**
	 * The function call to get files in block-ipfs
	 *  
	 * @param nodeId  Id of the node
	 * @return A JSONObject containing details of all files uploaded to this node.
	 * @throws IOException
	 */

	public String getFiles(String nodeId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/ipfs-service/dltk-ipfs/files/" + nodeId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function call to add device in dltk IOT.
	 * 
	 * @param name Name of the device
	 * @param description description for the device.
	 * @param eventInterval  5s or 1m or 2h or 3d.Interval of time at which device is sending data.m-minutes,h-hours,s-seconds,d-days
	 * @return  A JSONObject containing the id of the device added.
	 * @throws IOException
	 */

	public String addDevice(String name, String description, String eventInterval) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/device";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		body.put("eventInterval", eventInterval);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	
	/**
	 * The function call to get device in dltk IOT.
	 * 
	 * @param deviceId Id of the device.
	 * @return A JSONObject containing the details of the device.
	 * @throws IOException
	 */

	public String getDevice(String deviceId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/device/" + deviceId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}

	/**
	 * The function call to add dashboard in dltk IOT.
	 * 
	 * @param name name of the dashboard.
	 * @param description Description for the dashboard.
	 * @return A JSONObject containing the id of the dashboard added.
	 * @throws IOException
	 * 
	 */
	
	
	public String addDashboard(String name, String description) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/dashboard";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call to get dashboard in dltk IOT.
	 * 
	 * @param dashboardId Id of the dashboard.
	 * @return  A JSONObject containing the details of the dashboard.
	 * @throws IOException
	 */

	public String getDashboard(String dashboardId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/dashboard/" + dashboardId;
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		return new String(sendGet(requestURL, headersMap));
	}
	
	/**
	 * The function call to add widget in dltk IOT.
	 *           
	 * @param name  name of the widget
	 * @param description  Description for the widget.
	 * @param type_ TEXT or TIMESERIESGRAPH
	 * @param resource  Temperature(you need to add this same resource in your device code.)
	 * @param deviceId device Id
	 * @param dashboardId dashboard Id.
	 * @return A JSONObject containing the details of the widget.
	 * @throws IOException
	 */

	public String addWidget(String name, String description, String type_, String resource, String deviceId,
			String dashboardId) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/widget";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		body.put("name", name);
		body.put("description", description);
		body.put("type", type_);
		body.put("resource", resource);
		JSONObject device = new JSONObject();
		device.put("id", deviceId);
		JSONObject dashboard = new JSONObject();
		device.put("id", dashboardId);
		body.put("device", device);
		body.put("dashboard", dashboard);
		return new String(sendPost(requestURL, headersMap, body));
	}
	
	/**
	 * The function call get widget in dltk IOT.
	 * 
	 * @param widgetId id of the widget
	 * @param name name of the widget
	 * @param description Description for the widget.
	 * @param type_ type of widget TEXT or TIMESERIESGRAPH
	 * @param resource Temperature(you need to add this same resource in your device code.)
	 * @param device  dashboard details obtained from response by using add device api.
	 * @param dashboard  dashboard details obtained from response by using add dashboard api.
	 * @param timePeriod time period to fetch data from the widget. 1d(should give none for TEXT widget)
	 * @return A JSONObject containing the details of the widget.
	 * @throws IOException
	 */

	public String getWidgetData(String widgetId, String name, String description, String type_, String resource,
			JSONObject device, JSONObject dashboard, String timePeriod) throws IOException {

		//String response = "";
		//String charset = "UTF-8";
		String requestURL = this.baseUrl + "/iot-service/dltk-iot/widget/data";
		HashMap<String, String> headersMap = new HashMap<>();
		headersMap.put("ApiKey", this.apiKey);
		headersMap.put("User-Agent", USER_AGENT);
		headersMap.put("Content-Type", "application/json");
		JSONObject body = new JSONObject();
		JSONObject widget = new JSONObject();
		widget.put("id", widgetId);
		widget.put("name", name);
		widget.put("description", description);
		widget.put("type", type_);
		widget.put("resource", resource);
		JSONObject device_ = new JSONObject();
		device_.put("id", device.get("Id"));
		device_.put("userId", device.get("userId"));
		device_.put("name", device.get("name"));
		device_.put("description", device.get("description"));
		device_.put("appId", device.get("appId"));
		JSONObject dashboard_ = new JSONObject();
		dashboard_.put("id", dashboard.get("Id"));
		dashboard_.put("userId", dashboard.get("userId"));
		dashboard_.put("name", dashboard.get("name"));
		dashboard_.put("description", dashboard.get("description"));
		dashboard_.put("description", dashboard.get("description"));
		dashboard_.put("appId", dashboard.get("appId"));
		widget.put("device", device_);
		widget.put("dashboard", dashboard_);
		body.put("widget", widget);
		body.put("timePeriod", timePeriod);

		return new String(sendPost(requestURL, headersMap, body));
	}

}
