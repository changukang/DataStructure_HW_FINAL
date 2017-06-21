import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.lang.Integer;
import java.lang.Double;
import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

class Node {
  public int val;
  public Node left;
  public Node right;
  public Node p;
  public int color;
  /////
  /// color RED is assigned to integer 1
  /// color BLACK is assigned to integer 0
  /////
  public Node(){
  	val = 0;
    left = null;
    right = null;
    p = null;
    color = 0;
  }

  public static Node Nil(){
  	return new Node();
  }	

  public Node(int newval) {
    val = newval;
    left = Nil();
    right = Nil();
    left.p = this;
    right.p = this;
    p = Nil();
    color = 1;
  }
}

class RB{
	public int[] none_found = new int[100000]; //없느 노드를 기록하기 위함
	public int miss = 0;
	public int index = 0;
	public int insert = 0;
	public int delete = 0;

	public Node root;
	public RB(){
		root = Node.Nil();
	}

	public void Left_Rotate(Node x){
		Node y = x.right;
		x.right = y.left;

		if(y.left.val != 0){
			y.left.p = x;
		}
		y.p = x.p;
		if(x.p.val == 0){
			root = y;
		}
		else if(x==x.p.left){
			x.p.left =y;
		}
		else{
			x.p.right = y;
		}
		y.left = x;
		x.p = y;
	}

	public void Right_Rotate(Node x){
		Node y = x.left;
		x.left = y.right;
		if(y.right.val != 0){
			y.right.p = x;
		}
		y.p = x.p;
		if(x.p.val == 0){
			root = y;
		}
		else if( x==x.p.right ){
			x.p.right =y;
		}
		else{
			x.p.left = y;
		}
		y.right = x;
		x.p =y;
	}

	public void RB_INSERT_FIXUP(Node z){
		Node y;
			while( z.p.color == 1){
				if(z.p == z.p.p.left){
					y = z.p.p.right; //y 는 삼촌
					if(y.color == 1){ //CASE 1
						z.p.color = 0;
						y.color = 0;
						z.p.p.color = 1;
						z = z.p.p;
					}
					else
					{
					 if (z==z.p.right){ //CASE 2
						z = z.p;
						Left_Rotate(z);
					}
					z.p.color = 0;//CASE 3
					z.p.p.color = 1;
					Right_Rotate(z.p.p);
					}
					
				}
				////Following code is for right
				else{
				if(z.p == z.p.p.right){
					y = z.p.p.left; //y 는 삼촌
					if(y.color == 1){ //CASE 1
						z.p.color = 0;
						y.color = 0;
						z.p.p.color = 1;
						z = z.p.p;
					}
					else
					{
					 if (z==z.p.left){ //CASE 2
						z = z.p;
						Right_Rotate(z);
					}
					z.p.color = 0;//CASE 3
					z.p.p.color = 1;
					Left_Rotate(z.p.p);
				}
			}
		}
		}
		root.color = 0; 
	}


	public void RB_insert(Node z){
		Node y = Node.Nil();
		Node x = root;
	    while (x.val != 0){
	      y = x;
	      if (z.val < x.val){
	        x = x.left;
	      }
	      else{
	        x = x.right;
	      }
	    }

    z.p = y;
    if (y.val == 0){
      root = z;
    }
    else if (z.val < y.val){
      y.left = z;
    }
    else{
      y.right=z;
    }
    // z.left = Node.Nil();
    // z.right = Node.Nil();
    // z.color = 1;

	RB_INSERT_FIXUP(z);

	}

    // public void tree_print(Node tree, int level) {

	   //  if (tree.right != null)
	   //    tree_print(tree.right, level + 1);

	   //  for(int i = 0; i < level; i++)
	   //    System.out.print("        ");

	   //  System.out.println(tree.val+""+tree.color);
	   //  if (tree.left != null)
	   //    tree_print(tree.left, level + 1);
    // }

   public void RB_DELETE_FIXUP(Node x){
   	Node w;
  	while(x!=root && x.color == 0){
  		if(x==x.p.left){
  			w = x.p.right;
  			if(w.color == 1){
  				w.color = 0;
  				x.p.color = 1;
  				Left_Rotate(x.p);
  				w=x.p.right;
  			}
  			if(w.left.color == 0 && w.right.color == 0){
  				w.color =1;
  				x=x.p;
  			}
  			else{
  				if(w.right.color == 0){
  					w.left.color = 0;
  					w.color = 1;
  					Right_Rotate(w);
  					w = x.p.right;
  				}
  				w.color = x.p.color;
  				x.p.color = 0;
  				w.right.color = 0;
  				Left_Rotate(x.p);
  				x = root;
  			}
  		}
  		else{
  			w = x.p.left;
  			if(w.color == 1){
  				w.color = 0;
  				x.p.color = 1;
  				Right_Rotate(x.p);
  				w=x.p.left;
  			}
  			if(w.right.color == 0 && w.left.color ==0){
  				w.color = 1;
  				x=x.p;
  			}
  			else{
  				if(w.left.color == 0){
  					w.right.color = 0;
  					w.color = 1;
  					Left_Rotate(w);
  					w = x.p.left;

  					}
  				w.color = x.p.color;
  				x.p.color = 0;
  				w.left.color = 0;
  				Right_Rotate(x.p);
  				x = root;
  			}
  		}
	}
	x.color = 0;

  	}

