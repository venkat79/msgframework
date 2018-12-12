package commons.msginfra.listeners.inf;

import commons.msginfra.entities.ServiceMessage;

public interface ServiceMessageSyncListener<MESSAGE extends ServiceMessage> {

    ServiceMessage handleMessage(MESSAGE message);
}