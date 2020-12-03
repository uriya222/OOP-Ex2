package api;

import com.google.gson.*;

import java.lang.reflect.Type;


public class GraphAdapter implements  JsonDeserializer<DWGraph_DS>{


        public DWGraph_DS deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            System.out.println("probe");
            DWGraph_DS g1=new DWGraph_DS();
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray nodeData=jsonObject.get("Nodes").getAsJsonArray();
            JsonArray edgeData=jsonObject.get("Edges").getAsJsonArray();
            for (int i=0;i<nodeData.size();i++) {
                JsonElement json_node = nodeData.get(i);
                int _key=json_node.getAsJsonObject().get("id").getAsInt();
                NodeData n=new NodeData(_key);
              //  double _weight=json_node.getAsJsonObject().get("_weight").getAsDouble();
              //  n.setWeight(_weight);
              //  String _info=json_node.getAsJsonObject().get("_info").getAsString();
               // n.setInfo(_info);
              //  int _tag=json_node.getAsJsonObject().get("_tag").getAsInt();
               // n.setTag(_tag);
                String geo=json_node.getAsJsonObject().get("pos").getAsString();
                String[] st=geo.split(",");
                double x=Double.parseDouble(st[0]);
                double y=Double.parseDouble(st[1]);
                double z=Double.parseDouble(st[2]);
                geo_location gn=new GeoLocation(x,y,z);
                n.setLocation(gn);
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

    }


