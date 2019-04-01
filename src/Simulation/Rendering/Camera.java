package Simulation.Rendering;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Camera {
	private Point2D centerPoint = new Point2D.Double(0,0);
	private double zoom = 1;
	private Point2D lastMousePos;
	private Canvas canvas;
	private Resizable resizable;
	private FXGraphics2D g2d;
	private Point2D dimensions;

	public Camera(Canvas canvas, Resizable resizable, FXGraphics2D g2d, Point2D dimensions) {
		this.canvas = canvas;
		this.resizable = resizable;
		this.g2d = g2d;
		this.dimensions = dimensions;

		canvas.setOnMousePressed(e -> {lastMousePos = new Point2D.Double(e.getX(), e.getY());});
		canvas.setOnMouseDragged(e -> mouseDragged(e));
		canvas.setOnScroll(e-> mouseScroll(e));
	}

	public AffineTransform getTransform()  {
		AffineTransform tx = new AffineTransform();
		tx.translate(canvas.getWidth()/2, canvas.getHeight()/2);
		tx.scale(zoom, zoom);
		tx.translate(centerPoint.getX(), centerPoint.getY());
		return tx;
	}

	public void mouseDragged(MouseEvent e) {
		if(e.getButton() == MouseButton.MIDDLE) {
			centerPoint = new Point2D.Double(
				centerPoint.getX() - (lastMousePos.getX() - e.getX()) / zoom,
				centerPoint.getY() - (lastMousePos.getY() - e.getY()) / zoom
			);

			if (centerPoint.getX() < canvas.getWidth()/2*-1-dimensions.getX()+32){
				centerPoint = new Point2D.Double(canvas.getWidth()/2*-1-dimensions.getX()+32, centerPoint.getY());
			}

			if (centerPoint.getY() < canvas.getHeight()/2*-1-dimensions.getY()+32){
				centerPoint = new Point2D.Double(centerPoint.getX(), canvas.getHeight()/2*-1-dimensions.getY()+32);
			}

			if (centerPoint.getX() > canvas.getWidth()/2-32){
				centerPoint = new Point2D.Double(canvas.getWidth()/2-32, centerPoint.getY());
			}

			if (centerPoint.getX() > canvas.getWidth()/2-32){
				centerPoint = new Point2D.Double(canvas.getWidth()/2-32, centerPoint.getY());
			}

			lastMousePos = new Point2D.Double(e.getX(), e.getY());
			resizable.draw(g2d);
		}
	}

	public void mouseScroll(ScrollEvent e) {
		zoom *= (1 + e.getDeltaY()/250.0f);
		if (zoom > 40){
			zoom = 40;
		}
		if (zoom < 0.02){
			zoom = 0.02;
		}
		resizable.draw(g2d);
	}
}
