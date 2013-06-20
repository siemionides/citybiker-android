package pl.citybikerandroid.domain;

public class Station {

	private String number;
	private String location;
	private int bicycles;
	private int stands;
	private String operator;
	private String district;
	private double x;
	private double y;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getBicycles() {
		return bicycles;
	}
	public void setBicycles(int bicycles) {
		this.bicycles = bicycles;
	}
	public int getStands() {
		return stands;
	}
	public void setStands(int stands) {
		this.stands = stands;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
}
