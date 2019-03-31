package Simulation;

import Data.AgendaModule;
import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.ObjectGroup;
import Data.Tiled.Layer.TiledObject;
import javafx.animation.AnimationTimer;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Simulation {

    private Map map;
    private Data.Tiled.Map dataMap;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private AgendaModule agendaModule;
    private BorderPane mainPane;

    public static final String path = System.getProperty("user.dir");

    public Simulation() throws Exception {
    	this.dataMap = new Data.Tiled.Map(path + "\\res\\Tiled\\untitled.json");
		this.map = new Map(this.dataMap);

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
        canvas.setOnMouseClicked(e -> {
			for (Layer layer : this.dataMap.getLayers()){
				if (layer.getName().toLowerCase().equals("places")){
					TiledObject[] places = ((ObjectGroup)layer).getObjects();
					for (TiledObject place : places){
						if (
								(e.getX() <= place.getX() && e.getY() <= place.getY())
							&&	(place.getX() + place.getWidth() >= e.getX() && place.getY() + place.getHeight() >= e.getY())
							){
							System.out.println("Sending visitors to " + place.getName());
							visitors.parallelStream().forEach(
								(visitor -> {
									visitor.setTarget(place);
								})
							);
							return;
						}
					}
					return;
				}
			}
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
        graphics.setBackground(Color.BLACK);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        this.map.draw(graphics);

        for(Visitor visitor : visitors){
			visitor.draw(graphics);
		}
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
