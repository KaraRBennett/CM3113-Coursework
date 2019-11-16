# CM3113 Coursework
The following is my coursework submission for the third year moduel CM3113 Computer Vision (2019). The coursework task was to implement a program that could perform various image processing tasks on a stack of images. Specifically these tasks were: -
* Computer the mean image of the stack
* Sobel Edge Detection for each image 
* Compute the Difference of Gaussian for each image
* Generate a 3D Depth Map from either the Sobel or DoG
* Apply a Median Filter to the 3D Depth Map
* Generate an Extended Depth of Field image form the 3D Depth Map

The program takes in a stack of .pgm images (provided as a newline seperated file containing the filenames of the images) whose dimensions are 1500 X 1500 or less. The program then generates five images. These images are; the first image in the stack, the Mean Image, the first image convoluted by either Sobel or DoG, the Depth Map (post Median Filtering if applied), and the Extended Depth of Field of the image stack. Exmaple images can be found in ImageStack.

<br/>

## Instructions to Run
To compile code run the following command from the project root `javac @edof.txt`. To run the program use the command `java edof.EDoF <filename> <optional arguments>`.

<br/>

### Optional Arguments
Optional Argument           | Effect
----------------------------|-------------------------------------------------------------------------------------------------
__1<sup>st</sup>__ | Toggle Median Filtering on or off (`1` for on, `0` for off)<br/>
__2<sup>nd</sup>__ | Toggle Spacial Coherence Filtering on or off (`1` for on, `0` for off)<br/>
__3<sup>rd</sup>__ | Change convolution method from Sobel to Difference of Gaussian (`D` or `d`)<br/>
__4<sup>th</sup>__ | Sigma value to be used if Difference of Gaussian Convolution is choosen (Any integer value)<br/>
