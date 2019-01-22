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

        glOrtho(-1,1, -1,1, -1,1)

        gluLookAt(.1*np.sin(camAng),.1,.1*np.cos(camAng), 0,0,0, 0,1,0)

        drawFrame()

        glPushMatrix()
        glTranslatef(-0.5+(count%360)*.003,0,0)
        drawFrame()
        
        glPushMatrix()
        glScalef(.2,.2,.2)
        glColor3ub(0,0,255)
        drawBox()
        glPopMatrix()

        glPushMatrix()
        glRotatef(count%360,0,0,1)
        glTranslatef(.5,0,.01)
        drawFrame()

        glPushMatrix()
        glScalef(.5,.1,.1)
        glColor3ub(255,0,0)
        drawBox()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(.5,0,.01)
        glRotatef(count%360,0,0,1)
        drawFrame()

        glPushMatrix()
        glScalef(.2,.2,.2)
        glColor3ub(0,255,0)
        drawBox()
        glPopMatrix()

        glPopMatrix()
        glPopMatrix()
        glPopMatrix()
        

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
