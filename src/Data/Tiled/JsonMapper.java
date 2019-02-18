package Data.Tiled;

import Data.Tiled.TileMap;

import javax.json.*;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JsonMapper {
    private void callSetter(Object target, String fieldName, Object parameter) throws Exception{
        for (Method method : target.getClass().getMethods()){
            if (method.getName().equals("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1))){
                method.invoke(
                    target,
                    parameter
                );
            }
        }
    }

    public void MapJson(JsonObject root, Object target) throws Exception{
        for (Field field : target.getClass().getDeclaredFields()) {
            Class fieldType = field.getType();
            if (fieldType.equals(boolean.class)) {
                this.callSetter(
                    target,
                    field.getName(),
                    root.getBoolean(
                        field.getName()
                    )
                );
            } else if (fieldType.equals(String.class)) {
                this.callSetter(
                    target,
                    field.getName(),
                    root.getString(
                        field.getName()
                    )
                );
            } else if (fieldType.equals(int.class)) {
                this.callSetter(
                    target,
                    field.getName(),
                    root.getInt(
                        field.getName()
                    )
                );
            } else if (fieldType.equals(float.class)) {
                this.callSetter(
                    target,
                    field.getName(),
                    (float) root.getJsonNumber(
                        field.getName()
                    ).doubleValue()
                );
            } else if (fieldType.isArray()) {
                JsonArray jsonArray = root.getJsonArray(
                    field.getName()
                );
                Class arrayType = field.getType().getComponentType();
                if (arrayType.equals(int.class)){
                    int[] array = new int[jsonArray.size()];
                    for (int i = 0; i < jsonArray.size(); i++){
                        array[i] = jsonArray.getInt(i);
                    }
                    callSetter(
                        target,
                        field.getName(),
                        array
                    );
                }
                else {
                    // CreateInstance Werkt niet?
                    // Dan kan 't niet universeel :/
                    if (arrayType.equals(TileMap.TileSet.class)){
                        JsonArray tileArray = root.getJsonArray(
                            field.getName()
                        );
                        TileMap.TileSet[] tileSets = new TileMap.TileSet[tileArray.size()];
                        for (int i = 0; i < tileArray.size(); i++){
                            TileMap.TileSet tileSet = ((TileMap)target).new TileSet();
                            this.MapJson(
                                tileArray.getJsonObject(i),
                                tileSet
                            );
                            tileSets[i] = tileSet;
                        }
                        this.callSetter(
                            target,
                            field.getName(),
                            tileSets
                        );
                    }
                    else if (arrayType.equals(TileMap.Layer.class)){
                        if (arrayType.equals(TileMap.Layer.class)){
                            JsonArray layerArray = root.getJsonArray(
                                field.getName()
                            );
                            TileMap.Layer[] layers = new TileMap.Layer[layerArray.size()];
                            for (int i = 0; i < layerArray.size(); i++){
                                TileMap.Layer layer = ((TileMap)target).new Layer();
                                this.MapJson(
                                    layerArray.getJsonObject(i),
                                    layer
                                );
                                layers[i] = layer;
                            }
                            this.callSetter(
                                target,
                                field.getName(),
                                layers
                            );
                        }
                    }
                }
            }
        }
    }

}
