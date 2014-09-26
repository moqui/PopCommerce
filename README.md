
## POP (Plain-Old-Product) Commerce

"Commerce that Pops"

### Design Goals

The goal for POP Commerce is to be a simple ecommerce application for the sale
of basic products. It is not meant to feature more advanced ecommerce
functionality such as configurable products, products with selectable variants,
and so on.

While meant to be a usable and functional, the primary purpose is to be a easy
to customize starting point for custom ecommerce sites and a demonstration of
the use of Moqui Framework and Mantle Business Artifacts entities and services 
for building ecommerce applications.

The design is meant to be simple but full-featured and will be expanded over time.
One area of expansions is an admin app to administer the ecommerce site and manage
various aspects of a retail business, from customer service and fulfillment to 
accounting. The goal is for POP Commerce to include all functionality a general retail
business might need.

POP Commerce is based on the Moqui Framework and Mantle Business Artifacts projects.

### Running POP Commerce

To run POP Commerce you need Moqui Framework and Mantle Business Artifacts as
well as POP Commerce itself. Moqui supports a few methods for setup and
deployment as described in the documentation here:

http://www.moqui.org/framework/docs/RunDeploy.html

The easiest way to try POP Commerce is with the binary distribution available
on GitHub:

https://github.com/moqui/PopCommerce/releases

If you don't have gradle or ant installed you can use this command line to
run Java directly:

$ java -Xmx256M -XX:MaxPermSize=128m -jar moqui-${version}.war

### Build and Run Locally

To get the latest Moqui, Mantle, and POP Commerce and run locally you'll need
JDK 7 or later, Gradle 1.6 or later (1.12 recommended but anything in the 1 series, NOT the Gradle 2 series),
and either a git client or you can use the
"ZIP" download links on each project page on github. The github project
pages are at:

https://github.com/moqui/moqui
https://github.com/moqui/mantle
https://github.com/moqui/PopCommerce

Java can be downloaded here (make sure to use the Download button under
the "JDK" column, not the under the "JRE" column):

http://www.oracle.com/technetwork/java/javase/downloads/index.html

Gradle can be downloaded here (use a release before 2.0 in the Previous Releases section):

http://www.gradle.org/downloads

To download Moqui/Mantle/PopCommerce source and build/run locally use the
following steps:

#### Step 1: Download Moqui Framework

Zip: https://github.com/moqui/moqui/archive/master.zip
Git: git://github.com/moqui/moqui.git

From either source you should have a "moqui" directory for the next steps.
If you use the Zip download change the directory name from "moqui-master"
to "moqui".

#### Step 2: Download Mantle Business Artifacts

Zip: https://github.com/moqui/mantle/archive/master.zip
Git: git://github.com/moqui/mantle.git

Put the "mantle" directory in the "moqui/runtime" directory so it is
located at "moqui/runtime/mantle". If you use the Zip download change the
name of the directory from "mantle-master" to "mantle" to make sure the
directory under the "moqui/runtime" directory has the correct name.

#### Step 3: Download POP Commerce

Zip: https://github.com/moqui/PopCommerce/archive/master.zip
Git: git://github.com/moqui/PopCommerce.git

Put the "PopCommerce" directory in the "moqui/runtime/component" directory so
it is located at "moqui/runtime/component/PopCommerce". If you use the Zip
download change the name of the directory from "PopCommerce-master" to
"PopCommerce" to make sure the directory under the "moqui/runtime/component"
directory has the correct name.

#### Step 4: Build and load seed and demo data

From the "moqui" directory run "gradle load".

This will build Moqui and load data from Moqui, Mantle and POP Commerce into
an embedded Derby database.

#### Step 5: Run Moqui

From the "moqui" directory run "gradle run".

#### Step 6: Access the POP Commerce application

In your browser go to:

http://localhost:8080/popc


### Setup Commands Quick Reference

Java 7 is recommended: http://www.oracle.com/technetwork/java/javase/downloads/index.html

Gradle 1.12 recommended (use a release before 2.0 in the Previous Releases section): http://www.gradle.org/downloads

Here are command line steps, and you can do the same thing with a Git desktop app:

    $ git clone git@github.com:moqui/moqui.git moqui
    $ cd moqui/runtime/
    $ git clone git@github.com:moqui/mantle.git mantle
    $ cd component/
    $ git clone git@github.com:moqui/PopCommerce.git PopCommerce
    $ cd ../.. (to the moqui directory)
    $ gradle load
    $ gradle run

Here are steps for a basic update:

    $ cd moqui
    $ git pull
    $ cd runtime/mantle
    $ git pull
    $ cd ../component/PopCommerce
    $ git pull
    $ cd ../../.. (to the moqui directory)
    $ gradle cleanAll
    $ gradle load
    $ gradle run

To access PopCommerce go to something like "http://localhost:8080/popc" in a
web browser.

### Screen Outline

* Login Screen
    * Login Form
    * Simple New Account Form
* Customer
    * Profile (name, CC, address, phone)
    * Order History (summary of each order, no separate order detail screen)
* Product
    * Search Results
    * Category Listing
    * Product Detail
* Order
    * Cart
    * Checkout (One Page)
    * Confirmation
