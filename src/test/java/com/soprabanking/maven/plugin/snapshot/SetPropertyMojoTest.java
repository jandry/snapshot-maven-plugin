package com.soprabanking.maven.plugin.snapshot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SetPropertyMojoTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private MavenProject mavenProject;

    private SetPropertiesMojo mojo;

    @Before
    public void setup() {
        // Maven project conf
        mavenProject = new MavenProject();
        // Mojo conf
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("test", "snapshot", "release"));
        // Mojo
        mojo = new SetPropertiesMojo();
        mojo.project = mavenProject;
        mojo.properties = properties;
    }

    @Test
    public void testRelease() throws Exception {
        mavenProject.setVersion("1.0.0");
        // Execute
        mojo.execute();
        // Check
        assertFalse(Boolean.parseBoolean(mavenProject.getProperties().getProperty("isSnapshot")));
        assertEquals("release", mavenProject.getProperties().getProperty("test"));
    }

    @Test
    public void testSnapshot() throws Exception {
        mavenProject.setVersion("1.0.0-SNAPSHOT");
        // Execute
        mojo.execute();
        // Check
        assertTrue(Boolean.parseBoolean(mavenProject.getProperties().getProperty("isSnapshot")));
        assertEquals("snapshot", mavenProject.getProperties().getProperty("test"));
    }

    @Test
    public void testCheckParameter() throws Exception {
        exceptionRule.expect(MojoExecutionException.class);
        exceptionRule.expectMessage("Plugin useless without variables to set.");
        // List null
        mojo.properties = null;
        // Execute
        mojo.execute();
        // Empty list
        mojo.properties = new ArrayList<>();
        // Execute
        mojo.execute();
    }
}
