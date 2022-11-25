package com.randombattlegenerator.main.entities;

import java.io.Serializable;

public class  UserSession  implements Serializable{

    private String usename;

    private boolean isAuthenticated;

    private Integer total;

    private Integer wins;

    private Integer loses;

    private  Integer streak;

    private Integer userId;

    

    public UserSession() {

    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public Integer getStreak() {
        return streak;
    }

    public void setStreak(Integer streak) {
        this.streak = streak;
    }



    

    

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    

    // private  Object test6;
	
	// private String sessionId;
    //private boolean isAuthenticated;
	// private String username;
	// private int logincount;

    // public String getSessionId() {
    //     return sessionId;
    // }

    // public void setUserid(String sessionId) {
    //     this.sessionId = sessionId;
    // }

    // public boolean isAuthenticated() {
    //     return isAuthenticated;
    // }

    // public  void setAuthenticated(boolean isAuthenticated) {
    //     this.isAuthenticated = isAuthenticated;
    // }
    // public String getUsername() {
    //     return username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // public int getLogincount() {
    //     return logincount;
    // }

    // public void setLogincount(int logincount) {
    //     this.logincount = logincount;
    // }

}
