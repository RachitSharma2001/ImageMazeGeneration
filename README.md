## Easily generate beautiful mazes

This software allows the user to create mazes of any size and any complexity, as well as save it to their local computer or print it. This makes for great poster material, great challenges for you and your kids, etc. 

### The Algorithm Behind

To create the mazes, I used a datastructure called disjoint sets. The algorithm starts with a matrix of nxm cells. Each cell is represented by a set, and sets are randomly combined (unioned) until there contains just one set. When a pair of sets are combined, the wall between the two cells that represented those sets is removed. By the end, the resulting set of cells, with walls at different cells, forms what we would call a maze. The beautiful thing about this algorithm is that not only does it generate one, and only one, path from the start to the end, it also generates a lot of false paths. So as the maze gets larger, it gets harder and harder. 

## Download
Click here to download: [Mazes.zip](https://github.com/RachitSharma2001/ImageMazeSite/files/6827464/ImageMaze_3.jar.zip)


### The code
View the code at [here](https://github.com/RachitSharma2001/ImageMazeGeneration). I used an OOP structure, which has helped make my code more organized than past projects. However, there is still much to improve. Many functions are repetitory and deserve to be in their own class. I will work to improve the code and I would love suggestions on improvement too. 

### Questions?

If you have any questions about the code, this project, or anything else, please feel free to contact me at rachitsharma613@gmail.com. If you have any advice on how to improve this project, or my code, be sure contact me. 