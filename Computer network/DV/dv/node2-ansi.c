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

int lkcost2[4];    /*the link cost between node 0 and other nodes*/
int spath2[4];    /*the shortest path between node 0 and other nodes*/

struct distance_table /*define distance table*/
{
  int costs[4][4];
} dt2;

void printdt2(struct distance_table *dtptr);
void sendpkt2();
int update2();
void tolayer2(struct rtpkt packet);
void g_print2()
{
  printdt2(&dt2);
}

/* students to write the following two routines, and maybe some others */
void rtinit2()
{
  int i, j;
  printf("At time t=%.3f, rtinit2() called \n", clocktime);

  /* initialize the link costs */
  lkcost2[0] = 3;
  lkcost2[1] = 1;
  lkcost2[2] = 0;
  lkcost2[3] = 2;

  /*initialze the distance table 
  i denotes the destination node, j denotes the neighbour node*/
  for (i=0; i<4; i++)
    for (j=0; j<4; j++) {
      if (i==j)
        dt2.costs[i][j] = lkcost2[i];
      else
        dt2.costs[i][j] = INFINITY;
    }

    /* compute the shortest path*/
    for (i=0; i<4; i++)  spath2[i]=lkcost2[i];
    printdt2(&dt2);    /* print distance table */
    sendpkt2();             /*make packet and send it to other nodes*/
}


void rtupdate2(struct rtpkt *rcvdpkt)
{
  int i, j;
  j=rcvdpkt->sourceid;
  printf("At time t=%.3f, rtupdate2() called. node 2 receives a packet from node %d\n",
    clocktime, j);

  /* update distance table*/
  for ( i= 0; i<4; i++) {
    dt2.costs[i][j] = lkcost2[j] + rcvdpkt->mincost[i];
    if (dt2.costs[i][j] > INFINITY)
      dt2.costs[i][j]=INFINITY;
  }

  printdt2(&dt2);      /*print distance table*/

  if (update2() == 1)  /*update shortest paths*/
    sendpkt2();
}


void printdt2(struct distance_table *dtptr)
{
  printf("                via     \n");
  printf("   D2 |    0     1    3 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d   %3d\n",dtptr->costs[0][0], dtptr->costs[0][1],dtptr->costs[0][3]);
  printf("dest 1|  %3d   %3d   %3d\n",dtptr->costs[1][0], dtptr->costs[1][1],dtptr->costs[1][3]);
  printf("     3|  %3d   %3d   %3d\n",dtptr->costs[3][0], dtptr->costs[3][1],dtptr->costs[3][3]);
}

void linkhandler2(int linkid, int newcost)   
/* called when cost from 0 to linkid changes from current value to newcost*/
/* You can leave this routine empty if you're an undergrad. If you want */
/* to use this routine, you'll need to change the value of the LINKCHANGE */
/* constant definition in prog3.c from 0 to 1 */
  
{

  int i, oldcost;
  printf("At time t=%.3f, linkhandler2() called. \n", clocktime);

  oldcost = lkcost2[linkid];
  lkcost2[linkid] = newcost;

  /*update distance table*/
  for (i=0; i<4; i++) {
    dt2.costs[i][linkid] = dt2.costs[i][linkid] - oldcost + newcost;
    if (dt2.costs[i][linkid] > INFINITY)
      dt2.costs[i][linkid] = INFINITY;
  }

  printdt2(&dt2);        /* print distance table*/
  if (update2()==1)           /* update shortest paths*/
    sendpkt2();
}

int update2()
{
  int i, j;
  int tmp[4];
  int flag =0;

  /*compute new shortest paths*/
  for (i=0; i<4; i++) {
    tmp[i] = dt2.costs[i][0];

    for (j=1; j<4; j++) {
      if (tmp[i] > dt2.costs[i][j])
        tmp[i] = dt2.costs[i][j];
    }
    if (tmp[i] != spath2[i] ) {   /*update shortest path*/
      spath2[i] = tmp[i];
      flag =1;
    }
  }
  return flag;
}

void sendpkt2()
{
  int i;
  struct rtpkt packet;
  packet.sourceid = 2;  /*make  packet*/
  for (i=0; i<4; i++) 
    packet.mincost[i] = spath2[i];
  
  /*send out packet*/
  packet.destid=0;
  tolayer2(packet);
  printf("At time t=%.3f, node 2 sends packet to node 0 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  packet.destid=1;
  tolayer2(packet);
  printf("At time t=%.3f, node 2 sends packet to node 1 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  packet.destid=3;
  tolayer2(packet);
  printf("At time t=%.3f, node 2 sends packet to node 3 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  printf("\n");

}

