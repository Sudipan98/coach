package com.example.sudip.coach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//goals
public class Main2Activity extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private ArrayList<Property> rentalProperties = new ArrayList<>();
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private String coachId;
    FloatingActionButton fab;
    //private SliderAdapter sliderAdapter;
    NavigationView navigationView;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_goals);
        fab = findViewById(R.id.fab);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        Bundle extras = getIntent().getExtras();  //used to share data b/w activities
        coachId = extras.getString("Coach ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txt = new Intent(Main2Activity.this, Main4Activity.class);
                txt.putExtra("Coach ID",coachId);
                startActivity(txt);

            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));


       /* mSlideViewPager =(ViewPager)findViewById(R.id.slodeViewPager);
        mDotLayout=(LinearLayout)findViewById(R.id.dotsLayout);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);*/
        //rentalProperties.add(new Property("01", "Goal Name", 3, 300.00, 0.50,  "task",false));
       /* rentalProperties.add(new Property(02, "Task Name", "Goal ID", "People", "Task Description",  "task", 3, 1, 50, false));
        rentalProperties.add(new Property(03, "Task Name", "Goal ID", "People", "Task Description",  "task", 3, 1, 50, false));
        rentalProperties.add(new Property(04, "Task Name", "Goal ID", "People", "Task Description",  "task", 3, 1, 50, false));
        rentalProperties.add(new Property(05, "Task Name", "Goal ID", "People", "Task Description",  "task", 3, 1, 50, false));
        *///create our new array adapter
        addGoals();
        ArrayAdapter<Property> adapter = new propertyArrayAdapter(this, 0, rentalProperties);
        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.customListView);
        listView.setAdapter(adapter);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all_goals:
                        Intent intent = new Intent(Main2Activity.this, Main4Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.pendingrequest:
                        Intent intent1=new Intent(Main2Activity.this, PendingRequests.class);
                        startActivity(intent1);
                        break;
                    case R.id.navgoals:
                        Intent intent2=new Intent(Main2Activity.this, Main4Activity.class);
                        startActivity(intent2);
                        break;
                    case R.id.settings:
                        Intent intent3=new Intent(Main2Activity.this, Main4Activity.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });

        //add event listener so we can handle clicks
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Property property = rentalProperties.get(position);

                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                intent.putExtra("Goal ID", property.getGoalID());
                //intent.putExtra("streetName", property.getGoalName());
                //intent.putExtra("suburb", property.getGoalID());
                //intent.putExtra("state", property.getPeople());
                intent.putExtra("image", property.getImage());
                intent.putExtra("Coach ID",coachId);
                startActivityForResult(intent, 1000);

            }
        };
        //set the listener to the list view
        listView.setOnItemClickListener(adapterViewListener);


        //fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.)


    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
       /* int id = item.getItemId();

        if (id == R.id.action_cart) {
            //process your onClick here
            Intent txt = new Intent(Main2Activity.this, Main4Activity.class);
            txt.putExtra("Coach ID",coachId);
            startActivity(txt);
            return true;
        }*/
        if (mtoggle.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);


    }


    /*public void onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.navworkpanel: {
                Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                break;
            }
        }
        //close navigation drawer
        mdrawerlayout.closeDrawer(GravityCompat.START);

    }*/
    public void addGoals(){

        //String BASE_URL = "http://192.168.0.104/task/";
        String BASE_URL = api.BASE_GOAL_URL;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Player playerObj = new Player(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.GET, BASE_URL+"allgoals/"+coachId,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONArray mJsonArray = new JSONArray(response);
                            int arrayLen = mJsonArray.length();
                            for(int i=0;i<arrayLen;i++){
                                JSONObject obj = mJsonArray.getJSONObject(i);
                                JSONObject goalObj = obj.getJSONObject("goal");
                                JSONArray taskArray = goalObj.getJSONArray("tasks");
                                int taskNo=taskArray.length();
                                Log.d(" goal obj ",obj.toString());
                                String success= obj.getString("success");

                                Log.d(" success", success);
                                Log.d(" goal Id ", goalObj.getString("goalId").toString());
                                if(success.equals("true")){
                                    String finished= obj.getString("finished");
                                    if(finished.equals("true")){
                                        rentalProperties.add(new Property(goalObj.getString("goalId"), goalObj.getString("goalName"), taskNo, Integer.parseInt(goalObj.getString("timeLimit")), Integer.parseInt(goalObj.getString("progress")),  goalObj.getString("goalImage"),false));
                                    }else if(finished.equals("false")){
                                        rentalProperties.add(new Property(goalObj.getString("goalId"), goalObj.getString("goalName"), taskNo, Integer.parseInt(goalObj.getString("timeLimit")), Integer.parseInt(goalObj.getString("progress")),  goalObj.getString("goalImage"),true));

                                    }else{

                                    }
                                }else{
                                    //rentalProperties.add(new Property("01", "Goal Name", 3, 300.00, 0.50,  "task",false));
                                    }


                            }
                            Log.d(" end loop "," end ");
                            if(rentalProperties.isEmpty()){
                                Log.d(" empty rentalProp ","empty ");
                            }else{
                                Log.d(" rentalProp "," not empty ");
                                ArrayAdapter<Property> adapter = new propertyArrayAdapter(getApplicationContext(), 0, rentalProperties);

                                //Find list view and bind it with the custom adapter
                                ListView listView = (ListView) findViewById(R.id.customListView);
                                listView.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());

                    }
                });
        queue.add(sr);
    }


    //custom ArrayAdapater
    class propertyArrayAdapter extends ArrayAdapter<Property> {

        private Context context;
        private List<Property> rentalProperties;

        //constructor, call on creation
        public propertyArrayAdapter(Context context, int resource, ArrayList<Property> objects) {
            super(context, resource, objects);

            this.context = context;
            this.rentalProperties = objects;
        }

        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            Property property = rentalProperties.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            //conditionally inflate either standard or special template
            View view;
            if (property.getFeatured() == true) {
                view = inflater.inflate(R.layout.property_layout, null);
            } else {
                view = inflater.inflate(R.layout.property_layout, null);
            }


             TextView description = (TextView) view.findViewById(R.id.description);
            TextView address = (TextView) view.findViewById(R.id.address);
            TextView bedroom = (TextView) view.findViewById(R.id.bedroom);
            TextView bathroom = (TextView) view.findViewById(R.id.bathroom);
            TextView carspot = (TextView) view.findViewById(R.id.carspot);
            // TextView price = (TextView) view.findViewById(R.id.price);
            ImageView image = (ImageView) view.findViewById(R.id.image);


           /*Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/abc.otf");

            address.setTypeface(custom_font);*/

            //set address and description
            String completeAddress = property.getGoalID() + " " + property.getGoalName() ;
            address.setText(completeAddress);

            //display trimmed excerpt for description
            //int descriptionLength = property.getDescription().length();
            /*if(descriptionLength >= 100){
                String descriptionTrim = property.getDescription().substring(0, 100) + "...";
                description.setText(descriptionTrim);
            }else{
                description.setText(property.getDescription());
            }*/
            description.setText("No. of Tasks: " + String.valueOf(property.getNumbertasks())+"\nTime left: " + String.valueOf(property.getTimeleft())+" days"+" \nProgress Achieved : " + String.valueOf(property.getCompletion())+" %");

            //set price and rental attributes
            //price.setText("$" + String.valueOf(property.getPrice()));
            bedroom.setText(""/* + String.valueOf(property.getNumbertasks())*/);
            bathroom.setText("" /*+ String.valueOf(property.getTimeleft())*/);
            carspot.setText(""/* + String.valueOf(property.getCompletion())*/);


            /*Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/abc.otf");

            address.setTypeface(custom_font);
            bedroom.setTypeface(custom_font);
            bathroom.setTypeface(custom_font);
            carspot.setTypeface(custom_font);
            //description.setTypeface(custom_font);

            //get the image associated with this property
            int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            image.setImageResource(imageID);*/

            return view;


        }
    }
}
