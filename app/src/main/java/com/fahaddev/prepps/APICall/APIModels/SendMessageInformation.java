package com.fahaddev.prepps.APICall.APIModels;

public class SendMessageInformation {
    int session_id;
    int receive_id;
    String message;

    public SendMessageInformation(int session_id, int receive_id, String message) {
        this.session_id = session_id;
        this.receive_id = receive_id;
        this.message = message;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(int receive_id) {
        this.receive_id = receive_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
