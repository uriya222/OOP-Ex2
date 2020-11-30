package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class GraphAdapter2 implements JsonDeserializer<DWGraph_DS>{

        public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            DWGraph_DS g1=new DWGraph_DS();
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject nodeData=jsonObject.get("Nodes").getAsJsonObject();
            JsonObject edgeData=jsonObject.get("Edges").getAsJsonObject();
            JsonObject src_list=edgeData.getAsJsonObject().get("_srcList").getAsJsonObject();
            for (Map.Entry<String, JsonElement> set: nodeData.entrySet()) {
                JsonElement json_node = set.getValue();
                int _key=json_node.getAsJsonObject().get("id").getAsInt();
                NodeData n=new NodeData(_key);
                double _weight=json_node.getAsJsonObject().get("_weight").getAsDouble();
                n.setWeight(_weight);
                if(json_node.getAsJsonObject().get("_info")!=null) {
                    String _info = json_node.getAsJsonObject().get("_info").getAsString();
                    n.setInfo(_info); //_info could be null
                }
                int _tag=json_node.getAsJsonObject().get("_tag").getAsInt();
                n.setTag(_tag);
                if(json_node.getAsJsonObject().get("pos")!=null) {
                    JsonObject geo = json_node.getAsJsonObject().get("pos").getAsJsonObject();
                    double x = geo.get("x").getAsDouble();
                    double y = geo.get("y").getAsDouble();
                    double z = geo.get("z").getAsDouble();
                    geo_location gn = new GeoLocation(x, y, z);
                    n.setLocation(gn);
                }
                g1.addNode(n);
            }
            for (Map.Entry<String, JsonElement> set: src_list.entrySet()) {
                JsonElement json_src = set.getValue();
                for (Map.Entry<String, JsonElement> set2: json_src.getAsJsonObject().entrySet()) {
                    JsonElement json_edge = set2.getValue();
                    int src=json_edge.getAsJsonObject().get("src").getAsInt();
                    int dest=json_edge.getAsJsonObject().get("dest").getAsInt();
                    double _weight=json_edge.getAsJsonObject().get("w").getAsDouble();
                    g1.connect(src,dest,_weight);
                }
            }
            return g1;
        }

    }

