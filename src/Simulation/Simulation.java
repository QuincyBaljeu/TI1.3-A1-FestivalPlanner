package Simulation;

import javafx.animation.AnimationTimer;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Simulation {

    private Map map;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;

    private BorderPane mainPane;

    public static final String path = System.getProperty("user.dir");

    public Simulation() throws Exception {
        try (
			InputStream jsonMap = new FileInputStream( path + "\\res\\Tiled\\untitled.json");
			JsonReader jsonReader = Json.createReader(jsonMap)
        ) {
            JsonObject jsonArrayOfBands = jsonReader.readObject();
            this.map = new Map(path + "\\res\\Tiled\\untitled.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.mainPane = new BorderPane();

        CheckBox collisionL = new CheckBox("show Collision");
        HBox top = new HBox();
        top.getChildren().addAll(collisionL);

        mainPane.setTop(top);
        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);

        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        visitors = new ArrayList<>();

        while(visitors.size() < 50) {
            double x = Math.random()*1920;
            double y = Math.random()*1080;
            Visitor newVisitor = new Visitor(new Point2D.Double(x,y));
            if (map.hasCollision(newVisitor)){
            	continue;
			}
            for(Visitor visitor : visitors){
				if(visitor.hasCollision(newVisitor.getPosition())){
					continue;
				}
			}
			visitors.add(new Visitor(new Point2D.Double(x, y)));
        }
        
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        this.map.drawCache();
        canvas.setOnMouseMoved(e -> {
			visitors.parallelStream().forEach(
				(visitor -> {
					visitor.setTarget(new Point2D.Double(e.getX(), e.getY()));
				})
			);
        });
        draw(g2d);
    }

    public void update(double deltaTime) {
    	visitors.parallelStream().forEach(
			(visitor -> {
				visitor.update(visitors, map);
			})
		);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        this.map.draw(graphics);

        for(Visitor visitor : visitors)
            visitor.draw(graphics);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
