package commons.msgentities.request;


import commons.msginfra.entities.RPCServiceMessage;

public class RequestAlphaSync implements RPCServiceMessage {

    private String requestName = "syncRequest";
    private String producer = "";

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "RequestAlphaSync{" +
                "requestName='" + requestName + '\'' +
                ", producer='" + producer + '\'' +
                '}';
    }
}

