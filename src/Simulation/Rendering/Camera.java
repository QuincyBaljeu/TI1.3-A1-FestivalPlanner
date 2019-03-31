package Simulation.Rendering;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;

public class Camera {
	private double offSetX;
	private double offSetY;
	private double zoom;

	public Camera(){
		this.offSetX = 0;
		this.offSetY = 0;
		this.zoom = 1;
	}

	public double getOffSetX() {
		return offSetX;
	}

	public void setOffSetX(double offSetX) {
		this.offSetX = offSetX;
	}

	public double getOffSetY() {
		return offSetY;
	}

	public void setOffSetY(double offSetY) {
		this.offSetY = offSetY;
	}

	public void applyTransform(FXGraphics2D g){
		g.setTransform(getTransform());
	}
	public AffineTransform getTransform(){
		AffineTransform at = new AffineTransform();
		at.translate(offSetX, offSetY);
		at.scale(zoom, zoom);
		return at;
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
}
