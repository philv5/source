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
int lkcost3[4];    /*the link cost between node 0 and other nodes*/
int spath3[4];    /*the shortest path between node 0 and other nodes*/

struct distance_table /*define distance table*/
{
  int costs[4][4];
} dt3;

void printdt3(struct distance_table *dtptr);
void sendpkt3();
int update3();
void tolayer2(struct rtpkt packet);
void g_print3()
{
  printdt3(&dt3);
}
/* students to write the following two routines, and maybe some others */
void rtinit3() 
{
  int i, j;  
  printf("At time t=%.3f, rtinit3() called. \n", clocktime);

  /* initialize the link costs */
  lkcost3[0] = 7;
  lkcost3[1] = INFINITY;
  lkcost3[2] = 2;
  lkcost3[3] = 0;

  /*initialze the distance table 
  i denotes the destination node, j denotes the neighbour node*/
  for (i=0; i<4; i++)
    for (j=0; j<4; j++) {
      if (i==j)
        dt3.costs[i][j] = lkcost3[i];
      else
        dt3.costs[i][j] = INFINITY;
    }
    
    for (i=0; i<4; i++) /* compute the shortest path*/
      spath3[i]=lkcost3[i];

    printdt3(&dt3);    /* print distance table */
    sendpkt3();    /*make packet and send it to other nodes*/
}


void rtupdate3(struct rtpkt *rcvdpkt)
{
  int i, j;
  j=rcvdpkt->sourceid;
  printf("At time t=%.3f, rtupdate3() called. node 3 receives a packet from node %d\n",
    clocktime, j);

  /* update distance table*/
  for ( i= 0; i<4; i++) {
    dt3.costs[i][j] = lkcost3[j] + rcvdpkt->mincost[i];
    if (dt3.costs[i][j] > INFINITY)
      dt3.costs[i][j]=INFINITY;
  }
  printdt3(&dt3);      /*print distance table*/
  if (update3() == 1)  /*update shortest paths*/
    sendpkt3();
}


void printdt3(struct distance_table *dtptr)
{
  printf("                via     \n");
  printf("   D3 |    0     2 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d\n",dtptr->costs[0][0],dtptr->costs[0][2]);
  printf("dest 1|  %3d   %3d\n",dtptr->costs[1][0],dtptr->costs[1][2]);
  printf("     2|  %3d   %3d\n",dtptr->costs[2][0],dtptr->costs[2][2]);

}

void linkhandler3(int linkid, int newcost)   
/* called when cost from 0 to linkid changes from current value to newcost*/
/* You can leave this routine empty if you're an undergrad. If you want */
/* to use this routine, you'll need to change the value of the LINKCHANGE */
/* constant definition in prog3.c from 0 to 1 */
  
{

  int i, oldcost;
  printf("At time t=%.3f, linkhandler3() is called \n", clocktime);

  oldcost = lkcost3[linkid];
  lkcost3[linkid] = newcost;

  /*update distance table*/
  for (i=0; i<4; i++) {
    dt3.costs[i][linkid] = dt3.costs[i][linkid] - oldcost + newcost;
    if (dt3.costs[i][linkid] > INFINITY)
      dt3.costs[i][linkid] = INFINITY;
  }
  printdt3(&dt3);    /* print distance table*/
  if (update3()==1)       /* update shortest paths*/
    sendpkt3();
}

int update3()
{
  int i, j;
  int tmp[4];
  int flag =0;

  /*compute new shortest paths*/
  for (i=0; i<4; i++) {
    tmp[i] = dt3.costs[i][0];
    for (j=1; j<4; j++) {
      if (tmp[i] > dt3.costs[i][j])
        tmp[i] = dt3.costs[i][j];
    }
    if (tmp[i] != spath3[i] ) {
      /*update shortest path*/
      spath3[i] = tmp[i];
      flag =1;
    }
  }
  return flag;
}


void sendpkt3()
{
  int i;
  struct rtpkt packet;

  /*make  packet*/
  packet.sourceid = 3;
  for (i=0; i<4; i++) 
    packet.mincost[i] = spath3[i];

  /*send out packet*/
  packet.destid=0;
  tolayer2(packet);
  printf("At time t=%.3f, node 3 sends packet to node 0 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  packet.destid=2;
  tolayer2(packet);
  printf("At time t=%.3f, node 3 sends packet to node 2 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  printf("\n");

}

