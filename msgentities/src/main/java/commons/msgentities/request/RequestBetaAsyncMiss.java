package commons.msgentities.request;

import commons.msginfra.entities.AsyncServiceMessage;

public class RequestBetaAsyncMiss implements AsyncServiceMessage {

    private String requestName = "asyncRequest";
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
        return "RequestBetaAsyncMiss{" +
                "requestName='" + requestName + '\'' +
                ", producer='" + producer + '\'' +
                '}';
    }
}
