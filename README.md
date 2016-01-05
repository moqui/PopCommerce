
## POP (Plain-Old-Product) Commerce - Retail and Wholesale ERP and eCommerce

[![license](http://img.shields.io/badge/license-CC0%201.0%20Universal-blue.svg)](https://github.com/moqui/PopCommerce/blob/master/LICENSE.md)
[![release](http://img.shields.io/github/release/moqui/PopCommerce.svg)](https://github.com/moqui/PopCommerce/releases)
[![commits since release](http://img.shields.io/github/commits-since/moqui/PopCommerce/v1.0.0.svg)](https://github.com/moqui/PopCommerce/commits/master)
[![downloads](http://img.shields.io/github/downloads/moqui/PopCommerce/latest/total.svg)](https://github.com/moqui/PopCommerce/releases)

POP Commerce is a simple eCommerce application and an 
admin app to administer the eCommerce site and manage various aspects of a 
retail or wholesale business, from customer service and fulfillment
to accounting. The goal is for POP Commerce to include all functionality a 
general retail business might need.

While meant to be a usable and functional, the primary purpose is to be a easy
to customize starting point for custom eCommerce sites and a demonstration of
the use of Moqui Framework and Mantle Business Artifacts entities and services 
for building eCommerce applications.

POP Commerce is based on the Moqui Framework and Mantle Business Artifacts 
projects. It reuses various ERP screens from the SimpleScreens project. 

### Running POP Commerce

To run POP Commerce you need Moqui Framework, Mantle Business Artifacts, 
and SimpleScreens as well as POP Commerce itself. Moqui supports a few methods 
for setup and deployment as described in the documentation here:

<http://www.moqui.org/framework/docs/RunDeploy.html>

The easiest way to try POP Commerce is with the binary distribution available
on GitHub:

<https://github.com/moqui/PopCommerce/releases>

If you don't have gradle or ant installed you can use this command line to
run Java directly:

$ java -Xmx512M -XX:MaxPermSize=128m -jar moqui-${version}.war

Note that with JDK 8 you don't need the -XX:MaxPermSize argument.

### Build and Run Locally

To get and locally run the latest Moqui, Mantle, SimpleScreens, and POP 
Commerce you'll need JDK 7 or later (Oracle JDK 8 recommended), and either
a git client or you can use the **ZIP** download links on each project page 
on GitHub. The GitHub project pages are at:

<https://github.com/moqui/moqui>
<https://github.com/moqui/mantle>
<https://github.com/moqui/SimpleScreens>
<https://github.com/moqui/PopCommerce>

Java can be downloaded here (make sure to use the Download button under
the **JDK** column, not the under the **JRE** column):

<http://www.oracle.com/technetwork/java/javase/downloads/index.html>

The following instructions use the Gradle Wrapper to build. You can 
optionally download and install Gradle (from <http://www.gradle.org/downloads>)
and use **gradle** instead of **./gradlew** in the example commands.

To download Moqui/Mantle/PopCommerce source and build/run locally use the
following steps:

#### Step 1: Download Moqui Framework

Zip: <https://github.com/moqui/moqui/archive/master.zip>

Git: <git://github.com/moqui/moqui.git>

From either source you should have a **moqui** directory for the next steps.
If you use the Zip download change the directory name from **moqui-master**
to **moqui**.

#### Step 2: Download Mantle Business Artifacts

Zip: <https://github.com/moqui/mantle/archive/master.zip>

Git: <git://github.com/moqui/mantle.git>

Put the **mantle** directory in the **moqui/runtime** directory so it is
located at **moqui/runtime/mantle**. If you use the Zip download change the
name of the directory from **mantle-master** to **mantle** to make sure the
directory under the **moqui/runtime** directory has the correct name.

#### Step 3: Download SimpleScreens

Zip: <https://github.com/moqui/SimpleScreens/archive/master.zip>

Git: <git://github.com/moqui/SimpleScreens.git>

Put the **SimpleScreens** directory in the **moqui/runtime/component** directory so
it is located at **moqui/runtime/component/SimpleScreens**. If you use the Zip
download change the name of the directory from **SimpleScreens-master** to
**SimpleScreens** to make sure the directory under the **moqui/runtime/component**
directory has the correct name.

#### Step 4: Download POP Commerce

Zip: <https://github.com/moqui/PopCommerce/archive/master.zip>

Git: <git://github.com/moqui/PopCommerce.git>

Put the **PopCommerce** directory in the **moqui/runtime/component** directory so
it is located at **moqui/runtime/component/PopCommerce**. If you use the Zip
download change the name of the directory from **PopCommerce-master** to
**PopCommerce** to make sure the directory under the **moqui/runtime/component**
directory has the correct name.

#### Step 5: Build and load seed and demo data

From the **moqui** directory run **./gradlew load**.

This will build Moqui and load data from Moqui, Mantle, SimpleScreens, and POP 
Commerce into an embedded H2 database.

#### Step 6: Run Moqui

From the **moqui** directory run **./gradlew run**.

#### Step 7: Access the POP Commerce application

For the eCommerce application, in your browser go to:

<http://localhost:8080/popc>

Or for the admin application go to:

<http://localhost:8080/apps/PopcAdmin>

Use the button in the lower-left corner of the screen login as John Doe.

### Setup Commands Quick Reference

Java 8 is recommended: <http://www.oracle.com/technetwork/java/javase/downloads/index.html>

Here are command line steps, and you can do the same thing with a Git desktop app:

    $ git clone git@github.com:moqui/moqui.git
    $ cd moqui/runtime/
    $ git clone git@github.com:moqui/mantle.git
    $ cd component/
    $ git clone git@github.com:moqui/SimpleScreens.git
    $ git clone git@github.com:moqui/PopCommerce.git
    $ cd ../.. (to the moqui directory)
    $ ./gradlew load
    $ ./gradlew run

Here are steps for a basic update:

    $ cd moqui
    $ git pull
    $ cd runtime/mantle
    $ git pull
    $ cd ../component/SimpleScreens
    $ git pull
    $ cd ../PopCommerce
    $ git pull
    $ cd ../../.. (to the moqui directory)
    $ ./gradlew cleanAll
    $ ./gradlew load
    $ ./gradlew run

To access the eCommerce app go to something like <http://localhost:8080/popc> in a
web browser. To access the admin app go to <http://localhost:8080/PopcAdmin>.
