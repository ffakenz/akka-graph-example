
import plotly
import plotly.plotly as py
import plotly.graph_objs as go

plotly.tools.set_credentials_file(username='MigueLemos', api_key='SuIEvPQEAYWTC2rvmuJJ')


class CubeFactory(object):

    vertex_indices = [[7, 0, 0, 0, 4, 4, 6, 6, 4, 0, 3, 2],
                      [3, 4, 1, 2, 5, 6, 5, 2, 0, 1, 6, 3],
                      [0, 7, 2, 3, 6, 7, 1, 1, 5, 5, 7, 6]]
    vertex_coordinates = [[0, 0, 1, 1, 0, 0, 1, 1],
                          [0, 1, 1, 0, 0, 1, 1, 0],
                          [0, 0, 0, 0, 1, 1, 1, 1]]
    colors = {"1":'rgb(135,206,250)', "2": 'rgb(222,184,135)'}


    @staticmethod
    def createCubes(matrix):
        cubes = []
        for Z,z in enumerate(matrix):
            for Y,y in enumerate(z):
                for X,value in enumerate(y):
                    if value != "0":
                        cubes.append(Cube.createCube(X,Y,Z,value))

        data = go.Data(cubes)
        return data

    @staticmethod
    def createCube(X, Y, Z, value):
        data = go.Mesh3d(
                x = [item + X for item in Cube.vertex_coordinates[0]],
                y = [item + Y for item in Cube.vertex_coordinates[1]],
                z = [item + Z for item in Cube.vertex_coordinates[2]],
                color = Cube.colors[value],
                opacity = 0.5 if value == "1" else 1,
                i = Cube.vertex_indices[0],
                j = Cube.vertex_indices[1],
                k = Cube.vertex_indices[2],
            )
        return data



if __name__ == "__main__":

    #output =
    with open("../output") as f:
        content = f.read()

    z_batches = [z for z in content.split('\n\n') if z != "\n"]
    y_batches = [z_batch.split('\n') for z_batch in z_batches]
    x_batches = [[[z for z in x.split(" ") if z] for x in y_batch] for y_batch in y_batches]
    reconstructed_matrix = x_batches

    data = CubeFactory.createCubes(reconstructed_matrix)


    layout = go.Layout(
        xaxis=go.XAxis(
            title='x'
        ),
        yaxis=go.YAxis(
            title='y'
        )
    )
    #data = Cube.createCube(3,3,3,2)
    fig = go.Figure(data=data, layout=layout)
    py.plot(fig, filename='3d-mesh-cube-python')
