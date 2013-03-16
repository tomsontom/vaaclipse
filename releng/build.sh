#!/bin/sh

ECLIPSE_INSTALL=~/Applications/Eclipse/eclipse-0.8.1
LAUNCHER_VERSION=1.3.0.v20120522-1813
VAADIN_VERSION=6.8.1.dev-20121129

rm -rf /tmp/vaadin-target
rm -rf /tmp/vaadin-repository
mkdir -p /tmp/vaadin-target/plugins
cp ../org.semanticsoft.vaaclipse.targetdef/bundles/com.vaadin_$VAADIN_VERSION.jar /tmp/vaadin-target/plugins 
cp ../org.semanticsoft.vaaclipse.vaadinaddons/target/org.semanticsoft.vaaclipse.vaadinaddons-1.0.0.M2.jar /tmp/vaadin-target/plugins

java -jar $ECLIPSE_INSTALL/plugins/org.eclipse.equinox.launcher_$LAUNCHER_VERSION.jar \
  -application org.eclipse.equinox.p2.publisher.FeaturesAndBundlesPublisher \
  -metadataRepository file:/tmp/vaadin-repository  \
  -artifactRepository file:/tmp/vaadin-repository  \
  -source /tmp/vaadin-target -compress -publishArtifacts

#mvn clean package