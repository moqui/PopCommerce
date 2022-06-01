## POP (Plain-Old-Product) Commerce - Retail and Wholesale ERP and eCommerce

[![license](http://img.shields.io/badge/license-CC0%201.0%20Universal-blue.svg)](https://github.com/moqui/PopCommerce/blob/master/LICENSE.md)
[![build](https://travis-ci.org/moqui/PopCommerce.svg)](https://travis-ci.org/moqui/PopCommerce)
[![release](http://img.shields.io/github/release/moqui/PopCommerce.svg)](https://github.com/moqui/PopCommerce/releases)
[![commits since release](http://img.shields.io/github/commits-since/moqui/PopCommerce/v2.2.0.svg)](https://github.com/moqui/PopCommerce/commits/master)
[![downloads](http://img.shields.io/github/downloads/moqui/PopCommerce/total.svg)](https://github.com/moqui/PopCommerce/releases)

[![Discourse Forum](https://img.shields.io/badge/moqui%20forum-discourse-blue.svg)](https://forum.moqui.org)
[![Google Group](https://img.shields.io/badge/google%20group-moqui-blue.svg)](https://groups.google.com/d/forum/moqui)
[![LinkedIn Group](https://img.shields.io/badge/linked%20in%20group-moqui-blue.svg)](https://www.linkedin.com/groups/4640689)
[![Stack Overflow](https://img.shields.io/badge/stack%20overflow-moqui-blue.svg)](http://stackoverflow.com/questions/tagged/moqui)

POP Commerce is a simple eCommerce application and an admin app to administer the eCommerce site and manage various 
aspects of a retail or wholesale business, from customer service and fulfillment to accounting. The goal is for POP 
Commerce to include all functionality a general retail business might need.

While meant to be a usable and functional, the primary purpose is to be a easy to customize starting point for custom 
eCommerce sites and a demonstration of the use of Moqui Framework and Mantle Business Artifacts entities and services 
for building eCommerce applications.

POP Commerce is based on the Moqui Framework and Mantle Business Artifacts projects. It reuses various ERP screens from 
the SimpleScreens project. 

### Running POP Commerce

To run POP Commerce you need Moqui Framework, POP Commerce itself, and the components it depends on. Moqui supports a 
few methods for setup and deployment as described in the documentation here:

<http://www.moqui.org/docs/framework/Run+and+Deploy>

The easiest way to try POP Commerce is with the binary distribution available on GitHub:

<https://github.com/moqui/PopCommerce/releases>

If you don't have gradle or ant installed you can use this command line to run Java directly:

    $ java -jar moqui.war

### Build and Run Locally

To get and locally run the latest POP Commerce you'll need JDK 8 or later (OpenJDK or Oracle), and either a git client or you can 
use the binary download link on GitHub.

Java can be downloaded here (make sure to use the Download button under the **JDK** column, NOT the under the JRE column):

<http://www.oracle.com/technetwork/java/javase/downloads/index.html>

The following instructions use the Gradle Wrapper to build. You can optionally download and install Gradle 
(from <http://www.gradle.org/downloads>) and use **gradle** instead of **./gradlew** in the example commands.

To download Moqui/Mantle/PopCommerce source and build/run locally use the following steps:

#### Step 1: Download Moqui Framework

Zip: <https://github.com/moqui/moqui-framework/archive/master.zip>

Git: <https://github.com/moqui/moqui-framework.git>

From either source you should put the contents in a **moqui** directory for the next steps. If you use the Zip download 
change the directory name from **moqui-framework-master** to **moqui**. If you clone the Git repository clone it into 
a **moqui** directory. 

#### Step 2: Download POP Commerce and Dependencies

This is easy with the dependency configuration per component, and the Gradle get component tasks. With Gradle Wrapper 
you don't need to install Gradle separately to do this. The PopCommerce component is configured by default in the Moqui 
addons.xml file, so just run:

    $ ./gradlew getComponent -Pcomponent=PopCommerce

If you downloaded the zip archive for Moqui Framework this will download the zip archives for PopCommerce and each 
component it depends on. If you cloned from the git repository this will clone all components from their repositories. 

#### Step 3: Build and Load Data

From the **moqui** directory run:
 
    $ ./gradlew load

This will build Moqui and load seed and demo data from all components into an embedded H2 database.

#### Step 4: Run Moqui

From the **moqui** directory run:
 
    $ java -jar moqui.war

#### Step 5: Access the POP Commerce applications

For the eCommerce application, in your browser go to:

<http://localhost:8080/popc>

Or for the admin application go to:

<http://localhost:8080/vapps/PopcAdmin>

Use the button in the lower-left corner of the screen login as John Doe.

### Setup Commands Quick Reference

Java 8 JDK is required (OpenJDK or Oracle): <http://www.oracle.com/technetwork/java/javase/downloads/index.html>

Here are command line steps for initial checkout, setup, and run:

    $ git clone git@github.com:moqui/moqui-framework.git moqui
    $ cd moqui
    $ ./gradlew getComponent -Pcomponent=PopCommerce
    $ ./gradlew load
    $ java -jar moqui.war

Here are steps for a basic update (for development with clean out and rebuild of database):

    $ cd moqui
    $ ./gradlew cleanAll gitPullAll
    $ ./gradlew load
    $ java -jar moqui.war

To access the eCommerce app go to something like <http://localhost:8080/popc> in a web browser. To access the admin app 
go to <http://localhost:8080/vapps/PopcAdmin>.
