package me.ilya.schedule;


import java.time.LocalTime;

class ClassSchedule {
	private LocalTime startTime;
	private LocalTime endTime;
	private String className;

	public ClassSchedule(LocalTime startTime, LocalTime endTime, String className) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.className = className;
	}

	public boolean isClassInProgress(LocalTime currentTime) {
		return !currentTime.isBefore(startTime) && currentTime.isBefore(endTime);
	}

	public String getClassName() {
		return className;
	}

	public String getClassTimeRange() {
		return this.startTime + "-" + this.endTime;
	}
}

