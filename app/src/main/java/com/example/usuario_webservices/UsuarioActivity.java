package com.example.usuario_webservices;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class UsuarioActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText jetusr,jetnombre,jetcorreo,jetclave;
    Button jbtregistrar,jbtregresar,jbtconsultar,jbtmodificar,jbteliminar,jbtlimpiar;
    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        getSupportActionBar().hide();
        jetusr = findViewById(R.id.etuser);
        jetnombre = findViewById(R.id.etnombre);
        jetcorreo = findViewById(R.id.etcorreo);
        jetclave = findViewById(R.id.etclave);
        jbtregistrar = findViewById(R.id.btregistrar);
        jbtconsultar=findViewById(R.id.btconsultar);
        jbtregresar = findViewById(R.id.btregresar);
        jbtmodificar = findViewById(R.id.btmodificar);
        jbtlimpiar = findViewById(R.id.btLimpiar);
        jbteliminar = findViewById(R.id.bteliminar);

        rq = Volley.newRequestQueue(this);//conexion a internet

        jbtregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_usuario();
            }
        });

        jbtconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar_usuario();
            }
        });

        jbtregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });

        jbtmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { modificar();}
        });

        jbtlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Limpiar_campos();}
        });

        jbteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { eliminar();}
        });
    }

    private void registrar_usuario() {
        String url = "http://172.16.59.198:8081/usuarios/registrocorreo.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Limpiar_campos();
                        Toast.makeText(getApplicationContext(), "Registro de usuario realizado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registro de usuario incorrecto!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr",jetusr.getText().toString().trim());
                params.put("nombre", jetnombre.getText().toString().trim());
                params.put("correo",jetcorreo.getText().toString().trim());
                params.put("clave",jetclave.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void consultar_usuario(){
        if (jetusr.getText().toString().isEmpty()){
            Toast.makeText(this,"El usuario es requerido",Toast.LENGTH_SHORT).show();
            jetusr.requestFocus();
        }
        else{
            String url = "http://172.16.59.198:8081/usuarios/consulta.php?usr="+jetusr.getText().toString()+"";
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }


    public void modificar(){
        String url = "http://172.16.59.198:8081/usuarios/actualiza.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jetclave.setText("");
                        jetcorreo.setText("");
                        jetnombre.setText("");
                        jetusr.setText("");
                        jetusr.requestFocus();
                        Toast.makeText(getApplicationContext(), "su cambio se ha realizado con exito!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "no se pudo hacer el cambio!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr",jetusr.getText().toString().trim());
                params.put("nombre", jetnombre.getText().toString().trim());
                params.put("correo",jetcorreo.getText().toString().trim());
                params.put("clave",jetclave.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void eliminar(){
        String url = "http://172.16.59.198:8081/usuarios/elimina.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jetclave.setText("");
                        jetcorreo.setText("");
                        jetnombre.setText("");
                        jetusr.setText("");
                        jetusr.requestFocus();
                        Toast.makeText(getApplicationContext(), "Registro eliminado", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registrono se pudo eliminar", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usr",jetusr.getText().toString().trim());
                params.put("nombre", jetnombre.getText().toString().trim());
                params.put("correo",jetcorreo.getText().toString().trim());
                params.put("clave",jetclave.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }


    public void regresar() {
        Intent intmain=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intmain);
    }

    public void Limpiar_campos(){
        jetusr.setText("");
        jetnombre.setText("");
        jetcorreo.setText("");
        jetclave.setText("");
        jetusr.requestFocus();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Usuario invalido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        ClsUsuario usuario = new ClsUsuario();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            jetnombre.setText(jsonObject.optString("nombre"));
            jetcorreo.setText(jsonObject.optString("correo"));
            jetclave.setText(jsonObject.optString("clave"));
        }
        catch ( JSONException e)
        {
            e.printStackTrace();
        }
    }
}