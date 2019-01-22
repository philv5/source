import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np
from OpenGL.arrays import vbo
import ctypes

gCamAng = 0.
gCamHeight = 1.
gVertexArraySeparate = None
gVertexArrayIndexed = None
gIndexArray = None
gAxis = np.array([0.,1.,0.])


def render(ang):
    global gCamAng, gCamHeight, gAxis
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT)

    glEnable(GL_DEPTH_TEST)

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    gluPerspective(45, 1, 1,10)

    glMatrixMode(GL_MODELVIEW)
    glLoadIdentity()
    gluLookAt(5*np.sin(gCamAng),gCamHeight,5*np.cos(gCamAng), 0,0,0, 0,1,0)

    # draw global frame
    drawFrame()

    # draw rotation axis
    glBegin(GL_LINES)
    glColor3ub(255, 255, 255)
    glVertex3fv(np.array([0.,0.,0.]))
    glVertex3fv(gAxis)
    glEnd()

    glEnable(GL_LIGHTING)
    glEnable(GL_LIGHT0)
    glEnable(GL_RESCALE_NORMAL) # rescale normal vectors after transformation and before lighting to have unit length

    # set light properties
    lightPos = (1.,2.,3.,1.)
    glLightfv(GL_LIGHT0, GL_POSITION, lightPos)

    ambientLightColor = (.1,.1,.1,1.)
    diffuseLightColor = (1.,1.,1.,1.)
    specularLightColor = (1.,1.,1.,1.)
    glLightfv(GL_LIGHT0, GL_AMBIENT, ambientLightColor)
    glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLightColor)
    glLightfv(GL_LIGHT0, GL_SPECULAR, specularLightColor)

    # for your answer
    R = getRotMatFrom(gAxis,ang)
    M = np.identity(4)
    M[:3,:3] = R
    glMultMatrixf(M.T)

    glScalef(.5,.5,.5)

    # draw cubes
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, (.5,.5,.5,1.))
    drawUnitCube_glDrawArray()

    glTranslatef(1.5,0,0)
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, (1.,0.,0.,1.))
    drawUnitCube_glDrawArray()

    glTranslatef(-1.5,1.5,0)
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, (0.,1.,0.,1.))
    drawUnitCube_glDrawArray()

    glTranslatef(0,-1.5,1.5)
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, (0.,0.,1.,1.))
    drawUnitCube_glDrawArray()

    glDisable(GL_LIGHTING)


def key_callback(window, key, scancode, action, mods):
        global gCamAng
        global gCamHeight
        global gAxis
        
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

        elif key ==glfw.KEY_A:
                if action==glfw.PRESS:
                        gAxis += np.array([0.1,0.,0.])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis += np.array([0.1,0.,0.])

        elif key ==glfw.KEY_Z:
                if action==glfw.PRESS:
                        gAxis -= np.array([0.1,0.,0.])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis -= np.array([0.1,0.,0.])

        elif key ==glfw.KEY_S:
                if action==glfw.PRESS:
                        gAxis += np.array([0.,0.1,0.])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis += np.array([0.,0.1,0.])

        elif key ==glfw.KEY_X:
                if action==glfw.PRESS:
                        gAxis -= np.array([0.,0.1,0.])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis -= np.array([0.,0.1,0.])

        elif key ==glfw.KEY_D:
                if action==glfw.PRESS:
                        gAxis += np.array([0.,0.,0.1])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis += np.array([0.,0.,0.1])

        elif key ==glfw.KEY_C:
                if action==glfw.PRESS:
                        gAxis -= np.array([0.,0.,0.1])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gAxis -= np.array([0.,0.,0.1])

        elif key ==glfw.KEY_V:
                if action==glfw.PRESS:
                        gAxis = np.array([0.,1.,0.])
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        pass
                        


def drawUnitCube_glDrawArray():
        global gVertexArraySeparate
        varr = gVertexArraySeparate
        glEnableClientState(GL_VERTEX_ARRAY)
        glEnableClientState(GL_NORMAL_ARRAY)
        glNormalPointer(GL_FLOAT, 6*varr.itemsize, varr)
        glVertexPointer(3, GL_FLOAT, 6*varr.itemsize,
        ctypes.c_void_p(varr.ctypes.data + 3*varr.itemsize))
        glDrawArrays(GL_TRIANGLES, 0, int(varr.size/6))

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

def l2norm(v):
        return np.sqrt(np.dot(v, v))

def normalized(v):
        l = l2norm(v)
        return 1/l * np.array(v)

