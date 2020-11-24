package api;

public class Stam {
    public static void main(String[] args) {
        directed_weighted_graph g1=new DWGraph_DS();
        dw_graph_algorithms g2=new DWGraph_Algo();
        g2.init(g1);
        node_data n=new NodeData(0);
        node_data n1=new NodeData(1);
        g1.addNode(n);
        g1.addNode(n1);
        g1.connect(0,1,3);
        g2.save("mm");

    }
}
