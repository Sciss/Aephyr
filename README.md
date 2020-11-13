# TreeTable

[![Build Status](https://travis-ci.org/Sciss/TreeTable.svg?branch=main)](https://travis-ci.org/Sciss/TreeTable)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.sciss/treetable-scala_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/de.sciss/treetable-scala_2.13)

A TreeTable component for Swing. This started as a fork/clone from the Java project
at [code.google.com/p/aephyr](https://code.google.com/p/aephyr/) -- "Swing components, accessories, utilities, 
etc for use in a GUI", released under the GNU LGPL v3+. The original author was `drael4...@gmail.com`.

The Java component, in sub project `java`, is accompanied by Scala-Swing component, in sub project `scala`.

All modifications, extensions and Scala project (C)opyright 2013&ndash;2020 by Hanns Holger Rutz. New project
released under the GNU LGPL v3+.

## example

An example is currently included in the test sources of the Java sub project:

    $ sbt treetable-java/test:run

## linking

To use the library:

    "de.sciss" %  "treetable-java"  % v  // Java only component
    "de.sciss" %% "treetable-scala" % v  // Scala component

The current version `v` is `"1.6.1"`

## building

This project builds with sbt against Scala 2.13, 2.12, Dotty. The last version to support Scala 2.11 was 1.5.1.

## contributing

Please see the file [CONTRIBUTING.md](CONTRIBUTING.md)

## known issues

There is a problem with the Java project, resulting in a `java.lang.reflect.GenericSignatureFormatError` for a
fresh compilation. Just run the `compile` task again, and thanks to sbt incremental compilation this second run
will pass.
