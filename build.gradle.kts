plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dudu"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
		vendor.set(JvmVendorSpec.ADOPTIUM) // Adoptium 설정


	}
}

// Gradle에 다운로드 리포지토리 설정 추가
tasks.withType<JavaCompile>().configureEach {
	options.isFork = true
}


tasks.withType<JavaCompile> {
	options.compilerArgs.add("-parameters")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	//머스태쳐 템플릿
	implementation("org.springframework.boot:spring-boot-starter-mustache")
	//시큐리티 oauth2
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")


	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.h2database:h2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.security:spring-security-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
