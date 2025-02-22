plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("jacoco")
    alias(libs.plugins.jacocolog)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(libs.exp4j)

    testImplementation(kotlin("test"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
    }
}
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}
tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}