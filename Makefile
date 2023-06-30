.PHONY: build push

### SETUP LOCAL_ENV ###
install-bibnotif:
	@echo \# Installing bib-notif-libs to local-repo 
	mvn install:install-file -Dfile=./libs/notification.jar -DgroupId=id.bukalapak -DartifactId=notification -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true

binary:
	@echo \# Get libs, build and install to local-repo
	gradle build
	cp build/libs/*.jar bin/ 
	#cp .envrc-local .envrc

prepare: binary

start:
	@echo \# Run App
	java -jar bin/poc-0.0.1-SNAPSHOT.jar


### DEPLOY TO GCP ###
export CI_REGISTRY ?= asia.gcr.io/cloudeng-prod-cicd
SVC_AUTH = rm-tools-api-auth
IMAGE_AUTH = $(CI_REGISTRY)/bib/rmtools/$(SVC_AUTH)

SVC_CRM = rm-tools-api-crm
IMAGE_CRM = $(CI_REGISTRY)/bib/rmtools/$(SVC_CRM)

SVC_BFF = rm-tools-api-bff
IMAGE_BFF = $(CI_REGISTRY)/bib/rmtools/$(SVC_BFF)

export VERSION ?= $(shell git show -q --format=%h)

build-auth:
	docker build --target authapi -t $(IMAGE_AUTH):$(VERSION) -f ./deploy/env.Dockerfile --build-arg CI_JOB_TOKEN="$(CI_JOB_TOKEN)" .
push-auth:
	docker push $(IMAGE_AUTH):$(VERSION)

build-crm:
	docker build --target crmapi -t $(IMAGE_CRM):$(VERSION) -f ./deploy/env.Dockerfile --build-arg CI_JOB_TOKEN="$(CI_JOB_TOKEN)" .
push-crm:
	docker push $(IMAGE_CRM):$(VERSION)

build-bff:
	docker build --target bffapi -t $(IMAGE_BFF):$(VERSION) -f ./deploy/env.Dockerfile --build-arg CI_JOB_TOKEN="$(CI_JOB_TOKEN)" .
push-bff:
	docker push $(IMAGE_BFF):$(VERSION)

build: build-auth build-crm build-bff
push: push-auth push-crm push-bff

