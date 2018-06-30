package com.example.sudip.coach;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class Main7Activity extends AppCompatActivity {
    final String[] res = {new String()};
    TextView promptMsg;
    EditText username,dateofbirth,college;
    RadioGroup radioSexGroup;
    private int  selectID;
    Button b1;
    RadioButton r1,r2,rm,rf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_details);
        username=(EditText)findViewById(R.id.editText5);
        dateofbirth=(EditText)findViewById(R.id.editText6);
        college=(EditText)findViewById(R.id.editText7);
        b1=(Button)findViewById(R.id.button3);
        rm=(RadioButton)findViewById(R.id.radioButton3);
        rf=(RadioButton)findViewById(R.id.radioButton4);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioSex);
        selectID=radioSexGroup.getCheckedRadioButtonId();
        r1=(RadioButton)findViewById(selectID);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((username.getText().toString().isEmpty())||(dateofbirth.getText().toString().isEmpty())||(college.getText().toString().isEmpty())||((!( rm.isChecked()))&&(!( rm.isChecked()))))
                {
                    Toast.makeText(getApplicationContext(),"Empty : "+R.id.radioButton3,Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("username",username.getText().toString());
                    params.put("DoBs",dateofbirth.getText().toString());
                    params.put("Profession",college.getText().toString());
                    //params.put("Gender",r1.getText().toString());
                    if(rm.isChecked())
                    {
                        params.put("Gender","Male");
                    }
                    if(rf.isChecked())
                    {
                        params.put("Gender","Female");
                    }
                    Bundle extras = getIntent().getExtras();  //used to share data b/w activities
                    String value1 = extras.getString("Coach ID");
                    params.put("coachId",value1);
                    update((HashMap) params);


               /* Intent txt = new Intent(Main7Activity.this, Main2Activity.class);
                startActivity(txt);

                params.put("coachId",value1);
                txt.putExtra("CoachID",value1);*/
                }
            }});

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorOrange)));

    }



    public void update(final HashMap obj){
        //String BASE_URL = "http://192.168.0.104/coach/";
        String BASE_URL = api.BASE_COACH_URL;

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        //Coach coachObj = new Coach(account.getId(),account.getGivenName(),account.getEmail(),"male","tisl",account.getDisplayName(),"task_id","1/1/1");
        StringRequest sr = new StringRequest(Request.Method.PUT, BASE_URL+"update",
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
                                String coachId = result.getString("coachId");
                                Intent txt = new Intent(Main7Activity.this, Main2Activity.class);
                                txt.putExtra("Coach ID",coachId);
                                startActivity(txt);
                                Main7Activity.this.finish();
                            }else{
                                if(msg.equals("username exist")){
                                    promptMsg=(TextView)findViewById(R.id.promptMsg);
                                    promptMsg.setText("username allready exist");
                                    // make text view red
                                }else{
                                    Toast.makeText(getApplicationContext(),"Not updated. Try Again",Toast.LENGTH_SHORT).show();
                                    //tost mesg
                                }
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
