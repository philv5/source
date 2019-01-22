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
int lkcost1[4];    /*the link cost between node 0 and other nodes*/
int spath1[4];    /*the shortest path between node 0 and other nodes*/

struct distance_table /*define distance table*/
{
  int costs[4][4];
} dt1;

void printdt1(struct distance_table *dtptr);
void sendpkt1();
int update1();
void tolayer2(struct rtpkt packet);
void g_print1()
{
  printdt1(&dt1);
}

/* students to write the following two routines, and maybe some others */
void rtinit1() 
{
  int i, j;  
  printf("At time t=%.3f, rtinit1() called \n", clocktime);

  /* initialize the link costs */
  lkcost1[0] = 1;
  lkcost1[1] = 0;
  lkcost1[2] = 1;
  lkcost1[3] = INFINITY;

  /*initialze the distance table 
  i denotes the destination node, j denotes the neighbour node*/
  for (i=0; i<4; i++)
    for (j=0; j<4; j++) {
      if (i==j)   dt1.costs[i][j] = lkcost1[i];
      else    dt1.costs[i][j] = INFINITY;
    }
    for (i=0; i<4; i++)  /* compute the shortest path*/
      spath1[i]=lkcost1[i];
    printdt1(&dt1);         /* print distance table */
    sendpkt1();    /*make packet and send it to other nodes*/
}


void rtupdate1(struct rtpkt *rcvdpkt)
{
  int i, j;

  j=rcvdpkt->sourceid;

  printf("At time t=%.3f, rtupdate1() called. node 1 receives a packet from node %d\n",clocktime, j);

  /* update distance table*/
  for ( i= 0; i<4; i++) {
    dt1.costs[i][j] = lkcost1[j] + rcvdpkt->mincost[i];
    if (dt1.costs[i][j] > INFINITY)
      dt1.costs[i][j]=INFINITY;
  }

  printdt1(&dt1);        /*print distance table*/
  if (update1() == 1)  sendpkt1();   /*update shortest paths*/
}

void printdt1(struct distance_table *dtptr)
{
  printf("                via     \n");
  printf("   D1 |    0     2 \n");
  printf("  ----|-----------------\n");
  printf("     0|  %3d   %3d \n",dtptr->costs[0][0], dtptr->costs[0][2]);
  printf("dest 2|  %3d   %3d \n",dtptr->costs[2][0], dtptr->costs[2][2]);
  printf("     3|  %3d   %3d \n",dtptr->costs[3][0], dtptr->costs[3][2]);
}

void linkhandler1(int linkid, int newcost)   
/* called when cost from 0 to linkid changes from current value to newcost*/
/* You can leave this routine empty if you're an undergrad. If you want */
/* to use this routine, you'll need to change the value of the LINKCHANGE */
/* constant definition in prog3.c from 0 to 1 */  
{

  int i, oldcost;
  printf("At time t=%.3f, linkhandler0() called \n", clocktime);
  oldcost = lkcost1[linkid];
  lkcost1[linkid] = newcost;

  /*update distance table*/
  for (i=0; i<4; i++) {
    dt1.costs[i][linkid] = dt1.costs[i][linkid] - oldcost + newcost;
    if (dt1.costs[i][linkid] > INFINITY)
      dt1.costs[i][linkid] = INFINITY;
  }

  printdt1(&dt1);      /* print distance table*/
  if (update1()==1)    /* update shortest paths*/
    sendpkt1();
}

int update1()
{
  int i, j;
  int tmp[4];
  int flag =0;

  /*compute new shortest paths*/
  for (i=0; i<4; i++) {
    tmp[i] = dt1.costs[i][0];
    for (j=1; j<4; j++) {
      if (tmp[i] > dt1.costs[i][j])
        tmp[i] = dt1.costs[i][j];
    }
    if (tmp[i] != spath1[i] ) {
      /*update shortest path*/
      spath1[i] = tmp[i];
      flag =1;
    }
  }
  return flag;
}


void sendpkt1()
{
  int i;
  struct rtpkt packet;
  packet.sourceid = 1;  /*make  packet*/
  for (i=0; i<4; i++) 
    packet.mincost[i] = spath1[i];
  packet.destid=0;  /*send out packet*/
  tolayer2(packet);
  printf("At time t=%.3f, node 1 sends packet to node 0 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  packet.destid=2;
  tolayer2(packet);
  printf("At time t=%.3f, node 1 sends packet to node 2 with: %d %d %d %d\n",
    clocktime, packet.mincost[0], packet.mincost[1], packet.mincost[2],packet.mincost[3]);
  printf("\n");

}

