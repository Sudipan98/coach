package com.example.sudip.coach;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.View;
//tasks
public class Main3Activity extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private ArrayList<Property2> rentalProperties = new ArrayList<>();
    private ViewPager mSlideViewPager;
    String desc;
    private LinearLayout mDotLayout;
    //private SliderAdapter sliderAdapter;
    FloatingActionButton fab;
    NavigationView navigationView;
    private String coachId,goalId;
    TextView txt_help_gest,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_present);
        mdrawerlayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        fab = findViewById(R.id.fab);
        mtoggle=new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);

        address=(TextView)findViewById(R.id.address);
        mtoggle.syncState();
        Bundle extras = getIntent().getExtras();  //used to share data b/w activities
        coachId = extras.getString("Coach ID");
        goalId = extras.getString("Goal ID");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txt = new Intent(Main3Activity.this, Main5Activity.class);
                txt.putExtra("Coach ID",coachId);
                txt.putExtra("Goal ID",goalId);
                startActivity(txt);

            }
        });
        /*public void toggle_contents(View view){
            txt_help_gest.setVisibility(txt_help_gest.isShown()
                    ? View.GONE
                    : View.VISIBLE);
        }*/
      /*  address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_help_gest.setVisibility(txt_help_gest.isShown()
                        ? View.GONE
                        : View.VISIBLE);
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addTasks();
        ArrayAdapter<Property2> adapter;
        adapter = new Main3Activity.propertyArrayAdapter(this, 0, rentalProperties);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));

        //Find list view and bind it with the custom adapter
        ListView listView = (ListView) findViewById(R.id.customListView);
        //listView.setAdapter(adapter);
        navigationView=(NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.all_goals:
                        Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                        startActivity(intent);
                        break;
                   /* case R.id.pendingrequest:
                        Intent intent1=new Intent (Main3Activity.this,Main4Activity.class);
                        startActivity(intent1);
                        break;*/
                   /* case R.id.navgaols:
                        Intent intent2=new Intent (Main2Activity.this,Main2Activity.class);
                        intent2.putExtra("Coach ID",coachId);
                        startActivity(intent2);*/

                }


                return false;
            }
        });

        //add event listener so we can handle clicks
        AdapterView.OnItemClickListener adapterViewListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Property2 property = rentalProperties.get(position);
                txt_help_gest = (TextView) findViewById(R.id.txt_help_gest);
                txt_help_gest.setVisibility(View.VISIBLE);
                txt_help_gest.setText(desc);
                /*Intent intent = new Intent(Main3Activity.this, Main4Activity.class);
                intent.putExtra("streetNumber", property.getTaskID());
                intent.putExtra("streetName", property.getTaskName());
                intent.putExtra("suburb", property.getGoalID());
                //intent.putExtra("state", property.getPeople());
                intent.putExtra("image", property.getImage());

                startActivityForResult(intent, 1000);*/
            }
        };
        //set the listener to the list view
        listView.setOnItemClickListener(adapterViewListener);


        //fragmentTransaction=getSupportFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.)


    }
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    public void addTasks(){

        //String BASE_URL = "http://192.168.0.104/task/";
        String BASE_URL = api.BASE_TASK_URL;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Player playerObj = new Player(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.GET, BASE_URL+"goalTasks/"+goalId,
                new Response.Listener<String>() {

                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());

                        try {
                            JSONArray mJsonArray = new JSONArray(response);
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
                                ArrayAdapter<Property2> adapter = new propertyArrayAdapter(getApplicationContext(), 0, rentalProperties);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       /* int id = item.getItemId();

        if (id == R.id.action_cart) {
            //process your onClick here

            Intent txt = new Intent(Main3Activity.this, Main5Activity.class);
            txt.putExtra("Coach ID",coachId);
            txt.putExtra("Goal ID",goalId);
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


    //custom ArrayAdapater
    class propertyArrayAdapter extends ArrayAdapter<Property2>{

        private Context context;
        private List<Property2> rentalProperties;

        //constructor, call on creation
        public propertyArrayAdapter(Context context, int resource, ArrayList<Property2> objects) {
            super(context, resource, objects);

            this.context = context;
            this.rentalProperties = objects;
        }

        //called when rendering the list
        public View getView(int position, View convertView, ViewGroup parent) {

            //get the property we are displaying
            Property2 property = rentalProperties.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            //conditionally inflate either standard or special template
            View view;
            if(property.getFeatured() == true){
                view = inflater.inflate(R.layout.property_layout, null);
            }else{
                view = inflater.inflate(R.layout.property_layout, null);
            }
            TextView description = (TextView) view.findViewById(R.id.description);
            TextView address = (TextView) view.findViewById(R.id.address);
            TextView bedroom = (TextView) view.findViewById(R.id.bedroom);
            TextView bathroom = (TextView) view.findViewById(R.id.bathroom);
            TextView carspot = (TextView) view.findViewById(R.id.carspot);
            // TextView price = (TextView) view.findViewById(R.id.price);
            ImageView image = (ImageView) view.findViewById(R.id.image);


           /* Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/abc.otf");

            address.setTypeface(custom_font);*/

            //set address and description
            String completeAddress ="Task Name :  "+ property.getTaskName() + "\nTask ID : " + property.getTaskID()  ;
            address.setText(completeAddress);

            //display trimmed excerpt for description
            int descriptionLength = property.getDescription().length();
           /* if(descriptionLength >= 100){
                String descriptionTrim = property.getDescription().substring(0, 100) + "...";
                description.setText(descriptionTrim);
            }else{*/
            description.setText("Points: " + String.valueOf(property.getPoints())+"\nTime left: " + String.valueOf(property.getTimeleft())+" days"+" \nProgress Achieved : " + String.valueOf(property.getCompletion())+" %");


            //set price and rental attributes
            //price.setText("$" + String.valueOf(property.getPrice()));
            bedroom.setText("" /*+ String.valueOf(property.getPoints())*/);
            bathroom.setText(""/* + String.valueOf(property.getTimeleft())*/);
            carspot.setText("" /* String.valueOf(property.getCompletion())*/);


            Typeface custom_font = Typeface.createFromAsset(getAssets(),  "font/abc.otf");

            address.setTypeface(custom_font);
            bedroom.setTypeface(custom_font);
            bathroom.setTypeface(custom_font);
            carspot.setTypeface(custom_font);
            //description.setTypeface(custom_font);

            //get the image associated with this property
            int imageID = context.getResources().getIdentifier(property.getImage(), "drawable", context.getPackageName());
            image.setImageResource(imageID);

            return view;
        }
    }

}