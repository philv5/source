import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np

gComposedM = np.identity(4)
gcamAng = 0

def render(camAng, count):
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)
        
        glLoadIdentity()

        glOrtho(-1,1, -1,1, -10,10)

        #gluLookAt(1*np.sin(camAng),1,1*np.cos(camAng), 0,0,0, 0,1,0)
        
        eye = np.array([1*np.sin(camAng),1,1*np.cos(camAng)])
        at = np.array([0,0,0])
        up = np.array([0,1,0])
        myLookAt(eye, at, up)
        
        drawFrame()
        

def key_callback(window, key, scancode, action, mods):
        global gComposedM
        global gcamAng
        
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
        
        
def main():
	if not glfw.init():
		return
	
	window = glfw.create_window(640,640,"2014005014", None,None)
	
	if not window:
		glfw.terminate()
		return

	glfw.set_key_callback(window, key_callback)
	glfw.make_context_current(window)

	glfw.swap_interval(1)

	count = 0
	
	while not glfw.window_should_close(window):
		glfw.poll_events()
				
		render(gcamAng, count)
		
		glfw.swap_buffers(window)

		count += 1
		
	glfw.terminate()
	

if __name__=="__main__":
    main()
