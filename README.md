# Java RayTracing Engine

A project created for the Object-Oriented Programming course at AGH UST during the 2022/2023 academic year.

## Overview

This project is an implementation of a simple graphic engine using ray-tracing technology. The engine allows the user to set up a scene containing specified objects. The engine supports adding spheres, planes, and simple triangle-based models given in `.obj` format. The user can also specify the resolution of the output image, the depth of recurrence, and the number of samples per pixel. After setting up the scene, the rendering process is shown live on a second window. The output file is saved in the project source code folder, in `.ppm` format. Note that you may need to use an [external tool](https://www.cs.rhodes.edu/welshc/COMP141_F16/ppmReader.html) to view the image.

The project was created as an assignment and follows the requirements from the task description, which can be found [here](https://github.com/apohllo/obiektowe-lab/blob/master/proj2/Raytracing.md).

For my first experience with creating a graphic engine, I used the [_Ray Tracing in One Weekend_](https://raytracing.github.io/books/RayTracingInOneWeekend.html) tutorial to understand this topic and implement some logic.

## Launching the Application

To run the application, you will need:

- Jdk 16
- Gradle 7.4
- The dependencies listed in the build.gradle file

It is recommended to use IntelliJ to run the simulation. Alternatively, you can use the `./gradlew.bat run` command in the main folder.

## Setting up a Scene

After running the program, an initial screen is displayed.

<p align="center">
  <img src="https://i.imgur.com/okFYU5J.png">
</p>

In this window, the user can choose the objects (with their positions and material) that will be added to the scene. There is no option to remove objects from the scene.

Supported objects:

- Sphere - the user has to specify the central point and radius
- Plane - the user has to specify a point that belongs to the plane and a normal vector
- Triangle-based shape - the user has to specify the position of the object and the path to the file containing the object. The file with the object should consist of lines starting with `v` for vertex and `f` for face.

The user can also choose the material of an object.

Supported materials:

- Metal - the user specifies the color and the diffuse value
- Mat - metal with the diffuse value set to `1`, the user has to specify the color
- Glass - the user has to specify the refraction value
- Light - a material that emits light

The user can also set the background color of the render or use an image in `.jpg` or `.png` format as the background.

## Working with External Files

To add a shape or external image, you need to provide the file's path. It's recommended to use absolute paths, but you can also paste the files into the main folder of the project (the folder where the images are saved) and only use the filenames. Writing the scene to a file is possible. To do so, enter the file name in the `Path to file` text box and select the corresponding button. The scene will be saved in the main folder of the project. You can read a saved scene in the same way you would read external objects or images.

## Sample renders
<p align="center">
  <img src="https://i.imgur.com/qCYyz8v.jpg">
</p>

<p align="center">
  <img src="https://i.imgur.com/knXtQRq.jpg">
</p>

<p align="center">
  <img src="https://i.imgur.com/W9pNpxs.jpg">
</p>

<p align="center">
  <img src="https://i.imgur.com/lv9L0bA.jpg">
</p>