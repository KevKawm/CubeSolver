package TheAmazingAtheists.com.github.Algorithms;

abstract public interface F2L{
	
	public static String[] F2L = {
		//Corner correct, edge not placed
/*0*/		"",
/*1*/		"R,U2,RI,U,R,U2,RI,U,FI,UI,F,",
/*2*/		"U,R,UI,RI,UI,FI,U,F,",
/*3*/		"UI,FI,U,F,U,R,UI,RI,",
		//Edge correct, corner slot free
/*4*/   	"R,U,RI,UI,R,U,RI,UI,R,U,RI,",
/*5*/		"UI,R,U2,RI,U,R,U,RI,",
/*6*/		"U,FI,U2,F,UI,FI,UI,F,",
		//Corner in first layer twisted clockwise
/*7*/		"R2,U2,RI,UI,R,UI,RI,U2,RI,",
/*8*/		"R,UI,R,U,B,UI,BI,R2,",
/*9*/		"U,FI,U2,F,U2,FI,UI,F,",
/*10*/		"R,UI,RI,U,R,UI,RI,",
		//Corner in first layer twisted counter-clockwise
/*11*/		"R,U2,R,U,RI,U,R,U2,R2,",
/*12*/		"R,UI,RI,FI,LI,U2,L,F,",
/*13*/		"R,U,RI,U2,FI,U,F,",
/*14*/		"R,U,RI,UI,R,U,RI,",
		//Corner in U-layer with cross-color on U-face
/*15*/		"FI,U,F,R,U2,RI,",
/*16*/		"FI,U2,F,U,FI,UI,F,",
/*17*/		"U2,R2,U2,RI,UI,R,UI,R2,",
/*18*/		"UI,FI,U2,F,UI,FI,U,F,",
/*19*/		"U2,R,U,RI,U,R,UI,RI,",
/*20*/		"R,U2,RI,UI,R,U,RI,",
/*21*/		"U2,F2,U2,F,U,FI,U,F2,",
/*22*/		"U,R,U2,RI,U,R,UI,RI,",
/*23*/		"FI,LI,U2,L,F,",
		//Corner in U-layer with cross-color on R-face
/*24*/		"U,FI,UI,F,UI,R,U,RI,",
/*25*/		"UI,FI,U,F,",
/*26*/		"U,FI,U2,F,UI,R,U,RI,",
/*27*/		"U,FI,UI,F,U2,FI,U,F,",
/*28*/		"U,FI,U,F,UI,R,U,RI,",
/*29*/		"UI,R,UI,RI,U,R,U,RI,",
/*30*/		"R,UI,RI,U2,FI,UI,F,",
/*31*/		"R,U,RI,",
/*32*/		"U,FI,U2,F,U2,FI,U,F,",
		//Corner in U-layer with cross-color in F-face
/*33*/		"UI,R,U,RI,U,FI,UI,F,",
/*34*/		"U,FI,U,F,UI,FI,UI,F,",
/*35*/		"R,B,L,UI,LI,BI,RI,",
/*36*/		"FI,UI,F,",
/*37*/		"UI,R,U2,RI,U2,R,UI,RI,",
/*38*/		"U,R,UI,RI,",
/*39*/		"UI,R,U2,RI,U,FI,UI,F,",
/*40*/		"UI,R,U,RI,U2,R,UI,RI,",
/*41*/		"UI,R,UI,RI,U,FI,UI,F,"
	};
	
}
