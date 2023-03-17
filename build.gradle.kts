plugins {
    id("com.adarshr.test-logger")
    id("io.gitlab.arturbosch.detekt")
    id("io.spring.dependency-management")
    id("org.jetbrains.dokka")
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.allopen")
    kotlin("plugin.jpa")
    kotlin("plugin.spring")

}

group = "org.powermilk"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_19
java.targetCompatibility = JavaVersion.VERSION_19

val detektVersion: String by project
val dokkaVersion: String by project
val flywayVersion: String by project
val gitPropertiesPluginVersion: String by project
val jacksonVersion: String by project
val junitVersion: String by project
val kotlinLoggingVersion: String by project
val kotlinVersion: String by project
val openApiDocVersion: String by project
val oktaVersion: String by project
val springBootVersion: String by project
val springMockkVersion: String by project
val testContainersVersion: String by project
val testLoggerVersion: String by project

// class main name
val classMainName = "org.powermilk.quiz.QuizApplicationKt"

repositories {
    google()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation(kotlin("allopen"))
    implementation(kotlin("noarg"))
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    kapt("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("com.ninja-squad:springmockk:$springMockkVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

// configuration for excluding
configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
        exclude("org.slf4j", "slf4j-log4j12")
        exclude("log4j", "log4j")
        exclude("org.junit.vintage", "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
}

// all open configuration
allOpen {
    annotation("org.junit.jupiter.api.extension.ExtendWith")
}

// detekt configuration
detekt {
    source = files("src/main/kotlin")
    config = files("$projectDir/detekt.yml")
    autoCorrect = true
}

kapt {
    useBuildCache = false
}

testlogger {
    showStackTraces = false
    showFullStackTraces = false
    showCauses = false
    @Suppress("MagicNumber")
    slowThreshold = 10000
    showSimpleNames = true
}


tasks {
	// compile kotlin configuration
	compileKotlin {
		kotlinOptions {
			allWarningsAsErrors = true // report an error if there are any warnings
			verbose = true // enable verbose logging output
			jvmTarget = java.targetCompatibility.toString() // target version of the generated JVM bytecode
			freeCompilerArgs = listOf("-Xjsr305=strict") // list of additional compiler arguments
		}
	}

	// compile kotlin tests configuration
	compileTestKotlin {
		kotlinOptions {
			verbose = true // enable verbose logging output
			jvmTarget = java.targetCompatibility.toString() // target version of the generated JVM bytecode
			freeCompilerArgs = listOf("-Xjsr305=strict") // list of additional compiler arguments
		}
	}

	// detekt configuration
	detekt.configure {
		reports {
			xml.required.set(false) // set XML report generation
			txt.required.set(false) // set TXT report generation
			sarif.required.set(false)  // set Sarif report generation
			html.required.set(true) // set HTML report generation
			html.outputLocation.set(file("$buildDir/reports/detekt/detekt.html")) // set HTML report output location
		}
	}

	// dokka configuration
	dokkaHtml {
		outputDirectory.set(buildDir.resolve("dokka")) // output directory of dokka documentation.
		// source set configuration.
		dokkaSourceSets {
			named("main") { // source set name.
				jdkVersion.set(java.targetCompatibility.toString().toInt()) // Used for linking to JDK documentation
				skipDeprecated.set(false) // Add output to deprecated members. Applies globally, can be overridden by packageOptions
				includeNonPublic.set(true) // non-public modifiers should be documented
			}
		}
	}

    test {
        useJUnitPlatform()
    }
}
