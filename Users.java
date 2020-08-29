package net.smallacademy.authenticatorapp;

public class Users {

    public String book, imageURL, rate,phone,userNo;

    public Users(){

    }

    public String getphone(){
        return phone;
    }
    public void setphone(String phone){
        this.phone=phone;
    }
    public String getbook() {
        return book;
    }

    public void setuserNo(String userNo){
        this.userNo=userNo;
    }
    public String getuserNo() {
        return userNo;
    }

    public void setbook(String book) {
        this.book = book;
    }

    public String getimageURL() {
        return imageURL;
    }

    public void setImage(String image) {
        this.imageURL = image;
    }

    public String getrate() {
        return rate;
    }

    public void setrate(String rate) {
        this.rate = rate;
    }

    public Users(String book, String imageURL, String rate) {
        this.book = book;
        this.imageURL = imageURL;
        this.rate = rate;
    }
}
