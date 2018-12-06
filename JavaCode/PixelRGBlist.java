import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PixelRGBlist {
	private HashMap<Integer,LinkedList<PixelRGB>> list;
	
	public PixelRGBlist() {
		list=new HashMap<Integer,LinkedList<PixelRGB>>();
	}
	
	public void add(PixelRGB p) {
		LinkedList<PixelRGB> l=list.get(p.getPixel());
		if(l==null) {
			LinkedList<PixelRGB> n=new LinkedList<PixelRGB>();
			n.add(p);
			list.put(p.getPixel(), n);
		}
		else {
			l.add(p);
		}
	}
	
	public LinkedList<PixelRGB> sort(){
		List<Map.Entry<Integer, LinkedList<PixelRGB>>> intervals =
                new ArrayList<Map.Entry<Integer, LinkedList<PixelRGB>>>(list.entrySet());
		
		for (int i = 0; i < intervals.size(); i++) {
			if(intervals.get(i).getValue().size()<20)intervals.remove(i);
			else {
				LinkedList<PixelRGB> nl=finddifferent(intervals.get(i).getValue());
				intervals.get(i).setValue(nl);
			}
		}
//        System.out.println("------------------排序前--------------------------");
//        for (int i = 0; i < intervals.size(); i++) {
//        	PixelRGB p=new PixelRGB();
//        	p.setRGB(intervals.get(i).getKey());
//        	p.printRGB();
//        	System.out.println("  "+intervals.get(i).getKey()+" "+intervals.get(i).getValue().size());
//        }
        
        Collections.sort(intervals, new Comparator<Map.Entry<Integer, LinkedList<PixelRGB>>>() {   
            public int compare(Map.Entry<Integer, LinkedList<PixelRGB>> o1, Map.Entry<Integer, LinkedList<PixelRGB>> o2) { 
                  return o2.getValue().size()-o1.getValue().size();
            }
        }); 
        for (int i = 1; i < intervals.size(); i++) {
        	if(!intervals.get(0).getValue().get(0).compara(intervals.get(i).getValue().get(0), 10))return intervals.get(i).getValue();
        }
        return null;
//        System.out.println("------------------排序后--------------------------");
//        for (int i = 0; i < intervals.size(); i++) {
//        	PixelRGB p=new PixelRGB();
//        	p.setRGB(intervals.get(i).getKey());
//        	p.printRGB();
//        	System.out.println("  "+intervals.get(i).getKey()+" "+intervals.get(i).getValue().size());
//        	if(intervals.get(i).getValue().size()<5)break;
//        }
	}
	
	public LinkedList<PixelRGB> finddifferent(LinkedList<PixelRGB> l) {
		LinkedList<PixelRGB> nl=new LinkedList<PixelRGB>();
		PixelRGB before=l.get(0);
		for (PixelRGB j : l) {
			if(before.comparaPosition(j,30)) {
				nl.add(j);
			}
			else break;
			before=j;
		}
		return nl;
	}
	
}
