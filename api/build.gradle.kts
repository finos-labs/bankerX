plugins {
    id("scala")
}

group = "bankerX"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.14")
    // testImplementation("org.scalatest:scalatest_2.13:3.2.10")
    // testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// tasks {
//     withType<Test> {
//         useJUnitPlatform()
//     }
// }

sourceSets {
    main {
        scala.srcDirs("src/main/scala", "build/generated/sources/scala")
    }
    test {
        scala.srcDirs("src/test/scala")
    }
}