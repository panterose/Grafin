package grails.finance

class Exchange {
	static constraints = {
		name(unique:true, maxSize:100, blank:false)
		country(maxSize:50, blank:false)
	}
	
	String name
	String country
	
	String toString() {name}
}
