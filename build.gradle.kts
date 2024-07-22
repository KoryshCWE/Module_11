plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation ("org.thymeleaf:thymeleaf:3.0.12.RELEASE")
    implementation ("org.thymeleaf:thymeleaf-spring5:3.0.12.RELEASE")
    implementation ("org.eclipse.jetty:jetty-server:9.4.38.v20210224")
    implementation ("org.eclipse.jetty:jetty-servlet:9.4.38.v20210224")

}

tasks.test {
    useJUnitPlatform()
}