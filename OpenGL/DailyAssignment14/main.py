import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np

gComposedM = np.identity(4)
gcamAng = 0.
gcamHeight = 1.
gVertexArraySeparate = None
gVertexArrayIndexed = None
gIndexArray = None

def render():
        global gcamAng,gcamHeight
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)
        glPolygonMode(GL_FRONT_AND_BACK,GL_LINE)

        #glViewport(100,100,200,200)
        
        glLoadIdentity()

        #glOrtho(-5,5,-5,5,-10,10)

        gluPerspective(45,1,1,10)

        gluLookAt(5*np.sin(gcamAng),gcamHeight,5*np.cos(gcamAng), 0,0,0, 0,1,0)
        
        drawFrame()

        glColor3ub(255,255,255)

        drawUnitCube_glDrawElements()
        
        #drawCubeArray()
        #drawUnitCube()
        

def key_callback(window, key, scancode, action, mods):
        global gComposedM
        global gcamAng
        global gcamHeight
        
        if key ==glfw.KEY_1:
                if action==glfw.PRESS:
                        gcamAng += np.radians(-10)
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gcamAng += np.radians(-10)
                        
        elif key ==glfw.KEY_3:
                if action==glfw.PRESS:
                        gcamAng += np.radians(10)
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gcamAng += np.radians(10)

        elif key ==glfw.KEY_2:
                if action==glfw.PRESS:
                        gcamHeight += .1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gcamHeight += .1
                        
        elif key ==glfw.KEY_W:
                if action==glfw.PRESS:
                        gcamHeight += -.1
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gcamHeight += -.1


def drawTriangleTransformedBy(M):
        glColor3ub(255,255,255)
        glBegin(GL_TRIANGLES)
        glVertex3fv((M @ np.array([.0,.5,0.,1.]))[:-1])
        glVertex3fv((M @ np.array([.0,.0,0.,1.]))[:-1])
        glVertex3fv((M @ np.array([.5,.0,0.,1.]))[:-1])
        glEnd()

def drawTriangle():
        glBegin(GL_TRIANGLES)
        glVertex3fv(np.array([.0,.5,0.]))
        glVertex3fv(np.array([.0,.0,0.]))
        glVertex3fv(np.array([.5,.0,0.]))
        glEnd()

def drawFrameTransformedBy1(M):
        glBegin(GL_LINES)
        glColor3ub(255, 0, 0)
        glVertex3fv((M @ np.array([0.,0.,0.,1.]))[:-1])
        glVertex3fv((M @ np.array([1.,0.,0.,1.]))[:-1])
        glColor3ub(0, 255, 0)
        glVertex3fv((M @ np.array([0.,0.,0.,1.]))[:-1])
        glVertex3fv((M @ np.array([0.,1.,0.,1.]))[:-1])
        glColor3ub(0, 0, 255)
        glVertex3fv((M @ np.array([0.,0.,0.,1.]))[:-1])
        glVertex3fv((M @ np.array([0.,0.,1.,1.]))[:-1])
        glEnd()

def drawFrameTransformedBy2(M):
        glBegin(GL_LINES)
        glColor3ub(255, 0, 0)
        glVertex3fv(M[:3,3])
        glVertex3fv(M[:3,3] + M[:3,0])
        glColor3ub(0, 255, 0)
        glVertex3fv(M[:3,3])
        glVertex3fv(M[:3,3] + M[:3,1])
        glColor3ub(0, 0, 255)
        glVertex3fv(M[:3,3])
        glVertex3fv(M[:3,3] + M[:3,2])
        glEnd()

def drawFrame():
        # draw cooridnate : x in red, y in green, z in blue
        glBegin(GL_LINES)
        glColor3ub(255, 0, 0)
        glVertex3fv(np.array([0.,0.,0.]))
        glVertex3fv(np.array([1.,0.,0.]))
        glColor3ub(0, 255, 0)
        glVertex3fv(np.array([0.,0.,0.]))
        glVertex3fv(np.array([0.,1.,0.]))
        glColor3ub(0, 0, 255)
        glVertex3fv(np.array([0.,0.,0.]))
        glVertex3fv(np.array([0.,0.,1.]))
        glEnd()

def drawBox():
        glBegin(GL_QUADS)
        glVertex3fv(np.array([1,1,0.]))
        glVertex3fv(np.array([-1,1,0.]))
        glVertex3fv(np.array([-1,-1,0.]))
        glVertex3fv(np.array([1,-1,0.]))
        glEnd()

def myLookAt(eye, at, up):
        w = (eye - at)/np.sqrt(np.dot((eye - at),(eye - at)))
        u = np.cross(up,w)/np.sqrt(np.dot(np.cross(up,w),np.cross(up,w)))
        v = np.cross(w,u)

        M = np.array([[u[0],u[1],u[2],-np.dot(u,eye)],
                     [v[0],v[1],v[2],-np.dot(v,eye)],
                     [w[0],w[1],w[2],-np.dot(w,eye)],
                     [0,0,0,1]])
        
        glMultMatrixf(M.T)

