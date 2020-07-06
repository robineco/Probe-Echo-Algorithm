package rocks.aereo.probe;

import rocks.aereo.probe.network.NodeThread;
import rocks.aereo.probe.network.VirtualNetwork;

public class ProbeEchoThread {

    public static void main(String[] args) {

        VirtualNetwork network = new VirtualNetwork();

        NodeThread initiator = new NodeThread(network, true);
        NodeThread nodeA = new NodeThread(network, false);
        NodeThread nodeB = new NodeThread(network, false);
        NodeThread nodeC = new NodeThread(network, false);
        NodeThread nodeD = new NodeThread(network, false);
        NodeThread nodeE = new NodeThread(network, false);
        NodeThread nodeF = new NodeThread(network, false);

        initiator.addNeighbor(nodeA);
        initiator.addNeighbor(nodeC);
        initiator.addNeighbor(nodeD);

        nodeA.addNeighbor(initiator);
        nodeA.addNeighbor(nodeB);
        nodeA.addNeighbor(nodeC);

        nodeB.addNeighbor(nodeA);
        nodeB.addNeighbor(nodeC);

        nodeC.addNeighbor(nodeA);
        nodeC.addNeighbor(nodeB);
        nodeC.addNeighbor(nodeD);
        nodeC.addNeighbor(initiator);

        nodeD.addNeighbor(initiator);
        nodeD.addNeighbor(nodeC);
        nodeD.addNeighbor(nodeE);
        nodeD.addNeighbor(nodeF);

        nodeE.addNeighbor(nodeD);
        nodeE.addNeighbor(nodeF);

        nodeF.addNeighbor(nodeD);
        nodeF.addNeighbor(nodeE);


        initiator.start();
        nodeA.start();
        nodeB.start();
        nodeC.start();
        nodeD.start();
        nodeE.start();
        nodeF.start();


    }
}
