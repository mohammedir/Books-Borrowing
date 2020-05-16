package com.example.booksapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class Model_Book implements Parcelable {

    public String id , name_book , type_book  , describe ,communicate,user_id,availability
            ;
    public ImageView imageView ;



    public Model_Book(){


    }

    public Model_Book(String name_book , String type_book){
        this.name_book = name_book ;
        this.type_book = type_book ;

    }
    public Model_Book(String name_book , String type_book , String describe ){
        this.name_book = name_book ;
        this.type_book = type_book ;
        this.describe  = describe ;

    }

    public Model_Book(String name_book , String type_book , String describe ,String communicate,
                      String user_id ,String id,String availability){
        this.name_book = name_book ;
        this.type_book = type_book ;
        this.describe  = describe ;
        this.user_id = user_id;
        this.id = id;
        this.communicate = communicate ;
        this.availability = availability ;

    }

    protected Model_Book(Parcel in) {
        id = in.readString();
        name_book = in.readString();
        type_book = in.readString();
        describe = in.readString();
        user_id = in.readString();
        communicate = in.readString();
        availability = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name_book);
        dest.writeString(type_book);
        dest.writeString(describe);
        dest.writeString(user_id);
        dest.writeString(communicate);
        dest.writeString(availability);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Model_Book> CREATOR = new Creator<Model_Book>() {
        @Override
        public Model_Book createFromParcel(Parcel in) {
            return new Model_Book(in);
        }

        @Override
        public Model_Book[] newArray(int size) {
            return new Model_Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_book() {
        return name_book;
    }

    public void setName_book(String name_book) {
        this.name_book = name_book;
    }

    public String getType_book() {
        return type_book;
    }

    public void setType_book(String type_book) {
        this.type_book = type_book;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getCommunicate() {
        return communicate;
    }

    public void setCommunicate(String communicate) {
        this.communicate = communicate;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Model_Book){
            Model_Book book= (Model_Book) obj;
            return this.id.equals(book.getId());
        }
        else
            return  false;
    }


}
