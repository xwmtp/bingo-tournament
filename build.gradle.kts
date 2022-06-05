import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {

  kotlin("jvm") version "1.6.21"

  id("org.springframework.boot") version "2.7.0"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("plugin.spring") version "1.6.21"
  kotlin("plugin.jpa") version "1.6.21"

  id("org.openapi.generator") version "5.4.0"
}

group = "com.github.xwmtp"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17

  sourceSets {
    main {
      java {
        srcDir("$buildDir/generated/api")
      }
    }
  }
}

repositories {
  mavenCentral()
}

dependencies {

  implementation("org.springframework.boot:spring-boot-starter-web") {
    exclude(module = "spring-boot-starter-json")
  }
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
  implementation("org.springframework.session:spring-session-jdbc")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  implementation("com.google.code.gson:gson:2.9.0")

  implementation("io.springfox:springfox-swagger-ui:3.0.0")
  implementation("io.springfox:springfox-oas:3.0.0")
  implementation("javax.validation:validation-api:2.0.1.Final")

  runtimeOnly("com.h2database:h2")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {

  dependsOn += "openApiGenerate"

  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

sourceSets {
  main {

  }
}

openApiGenerate {

  generatorName.set("kotlin-spring")
  inputSpec.set("$rootDir/api/bingo-tournament.yaml")
  outputDir.set("$buildDir/generated/api")

  modelPackage.set("com.github.xwmtp.api.model")
  apiPackage.set("com.github.xwmtp.api")

  configOptions.set(mapOf(
      "interfaceOnly" to "true",
      "sourceFolder" to "",
      "gradleBuildFile" to "false",
      "serializationLibrary" to "gson",
      "enumPropertyNaming" to "original",
  ))
}

tasks.create<GenerateTask>("openApiNpm") {

  group = "openapi tools"

  generatorName.set("typescript-fetch")
  inputSpec.set("$rootDir/api/bingo-tournament.yaml")
  outputDir.set("$rootDir/npm")

  configOptions.set(mapOf(
      "npmName" to "@xwmtp/bingo-tournament",
  ))
}
