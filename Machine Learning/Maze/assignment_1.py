from queue import Queue
from queue import PriorityQueue

maze = []
m = 0
n = 0

class State:
    def __init__(self,m,n,par):
        self.posRow = m
        self.posCol = n
        self.cost = 0
        self.left = None
        self.right = None
        self.up = None
        self.down = None
        self.parent = par

    def insert_left(self,m,n):
        self.left = State(m,n,self)
        self.left.cost = self.cost + 1

    def insert_right(self,m,n):
        self.right = State(m,n,self)
        self.right.cost = self.cost + 1

    def insert_up(self,m,n):
        self.up = State(m,n,self)
        self.up.cost = self.cost + 1

    def insert_down(self,m,n):
        self.down = State(m,n,self)
        self.down.cost = self.cost + 1

    def get_position(self):
        return self.posRow, self.posCol

    def get_left(self):
        return self.left

    def get_right(self):
        return self.right

    def get_up(self):
        return self.up

    def get_down(self):
        return self.down

    def get_parent(self):
        return self.parent

    def get_cost(self):
        return self.cost

    def is_parent(self,m,n):
        if self.parent == None:
            return False
        if self.parent.posRow == m and self.parent.posCol == n:
            return True
        else:
            return False

def get_key_position():
    for i in range(m):
        for j in range(n):
            if maze[i][j] == '6':
                return i,j

    return -1,-1

def get_start_position():
    for i in range(n):
        if maze[0][i] == '3':
            return 0,i

    return -1,-1

def get_goal_position():
    for i in range(n):
        if maze[m-1][i] == '4':
            return m-1,i

    return -1,-1

def search_with_BFS(start_row,start_col,goal_row,goal_col):
    global maze, m, n

    q = Queue()
    
    root = State(start_row,start_col,None)
    q.put(root)

    time = 0
    while 1:
        s = q.get()
        row, col = s.get_position()
        time += 1
        
        # is goal?
        if row == goal_row and col == goal_col:
            break

        # left
        if col > 0:
            if maze[row][col-1] != "1":
                if not s.is_parent(row,col-1):
                    s.insert_left(row,col-1)
                    q.put(s.get_left())

        # dowm
        if row < m-1:
            if maze[row+1][col] != "1":
                if not s.is_parent(row+1,col):
                    s.insert_down(row+1,col)
                    q.put(s.get_down())

        # right
        if col < n-1:
            if maze[row][col+1] != "1":
                if not s.is_parent(row,col+1):
                    s.insert_right(row,col+1)
                    q.put(s.get_right())

        # up
        if row > 0:
            if maze[row-1][col] != "1":
                if not s.is_parent(row-1,col):
                    s.insert_up(row-1,col)
                    q.put(s.get_up())
                    
    length = 0
    while s.get_parent() != None:
        row, col = s.get_position()
        if maze[row][col] != '4':
            maze[row][col] = '5'
        length += 1
        s = s.get_parent()

    return time, length

def search_with_GBF(start_row,start_col,goal_row,goal_col):
    global maze, m, n

    time = 0
    count = 0
    q = PriorityQueue()
    
    root = State(start_row,start_col,None)
    q.put((0,count,root))
    count += 1

    while 1:
        t = q.get()
        s = t[2]
        row, col = s.get_position()
        time += 1
        
        # is goal?
        if row == goal_row and col == goal_col:
            break

        # left
        if col > 0:
            if maze[row][col-1] != "1":
                if not s.is_parent(row,col-1):
                    s.insert_left(row,col-1)
                    h = abs(goal_row - row) + abs(goal_col - (col-1))
                    q.put((h,count,s.get_left()))
                    count += 1

        # dowm
        if row < m-1:
            if maze[row+1][col] != "1":
                if not s.is_parent(row+1,col):
                    s.insert_down(row+1,col)
                    h = abs(goal_row - (row+1)) + abs(goal_col - col)
                    q.put((h,count,s.get_down()))
                    count += 1

        # right
        if col < n-1:
            if maze[row][col+1] != "1":
                if not s.is_parent(row,col+1):
                    s.insert_right(row,col+1)
                    h = abs(goal_row - row) + abs(goal_col - (col+1))
                    q.put((h,count,s.get_right()))
                    count += 1

        # up
        if row > 0:
            if maze[row-1][col] != "1":
                if not s.is_parent(row-1,col):
                    s.insert_up(row-1,col)
                    h = abs(goal_row - (row-1)) + abs(goal_col - col)
                    q.put((h,count,s.get_up()))
                    count += 1
                    
    length = 0
    while s.get_parent() != None:
        row, col = s.get_position()
        if maze[row][col] != '4':
            maze[row][col] = '5'
        length += 1
        s = s.get_parent()

    return time, length

