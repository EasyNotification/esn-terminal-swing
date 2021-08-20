package main.gui.setting;

import conn.ESNSession;
import conn.ISessionListener;
import main.boot.Broadcast;
import main.boot.Preference;
import main.boot.TerminalMain;
import main.gui.NotificationPanel;
import main.util.Out;
import packs.PackRespNotification;
import packs.PackResult;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Account extends JPanel {
    final static Font nameFont=new Font("Serif",Font.BOLD,18);
    public class Entry extends JPanel{
        public boolean deleted=false;
        public ESNSession session;
        public JButton delete=new JButton("X");
        public JLabel nameL=new JLabel();
        public JLabel state=new JLabel("●");
        public JLabel priv=new JLabel("No connection.");
        public String name;
        public Thread proxyThr;
        public Entry(String name,String pass,int lastID){
            this.name=name;
            this.setLayout(null);
            this.setBorder(BorderFactory.createEtchedBorder());

            delete.setBounds(5,5,45,28);
            delete.addActionListener((e -> {
                if (JOptionPane.showConfirmDialog(Account.this,"Confirm to logout?")!=0){
                    return;
                }
                Account.this.removeAccount(this);
            }));
            this.add(delete);

            state.setBounds(delete.getX()+delete.getWidth()+7,5,15,15);
            state.setForeground(Color.gray);
            this.add(state);


            nameL.setBounds(state.getX()+state.getWidth()+3,0,80,20);
            nameL.setText(name);
            nameL.setFont(nameFont);
            this.add(nameL);

            priv.setBounds(state.getX(),state.getY()+state.getHeight()-3,250,25);
            this.add(priv);


            this.setSize(250,40);

            proxyThr=new Thread(()->{
                for (int j=0;j<10;j++) {
                    try {
                        this.session = new ESNSession(TerminalMain.preference.service, name, pass, 10000, new ISessionListener() {
                            @Override
                            public void notificationReceived(PackRespNotification packRespNotification) {
                                //TODO fill out
                                setLastID(packRespNotification.Id,name);
                                Out.sayWithTimeLn(name+" receiving notification:"+packRespNotification.Id);
//                                System.out.println("Target:"+packRespNotification.Target);
                                if (packRespNotification.Target.contains(",_global_,")) {
                                    synchronized (NotificationPanel.globalNotificationIDs) {
                                        if (!NotificationPanel.globalNotificationIDs.contains(packRespNotification.Id)) {
                                            NotificationPanel.globalNotificationIDs.add(packRespNotification.Id);
                                        }else {
                                            return;
                                        }
                                    }
                                    Broadcast.announce(packRespNotification,"_global_");
                                    TerminalMain.window.notificationPanel.addEntry(packRespNotification,"_global_");
                                }else {
                                    Broadcast.announce(packRespNotification,name);
                                    TerminalMain.window.notificationPanel.addEntry(packRespNotification,name);
                                }
                            }

                            @Override
                            public void sessionLogout(PackResult packResult) {
                                state.setForeground(Color.red);
//                                System.out.println("logout");
                                for (int i = 0; i < 10; i++) {
                                    try {
                                        Out.sayWithTimeLn("Reconnecting:"+name);
                                        session.reConnect(TerminalMain.preference.service, name, pass);
                                        state.setForeground(Color.green);
                                        break;
                                    } catch (Exception ignored) {
                                    }
                                    try {
                                        Thread.sleep(10000);
                                    } catch (Exception e) {
                                        break;
                                    }
                                }
                            }
                        });
//                        System.out.println("#############################################"+name+":"+session.isAvailable()+" "+session.getProtocolVersion());
                        if (session.isAvailable()){
//                            System.out.println("#############################################"+name+":"+session.isAvailable()+" "+session.getProtocolVersion());
                            synchronized (TerminalMain.window.tips) {
                                Out.sayWithTimeLn("Connection established:"+name);
                                if (TerminalMain.window.tips.getText().contains("Server connected")){
                                    TerminalMain.window.tips.setText(TerminalMain.window.tips.getText()+","+name);
                                }else {
                                    TerminalMain.window.tips.setText("Server connected:"+name);
                                }
                            }

                            state.setForeground(Color.green);
                            StringBuilder privstr=new StringBuilder("[");
                            if (session.can("account")){
                                privstr.append("account ");
                            }
                            if (session.can("pull")){
                                privstr.append("pull ");
                            }
                            if (session.can("push")){
                                privstr.append("push ");
                            }
                            privstr.append("]");
                            priv.setText(privstr.toString());

                            //request notifications pushed after last logout
                            if ((lastID==-1&&TerminalMain.preference.requestHistoryNotificationsWhenLoginToAAccount)){
                                setLastID(0,name);
                                session.requestNotifications(0,40);
                            }else if (lastID!=-1){
                                session.requestNotifications(lastID, 40);
                            }



                            return;
                        }
                    } catch (Exception e) {
                        Out.sayWithTimeLn(TerminalMain.getErrorInfo(e));
                    }
                    try{
                        Thread.sleep(5000);
                    }catch (Exception e){
                        break;
                    }
                }
                state.setForeground(Color.red);
            });
            proxyThr.start();
        }
        public void dispose(){
            if (session!=null){
                session.dispose();
            }
            proxyThr.stop();
        }
    }

    public ArrayList<Entry> entries=new ArrayList<>();
    public JButton addAcc=new JButton("+Add");
    public JLabel serviceTips=new JLabel();
    public ConfigAccount configAccount=new ConfigAccount();
    public Account(){
        this.setLayout(null);

        addAcc.setBounds(10,10,70,20);
        addAcc.addActionListener((e -> {
            if (TerminalMain.preference.service==null){
                TerminalMain.preference.service= JOptionPane.showInputDialog("Please input service address as \"<hostName>:<port>\":");
                TerminalMain.serializePreference();
            }

            configAccount.reset();
            configAccount.setVisible(true);
        }));
        this.add(addAcc);

        serviceTips.setBounds(addAcc.getX()+addAcc.getWidth()+10,addAcc.getY(),250,20);
        if (TerminalMain.preference!=null&&TerminalMain.preference.service!=null){
            serviceTips.setText("Service:"+TerminalMain.preference.service);
        }else {
            serviceTips.setText("Service address undefined.");
        }
        this.add(serviceTips);


    }
    public void addAccount(String name,String pass,int selectFrom){
        Entry e=new Entry(name,pass,selectFrom);
        e.setBounds(10,entries.size()*(e.getHeight()+7)+40,340,e.getHeight());
        this.setPreferredSize(new Dimension(this.getWidth(),(e.getHeight()+7)*(entries.size()+1)+40));
        this.entries.add(e);
        this.add(e);
        this.repaint();
//        System.out.println("add"+e.getWidth()+" h:"+e.getHeight()+" x"+e.getX()+" y"+e.getY());
    }
    public synchronized void removeAccount(Entry entry){
//        System.out.println("remove");
//        entries.remove(entry);
//        this.remove(entry);
        entry.deleted=true;
        entry.nameL.setText("<deleted>");
        entry.priv.setText("<deleted>");
        entry.delete.setEnabled(false);
        entry.state.setForeground(Color.gray);
        entry.setEnabled(false);
        this.repaint();

        synchronized (TerminalMain.preference.users) {
            ArrayList<Preference.User> userArrayList = new ArrayList<>();
            for (Preference.User user : TerminalMain.preference.users) {
                if (user.name.equals(entry.name)) {
                    continue;
                }
                userArrayList.add(user);
            }

            Preference.User[] users = new Preference.User[userArrayList.size()];
            int index = 0;
            for (Preference.User user : userArrayList) {
                users[index++] = user;
            }
            TerminalMain.preference.users = users;
        }
        TerminalMain.serializePreference();

        entry.dispose();
    }
    public void loadStoredAccount(){

        synchronized (TerminalMain.preference.users) {
            if (TerminalMain.preference != null && TerminalMain.preference.service != null) {
//            System.out.println("load accounts");
                if (TerminalMain.preference.users != null) {//load stored account
//                System.out.println("load accounts1"+TerminalMain.preference.users.length);
                    int len = TerminalMain.preference.users.length;
                    for (Preference.User user : TerminalMain.preference.users) {
//                    System.out.println("load account:"+user.name+" "+user.pass);
                        addAccount(user.name, user.pass,user.last+1);
                    }
                }
            }
        }
    }
    public void setLastID(int id,String name){
        synchronized (TerminalMain.preference.users) {
            for (Preference.User user : TerminalMain.preference.users) {
                if (user.name.equals(name)) {
                    user.last = id;
                }
            }
        }
        TerminalMain.serializePreference();

    }
}
