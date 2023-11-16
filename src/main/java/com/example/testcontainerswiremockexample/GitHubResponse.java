package com.example.testcontainerswiremockexample;

public record GitHubResponse(Issues issues, PullRequests pullRequests, Stargazers stargazers, Watchers watchers, Forks forks) {
	record Issues(int totalCount) {}

	record PullRequests(int totalCount) {}

	record Stargazers(int totalCount) {}

	record Watchers(int totalCount) {}

	record Forks(int totalCount) {}
}
