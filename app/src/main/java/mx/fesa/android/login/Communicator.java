package mx.fesa.android.login;

import android.util.Log;

import com.squareup.otto.Produce;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Communicator {
    private static  final String TAG = "Communicator";
    private static final String SERVER_URL = "http://granfonda.com";

    public void loginPost(String username, String password){
        RestAdapter restAdapter = new RestAdapter.Builder()

                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response2) {
                if(serverResponse.getResponseCode() == 0){
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                }else{
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if(error != null ){
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200,error.getMessage()));
            }
        };
        communicatorInterface.postData("login", username, password, callback);
    }

    public void loginGet(String username, String password){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SERVER_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        Interface communicatorInterface = restAdapter.create(Interface.class);
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void success(ServerResponse serverResponse, Response response2) {
                if(serverResponse.getResponseCode() == 0){
                    BusProvider.getInstance().post(produceServerEvent(serverResponse));
                }else{
                    BusProvider.getInstance().post(produceErrorEvent(serverResponse.getResponseCode(), serverResponse.getMessage()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if(error != null ){
                    Log.e(TAG, error.getMessage());
                    error.printStackTrace();
                }
                BusProvider.getInstance().post(produceErrorEvent(-200,error.getMessage()));
            }
        };
        communicatorInterface.getData("login", username, password, callback);
    }

    @Produce
    public ServerEvent produceServerEvent(ServerResponse serverResponse) {
        return new ServerEvent(serverResponse);
    }

    @Produce
    public ErrorEvent produceErrorEvent(int errorCode, String errorMsg) {
        return new ErrorEvent(errorCode, errorMsg);
    }
}