def drawUnitCube_glDrawElements():
        global gVertexArrayIndexed, gIndexArray
        varr = gVertexArrayIndexed
        iarr = gIndexArray
        glEnableClientState(GL_VERTEX_ARRAY)
        glEnableClientState(GL_NORMAL_ARRAY)
        glNormalPointer(GL_FLOAT, 6*varr.itemsize,varr)
        glVertexPointer(3, GL_FLOAT,6*varr.itemsize,ctypes.c_void_p(varr.ctypes.data +3*varr.itemsize))
        glDrawElements(GL_TRIANGLES, iarr.size,GL_UNSIGNED_INT, iarr)

def createVertexAndIndexArrayIndexed():
        varr = np.array([
                normalized([0,1,0]),
                [0.5,0.5,-0.5],
                normalized([0,-1,0]),
                [-0.5, 0.5,-0.5],
                normalized([0,0,1]),
                [-0.5, 0.5, 0.5],
                normalized([0,0,-1]),
                [0.5, 0.5, 0.5],
                normalized([-1,0,0]),
                [0.5,-0.5, 0.5],
                normalized([1,0,0]),
                [-0.5,-0.5, 0.5],
                normalized([0,1,0]),
                [-0.5,-0.5,-0.5],
                normalized([-1,0,0]),
                [0.5,-0.5,-0.5],
                ],'float32')
        
        iarr = np.array([
                [0,1,2],
                [0,2,3],
                [4,5,6],
                [4,6,7],
                [3,2,5],
                [3,5,4],
                [7,6,1],
                [7,1,0],
                [2,1,6],
                [2,6,5],
                [0,3,4],
                [0,4,7],
                ])
        return varr, iarr

def createVertexArraySeparate():
        varr = np.array([
                [0,1,0],
                [0.5,0.5,-0.5],
                [0,1,0],
                [-0.5,0.5,-0.5],
                [0,1,0],
                [-0.5,0.5,0.5],

                [0,1,0],
                [0.5,0.5,-0.5],
                [0,1,0],
                [-0.5,0.5,0.5],
                [0,1,0],
                [0.5,0.5,0.5],

                [0,-1,0],
                [0.5,-0.5,0.5],
                [0,-1,0],
                [-0.5,-0.5,0.5],
                [0,-1,0],
                [-0.5,-0.5,-0.5],

                [0,-1,0],
                [0.5,-0.5,0.5],
                [0,-1,0],
                [-0.5,-0.5,-0.5],
                [0,-1,0],
                [0.5,-0.5,-0.5],

                [0,0,1],
                [0.5,0.5,0.5],
                [0,0,1],
                [-0.5,0.5,0.5],
                [0,0,1],
                [-0.5,-0.5,0.5],

                [0,0,1],
                [0.5,0.5,0.5],
                [0,0,1],
                [-0.5,-0.5,0.5],
                [0,0,1],
                [0.5,-0.5,0.5],

                [0,0,-1],
                [0.5,-0.5,-0.5],
                [0,0,-1],
                [-0.5,-0.5,-0.5],
                [0,0,-1],
                [-0.5,0.5,-0.5],

                [0,0,-1],
                [0.5,-0.5,-0.5],
                [0,0,-1],
                [-0.5,0.5,-0.5],
                [0,0,-1],
                [0.5,0.5,-0.5],

                [-1,0,0],
                [-0.5,0.5,0.5],
                [-1,0,0],
                [-0.5,0.5,-0.5],
                [-1,0,0],
                [-0.5,-0.5,-0.5],

                [-1,0,0],
                [-0.5,0.5,0.5],
                [-1,0,0],
                [-0.5,-0.5,-0.5],
                [-1,0,0],
                [-0.5,-0.5,0.5],

                [1,0,0],
                [0.5,0.5,-0.5],
                [1,0,0],
                [0.5,0.5,0.5],
                [1,0,0],
                [0.5,-0.5,0.5],

                [1,0,0],
                [0.5,0.5,-0.5],
                [1,0,0],
                [0.5,-0.5,0.5],
                [1,0,0],
                [0.5,-0.5,-0.5],
                ], 'float32')
        return varr

def getRotMatFrom(axis,ang):
    zang = np.radians(ang)
    a = normalized(axis)
    z = np.array([0.,0.,1.])
    p = normalized(np.cross(a, z))

    R1 = np.column_stack((z, p, np.cross(p,z)))
    R2 = np.column_stack((a, p, np.cross(p,a)))
    Raz = R1 @ np.linalg.inv(R2)

    Rz = np.array([[np.cos(zang), -np.sin(zang), 0],
                   [np.sin(zang), np.cos(zang), 0],
                   [0,0,1]])

    R = np.linalg.inv(Raz) @ Rz @ Raz

    return R    

def main():
        global gVertexArraySeparate
        if not glfw.init():
                return
        window = glfw.create_window(640,640,'2014005014',None,None)
        if not window:
                glfw.terminate()
                return
        glfw.make_context_current(window)
        glfw.set_key_callback(window, key_callback)
        glfw.swap_interval(1)
        
        gVertexArraySeparate = createVertexArraySeparate()
        
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
