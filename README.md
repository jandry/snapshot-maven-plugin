# snapshot-maven-plugin
**Set maven properties dynamically dependending if the project is a snapshot or release**

The plugin only provides one goal: set-property

This goal intends to 
- set isSnapshot property depending the project.version type: snapshot or release (-SNAPSHOT will set to true)
- define property with value that depends on version type. It define a different value for snapshot and release.

It can be combined with maven-deploy-plugin deploy-file goal to deploy in right repo.

Example configuration:

```
<plugin>
    <groupId>com.github.jandry.plugins</groupId>
    <artifactId>snapshot-maven-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
        <properties>
            <property>
                <name>deploy.repo.id</name>
                <snapshotValue>${project.distributionManagement.snapshotRepository.id}</snapshotValue>
                <releaseValue>${project.distributionManagement.repository.id}</releaseValue>
            </property>
            <property>
                <name>deploy.repo.url</name>
                <snapshotValue>${project.distributionManagement.snapshotRepository.url}</snapshotValue>
                <releaseValue>${project.distributionManagement.repository.url}</releaseValue>
            </property>
        </properties>
    </configuration>
    <executions>
        <execution>
            <id>test</id>
            <phase>validate</phase>
            <goals>
                <goal>set-properties</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Example configuration used in maven-deploy-plugin
```
<plugin>
    <artifactId>maven-deploy-plugin</artifactId>
    <configuration>
        <skip>true</skip>
    </configuration>
    <executions>
        <execution>
            <!-- deploy platform artifact -->
            <id>deploy-peng</id>
            <phase>deploy</phase>
            <goals>
                <goal>deploy-file</goal>
            </goals>
            <inherited>false</inherited>
            <configuration>
                <repositoryId>${deploy.repo.id}</repositoryId>
                <url>${deploy.repo.url}</url>
                <file>${project.build.directory}/myfile-${project.version}.tar.gz</file>
                <generatePom>false</generatePom>
                <artifactId>peng</artifactId>
                <groupId>${project.groupId}</groupId>
                <version>${project.version}</version>
                <classifier>unix</classifier>
                <packaging>tar.gz</packaging>
            </configuration>
        </execution>
    </executions>
</plugin>

```