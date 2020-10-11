![](screenshots/logo.png?raw=true "Music-VisualizerV2 Logo")

## Synopsis
The prodigal Music Visualizer returns, and it's even better. Version 2 of Music-Visualizer is finally here!
V2 brings a much better codebase and more ease for addition, and will only improve over time.

---

## Active/Current Issues
1. Unfortunately, right now there's an issue with running the ```.jar``` on Mac OSX. When trying to run using JDK 1.8, 11 (LTS), or higher, the application is launched and can be seen in the Dock, and then withing a few seconds it crashes. Once it crashes, it then gives an error window with this or similar message: ```The Java JAR file "Music Visualizer-V2.jar" could not be launched. Check the console for possible error messages.```.
    - When running the jar from a terminal, the error message is: ```java.lang.NoClassDefFoundError: com/apple/eawt/QuitHandler```

---

## Releases?
Yes, yes, I know! Unlike the previous version, for now, V2 releases of the ```.jar``` will be a stand-alone player. SoundCloud integration will be hidden unless a valid SoundCloud ```ClientId``` is set via environment variable. 

To protect the ```ClientId``` which SoundCloud uses to authenticate requests to its API, the best way (I've found for now) from it being decompiled out of the ```.jar``` is to set an environment variable and retrieve it at run time.

---

## Working with the Source

### What you'll need to build and run from the source code
```
Java JDK + IntelliJ + Kotlin
```
#### The codebase is an IntelliJ IDEA project, so I'd recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/). This project also uses [Kotlin](https://kotlinlang.org/?fromMenu), which is an incredible language! I'd also recommend using the ```AdoptOpenJDK 11 (LTS)```: [AdoptOpenJDK - Windows, Mac, Linux](https://adoptopenjdk.net/releases.html?variant=openjdk11&jvmVariant=hotspot)

### Project Needed Setup

#### SoundCloud ```ClientId``` Token:
- To avoid the token being packaged into the ```.jar``` during the Maven package Lifecycle, for now, I thought setting an environment variable would be the best way to secure the token.

- I've created a convenience scripts for setting the environment variable! In a terminal window change into the ```tools``` folder of this repo, then execute the script for your OS:
    - Note: Replace ```<client-id>``` with your actual SoundCloud ClientId

#### For Windows
1. ```
   ./tokenhelper.ps1 <client-id>
   ```

#### For Mac and Linux
1. ```python
   ./tokenhelper.sh <client-id>
   ```
   1. Note: If you mess up setting the token, you'll need to manually edit your ```.bashrc``` file to correct the export value

2. And Boom! You should be all set to go!

#### Maven Setup
- If using [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE, when opening the project, hit the popup about the ```pom.xml``` file that says something like: ```Add as Maven Project```
    - This will make sure IntelliJ recognizes the project as being a Maven project!

### Maven Tools
- To build the .jar, in the Maven Lifecycle, use the ```package``` command
- To install local needed dependencies for .jars in the ```lib``` folder, and to clean the project ('target' in root project dir) use the ```clean``` command

---

## Issues? Features? More???

Create an ```issue``` for the repo if you encounter any errors or strange behavior/functionality with the application. 
Please feel free to fork and submit bug fixes or feature requests!

---

## Errors / Gotchas?
1. If you're using IntelliJ, if you happen to get this exception: ```NoClassFoundException - Main```, try the following:
    1. Close the project
    2. In the root of the repo, delete the ```.idea``` folder (this is the folder IntelliJ uses for the IDE)
    3. Re-open the project in IntelliJ and then rebuild and debug
    4. Hopefully that fixes that exception, if not please open an issue for it.

---

## Libraries and API's Used
- [Processing 3](https://processing.org/) - Graphics library
- [Minim](https://github.com/ddf/Minim) - Audio library
- [ControlP5](https://www.sojamo.de/libraries/controlP5/) - Awesome GUI controls
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) - IO Utility library
- [Unirest](https://kong.github.io/unirest-java/) - Java http-client library
- [Gson](https://github.com/google/gson) - Java JSON library 

---

## Closing Comments
Thank you for checking out my work/experiment! I'll be pushing updates to this project as often as I can. Please feel free to fork or port this to other languages! Have fun and keep on creating!
