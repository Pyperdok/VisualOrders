package app.visualorders.Util;

import okhttp3.Response;

public interface IHttpResponseCallback {
    void execute(Response response) throws Exception;
}
