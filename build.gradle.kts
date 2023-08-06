import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.bundling.BootWar


tasks.getByName<BootJar>("bootJar") {
	enabled = true
	archiveBaseName.set("gigiScheduler")
}

tasks.getByName<BootWar>("bootWar") {
	enabled = true
	archiveBaseName.set("gigiScheduler")
}

plugins {
	java
	id("org.springframework.boot") version "2.7.12"
	id("io.spring.dependency-management") version "1.1.0"
	war
}

group = "com.ujo"
java.sourceCompatibility = JavaVersion.VERSION_11


repositories {
	mavenCentral();
	maven { setUrl("https://jitpack.io") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-web")

	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.batch:spring-batch-test")

	//log4jdbc
	implementation ("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")

	//logback discord
	implementation ("com.github.napstr:logback-discord-appender:1.0.0")

	//mybitis
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
	testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:2.1.3")

	//jsp
	implementation("org.apache.tomcat.embed:tomcat-embed-jasper")
	implementation("javax.servlet:jstl")

	//lombok
	implementation("org.projectlombok:lombok:1.18.26")
	annotationProcessor("org.projectlombok:lombok:1.18.12")

	//json
	implementation("com.googlecode.json-simple:json-simple:1.1.1")

	// web socket
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
