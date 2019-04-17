package com.eng.chula.se.hygeia.models;

public class PharmacyFormRequest {

    private String pharmacistName;
    private String pharmacistSurname;
    private String pharmacistLicenseId;

    public PharmacyFormRequest(String pharmacistName, String pharmacistSurname, String pharmacistLicenseId) {
        this.pharmacistName = pharmacistName;
        this.pharmacistSurname = pharmacistSurname;
        this.pharmacistLicenseId = pharmacistLicenseId;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public String getPharmacistSurname() {
        return pharmacistSurname;
    }

    public String getPharmacistLicenseId() {
        return pharmacistLicenseId;
    }

    private void register(){

    }

    private void login(){

    }

    private void logout(){

    }

    private void uploadFile(){

    }

    private void postAnswer(){

    }

    private void sendCharMessage(){

    }

    private void createArticle(){

    }

    private void deleteArticle(){

    }

    private void editArticle(){

    }

    private void readRequest(){

    }

    private void rejectRequst(){

    }

    private void createDrugRecommend(){

    }

    private void requestStore(){

    }

    private void pinStoreLocation(){

    }

    private void checkApplicationRegisterStatus(){

    }

}
