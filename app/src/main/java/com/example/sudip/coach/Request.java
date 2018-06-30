package com.example.sudip.coach;




public class Request {

    private String  taskID;
    private String taskName;

    private String PlayerName;
    private String PlayerID;
    private String PlayerImage;




    //constructor
    public Request(
            String taskID, String taskName,String playerName,String playerID,String playerImage){

        this.taskID = taskID;
        this.taskName =taskName;

        this.PlayerID = playerID;
        this.PlayerName = playerName;

        this.PlayerImage=playerImage;

    }

    //getters
    public String getTaskID() { return taskID; }
    public String getImage() { return PlayerImage; }
    public String getTaskName() {return taskName; }
    public String  getPlayerID(){return  PlayerID;}
    public String  getPlayerName(){return  PlayerName;}

}






