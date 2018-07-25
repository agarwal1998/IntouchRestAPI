package com.mmi.output;

public class Result {
	private Integer DeviceType;
	private long working_count;
	private long nonworking_count;
	private long deactive_count;
	private long devicemodel;
	public void caldevicemodel() {
		devicemodel =  working_count +nonworking_count+ deactive_count;
	}
	public long getDevicemodel() {
		return devicemodel;
	}



	public void setDevicemodel(long devicemodel) {
		this.devicemodel = devicemodel;
	}



	public Result(Integer type) {
		DeviceType = type;
		working_count = 0;
		nonworking_count=0;
		deactive_count = 0;
	}
	


	public Integer getDeviceType() {
		return DeviceType;
	}



	public void setDeviceType(Integer deviceType) {
		DeviceType = deviceType;
	}



	public long getWorking_count() {
		return working_count;
	}
	public void setWorking_count(long working_count) {
		this.working_count = working_count;
	}
	public long getNonworking_count() {
		return nonworking_count;
	}
	public void setNonworking_count(long nonworking_count) {
		this.nonworking_count = nonworking_count;
	}
	public long getDeactive_count() {
		return deactive_count;
	}
	public void setDeactive_count(long active_count) {
		this.deactive_count = active_count;
	}
	
}
