package com.ringov.justnews.internet;

import com.ringov.justnews.internet.retrofit.RSSClient;
import com.ringov.justnews.internet.retrofit.lenta.LentaClient;
import com.ringov.justnews.internet.retrofit.yandex.YandexClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Сергей on 29.12.2016.
 */

public class Service implements InternetService {

    private static Service instance;

    public static Service getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new Service();
            return instance;
        }
    }

    private RSSClient client;

    private boolean canceled;

    private Service() {

        client = new LentaClient();

        canceled = false;
    }

    @Override
    public void getData(ClientCallback callback) {
        Call<ResponseBody> result = client.getRSS();
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
                    callback.onResponseSuccess(json, client);
                } catch (JSONException e) {
                    callback.onParseFailure(e);
                } catch (IOException ioe){
                    callback.onResponseFailure(ioe);
                } catch (Exception exception){
                    callback.onResponseFailure(exception);
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
        JSONObject json = XML.toJSONObject(client.fixResponseString(body));
        return json;
    }
}
