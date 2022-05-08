package app.visualorders.Util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.logging.LoggingPermission;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String _host;
    private String _ip;
    private OkHttpClient _client;
    private Handler _mainHandler;

    public HttpClient(String host, String ip) {
        _host = host;
        _ip = ip;
        _client = new OkHttpClient();
        _mainHandler = new Handler(Looper.getMainLooper());
    }
    public HttpClient(String host) {
        this(host, host);
    }

    private HttpResponse requestAsync(String method, String endpoint, String body) {
        Request.Builder builder = new Request.Builder()
        .url("http://" + _ip + endpoint)
        .addHeader("Host", _host);

        if(method != "GET" && body != null) {
            RequestBody json = RequestBody.create(body, JSON);
            builder.method(method, json);
        }

        Request request = builder.build();
        HttpResponse promise = new HttpResponse();

        _client.newCall(request).enqueue(new Callback() {
            @Override public void onResponse(Call call, Response response) {
                _mainHandler.post(() -> promise.resolve(response));
            }
            @Override public void onFailure(Call call, IOException exception) {
                _mainHandler.post(() -> promise.reject(exception));
            }
        });

        return promise;
    }

    public HttpResponse request(String method, String endpoint) {
        return request(method, endpoint, null);
    }
    public HttpResponse request(String method, String endpoint, String body) {
        return requestAsync(method, endpoint, body);
    }

    public class HttpResponse {
        private IHttpResponseCallback _callback;
        private IHttpResponseError _error;
        private Response _response;
        private Exception _exception;
        boolean _isCompleted = false;
        private HttpResponse() {}

        private void resolve(Response response) {
            _isCompleted = true;
            _response = response;
            if (_callback == null) return;

            try {
                _callback.execute(_response);
            }
            catch (Exception exception) {
                reject(_exception);
            }
        }
        private void reject(Exception exception) {
            _isCompleted = true;
            _exception = exception;
            if (_error == null) return;

            _error.execute(_exception);
        }
        public HttpResponse then(IHttpResponseCallback callback) {
            _mainHandler.post(() -> {
                _callback = callback;
                if (_isCompleted) {
                    resolve(_response);
                }
            });
            return this;
        }
        public void err(IHttpResponseError error) {
            _mainHandler.post(() -> {
                _error = error;
                if(_isCompleted && _exception != null) {
                    reject(_exception);
                }
            });
        }
    }
}


