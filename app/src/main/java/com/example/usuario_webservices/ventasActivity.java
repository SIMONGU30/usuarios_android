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

public class ventasActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText jedtCodigoVenta , jedtFechaVenta , jedtUsuarioVenta;
    Button jbtIngresarVenta ,jbtCosultarVenta, jbtEliminarVenta , jbtRegresarVenta;
    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        getSupportActionBar().hide();

        jedtCodigoVenta=findViewById(R.id.etCod_venta);
        jedtFechaVenta=findViewById(R.id.etFechaVenta);
        jedtUsuarioVenta=findViewById(R.id.etUsuarioVenta);
        jbtIngresarVenta=findViewById(R.id.btIngresarVenta);
        jbtCosultarVenta=findViewById(R.id.btconsultarVenta);
        jbtEliminarVenta=findViewById(R.id.btEliminarVenta);
        jbtRegresarVenta=findViewById(R.id.btRegresarVentas);

        rq = Volley.newRequestQueue(this);//conexion a internet


        jbtIngresarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresarVenta();
            }
        });

        jbtCosultarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar_Venta();
            }
        });

        jbtEliminarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarVenta();
            }
        });

        jbtRegresarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ;}
        });


    }

    private void ingresarVenta() {
        String url = "http:// 172.18.87.207:80/usuarios/ingresarVenta.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Limpiar_campos();
                        Toast.makeText(getApplicationContext(), "Registro de venta realizado correctamente!", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Registro de venta incorrecto!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod_venta",jedtCodigoVenta.getText().toString().trim());
                params.put("fecha", jedtFechaVenta.getText().toString().trim());
                params.put("usr",jedtUsuarioVenta.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    public void consultar_Venta(){
        if (jedtCodigoVenta.getText().toString().isEmpty()){
            Toast.makeText(this,"El codigo de la venta es requerido  es requerido",Toast.LENGTH_SHORT).show();
            jedtCodigoVenta.requestFocus();
        }
        else{
            String url = "http:// 172.18.87.207:80/usuarios/consultarVenta.php?cod_venta="+jedtCodigoVenta.getText().toString()+"";
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }

    public void eliminarVenta(){
        String url = "http:// 172.18.87.207:80/usuarios/eliminarVenta.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        jedtCodigoVenta.setText("");
                        jedtFechaVenta.setText("");
                        jedtUsuarioVenta.setText("");
                        jedtCodigoVenta.requestFocus();
                        Toast.makeText(getApplicationContext(), "venta eliminada", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "venta se pudo eliminar", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod_venta",jedtCodigoVenta.getText().toString().trim());
                params.put("fecha", jedtFechaVenta.getText().toString().trim());
                params.put("usr",jedtUsuarioVenta.getText().toString().trim());
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
        jedtCodigoVenta.setText("");
        jedtFechaVenta.setText("");
        jedtUsuarioVenta.setText("");
        jedtCodigoVenta.requestFocus();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "codigo invalido", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        ClsVentas ventas = new ClsVentas();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);//posicion 0 del arreglo....
            jedtFechaVenta.setText(jsonObject.optString("fecha"));
            jedtUsuarioVenta.setText(jsonObject.optString("usr"));
        }


        catch ( JSONException e)
        {
            e.printStackTrace();
        }
    }
}
