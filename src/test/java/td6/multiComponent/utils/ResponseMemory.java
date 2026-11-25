package td6.multiComponent.utils;

import io.restassured.response.Response;

public class ResponseMemory {
    public static Response response;

    public static void clear(){
        response = null;
    }
}
