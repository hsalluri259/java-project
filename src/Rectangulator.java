public class Rectangulator {
	public static void main(String[] args){
		int length = Integer.parseInt(args[0]);
		int width = Integer.parseInt(args[1]);
		
		Rectangle myRectangle = new Rectangle(length, width);
		String output = String.format("*** Your Rectangle *** \n\n Length: %d\n Width: %d\n area: %d \n Perimeter: %d\n", myRectangle.length, myRectangle.width, myRectangle.getArea(), myRectangle.getPerimeter());
		System.out.println(output);
	}
}
