/** Class to represent the nodes of the binary search tree.
 * @author Michelle Jensen (mejensen5)
 */

public class CampTreeNode {

	private Camper data;
	private CampTreeNode leftNode;
	private CampTreeNode rightNode;
	
	/*Constructor for an empty CampTreeNode*/
	public CampTreeNode()
	{
		data=null;
		leftNode=null;
		rightNode=null;
	}
	
	/**Getter for data field.
	 * @return The data of this CampTreeNode
	 */
	public Camper getData()
	{return data;}
	
	/**Getter for leftNode field.
	 * @return The leftNode of this CampTreeNode
	 */
	public CampTreeNode getLeftNode()
	{return leftNode;}
	
	/**Getter for rightNode field.
	 * @return The rightNode of this CampTreeNode
	 */
	public CampTreeNode getRightNode()
	{return rightNode;}
	
	/** Setter for data field
	 * @param camper, the Camper that the data  field will be set to
	 */
	public void setData(Camper camper)
	{data = camper;}
	
	/** Setter for leftNode field
	 * @param node, the CampTreeNode that the leftNode field will be set to
	 */
	public void setLeftNode(CampTreeNode node)
	{leftNode = node;}
	
	/** Setter for rightNode field
	 * @param node, the CampTreeNode that the rightNode field will be set to
	 */
	public void setRightNode(CampTreeNode node)
	{rightNode = node;}
	
}
