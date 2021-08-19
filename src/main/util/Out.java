package main.util;


import main.boot.TerminalMain;

public class Out {
    public static void sayWithTimeLn(String msg){
        sayWithTime(msg+"\n");
    }
    public static void sayWithTime(String msg){
        System.out.print(TimeUtil.nowMMDDHHmmSS()+"|"+msg);
        if (TerminalMain.settings!=null)
            TerminalMain.settings.logPanel.append(TimeUtil.nowMMDDHHmmSS()+"|"+msg);
    }
}
