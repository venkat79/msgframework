package commons.msgentities.response;

import commons.msginfra.entities.ServiceMessage;

public class ResponseX2 implements ServiceMessage {

    private String uuid = "";

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "ResponseX2{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}
