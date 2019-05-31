import matplotlib.pyplot as plt
import numpy as np

def plotTSP(path, points, n=130):
    """
    path: List of lists with the different orders in which the nodes are visited
    points: coordinates for the different nodes
    num_iters: number of paths that are in the path list

    """
    # Unpack the primary TSP path and transform it into a list of ordered
    # coordinates

    np_points = np.array(points)
    x_np = np_points[:, 0]
    y_np = np_points[:, 1]

    # Set a scale for the arrow heads (there should be a reasonable default for this, WTF?)
    a_scale = float(max(x_np)) / float(50)

    plt.scatter(x_np, y_np, marker='o', color='g')
    plt.scatter(x_np[path[0]], y_np[path[0]], marker='D', color='b')
    # Draw the primary path for the TSP problem

    for i in range(0, len(path)-1):
        plt.arrow(x_np[path[i]], y_np[path[i]], (x_np[path[i+1]] - x_np[path[i]]), (y_np[path[i+1]] - y_np[path[i]]), head_width=a_scale,
                  color='r', length_includes_head=True)

    # Set axis too slitghtly larger than the set of x and y
    plt.xlim(0, max(x_np))
    plt.ylim(0, max(y_np))
    plt.title("Size of TSP route = " + str(n))
    plt.show()

def parse(filename):
    x = []
    y = []
    with open(filename) as f:
        for line in f:
            data = line.split(" ")
            x.append(float(data[1]))
            y.append(float(data[2]))
    return x, y



if __name__ == '__main__' :
    x, y = parse("hachula130.dat")
    points = []
    for i in range(len(x)):
        points.append([x[i], y[i]])

    #  the path is by the indices
    path = [120, 16, 20, 120]

    # Run the function
    plotTSP(path, points,len(path))