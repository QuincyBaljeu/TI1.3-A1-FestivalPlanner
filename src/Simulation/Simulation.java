package Simulation;

import Data.FestivalDay;
import Data.Performance;
import Data.Tiled.Layer.ObjectGroup;
import Data.Tiled.Layer.TiledObject;
import Simulation.Rendering.Camera;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulation {

    private Map map;
    private Data.Tiled.Map dataMap;
    private ResizableCanvas canvas;
    private ArrayList<Visitor> visitors;
    private BorderPane mainPane;
    private Camera camera;
    private LocalTime simTime = LocalTime.of(00,00,00);
    private int lastSeconds = LocalTime.now().getSecond();
    private boolean counting = false;
    private Label time = new Label();

    private FestivalDay festivalDay;

    private CheckBox debugDraw;
    private Label clock;
    private TextField visitorCount;
    private Label visitorCountLabel;
    private Label currentVisitorCount;
    private Label speedLabel;
    private Label clockLabel;
    private Button loadButton;
    private TextField speedControl;
    private TextField clockControl;
	private int maximumVisitors;
	private double elapsedDeltaTime = 0;
	private double timeMultiplyer;
	private double clockMultiplyer;
    private HashMap<String, TiledObject> podiumObjectMap;
    private LocalTime simulationTime;
    private Stage stage;

    public Simulation(FestivalDay festivalDay, Stage stage) throws Exception {
    	this.stage = stage;
		this.addSimulationControls();
    	this.festivalDay = festivalDay;
    	//this.initialize();
    }

    private void initialize(String mapFile) throws Exception{
		this.simulationTime = LocalTime.of(11, 30, 0);
		this.canvas = new ResizableCanvas(g -> draw(g), mainPane);
		FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
		mainPane.setCenter(canvas);
		this.dataMap = new Data.Tiled.Map(mapFile);
		this.map = new Map(this.dataMap, this.canvas);
		this.camera = new Camera(canvas, g -> draw(g), g2d, this.map.getRequiredCacheSize());
		this.initPodiumObjectMap();
		visitors = new ArrayList<>();
		new AnimationTimer() {
			long last = -1;

			@Override
			public void handle(long now) {
				if (last == -1)
					last = now;
				update((now - last) / 1000000.0);
				last = now;
				draw(g2d);
			}
		}.start();
		setEvents();
		this.map.drawCache();
		draw(g2d);
	}

    private void addSimulationControls() {
		this.mainPane = new BorderPane();
		HBox top = new HBox();
		this.debugDraw = new CheckBox("debugDraw");
		this.clock = new Label();
		this.visitorCount = new TextField("1000");
		this.currentVisitorCount = new Label();
		this.visitorCountLabel = new Label("\tMaximum number of visitors:");
		this.speedLabel = new Label("\t simulation speed multiplier: ");
		this.speedControl = new TextField("1");
		this.clockLabel = new Label("\t simulation clock multiplier: ");
		this.clockControl = new TextField("1");
		this.loadButton = new Button("Load map...");
		this.loadButton.setOnAction((e) -> {
			FileChooser fileChooser =
				new FileChooser();
			fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(
					"json files (*.json)", "*.json"
				)
			);
			File saveFile = fileChooser.showOpenDialog(this.stage);
			try {
				initialize(saveFile.getPath());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		mainPane.setTop(top);
		this.addControlsToTop(
			loadButton,
			debugDraw,
			visitorCountLabel,
			visitorCount,
			currentVisitorCount,
			speedLabel,
			speedControl,
			clockLabel,
			clockControl,
			clock
		);
	}

	public boolean getDebugDraw(){
		return this.debugDraw.isSelected();
	}

	public void setDebugDraw(boolean isEnabled){
		this.debugDraw.setSelected(isEnabled);
	}

	private void updateGui(){
		this.currentVisitorCount.setText("\tCurrent visitor count: " + this.visitors.size());
		this.clock.setText("\tCurrent simulation time: " + this.simulationTime.toString());
		try {
			this.maximumVisitors = Integer.valueOf(this.visitorCount.getText());
		} catch (Exception ex) { }
		try {
			this.timeMultiplyer = Double.valueOf(this.speedControl.getText());
		} catch (Exception ex) { }
		try {
			this.clockMultiplyer = Double.valueOf(this.clockControl.getText());
		} catch (Exception ex) { }
	}

	private void addControlsToTop(Node... nodes){
    	for (Node node : nodes){
			((HBox)mainPane.getTop()).getChildren().add(node);
		}
	}

    private TiledObject getRandomPodium(){
		ObjectGroup podia = (ObjectGroup)this.dataMap.getLayer("Places");
		int iterator = Math.round((float) (Math.random() * podia.getObjects().length-1));
		if (iterator < 0) {
			iterator = 0;
		}
		return podia.getObjects()[iterator];
	}

    private void setEvents() {
        this.canvas.setOnMouseClicked(event -> {
        	if (!event.getButton().equals(MouseButton.PRIMARY)) return;
        	//this.randomizeTargets();
        });
    }

    private void randomizeTargets(){
		this.visitors.parallelStream().forEach(visitor -> {
			visitor.setTarget(getRandomPodium());
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
				//newVisitor.setTarget(getRandomPodium());
				if (visitors.size() < this.maximumVisitors){
					visitors.add(newVisitor);
				}
				System.out.println("spawned visitor #" + visitors.size());
			}
		}
	}

    public void update(double deltaTime) {
    	this.elapsedDeltaTime += deltaTime * timeMultiplyer;
		while (elapsedDeltaTime > 20){
			this.runUpdates();
			this.elapsedDeltaTime -= 20;
		}
		if (elapsedDeltaTime < 0){
			elapsedDeltaTime = 0;
		}
		this.updateGui();
    }

    private void runUpdates(){
		if (visitors.size() < this.maximumVisitors){
			spawnVisitors(10);
		}
		if (visitors.size() > this.maximumVisitors){
			visitors.remove(0);
		}
		this.dataMap.generateFlowPaths(this.visitors, this.dataMap);
		this.updateGui();
		visitors.parallelStream().forEach(
			(visitor -> {
				visitor.update(visitors, map);
			})
		);
		this.updateSimulationClock();
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
            visitor.draw(graphics, getDebugDraw());
        }

        graphics.setTransform(new AffineTransform());
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    private void initPodiumObjectMap(){
    	podiumObjectMap = new HashMap<String, TiledObject>();
		TiledObject[] places = ((ObjectGroup)this.dataMap.getLayer("Places")).getObjects();
		for (TiledObject place : places){
			podiumObjectMap.put(place.getName(), place);
		}
	}

    public void updateSimulationClock(){
    	this.simulationTime = this.simulationTime.plusNanos(20000000*(long)clockMultiplyer);
    	ArrayList<Performance> currentPerformances = new ArrayList<Performance>();
    	double totalPopularity = 0;
    	for (Performance performance : this.festivalDay.getPerformances()){
    		if (performance.getStartTime().isBefore(simulationTime) && performance.getEndTime().isAfter(simulationTime)){
    			totalPopularity += performance.getPopularity();
    			currentPerformances.add(performance);
			}
		}
		int visitorIndex = 0;
    	visitors.parallelStream().forEach(
			(visitor -> {
				visitor.setTarget(null);
			})
		);
		for (Performance performance : currentPerformances){
			int fans = (int)Math.floor(performance.getPopularity() / totalPopularity * visitors.size());
			for (int i = visitorIndex; i < visitorIndex + fans; i++) {
				visitors.get(i).setTarget(
					this.podiumObjectMap.get(
						performance.getPodium().getName()
					)
				);
			}
		}
	}
}