def search_with_Astar(start_row,start_col,goal_row,goal_col):
    global maze, m, n

    time = 0
    count = 0
    q = PriorityQueue()
    
    root = State(start_row,start_col,None)
    q.put((0,count,root))
    count += 1

    while 1:
        t = q.get()
        s = t[2]
        row, col = s.get_position()
        time += 1
        
        # is goal?
        if row == goal_row and col == goal_col:
            break

        # left
        if col > 0:
            if maze[row][col-1] != "1":
                if not s.is_parent(row,col-1):
                    s.insert_left(row,col-1)
                    h = abs(goal_row - row) + abs(goal_col - (col-1))
                    g = s.get_cost() + 1
                    q.put((h+g,count,s.get_left()))
                    count += 1

        # dowm
        if row < m-1:
            if maze[row+1][col] != "1":
                if not s.is_parent(row+1,col):
                    s.insert_down(row+1,col)
                    h = abs(goal_row - (row+1)) + abs(goal_col - col)
                    g = s.get_cost() + 1
                    q.put((h+g,count,s.get_down()))
                    count += 1

        # right
        if col < n-1:
            if maze[row][col+1] != "1":
                if not s.is_parent(row,col+1):
                    s.insert_right(row,col+1)
                    h = abs(goal_row - row) + abs(goal_col - (col+1))
                    g = s.get_cost() + 1
                    q.put((h+g,count,s.get_right()))
                    count += 1

        # up
        if row > 0:
            if maze[row-1][col] != "1":
                if not s.is_parent(row-1,col):
                    s.insert_up(row-1,col)
                    h = abs(goal_row - (row-1)) + abs(goal_col - col)
                    g = s.get_cost() + 1
                    q.put((h+g,count,s.get_up()))
                    count += 1
                    
    length = 0
    while s.get_parent() != None:
        row, col = s.get_position()
        if maze[row][col] != '4':
            maze[row][col] = '5'
        length += 1
        s = s.get_parent()

    return time, length
    

def read_file(path):
    global maze, m, n
    maze = []
    m = 0
    n = 0
    
    f = open(path,"r")
    meta_data = f.readline()
    floor = meta_data.split()[0]
    m = int(meta_data.split()[1])
    n = int(meta_data.split()[2])

    maze = []
    for line in f:
        maze.append(line.split())
    f.close

    return floor, m, n, maze

def write_file(path,length, time):
    f = open(path,"w")
    for i in range(int(n)):
        f.write(' '.join(maze[i]) + '\n')
    f.write("---\nlength="+length+"\ntime="+time)
    f.close
    print("Success for "+path+"!")


def first_floor():
    read_file("first_floor_input.txt")

    s_pos_row, s_pos_col = get_start_position()
    g_pos_row, g_pos_col = get_goal_position()
    k_pos_row, k_pos_col = get_key_position()

    time_start_to_key, length_start_to_key = search_with_GBF(s_pos_row,s_pos_col,k_pos_row,k_pos_col)
    time_key_to_goal, length_key_to_goal = search_with_GBF(k_pos_row,k_pos_col,g_pos_row, g_pos_col)

    time = time_start_to_key + time_key_to_goal
    length = length_start_to_key + length_key_to_goal

    write_file("first_floor_output.txt", str(length), str(time))

def second_floor():
    read_file("second_floor_input.txt")

    s_pos_row, s_pos_col = get_start_position()
    g_pos_row, g_pos_col = get_goal_position()
    k_pos_row, k_pos_col = get_key_position()

    time_start_to_key, length_start_to_key = search_with_GBF(s_pos_row,s_pos_col,k_pos_row,k_pos_col)
    time_key_to_goal, length_key_to_goal = search_with_GBF(k_pos_row,k_pos_col,g_pos_row, g_pos_col)

    time = time_start_to_key + time_key_to_goal
    length = length_start_to_key + length_key_to_goal

    write_file("second_floor_output.txt", str(length), str(time))

def third_floor():
    read_file("third_floor_input.txt")

    s_pos_row, s_pos_col = get_start_position()
    g_pos_row, g_pos_col = get_goal_position()
    k_pos_row, k_pos_col = get_key_position()

    time_start_to_key, length_start_to_key = search_with_GBF(s_pos_row,s_pos_col,k_pos_row,k_pos_col)
    time_key_to_goal, length_key_to_goal = search_with_GBF(k_pos_row,k_pos_col,g_pos_row, g_pos_col)

    time = time_start_to_key + time_key_to_goal
    length = length_start_to_key + length_key_to_goal

    write_file("third_floor_output.txt", str(length), str(time))

def fourth_floor():
    read_file("fourth_floor_input.txt")

    s_pos_row, s_pos_col = get_start_position()
    g_pos_row, g_pos_col = get_goal_position()
    k_pos_row, k_pos_col = get_key_position()

    time_start_to_key, length_start_to_key = search_with_GBF(s_pos_row,s_pos_col,k_pos_row,k_pos_col)
    time_key_to_goal, length_key_to_goal = search_with_GBF(k_pos_row,k_pos_col,g_pos_row, g_pos_col)

    time = time_start_to_key + time_key_to_goal
    length = length_start_to_key + length_key_to_goal

    write_file("fourth_floor_output.txt", str(length), str(time))

def fifth_floor():
    read_file("fifth_floor_input.txt")

    s_pos_row, s_pos_col = get_start_position()
    g_pos_row, g_pos_col = get_goal_position()
    k_pos_row, k_pos_col = get_key_position()

    time_start_to_key, length_start_to_key = search_with_GBF(s_pos_row,s_pos_col,k_pos_row,k_pos_col)
    time_key_to_goal, length_key_to_goal = search_with_GBF(k_pos_row,k_pos_col,g_pos_row, g_pos_col)

    time = time_start_to_key + time_key_to_goal
    length = length_start_to_key + length_key_to_goal

    write_file("fifth_floor_output.txt", str(length), str(time))
    
    
def main():
    
    first_floor()

    second_floor()

    third_floor()

    fourth_floor()

    fifth_floor()

    

if __name__=="__main__":
    main()
