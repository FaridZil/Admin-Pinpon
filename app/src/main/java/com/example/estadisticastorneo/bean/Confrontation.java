package com.example.estadisticastorneo.bean;

import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Confrontation {
    private List<String> idKey;
    private List<String> imgViewTeam1;
    private List<String> imgViewTeam2;
    private List<String> textNameTeam1;
    private List<String> textNameTeam2;
    private List<String> txtTeamName1;
    private List<String> txtTeamName2;
    private List<String> textViewTime;
    private List<String> textViewPosition;
    private List<String> textViewScoreTm1;
    private List<String> textViewScoreTm2;

    public Confrontation() {
        idKey = new ArrayList<>();
        imgViewTeam1 = new ArrayList<>();
        imgViewTeam2 = new ArrayList<>();
        textNameTeam1 = new ArrayList<>();
        textNameTeam2 = new ArrayList<>();
        txtTeamName1 = new ArrayList<>();
        txtTeamName2 = new ArrayList<>();
        textViewTime = new ArrayList<>();
        textViewPosition = new ArrayList<>();
        textViewScoreTm1 = new ArrayList<>();
        textViewScoreTm2 = new ArrayList<>();
        int x = 0;
    }
    public void changedItem(String key,String _imgViewTeam1, String _imgViewTeam2, String _textNameTeam1,
                            String _textNameTeam2, String _txtTeamName1, String _txtTeamName2,
                            String _textViewTime, String _textViewPosition, String _textViewScoreTm1,
                            String _textViewScoreTm2){
        for(int x = 0; x < idKey.size(); x++){
            if(key.equals(idKey.get(x))){
                imgViewTeam1.set(x,_imgViewTeam1);
                imgViewTeam2.set(x,_imgViewTeam2);
                textNameTeam1.set(x,_textNameTeam1);
                textNameTeam2.set(x,_textNameTeam2);
                txtTeamName1.set(x,_txtTeamName1);
                txtTeamName2.set(x,_txtTeamName2);
                textViewTime.set(x,_textViewTime);
                textViewPosition.set(x,_textViewPosition);
                textViewScoreTm1.set(x,_textViewScoreTm1);
                textViewScoreTm2.set(x,_textViewScoreTm2);
                break;
            }
        }
    }

    public void removeItem(String key){
        for(int x = 0; x < idKey.size() ; x++) {
            if(key.equals(idKey.get(x))){
                idKey.remove(x);
                imgViewTeam1.remove(x);
                imgViewTeam2.remove(x);
                textNameTeam1.remove(x);
                textNameTeam2.remove(x);
                txtTeamName1.remove(x);
                txtTeamName2.remove(x);
                textViewTime.remove(x);
                textViewPosition.remove(x);
                textViewScoreTm1.remove(x);
                textViewScoreTm2.remove(x);
                break;
            }
        }
    }

    public void addItem(String _idKey,String _imgViewTeam1, String _imgViewTeam2, String _textNameTeam1,
                        String _textNameTeam2, String _txtTeamName1, String _txtTeamName2,
                        String _textViewTime, String _textViewPosition, String _textViewScoreTm1,
                        String _textViewScoreTm2) {
        idKey.add(_idKey);
        imgViewTeam1.add(_imgViewTeam1);
        imgViewTeam2.add(_imgViewTeam2);
        textNameTeam1.add(_textNameTeam1);
        textNameTeam2.add(_textNameTeam2);
        txtTeamName1.add(_txtTeamName1);
        txtTeamName2.add(_txtTeamName2);
        textViewTime.add(_textViewTime);
        textViewPosition.add(_textViewPosition);
        textViewScoreTm1.add(_textViewScoreTm1);
        textViewScoreTm2.add(_textViewScoreTm2);
    }

    public List<String> getImgViewTeam1() {
        return imgViewTeam1;
    }

    public void setImgViewTeam1(List<String> imgViewTeam1) {
        this.imgViewTeam1 = imgViewTeam1;
    }

    public List<String> getImgViewTeam2() {
        return imgViewTeam2;
    }

    public void setImgViewTeam2(List<String> imgViewTeam2) {
        this.imgViewTeam2 = imgViewTeam2;
    }

    public List<String> getTextNameTeam1() {
        return textNameTeam1;
    }

    public void setTextNameTeam1(List<String> textNameTeam1) {
        this.textNameTeam1 = textNameTeam1;
    }

    public List<String> getTextNameTeam2() {
        return textNameTeam2;
    }

    public void setTextNameTeam2(List<String> textNameTeam2) {
        this.textNameTeam2 = textNameTeam2;
    }

    public List<String> getTxtTeamName1() {
        return txtTeamName1;
    }

    public void setTxtTeamName1(List<String> txtTeamName1) {
        this.txtTeamName1 = txtTeamName1;
    }

    public List<String> getTxtTeamName2() {
        return txtTeamName2;
    }

    public void setTxtTeamName2(List<String> txtTeamName2) {
        this.txtTeamName2 = txtTeamName2;
    }

    public List<String> getTextViewTime() {
        return textViewTime;
    }

    public void setTextViewTime(List<String> textViewTime) {
        this.textViewTime = textViewTime;
    }

    public List<String> getTextViewPosition() {
        return textViewPosition;
    }

    public void setTextViewPosition(List<String> textViewPosition) {
        this.textViewPosition = textViewPosition;
    }

    public List<String> getTextViewScoreTm1() {
        return textViewScoreTm1;
    }

    public void setTextViewScoreTm1(List<String> textViewScoreTm1) {
        this.textViewScoreTm1 = textViewScoreTm1;
    }

    public List<String> getTextViewScoreTm2() {
        return textViewScoreTm2;
    }

    public void setTextViewScoreTm2(List<String> textViewScoreTm2) {
        this.textViewScoreTm2 = textViewScoreTm2;
    }
}
