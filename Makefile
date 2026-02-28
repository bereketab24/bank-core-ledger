SHELL := /bin/bash

CYAN := \033[36m
RESET := \033[0m
VERSION ?=
.PHONY: help release

help: ## Show this help message
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

release: ## Build and Push docker image. Pass VERSION=vX.Y.Z or be prompted.
	@if [ -z "$(VERSION)" ]; then \
		read -p "Provide the version number in the format 'v0.0.1': " prompt_ver; \
		FINAL_VER=$$prompt_ver; \
	else \
		FINAL_VER="$(VERSION)"; \
	fi; \
	echo -e "$(CYAN)Building the image for tag: $$FINAL_VER...$(RESET)"; \
	docker build -t bereketab24/bank-core:$$FINAL_VER .; \
	echo -e "$(CYAN)Pushing the image...$(RESET)"; \
	docker push bereketab24/bank-core:$$FINAL_VER