/**
 *	The Snake is a singly linked list with snake behaviors.
 * 
 *	@author	Aryan Singhal
 *	@since	May 2, 2023
 */

public class Snake extends SinglyLinkedList<Coordinate>
{
    public Snake(int row, int col)
    {
        for(int i = 0; i < 5; i++)
        {
            Coordinate coord = new Coordinate(row + i, col);
            this.add(coord);
        }
    }
}