def drawUnitCube():
        glBegin(GL_QUADS)
        glVertex3f(0.5,0.5,-0.5)
        glVertex3f(-0.5,0.5,-0.5)
        glVertex3f(-0.5,0.5,0.5)
        glVertex3f(0.5,0.5,0.5)

        glVertex3f(0.5,-0.5,0.5)
        glVertex3f(-0.5,-0.5,0.5)
        glVertex3f(-0.5,-0.5,-0.5)
        glVertex3f(0.5,-0.5,-0.5)

        glVertex3f(0.5,0.5,0.5)
        glVertex3f(-0.5,0.5,0.5)
        glVertex3f(-0.5,-0.5,0.5)
        glVertex3f(0.5,-0.5,0.5)

        glVertex3f(0.5,-0.5,-0.5)
        glVertex3f(-0.5,-0.5,-0.5)
        glVertex3f(-0.5,0.5,-0.5)
        glVertex3f(0.5,0.5,-0.5)

        glVertex3f(-0.5,0.5,0.5)
        glVertex3f(-0.5,0.5,-0.5)
        glVertex3f(-0.5,-0.5,-0.5)
        glVertex3f(-0.5,-0.5,0.5)

        glVertex3f(0.5,0.5,-0.5)
        glVertex3f(0.5,0.5,0.5)
        glVertex3f(0.5,-0.5,0.5)
        glVertex3f(0.5,-0.5,-0.5)
        glEnd()

def drawCubeArray():
        for i in range(5):
                for j in range(5):
                        for k in range(5):
                                glPushMatrix()
                                glTranslatef(i,j,-k-1)
                                glScalef(.5,.5,.5)
                                drawUnitCube()
                                glPopMatrix()
                                

def myOrtho(left,right,bottom,top,near,far):
        
        M = np.array([[2/(right-left),0,0,-(right+left)/(right-left)],
                     [0,2/(top-bottom),0,-(top+bottom)/(top-bottom)],
                     [0,0,-2/(far-near),-(far+near)/(far-near)],
                     [0,0,0,1]])
        
        glMultMatrixf(M.T)

def framebuffer_size(window, width, height):
    glViewport(0, 0, width, height)

def drawUnitCube_glDrawElements():
        global gVertexArrayIndexed, gIndexArray
        varr = gVertexArrayIndexed
        iarr = gIndexArray
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3,GL_FLOAT,3*varr.itemsize,varr)
        glDrawElements(GL_TRIANGLES,iarr.size,GL_UNSIGNED_INT,iarr)


def drawUnitCube_glDrawElements():
        global gVertexArrayIndexed, gIndexArray
        varr = gVertexArrayIndexed
        iarr = gIndexArray
        glEnableClientState(GL_VERTEX_ARRAY)
        glVertexPointer(3,GL_FLOAT,3*varr.itemsize,varr)
        glDrawElements(GL_TRIANGLES,iarr.size,GL_UNSIGNED_INT,iarr)

def createVertexArraySeparate():
        varr = np.array([
                [0.5,0.5,-0.5],
                [-0.5,0.5,-0.5],
                [-0.5,0.5,0.5],

                [0.5,0.5,-0.5],
                [-0.5,0.5,0.5],
                [0.5,0.5,0.5],

                [0.5,-0.5,0.5],
                [-0.5,-0.5,0.5],
                [-0.5,-0.5,-0.5],

                [0.5,-0.5,0.5],
                [-0.5,-0.5,-0.5],
                [0.5,-0.5,-0.5],

                [0.5,0.5,0.5],
                [-0.5,0.5,0.5],
                [-0.5,-0.5,0.5],

                [0.5,0.5,0.5],
                [-0.5,-0.5,0.5],
                [0.5,-0.5,0.5],

                [0.5,-0.5,-0.5],
                [-0.5,-0.5,-0.5],
                [-0.5,0.5,-0.5],

                [0.5,-0.5,-0.5],
                [-0.5,0.5,-0.5],
                [0.5,0.5,-0.5],

                [-0.5,-0.5,0.5],
                [-0.5,0.5,-0.5],
                [-0.5,-0.5,-0.5],

                [-0.5,0.5,0.5],
                [-0.5,-0.5,-0.5],
                [-0.5,-0.5,0.5],

                [0.5,0.5,-0.5],
                [0.5,0.5,0.5],
                [0.5,-0.5,0.5],

                [0.5,0.5,-0.5],
                [0.5,-0.5,0.5],
                [0.5,-0.5,-0.5],
                ],'float32')
        return varr

def createVertexIndexArrayIndexed():
        varr = np.array([
                [0.5,0.5,-0.5],
                [-0.5,0.5,-0.5],
                [-0.5,0.5,0.5],
                [0.5,0.5,0.5],
                [0.5,-0.5,0.5],
                [-0.5,-0.5,0.5],
                [-0.5,-0.5,-0.5],
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

def createVertexTP():
        varr = np.array([
                [0.,0.,0.],
                [1.5,0.,0.],
                [0.,1.5,0.],
                [0.,0.,1.5],
                ],'float32')
        iarr = np.array([
                [2,0,1],
                [2,3,0],
                [0,3,1],
                ])
        return varr, iarr


def main():
	if not glfw.init():
		return
	
	window = glfw.create_window(640,640,"2014005014", None,None)
	
	if not window:
		glfw.terminate()
		return

	glfw.set_key_callback(window, key_callback)
	glfw.make_context_current(window)
	glfw.set_framebuffer_size_callback(window, framebuffer_size)

	glfw.swap_interval(1)

	global gVertexArrayIndexed, gIndexArray

	gVertexArrayIndexed, gIndexArray = createVertexTP()

	count = 0
	
	while not glfw.window_should_close(window):
		glfw.poll_events()
				
		render()
		
		glfw.swap_buffers(window)

		count += 1
		
	glfw.terminate()
	

if __name__=="__main__":
    main()
