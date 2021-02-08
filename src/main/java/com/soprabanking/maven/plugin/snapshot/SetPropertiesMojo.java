package com.soprabanking.maven.plugin.snapshot;

import java.util.List;

import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "set-properties", defaultPhase = LifecyclePhase.VALIDATE, requiresProject = true, threadSafe = true)
public class SetPropertiesMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}", readonly = false, required = true)
	protected org.apache.maven.project.MavenProject project;

	@Parameter
	protected List<Property> properties;

	public void execute() throws MojoExecutionException, MojoFailureException {
		String version = project.getVersion();
		//
		checkParameters();
		//
		Boolean isSnapshot = ArtifactUtils.isSnapshot(version);
		// Set isSnapshot
		project.getProperties().setProperty("isSnapshot", isSnapshot.toString().toLowerCase());
		getLog().info("Property 'isSnapshot' is set to '" + isSnapshot + "'");
		// Set properties
		if (isSnapshot) {
			properties.stream().forEach(t -> loadProperty(t.getName(), t.getSnapshotValue()));
		} else {
			properties.stream().forEach(t -> loadProperty(t.getName(), t.getReleaseValue()));
		}
	}

	private void loadProperty(String name, String value) {
		project.getProperties().setProperty(name, value);
		getLog().info("Property '" + name + "' set to '" + value + "'");
	}

	private void checkParameters() throws MojoExecutionException {
		if (properties == null || properties.size() <= 0) {
			throw new MojoExecutionException("Plugin useless without variables to set.");
		}
	}

}
