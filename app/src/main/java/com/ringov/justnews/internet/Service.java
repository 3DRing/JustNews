package com.ringov.justnews.internet;

import com.ringov.justnews.SrcStr;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Сергей on 29.12.2016.
 */

public class Service implements DataGetter {

    private static Service instance;

    public static Service getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new Service();
            return instance;
        }
    }

    private RetrofitClient client;
    private final String BASE_URL;

    private boolean canceled;

    private Service() {
        BASE_URL = SrcStr.BASE_URL;
        String baseUrl = BASE_URL;

        client = new Retrofit.Builder()
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
                .create(RetrofitClient.class);

        canceled = false;
    }

    @Override
    public void getData(ClientCallback callback) {
        Call<ResponseBody> result = client.getTestRss();
        executeCallback(result,callback);
    }

    @Override
    public void cancel() {
        canceled = true;
    }

    private boolean isCanceled(){
        if(canceled){
            canceled = false;
            return true;
        }
        return false;
    }

    private void executeCallback(Call<ResponseBody> result, final ClientCallback callback){
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(isCanceled()){
                    return;
                }

                try{
                    JSONObject json = getJson(response);
                    callback.onResponseSuccess(json);
                } catch (JSONException e) {
                    callback.onParseFailure(e);
                } catch (IOException ioe){
                    callback.onResponseFailure(ioe);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(isCanceled()){
                    return;
                }

                callback.onResponseFailure(t);
            }
        });
    }

    private JSONObject getJson(Response<ResponseBody> response) throws IOException, JSONException {
        String body;
        if (response.isSuccessful()) {
            body = response.body().string();
        } else {
            body = response.errorBody().string();
        }
        JSONObject json = XML.toJSONObject(body);
        return json;
    }
}
