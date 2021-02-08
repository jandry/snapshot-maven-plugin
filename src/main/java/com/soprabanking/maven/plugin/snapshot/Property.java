package com.soprabanking.maven.plugin.snapshot;

public class Property {

	private String name;
	private String snapshotValue;
	private String releaseValue;
	
	public String getName() {
		return name;
	}

	public String getSnapshotValue() {
		return snapshotValue;
	}

	public String getReleaseValue() {
		return releaseValue;
	}

	public Property() {}

	public Property(String name, String snapshotValue, String releaseValue) {
		this.name = name;
		this.snapshotValue = snapshotValue;
		this.releaseValue = releaseValue;
	}
}
