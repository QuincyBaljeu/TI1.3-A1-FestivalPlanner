package Simulation;

import Data.AgendaModule;
import Data.Performance;
import Data.Tiled.Layer.Layer;
import Data.Tiled.Layer.ObjectGroup;
import Data.Tiled.Layer.TiledObject;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;


import java.awt.*;
import java.awt.geom.Point2D;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private Map map;
    private Data.Tiled.Map dataMap;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private AgendaModule agendaModule;
    private BorderPane mainPane;
    private LocalTime simTime = LocalTime.of(00,00,00);
    private int lastSeconds = LocalTime.now().getSecond();
    private Label time = new Label();
    private Boolean counting = false;

    public static final String path = System.getProperty("user.dir");

    public Simulation() throws Exception {
    	this.dataMap = new Data.Tiled.Map(path + "\\res\\Tiled\\untitled.json");
		this.map = new Map(this.dataMap);

        this.mainPane = new BorderPane();
        CheckBox collisionL = new CheckBox("show Collision");
        Button setTimeBack = new Button("-1 hour");
        setTimeBack.setOnAction(event -> {
                    if(simTime.getHour() < 1){
                        simTime = simTime.minusMinutes(simTime.getMinute());
                    } else {
                        simTime = simTime.minusHours(1);
                    }
                    lastSeconds = LocalTime.now().getSecond();
                }
        );
        Button setTimeForward = new Button("+1 hour");
        setTimeForward.setOnAction(event -> {
                    if(simTime.getHour() != 23){
                        simTime = simTime.plusHours(1);
                    } else {
                        simTime = simTime.plusMinutes(50 - simTime.getMinute());
                    }
                    lastSeconds = LocalTime.now().getSecond();
                }
        );
        Button start = new Button("Start");
        start.setOnAction(event ->{
            counting = true;
            lastSeconds = LocalTime.now().getSecond();
        });
        Button stop = new Button("Stop");
        stop.setOnAction(event -> counting = false);
        HBox top = new HBox();
        top.setSpacing(40);
        top.getChildren().addAll(collisionL, time, setTimeBack, setTimeForward, start, stop);

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
    	LocalTime simTime = simTimer();
        time.setText(simTime.toString());
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

    public void updateVisitorPosition() {
        int amountOfVisitors = visitors.size();
        int totalPopularity = 0;
        ArrayList<Performance> performances = new ArrayList();
        performances.addAll(agendaModule.getFestivalDays().get(0).getPerformances());

        for (Performance performance : performances) {
            totalPopularity += performance.getPopularity();
        }

        int positionedVisitors = 0;

        for (Performance performance : performances) {
            double popularity = (double) performance.getPopularity() / totalPopularity * amountOfVisitors;

            for (int i = 0; i < popularity; i++) {
                for (ObjectGroup objectLayer : this.dataMap.getObjectLayers()) {
                    visitors.get(i + positionedVisitors).setTarget(objectLayer.getObject(performance.getPodium().getName()));
                }
                positionedVisitors = (int) popularity - 1;
            }
        }
    }

    public LocalTime simTimer(){
        if (counting){
            if (!simTime.equals(LocalTime.of(23, 50))){
                if ((lastSeconds + 5) >= 60){
                    lastSeconds = 60 - (lastSeconds + 5);
                    if (lastSeconds == LocalTime.now().getSecond());{
                        System.out.println("5 seconden / is equal");
                        simTime = simTime.plusMinutes(10);
                        System.out.println(simTime);

                    }
                } else if ((lastSeconds + 5) == LocalTime.now().getSecond()){
                    lastSeconds = LocalTime.now().getSecond();
                    System.out.println("5 seconden");
                    simTime = simTime.plusMinutes(10);
                    System.out.println(simTime);

                }
            }
        }

        return simTime;
    }
}
