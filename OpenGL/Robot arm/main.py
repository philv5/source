import glfw
import math
from OpenGL.GL import *
from OpenGL.GLU import *
import numpy as np

gcamAng = 0.
gcamHeight = 1.
shoulder = 0
elbow = 0
finger = 0
sbit = 0
ebit = 0
fbit = 0

def render():
        global gcamAng,gcamHeight
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        glEnable(GL_DEPTH_TEST)
        glPolygonMode(GL_FRONT_AND_BACK,GL_LINE)
        
        glLoadIdentity()

        glOrtho(-1,1,-1,1,-10,10)

        gluLookAt(5*np.sin(gcamAng),gcamHeight,5*np.cos(gcamAng), 0,0,0, 0,1,0)
        
        drawFrame()

        drawRobotArm()
        
        

def key_callback(window, key, scancode, action, mods):
        global gcamAng,gcamHeight
        
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


def drawCube():
        glBegin(GL_QUADS)
        glVertex3f(1.0,1.0,-1.0)
        glVertex3f(-1.0,1.0,-1.0)
        glVertex3f(-1.0,1.0,1.0)
        glVertex3f(1.0,1.0,1.0)

        glVertex3f(1.0,-1.0,1.0)
        glVertex3f(-1.0,-1.0,1.0)
        glVertex3f(-1.0,-1.0,-1.0)
        glVertex3f(1.0,-1.0,-1.0)

        glVertex3f(1.0,1.0,1.0)
        glVertex3f(-1.0,1.0,1.0)
        glVertex3f(-1.0,-1.0,1.0)
        glVertex3f(1.0,-1.0,1.0)

        glVertex3f(1.0,-1.0,-1.0)
        glVertex3f(-1.0,-1.0,-1.0)
        glVertex3f(-1.0,1.0,-1.0)
        glVertex3f(1.0,1.0,-1.0)

        glVertex3f(-1.0,1.0,1.0)
        glVertex3f(-1.0,1.0,-1.0)
        glVertex3f(-1.0,-1.0,-1.0)
        glVertex3f(-1.0,-1.0,1.0)

        glVertex3f(1.0,1.0,-1.0)
        glVertex3f(1.0,1.0,1.0)
        glVertex3f(1.0,-1.0,1.0)
        glVertex3f(1.0,-1.0,-1.0)
        glEnd()



def drawSphere(numLats=12, numLongs=12):
        for i in range(0,numLats+1):
                lat0 = np.pi * (-0.5 + float(float(i-1) / float(numLats)))
                z0 = np.sin(lat0)
                zr0 = np.cos(lat0)

                lat1 = np.pi * (-0.5 + float(float(i) / float(numLats)))
                z1 = np.sin(lat1)
                zr1 = np.cos(lat1)

                glBegin(GL_QUAD_STRIP)

                for j in range(0, numLongs+1):
                        lng = 2 * np.pi * float(float(j-1) / float(numLongs))

                        x = np.cos(lng)
                        y = np.sin(lng)
                        glVertex3f(x*zr0,y*zr0,z0)
                        glVertex3f(x*zr1,y*zr1,z1)

                glEnd()

def drawRobotArm():
        global shoulder,elbow,finger
        
        
        glPushMatrix()
        glRotatef(shoulder,0,0,1)

        glPushMatrix()
        glScalef(.2,.04,.1)
        glColor3ub(255,255,0)
        drawCube()
        glPopMatrix()
        
        glPushMatrix()
        glTranslatef(.4,0,0)
        glRotatef(elbow,0,0,1)
        
        glPushMatrix()
        glScalef(.2,.04,.1)
        glColor3ub(255,165,0)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(.24,0,0)
        glRotatef(finger,0,0,1)
        glColor3ub(255,0,0)
        
        glPushMatrix()
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(0,0,.08)
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(0,0,-.08)
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(.08,0,0)
        glRotatef(finger,0,0,1)

        glPushMatrix()
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(0,0,.08)
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()

        glPushMatrix()
        glTranslatef(0,0,-.08)
        glScalef(.04,.02,.02)
        drawCube()
        glPopMatrix()
        
        glPopMatrix()
        glPopMatrix()
        glPopMatrix()
        glPopMatrix()

def animated():
        global shoulder,elbow,finger,sbit,ebit,fbit

        if sbit == 0:
                shoulder += 1
        elif sbit == 1:
                shoulder += -1
        else:
                pass

        if ebit == 0:
                elbow += 1
        elif ebit == 1:
                elbow += -1
        else:
                pass

        if fbit == 0:
                finger += 1
        elif fbit == 1:
                finger += -1
        else:
                pass

        if shoulder == 30:
                sbit = 1
        elif shoulder == 0:
                sbit = 0
        else:
                pass

        if elbow == 30:
                ebit = 1
        elif elbow == 0:
                ebit = 0
        else:
                pass

        if finger == 30:
                fbit = 1
        elif finger == 0:
                fbit = 0
        else:
                pass

        
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
	
	while not glfw.window_should_close(window):
		glfw.poll_events()
				
		render()

		animated()
                        
		glfw.swap_buffers(window)



	glfw.terminate()
	

if __name__=="__main__":
    main()
