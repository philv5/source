import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np

gComposedM = np.identity(4)
gcamAng = 0

def render(M, camAng):
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)
        
        glLoadIdentity()

        glOrtho(-1,1, -1,1, -1,1)

        gluLookAt(.1*np.sin(camAng),.1,.1*np.cos(camAng), 0,0,0, 0,1,0)
        
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

        glColor3ub(255,255,255)

        glMultMatrixf(M.T)
        
        drawTriangle()


def key_callback(window, key, scancode, action, mods):
        global gComposedM
        global gcamAng
        th = np.radians(10)
        
        if key ==glfw.KEY_Q:
                if action==glfw.PRESS:
                        gComposedM = np.array([[1.,0.,0.,-0.1],[0.,1.,0.,0.],[0.,0.,1.,0.],[0.,0.,0.,1.]]) @ gComposedM
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gComposedM = np.array([[1.,0.,0.,-0.1],[0.,1.,0.,0.],[0.,0.,1.,0.],[0.,0.,0.,1.]]) @ gComposedM
                        
        elif key ==glfw.KEY_E:
                if action==glfw.PRESS:
                        gComposedM = np.array([[1.,0.,0.,0.1],[0.,1.,0.,0.],[0.,0.,1.,0.],[0.,0.,0.,1.]]) @ gComposedM
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        gComposedM = np.array([[1.,0.,0.,0.1],[0.,1.,0.,0.],[0.,0.,1.,0.],[0.,0.,0.,1.]]) @ gComposedM
                        
        elif key ==glfw.KEY_A:
                if action==glfw.PRESS:
                        th = np.radians(-10)
                        gComposedM = gComposedM @ np.array([[np.cos(th),0.,np.sin(th),0.],[0.,1.,0.,0.],[-np.sin(th), 0.,np.cos(th),0.],[0.,0.,0.,1.]]) 
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        th = np.radians(-10)
                        gComposedM = gComposedM @ np.array([[np.cos(th),0.,np.sin(th),0.],[0.,1.,0.,0.],[-np.sin(th), 0.,np.cos(th),0.],[0.,0.,0.,1.]])
                        
        elif key ==glfw.KEY_D:
                if action==glfw.PRESS:
                        th = np.radians(10)
                        gComposedM = gComposedM @ np.array([[np.cos(th),0.,np.sin(th),0.],[0.,1.,0.,0.],[-np.sin(th), 0.,np.cos(th),0.],[0.,0.,0.,1.]]) 
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        th = np.radians(10)
                        gComposedM = gComposedM @ np.array([[np.cos(th),0.,np.sin(th),0.],[0.,1.,0.,0.],[-np.sin(th), 0.,np.cos(th),0.],[0.,0.,0.,1.]])
                        
        elif key ==glfw.KEY_W:
                if action==glfw.PRESS:
                        th = np.radians(-10)
                        gComposedM = gComposedM @ np.array([[1.,0.,0.,0.],[0.,np.cos(th),-np.sin(th),0.],[0.,np.sin(th),np.cos(th),0.],[0.,0.,0.,1.]]) 
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        th = np.radians(-10)
                        gComposedM = gComposedM @ np.array([[1.,0.,0.,0.],[0.,np.cos(th),-np.sin(th),0.],[0.,np.sin(th),np.cos(th),0.],[0.,0.,0.,1.]])
                        
        elif key ==glfw.KEY_S:
                if action==glfw.PRESS:
                        th = np.radians(10)
                        gComposedM = gComposedM @ np.array([[1.,0.,0.,0.],[0.,np.cos(th),-np.sin(th),0.],[0.,np.sin(th),np.cos(th),0.],[0.,0.,0.,1.]]) 
                elif action==glfw.RELEASE:
                        pass
                elif action==glfw.REPEAT:
                        th = np.radians(10)
                        gComposedM = gComposedM @ np.array([[1.,0.,0.,0.],[0.,np.cos(th),-np.sin(th),0.],[0.,np.sin(th),np.cos(th),0.],[0.,0.,0.,1.]])
                        
        elif key ==glfw.KEY_1:
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

        render(gComposedM,gcamAng)


def drawTriangleTransformedBy(M):
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

def main():
	if not glfw.init():
		return
	
	window = glfw.create_window(480,480,"2014005014", None,None)
	
	if not window:
		glfw.terminate()
		return

	glfw.set_key_callback(window, key_callback)
	glfw.make_context_current(window)

	#glfw.swap_interval(1)
	
	while not glfw.window_should_close(window):
		glfw.poll_events()
				
		render(gComposedM,gcamAng)
		
		glfw.swap_buffers(window)
		
	glfw.terminate()
	

if __name__=="__main__":
    main()
