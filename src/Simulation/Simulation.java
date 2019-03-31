package Simulation;

import Data.AgendaModule;
import Data.Configuration.Settings;
import Simulation.Rendering.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Simulation {

    private Map map;
    private Data.Tiled.Map dataMap;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private AgendaModule agendaModule;
    private BorderPane mainPane;
    private Camera camera;

    public Simulation() throws Exception {
        this.mainPane = new BorderPane();
        CheckBox collisionL = new CheckBox("show Collision");
        HBox top = new HBox();
        top.getChildren().addAll(collisionL);

        mainPane.setTop(top);
        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
		FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
		this.camera = new Camera(canvas, g -> draw(g), g2d);
        mainPane.setCenter(canvas);

		this.dataMap = new Data.Tiled.Map(Settings.rootPath + "\\res\\Tiled\\untitled.json");
		this.map = new Map(this.dataMap, this.canvas);

        visitors = new ArrayList<>();
        while(visitors.size() < 50) {
            double x = Math.random()*canvas.getWidth();
            double y = Math.random()*canvas.getHeight();
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
        graphics.setBackground(Color.BLACK);
		graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        graphics.setTransform(camera.getTransform());
        this.map.draw(graphics);

        for(Visitor visitor : visitors){
			visitor.draw(graphics);
		}

		graphics.setTransform(new AffineTransform());
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
