package main.boot;

public class Preference {
    public String service;

    public static class User{
        public String name;
        public String pass;
        public int last;
    }
    public User[] users;

    public boolean store=false;

    public boolean showMainWindowAtStartup=true;
}
