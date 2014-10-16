
package com.fm.api.classes;

public class JsonResponse {
    private String status;
    private String error_message;

    public JsonResponse(String status, String message){
        this.status = status;
        this.error_message = message;
    }
}
