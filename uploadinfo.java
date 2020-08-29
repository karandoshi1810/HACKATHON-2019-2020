package net.smallacademy.authenticatorapp;

public class uploadinfo {
    private String book;
    private String phone;
    private String rate;
    public String imageURL;
    public uploadinfo(){}

    public uploadinfo(String url){
        this.imageURL = url;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL(){
        return imageURL;
    }

    public String getBook() {
        return book;
    }

    public  void setBook(String book) {
        this.book = book;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
