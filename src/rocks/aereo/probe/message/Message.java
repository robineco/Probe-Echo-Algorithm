package rocks.aereo.probe.message;

import rocks.aereo.probe.network.NodeThread;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private static int messageCounter = 0;
    private int messageId;

    private boolean isEmpty;
    private NodeThread senderNode;
    private NodeThread receiverNode;
    private MessageType messageType;

    public Message(NodeThread senderNode, NodeThread receiverNode, MessageType messageType) {
        this.senderNode = senderNode;
        this.receiverNode = receiverNode;
        this.messageType = messageType;

        messageId = messageCounter++;
    }

    @Override
    public String toString() {
        return "MessageThreaded{" +
                "senderNode=" + senderNode +
                ", receiverNode=" + receiverNode +
                ", messageType=" + messageType +
                '}';
    }

    public Message(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public NodeThread getSenderNode() {
        return senderNode;
    }

    public NodeThread getReceiverNode() {
        return receiverNode;
    }


    public MessageType getMessageType() {
        return messageType;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
