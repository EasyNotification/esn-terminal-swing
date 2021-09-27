package main.boot;

public class Preference {
    public String service;

    public static class User{
        public String name;
        public String pass;
        public int last;
    }
    public User[] users=new User[0];

    public boolean store=false;

    public boolean showMainWindowAtStartup=true;

    public boolean requestHistoryNotificationsWhenLoginToAAccount=false;

    public boolean broadcast=true;
    public int broadcastFrameXOnScreen=80;
    public int broadcastFrameYOnScreen=60;
    public int broadcastFrameW=530;

    public int mainWindowWidth=400;
    public int mainWindowHeight=600;

    public int recentLimit=100;
}
