JSketch App

By: Steven Zhu

ID: 20471572


Program Description:
android studio -> run

Tool bar - all working, toggles using xml "btn_pressed" and "btn_unpressed"


Color Palette - 4 static color, black, red, green, blue, toggles using xml "btn_pressed" and "btn_unpressed"


Thickness Palette - 3 static size, small, medium, large

Portrait orientation, tools docked bottom

Landscape orientation, tools docked to the right (with the exception of the save icon which is dynamically moved to the rightmost row)
(docking to the left would mean changing linearlayout to relativelayout with tons of issues, so I decided to just dock the tools to the right as it is very usable and works well)

In both orientations, the canvas is set to a fixed size of 656 x 656. When orientating, the canvas is always in full view.


Drawing Preview for all shapes is working

Moving Shapes work, selection of shape is handled during the first moment when you touch the screen

Selection and drawing of shapes is based on the most recent shape if there is an overlap

Tool Palette reuses images from PaintDemo in java_examples.zip on the CS349 website


OS - Ubuntu 16.04
JDK - 1.8.0_91


Enhancements:

Save icon - http://www.foulders.com/outlook-gtd/wp-content/uploads/2015/07/save-2-icon.png


Saving to gallary works into a png file, user must grant premission when prompted

Native sharing options are un-touched
