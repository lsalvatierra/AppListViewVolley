package pe.edu.idat.applistviewvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvDatos;
    List<String> vDatos = new ArrayList<String>();
    //Declaramos la petición al servicio.
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDatos = findViewById(R.id.lvDatos);
        //Instanciamos la cola de peticiones.
        mQueue = Volley.newRequestQueue(this);
        //Llamar al método ConsumirWS
        ConsumirWS();

    }

    private void ConsumirWS(){
        //Inicializar el URL del servicio web.
        String url = "https://api.myjson.com/bins/kp9wz";
        //Instanciar el objeto request para que sea agregado
        // a la cola de requests.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray =
                                    response.getJSONArray("employees");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject objEmployee
                                        = jsonArray.getJSONObject(i);
                                vDatos.add(
                                        objEmployee.getString("firstname"));
                            }
                            lvDatos.setAdapter(
                                    new ArrayAdapter<String>(
                                            getApplicationContext(),
                                            android.R.layout.simple_expandable_list_item_1,
                                            vDatos
                                    ));
                        }catch (JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                }
        );
        mQueue.add(request);
    }

}
