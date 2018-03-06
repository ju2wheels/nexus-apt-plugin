FROM maven:3-jdk-8-alpine

VOLUME [/target]

COPY . /nexus-apt-plugin/
RUN  echo -e '#!/bin/bash\nmvn -e package && rm -rf /root/.m2 && cp ./target/nexus-apt-plugin-*-SNAPSHOT.jar /target' >> /usr/bin/nexus-apt-plugin-build && \
     chmod 755 /usr/bin/nexus-apt-plugin-build

WORKDIR /nexus-apt-plugin

ENTRYPOINT ["/usr/bin/nexus-apt-plugin-build"]
