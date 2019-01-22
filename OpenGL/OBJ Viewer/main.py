import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np
from OpenGL.arrays import vbo
import ctypes

gCamAng = 0.
gCamHeight = 1.
gCamZoom = 45
gVertexArray = None
gVertexNormalArray = None
gIndexArray = None
gIndexNormal = None
gWireframeBit = 0

def render(ang):
    global gCamAng, gCamHeight, gCamZoom, gIndexArray, gWireframeBit
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT)

    glEnable(GL_DEPTH_TEST)

    if gWireframeBit == 0:
        glPolygonMode(GL_FRONT_AND_BACK,GL_FILL)
    elif gWireframeBit == 1:
        glPolygonMode(GL_FRONT_AND_BACK,GL_LINE)

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(gCamZoom, 1, 1,10)

    glMatrixMode(GL_MODELVIEW)
    glLoadIdentity()
    gluLookAt(5*np.sin(gCamAng),gCamHeight,5*np.cos(gCamAng), 0,0,0, 0,1,0)

    # draw global frame
    drawFrame()

    glEnable(GL_LIGHTING)
    glEnable(GL_LIGHT0)
    glEnable(GL_LIGHT1)
    glEnable(GL_LIGHT2)
    
    glEnable(GL_RESCALE_NORMAL) # rescale normal vectors after transformation and before lighting to have unit length

    # set light0 properties
    lightPos = (1.,2.,3.,1.)
    ambientLightColor = (.1,.1,.1,1.)
    diffuseLightColor = (1.,1.,1.,1.)
    specularLightColor = (1.,1.,1.,1.)
    glLightfv(GL_LIGHT0, GL_POSITION, lightPos)
    glLightfv(GL_LIGHT0, GL_AMBIENT, ambientLightColor)
    glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLightColor)
    glLightfv(GL_LIGHT0, GL_SPECULAR, specularLightColor)

    # set light1 properties
    lightPos = (0.,0.,1.,0.)
    ambientLightColor = (.1,.1,.1,1.)
    diffuseLightColor = (.1,.1,.1,1.)
    specularLightColor = (.1,.1,.1,1.)
    glLightfv(GL_LIGHT1, GL_POSITION, lightPos)
    glLightfv(GL_LIGHT1, GL_AMBIENT, ambientLightColor)
    glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuseLightColor)
    glLightfv(GL_LIGHT1, GL_SPECULAR, specularLightColor)

    # set light2 properties
    lightPos = (1.,0.,0.,1.)
    ambientLightColor = (.1,.1,.1,1.)
    diffuseLightColor = (.5,.5,.5,1.)
    specularLightColor = (.5,.5,.5,1.)
    glLightfv(GL_LIGHT2, GL_POSITION, lightPos)
    glLightfv(GL_LIGHT2, GL_AMBIENT, ambientLightColor)
    glLightfv(GL_LIGHT2, GL_DIFFUSE, diffuseLightColor)
    glLightfv(GL_LIGHT2, GL_SPECULAR, specularLightColor)

    if not gIndexArray == None:
        glScalef(.5,.5,.5)
        drawOBJ()

    glDisable(GL_LIGHTING)


def key_callback(window, key, scancode, action, mods):
        global gCamAng, gCamHeight, gCamZoom, gWireframeBit
        
        if key ==glfw.KEY_1:
                if action==glfw.PRESS:
                        gCamAng += np.radians(-10)
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gCamAng += np.radians(-10)
                        
        elif key ==glfw.KEY_3:
                if action==glfw.PRESS:
                        gCamAng += np.radians(10)
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gCamAng += np.radians(10)

        elif key ==glfw.KEY_2:
                if action==glfw.PRESS:
                        gCamHeight += .1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gCamHeight += .1
                        
        elif key ==glfw.KEY_W:
                if action==glfw.PRESS:
                        gCamHeight += -.1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gCamHeight += -.1
                        
        elif key ==glfw.KEY_A and gCamZoom > 0:
                if action==glfw.PRESS:
                        gCamZoom -= 1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT and gCamZoom > 0:
                        gCamZoom -= 1

        elif key ==glfw.KEY_S:
                if action==glfw.PRESS and gCamZoom < 180:
                        gCamZoom += 1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT and gCamZoom < 180:
                        gCamZoom += 1

        elif key ==glfw.KEY_Z:
                if action==glfw.PRESS:
                        gWireframeBit = (gWireframeBit + 1) % 2
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gWireframeBit = (gWireframeBit + 1) % 2


