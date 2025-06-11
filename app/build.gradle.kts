plugins {
    application
}

group = "io.github.crolang-p2p"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("io.github.crolang-p2p:crolang-p2p-node-jvm:0.1.7-alpha")
    implementation("com.google.code.gson:gson:2.10.1")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

fun registerExampleRunTask(exampleN: String, node: String) {
    tasks.register<JavaExec>("runEx${exampleN}${node}") {
        group = "application"
        classpath = sourceSets["main"].runtimeClasspath
        mainClass.set("examples.ex_${exampleN}.Ex_${exampleN}_${node}")
    }
}

registerExampleRunTask("1", "Alice")
registerExampleRunTask("1", "Bob")
registerExampleRunTask("2", "Alice")
