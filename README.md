![](screenshots/logo.png?raw=true "Music-VisualizerV2 Logo")

## Synopsis
The prodigal Music Visualizer returns, and it's even better. Version 2 of Music-Visualizer is finally here!
V2 brings a much better codebase and more ease for addition, and will only improve over time.

---

## What? No Jar??
Yes, yes, I know! Unlike the previous version, for now, V2 will only ship the codebase. To protect the ```ClientId``` which SoundCloud uses to authenticate requests to its API, the only way to protect it (to my knowledge for now) from being decompiled out of the ```.jar``` is to read it in from a file at runtime.
<br/>
<br/>

Apologies for the inconvenience! The quickest way to get your ```.jar``` is to package the project. Take a look at the [Maven Tools](#Maven Tools) section below. I will try to figure out a way to get you the application not just the source!

---

## Working with the Source

### What you'll need to build and run from the source code
```
Java JDK + IntelliJ + Kotlin
```
#### The codebase is an IntelliJ IDEA project, so I'd recommend using [IntelliJ IDEA](https://www.jetbrains.com/idea/). This project also uses [Kotlin](https://kotlinlang.org/?fromMenu), which is an incredible language! I'd also recommend using the ```AdoptOpenJDK 12 or higher```: [AdoptOpenJDK - Windows, Mac, Linux](https://adoptopenjdk.net/releases.html?variant=openjdk12&jvmVariant=hotspot)

### Project Needed Setup
1. Add ```SoundCloud``` token:
    - Create a file called ```token.txt``` in the following folder: ```/src/main/resources/``` that just includes your SoundCloud ```ClientId``` 

2. If using [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE, when opening the project, hit the popup about the ```pom.xml``` file that says something like: ```Add as Maven Project```
    - This will make sure IntelliJ recognizes the project as being a Maven project!

### Maven Tools
- To build the .jar, in the Maven Lifecycle, use the ```package``` command
- To install any needed dependencies for local .jars (controlp5), use the ```validate``` command
- To clean the project ('target' in root project dir) use the ```clean``` command

---

## Issues? Features? More???

Create an ```issue``` for the repo if you encounter any errors or strange behavior/functionality with the application. 
Please feel free to fork and submit bug fixes or feature requests!

---

## Libraries and API's Used
- [Processing 3](https://processing.org/) - Graphics library
- [Minim](https://github.com/ddf/Minim) - Audio library
- [ControlP5](https://www.sojamo.de/libraries/controlP5/) - Awesome GUI controls
- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) - IO Utility library
- [Unirest](https://kong.github.io/unirest-java/) - Java http-client library
- [Gson](https://github.com/google/gson) - Java JSON library 

---

### Closing Comments
Thank you for checking out my work/experiment! I'll be pushing updates to this project as often as I can. Please feel free to fork or port this to other languages! Have fun and keep on creating!