#include <stdio.h>
#define INFINITY 9999

extern struct rtpkt {
  int sourceid;       /* id of sending router sending this pkt */
  int destid;         /* id of router to which pkt being sent 
                         (must be an immediate neighbor) */
  int mincost[4];    /* min cost to node 0 ... 3 */
};

extern int TRACE;
extern int YES;
extern int NO;
extern float clocktime;

int lkcost0[4];		/*The link cost between node 0 and other nodes*/
int spath0[4];		/*The shortest path between node 0 and other nodes*/
struct distance_table 		/*Define distance table*/
{
  int costs[4][4];
} dt0;

void printdt0(struct distance_table *dtptr);
void sendpkt0();
int update0();
void tolayer2(struct rtpkt packet);
void g_print0()
{
  printdt0(&dt0);
}
/* students to write the following two routines, and maybe some others */

void rtinit0() 
{
  int i, j;
  printf("At time t=%.3f, rtinit0() called. \n", clocktime);
  
  /* initialize the link costs */
  lkcost0[0] = 0;	lkcost0[1] = 1;	lkcost0[2] = 3;	lkcost0[3] = 7;
  
  /*initialze the distance table i denotes the destination node, j denotes the neighbour node*/
  for (i=0; i<4; i++)
    for (j=0; j<4; j++) {
      if (i==j)
      	dt0.costs[i][j] = lkcost0[i];
      else
      	dt0.costs[i][j] = INFINITY;
    }
    for (i=0; i<4; i++) 		/* compute the shortest path*/
      spath0[i]=lkcost0[i];
    printdt0(&dt0);			/* print distance table */
    sendpkt0();			/* make packet and send to other nodes*/
}


void rtupdate0(struct rtpkt *rcvdpkt)
{
  int i, j;
  j=rcvdpkt->sourceid;
  
  printf("At time t=%.3f, rtupdate0() called, and node 0 receives a packet from node %d\n",
    clocktime, j);
  
  /* update distance table*/
  for ( i= 0; i<4; i++) {
    dt0.costs[i][j] = lkcost0[j] + rcvdpkt->mincost[i];
    if (dt0.costs[i][j] > INFINITY)
      dt0.costs[i][j]=INFINITY;
  }
  printdt0(&dt0);				/*print distance table*/
  if (update0() == 1)	sendpkt0();	/*update shortest paths*/
}


void printdt0(struct distance_table *dtptr)
{
  printf("                via     \n");
  printf("   D0 |    1     2    3 \n");
  printf("  ----|-----------------\n");
  printf("     1|  %3d   %3d   %3d\n",dtptr->costs[1][1], dtptr->costs[1][2],dtptr->costs[1][3]);
  printf("dest 2|  %3d   %3d   %3d\n",dtptr->costs[2][1], dtptr->costs[2][2],dtptr->costs[2][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][1], dtptr->costs[3][2],dtptr->costs[3][3]);
}

void linkhandler0(int linkid, int newcost)   
/* called when cost from 0 to linkid changes from current value to newcost*/
/* You can leave this routine empty if you're an undergrad. If you want */
/* to use this routine, you'll need to change the value of the LINKCHANGE */
/* constant definition in prog3.c from 0 to 1 */	
{

  int i, oldcost;
  printf("At time t=%.3f, linkhandler0() called \n", clocktime);
  
  oldcost = lkcost0[linkid];
  lkcost0[linkid] = newcost;
  
  /*update distance table*/
  for (i=0; i<4; i++) {
    dt0.costs[i][linkid] = dt0.costs[i][linkid] - oldcost + newcost;
    if (dt0.costs[i][linkid] > INFINITY) dt0.costs[i][linkid] = INFINITY;
  }
  
  printdt0(&dt0);/* print distance table*/
  if (update0()==1) sendpkt0();	/* update shortest paths*/
	
}

int update0()
{
  int i, j;
  int tmp[4];
  int flag =0;
  
  for (i=0; i<4; i++) {	/*compute new shortest paths*/
    tmp[i] = dt0.costs[i][0];
    for (j=1; j<4; j++) {
      if (tmp[i] > dt0.costs[i][j]) tmp[i] = dt0.costs[i][j];
    }
    
    if (tmp[i] != spath0[i] ) {
      /*update shortest path*/
      spath0[i] = tmp[i];
      flag =1;
    }
  }
  return flag;
}


void sendpkt0()
{
  int i;
  struct rtpkt packet;
  
  /*make  packet*/
  packet.sourceid = 0;
  for (i=0; i<4; i++) 
    packet.mincost[i] = spath0[i];
  
  /*send out packet*/
  packet.destid=1;
  tolayer2(packet);
  printf("At time t=%.3f, node 0 sends packet to node 1 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  
  packet.destid=2;
  tolayer2(packet);
  printf("At time t=%.3f, node 0 sends packet to node 2 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  
  packet.destid=3;
  tolayer2(packet);
  printf("At time t=%.3f, node 0 sends packet to node 3 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  
  printf("\n");

}

