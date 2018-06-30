package com.example.sudip.coach;


public class Property {

    //property basics
    private String  goalID;
    private String goalName;

    /*private String People;
    private String description;
    private String image;
    private Double price;*/
    private int numbertasks;
    private String image;
    private double timeleft;
    private double completion;
    private Boolean featured;

    //constructor
    public Property(
            String goalID, String goalName,int numbertasks, double timeleft, double completion,String image, Boolean featured){

        this.goalID = goalID;
        this.goalName =goalName;

        this.numbertasks = numbertasks;
        this.timeleft = timeleft;
        this.completion = completion;
        this.image=image;
        this.featured = featured;
    }

    //getters
    public String getGoalID() { return goalID; }
    public String getImage() { return goalID; }
    public String getGoalName() {return goalName; }
    public int getNumbertasks(){return  numbertasks;}
    public double getTimeleft(){ return timeleft; }
    public double getCompletion(){ return completion; }
    public Boolean getFeatured(){return featured; }
}
