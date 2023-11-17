// allows to declare different plugins. Depends on the type of initialized application
plugins {
    id("java")
}

// mandatory attributes for each gradle project
group = "org.example"
version = "1.0-SNAPSHOT"


// repositories declaration
// Besides the most used maven central, another public/private could be declared as well as the local one
repositories {
    mavenCentral()
}

// dependencies. Be aware of the different types/scopes of dependencies
dependencies {
    implementation("org.jetbrains:annotations:24.0.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

// modifying test task by specifying the test framework we are about to use in the project. JUnit is the most popular one
tasks.test {
    useJUnitPlatform()
}

// modifying task 'jar' by adding main class to the manifest and packaging all the project dependencies into artifact
tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.example.Main"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

// modification of each task that has type 'JavaCompile'
// For example, build, jar, assemble, etc => each task that works with code compilation and packaging
tasks.withType<JavaCompile> {
    sourceCompatibility = JavaVersion.VERSION_11.toString()
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

// registration of our custom task
// Will appear in the IDE Gradle nav bar under 'other' group with the name declared here
tasks.register("myTestTask") {
    dependsOn("build")

    doLast {
        println("The custom task executed!")
    }
}
