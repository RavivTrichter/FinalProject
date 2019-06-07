import matplotlib.pyplot as plt
import numpy as np


def plotTSP(path, points, n, fig_name, objective_val, seconds, status,save_fig=False):
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
    plt.arrow(x_np[path[-1]], y_np[path[-1]], (x_np[path[0]] - x_np[path[-1]]), (y_np[path[0]] - y_np[path[-1]]),
              head_width=a_scale, color='r', length_includes_head=True)

    # Set axis too slitghtly larger than the set of x and y
    plt.xlim(0, max(x_np))
    plt.ylim(0, max(y_np))
    plt.xlabel("Objective function value : " + str(round(objective_val, 2)) + "     Status : " + status )
    if seconds < 60 :
        plt.title("TSP route of " + str(n) + " nodes in " + str(seconds) + " seconds")
    else :
        minutes = seconds / 60
        plt.title("TSP route of " + str(n) + " nodes in " + str(round(minutes)) + "minutes and " + str(seconds % 60) +" seconds")
    if save_fig:
        plt.savefig(fig_name)
    plt.show()

def parseGraph(filename):
    x = []
    y = []
    with open(filename) as f:
        for line in f:
            data = line.split(" ")
            x.append(float(data[1]))
            y.append(float(data[2]))
    return x, y

def parseRoute(filename):
    route = []
    with open(filename) as f:
        path = f.readline().split(" ")
        for i in range(len(path) - 1):  # except '\n' that is the last character
            route.append(int(path[i]))
        obj_value = float(f.readline().split(":")[1])
        seconds = int(f.readline().split(":")[1])
        status = f.readline().split(":")[1]
    return route, obj_value, seconds, status


if __name__ == '__main__' :
    x, y = parseGraph("hachula130.dat")
    T = True
    points = []
    for i in range(len(x)):
        points.append([x[i], y[i]])


    #  the path is by the indices
    path, obj_val, seconds, status = parseRoute('G1Route.dat')

    # Plot TSP Route 1
    plotTSP(path, points, len(path)-1, 'G1_fig.png', obj_val, seconds, status, T)  # insert True as last argument to save fig

    path, obj_val, seconds, status = parseRoute('G2Route.dat')

    # Plot TSP Route 2
    plotTSP(path, points, len(path)-1, 'G2_fig.png', obj_val, seconds, status, T)

    path, obj_val, seconds, status = parseRoute('G3Route.dat')

    # Plot TSP Route 3
    plotTSP(path, points, len(path)-1, 'G3_fig.png', obj_val, seconds, status, T)