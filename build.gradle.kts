plugins {

	java
	id("org.springframework.boot") version "3.1.4" // 안정화된 호환 버전 사용
	id("io.spring.dependency-management") version "1.1.3" // 버전 호환 확인

}

group = "dudu"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
		vendor.set(JvmVendorSpec.AMAZON)  // Amazon Corretto 지정



	}
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
