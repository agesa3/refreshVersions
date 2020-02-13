import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("de.fayard.refreshVersions").version("0.8.4")
    id("com.gradle.plugin-publish").version("0.10.1")
    `java-gradle-plugin`
    `maven-publish`
    `kotlin-dsl`
}


version = file("plugins_version.txt").readLines().first()
group = "de.fayard"


gradlePlugin {
    plugins {
        create("dependencies") {
            id = "de.fayard.dependencies"
            displayName = "Typesafe Gradle Dependencies"
            description = "Common Gradle dependencies - See gradle refreshVersions"
            implementationClass = "de.fayard.dependencies.DependenciesPlugin"
        }
    }
}

tasks.register<DefaultTask>("hello") {
    group = "Custom"
    description = "Minimal task that do nothing. Useful to debug a failing build"
}

repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
}

pluginBundle {
    website = "https://builtwithgradle.netlify.com/"
    vcsUrl = "https://github.com/jmfayard/gradle-dependencies-plugins"
    tags = listOf("dependencies", "versions", "buildSrc", "kotlin", "kotlin-dsl")
}

dependencies {
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.9")

    testImplementation(platform(notation = "org.junit:junit-bom:5.6.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("allows tests to run from IDEs that bundle older version of launcher")
    }

    implementation(gradleKotlinDsl())
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