	public void RB_Transplant(Node u, Node v){
	  	if(u.p.val==0){
	  		root = v;
	  	}
	  	else if(u == u.p.left){
	  		u.p.left = v;
	  	}
	  	else{
	  		u.p.right = v;
	  	}
	  	v.p = u.p;
	 }


	public void RB_delete(Node z){
		Node y = z;
 	 	int y_org_col = y.color;
 	 	Node x;
	 	//checker
	 	// System.out.println(z.left);
	 	if(z.left.val == 0){
	 		x = z.right;
	 		RB_Transplant(z,z.right);
	 	}
	 	else if(z.right.val == 0){
	 		x = z.left;
	 		RB_Transplant(z, z.left);
	 	}
	 	else{
	 		y = tree_min(z.right);
	 		y_org_col = y.color;
	 		x = y.right;
	 		if(y.p == z){
	 		 x.p=y;
	 		}
	 		else{
	 			RB_Transplant(y,y.right);
	 			y.right = z.right;
	 			y.right.p =y;
	 		}
	 		RB_Transplant(z,y);
	 		y.left = z.left;
	 		y.left.p = y;
	 		y.color = z.color;
	 	}
	 	if(y_org_col == 0){
	 		RB_DELETE_FIXUP(x);
	 	}

	 }



  	public void inorder(Node tree) {
  		String col = null;
    	if (tree.val == 0)
      		return;
   		else {
	      inorder(tree.left);
	      if(tree.color ==1){
	      	col = "R";
	      }
	      else{
	      	col = "B";
	      }
	      System.out.println(tree.val +" " + col);
	      inorder(tree.right);
   		 }
 	 }

 	 public int NodeCount(Node tree){
 	 	int c = 1;
 	 	if( tree.right.val!=0) { c+= NodeCount(tree.right); }
 	 	if( tree.left.val!=0) { c+= NodeCount(tree.left); }

 	 	return c;
  	}

	public int BlackNodeCount(Node tree){	
		int c = 0;
		if(tree.left.val != 0) c+=BlackNodeCount(tree.left);
		if(tree.right.val!=0) c+=BlackNodeCount(tree.right);
		if(tree.color == 0) c+=1;
		return c;
	}


	public int BlackHeight(Node tree){
		if(tree.val == 0){
			return 0;
		}
		else if(tree.color==0){
			return BlackHeight(tree.left) + 1;
		}
		else
			return BlackHeight(tree.left);
	}



	 public Node tree_search(Node tree, int val) {
	 	if (tree.val == 0)
	      return Node.Nil();
	    else if (val == tree.val)
	      return tree;
	    else if (val < tree.val)
	      return tree_search(tree.left,val);
	    else
	      return tree_search(tree.right,val);
  	}


  	public Node tree_min(Node n){
	    while(n.left.val != 0){
	      n = n.left;
	    }
	    return n;
	  }

	public Node tree_max(Node n){
	    while(n.right.val != 0){
	      n = n.right;
	    }
	    return n;
	  }




	public Node Successor(Node x){
		if(x.right.val!=0){
			return tree_min(x.right);
		}
		Node y = x.p;
		while (y.val != 0 && x==y.right) {
			x = y;
			y = y.p;
		}

		return y;
	}


	public Node Predecessor(Node x){

		if(x.left.val!=0){
			return tree_max(x.left);
		}
		Node y = x.p;
		while (y.val != 0 && x==y.left) {
			x = y;
			y = y.p;
		}

		return y;
	}

	//following is method for HW06

