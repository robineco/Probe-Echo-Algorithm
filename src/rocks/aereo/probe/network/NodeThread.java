package rocks.aereo.probe.network;

import rocks.aereo.probe.message.Message;
import rocks.aereo.probe.message.MessageType;

import java.util.ArrayList;
import java.util.List;

public class NodeThread extends Thread {

    private static int nodeCounter = 0;
    private final int nodeId;

    private final VirtualNetwork network;

    private final List<NodeThread> neighbors;
    private final boolean isInitiator;
    private final boolean isActive;
    private int messageCounter;
    private NodeThread senderNode;
    private boolean engaged = false;


    public NodeThread(VirtualNetwork network, boolean isInitiator) {
        this.network = network;
        this.isInitiator = isInitiator;
        neighbors = new ArrayList<>();
        nodeId = nodeCounter++;
        isActive = true;
        messageCounter = 0;
        senderNode = null;
    }

    @Override
    public String toString() {
        return "NodeThread{" +
                "nodeId=" + nodeId +
                '}';
    }

    @Override
    public void run() {
        if (isInitiator) {
            engaged = true;
            messageCounter = 0;
            for (NodeThread node : neighbors) {
                network.sendMessage(new Message(this, node, MessageType.PROBE));
                System.out.println("Node: " + nodeId + " Sending Initial PROBE");
            }
        }
        while (isActive) {
            try {
                //System.out.println("Node: " + nodeId + " Waiting for new messages...");
                Thread.sleep(500);

                Message message = network.receiveMessage(this);
                if (!message.isEmpty()) {
                    System.out.println("Node: " + nodeId + " New Message: " + message);
                    processMessage(message);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void processMessage(Message message) {

        if (message.getMessageType() == MessageType.PROBE) {
            if (!engaged) {
                engaged = true;
                senderNode = message.getSenderNode();
                messageCounter = 0;

                for (NodeThread node : neighbors) {
                    if (node.equals(senderNode)) {
                        continue;
                    }
                    network.sendMessage(new Message(this, node, MessageType.PROBE));
                }

            }
            messageCounter++;

            if (messageCounter == neighbors.size()) {
                engaged = false;
                if (isInitiator) {
                    System.out.println("FINISHED!");
                } else {
                    network.sendMessage(new Message(this, senderNode, MessageType.ECHO));
                }
            }
        } else {
            messageCounter++;
            if (messageCounter == neighbors.size()) {
                engaged = false;
                if (isInitiator) {
                    System.out.println("FINISHED!");
                } else  {
                    network.sendMessage(new Message(this, senderNode, MessageType.ECHO));
                }
            }
        }

    }

    public void addNeighbor(NodeThread node) {
        neighbors.add(node);
    }
}
