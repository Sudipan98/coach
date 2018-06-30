package com.example.sudip.coach;
//new task
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main5Activity extends AppCompatActivity {
    private String coachId,goalId; Button add;
    final String[] res = {new String()};
    EditText taskname,taskdescription,timeleft,points;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_task);
        Bundle extras = getIntent().getExtras();  //used to share data b/w activities
        coachId = extras.getString("Coach ID");
        goalId = extras.getString("Goal ID");
        add=(Button)findViewById(R.id.button2);
        taskname=(EditText)findViewById(R.id.editText51);

        points=(EditText)findViewById(R.id.editText61);

        taskdescription=(EditText)findViewById(R.id.editText31);
        timeleft=(EditText)findViewById(R.id.editText41);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> params = new HashMap<String, String>();
                params.put("taskName",taskname.getText().toString());
                params.put("taskDes",taskdescription.getText().toString());
                //params.put("coachId",coachId);
                params.put("timeLimit",timeleft.getText().toString());
                params.put("Points",timeleft.getText().toString());
                params.put("taskImage"," ");
                addNewTask((HashMap) params);
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));


    }
    public void addNewTask(final HashMap obj){
        //String BASE_URL = "http://192.168.0.104/coach/";
        String BASE_URL = api.BASE_TASK_URL;
        long newGoalId = Long.parseLong(goalId);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Coach coachObj = new Coach(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.POST, BASE_URL+"add/"+newGoalId,
                new Response.Listener<String>() {

                    public void onResponse(String response) {

                        Log.e("HttpClient", "success! response: " + response.toString());
                        res[0] =response.toString();
                        JSONObject result=new JSONObject();
                        try {
                            result = new JSONObject(res[0]);
                            String success = result.getString("success");
                            String msg = result.getString("msg");
                            Log.d("success ",success);
                            Log.d("msg ",msg);
                            if(success.equals("true")){
                                //String coachId = result.getString("coachId");

                                Intent txt = new Intent(Main5Activity.this, Main3Activity.class);
                                txt.putExtra("Coach ID",coachId);
                                txt.putExtra("Goal ID",goalId);
                                txt.setFlags(txt.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(txt);
                                Main5Activity.this.finish();
                                //Main2Activity.class.getClass().finish();


                            }else {
                                Toast.makeText(getApplicationContext(), "Not Added. Try Again", Toast.LENGTH_SHORT).show();
                                //tost mesg
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
                })
        {
            @Override
            protected Map<String,String> getParams(){

                Map<String,String> params = new HashMap<String, String>(obj);
                params = obj;
                Log.d(" hash",params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
}