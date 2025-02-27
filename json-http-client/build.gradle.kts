import org.gradle.internal.os.OperatingSystem

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.25"
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
    id("groovy") 
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.4"
    id("io.micronaut.test-resources") version "4.4.4"
    id("io.micronaut.aot") version "4.4.4"
}
sourceSets {
    test {
        resources {
            srcDirs("src/test/resources", "../shared-resources")
        }
    }
}

version = "0.0.1"
group = "jonathan_zollinger"
val kotlinVersion= project.rootProject.properties["kotlinVersion"]
val projectReactorVersion = project.rootProject.properties["projectReactorVersion"]
val jakartaVersion = project.rootProject.properties["jakartaVersion"]
val groovySqlVersion = project.rootProject.properties["groovySqlVersion"]

repositories {
    mavenCentral()
}


dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.projectreactor:reactor-core:${projectReactorVersion}")
    implementation("jakarta.validation:jakarta.validation-api:$jakartaVersion")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("org.testcontainers:spock")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.apache.groovy:groovy-sql:${groovySqlVersion}")
}


application {
    mainClass = "jonathan_zollinger.ApplicationKt"
}
java {
}

tasks.test {
    if (OperatingSystem.current().isLinux) {
        environment("DOCKER_HOST", "unix:///run/user/1000/podman/podman.sock") //change for your user id
        environment("TESTCONTAINERS_RYUK_DISABLED", "true")
    }
}

graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("jonathan_zollinger.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

kotlin {
    jvmToolchain(17)
}
