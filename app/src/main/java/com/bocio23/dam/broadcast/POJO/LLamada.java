package com.bocio23.dam.broadcast.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Observable;

/**
 * Created by Usuario on 25/01/2018.
 */

public class LLamada {
    private String number;

    private String date;

    private String state_call;

    private String endCall;

    public LLamada(String number, String date, String state_call,String endCall) {
        this.number = number;

        this.date = date;

        this.state_call = state_call;
        this.endCall=endCall;
    }

    public LLamada(Parcel in) {
    }

    public String getnumber() {
        return number;
    }

    public void setnumber(String number) {
        this.number = number;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getState_call() {
        return state_call;
    }

    public void setState_call(String state_call) {
        this.state_call = state_call;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEndCall() {
        return endCall;
    }

    public void setEndCall(String endCall) {
        this.endCall = endCall;
    }


    @Override
    public String toString() {
        return "LLamada{" +
                "number='" + number + '\'' +
                ", date=" + date +
                ", state_call='" + state_call + '\'' +
                ", endCall=" + endCall +
                '}';
    }

    /*@Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public LLamada createFromParcel(Parcel in) {
            return new LLamada(in);
        }

        public LLamada[] newArray(int size) {
            return new LLamada[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(number);
        dest.writeString(state_call);
        dest.writeSerializable(date);
        dest.writeSerializable(endCall);

    }*/
}
