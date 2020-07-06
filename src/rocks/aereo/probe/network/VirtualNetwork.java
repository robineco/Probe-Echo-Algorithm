package rocks.aereo.probe.network;

import rocks.aereo.probe.message.Message;

import java.util.concurrent.ConcurrentLinkedQueue;

public class VirtualNetwork {

    private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    public void sendMessage(Message message) {
        messageQueue.add(message);
    }

    public Message receiveMessage(NodeThread node) {
        if (messageQueue.peek() != null && messageQueue.peek().getReceiverNode().equals(node)) {
            return messageQueue.poll();
        } else {
            return new Message(true);
        }
    }
}
