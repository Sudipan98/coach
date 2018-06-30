package com.example.sudip.coach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingRequests extends AppCompatActivity {
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
        setContentView(R.layout.activity_pending_requests);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
       Bundle extras = getIntent().getExtras();  //used to share data b/w activities
        coachId = extras.getString("Coach ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        Intent intent = new Intent(PendingRequests.this, Main2Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.pendingrequest:
                        Intent intent1=new Intent(PendingRequests.this, PendingRequests.class);
                        startActivity(intent1);
                        break;
                    case R.id.navgoals:
                        Intent intent2=new Intent(PendingRequests.this, Main5Activity.class);
                        startActivity(intent2);
                        break;
                    case R.id.settings:
                        Intent intent3=new Intent(PendingRequests.this, Main5Activity.class);
                        startActivity(intent3);
                        break;



                }

                return false;
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));

    }
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
                view = inflater.inflate(R.layout.pending_layout, null);
            } else {
                view = inflater.inflate(R.layout.pending_layout, null);
            }


            // TextView description = (TextView) view.findViewById(R.id.description);
            TextView address = (TextView) view.findViewById(R.id.address);
            Button accept = (Button) view.findViewById(R.id.bedroom);
            Button viewTask = (Button) view.findViewById(R.id.bathroom);
            Button reject = (Button) view.findViewById(R.id.carspot);
            Button viewPlayer = (Button) view.findViewById(R.id.price);
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

            //set price and rental attributes
            //price.setText("$" + String.valueOf(property.getPrice()));
            //bedroom.setText("No. of Tasks: " + String.valueOf(property.getNumbertasks()));
            //bathroom.setText("  Time left: " + String.valueOf(property.getTimeleft()));
            //carspot.setText("  Completion : " + String.valueOf(property.getCompletion()));



            /*Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/abc.otf");

            address.setTypeface(custom_font);
            bedroom.setTypeface(custom_font);
            bathroom.setTypeface(custom_font);
            carspot.setTypeface(custom_font);
            //description.setTypeface(custom_font);

            //get the image associated with this property
            int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            image.setImageResource(imageID);*/

            viewPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.view_player);
                }
            });
            viewTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.view_task);
                }
            });
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return view;


        }
    }

   /* public void addTasks(){

        //String BASE_URL = "http://192.168.0.104/task/";
        String BASE_URL = api.BASE_COACH_URL;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Player playerObj = new Player(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.GET, BASE_URL+"requests/"+coachId,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONObject mjsonobj = new JSONObject(response);
                            String success = mjsonobj.getString("success");
                            if(success.equals("true")){
                                String taskObjStr = mjsonobj.getString("taskObj");
                                String playerObjStr = mjsonobj.getString("playerObj");
                            }
                            int arrayLen = mJsonArray.length();
                            for(int i=0;i<arrayLen;i++){
                                JSONObject taskObj = mJsonArray.getJSONObject(i);
                                //JSONObject taskObj = obj.getJSONObject("task");
                                Log.d(" task obj ",taskObj.toString());
                                //String success= obj.getString("success");
                                //int state = Integer.parseInt(obj.getString("state"));
                                //Log.d(" success", success);
                                //Log.d(" task Id ", taskObj.getString("taskId").toString());
                                //if(success.equals("true")){
                                //if(state==1){
                                rentalProperties.add(new Property2(Integer.parseInt(taskObj.getString("taskId")), taskObj.getString("taskName"), taskObj.getString("gitRipoLink"), taskObj.getString("taskDes"),  taskObj.getString("taskImage"), Integer.parseInt(taskObj.getString("Points")), Integer.parseInt(taskObj.getString("timeLimit")), 50, true));
                                //}else if(state==0 ){
                                // rentalProperties.add(new Property2(Integer.parseInt(taskObj.getString("taskId")), taskObj.getString("taskName"), 123456, taskObj.getString("gitRipoLink"), taskObj.getString("taskDes"), taskObj.getString("taskImage"), Integer.parseInt(taskObj.getString("Points")), Integer.parseInt(taskObj.getString("timeLimit")), 50, false));
                                //}else{

                                //}
                                // }else{
                                // rentalProperties.add(new Property2(Integer.parseInt(taskObj.getString("taskId")), taskObj.getString("taskName"), 123456,taskObj.getString("gitRipoLink"), taskObj.getString("taskDes"),  taskObj.getString("taskImage"), Integer.parseInt(taskObj.getString("Points")), Integer.parseInt(taskObj.getString("timeLimit")), 50, false));
                                // }
                                desc=taskObj.getString("taskDes");

                            }
                            Log.d(" end loop "," end ");
                            if(rentalProperties.isEmpty()){
                                Log.d(" empty rentalProp ","empty ");
                            }else{
                                Log.d(" rentalProp "," not empty ");
                                ArrayAdapter<Property2> adapter = new Main3Activity.propertyArrayAdapter(getApplicationContext(), 0, rentalProperties);

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
    }*/
}
