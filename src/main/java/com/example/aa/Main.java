package com.example.aa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("1.fxml"));
        primaryStage.setTitle("start");
        primaryStage.setScene(new Scene(root, 1500, 700));
        primaryStage.show();
    }




    public void loop(){
        Boolean result = false;
        while(!result) {
            try {
                Thread.sleep(1 * 1000); //设置暂停的时间 5 秒
                NowString b= new NowString();;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }}
    public static class NowString {
        public static String get() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
            return df.format(new Date());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}