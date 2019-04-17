package com.eng.chula.se.hygeia.models;

import java.util.List;

public class HealthInfoResponse {
    private String weight;
    private String height;
    private List<String> diseaseList;
    private List<String> allergicList;

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public List<String> getDiseaseList() {
        return diseaseList;
    }

    public List<String> getAllergicList() {
        return allergicList;
    }
}
