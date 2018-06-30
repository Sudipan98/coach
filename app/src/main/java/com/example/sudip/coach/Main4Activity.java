package com.example.sudip.coach;
//new goal
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

public class Main4Activity extends AppCompatActivity {
    private String coachId,goalID;
    Button add;
    EditText goalname,goaldescription,timeleft;
    final String[] res = {new String()};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_goal);
        Bundle extras = getIntent().getExtras();  //used to share data b/w activities
        coachId = extras.getString("Coach ID");
      //  goalID=extras.getString("Goal ID");
        goalname=(EditText)findViewById(R.id.editText5);
        add=(Button)findViewById(R.id.button2);
        goaldescription=(EditText)findViewById(R.id.editText3);
        timeleft=(EditText)findViewById(R.id.editText4);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String> params = new HashMap<String, String>();
                params.put("goalName",goalname.getText().toString());
                params.put("goalDes",goaldescription.getText().toString());
                params.put("coachId",coachId);
                params.put("timeLimit",timeleft.getText().toString());
                params.put("goalImage"," ");
                addNewGoal((HashMap) params);

            }
        });


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));

    }
    public void addNewGoal(final HashMap obj){
        //String BASE_URL = "http://192.168.0.104/coach/";
        String BASE_URL = api.BASE_GOAL_URL;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Coach coachObj = new Coach(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.POST, BASE_URL+"add",
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
                                Log.d(" success true"," true ");

                                Intent txt = new Intent(Main4Activity.this, Main2Activity.class);
                                txt.putExtra("Coach ID",coachId);
                                txt.setFlags(txt.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(txt);
                                Main4Activity.this.finish();
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
