package Simulation;

import Data.AgendaModule;
import Data.Configuration.Settings;
import Data.Tiled.Layer.ObjectGroup;
import Data.Tiled.Layer.TiledObject;
import Simulation.Rendering.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.ArrayList;

public class Simulation {

    private Map map;
    private Data.Tiled.Map dataMap;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private AgendaModule agendaModule;
    private BorderPane mainPane;
    private Camera camera;
    private LocalTime simTime = LocalTime.of(00,00,00);
    private int lastSeconds = LocalTime.now().getSecond();
    private boolean counting = false;
    private Label time = new Label();

    public Simulation() throws Exception {
        this.mainPane = new BorderPane();
        CheckBox collisionL = new CheckBox("show Collision");
        Button timeForward = new Button("+1 hour");
        timeForward.setOnAction(event -> {
            if (simTime.getHour() == 23){
                simTime = simTime.plusMinutes(50 - simTime.getMinute());
            } else {
                simTime = simTime.plusHours(1);
                lastSeconds = LocalTime.now().getSecond();
            }
        });
        Button timeBackward = new Button("-1 hour");
        timeBackward.setOnAction(event -> {
            if (simTime.getHour() < 1){
                simTime = simTime.minusMinutes(simTime.getMinute());
            } else {
                simTime = simTime.minusHours(1);
                lastSeconds = LocalTime.now().getSecond();
            }
        });
        Button start = new Button("Start");
        start.setOnAction(event -> {
            counting = true;
            lastSeconds = LocalTime.now().getSecond();
        });
        Button stop = new Button("Stop");
        stop.setOnAction(event -> {
            counting = false;
            lastSeconds = LocalTime.now().getSecond();
        });
        HBox top = new HBox();
        top.setSpacing(20);
        top.getChildren().addAll(collisionL, time, timeBackward, timeForward, start, stop);

        mainPane.setTop(top);
        this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.camera = new Camera(canvas, g -> draw(g), g2d);
        mainPane.setCenter(canvas);

        this.dataMap = new Data.Tiled.Map(Settings.rootPath + "\\res\\Tiled\\FestivalMap.json");
        this.map = new Map(this.dataMap, this.canvas);

        visitors = new ArrayList<>();

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        setEvents();
        this.map.drawCache();

        draw(g2d);
    }

    private void setEvents() {
        this.canvas.setOnMouseClicked(event -> {
            System.out.println("primmary click");
            ObjectGroup podia = (ObjectGroup)this.dataMap.getLayer("Places");
            this.visitors.parallelStream().forEach(visitor -> {
                int iterator = Math.round((float) (Math.random() * podia.getObjects().length-1));
                if (iterator < 0) {
                    iterator = 0;
                }
                visitor.setTarget(podia.getObjects()[iterator]);
                //System.out.println(this.dataMap.getObjectLayer().getObjects()[iterator].getName());
            });
        });
    }

    private void spawnVisitors(int count){
		for (int i = 0; i < count; i++) {
			TiledObject[] spawnPoints = ((ObjectGroup)dataMap.getLayer("SpawnPoints")).getObjects();
			for (TiledObject spawnPoint : spawnPoints){
				double x = (Math.random() * 100) + spawnPoint.getX();
				double y = (Math.random() * 100) + spawnPoint.getY();
				Visitor newVisitor = new Visitor(new Point2D.Double(x, y));
				if (map.hasCollision(newVisitor)){
					return;
				}
				if (x < 0 || y < 0 || x > (dataMap.getWidth() * dataMap.getTileWidth()) || y > (dataMap.getHeight() * dataMap.getTileHeight())){
					return;
				}
				for (Visitor visitor : visitors) {
					if (visitor.hasCollision(newVisitor.getPosition())) {
						return;
					}
				}
				visitors.add(new Visitor(new Point2D.Double(x, y)));
				System.out.println("spawned visitor #" + visitors.size());
			}
		}
	}

    public void update(double deltaTime) {
    	time.setText(simTimer().toString());
        if (visitors.size() < 20){
			spawnVisitors(10);
		}
        visitors.forEach(
			(visitor -> {
				visitor.update(visitors, map);
			})
        );
    }

    public LocalTime simTimer(){
        if (counting){
            if (!simTime.equals(LocalTime.of(23, 50))){
                if ((lastSeconds + 5) >= 60){
                    lastSeconds = (lastSeconds + 5) - 60;
                    if (lastSeconds == LocalTime.now().getSecond()){
                        System.out.println(LocalTime.now());
                        System.out.println("5 seconden / is equal");
                        simTime = simTime.plusMinutes(10);
                        System.out.println(simTime);

                    }
                } else if ((lastSeconds + 5) == LocalTime.now().getSecond()){
                    lastSeconds = LocalTime.now().getSecond();
                    System.out.println(LocalTime.now());
                    System.out.println("5 seconden");
                    simTime = simTime.plusMinutes(10);
                    System.out.println(simTime);

                }
            }
        }

        return simTime;
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.BLACK);
        graphics.setTransform(new AffineTransform());
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.setTransform(camera.getTransform());
        this.map.draw(graphics);

        for (Visitor visitor : visitors) {
            visitor.draw(graphics);
        }

        graphics.setTransform(new AffineTransform());
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