	public String NodeFind(Node tree, int n){ 
		Node x = tree_search(tree, n);

		Node x_s;
		Node x_p;

		String x_s_val = "temp";
		String x_val = "temp";
		String x_p_val = "temp";
		if(x.val!=0){
			x_s = Successor(x);
			x_p = Predecessor(x);

			x_val = "" + x.val;

			if(x_s.val!=0){
				x_s_val = "" + x_s.val;
			}
			else{
				x_s_val = "NIL";
			}

			if(x_p.val != 0 ){
				x_p_val = "" + x_p.val;
			}
			else{
				x_p_val = "NIL";
			}
		}
		else{
			Node min = tree_min(tree);
			Node max = tree_max(tree);
			if( min.val > n){
				x_p_val = "NIL";
				x_val = "NIL";
				x_s_val = "" + min.val;
 
			}
			else if(max.val< n){
				x_p_val = "" + max.val;
				x_val = "NIL";
				x_s_val = "NIL";
			}
			else{

				Node for_search = min;
				Node for_search_suc = Successor(for_search);
				while(for_search.val != 0 ){
				if(for_search.val < n &&  n < for_search_suc.val){
					x_p = for_search;
					x_p_val = ""+x_p.val;
					x_val = "NIL";
					x_s = for_search_suc;
					x_s_val = ""+x_s.val;
					break;
				}
				else{
					for_search = for_search_suc;
				}
			}

			}
		}

		return (x_p_val+ " " + x_val +" "+ x_s_val);




	}

}


public class HW06 {


    public static void main(String[] args) throws IOException {
//   	int index = 0;    	
  //   	 //Getting input from txt file
    	BufferedReader br= null;

  //   	RB[] temp = new RB[1000];
    	RB temp = new RB();
    	br = new BufferedReader(new FileReader("./prjtest/test01.txt"));

		while(true) {
            String line = br.readLine();
            if (line==null) break;
            double d = Double.parseDouble(line);
            int num = (int)d;
            if(num > 0){
            	temp.RB_insert(new Node(num));
            	temp.insert +=1;
            }
            else if(num<0){
            	Node del;
            	del = temp.tree_search(temp.root, num *= -1);
            	if(del.val != 0){
           			temp.RB_delete(del);
           			temp.delete+=1;
           		}
           		else{
           			num*=-1;
           			temp.none_found[temp.index] = num;
           			temp.index += 1;
           			temp.miss+=1;
           		}
           	}
            else if(num==0){
            	break;
    	}

        }

	    br.close();
	    BufferedWriter fw = new BufferedWriter(new FileWriter("./output.txt",true));
	    BufferedReader search = new BufferedReader(new FileReader("./prjtest/search01.txt"));
	    while(true){
	    	String line = search.readLine();
	    	if (line==null) break;
	    	int num = Integer.parseInt(line);
	    	if(num==0){
	    		break;
	    	}
	    	else{
	    		System.out.println(temp.NodeFind(temp.root,num));
	    		fw.write(temp.NodeFind(temp.root,num)+"\n");
	    	
	    	}

	    }
	    fw.close();
	    search.close();
	    // System.out.println(temp.NodeFind(temp.root,5));
	    // System.out.println(temp.NodeFind(temp.root,30));
	    // System.out.println(temp.NodeFind(temp.root,4));

  //   	File dir = new File("./rbtest/"); 
		// File[] fileList = dir.listFiles(); 

		// for(int i = 0 ; i < fileList.length ; i++){
		// 		File file = fileList[i]; 
		// 		temp[index] = new RB();
		// 		br = new BufferedReader(new FileReader("./rbtest/" + file.getName()));
		// 		while(true) {
		//             String line = br.readLine();
		//             // System.out.println(line);
		//             if (line==null) break;
		//             // int num = Integer.parseInt(line);
		//             double d = Double.parseDouble(line);
		//             int num = (int)d;
		//             if(num > 0){
		//             	temp[index].RB_insert(new Node(num));
		//             	temp[index].insert +=1;
		//             }
		//             else if(num<0){
		//             	Node del;
		//             	del = temp[index].tree_search(temp[index].root, num *= -1);
		//             	if(del.val != 0){
		//            			temp[index].RB_delete(del);
		//            			temp[index].delete+=1;
		//            		}
		//            		else{
		//            			num*=-1;
		//            			temp[index].none_found[temp[index].index] = num;
		//            			temp[index].index += 1;
		//            			temp[index].miss+=1;
		//            		}
		//            	}
		//             else if(num==0){
		//             	break;
  //           }


  //       }
	 //        br.close();

	 //        System.out.println("filename = " + file.getName());
	 //        System.out.println("total = " + temp[index].NodeCount(temp[index].root));
	 //        System.out.println("insert = " + temp[index].insert);
	 //        System.out.println("deleted = " + temp[index].delete);
	 //        System.out.println("miss = " + temp[index].miss);
	 //        System.out.println("nb = " + temp[index].BlackNodeCount(temp[index].root));
	 //        System.out.println("bh = " + temp[index].BlackHeight(temp[index].root));
	 //        temp[index].inorder(temp[index].root);
	 //        index+=1;
		// }


    }
}
