package main.boot;

import com.google.gson.Gson;
import main.gui.Window;
import main.gui.functions.AddDialog;
import main.gui.functions.PullDialog;
import main.gui.functions.PushDialog;
import main.gui.functions.RemoveDialog;
import main.gui.setting.Settings;
import main.util.Config;
import main.util.FileIO;
import main.util.Out;
import packs.PackRespNotification;
import util.Debug;

import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TerminalMain {
    public static Preference preference;
    public static Window window;
    public static Settings settings;
    public static PushDialog pushDialog;
    public static PullDialog pullDialog;
    public static AddDialog addDialog;
    public static RemoveDialog removeDialog;
    public static TrayMgr trayMgr;
    public static final String version="1.0";
    public static void main(String[] args) throws Exception{
//        Debug.debug=true;
        //load config
        if (new File("config/esn-terminal.conf").exists()){
            preference=new Gson().fromJson(FileIO.read("config/esn-terminal.conf"),Preference.class);
            Out.sayWithTimeLn("Load preference from file.");
        }
        if (preference==null){
            Out.sayWithTimeLn("Initialize preference.");
            preference=new Preference();
            if (!new File("config").isDirectory()){
                new File("config").mkdir();
            }
            if (!new File("config/esn-terminal.conf").exists()){
                new File("config/esn-terminal.conf").createNewFile();
            }
        }

        window=new Window();
        settings=new Settings();
        settings.accountPanel.loadStoredAccount();

        pushDialog=new PushDialog();
        pullDialog=new PullDialog();
        addDialog=new AddDialog();
        removeDialog=new RemoveDialog();



        trayMgr=new TrayMgr();


        window.setVisible(preference.showMainWindowAtStartup);
    }
    public synchronized static void serializePreference(){
        if (!new File("config").isDirectory()){
            new File("config").mkdir();
        }
        try {
            FileIO.write("config/esn-terminal.conf",new Gson().toJson(preference));
        } catch (Exception e) {
            Out.sayWithTimeLn("Cannot serialize preference.\n"+getErrorInfo(e));
        }
    }
    public static String getErrorInfo(Exception e){
        StringWriter sw=new StringWriter();
        PrintWriter pw=new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString().replaceAll("\t","    ");
    }

}
