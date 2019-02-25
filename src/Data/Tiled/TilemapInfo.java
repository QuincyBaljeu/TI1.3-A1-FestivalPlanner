package Data.Tiled;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileInputStream;

public class TilemapInfo {
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
    private File tilemapFile;
    public TilemapInfo(String jsonFilePath) throws Exception {
        this.tilemapFile = new File(jsonFilePath);
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

    public File getTilemapFile() {
        return tilemapFile;
    }

    public TileSet[] getTilesets() {
		return tilesets;
	}

    public void setTilesets(TileSet[] tilesets) {
        this.tilesets = tilesets;
    }

	public Layer[] getLayers() {
		return layers;
	}

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
        this.height = height;
    }

	public boolean isInfinite() {
		return infinite;
	}

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

	public int getNextlayerid() {
		return nextlayerid;
	}

    public void setNextlayerid(int nextlayerid) {
        this.nextlayerid = nextlayerid;
    }

	public int getNextobjectid() {
		return nextobjectid;
	}

    public void setNextobjectid(int nextobjectid) {
        this.nextobjectid = nextobjectid;
    }

	public String getOrientation() {
		return orientation;
	}

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

	public String getRenderorder() {
		return renderorder;
	}

    public void setRenderorder(String renderorder) {
        this.renderorder = renderorder;
    }

	public String getTiledversion() {
		return tiledversion;
	}

    public void setTiledversion(String tiledversion) {
        this.tiledversion = tiledversion;
    }

	public int getTileheight() {
		return tileheight;
	}

    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

	public int getTilewidth() {
		return tilewidth;
	}

    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

	public String getType() {
		return type;
	}

    public void setType(String type) {
        this.type = type;
    }

	public float getVersion() {
		return version;
	}

    public void setVersion(float verion) {
        this.version = verion;
    }

	public int getWidth() {
		return width;
	}

    public void setWidth(int width) {
        this.width = width;
    }

    public class Layer {
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

		public int[] getData() {
			return data;
		}

		public void setData(int[] data) {
            this.data = data;
        }

		public int getHeight() {
			return height;
		}

        public void setHeight(int height) {
            this.height = height;
        }

		public int getId() {
			return id;
		}

        public void setId(int id) {
            this.id = id;
        }

		public String getName() {
			return name;
		}

        public void setName(String name) {
            this.name = name;
        }

		public int getOpacity() {
			return opacity;
		}

        public void setOpacity(int opacity) {
            this.opacity = opacity;
        }

		public String getType() {
			return type;
		}

        public void setType(String type) {
            this.type = type;
        }

		public boolean isVisible() {
			return visible;
		}

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

		public int getWidth() {
			return width;
		}

        public void setWidth(int width) {
            this.width = width;
        }

		public int getX() {
			return x;
		}

        public void setX(int x) {
            this.x = x;
        }

		public int getY() {
			return y;
		}

        public void setY(int y) {
            this.y = y;
        }
    }

    public class TileSet {
        private int firstgid;
        private String source;

		public int getFirstgid() {
			return firstgid;
		}

        public void setFirstgid(int firstgid) {
            this.firstgid = firstgid;
        }

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
            this.source = source;
        }
    }
}
