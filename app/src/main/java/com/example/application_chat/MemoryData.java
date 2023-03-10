package com.example.application_chat;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryData {
    public static void saveData(String data, Context context) throws IOException {
        FileOutputStream fileOutputStream=context.openFileOutput("datata.txt",Context.MODE_PRIVATE);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();
    }
    public static void saveLastMsg(String data,String chatID, Context context) throws IOException {
        FileOutputStream fileOutputStream=context.openFileOutput(chatID+".txt",Context.MODE_PRIVATE);
        fileOutputStream.write(data.getBytes());
        fileOutputStream.close();
    }
    public static String getData(Context context) throws IOException {
        String data="";
        FileInputStream fileInputStream=context.openFileInput("datata.txt");
        InputStreamReader isr=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(isr);
        StringBuilder sb=new StringBuilder();
        String line;
        while ((line=bufferedReader.readLine())!=null){
            sb.append(line);
        }
        data=sb.toString();
        return data;
    }
    public static String getlastMsg(String chatID,Context context) throws IOException {
        String data="";
        FileInputStream fileInputStream= null;
        try {
            fileInputStream = context.openFileInput(chatID+".txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        InputStreamReader isr=new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader=new BufferedReader(isr);
        StringBuilder sb=new StringBuilder();
        String line;
        while ((line=bufferedReader.readLine())!=null){
            sb.append(line);
        }
        data=sb.toString();
        return data;
    }
}
