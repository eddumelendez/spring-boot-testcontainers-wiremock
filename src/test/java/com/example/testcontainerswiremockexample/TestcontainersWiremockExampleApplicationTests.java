package com.example.testcontainerswiremockexample;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

@SpringBootTest
@Testcontainers
class TestcontainersWiremockExampleApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger("wiremock");

	@Container
	@ServiceConnection
	static WireMockContainer wireMock = new WireMockContainer("wiremock/wiremock:3.2.0-alpine")
			.withMapping("graphql", TestcontainersWiremockExampleApplicationTests.class, "graphql-resource.json")
			.withExtensions("graphql",
					List.of("io.github.nilwurtz.GraphqlBodyMatcher"),
					List.of(Paths.get("target", "test-wiremock-extension", "wiremock-graphql-extension-0.7.1-jar-with-dependencies.jar").toFile()))
			.withFileFromResource("testcontainers-java.json", TestcontainersWiremockExampleApplicationTests.class, "testcontainers-java.json")
			.withLogConsumer(new Slf4jLogConsumer(LOGGER));

	@Autowired
	private GHService ghService;

	@Test
	void contextLoads() {
		var variables = Map.<String, Object>of("owner", "testcontainers", "name", "testcontainers-java");
		StepVerifier.create(this.ghService.getStats(variables))
				.expectNext(new GitHubResponse(
						new GitHubResponse.Issues(385),
						new GitHubResponse.PullRequests(90),
						new GitHubResponse.Stargazers(6560),
						new GitHubResponse.Watchers(142),
						new GitHubResponse.Forks(1295)))
				.verifyComplete();
	}

}
