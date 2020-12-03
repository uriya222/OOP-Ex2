package api;

import com.google.gson.*;

import java.lang.reflect.Type;


public class GraphAdapter implements  JsonDeserializer<DWGraph_DS>, JsonSerializer<DWGraph_DS>{


    public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DWGraph_DS g1=new DWGraph_DS();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray nodeData=jsonObject.get("Nodes").getAsJsonArray();
        JsonArray edgeData=jsonObject.get("Edges").getAsJsonArray();
        for (int i=0;i<nodeData.size();i++) {
            JsonElement json_node = nodeData.get(i);
            int _key=json_node.getAsJsonObject().get("id").getAsInt();
            NodeData n=new NodeData(_key);
            if (json_node.getAsJsonObject().get("_weight")!=null) {
                double _weight=json_node.getAsJsonObject().get("_weight").getAsDouble();
                n.setWeight(_weight);
            }
            if (json_node.getAsJsonObject().get("_info")!=null) {
                String _info=json_node.getAsJsonObject().get("_info").getAsString();
                n.setInfo(_info);
            }
            if (json_node.getAsJsonObject().get("_tag")!=null){
                int _tag=json_node.getAsJsonObject().get("_tag").getAsInt();
                n.setTag(_tag);
            }
            if(json_node.getAsJsonObject().get("pos")!=null) {
                String geo = json_node.getAsJsonObject().get("pos").getAsString();
                String[] st = geo.split(",");
                double x = Double.parseDouble(st[0]);
                double y = Double.parseDouble(st[1]);
                double z = Double.parseDouble(st[2]);
                geo_location gn = new GeoLocation(x, y, z);
                n.setLocation(gn);
            }
            g1.addNode(n);
        }
        for (int i=0;i<edgeData.size();i++) {
            JsonElement json_edge = edgeData.get(i);
            if (json_edge.getAsJsonObject().get("src")==null) System.out.println("src"+i);
            int src=json_edge.getAsJsonObject().get("src").getAsInt();
            if (json_edge.getAsJsonObject().get("dest")==null) System.out.println("dest"+i);
            int dest=json_edge.getAsJsonObject().get("dest").getAsInt();
            if (json_edge.getAsJsonObject().get("w")==null) System.out.println("w"+i);
            double _weight=json_edge.getAsJsonObject().get("w").getAsDouble();
            g1.connect(src,dest,_weight);
        }
        return g1;
    }

    @Override
    public JsonElement serialize(DWGraph_DS dw, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject g1=new JsonObject();
        JsonArray nodeData=new JsonArray(dw.nodeSize());
        JsonArray edgeData=new JsonArray(dw.edgeSize());
        for (node_data x: dw.getV()) {
            JsonObject json_node = new JsonObject();
            if (x.getLocation()!=null) {
                String s = x.getLocation().toString();
                json_node.addProperty("pos", s);
            }
            json_node.addProperty("id",x.getKey());
            json_node.addProperty("_weight",x.getWeight());
            json_node.addProperty("_info",x.getInfo());
            json_node.addProperty("_tag",x.getTag());
            nodeData.add(json_node);
            if (dw.getE(x.getKey())!=null) {
                for (edge_data e : dw.getE(x.getKey())) {
                    JsonObject json_edge = new JsonObject();
                    json_edge.addProperty("src", e.getSrc());
                    json_edge.addProperty("w", e.getWeight());
                    json_edge.addProperty("dest", e.getDest());
                    edgeData.add(json_edge);
                }
            }
        }
        g1.add("Nodes",nodeData);
        g1.add("Edges",edgeData);
        return g1;
    }
}


