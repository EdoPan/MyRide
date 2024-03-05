package it.dsmt.myRide.dto;

public class LoginDTO {
    private String username;
    private boolean isMaintainer;

    public LoginDTO(String username, boolean isMaintainer){
        this.username = username;
        this.isMaintainer = isMaintainer;
    }

    public String getUsername(){
        return this.username;
    }

    public boolean getIsMaintainer(){
        return this.isMaintainer;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setIsMaintainer(boolean isMaintainer){
        this.isMaintainer = isMaintainer;
    }
}
