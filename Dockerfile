FROM maven:3.0.4-jdk-8-alpine

ARG NEXUS_BUILD=05
ARG NEXUS_VERSION=2.8.0

VOLUME ["/target"]

COPY . /nexus-apt-plugin/

RUN sed -i "s/2.8.0-05/${NEXUS_VERSION}-${NEXUS_BUILD}/g" /nexus-apt-plugin/pom.xml
RUN echo -e '#!/bin/bash\nmvn -e package && rm -rf /root/.m2 && cp ./target/nexus-apt-plugin-*-SNAPSHOT.jar /target' >> /usr/bin/nexus-apt-plugin-build && \
    chmod 755 /usr/bin/nexus-apt-plugin-build

WORKDIR /nexus-apt-plugin

ENTRYPOINT ["/usr/bin/nexus-apt-plugin-build"]
