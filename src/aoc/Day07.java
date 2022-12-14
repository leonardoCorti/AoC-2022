package aoc;

import java.util.LinkedList;
import java.util.List;

public class Day07 extends AocCore {

	public Day07() {
		super("7");
	}
	
	public static void main(String[] args) {
		new Day07();
	}
	
	public abstract class Resource{
		
		public Resource(Folder parentFolder, String name) {
			super();
			this.parentFolder = parentFolder;
			this.name = name;
		}
		public Folder parentFolder;
		public String name;
		public abstract int getSize();
		
	}
	
	public class Folder extends Resource{
		
		public List<Resource> contents;
		

		public Folder(Folder parentFolder, String name) {
			super(parentFolder, name);
			contents = new LinkedList<>();
		}

		@Override
		public int getSize() {
			int sizeOfContents=0;
			for(Resource element : contents) {
				sizeOfContents += element.getSize();
			}
			return sizeOfContents;
		}
		public void addContent(Resource toAdd) {
			contents.add(toAdd);
		}

	}

	public class File extends Resource {

		public File(Folder parentFolder, String name, int size) {
			super(parentFolder, name);
			this.size = size;
		}

		public int size;

		@Override
		public int getSize() {
			return size;
		}
		
	}

	@Override
	public void solve(List<String> input) {
		Folder currentFolder = new Folder(null, "/");		
		Folder root=currentFolder;

		List<Folder> allFolders = new LinkedList<>();
		allFolders.add(root);
		
		for(String line : input) {
//			System.out.println(line);
			var partsOfLine = line.split(" ");
			
			String firstPartLine = partsOfLine[0];
			if(firstPartLine.equals("$")) { //COMMAND
				String secondPart = partsOfLine[1];
				switch (secondPart) {
				case "cd": {
					String nameOfDir = partsOfLine[2];
					if(nameOfDir.equals("/")) {
						currentFolder=root;
						break;
					}else if (nameOfDir.equals("..")) {
						currentFolder = currentFolder.parentFolder;
						break;
					}
					currentFolder =(Folder) currentFolder.contents.stream()
							.filter(e->e.name.equals(nameOfDir))
							.findFirst().get();
					allFolders.add(currentFolder);
				}
				case "ls": {

					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + secondPart);
				}
			}else if (firstPartLine.equals("dir")) { //DIR
				String nameOfDir = partsOfLine[1];
				currentFolder.addContent(new Folder(currentFolder, nameOfDir));
			}else { //FILE
				int sizeOfFile = Integer.parseInt(partsOfLine[0]);
				String nameOfFile = partsOfLine[1];
				currentFolder.addContent(new File(currentFolder, nameOfFile, sizeOfFile));
			}
				
		} // end of for
		
		int sizeAtMost = 100000;
		int pt1 = allFolders.stream()
				.map(fol -> fol.getSize())
				.mapToInt(Integer::intValue)
				.filter(size -> size<sizeAtMost)
				.sum();
		
		int spaceAvaible = 70000000;
		int freeSpaceRequested = 30000000;
		int spaceToFree = freeSpaceRequested - ( spaceAvaible - root.getSize() );
		Folder folderToDelete = allFolders.stream()
				.filter(e -> e.getSize()>=spaceToFree)
				.sorted((e1,e2) -> e1.getSize()-e2.getSize())
				.findFirst()
				.get();
		int pt2 = folderToDelete.getSize();
		
		System.out.println("pt1: "+pt1);
		System.out.println("pt2: "+pt2);
		
		

	}

}
