package grails.finance

class Index {
	static constraints = {
		name(unique:true, maxSize:100, blank:false)
		symbol(unique:true, maxSize:10, blank:false)
		exchange(blank:false)
	}
	
	String name
	String symbol
	Exchange exchange
	
	String toString() {name}
}