def drawFrame():
        glBegin(GL_LINES)
        glColor3ub(255, 0, 0)
        glVertex3fv(np.array([0.,0.,0.]))
        glVertex3fv(np.array([1.,0.,0.]))
        glColor3ub(0, 255, 0)
        glVertex3fv(np.array([0.,0.,0.]))
        glVertex3fv(np.array([0.,1.,0.]))
        glColor3ub(0, 0, 255)
        glVertex3fv(np.array([0.,0.,0]))
        glVertex3fv(np.array([0.,0.,1.]))
        glEnd()

def drawOBJ():
    global gVertexArray, gVertexNormalArray, gIndexArray, gIndexNormal

    numFace = len(gIndexArray)

    for i in range(numFace):
        if len(gIndexArray[i]) >= 3:
            norms = []
            vertexs = []
            for j in range(len(gIndexArray[i])):
                vertexs.append(gVertexArray[gIndexArray[i][j] - 1])
                norms.append(gVertexNormalArray[gIndexNormal[i][j] - 1])
            
            glBegin(GL_POLYGON)
            for k in range(len(gIndexArray[i])):
                glNormal3f(norms[k][0],norms[k][1],norms[k][2])
                glVertex3f(vertexs[k][0],vertexs[k][1],vertexs[k][2])
                
            glEnd()


def drop_callback(window, path):
    global gVertexArray, gVertexNormalArray, gIndexArray, gIndexNormal

    numFacesTotal = 0
    numFaces3 = 0
    numFaces4 = 0
    numFacesMore = 0
    size = 0
    
    VertexArray = []
    VertexNormalArray = []
    IndexArray = []
    IndexNormal = []
    
    for n in path:
        filename = n

    for line in open(filename, 'r'):
        vals = line.split()

        if len(vals) == 0:
            continue
        
        if line.startswith('#'):
            continue

        if vals[0] == 'v':
            vertex = []
            for v in vals[1:4]:
                vertex.append(float(v))
            VertexArray.append(vertex)
        elif vals[0] == 'vn':
            norm = []
            for v in vals[1:4]:
                norm.append(float(v))
            VertexNormalArray.append(norm)
        elif vals[0] == 'f':
            face = []
            vnormal = []
            for v in vals[1:]:
                w = v.split('/')
                face.append(int(w[0]))
                vnormal.append(int(w[2]))
            IndexArray.append(face)
            IndexNormal.append(vnormal)
            if len(face) == 3:
                numFaces3 += 1
                size += 3
            elif len(face) == 4:
                numFaces4 += 1
                size += 4
            elif len(face) > 4:
                numFacesMore += 1
                size += len(face)
            numFacesTotal += 1

    gVertexArray = VertexArray
    gVertexNormalArray = VertexNormalArray
    gIndexArray = IndexArray
    gIndexNormal = IndexNormal
    
    
    print('File name: '+filename)
    print('Total number of faces:',format(numFacesTotal))
    print('Number of faces with 3 vertices: ',format(numFaces3))
    print('Number of faces with 4 vertices: ',format(numFaces4))
    print('Number of faces with more than 4 vertices: ',format(numFacesMore))
    print('\n')

    glfw.focus_window(window);


def main():
        
        if not glfw.init():
                return
        window = glfw.create_window(640,640,'2014005014',None,None)
        if not window:
                glfw.terminate()
                return
        glfw.make_context_current(window)
        glfw.set_key_callback(window, key_callback)
        glfw.set_drop_callback(window, drop_callback)
        glfw.swap_interval(1)
        
        count = 0
        while not glfw.window_should_close(window):
                glfw.poll_events()
                ang = count % 360
                render(ang)
                count += 1
                glfw.swap_buffers(window)
                
        glfw.terminate()
	

if __name__=="__main__":
    main()
