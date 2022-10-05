# Robocode Robots Wrapper

## About the wrapper

The Robots Wrapper is used to wrapping existing Robocode robot jar files making it possible to run those inside Robocode
Tank Royale.

The wrapper works by creating a Tank Royale specific bot directory beside the robot jar file containing:

- A JSON configuration file with robot information extracted from the robot jar file.
- Script files for starting the robot for Tank Royale.
- A `Wrapper.java` with a main entry for running the robot as an executable that makes use of
  the [Robocode API adapter library].

## Usage

The robots wrapper takes a single argument which is a file path of a directory containing one or more robot jar files
identical to the `robots` folder inside the `robocode` home directory.

Usage:

```
java -jar robots-wrapper-x.y.z.jar <robots-dir>
```

Example (for Windows):

```
java -jar robots-wrapper-0.1.1.jar "C:\robocode\robots"
```

### Important note

Note that you need to create a `lib` folder inside the robots directory prior to running the robots wrapper.
And you need to copy the `robocode-api-x.y.z.jar` library (from the [Robocode API adapter library]) into the `lib`
directory. The script files in the created bot directories is depending on this library.

## Build commands

#### Clean build directory:

```shell
gradle clean
```

#### Build/compile artifact:

```shell
gradle build
```

The generated library (Robots Wrapper) can be found under `/build/libs` named `robots-wrapper-x.y.z.jar`.


[Robocode API adapter library]: ../robocode-api "Robocode API adapter library for Tank Royale"