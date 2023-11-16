plugins {
    id("java")
}

group = "ru.netology.qa"
version = "1.0-SNAPSHOT"

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("com.codeborne:selenide:6.17.1")
}

tasks.test {
    useJUnitPlatform()
    systemProperty("selenide.headless", System.getProperty("selenide.headless"))
}