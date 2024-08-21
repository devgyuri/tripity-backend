package me.gyuri.tripity.domain.place.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyuri.tripity.domain.place.entity.Place;
import me.gyuri.tripity.domain.place.repository.PlaceRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlaceService {
    @Value("${spring.api.area.key}")
    private String apiKey;

    private final PlaceRepository placeRepository;

    public void initializeDB() throws Exception {
            getPlaceList(1);
    }


    // test: only A01 category
    private void getPlaceList(int areaCode) throws Exception {
            String apiUrl = "http://apis.data.go.kr/B551011/KorService1/areaBasedList1";
            String serviceKey = apiKey;
            String pageNo = "1";
            String numOfRows = "10";
            String mobileOS = "ETC";
            String mobileApp = "Tripity";
            String type = "json";
            String cat1 = "A01";

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode(mobileOS, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode(mobileApp, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("areaCode", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(areaCode), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("cat1", "UTF-8") + "=" + URLEncoder.encode(cat1, "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            log.info("Response code: " + conn.getResponseCode());

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                    sb.append(line);
            }

            rd.close();
            conn.disconnect();

            String result = sb.toString();
            log.info("+++++ open api data +++++");
            log.info(result);

            // json object
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) parseItems.get("item");

            for (int i=0; i<array.size(); i++) {
                    JSONObject tmp = (JSONObject)array.get(i);
                    log.info(i + ": " + (String) tmp.get("contentid"));
                    getPlaceData((String) tmp.get("contentid"));
            }
    }

    private void getPlaceData(String contentId) throws Exception {
            String apiUrl = "http://apis.data.go.kr/B551011/KorService1/detailCommon1";
            String serviceKey = apiKey;
            String pageNo = "1";
            String numOfRows = "10";
            String mobileOS = "ETC";
            String mobileApp = "Tripity";
            String type = "json";
            String defaultYN = "Y";
            String addrinfoYN = "Y";
            String overviewYN = "Y";
            String firstImageYN = "Y";
            String areacodeYN = "Y";
            String mapinfoYN = "Y";

            StringBuilder urlBuilder = new StringBuilder(apiUrl);
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileOS", "UTF-8") + "=" + URLEncoder.encode(mobileOS, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("MobileApp", "UTF-8") + "=" + URLEncoder.encode(mobileApp, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("contentId", "UTF-8") + "=" + URLEncoder.encode(contentId, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("defaultYN", "UTF-8") + "=" + URLEncoder.encode(defaultYN, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", "UTF-8") + "=" + URLEncoder.encode(addrinfoYN, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("overviewYN", "UTF-8") + "=" + URLEncoder.encode(overviewYN, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("firstImageYN", "UTF-8") + "=" + URLEncoder.encode(firstImageYN, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("areacodeYN", "UTF-8") + "=" + URLEncoder.encode(areacodeYN, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", "UTF-8") + "=" + URLEncoder.encode(mapinfoYN, "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                    sb.append(line);
            }

            rd.close();
            conn.disconnect();

            String result = sb.toString();

            // json object
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject parseItems = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) parseItems.get("item");

            JSONObject data = (JSONObject)array.get(0);
            log.info((String) data.get("overview"));
            try {
                    Place place = Place.builder()
                            .name((String) data.get("title"))
                            .description((String) data.get("overview"))
                            .address((String) (data.get("addr1")) + (String) (data.get("addr2")))
                            .postcode(Integer.parseInt((String) data.get("zipcode")))
                            .mapX(Double.parseDouble((String) data.get("mapx")))
                            .mapY(Double.parseDouble((String) data.get("mapy")))
                            .image((String) data.get("firstimage"))
                            .build();
                    placeRepository.save(place);

            } catch (Exception e) {
                    log.error(e.getMessage());
            }

    }
}
