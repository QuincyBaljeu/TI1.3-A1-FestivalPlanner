package Data.Tiled;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileInputStream;

public class TileMap {
    private TileSet[] tilesets;
    private Layer[] layers;
    private int height;
    private boolean infinite;
    private int nextlayerid;
    private int nextobjectid;
    private String orientation;
    private String renderorder;
    private String tiledversion;
    private int tileheight;
    private int tilewidth;
    private String type;
    private float version;
    private int width;
    public TileMap(String jsonFilePath) throws Exception {
        new JsonMapper().MapJson(
            Json.createReader(
                new FileInputStream(
                    new File(
                        jsonFilePath
                    )
                )
            ).readObject(),
            this
        );
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public void setNextlayerid(int nextlayerid) {
        this.nextlayerid = nextlayerid;
    }

    public void setNextobjectid(int nextobjectid) {
        this.nextobjectid = nextobjectid;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setRenderorder(String renderorder) {
        this.renderorder = renderorder;
    }

    public void setTiledversion(String tiledversion) {
        this.tiledversion = tiledversion;
    }

    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVersion(float verion) {
        this.version = verion;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTilesets(TileSet[] tilesets) {
        this.tilesets = tilesets;
    }

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

    class Layer {
        private int[] data;
        private int height;
        private int id;
        private String name;
        private int opacity;
        private String type;
        private boolean visible;
        private int width;
        private int x;
        private int y;

        public void setData(int[] data) {
            this.data = data;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOpacity(int opacity) {
            this.opacity = opacity;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    class TileSet {
        private int firstgid;
        private String source;

        public void setFirstgid(int firstgid) {
            this.firstgid = firstgid;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
