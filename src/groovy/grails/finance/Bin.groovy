package grails.finance

@Immutable
class Bin {
	double start
	double end
	double mid
	String label
	
	Bin(s, e) {
		start = s
		end = e
		mid =  (start + end) / 2 
		label = sprintf('%.3g', mid)
	}
	
	String toString() {"($start, $end, $mid, $label )"}
}
