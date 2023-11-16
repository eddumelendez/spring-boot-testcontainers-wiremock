package com.example.testcontainerswiremockexample;

import org.wiremock.integrations.testcontainers.WireMockContainer;

import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;

class WireMockContainerConnectionDetailsFactory extends ContainerConnectionDetailsFactory<WireMockContainer, GHConnectionDetails> {
	WireMockContainerConnectionDetailsFactory() {
	}

	protected GHConnectionDetails getContainerConnectionDetails(ContainerConnectionSource<WireMockContainer> source) {
		return new WireMockContainerConnectionDetails(source);
	}

	private static final class WireMockContainerConnectionDetails extends ContainerConnectionDetailsFactory.ContainerConnectionDetails<WireMockContainer> implements GHConnectionDetails {
		private WireMockContainerConnectionDetails(ContainerConnectionSource<WireMockContainer> source) {
			super(source);
		}


		@Override
		public String url() {
			return getContainer().getBaseUrl();
		}

		@Override
		public String token() {
			return "test-token";
		}
	}
}