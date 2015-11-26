package demorw;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Car {
	private BigDecimal accel = new BigDecimal(5);
	private BigDecimal maxvel = new BigDecimal(100000);
	private BigDecimal minvel = new BigDecimal(100000);
	private BigDecimal initvel = new BigDecimal(0);
	private BigDecimal decel = new BigDecimal(10);
	private BigDecimal lastpos = new BigDecimal(0);
	private BigDecimal lastvel = new BigDecimal(0);
	private boolean maxed = false;

	public Car(double accel, double maxvel, double initvel, double decel) {
		this.accel = BigDecimal.valueOf(accel);
		this.maxvel = BigDecimal.valueOf(maxvel);
		this.initvel = BigDecimal.valueOf(initvel);
		this.decel = BigDecimal.valueOf(-decel);
		lastvel = this.initvel;
		
	}

	public double accel(double deltat) {
		BigDecimal dt = BigDecimal.valueOf(deltat);
		if (!maxed) {
			if (lastvel.compareTo(maxvel) == 0) {
				lastpos = lastpos.add(maxvel.multiply(dt));
				return lastpos.doubleValue();
			} else {
				BigDecimal currentvel = BigDecimal.valueOf(velocitya(deltat));
				if (currentvel.compareTo(maxvel) > 0) {
					BigDecimal ttmax = new BigDecimal(0);
					ttmax=(maxvel.subtract(lastvel)).divide(accel,RoundingMode.HALF_UP);
					lastpos = (lastpos.add(lastvel.multiply(ttmax))
							.add(BigDecimal.valueOf(0.5d).multiply(accel).multiply(ttmax.pow(2))));
					lastvel=maxvel;
					
					maxed=true;
					BigDecimal dt2=dt.subtract(ttmax);
					lastpos = (lastpos.add(lastvel.multiply(dt2)));
					return lastpos.doubleValue();
				}
				lastpos = (lastpos.add(lastvel.multiply(dt))
						.add(BigDecimal.valueOf(0.5d).multiply(accel).multiply(dt.pow(2))));
				lastvel = BigDecimal.valueOf(velocitya(deltat));
				if (lastvel.compareTo(maxvel) >= 0) {
					maxed = true;
					lastvel = maxvel;
				}
				return lastpos.doubleValue();
			}
		} else {
			lastpos = lastpos.add(maxvel.multiply(dt));
			return lastpos.doubleValue();
		}

	}
	public double decel(double deltat) {
		BigDecimal dt = BigDecimal.valueOf(deltat);
		if (!maxed) {
			if (lastvel.compareTo(maxvel) == 0) {
				lastpos = lastpos.add(maxvel.multiply(dt));
				return lastpos.doubleValue();
			} else {
				BigDecimal currentvel = BigDecimal.valueOf(velocityd(deltat));
				if (currentvel.compareTo(maxvel) > 0) {
					BigDecimal ttmax = new BigDecimal(0);
					ttmax=(maxvel.subtract(lastvel)).divide(decel,RoundingMode.HALF_UP);
					lastpos = (lastpos.add(lastvel.multiply(ttmax))
							.add(BigDecimal.valueOf(0.5d).multiply(decel).multiply(ttmax.pow(2))));
					lastvel=maxvel;
					
					maxed=true;
					BigDecimal dt2=dt.subtract(ttmax);
					lastpos = (lastpos.add(lastvel.multiply(dt2)));
					
					return lastpos.doubleValue();
				}
				
				lastpos = (lastpos.add(lastvel.multiply(dt))
						.add((BigDecimal.valueOf(0.5d).multiply(decel).multiply(dt.pow(2)))));
				
				lastvel = BigDecimal.valueOf(velocityd(deltat));
				if (lastvel.compareTo(maxvel) >= 0) {
					maxed = true;
					lastvel = maxvel;
				}
				
				return lastpos.doubleValue();
			}
		} else {
			
			lastpos = lastpos.add(maxvel.multiply(dt));
			return lastpos.doubleValue();
		}

	}
	public double velocitya(double deltat) {
		BigDecimal dt = BigDecimal.valueOf(deltat);
		return lastvel.add(dt.multiply(accel)).doubleValue();
	}
	public double velocityd(double deltat) {
		BigDecimal dt = BigDecimal.valueOf(deltat);
		return lastvel.add(dt.multiply(decel)).doubleValue();
		
	}
	public static void main(String[] args) {
		Car car = new Car(0,100, 10, 5);
		System.out.println(car.decel(1));
		System.out.println(car.decel(1));
		System.out.println(car.decel(1));
	}
}